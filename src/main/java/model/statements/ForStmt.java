package model.statements;

import exceptions.MyException;
import model.ProgramState;
import model.adt.MyIDictionary;
import model.adt.MyIStack;
import model.expressions.IExp;
import model.expressions.RelationalExpression;
import model.expressions.VarExp;
import model.types.IType;
import model.types.IntType;
import model.types.StringType;

public class ForStmt implements IStmt{

    String varName;

    IExp exp1, exp2, exp3;

    IStmt givenStmt;

    public ForStmt(String varName, IExp exp1, IExp exp2, IExp exp3, IStmt givenStmt){
        this.varName = varName;
        this.exp1 = exp1;
        this.exp2 = exp2;
        this.exp3 = exp3;
        this.givenStmt = givenStmt;
    }

    public ProgramState execute(ProgramState state) throws MyException {


        IStmt newStmt = new CompStmt(new VarDeclStmt(varName, new IntType()),
                new CompStmt(new AssignStmt(varName, exp1),
                        new WhileStmt(new RelationalExpression(new VarExp(varName), exp2, "<"),
                                new CompStmt(givenStmt, new AssignStmt(varName, exp3)))
                        ));
        state.getExeStack().push(newStmt);
        return null;
    }


    public MyIDictionary<String, IType> typecheck(MyIDictionary<String, IType> typeEnv) throws MyException {

        typeEnv.update(varName, new IntType());

        IType type1 = exp1.typecheck(typeEnv);
        IType type2 = exp2.typecheck(typeEnv);
        IType type3 = exp3.typecheck(typeEnv);

        if(type1.equals(new IntType()) && type2.equals(new IntType()) && type3.equals(new IntType()))
            return typeEnv;
        throw new MyException("ForStmt: The expression isn't of int type!");
    }

    public IStmt deepcopy() {
        return new ForStmt(varName, exp1.deepcopy(), exp2.deepcopy(), exp3.deepcopy(), givenStmt.deepcopy());
    }


    public String toString() {
        return "for(" + varName + " = " + exp1 + "; " + varName + " < " + exp2 + "; " + varName + " = " + exp3 + ") {" + givenStmt + "}";
    }
}
