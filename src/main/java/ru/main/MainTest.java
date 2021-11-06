package ru.main;
import currencies.Currencies;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.value.ObservableValue;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.geometry.Side;
import javafx.scene.chart.PieChart;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.image.Image;
import javafx.scene.layout.*;
import javafx.scene.text.Font;
import javafx.scene.text.FontWeight;
import ru.exception.EmployeeAndStudentException;
import ru.person.*;
import bd.BdConnection;
import java.sql.SQLException;

import javafx.application.Application;
import javafx.stage.Stage;
import javafx.scene.Scene;

public class MainTest extends Application {
    static BdConnection myBD;
    Label currency;
    public Currencies currencies;
    private int width = 820;
    private int height = 600;
    Stage window;
    ObservableList<Person> listAllUsers;
    ObservableList<Person> listAllStudents = FXCollections.observableArrayList();
    ObservableList<Person> listAllEmployee = FXCollections.observableArrayList();
    ObservableList<String> listCurrencies  = FXCollections.observableArrayList("Рубли", "Доллары", "Евро");

    TableView<Person> table =  new TableView<>();
    TableView<Person> table2 = new TableView<>();
    ComboBox<String> currenciesComboBox;

    public static void main(String[] args) {
        Application.launch(args);
    }
    /*
        We extract data from BD and here fill lists of persons
     */
    public void Update() throws SQLException {
        currencies.loadingCurrencies();
        String s = String.format("USD: %.5s$ \t EUR: %.5s€ ", currencies.getDollarsCurrency(), currencies.getEuroCurrency());
        currency.setText(s);
        listAllStudents.clear();
        listAllEmployee.clear();
        /*Here mybd'method returns ObservableList from BD filled with data */
        listAllUsers = myBD.ExtractAllUsers();
        //System.out.println(listAllUsers + "____________________________________________");
        if(!listAllUsers.isEmpty()){
            for(Person p : listAllUsers){
                if(p instanceof Student) {
                    listAllStudents.add(p);
                }
                if(p instanceof Employee){
                    listAllEmployee.add(p);
                }
            }
            /*
             *  Add these lists to tables
             */
            if(!listAllEmployee.isEmpty()){
                table.setItems(listAllEmployee);
            }
            if(!listAllStudents.isEmpty()){
                table2.setItems(listAllStudents);
            }
        }
    }

    public void start(Stage primaryStage) throws SQLException {
        currencies = Currencies.getInstance();
        assert currencies != null;
        currencies.loadingCurrencies();
        myBD = BdConnection.getInstance();
        window = primaryStage;
        window.setTitle("Our Table");
        window.setWidth(width);
        window.setHeight(height);
        table.setPlaceholder(new Label("Table is has't any workers"));
        table2.setPlaceholder(new Label("Table is has't any students"));

        Button buttonRefresh =   new Button("Обновить таблицу");
        Button buttonAddPerson = new Button("Добавить человека");
        Button buttonDeleteAll = new Button("Очистить таблицу");
        Button buttonDeleteRow = new Button("Удалить строку");
        Button VALUE = new Button("Надо изменить САЛАРИ");
        Button buttonDiagram = new Button("Диаграмма");
        Font font = Font.font("Courier New", FontWeight.BOLD, 20);

        buttonRefresh.setFont(font);
        buttonAddPerson.setFont(font);
        buttonDeleteAll.setFont(font);
        buttonDeleteRow.setFont(font);

        buttonRefresh.setDefaultButton(true);
        buttonAddPerson.setDefaultButton(true);
        buttonDeleteAll.setDefaultButton(true);
        buttonDeleteRow.setDefaultButton(true);

        buttonRefresh.setMinWidth(400);
        buttonAddPerson.setMinWidth(400);
        buttonDeleteAll.setMinWidth(400);
        buttonDeleteRow.setMinWidth(400);
        //final String IDLE_BUTTON_STYLE = "-fx-background-color: transparent;";
        //final String HOVERED_BUTTON_STYLE = "-fx-background-color: -fx-shadow-green-color, -fx-outer-border, -fx-inner-border;";
        buttonDeleteRow.setWrapText(true);

        Label studentsColumnName = new Label("Студенты");
        Label employeesColumnName = new Label("Рабочие");

        currency = new Label("Валюты: ");
        currency.setText("Доллар: " + currencies.getDollarsCurrency() + " Евро: " + currencies.getEuroCurrency());
        currency.setStyle("-fx-font-size: 25px; -fx-background-color: black; -fx-text-fill: white; -fx-font-family: sans-serif;\n" +
                "    -fx-font-weight: bold;\n" +
                "    -fx-underline-color: blue;");



        final TableColumn<Person, String> nameColumn = new TableColumn<>("Name");
        nameColumn.setMinWidth(width/5);
        nameColumn.setMaxWidth(width/5);
        nameColumn.setCellValueFactory(new PropertyValueFactory<>("name"));

        final TableColumn<Person, Integer> ageColumn = new TableColumn<>("Age");
        ageColumn.setMinWidth(width/5);
        ageColumn.setMinWidth(width/5);
        ageColumn.setCellValueFactory(new PropertyValueFactory<>("age"));

        TableColumn<Person, String> universitiesColumn = new TableColumn<>("University");
        universitiesColumn.setMinWidth(width/5);
        universitiesColumn.setMinWidth(width/5);
        universitiesColumn.setCellValueFactory(new PropertyValueFactory<>("university"));

        TableColumn<Person, String> statusColumn = new TableColumn<>("Status");
        statusColumn.setMinWidth(width/5);
        statusColumn.setMinWidth(width/5);
        statusColumn.setCellValueFactory(new PropertyValueFactory<>("status"));

        TableColumn<Person, String> salaryColumn = new TableColumn<>("Salary");
        salaryColumn.setMinWidth(width/5);
        salaryColumn.setMinWidth(width/5);
        salaryColumn.setCellValueFactory(new PropertyValueFactory<>("salary"));
        salaryColumn.setEditable(true);

        table.getColumns().addAll(nameColumn, ageColumn, universitiesColumn, statusColumn, salaryColumn);

        final TableColumn<Person, String> nameColumn2 = new TableColumn<>("Name");
        nameColumn2.setMinWidth(width/4);
        nameColumn2.setMaxWidth(width/4);
        nameColumn2.setCellValueFactory(new PropertyValueFactory<>("name"));

        final TableColumn<Person, String> ageColumn2 = new TableColumn<>("Age");
        ageColumn2.setMinWidth(width/4 );
        ageColumn2.setMaxWidth(width/4 );
        ageColumn2.setCellValueFactory(new PropertyValueFactory<>("age"));

        final TableColumn<Person, String> universitiesColumn2 = new TableColumn<>("University");
        universitiesColumn2.setMinWidth(width/4 );
        universitiesColumn2.setMaxWidth(width/4 );
        universitiesColumn2.setCellValueFactory(new PropertyValueFactory<>("university"));

        final TableColumn<Person, String> statusColumn2 = new TableColumn<>("Status");
        statusColumn2.setMinWidth(width/4 );
        statusColumn2.setMaxWidth(width/4);
        statusColumn2.setCellValueFactory(new PropertyValueFactory<>("status"));

        table2.getColumns().addAll(nameColumn2, ageColumn2, universitiesColumn2, statusColumn2);


    buttonRefresh.setOnAction(actionEvent -> {
        try {
            Update();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    });

    /* Добавление человека */
    buttonAddPerson.setOnAction(actionEvent -> {
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
        Button exitFromHere = new Button("End");
        exitFromHere.setOnAction(actionEvent1 -> stage.close()
        );

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
        root.add(exitFromHere, 1,13);


        Label lb5 = new Label("Введите зарплату");
        TextField textSalary = new TextField();

        textField4.setOnMousePressed(keyEvent -> {
            root.add(lb5, 1, 5);   // столбец=1 строка=0
            root.add(textSalary, 2, 5);   // столбец=1 строка=0
        });

        btn.setOnAction(event -> {
            try {
                Person newPerson;
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
    });
    /******************************************/


    buttonDeleteAll.setOnAction(actionEvent -> {
        try {
            myBD.deleteAll();
            //table.setItems(myBD.ExtractAllUsers());
            Update();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    });

    VALUE.setOnAction(e -> {
        salaryColumn.getText();
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

    buttonDiagram.setOnAction(actionEvent -> {
        Stage stage = new Stage();
        ObservableList<PieChart.Data> pieChartData = FXCollections.observableArrayList(
                new PieChart.Data("Студенты", table2.getItems().size()),
                new PieChart.Data("Рабочие",  table.getItems().size()));
        final PieChart chart = new PieChart(pieChartData);
        chart.setTitle("Рабочие и студенты");
        Scene scene = new Scene(chart,800, 600);
        stage.setScene(scene);
        stage.getScene().getStylesheets().add("style.css");
        stage.show();
    });

        window.widthProperty().addListener((obs, oldVal, newVal) -> {
            width = (int)window.getWidth();
            nameColumn.setMinWidth(width / 5);
            nameColumn.setMaxWidth(width / 5);
            ageColumn.setMinWidth(width / 5 - 18);
            ageColumn.setMaxWidth(width / 5 - 18);
            statusColumn.setMinWidth(width/5);
            statusColumn.setMaxWidth(width/5);
            universitiesColumn.setMinWidth(width/5);
            universitiesColumn.setMaxWidth(width/5);
            salaryColumn.setMinWidth(width/5);
            salaryColumn.setMaxWidth(width/5);

            nameColumn2.setMinWidth(width / 4);
            nameColumn2.setMaxWidth(width / 4);
            ageColumn2.setMinWidth(width / 4 - 18);
            ageColumn2.setMaxWidth(width / 4 - 18);
            statusColumn2.setMinWidth(width/4);
            statusColumn2.setMaxWidth(width/4);
            universitiesColumn2.setMinWidth(width/4);
            universitiesColumn2.setMaxWidth(width/4);

            System.out.println(width);
        });

    Update();

    VBox content = new VBox();
    content.setBackground(new Background(
            new BackgroundImage(
                    new Image("https://bipbap.ru/wp-content/uploads/2021/04/11c3633086e52e789e117705380ab339.jpg"),
                    BackgroundRepeat.REPEAT, BackgroundRepeat.NO_REPEAT,
                    new BackgroundPosition(Side.LEFT, 0, true, Side.BOTTOM, 0, true),
                    new BackgroundSize(BackgroundSize.AUTO, BackgroundSize.AUTO, true, true, false, true)
                )
        ));
        content.setMinWidth(500);
        //content.setStyle("-fx-background-color: gray;");
        content.getChildren().addAll(employeesColumnName, table, studentsColumnName, table2,
                buttonRefresh, buttonAddPerson, buttonDeleteAll,
                buttonDeleteRow, VALUE, buttonDiagram, currency
        );
    VBox.setVgrow(table2, Priority.ALWAYS);
    table2.setMaxHeight(Double.MAX_VALUE);
    Scene scene = new Scene(content);
    window.setScene(scene);
    window.getScene().getStylesheets().add("style.css");
    window.show();
    }

}