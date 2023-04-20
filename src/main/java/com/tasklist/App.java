package com.tasklist;

import com.model.DbHelper;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;
import javafx.util.Callback;

import java.io.IOException;

/**
 * JavaFX App
 */
public class App extends Application {

    public static Scene scene;
    public static Stage stage;

    @Override
    public void start(Stage stageArg) throws IOException {
        scene = new Scene(loadFXML("TaskList"), 500, 400);
        stage = stageArg;
        stage.setScene(scene);
        stage.show();
    }

    static void setRoot(String fxml) throws IOException {
        scene.setRoot(loadFXML(fxml));
    }

    private static Parent loadFXML(String fxml) throws IOException {
        FXMLLoader fxmlLoader = getLoader(fxml);
        return fxmlLoader.load();
    }

    private static FXMLLoader getLoader(String fxml) {
        FXMLLoader fxmlLoader = new FXMLLoader(App.class.getResource(fxml + ".fxml"));
        return fxmlLoader;
    }

    static void openChildWindow(String fxmlName, Callback<Class<?>, Object> controllerFactory) throws IOException {
        Stage inputStage = new Stage();
        inputStage.initOwner(stage);
        FXMLLoader loader = getLoader(fxmlName);
        loader.setControllerFactory(controllerFactory);
        Scene newScene = new Scene(loader.load());
        inputStage.setScene(newScene);
        inputStage.showAndWait();

    }

    public static void main(String[] args) {

        DbHelper.getInstance();
        launch();
    }

}