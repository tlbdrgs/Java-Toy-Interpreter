package gui;

import controller.Controller;
import exceptions.MyException;
import javafx.beans.property.Property;
import javafx.beans.property.SimpleIntegerProperty;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.util.Pair;
import model.ProgramState;
import model.adt.*;
import model.statements.IStmt;
import model.values.IValue;
import repository.Repository;

import java.util.ArrayList;
import java.util.List;

public class MainController {

    private Controller controller;

    @FXML
    private ListView<Integer> allProgramStatesList;

    @FXML
    private ListView<String> outputList;

    @FXML
    private ListView<String> fileTable;

    @FXML
    private ListView<String> executionStack;

    @FXML
    private Button runOneStepBtn;

    @FXML
    private TextField noOfPrgStates;

    @FXML
    private TableView<Pair<String, IValue>> symbolTable;

    @FXML
    private TableColumn<Pair<String, IValue>, String> varNameColumn;

    @FXML
    private TableColumn<Pair<String,IValue>, String> varValueColumn;

    @FXML
    private  TableView<Pair<Integer, IValue>> heapTable;

    @FXML
    private TableColumn<Pair<Integer, IValue>, Integer> addressColumn;

    @FXML
    private TableColumn<Pair<Integer,IValue>, String> valueOfAddressColumn;

    @FXML
    private  TableView<Pair<Integer, Integer>> lockTable;

    @FXML
    private TableColumn<Pair<Integer, Integer>, Integer> indexColumn;

    @FXML
    private TableColumn<Pair<Integer,Integer>, Integer> valueOfIndexColumn;

    @FXML
    public void initialize(){
        varNameColumn.setCellValueFactory(new PropertyValueFactory<Pair<String, IValue>, String>("key"));
        varValueColumn.setCellValueFactory(new PropertyValueFactory<Pair<String, IValue>, String>("value"));
        addressColumn.setCellValueFactory(new PropertyValueFactory<Pair<Integer, IValue>, Integer>("key"));
        valueOfAddressColumn.setCellValueFactory(new PropertyValueFactory<Pair<Integer, IValue>, String>("value"));
        indexColumn.setCellValueFactory(new PropertyValueFactory<Pair<Integer, Integer>, Integer>("key"));
        valueOfIndexColumn.setCellValueFactory(new PropertyValueFactory<Pair<Integer, Integer>, Integer>("value"));

    }
    public void setController(Controller controller){
        this.controller = controller;
        initAll();

    }

    void initAll(){
        initAllProgramStatesList();
        initNoPrgStatesTextField();
        initExecutionStack();
        initSymbolTable();
        initHeapTable();
        initFileTable();
        initLockTable();
        initOutputList();
    }

    public void handleRunOneStepBtn(){
        if(controller == null){
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("An error has occurred!");
            alert.setContentText("No program selected! Please select an program first!");
            alert.showAndWait();
        }
        else {
            List<ProgramState> allProgramStates = controller.getCurrentProgramStatesList();
            if(allProgramStates.isEmpty()){
                controller.setNewProgramStatesList(allProgramStates);
                controller.shutdownExecutor();
                Alert alert = new Alert(Alert.AlertType.WARNING);
                alert.setTitle("An warning has occurred!");
                alert.setContentText("There isn't nothing else to execute!");
                alert.showAndWait();
            }
            else {
                try{
                    controller.oneStepForAllPrograms(allProgramStates);
                    initAll();
                    controller.setNewProgramStatesList(controller.removeCompletedPrograms(controller.getCurrentProgramStatesList()));
                    initAllProgramStatesList();
                    initNoPrgStatesTextField();
                }catch (Exception e){
                    Alert alert = new Alert(Alert.AlertType.ERROR);
                    alert.setTitle("An error has occurred!");
                    alert.setContentText(e.toString());
                    alert.showAndWait();
                }
            }
        }
    }

    private ProgramState getCurrentProgramState(){
        int indexOfCurrentState = allProgramStatesList.getSelectionModel().getSelectedIndex();
        if(indexOfCurrentState > -1)
            return controller.getCurrentProgramStatesList().get(indexOfCurrentState);
        else
            return controller.getCurrentProgramStatesList().getFirst();
    }

    private void initNoPrgStatesTextField(){
        noOfPrgStates.setText(String.valueOf(controller.getCurrentProgramStatesList().size()));
    }

    private void initOutputList(){
        ProgramState firstProgramState = controller.getCurrentProgramStatesList().getFirst();

        MyIList<IValue> outList = firstProgramState.getOutputList();
        List<String> outputListStringForm = new ArrayList<>();

        for(int index = 0; index < outList.getSize(); index++){
            outputListStringForm.add(outList.get(index).toString());
        }

        outputList.setItems(FXCollections.observableArrayList(outputListStringForm));
    }

    private void initFileTable(){
        ProgramState firstProgramState = controller.getCurrentProgramStatesList().getFirst();
        fileTable.setItems(FXCollections.observableArrayList(firstProgramState.getFileTable().getContent().keySet().stream().map(Object::toString).toList()));
    }

    private void initAllProgramStatesList(){
        List<ProgramState> allProgramStates = controller.getCurrentProgramStatesList();
        allProgramStatesList.setItems(FXCollections.observableArrayList(allProgramStates.stream().map(ProgramState::getId).toList()));

    }

    private void initExecutionStack(){
        ProgramState selectedProgramState = getCurrentProgramState();

        if(selectedProgramState != null){
            executionStack.setItems(FXCollections.observableArrayList(selectedProgramState.getExeStack().reversedStackListForm().stream().map(Object::toString).toList()));
        }
        else
            executionStack.setItems(FXCollections.observableArrayList());
    }

    private void initSymbolTable(){
        ProgramState selectedProgramState = getCurrentProgramState();

        if(selectedProgramState != null){
            MyIDictionary<String, IValue> symTbl = selectedProgramState.getSymTable();
            symbolTable.setItems(FXCollections.observableArrayList(symTbl.getContent().entrySet().stream().map(item -> new Pair<String, IValue>(item.getKey(), item.getValue())).toList()));
        }
        else
            symbolTable.setItems(FXCollections.observableArrayList());

    }

    private void initHeapTable(){
        ProgramState selectedProgramState = getCurrentProgramState();

        if(selectedProgramState != null){
            MyIHeap<Integer, IValue> heap = selectedProgramState.getHeap();
            heapTable.setItems(FXCollections.observableArrayList(heap.getContent().entrySet().stream().map(item -> new Pair<Integer, IValue>(item.getKey(), item.getValue())).toList()));
        }
        else
            heapTable.setItems(FXCollections.observableArrayList());

    }

    private void initLockTable(){
        ProgramState selectedProgramState = getCurrentProgramState();

        if(selectedProgramState != null){
            MyILockTable lockTbl = selectedProgramState.getLockTable();
            lockTable.setItems(FXCollections.observableArrayList(lockTbl.getContent().entrySet().stream().map(item -> new Pair<Integer, Integer>(item.getKey(), item.getValue())).toList()));
        }
        else
            lockTable.setItems(FXCollections.observableArrayList());
    }

    public void handleSelectedProgramState(){
        initExecutionStack();
        initSymbolTable();
    }

}




