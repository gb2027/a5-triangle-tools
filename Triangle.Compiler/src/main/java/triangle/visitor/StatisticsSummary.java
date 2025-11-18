package triangle.visitor;

import triangle.StdEnvironment;
import triangle.abstractSyntaxTrees.Program;
import triangle.abstractSyntaxTrees.actuals.*;
import triangle.abstractSyntaxTrees.aggregates.MultipleArrayAggregate;
import triangle.abstractSyntaxTrees.aggregates.MultipleRecordAggregate;
import triangle.abstractSyntaxTrees.aggregates.SingleArrayAggregate;
import triangle.abstractSyntaxTrees.aggregates.SingleRecordAggregate;
import triangle.abstractSyntaxTrees.commands.*;
import triangle.abstractSyntaxTrees.declarations.*;
import triangle.abstractSyntaxTrees.expressions.*;
import triangle.abstractSyntaxTrees.formals.*;
import triangle.abstractSyntaxTrees.terminals.CharacterLiteral;
import triangle.abstractSyntaxTrees.terminals.Identifier;
import triangle.abstractSyntaxTrees.terminals.IntegerLiteral;
import triangle.abstractSyntaxTrees.terminals.Operator;
import triangle.abstractSyntaxTrees.types.*;
import triangle.abstractSyntaxTrees.visitors.*;
import triangle.abstractSyntaxTrees.visitors.ActualParameterVisitor;
import triangle.abstractSyntaxTrees.visitors.FormalParameterSequenceVisitor;
import triangle.abstractSyntaxTrees.visitors.FormalParameterVisitor;
import triangle.abstractSyntaxTrees.vnames.DotVname;
import triangle.abstractSyntaxTrees.vnames.SimpleVname;
import triangle.abstractSyntaxTrees.vnames.SubscriptVname;

public class StatisticsSummary implements ProgramVisitor<Void, Void>,
        CommandVisitor<Void, Void>,
        ExpressionVisitor<Void, Void>,
        DeclarationVisitor<Void, Void>,
        TypeDenoterVisitor<Void, Void>,
        ActualParameterVisitor<Void, Void>,
        FormalParameterVisitor<Void, Void>,
        FormalParameterSequenceVisitor<Void, Void>,
        ActualParameterSequenceVisitor<Void, Void>,
        VnameVisitor<Void, Void>,
        ArrayAggregateVisitor<Void, Void>,
        RecordAggregateVisitor<Void, Void>,
        IdentifierVisitor<Void, Void>,
        OperatorVisitor<Void, Void>,
        LiteralVisitor<Void, Void>{

    private int integerCount = 0;
    private int characterCount = 0;

    public int getIntegerCount() { return integerCount; }
    public int getCharacterCount() { return characterCount; }

    @Override
    public Void visitProgram(Program ast, Void arg) {
        ast.C.visit(this,null);
        return null;
    }

    @Override
    public Void visitIntegerExpression(IntegerExpression ast, Void arg) {
        ast.IL.visit(this);
        return null;
    }


    @Override
    public Void visitRecordExpression(RecordExpression ast, Void unused) {
        ast.RA.visit(this);
        return null;
    }

    @Override
    public Void visitCharacterExpression(CharacterExpression ast, Void arg) {
        ast.CL.visit(this);
        return null;
    }

    @Override
    public Void visitConstFormalParameter(ConstFormalParameter ast, Void arg) {
        ast.I.visit(this);
        ast.T.visit(this);
        return null;
    }

    @Override
    public Void visitFuncFormalParameter(FuncFormalParameter ast, Void arg) {
        ast.I.visit(this);
        ast.T.visit(this);
        return null;
    }

    @Override
    public Void visitProcFormalParameter(ProcFormalParameter ast, Void arg) {
        ast.I.visit(this);
        ast.FPS.visit(this);
        return null;
    }

    @Override
    public Void visitVarFormalParameter(VarFormalParameter ast, Void arg) {
        ast.I.visit(this);
        ast.T.visit(this);
        return null;
    }

    @Override
    public Void visitMultipleFieldTypeDenoter(MultipleFieldTypeDenoter ast, Void arg) {
        ast.FT.visit(this);
        ast.I.visit(this);
        ast.T.visit(this);
        return null;
    }

    @Override
    public Void visitSingleFieldTypeDenoter(SingleFieldTypeDenoter ast, Void arg) {
        ast.I.visit(this);
        ast.T.visit(this);
        return null;
    }

    @Override
    public Void visitDotVname(DotVname ast, Void arg) {
        ast.I.visit(this);
        ast.V.visit(this);
        return null;
    }

    @Override
    public Void visitSimpleVname(SimpleVname ast, Void arg) {
        ast.I.visit(this);
        return null;
    }

    @Override
    public Void visitSubscriptVname(SubscriptVname ast, Void arg) {
        if (ast.E != null) {
            ast.E.visit(this);
        }
        ast.V.visit(this);
        return null;
    }

    @Override
    public Void visitAnyTypeDenoter(AnyTypeDenoter ast, Void arg) {
        return null;
    }

    @Override
    public Void visitArrayTypeDenoter(ArrayTypeDenoter ast, Void arg) {
        ast.IL.visit(this);
        ast.T.visit(this);
        return null;
    }

    @Override
    public Void visitBoolTypeDenoter(BoolTypeDenoter ast, Void arg) {
        return null;
    }

    @Override
    public Void visitCharTypeDenoter(CharTypeDenoter ast, Void arg) {
        return null;
    }

    @Override
    public Void visitErrorTypeDenoter(ErrorTypeDenoter ast, Void arg) {
        return null;
    }

    @Override
    public Void visitSimpleTypeDenoter(SimpleTypeDenoter ast, Void arg) {
        ast.I.visit(this);
        return null;
    }

    @Override
    public Void visitIntTypeDenoter(IntTypeDenoter ast, Void arg) {
        return null;
    }

    @Override
    public Void visitRecordTypeDenoter(RecordTypeDenoter ast, Void arg) {
        ast.FT.visit(this);
        return null;
    }

    @Override
    public Void visitMultipleRecordAggregate(MultipleRecordAggregate ast, Void arg) {
        if (ast.E != null) {
            ast.E.visit(this);
        }
        ast.I.visit(this);
        ast.RA.visit(this);
        return null;
    }

    @Override
    public Void visitSingleRecordAggregate(SingleRecordAggregate ast, Void arg) {
        if (ast.E != null) {
            ast.E.visit(this);
        }
        ast.I.visit(this);
        return null;
    }

    @Override
    public Void visitOperator(Operator ast, Void arg) {
        return null;
    }

    @Override
    public Void visitCharacterLiteral(CharacterLiteral ast, Void arg) {
        characterCount++;
        return null;
    }

    @Override
    public Void visitIntegerLiteral(IntegerLiteral ast, Void arg) {
        integerCount++;
        return null;
    }

    @Override
    public Void visitIdentifier(Identifier ast, Void arg) {
        return null;
    }

    @Override
    public Void visitEmptyFormalParameterSequence(EmptyFormalParameterSequence ast, Void arg) {
        return null;
    }

    @Override
    public Void visitMultipleFormalParameterSequence(MultipleFormalParameterSequence ast, Void arg) {
        ast.FP.visit(this);
        ast.FPS.visit(this);
        return null;
    }

    @Override
    public Void visitSingleFormalParameterSequence(SingleFormalParameterSequence ast, Void arg) {
        ast.FP.visit(this);
        return null;
    }

    @Override
    public Void visitArrayExpression(ArrayExpression ast, Void arg) {
        ast.AA.visit(this);
        return null;
    }

    @Override
    public Void visitBinaryExpression(BinaryExpression ast, Void arg) {
        if (ast.E1 != null) ast.E1.visit(this, arg);
        if (ast.E2 != null) ast.E2.visit(this, arg);
        ast.O.visit(this, arg);
        return null;
    }

    @Override
    public Void visitCallExpression(CallExpression ast, Void arg) {
        ast.APS.visit(this);
        ast.I.visit(this);
        return null;
    }


    @Override
    public Void visitEmptyExpression(EmptyExpression ast, Void arg) {
        return null;
    }

    @Override
    public Void visitIfExpression(IfExpression ast, Void arg) {
        if (ast.E1 != null) ast.E1.visit(this, arg);
        if (ast.E2 != null) ast.E2.visit(this, arg);
        if (ast.E3 != null) ast.E3.visit(this, arg);
        return null;
    }


    @Override
    public Void visitLetExpression(LetExpression ast, Void arg) {
        ast.D.visit(this);
        if (ast.E != null) {
            ast.E.visit(this);
        }
        return null;
    }


    @Override
    public Void visitUnaryExpression(UnaryExpression ast, Void arg) {
        ast.E.visit(this);
        if (ast.type == StdEnvironment.integerType) {
            integerCount++;
        } else if (ast.type == StdEnvironment.charType) {
            characterCount++;
        }
        return null;
    }

    @Override
    public Void visitVnameExpression(VnameExpression ast, Void arg) {
        ast.V.visit(this);
        return null;
    }

    @Override
    public Void visitBinaryOperatorDeclaration(BinaryOperatorDeclaration ast, Void arg) {
        ast.ARG1.visit(this);
        ast.ARG2.visit(this);
        ast.O.visit(this);
        ast.RES.visit(this);
        return null;
    }

    @Override
    public Void visitConstDeclaration(ConstDeclaration ast, Void arg) {
        if (ast.E != null) {
            ast.E.visit(this);
        }

        ast.I.visit(this);
        return null;
    }

    @Override
    public Void visitFuncDeclaration(FuncDeclaration ast, Void arg) {
        if (ast.E != null) {
            ast.E.visit(this);
        }
        ast.FPS.visit(this);
        ast.I.visit(this);
        ast.T.visit(this);
        return null;
    }

    @Override
    public Void visitProcDeclaration(ProcDeclaration ast, Void arg) {
        ast.C.visit(this);
        ast.FPS.visit(this);
        ast.I.visit(this);
        return null;
    }

    @Override
    public Void visitSequentialDeclaration(SequentialDeclaration ast, Void arg) {
        ast.D1.visit(this);
        ast.D2.visit(this);
        return null;
    }

    @Override
    public Void visitTypeDeclaration(TypeDeclaration ast, Void arg) {
        ast.I.visit(this);
        ast.T.visit(this);
        return null;
    }

    @Override
    public Void visitUnaryOperatorDeclaration(UnaryOperatorDeclaration ast, Void arg) {
        ast.ARG.visit(this);
        ast.O.visit(this);
        ast.RES.visit(this);
        return null;
    }

    @Override
    public Void visitVarDeclaration(VarDeclaration ast, Void arg) {
        ast.I.visit(this);
        ast.T.visit(this);
        return null;
    }

    @Override
    public Void visitAssignCommand(AssignCommand ast, Void arg) {
        if (ast.E != null) ast.E.visit(this, arg);
        ast.V.visit(this, arg);
        return null;
    }

    @Override
    public Void visitCallCommand(CallCommand ast, Void arg) {
        ast.APS.visit(this);
        ast.I.visit(this);
        return null;
    }

    @Override
    public Void visitEmptyCommand(EmptyCommand ast, Void arg) {
        return null;
    }

    @Override
    public Void visitIfCommand(IfCommand ast, Void arg) {
        if (ast.E != null) {
            ast.E.visit(this);
        }
        ast.C1.visit(this);
        ast.C2.visit(this);
        return null;
    }

    @Override
    public Void visitLetCommand(LetCommand ast, Void arg) {
        ast.C.visit(this);
        ast.D.visit(this);
        return null;
    }

    @Override
    public Void visitSequentialCommand(SequentialCommand ast, Void arg) {
        ast.C1.visit(this);
        ast.C2.visit(this);
        return null;
    }

    @Override
    public Void visitWhileCommand(WhileCommand ast, Void arg) {
        if (ast.E != null) {
            ast.E.visit(this);
        }
        ast.C.visit(this);
        return null;
    }

    @Override
    public Void visitLoopWhileCommand(LoopWhileCommand ast, Void arg) {
        if (ast.E != null) {
            ast.E.visit(this);
        }
        ast.C1.visit(this);
        ast.C2.visit(this);
        return null;
    }


    @Override
    public Void visitMultipleArrayAggregate(MultipleArrayAggregate ast, Void arg) {
        ast.AA.visit(this);
        if (ast.E != null) {
            ast.E.visit(this);
        }
        return null;
    }

    @Override
    public Void visitSingleArrayAggregate(SingleArrayAggregate ast, Void arg) {
        if (ast.E != null) {
            ast.E.visit(this);
        }
        return null;
    }

    @Override
    public Void visitEmptyActualParameterSequence(EmptyActualParameterSequence ast, Void arg) {
        return null;
    }

    @Override
    public Void visitMultipleActualParameterSequence(MultipleActualParameterSequence ast, Void arg) {
        ast.AP.visit(this);
        ast.APS.visit(this);
        return null;
    }

    @Override
    public Void visitSingleActualParameterSequence(SingleActualParameterSequence ast, Void arg) {
        ast.AP.visit(this);
        return null;
    }

    @Override
    public Void visitConstActualParameter(ConstActualParameter ast, Void arg) {
        if (ast.E != null) {
            ast.E.visit(this);
        }
        return null;
    }

    @Override
    public Void visitFuncActualParameter(FuncActualParameter ast, Void arg) {
        ast.I.visit(this);
        return null;
    }

    @Override
    public Void visitProcActualParameter(ProcActualParameter ast, Void arg) {
        ast.I.visit(this);
        return null;
    }

    @Override
    public Void visitVarActualParameter(VarActualParameter ast, Void arg) {
        ast.V.visit(this);
        return null;
    }
}
