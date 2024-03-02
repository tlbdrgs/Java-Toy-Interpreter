package model.statements;

import exceptions.MyException;
import model.ProgramState;
import model.adt.MyIDictionary;
import model.adt.MyIHeap;
import model.adt.MyIStack;
import model.expressions.IExp;
import model.types.BoolType;
import model.types.IType;
import model.values.BoolValue;
import model.values.IValue;

public class WhileStmt implements IStmt{

    IExp condition;
    IStmt body;

    public WhileStmt(IExp condition, IStmt body){
        this.condition = condition;
        this.body = body;
    }

    @Override
    public String toString() {
        return "while (" + this.condition.toString() + ") { " + this.body.toString() + " }";
    }

    @Override
    public IStmt deepcopy() {
        return new WhileStmt(condition.deepcopy(), body.deepcopy());
    }

    @Override
    public ProgramState execute(ProgramState state) throws MyException {

        MyIStack<IStmt> exeStack = state.getExeStack();
        MyIDictionary<String, IValue> symTable = state.getSymTable();
        MyIHeap<Integer, IValue> heap = state.getHeap();

        IValue value = condition.evaluation(symTable, heap);

        if (!value.getType().equals(new BoolType())) {
            throw new MyException("The condition expression is not a boolean!");
        }

        BoolValue boolValue = (BoolValue) value;

        if(boolValue.getVal()){
            exeStack.push(this);
            exeStack.push(body);
        }

        return null;
    }

    @Override
    public MyIDictionary<String, IType> typecheck(MyIDictionary<String, IType> typeEnv) throws MyException {
        IType typeExp = condition.typecheck(typeEnv);
        if (typeExp.equals(new BoolType())) {
            body.typecheck(typeEnv.copy());
            return typeEnv;
        }
        throw new MyException("The condition of WHILE has not the type bool");
    }
}
