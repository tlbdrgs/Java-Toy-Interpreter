package model.expressions;

import exceptions.MyException;
import model.adt.MyIDictionary;
import model.adt.MyIHeap;
import model.types.IType;
import model.values.IValue;

public class VarExp implements IExp{
    String id;

    public VarExp(String id){
        this.id = id;
    }

    @Override
    public String toString() {
        return this.id;
    }

    @Override
    public IValue evaluation(MyIDictionary<String, IValue> table, MyIHeap<Integer, IValue> heap) throws MyException {
        if(table.isDefined(id))
            return table.lookup(id);
        throw new MyException("Variable " + id + " isn't defined!");
    }

    @Override
    public IExp deepcopy() {
        return new VarExp(id);
    }

    @Override
    public IType typecheck(MyIDictionary<String, IType> typeEnv) throws MyException {
        return typeEnv.lookup(id);
    }
}
