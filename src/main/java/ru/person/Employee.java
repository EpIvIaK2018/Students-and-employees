package ru.person;

import ru.exception.EmployeeAndStudentException;

public class Employee extends Person {
    private int profit;
    public Employee(String name, int age, String university, int profit) throws EmployeeAndStudentException {
        super(name, age, university, "Рабочий");
        this.profit = profit;
    }

    @Override
    public String toString() {
        return "Работник:\n" + this.getName() + "\n" + this.getAge() + " лет" + "\nОкончил ВУЗ:" + getUniversity() + "\nЗарплата: " + this.profit + " рублей";
    }
    public float getSalary(){
        return this.profit;
    }

    @Override
    public String getTable() {
        return "employees";
    }

}

