package controller;

import exceptions.MyException;
import model.ProgramState;
import model.values.IValue;
import model.values.RefValue;
import repository.IRepository;

import java.util.*;
import java.util.concurrent.*;
import java.util.stream.Collectors;

public class Controller {

    IRepository repository;
    ExecutorService executor;

    public Controller(IRepository repo) {
        this.repository = repo;
    }

    public void addProgramState(ProgramState ps) {
        repository.addProgramState(ps);
    }


    Map<Integer, IValue> garbageCollector(List<Integer> addressesToKeep, Map<Integer, IValue> heap) {
        return heap.entrySet().stream()
                .filter(e -> addressesToKeep.contains(e.getKey()))
                .collect(Collectors.toMap(Map.Entry::getKey, Map.Entry::getValue));
    }

    List<Integer> getAddresses(Collection<IValue> collection) {
        return collection.stream().filter(v -> v instanceof RefValue).map(v -> {
            RefValue v1 = (RefValue) v;
            return v1.getAddress();
        }).collect(Collectors.toList());
    }

    public List<ProgramState> removeCompletedPrograms(List<ProgramState> programStates) {
        return programStates.stream()
                .filter(ProgramState::isNotCompleted)
                .collect(Collectors.toList());
    }


    public void oneStepForAllPrograms(List<ProgramState> programStatesList) throws Exception {

        programStatesList.forEach(program -> repository.logProgramStateExecution(program));

        List<Callable<ProgramState>> callList = programStatesList.stream()
                .map((ProgramState prg) -> (Callable<ProgramState>) (prg::oneStep))
                .toList();

        List<ProgramState> newProgramStatesList = executor.invokeAll(callList).stream()
                .map(future -> {
                    try {
                        return future.get();
                    } catch (Exception e) {
                        try {
                            throw new MyException(e.toString());
                        } catch (MyException ex) {
                            throw new RuntimeException(ex);
                        }
                    }
                })
                .filter(Objects::nonNull)
                .toList();

        ProgramState firstProgram = programStatesList.getFirst();
        List<Integer> availableAddressesFromHeap = getAddresses(firstProgram.getHeap().getContent().values());
        List<Integer> addressesToKeep = new ArrayList<>();
        for (ProgramState programState : programStatesList) {
            List<Integer> availableAddressesFromSymTable = getAddresses(programState.getSymTable().getContent().values());
            addressesToKeep.addAll(availableAddressesFromSymTable);
        }
        addressesToKeep.addAll(availableAddressesFromHeap);
        firstProgram.getHeap().setContent(garbageCollector(addressesToKeep, firstProgram.getHeap().getContent()));

        programStatesList.addAll(newProgramStatesList);

        programStatesList.forEach(program -> repository.logProgramStateExecution(program));

        repository.setCurrentProgramStatesList(programStatesList);

    }

    public void allSteps() throws Exception {

        executor = Executors.newFixedThreadPool(2);

        List<ProgramState> programStatesList = removeCompletedPrograms(repository.getCurrentProgramStatesList());

        while (!programStatesList.isEmpty()) {
            oneStepForAllPrograms(programStatesList);
            programStatesList = removeCompletedPrograms(repository.getCurrentProgramStatesList());
        }

        executor.shutdown();
        repository.setCurrentProgramStatesList(programStatesList);

    }

    public void shutdownExecutor(){
        executor.shutdown();
    }

    public void startExecutor(){

        executor = Executors.newFixedThreadPool(2);
        repository.setCurrentProgramStatesList(removeCompletedPrograms(repository.getCurrentProgramStatesList()));
    }
    public void setNewProgramStatesList(List<ProgramState> prgStatesList){
        repository.setCurrentProgramStatesList(prgStatesList);
    }

    public List<ProgramState> getCurrentProgramStatesList(){
        return repository.getCurrentProgramStatesList();
    }

}
