package repository;

import exceptions.MyException;
import model.ProgramState;
import model.adt.MyIDictionary;
import model.adt.MyIList;
import model.adt.MyIStack;
import model.adt.MyStack;
import model.statements.IStmt;
import model.values.IValue;
import model.values.StringValue;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

public class Repository implements IRepository {

    List<ProgramState> programStates = new ArrayList<>();
    String filePath;

    public Repository(ProgramState ps, String filePath) {
        programStates.add(ps);
        this.filePath = filePath;
    }

    @Override
    public void addProgramState(ProgramState ps) {
        programStates.add(ps);
    }

    @Override
    public List<ProgramState> getCurrentProgramStatesList() {
        return programStates;
    }

    @Override
    public void setCurrentProgramStatesList(List<ProgramState> newProgramStatesList) {
        this.programStates = newProgramStatesList;
    }

    @Override
    public void logProgramStateExecution(ProgramState ps){
        try (PrintWriter logFile = new PrintWriter(new BufferedWriter(new FileWriter(this.filePath, true)))) {
            logFile.write(ps.toString());
            logFile.flush();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

}

