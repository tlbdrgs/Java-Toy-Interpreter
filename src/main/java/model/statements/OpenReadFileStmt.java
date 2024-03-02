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
import java.io.FileReader;

public class OpenReadFileStmt implements IStmt{

    IExp expression;

    public OpenReadFileStmt(IExp expression){
        this.expression = expression;
    }

    @Override
    public String toString() {
        return "openReadFile(" + this.expression.toString() + ");";
    }

    @Override
    public IStmt deepcopy() {
        return new OpenReadFileStmt(expression.deepcopy());
    }

    @Override
    public ProgramState execute(ProgramState state) throws MyException {
        MyIDictionary<String, IValue> symTable = state.getSymTable();
        MyIDictionary<StringValue, BufferedReader> fileTable = state.getFileTable();
        MyIHeap<Integer, IValue> heap = state.getHeap();

        IValue value = expression.evaluation(symTable, heap);

        if(!value.getType().equals(new StringType()))
            throw new MyException("The expression isn't of string type!");

        StringValue fileName = (StringValue) value;

        if(fileTable.isDefined(fileName))
            throw new MyException("File already open!");

        BufferedReader bufferedReader;

        try{
            bufferedReader = new BufferedReader(new FileReader(fileName.getVal()));
        }catch (Exception e){
            throw new MyException(e.toString());
        }

        fileTable.update(fileName, bufferedReader);

        return null;
    }

    @Override
    public MyIDictionary<String, IType> typecheck(MyIDictionary<String, IType> typeEnv) throws MyException {
        IType typeExp = expression.typecheck(typeEnv);

        if (!(typeExp instanceof StringType)) {
            throw new MyException("OpenReadFileStmt: The expression isn't of string type!");
        }

        return typeEnv;
    }
}
