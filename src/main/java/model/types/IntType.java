package model.types;

import model.values.IValue;
import model.values.IntValue;

public class IntType implements IType {
    public boolean equals(Object another) {
        return another instanceof IntType;
    }

    @Override
    public IValue getDefaultValue() {
        return new IntValue(0);
    }

    public String toString() {
        return "int";
    }

    @Override
    public IType deepcopy() {
        return new IntType();
    }
}