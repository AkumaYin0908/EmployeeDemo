/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package model;

/**
 *
 * @author Philip
 */
public class Salary {
    
    private int salaryGrade;
    private double salary;

    public Salary() {
    }

    public Salary(int salaryGrade, double salary) {
        this.salaryGrade = salaryGrade;
        this.salary = salary;
    }

    public int getSalaryGrade() {
        return salaryGrade;
    }

    public void setSalaryGrade(int salaryGrade) {
        this.salaryGrade = salaryGrade;
    }

    public double getSalary() {
        return salary;
    }

    public void setSalary(double salary) {
        this.salary = salary;
    }
    
    
    
}
