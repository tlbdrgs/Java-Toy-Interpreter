package model.statements;

import exceptions.MyException;
import model.ProgramState;
import model.adt.MyIDictionary;
import model.adt.MyIHeap;
import model.adt.MyIList;
import model.expressions.IExp;
import model.types.IType;
import model.values.IValue;

public class PrintStmt implements IStmt{

    IExp expression;
    public PrintStmt(IExp expression){
        this.expression = expression;
    }

    @Override
    public String toString() {
        return "print(" + expression + ");";
    }

    @Override
    public ProgramState execute(ProgramState state) throws MyException {
        MyIList<IValue> outputList = state.getOutputList();
        MyIDictionary<String, IValue> symTable = state.getSymTable();
        MyIHeap<Integer, IValue> heap = state.getHeap();
        outputList.add(expression.evaluation(symTable, heap));
        return null;
    }

    @Override
    public MyIDictionary<String, IType> typecheck(MyIDictionary<String, IType> typeEnv) throws MyException {
        expression.typecheck(typeEnv);
        return typeEnv;
    }

    @Override
    public IStmt deepcopy() {
        return new PrintStmt(expression.deepcopy());
    }
}
