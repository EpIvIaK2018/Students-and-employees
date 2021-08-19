package ru.person;

import ru.exception.EmployeeAndStudentException;

public class Student extends Person {
    public Student(String name, int age, String university) throws EmployeeAndStudentException {
        super(name, age,  university, "Студент");
    }
    @Override
    public String toString() {
        return "Студент:\n" + this.getName() + "\n" + this.getAge() + " лет " + "\nОкончил ВУЗ:" + getUniversity();
    }

    @Override
    public String getTable() { return "student";
    }
}
