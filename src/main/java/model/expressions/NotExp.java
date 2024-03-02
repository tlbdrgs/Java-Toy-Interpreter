package model.expressions;

import exceptions.MyException;
import model.adt.MyIDictionary;
import model.adt.MyIHeap;
import model.types.IType;
import model.values.BoolValue;
import model.values.IValue;

public class NotExp implements IExp{

    IExp exp;

    public NotExp(IExp exp){
        this.exp = exp;
    }

    @Override
    public IValue evaluation(MyIDictionary<String, IValue> table, MyIHeap<Integer, IValue> heap) throws MyException {
        BoolValue value = (BoolValue) exp.evaluation(table, heap);
        if (value.getVal())
            return new BoolValue(false);
        else
            return new BoolValue(true);
    }

    @Override
    public IType typecheck(MyIDictionary<String, IType> typeEnv) throws MyException {
        return exp.typecheck(typeEnv);
    }

    @Override
    public IExp deepcopy() {
        return new NotExp(exp.deepcopy());
    }

    @Override
    public String toString() {
        return "!(" + exp + ")";
    }
}
