package model.statements;

import exceptions.MyException;
import model.ProgramState;
import model.adt.MyIDictionary;
import model.adt.MyIHeap;
import model.expressions.IExp;
import model.expressions.ValueExp;
import model.types.IType;
import model.values.IValue;
import model.values.IntValue;

public class AssignStmt implements IStmt{
    String id;
    IExp expression;
    public AssignStmt(String id, IExp expression){
        this.id = id;
        this.expression = expression;
    }

    @Override
    public String toString() {
        return id + "=" + expression.toString();
    }

    @Override
    public ProgramState execute(ProgramState state) throws MyException {
        MyIDictionary<String, IValue> symTable = state.getSymTable();
        MyIHeap<Integer, IValue> heap = state.getHeap();

        if(symTable.isDefined(id))
        {
            IValue value = expression.evaluation(symTable, heap);
            IType typeId = (symTable.lookup(id)).getType();

            if(value.getType().equals(typeId))
                symTable.update(id, value);
            else throw new MyException("declared type of variable " +id+" and type of the assigned expression do not match");
        }
        else throw new MyException("the used variable " +id + " was not declared before");

        return null;
    }

    @Override
    public MyIDictionary<String, IType> typecheck(MyIDictionary<String, IType> typeEnv) throws MyException {
        IType typeVar = typeEnv.lookup(id);
        IType typeExp = expression.typecheck(typeEnv);
        if(typeVar.equals(typeExp))
            return typeEnv;
        throw new MyException("Assignment: right hand side and left hand side have different types ");
    }

    @Override
    public IStmt deepcopy() {
        return new AssignStmt(id, expression.deepcopy());
    }
}
