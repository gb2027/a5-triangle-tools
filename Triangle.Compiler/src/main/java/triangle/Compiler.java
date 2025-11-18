/*
 * @(#)Compiler.java                       
 * 
 * Revisions and updates (c) 2022-2025 Sandy Brownlee. alexander.brownlee@stir.ac.uk
 * 
 * Original release:
 *
 * Copyright (C) 1999, 2003 D.A. Watt and D.F. Brown
 * Dept. of Computing Science, University of Glasgow, Glasgow G12 8QQ Scotland
 * and School of Computer and Math Sciences, The Robert Gordon University,
 * St. Andrew Street, Aberdeen AB25 1HG, Scotland.
 * All rights reserved.
 *
 * This software is provided free for educational use only. It may
 * not be used for commercial purposes without the prior written permission
 * of the authors.
 */

package triangle;

import triangle.abstractSyntaxTrees.Program;
import triangle.codeGenerator.Emitter;
import triangle.codeGenerator.Encoder;
import triangle.contextualAnalyzer.Checker;
import triangle.optimiser.ConstantFolder;
import triangle.syntacticAnalyzer.Parser;
import triangle.syntacticAnalyzer.Scanner;
import triangle.syntacticAnalyzer.SourceFile;
import triangle.treeDrawer.Drawer;
import org.apache.commons.cli.*;
import triangle.visitor.StatisticsSummary;

/**
 * The main driver class for the Triangle compiler.
 */
public class Compiler {

	/** The filename for the object program, normally obj.tam. */
	static String objectName = "obj.tam";
	
	static boolean showTree = false;
	static boolean folding = false;
    static boolean showTreeAfter = false;
    static boolean showStats = false;
    static String sourceName;

	private static Scanner scanner;
	private static Parser parser;
	private static Checker checker;
	private static Encoder encoder;
	private static Emitter emitter;
	private static ErrorReporter reporter;
	private static Drawer drawer;

	/** The AST representing the source program. */
	private static Program theAST;

	/**
     * Compile the source program to TAM machine code.
     *
     * @param sourceName   the name of the file containing the source program.
     * @param objectName   the name of the file containing the object program.
     * @param showingAST   true iff the AST is to be displayed after contextual
     *                     analysis
     * @param showingTable true iff the object description details are to be
     *                     displayed during code generation (not currently
     *                     implemented).
     * @param showStats
     * @return true iff the source program is free of compile-time errors, otherwise
     * false.
     */
	static boolean compileProgram(String sourceName, String objectName, boolean showingAST, boolean showingTable, boolean showStats) {

		System.out.println("********** " + "Triangle Compiler (Java Version 2.1)" + " **********");

		System.out.println("Syntactic Analysis ...");
		SourceFile source = SourceFile.ofPath(sourceName);

		if (source == null) {
			System.out.println("Can't access source file " + sourceName);
			System.exit(1);
		}

		scanner = new Scanner(source);
		reporter = new ErrorReporter(false);
		parser = new Parser(scanner, reporter);
		checker = new Checker(reporter);
		emitter = new Emitter(reporter);
		encoder = new Encoder(emitter, reporter);
		drawer = new Drawer();

		// scanner.enableDebugging();
		theAST = parser.parseProgram(); // 1st pass
		if (reporter.getNumErrors() == 0) {
			// if (showingAST) {
			// drawer.draw(theAST);
			// }
			System.out.println("Contextual Analysis ...");
			checker.check(theAST); // 2nd pass
			if (showingAST) {
				drawer.draw(theAST);
			}
			if (folding) {
				theAST.visit(new ConstantFolder());
			}

            if(showTreeAfter) {
                drawer.draw(theAST);
            }

            if (Compiler.showStats) {
                System.out.println("Collecting program statistics ...");
                StatisticsSummary stats = new StatisticsSummary();
                theAST.visit(stats, null);
                System.out.println("Integer expressions: " + stats.getIntegerCount());
                System.out.println("Character expressions: " + stats.getCharacterCount());
            }
			
			if (reporter.getNumErrors() == 0) {
				System.out.println("Code Generation ...");
				encoder.encodeRun(theAST, showingTable); // 3rd pass
			}
		}

		boolean successful = (reporter.getNumErrors() == 0);
		if (successful) {
			emitter.saveObjectProgram(objectName);
			System.out.println("Compilation was successful.");
		} else {
			System.out.println("Compilation was unsuccessful.");
		}
		return successful;
	}

	/**
	 * Triangle compiler main program.
	 *
	 * @param args the only command-line argument to the program specifies the
	 *             source filename.
	 */
	public static void main(String[] args) {

        parseArgs(args);

        var compiledOK = compileProgram(sourceName, objectName, showTree, false, showStats);

        if (!showTree) {
            System.exit(compiledOK ? 0 : 1);
        }

    }

    private static void parseArgs(String[] args) {
        Options options = new Options();

        // Define options: short name, long name, hasArg, description
        options.addOption("o", "output", true, "output object file name");
        options.addOption("t", "tree", false, "show abstract syntax tree");
        options.addOption("f", "folding", false, "perform constant folding");
        options.addOption("a", "treeAfter", false, "show abstract tree after folding");
        options.addOption("s","showStats", false, "show stats");

        CommandLineParser parser = new DefaultParser();
        try {
            CommandLine cmd = parser.parse(options, args);

            if (cmd.getArgList().isEmpty()) {
                System.out.println("Usage: tc filename [-o outputfile] [-t|--tree] [-f|--folding] [-a|--treeAfter]");
                System.exit(1);
            }

            sourceName = cmd.getArgList().get(0);

            if (cmd.hasOption("o")) {
                objectName = cmd.getOptionValue("o");
            }
            showTree = cmd.hasOption("t");
            folding = cmd.hasOption("f");
            showTreeAfter = cmd.hasOption("a");
            showStats = cmd.hasOption("s");

        } catch (ParseException e) {
            System.out.println("Error parsing command-line arguments: " + e.getMessage());
            System.exit(1);
        }
    }

}
