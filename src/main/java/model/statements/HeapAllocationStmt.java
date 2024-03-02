package model.statements;

import exceptions.MyException;
import model.ProgramState;
import model.adt.MyIDictionary;
import model.adt.MyIHeap;
import model.expressions.IExp;
import model.types.IType;
import model.types.RefType;
import model.values.IValue;
import model.values.RefValue;

public class HeapAllocationStmt implements IStmt{
    String variableName;
    IExp expression;

    public HeapAllocationStmt(String variableName, IExp expression){
        this.variableName = variableName;
        this.expression = expression;
    }

    @Override
    public IStmt deepcopy() {
        return new HeapAllocationStmt(variableName, expression.deepcopy());
    }

    @Override
    public String toString() {
        return "new(" + variableName + ", " + expression + ");";
    }

    @Override
    public ProgramState execute(ProgramState state) throws MyException {

        MyIDictionary<String, IValue> symTable = state.getSymTable();
        MyIHeap<Integer, IValue> heap = state.getHeap();

        if(!symTable.isDefined(variableName))
            throw new MyException("Variable isn't defined!");

        IValue varValue = symTable.lookup(variableName);

        if(!(varValue.getType() instanceof RefType))
            throw new MyException("Variable is not of type reference!");


        IValue expressionValue = expression.evaluation(symTable, heap);

        if (!expressionValue.getType().equals(((RefType) varValue.getType()).getInner())) {
            throw new MyException("Types not equal!");
        }

        int address = heap.getFreeAddress();
        heap.allocate(expressionValue);
        symTable.update(variableName, new RefValue(address, expressionValue.getType()));

        return null;
    }

    @Override
    public MyIDictionary<String, IType> typecheck(MyIDictionary<String, IType> typeEnv) throws MyException {
        IType typeVar = typeEnv.lookup(variableName);
        IType typeExp = expression.typecheck(typeEnv);
        if (typeVar.equals(new RefType(typeExp)))
            return typeEnv;
        throw new MyException("NEW stmt: right hand side and left hand side have different types ");
    }

}
