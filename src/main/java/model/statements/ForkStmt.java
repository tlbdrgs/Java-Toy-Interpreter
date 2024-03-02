package model.statements;

import exceptions.MyException;
import model.ProgramState;
import model.adt.*;
import model.types.IType;
import model.values.IValue;
import model.values.StringValue;

import java.io.BufferedReader;

public class ForkStmt implements IStmt{
    IStmt statement;

    public ForkStmt(IStmt statement){
        this.statement = statement;
    }

    @Override
    public String toString()
    {
        return "fork{" + this.statement.toString() + "};";
    }

    @Override
    public ProgramState execute(ProgramState state) throws MyException {

        MyIHeap<Integer, IValue> heap = state.getHeap();
        MyIDictionary<String, IValue> symTable = state.getSymTable().copy();
        MyIList<IValue> outputList = state.getOutputList();
        MyIDictionary<StringValue, BufferedReader> fileTable = state.getFileTable();
        MyILockTable lockTable = state.getLockTable();

        return new ProgramState(new MyStack<>(), symTable, outputList, fileTable, heap, lockTable, this.statement);
    }

    @Override
    public MyIDictionary<String, IType> typecheck(MyIDictionary<String, IType> typeEnv) throws MyException {
         statement.typecheck(typeEnv.copy());
         return typeEnv;
    }

    @Override
    public IStmt deepcopy() {
        return new ForkStmt(statement.deepcopy());
    }
}
