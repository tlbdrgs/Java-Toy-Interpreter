package model.statements;

import exceptions.MyException;
import model.ProgramState;
import model.adt.MyIDictionary;
import model.adt.MyIHeap;
import model.expressions.IExp;
import model.types.IType;
import model.types.StringType;
import model.values.IValue;
import model.values.StringValue;

import java.io.BufferedReader;

public class CloseReadFileStmt implements IStmt {
    IExp expression;

    public CloseReadFileStmt(IExp expression) {
        this.expression = expression;
    }

    @Override
    public String toString() {
        return "closeReadFile(" + this.expression.toString() + ");";
    }

    @Override
    public IStmt deepcopy() {
        return new CloseReadFileStmt(expression.deepcopy());
    }

    @Override
    public ProgramState execute(ProgramState state) throws MyException {
        MyIDictionary<String, IValue> symTable = state.getSymTable();
        MyIHeap<Integer, IValue> heap = state.getHeap();

        IValue value = expression.evaluation(symTable, heap);

        if (!value.getType().equals(new StringType()))
            throw new MyException("The expression isn't of string type!");

        StringValue fileName = (StringValue) value;
        MyIDictionary<StringValue, BufferedReader> fileTable = state.getFileTable();

        BufferedReader bufferedReader = fileTable.lookup(fileName);

        if (bufferedReader == null) {
            throw new MyException("File with given name not found!");
        }

        try {
            bufferedReader.close();
        } catch (Exception e) {
            throw new MyException(e.toString());
        }

        fileTable.remove(fileName);

        return null;
    }

    @Override
    public MyIDictionary<String, IType> typecheck(MyIDictionary<String, IType> typeEnv) throws MyException {
        IType typeExp = expression.typecheck(typeEnv);

        if (!(typeExp instanceof StringType)) {
            throw new MyException("CloseReadFileStmt: The expression isn't of string type!");
        }

        return typeEnv;
    }
}
