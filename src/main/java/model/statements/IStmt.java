package model.statements;

import exceptions.MyException;
import model.ProgramState;
import model.adt.MyIDictionary;
import model.types.IType;

public interface IStmt {
    ProgramState execute(ProgramState state) throws MyException;

    MyIDictionary<String, IType> typecheck(MyIDictionary<String,IType> typeEnv) throws MyException;
    IStmt deepcopy();
}
