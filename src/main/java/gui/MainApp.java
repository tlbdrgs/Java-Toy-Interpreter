package gui;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.stage.Stage;

import java.io.IOException;

public class MainApp extends Application {

    @Override
    public void start(Stage primaryStage) throws Exception {

        Stage secondaryStage = new Stage();
        initView(primaryStage, secondaryStage);
        secondaryStage.setTitle("All Programs");
        primaryStage.setTitle("Interpreter");
        primaryStage.show();
        secondaryStage.show();
    }

    private void initView(Stage primaryStage, Stage secondaryStage) throws IOException {
        FXMLLoader fxmlLoaderMain = new FXMLLoader(MainApp.class.getResource("main-view.fxml"));
        primaryStage.setScene(new Scene(fxmlLoaderMain.load(), 1200, 800));
        MainController mainController = fxmlLoaderMain.getController();

        FXMLLoader fxmlLoaderSecondary = new FXMLLoader(MainApp.class.getResource("select-program-view.fxml"));
        secondaryStage.setScene(new Scene(fxmlLoaderSecondary.load(), 800, 400));
        SelectProgramController selectProgramController = fxmlLoaderSecondary.getController();
        selectProgramController.setController(mainController);

    }


    public static void main(String[] args) {
        launch();
    }
}
