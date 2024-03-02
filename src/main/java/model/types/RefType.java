package model.types;

import model.values.IValue;
import model.values.RefValue;

public class RefType implements  IType{
    IType inner;

    public RefType(IType inner){
        this.inner = inner;
    }

    public IType getInner(){
        return inner;
    }

    public boolean equals(Object obj) {
        if(obj instanceof RefType)
            return inner.equals(((RefType) obj).getInner());
        else
            return false;
    }

    @Override
    public String toString() {
        return "Ref(" + inner.toString()+ ")";
    }

    @Override
    public IType deepcopy() {
        return new RefType(inner.deepcopy());
    }

    @Override
    public IValue getDefaultValue() {
        return new RefValue(0, inner);
    }
}
