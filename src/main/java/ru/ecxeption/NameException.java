package ru.ecxeption;

import javafx.scene.control.Alert;

public class NameException extends EmployeeAndStudentException {
    private String message;

    public NameException(String message) {
        super(message);
        this.message = message;
        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setTitle("Некорректное имя");
        alert.setHeaderText("Некорректное имя");
        alert.setContentText("Некорректное имя человека (name)");
        alert.showAndWait();
    }
    public String getMessage(){return this.message;}
}
