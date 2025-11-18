package triangle.abstractSyntaxTrees.commands;

import triangle.abstractSyntaxTrees.expressions.Expression;
import triangle.abstractSyntaxTrees.visitors.CommandVisitor;
import triangle.syntacticAnalyzer.SourcePosition;

public class LoopWhileCommand extends Command {

    public LoopWhileCommand(Command initialCommand, Expression condition, Command repeatingCommand,
                            SourcePosition position) {
        super(position);
        C1 = initialCommand;
        E = condition;
        C2 = repeatingCommand;
    }

    public <TArg, TResult> TResult visit(CommandVisitor<TArg, TResult> v, TArg arg) {
        return v.visitLoopWhileCommand(this, arg);
    }

    public final Command C1;
    public Expression E;
    public final Command C2;
}

