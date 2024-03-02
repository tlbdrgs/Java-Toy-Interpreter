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

public class HeapWritingStmt implements IStmt{

    String variableName;
    IExp expression;

    public HeapWritingStmt(String variableName, IExp expression){
        this.variableName = variableName;
        this.expression = expression;
    }

    @Override
    public IStmt deepcopy() {
        return new HeapAllocationStmt(variableName, expression.deepcopy());
    }

    @Override
    public String toString() {
        return "wH(" + variableName + ", " + expression + ");";
    }

    @Override
    public ProgramState execute(ProgramState state) throws MyException {

        MyIDictionary<String, IValue> symTable = state.getSymTable();

        if(!symTable.isDefined(variableName))
            throw new MyException("Variable isn't defined!");

        IValue varValue = symTable.lookup(variableName);

        if(!(varValue.getType() instanceof RefType))
            throw new MyException("Variable is not of type reference!");

        MyIHeap<Integer, IValue> heap = state.getHeap();

        if(!(heap.isDefined(((RefValue)varValue).getAddress())))
            throw new MyException("Address not in heap!");

        IValue expressionValue = expression.evaluation(symTable, heap);

        if (!expressionValue.getType().equals(((RefType) varValue.getType()).getInner())) {
            throw new MyException("Types not equal!");
        }

        heap.update(((RefValue)varValue).getAddress(), expressionValue);

        return null;
    }

    @Override
    public MyIDictionary<String, IType> typecheck(MyIDictionary<String, IType> typeEnv) throws MyException {
        IType typeVar = typeEnv.lookup(variableName);
        IType typeExp = expression.typecheck(typeEnv);

        if (typeExp.equals(((RefType)typeVar).getInner()))
            return typeEnv;
        throw new MyException("WriteHeap: right hand side and left hand side have different types");
    }

}
