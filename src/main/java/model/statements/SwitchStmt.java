package model.statements;

import exceptions.MyException;
import model.ProgramState;
import model.adt.MyIDictionary;
import model.expressions.IExp;
import model.expressions.RelationalExpression;
import model.types.IType;
import model.values.IValue;

public class SwitchStmt implements IStmt{

    IExp givenExp, exp1, exp2;
    IStmt stmt1, stmt2, defaultStmt;

    public SwitchStmt(IExp givenExp, IExp exp1, IStmt stmt1, IExp exp2, IStmt stmt2, IStmt defaultStmt){
        this.givenExp = givenExp;
        this.exp1 = exp1;
        this.stmt1 = stmt1;
        this.exp2 = exp2;
        this.stmt2 = stmt2;
        this.defaultStmt = defaultStmt;
    }

    @Override
    public ProgramState execute(ProgramState state) throws MyException {
        IStmt newStmt = new IfStmt(new RelationalExpression(givenExp, exp1, "=="), stmt1, new IfStmt(new RelationalExpression(givenExp, exp2, "=="), stmt2, defaultStmt));
        state.getExeStack().push(newStmt);
        return null;
    }

    @Override
    public MyIDictionary<String, IType> typecheck(MyIDictionary<String, IType> typeEnv) throws MyException {
        IType type1 = givenExp.typecheck(typeEnv);
        IType type2 = exp1.typecheck(typeEnv);
        IType type3 = exp2.typecheck(typeEnv);

        if(type1.equals(type2) && type1.equals(type3)){
            stmt1.typecheck(typeEnv.copy());
            stmt2.typecheck(typeEnv.copy());
            defaultStmt.typecheck(typeEnv.copy());
            return typeEnv;
        }

        throw new MyException("SwitchStmt: expressions don't have the same type!");

    }

    @Override
    public IStmt deepcopy() {
        return new SwitchStmt(givenExp.deepcopy(), exp1.deepcopy(), stmt1.deepcopy(), exp2.deepcopy(), stmt2.deepcopy(), defaultStmt.deepcopy());
    }

    @Override
    public String toString() {
        return "switch(" + givenExp + ") (case (" + exp1 + ") : {" + stmt1 + "}) (case (" + exp2 + ") : {" + stmt2 + "}) (default : {" + defaultStmt + "})";
    }
}
