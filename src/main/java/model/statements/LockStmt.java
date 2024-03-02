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

public class LockStmt implements IStmt{

    String varName;

    static final Lock lock = new ReentrantLock();

    public LockStmt(String varName){
        this.varName = varName;
    }


    public ProgramState execute(ProgramState state) throws MyException {
        lock.lock();

        MyIDictionary<String, IValue> symTable = state.getSymTable();
        MyILockTable lockTable = state.getLockTable();

        if(!symTable.isDefined(varName))
            throw new MyException("LockStmt: given variable isn't declared!");

        IValue value = symTable.lookup(varName);

        if(!value.getType().equals(new IntType()))
            throw new MyException("LockStmt: variable isn't of type int!");

        int foundIndex = ((IntValue) value).getVal();
        if(!lockTable.isDefined(foundIndex))
            throw new MyException("LockStmt: value isn't in the lock table!");

        if(lockTable.get(foundIndex) == -1)
            lockTable.update(foundIndex, state.getId());
        else
            state.getExeStack().push(this);

        lock.unlock();

        return null;
    }

    @Override
    public MyIDictionary<String, IType> typecheck(MyIDictionary<String, IType> typeEnv) throws MyException {
        if(typeEnv.lookup(varName).equals(new IntType()))
            return typeEnv;
        throw new MyException("LockStatement: given variable isn't of type int!");
    }

    @Override
    public IStmt deepcopy() {
        return new LockStmt(varName);
    }

    @Override
    public String toString() {
        return "lock(" + varName + ")";
    }
}
