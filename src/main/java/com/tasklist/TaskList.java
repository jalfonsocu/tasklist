package com.tasklist;

import java.io.IOException;
import java.net.URL;
import java.util.List;
import java.util.Optional;
import java.util.ResourceBundle;

import com.model.DbHelper;
import com.model.Task;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.*;
import javafx.scene.control.Alert.AlertType;

public class TaskList implements Initializable {
    /**
     *
     */
    @FXML
    ListView<Task> taskListView;
    @FXML
    TextField textTitulo;
    @FXML
    TextArea areaDescripcion;
    @FXML
    DatePicker datePicker;

    @FXML
    private void addNewTask() {

        Task task = new Task();

        task.setTitulo(textTitulo.getText());
        task.setDescripcion(areaDescripcion.getText());
        task.setFecha(datePicker.getValue().atStartOfDay());

        DbHelper.getInstance().addTask(task);
        //openCrudeView(task);
        refreshTaskListView();
    }

    @FXML
    private void updateTask(){
        try {
            Task selectedTask = selectedTask();
            selectedTask.setTitulo(textTitulo.getText());
            selectedTask.setDescripcion(areaDescripcion.getText());
            selectedTask.setFecha(datePicker.getValue().atStartOfDay());

            DbHelper.getInstance().editTask(selectedTask);

            refreshTaskListView();
        } catch (IOException e) {
            throw new RuntimeException(e);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    @FXML
    private void viewTask() {
        try {
            Task selectedTask = selectedTask();
            textTitulo.setText(selectedTask.getTitulo());
            areaDescripcion.setText(selectedTask.getDescripcion());
            datePicker.setValue(selectedTask.getFecha().toLocalDate());
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private Task selectedTask() throws IOException {
        Task selectedTask = taskListView.getSelectionModel().getSelectedItem();
        if (selectedTask == null) {
            throw new NullPointerException();
        }
        return selectedTask;
    }

    @FXML
    private void deleteTask() {
        try {
            Task selectedTask = selectedTask();
            Optional<ButtonType> result = deleteTaskConfirmationDialog(selectedTask);
            if (result.get() == ButtonType.OK) {

                DbHelper.getInstance().deleteTask(selectedTask);
                taskListView.getItems().remove(selectedTask);
            }
        } catch (NullPointerException e) {
            Alert alert = new Alert(AlertType.CONFIRMATION);
            alert.setTitle("Error");
            alert.setHeaderText("No hay tarea seleccionada");
            alert.setContentText("No hay tarea seleccionada");

            Optional<ButtonType> result = alert.showAndWait();
        } catch (IOException e) {
            Alert alert = new Alert(AlertType.CONFIRMATION);
            alert.setTitle("Error");
            alert.setHeaderText("Error");
            alert.setContentText("error");

            Optional<ButtonType> result = alert.showAndWait();
        }
    }



    private void refreshTaskListView() {

        List<Task> taskList = DbHelper.getInstance().getTaskList();
        // Convertir la lista en un ObservableList
        ObservableList<Task> observableList = FXCollections.observableList(taskList);
        taskListView.setItems(observableList);

    }

    private Optional<ButtonType> deleteTaskConfirmationDialog(Task selectedCrude) {
        Alert alert = new Alert(AlertType.CONFIRMATION);
        alert.setTitle("Atención");
        alert.setHeaderText("Al parecer decea eliminar la tarea %s".formatted(selectedCrude.getTitulo()));
        alert.setContentText("¿Estás seguro de querer eliminarlo?");

        Optional<ButtonType> result = alert.showAndWait();
        return result;
    }


    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        refreshTaskListView();
    }
}
