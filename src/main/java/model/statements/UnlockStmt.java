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

public class UnlockStmt implements IStmt{

    String varName;

    static final Lock lock = new ReentrantLock();

    public UnlockStmt(String varName){
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

        if(lockTable.get(foundIndex) == state.getId())
            lockTable.update(foundIndex, -1);

        lock.unlock();

        return null;
    }


    public MyIDictionary<String, IType> typecheck(MyIDictionary<String, IType> typeEnv) throws MyException {
        if(typeEnv.lookup(varName).equals(new IntType()))
            return typeEnv;
        throw new MyException("UnlockStatement: given variable isn't of type int!");
    }


    public IStmt deepcopy() {
        return new UnlockStmt(varName);
    }


    public String toString() {
        return "unlock(" + varName + ")";
    }
}
