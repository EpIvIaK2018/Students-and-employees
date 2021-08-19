package ru.exeption;

import javafx.scene.control.Alert;

public class VYSException extends EmployeeAndStudentException {
    private String message;
    public VYSException(String message) {
        super(message);
        this.message = message;
        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setTitle("Некорректный ВУЗ");
        alert.setHeaderText("Некорректный ВУЗ");
        alert.setContentText("Некорректные данные в графе Институт");
        alert.showAndWait();
    }
    public String getMessage(){return this.message;}
}
