package model.expressions;

import exceptions.MyException;
import model.adt.MyIDictionary;
import model.adt.MyIHeap;
import model.types.IType;
import model.values.IValue;

public interface IExp {
    IValue evaluation(MyIDictionary<String, IValue> table, MyIHeap<Integer, IValue> heap) throws MyException;

    IType typecheck(MyIDictionary<String,IType> typeEnv) throws MyException;

    IExp deepcopy();
}
