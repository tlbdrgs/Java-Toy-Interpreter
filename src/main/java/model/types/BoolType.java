package model.types;

import model.values.BoolValue;
import model.values.IValue;

public class BoolType implements IType {

    public boolean equals(Object another) {

        return another instanceof BoolType;
    }

    @Override
    public IValue getDefaultValue() {
        return new BoolValue(false);
    }

    public String toString() {

        return "bool";
    }

    @Override
    public IType deepcopy() {
        return new BoolType();
    }
}

