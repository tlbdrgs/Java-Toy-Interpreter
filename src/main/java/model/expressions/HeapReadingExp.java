package model.expressions;

import exceptions.MyException;
import model.adt.MyIDictionary;
import model.adt.MyIHeap;
import model.types.IType;
import model.types.RefType;
import model.values.IValue;
import model.values.RefValue;

public class HeapReadingExp implements IExp{

    IExp expression;

    public HeapReadingExp(IExp expression){
        this.expression = expression;
    }

    @Override
    public IExp deepcopy() {
        return new HeapReadingExp(expression.deepcopy());
    }


    @Override
    public String toString() {
        return "rH(" + expression + ")";
    }

    @Override
    public IValue evaluation(MyIDictionary<String, IValue> table, MyIHeap<Integer, IValue> heap) throws MyException {
        IValue expressionValue = expression.evaluation(table, heap);

        if (!(expressionValue.getType() instanceof RefType)) {
            throw new MyException("Expression not of type Reference");
        }

        RefValue referenceValue = (RefValue) expressionValue;

        if(!heap.isDefined(referenceValue.getAddress())) {
            throw new MyException("Address not in heap");
        }

        return heap.lookup(referenceValue.getAddress());
    }

    @Override
    public IType typecheck(MyIDictionary<String, IType> typeEnv) throws MyException {
        IType type = expression.typecheck(typeEnv);
        if (type instanceof RefType refType) {
            return refType.getInner();
        }
        throw new MyException("the rH argument is not a Ref Type");
    }
}
