package ru.exeption;

import javafx.scene.control.Alert;

public class AgeException extends EmployeeAndStudentException {
    private String message;

    public AgeException(String message) {
        super(message);
        this.message = message;
        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setTitle("Плохая цифра");
        alert.setHeaderText("Исключение");
        alert.setContentText("Плохая цифра в поле Возраст");
        alert.showAndWait();

    }
    public String getMessage(){return this.message;}
}
