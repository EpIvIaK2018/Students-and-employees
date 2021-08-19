package ru.main;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.*;
import javafx.scene.text.Font;
import javafx.scene.text.FontWeight;
import ru.ecxeption.EmployeeAndStudentException;
import ru.person.*;
import bd.BdConnection;
import java.sql.SQLException;
import javafx.beans.property.SimpleObjectProperty;
import javafx.application.Application;
import javafx.stage.Stage;
import javafx.scene.Scene;

public class MainTest extends Application {
    static BdConnection myBD;
    Stage window;
    ObservableList<Person> ListAllUsers;
    ObservableList<Person> ListAllStudents = FXCollections.observableArrayList();
    ObservableList<Person> ListAllEmployee = FXCollections.observableArrayList();
    TableView<Person> table =  new TableView<>();
    TableView<Person> table2 = new TableView<>();
    public static void main(String[] args) {
        Application.launch(args);
    }

    public void Update() throws SQLException {
        ListAllStudents.clear();
        ListAllEmployee.clear();
        ListAllUsers = myBD.ExtractAllUsers();

        System.out.println(ListAllUsers + "____________________________________________");
        if(!ListAllUsers.isEmpty()){
            for(Person p : ListAllUsers){
                    if(p instanceof Student) {
                        ListAllStudents.add((Student)p);
                    }
                    if(p instanceof Employee){
                        ListAllEmployee.add((Employee)p);
                    }
            }
            if(!ListAllEmployee.isEmpty())  table.setItems(ListAllEmployee);
            if(!ListAllStudents.isEmpty()) table2.setItems(ListAllStudents);
            System.out.println(ListAllEmployee + "\n\n\n\n\n\n");
            System.out.println(ListAllStudents);
        }
    }

    public void start(Stage primaryStage) throws SQLException {
        myBD = BdConnection.getInstance();
        window = primaryStage;
        window.setTitle("Our Table");
        Button buttonRefresh =   new Button("Обновить таблицу");
        Button buttonAddPerson = new Button("Добавить человека");
        Button buttonDeleteAll = new Button("Очистить таблицу");
        Button buttonDeleteRow = new Button("Удалить строку");
        Font font = Font.font("Courier New", FontWeight.BOLD, 36);

        buttonDeleteRow.setFont(font);
        final String IDLE_BUTTON_STYLE = "-fx-background-color: transparent;";
        final String HOVERED_BUTTON_STYLE = "-fx-background-color: -fx-shadow-green-color, -fx-outer-border, -fx-inner-border, -fx-body-color;";
        buttonDeleteRow.setWrapText(true);

        buttonDeleteRow.setOnMouseEntered(e -> buttonDeleteRow.setStyle(HOVERED_BUTTON_STYLE));
        buttonDeleteRow.setOnMouseExited(e -> buttonDeleteRow.setStyle(IDLE_BUTTON_STYLE));

        TableColumn<Person, String> nameColumn = new TableColumn<>("Name");
        nameColumn.setMinWidth(200);
        nameColumn.setCellValueFactory(new PropertyValueFactory<Person, String>("name"));
        TableColumn<Person, Integer> ageColumn = new TableColumn<>("Age");
        ageColumn.setMinWidth(100);
        ageColumn.setCellValueFactory(new PropertyValueFactory<Person, Integer>("age"));

        TableColumn<Person, String> universitiesColumn = new TableColumn<>("University");
        universitiesColumn.setMinWidth(100);
        universitiesColumn.setCellValueFactory(new PropertyValueFactory<Person, String>("university"));

        TableColumn<Person, String> statusColumn = new TableColumn<>("Status");
        statusColumn.setMinWidth(100);
        statusColumn.setCellValueFactory(new PropertyValueFactory<Person, String>("status"));

        TableColumn<Person, String> salaryColumn = new TableColumn<>("Salary");
        salaryColumn.setMinWidth(100);
        salaryColumn.setCellValueFactory(new PropertyValueFactory<Person, String>("salary"));



        table.getColumns().addAll(nameColumn, ageColumn, universitiesColumn, statusColumn, salaryColumn);

        nameColumn = new TableColumn<>("Name");
        nameColumn.setMinWidth(200);
        nameColumn.setCellValueFactory(param -> new SimpleObjectProperty<>(param.getValue().getName()));

        ageColumn = new TableColumn<>("Age");
        ageColumn.setMinWidth(200);
        ageColumn.setCellValueFactory(param -> new SimpleObjectProperty<>(param.getValue().getAge()));

        universitiesColumn = new TableColumn<>("University");
        universitiesColumn.setMinWidth(200);
        universitiesColumn.setCellValueFactory(param -> new SimpleObjectProperty<>(param.getValue().getUniversity()));

        statusColumn = new TableColumn<>("Status");
        statusColumn.setMinWidth(200);
        statusColumn.setCellValueFactory(param -> new SimpleObjectProperty<>(param.getValue().getStatus()));

        table2.getColumns().addAll(nameColumn, ageColumn, universitiesColumn, statusColumn);


        buttonRefresh.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent actionEvent) {
                try {
                    Update();
                } catch (SQLException e) {
                    e.printStackTrace();
                }
            }
        });

        /* Добавление человека */
        buttonAddPerson.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent actionEvent) {
                Stage stage = new Stage();
                stage.setTitle("Добавление человека в БД");
                stage.setHeight(400);
                stage.setWidth(300);
                Label lbl = new Label("Введите имя");
                TextField textName = new TextField();
                textName.setPrefColumnCount(11);
                Label lb2 = new Label("Введите возраст");
                TextField textAge = new TextField();
                textAge.setPrefColumnCount(11);
                Label lb3 = new Label("Введите Университет");
                TextField textInst = new TextField();
                textInst.setPrefColumnCount(11);
                Label lb4 = new Label("Рабочий");
                CheckBox textField4 = new CheckBox();

                Button btn = new Button("Click");
                btn.setLayoutX(155);
                GridPane root = new GridPane();
                root.add(lbl, 1, 1);   // столбец=1 строка=0
                root.add(lb2, 1, 2);   // столбец=1 строка=0
                root.add(lb3, 1, 3);   // столбец=1 строка=0
                root.add(lb4, 1, 4);   // столбец=1 строка=0
                root.add(textName, 2, 1);   // столбец=1 строка=0
                root.add(textAge, 2, 2);   // столбец=1 строка=0
                root.add(textInst, 2, 3);   // столбец=1 строка=0
                root.add(textField4, 2, 4);   // столбец=1 строка=0

                root.add(btn, 1, 6);   // столбец=1 строка=0


                Label lb5 = new Label("Введите зарплату");
                TextField textSalary = new TextField();

                textField4.setOnMousePressed(keyEvent -> {
                            root.add(lb5, 1, 5);   // столбец=1 строка=0
                            root.add(textSalary, 2, 5);   // столбец=1 строка=0
                        });

                btn.setOnAction(event -> {
                    try {
                        Person newPerson = null;
                        // Спросить про полиморфизм у Бурилло
                        if (!textField4.isSelected()) {
                            newPerson = new Student(textName.getText(), Integer.valueOf(textAge.getText()), textInst.getText());
                            myBD.QueryAddStudent((Student) newPerson);
                        } else {
                            newPerson = new Employee(textName.getText(),Integer.valueOf(textAge.getText()), textInst.getText(), Integer.valueOf(textSalary.getText()));
                            myBD.QueryAddEmployee((Employee) newPerson);
                        }
                        Update();

                    } catch (SQLException | EmployeeAndStudentException e) {
                        e.printStackTrace();
                    }
                });

                System.err.println(lbl.getText());
                Scene scene = new Scene(root,500, 300);
                stage.setScene(scene);
                stage.show();
            }
        });

        buttonDeleteAll.setOnAction(actionEvent -> {
            try {
                myBD.deleteAll();
                //table.setItems(myBD.ExtractAllUsers());
                Update();
            } catch (SQLException e) {
                e.printStackTrace();
            }
        });
        /*s
        * Удаление Строки из Таблицы
        * */
        buttonDeleteRow.setOnAction(actionEvent -> {
            try {
                // тут определить какая таблица, студент или рабочий.
                Person selectedItem = null;
                if(!table2.getSelectionModel().isEmpty()){
                    selectedItem = table2.getSelectionModel().getSelectedItem();
                }else if(!table.getSelectionModel().isEmpty()){
                    selectedItem = table.getSelectionModel().getSelectedItem();
                }else{
                    System.out.println("Ничего не выбрано");
                }
                //System.out.println(table2.getSelectionModel().getSelectedItem().getTable().isEmpty());
                if (!(selectedItem == null)){
                    myBD.deleteRow(selectedItem.getTable(), selectedItem);
                    Update();
                }
                //table.setItems(myBD.ExtractAllUsers());
            } catch (SQLException e) {
                System.out.println("Не удалось удалить строку");
                e.printStackTrace();
            }
        });

        VBox vBox = new VBox();
        Update();
        vBox.getChildren().addAll(table, table2, buttonRefresh, buttonAddPerson, buttonDeleteAll, buttonDeleteRow);
        Scene scene = new Scene(vBox);
        window.setScene(scene);
        window.show();
    }
}
