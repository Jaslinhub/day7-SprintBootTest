package org.example.springboottest.Entity;

import jakarta.persistence.*;

@Entity
@Table(name = "t_employee")
public class Employee {

    private String name;
    private int age;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;
    private String gender;
    private double salary;
    private boolean status;

    @Column(name = "company_id")
    private int companyId;

    public int getCompanyId() {
        return companyId;
    }

    public void setCompanyId(int companyId) {
        this.companyId = companyId;
    }
    public Employee() {
    }

    public int getAge() {
        return age;
    }

    public void setAge(int age) {
        this.age = age;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public Employee(String name, String gender, double salary) {
        this.name = name;
        this.gender = gender;
        this.salary = salary;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
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

    public void setStatus(boolean status) {
        this.status = status;
    }
    public boolean getStatus() {
        return status;
    }
}
