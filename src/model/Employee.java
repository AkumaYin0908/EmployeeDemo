/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package model;

import java.time.LocalDate;
import java.time.Period;

/**
 *
 * @author Philip
 */
public class Employee {
    private int employeeID;
    private String name;
    private LocalDate birthDate;
    private int position;
    private boolean employed;
    
    public Employee(){}

    public Employee(int employeeID, String name, LocalDate birthDate, int position, boolean employed) {
        this.employeeID = employeeID;
        this.name = name;
        this.birthDate = birthDate;
        this.position = position;
        this.employed = employed;
    }

    public int getEmployeeID() {
        return employeeID;
    }

    public void setEmployeeID(int employeeID) {
        this.employeeID = employeeID;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public LocalDate getBirthDate() {
        return birthDate;
    }

    public void setBirthDate(LocalDate birthDate) {
        this.birthDate = birthDate;
    }

    public int getPosition() {
        return position;
    }

    public void setPosition(int position) {
        this.position = position;
    }

    public boolean isEmployed() {
        return employed;
    }

    public void setEmployed(boolean employed) {
        this.employed = employed;
    }
    
    public int getAge(){
        Period period =Period.between(birthDate, LocalDate.now());
        return period.getYears();
    }
    
    
    
}
