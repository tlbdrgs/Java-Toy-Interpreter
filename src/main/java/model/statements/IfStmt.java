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

public class IfStmt implements IStmt {

    IExp expression;
    IStmt thenS, elseS;

    public IfStmt(IExp expression, IStmt thenS, IStmt elseS) {
        this.expression = expression;
        this.thenS = thenS;
        this.elseS = elseS;
    }

    public String toString() {
        return "(IF(" + expression.toString() + ") THEN(" + thenS.toString() + ")ELSE(" + elseS.toString() + "))";
    }

    @Override
    public ProgramState execute(ProgramState state) throws MyException {
        MyIStack<IStmt> stack = state.getExeStack();
        MyIDictionary<String, IValue> symTable = state.getSymTable();
        MyIHeap<Integer, IValue> heap = state.getHeap();


        if (!expression.evaluation(symTable, heap).getType().equals(new BoolType())) {
            throw new MyException("Expression is not a boolean!");
        } else if (((BoolValue) expression.evaluation(symTable, heap)).getVal()) {
            stack.push(thenS);
        } else stack.push(elseS);

        return null;
    }

    @Override
    public MyIDictionary<String, IType> typecheck(MyIDictionary<String, IType> typeEnv) throws MyException {
        IType typeExp = expression.typecheck(typeEnv);
        if (typeExp.equals(new BoolType())) {
            thenS.typecheck(typeEnv.copy());
            elseS.typecheck(typeEnv.copy());
            return typeEnv;
        }
        throw new MyException("The condition of IF has not the type bool");
    }

    @Override
    public IStmt deepcopy() {
        return new IfStmt(expression.deepcopy(), thenS.deepcopy(), elseS.deepcopy());
    }
}
