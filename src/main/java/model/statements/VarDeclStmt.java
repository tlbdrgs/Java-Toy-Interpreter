package model.statements;

import exceptions.MyException;
import model.ProgramState;
import model.adt.MyIDictionary;
import model.types.IType;
import model.values.IValue;

public class VarDeclStmt implements IStmt{
    String name;
    IType type;

    public VarDeclStmt(String name, IType type)
    {
        this.name = name;
        this.type = type;
    }

    @Override
    public String toString() {
        return type + " " + name + ";";
    }

    @Override
    public ProgramState execute(ProgramState state) throws MyException {
        MyIDictionary<String, IValue> symTable = state.getSymTable();

        if(symTable.isDefined(name)){
            throw new MyException("Variable already declared");
        }
        else{
            symTable.update(name, type.getDefaultValue());
        }

        return null;
    }

    @Override
    public MyIDictionary<String, IType> typecheck(MyIDictionary<String, IType> typeEnv) throws MyException {
        typeEnv.update(name, type);
        return typeEnv;
    }

    @Override
    public IStmt deepcopy() {
        return new VarDeclStmt(name, type.deepcopy());
    }
}
