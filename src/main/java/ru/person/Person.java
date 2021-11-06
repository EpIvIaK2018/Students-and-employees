package ru.person;

import javafx.beans.property.SimpleStringProperty;
import ru.exception.AgeException;
import ru.exception.EmployeeAndStudentException;
import ru.exception.NameException;
import ru.exception.VYSException;

// Удалить строку не работает
abstract public class Person {
    private String age;
    private String  name;
    private String university;
    private String  status;
    public Person(String name, int age, String university, String status) throws EmployeeAndStudentException {
        if (!(String.valueOf(age).matches("[1-9][0-9]")) || (age < 15 || age > 99)) throw new AgeException("Некорректный возраст");
        if (!(name.matches("[А-Я][а-я]+"))) throw new NameException("Некорректное имя");
        if (!(university.matches("[А-Я]+"))) throw new VYSException("Некорректное название института");
        System.out.println("Привет: " + name + " " + age + " " + university + " " + status);
        this.age = String.valueOf(age);
        this.name = name;
        this.university = university;
        this.status = status;
    }

    public String getAge(){
        return this.age.toString();
    }
    public String getName() { return this.name.toString(); }
    public String getUniversity(){
        return this.university.toString();
    }
    public String getStatus(){ return this.status.toString(); }
    public abstract String getTable();
}


