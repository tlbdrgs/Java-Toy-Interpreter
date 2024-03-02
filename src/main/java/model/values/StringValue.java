package model.values;

import model.types.IType;
import model.types.StringType;

public class StringValue implements IValue{

    String value;

    public StringValue(String value){
        this.value = value;
    }

    public String getVal(){
        return value;
    }

    @Override
    public String toString() {
        return value;
    }

    @Override
    public IValue deepcopy() {
        return new StringValue(value);
    }

    @Override
    public IType getType() {
        return new StringType();
    }
}
