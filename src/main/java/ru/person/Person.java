package ru.person;

import ru.exception.AgeException;
import ru.exception.EmployeeAndStudentException;
import ru.exception.NameException;
import ru.exception.VYSException;

// Удалить строку не работает
abstract public class Person {
    private int age;
    private String name;
    private String university;
    private String status;
    public Person(String name, int age, String university, String status) throws EmployeeAndStudentException {
        if (!(String.valueOf(age).matches("[1-9][0-9]")) || (age < 15 || age > 99)) throw new AgeException("Некорректный возраст");
        if (!(name.matches("[А-Я][а-я]+"))) throw new NameException("Некорректное имя");
        if (!(university.matches("[А-Я]+"))) throw new VYSException("Некорректное название института");
        this.status = status;
        this.age = age;
        this.name = name;
        this.university = university;
    }
    public int getAge(){
        return this.age;
    }
    public String getName() {
        return this.name;
    }
    public String getUniversity(){
        return this.university;
    }
    public String getStatus(){ return this.status; }
    public abstract String getTable();
}


