package model.values;

import model.types.IType;
import model.types.RefType;

public class RefValue implements IValue{
    int address;
    IType locationType;

    public RefValue(int address, IType locationType){
        this.address = address;
        this.locationType = locationType;
    }

    public int getAddress(){
        return address;
    }

    @Override
    public IType getType() {
        return new RefType(locationType);
    }

    @Override
    public IValue deepcopy() {
        return new RefValue(address, locationType.deepcopy());
    }

    @Override
    public String toString() {
        return address + ", " + locationType;
    }
}
