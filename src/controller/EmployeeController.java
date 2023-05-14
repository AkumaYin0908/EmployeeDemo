/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package controller;

import connection.DBConnection;
import java.sql.Date;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.text.DecimalFormat;
import java.time.LocalDate;
import model.Employee;
import model.Position;
import model.Salary;

/**
 *
 * @author Philip
 */
import java.time.format.DateTimeFormatter;
import java.util.Locale;
import java.util.Scanner;
import view.MainForm;

public class EmployeeController {

    private DBConnection connection;
    private ResultSet result;
    private PreparedStatement preparedStatement;
    private Scanner scanner;
    private Employee employee;
    private Position position;
    private Salary salary;

    DateTimeFormatter formatter = DateTimeFormatter.ofPattern("MMMM dd, yyyy");
    DecimalFormat df = new DecimalFormat("###,###.00");

    public EmployeeController(DBConnection connection) {
        this.connection = connection;
        this.connection.connectDatabase();
    }

    public void viewEmployee() {
        employee = new Employee();
        position = new Position();
        salary = new Salary();

        System.out.println("*******************************View Employee*************************");
        try {
            preparedStatement = connection.getSqlConnection().prepareStatement("select * from tblemployee left join tblposition"
                    + " on tblemployee.position_id=tblposition.position_id"
                    + " left join tblsalary on tblposition.salary_grade=tblsalary.salary_grade");

            result = preparedStatement.executeQuery();
            System.out.printf("%s\t%-20s\t%-20s\t%-20s\t%-40s\t%-20s\t%-20s%n", "ID", "Name", "BirtDate", "Age", "Position", "Salary", "Employment Status");
            while (result.next()) {
                employee.setEmployeeID(result.getInt("employee_id"));
                employee.setName(result.getString("name"));
                employee.setBirthDate(result.getDate("birth_date").toLocalDate());
                position.setPosition(result.getString("position"));
                salary.setSalary(result.getDouble("salary"));
                employee.setEmployed(result.getBoolean("is_employed"));

                System.out.printf("%d\t%-20s\t%-20s\t%-20d\t%-40s\t%-20s\t%-20s%n",
                        employee.getEmployeeID(),
                        employee.getName(),
                        formatter.format(employee.getBirthDate()),
                        employee.getAge(),
                        position.getPosition(),
                        df.format(salary.getSalary()),
                        employee.isEmployed() ? "Employed" : "Not Employed");
            }
            System.out.println("***************************************************************");
            MainForm.init();

        } catch (SQLException ex) {
            System.err.println(ex);
            MainForm.init();
        } finally {
            try {
                connection.getSqlConnection().close();
            } catch (SQLException ex) {
                System.err.println(ex);
                MainForm.init();
            }
        }

    }

    public void addEmployee() {
        employee = new Employee();
        System.out.println("*******************************Add Employee*************************");

        scanner = new Scanner(System.in);

        System.out.println("Enter name: ");
        employee.setName(scanner.nextLine());

        System.out.println("Enter BirthDate(MM/dd/yyyy)");
        employee.setBirthDate(LocalDate.parse(scanner.nextLine(), DateTimeFormatter.ofPattern("MM/dd/yyyy")));
        
        viewPosition();

        System.out.println("Enter position id: ");
        employee.setPosition(scanner.nextInt());
        try {

            preparedStatement = connection.getSqlConnection().prepareStatement("insert into tblemployee(name,birth_date,position_id) "
                    + "values(?,?,?)");

            preparedStatement.setString(1, employee.getName());
            preparedStatement.setDate(2, Date.valueOf(employee.getBirthDate()));
            preparedStatement.setInt(3, employee.getPosition());

            preparedStatement.executeUpdate();

            System.out.println(employee.getName() + " has been successfully inserted to the database.");

            System.out.println("***************************************************************");
            MainForm.init();

        } catch (SQLException ex) {
            System.err.println(ex);
            MainForm.init();
        } finally {
            scanner.close();
            try {
                connection.getSqlConnection().close();
            } catch (SQLException ex) {
                System.err.println(ex);
                MainForm.init();
            }
        }

    }

    public void updateEmployee() {
        employee = new Employee();
        scanner = new Scanner(System.in);

        System.out.println("Enter employee ID to update:");
        employee.setEmployeeID(scanner.nextInt());
        try {
            preparedStatement = connection.getSqlConnection().prepareStatement("select * from tblemployee where employee_id=?");
            preparedStatement.setInt(1, employee.getEmployeeID());
            result = preparedStatement.executeQuery();

            if (result.next()) {
                System.out.println("Choose field to update: \n"
                        + "[1]Name\n"
                        + "[2]Birthdate\n"
                        + "[3]Positon ID\n"
                        + "[4]Cancel");
                int choice = scanner.nextInt();

                switch (choice) {
                    case 1:
                        updateName(employee.getEmployeeID());
                        break;
                    case 2:
                        updateBirthDate(employee.getEmployeeID());
                        break;
                    case 3:
                        updatePositionID(employee.getEmployeeID());
                        break;
                    case 4:
                        MainForm.init();
                        break;
                    default:
                        System.out.println("Invalid input");
                        updateEmployee();

                }

            } else {
                System.out.println("No Employee with this ID exists");
                MainForm.init();
            }

        } catch (SQLException ex) {
            System.err.println(ex);
            MainForm.init();
        } finally {
            scanner.close();
            try {
                connection.getSqlConnection().close();
            } catch (SQLException ex) {
                System.err.println(ex);
                MainForm.init();
            }
        }

    }

    public void deleteEmployee() {
        employee = new Employee();

        scanner = new Scanner(System.in);

        System.out.println("Enter Employee ID of the employee to delete: ");
        employee.setEmployeeID(scanner.nextInt());

        try {
            preparedStatement = connection.getSqlConnection().prepareStatement("select * from tblemployee where employee_id=?");
            preparedStatement.setInt(1, employee.getEmployeeID());
            result = preparedStatement.executeQuery();

            if (result.next()) {
                System.out.println("Are you sure you want to delete this employee?\n[1]Yes\n[2]No");
                int choice = scanner.nextInt();
                if (choice == 1) {
                    preparedStatement = connection.getSqlConnection().prepareStatement("delete from tblemployee where employee_id=?");
                    preparedStatement.setInt(1, employee.getEmployeeID());
                    preparedStatement.executeUpdate();

                    System.out.println("Employee with Employee ID No " + employee.getEmployeeID() + " has been deleted.");
                    System.out.println("***************************************************************");
                    MainForm.init();

                } else {
                    MainForm.init();
                }

            } else {
                System.out.println("Employee with this ID does not exists");
                MainForm.init();
            }
        } catch (SQLException ex) {
            System.err.println(ex);
        } finally {
            scanner.close();
            try {
                connection.getSqlConnection().close();
            } catch (SQLException ex) {
                System.err.println(ex);
                MainForm.init();
            }
        }
    }

    private void updateName(int employeeID) {
        scanner = new Scanner(System.in);
        System.out.println("Enter new name:");
        employee.setName(scanner.nextLine());
        try {

            preparedStatement = connection.getSqlConnection().prepareStatement("update tblemployee set name=? where employee_id=?");
            preparedStatement.setString(1, employee.getName());
            preparedStatement.setInt(2, employeeID);
            preparedStatement.executeUpdate();

            System.out.println("Name has been updated for Employee ID: " + employeeID);
            System.out.println("***************************************************************");
            MainForm.init();
        } catch (SQLException ex) {
            System.err.println(ex);
            MainForm.init();
        } finally {
            scanner.close();
            try {
                connection.getSqlConnection().close();
            } catch (SQLException ex) {
                System.err.println(ex);
                MainForm.init();
            }
        }

    }

    private void updateBirthDate(int employeeID) {
        scanner = new Scanner(System.in);
        System.out.println("Enter new birth date(MM/dd/yyyy):");
        employee.setBirthDate(LocalDate.parse(scanner.nextLine(), DateTimeFormatter.ofPattern("MM/dd/yyyy")));
        try {
            preparedStatement = connection.getSqlConnection().prepareStatement("update tblemployee set birth_date=? where employee_id=?");
            preparedStatement.setDate(1, Date.valueOf(employee.getBirthDate()));
            preparedStatement.setInt(2, employeeID);
            preparedStatement.executeUpdate();

            System.out.println("Birth date has been updated for Employee ID: " + employeeID);
            System.out.println("***************************************************************");
            MainForm.init();
        } catch (SQLException ex) {
            System.err.println(ex);
            MainForm.init();
        } finally {
            scanner.close();
            try {
                connection.getSqlConnection().close();
            } catch (SQLException ex) {
                System.err.println(ex);
                MainForm.init();
            }
        }

    }

    private void updatePositionID(int employeeID) {
        viewPosition();
        scanner = new Scanner(System.in);
        System.out.println("Enter new position ID:");
        employee.setPosition(scanner.nextInt());
        try {
            preparedStatement = connection.getSqlConnection().prepareStatement("update tblemployee set position_id=? where employee_id=?");
            preparedStatement.setInt(1, employee.getPosition());
            preparedStatement.setInt(2, employeeID);
            preparedStatement.executeUpdate();

            System.out.println("Position has been updated for Employee ID: " + employeeID);
            System.out.println("***************************************************************");

            MainForm.init();
        } catch (SQLException ex) {
            System.err.println(ex);
            MainForm.init();
        } finally {
            scanner.close();
            try {
                connection.getSqlConnection().close();
            } catch (SQLException ex) {
                System.err.println(ex);
                MainForm.init();
            }
        }
    }

    private void viewPosition() {
        Position position = new Position();
        Salary salary = new Salary();

        try {

            preparedStatement = connection.getSqlConnection().prepareStatement("select * from tblposition"
                    + " left join tblsalary on tblposition.salary_grade=tblsalary.salary_grade");

            result = preparedStatement.executeQuery();

            System.out.printf("%-10s\t%-40s\t%10s%n", "Position ID", " Position", "Salary");
            while (result.next()) {
                position.setPositionID(result.getInt("position_id"));
                position.setPosition(result.getString("position"));
                salary.setSalary(result.getDouble("salary"));

                System.out.printf("%-10s\t%-40s\t%10s%n", position.getPositionID(), position.getPosition(), df.format(salary.getSalary()));
            }
            System.out.println("***************************************************************");

        } catch (SQLException ex) {
            System.err.println(ex);
            MainForm.init();
        } 
        
    }

}
