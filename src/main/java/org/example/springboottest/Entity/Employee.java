package org.example.springboottest.Entity;

public class Employee {
    private String name;
    private int id;
    private String gender;
    private double salary;

    public Employee() {
    }

    public Employee(String name, int id, String gender, double salary) {
        this.name = name;
        this.id = id;
        this.gender = gender;
        this.salary = salary;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getGender() {
        return gender;
    }

    public void setGender(String gender) {
        this.gender = gender;
    }

    public double getSalary() {
        return salary;
    }

    public void setSalary(double salary) {
        this.salary = salary;
    }
}
