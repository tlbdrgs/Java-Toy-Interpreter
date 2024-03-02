package repository;

import exceptions.MyException;
import model.ProgramState;

import java.util.List;

public interface IRepository {


    List<ProgramState> getCurrentProgramStatesList();
    void setCurrentProgramStatesList(List<ProgramState> newProgramStatesList);
    void addProgramState(ProgramState ps);

    void logProgramStateExecution(ProgramState ps);
}
