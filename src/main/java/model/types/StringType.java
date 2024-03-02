package model.types;

import model.values.IValue;
import model.values.StringValue;

public class StringType implements IType{

    @Override
    public boolean equals(Object obj) {
        return  obj instanceof StringType;
    }

    @Override
    public IType deepcopy() {
        return new StringType();
    }

    @Override
    public String toString() {
        return "string";
    }

    @Override
    public IValue getDefaultValue() {
        return new StringValue("");
    }
}
