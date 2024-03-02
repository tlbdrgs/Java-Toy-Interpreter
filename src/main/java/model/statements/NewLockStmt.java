package model.statements;

import exceptions.MyException;
import model.ProgramState;
import model.adt.MyIDictionary;
import model.adt.MyILockTable;
import model.types.IType;
import model.types.IntType;
import model.values.IValue;
import model.values.IntValue;

import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

public class NewLockStmt implements IStmt{

    String varName;

    static final Lock lock = new ReentrantLock();

    public NewLockStmt(String varName){
        this.varName = varName;
    }

    @Override
    public ProgramState execute(ProgramState state) throws MyException {
        lock.lock();

        MyIDictionary<String, IValue> symTable = state.getSymTable();
        MyILockTable lockTable = state.getLockTable();

        int freeLocation = lockTable.getFreeLocation();
        lockTable.put(freeLocation, -1);

        if(symTable.isDefined(varName) && symTable.lookup(varName).getType().equals(new IntType()))
            symTable.update(varName, new IntValue(freeLocation));
        else
            throw new MyException("NewLockStmt: given variable isn't declared or doesn't have the  correct type!");

        lock.unlock();

        return null;
    }

    @Override
    public MyIDictionary<String, IType> typecheck(MyIDictionary<String, IType> typeEnv) throws MyException {
        if(typeEnv.lookup(varName).equals(new IntType()))
            return  typeEnv;
        throw new MyException("NewLockStmt: given variable isn't of type int!");
    }

    @Override
    public IStmt deepcopy() {
        return new NewLockStmt(varName);
    }

    @Override
    public String toString() {
        return "newLock(" + varName + ")";
    }
}
