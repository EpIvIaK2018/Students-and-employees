package bd;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import ru.exception.EmployeeAndStudentException;
import ru.person.*;

import java.sql.*;
import java.util.HashMap;

public class BdConnection {
    private static final String URL = "jdbc:mysql://localhost:3306/students_employee?autoRecconect=true&useSSL=false&serverTimezone=UTC";
    private static final String login = "EpIvIaK";
    private static final String password = "///";       // Убрал пароль для ГитХаба
    private static Connection connect;
    private static PreparedStatement prSt;
    private static BdConnection instance;
    private static HashMap<Person, Integer> personToIdMap = new HashMap<>();

    public static BdConnection getInstance() {
        if (instance == null) {
            instance = new BdConnection();
        }
        else System.err.print("Объект уже был создан\n");
        showHasMap();
        return instance;
    }
    public void QueryAddStudent(Student s) throws SQLException {
        try{
            connect = DriverManager.getConnection(URL, login, password);
            String result = "insert into student(age, name, VYS) values(?, ?, ?)";
            prSt = connect.prepareStatement(result, Statement.RETURN_GENERATED_KEYS);
            prSt.setInt   (1, Integer.parseInt(s.getAge().toString()));
            prSt.setString(2, s.getName().toString());
            prSt.setString(3, s.getUniversity().toString());
            prSt.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            connect.close();
        }
    }
    public void QueryAddEmployee(Employee e) throws SQLException {
        try{
            connect = DriverManager.getConnection(URL, login, password);
            String result = "insert into employees(age, name, VYS, salary) values(?, ?, ?, ?)";
            prSt = connect.prepareStatement(result);
            prSt.setInt   (1, Integer.parseInt(e.getAge().toString()));
            prSt.setString(2, e.getName().toString());
            prSt.setString(3, e.getUniversity().toString());
            prSt.setInt   (4, (int) e.getSalary());
            prSt.executeUpdate();
        } catch (SQLException ex) {
            ex.printStackTrace();
        } finally {
            connect.close();
        }
    }
    public ObservableList<Person> ExtractAllUsers() throws SQLException {
        ObservableList<Person> ListAllUsers = FXCollections.observableArrayList();
        connect = DriverManager.getConnection(URL, login, password);

        String resultExtract = "select * from student";
        Statement statement = connect.createStatement();
        ResultSet result = statement.executeQuery(resultExtract);
        while (result.next()) {
            Person studentCurrectly = null;
            try {
                studentCurrectly = new Student(result.getString("name"), result.getInt("age"),
                        result.getString("VYS"));
            } catch (EmployeeAndStudentException employeeAndStudentException) {
                employeeAndStudentException.printStackTrace();
            }
            ListAllUsers.add(studentCurrectly);
            personToIdMap.put(studentCurrectly, result.getInt("id"));
        }
        resultExtract = "select * from employees";
        result = statement.executeQuery(resultExtract);
        while (result.next()) {
            Person employeeCurrently = null;
            try {
                employeeCurrently = new Employee(result.getString("name"),result.getInt("age"),
                        result.getString("VYS"), result.getInt("salary"));
            } catch (EmployeeAndStudentException employeeAndStudentException) {
                employeeAndStudentException.printStackTrace();
            }
            ListAllUsers.add(employeeCurrently);
            personToIdMap.put(employeeCurrently, result.getInt("id"));
        }
        connect.close();
        return ListAllUsers;
    }

    private boolean checkForEmpty(Connection connect) throws SQLException {
        ResultSet rs;
        String qry = "SELECT count(*) from student, employees";
        PreparedStatement statement = connect.prepareStatement(qry);
        rs =  statement.executeQuery();
        int count = 0;
        while(rs.next()){
            count = rs.getInt("count(*)");
        }
        System.out.println(count);
        if(count > 0) return true;
        return false;
    }

    public void deleteAll() throws SQLException{
        try {
            connect = DriverManager.getConnection(URL, login, password);
            String qryDelete = "DELETE FROM student";
            Statement statement = connect.createStatement();
            statement.executeUpdate(qryDelete);
            qryDelete = "DELETE FROM employees";
            statement.executeUpdate(qryDelete);
            statement.close();
        } catch (SQLException ex){
            ex.printStackTrace();
        } finally {
            connect.close();
            System.out.println("БД очищенна");
        }
    }

    public void deleteRow(String Table, Person person) throws SQLException{
        try {
            connect = DriverManager.getConnection(URL, login, password);
            String qryDelete = "DELETE FROM " + Table + " Where id = " + personToIdMap.get(person);
            Statement statement = connect.createStatement();
            statement.executeUpdate(qryDelete);
        } catch (SQLException ex){
            ex.printStackTrace();
            System.out.println("Неудачно");
        } finally {
            connect.close();
        }
    }

    private static void showHasMap(){
        for (HashMap.Entry<Person, Integer> entry : personToIdMap.entrySet()) {
            //System.out.println("Key: " + entry.getKey() + " Value: " + entry.getValue());
        }
    }
}
