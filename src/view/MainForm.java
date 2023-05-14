/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package view;

import connection.DBConnection;
import controller.EmployeeController;
import java.util.InputMismatchException;
import java.util.Scanner;

/**
 *
 * @author Philip
 */
public class MainForm {

    public static void init() {
        DBConnection connection = new DBConnection();
        EmployeeController controller = new EmployeeController(connection);
        Scanner scanner = new Scanner(System.in);
        
        System.out.println("EMPLOYEE MANAGEMENT DEMO SYSTEM V 1.0.0");
        System.out.println("[1]View Employee");
        System.out.println("[2]Add Employee");
        System.out.println("[3]Update Employee");
        System.out.println("[4]Delete Employee");
        System.out.println("[0]Exit");
        try {

            int choice = scanner.nextInt();

            switch (choice) {
                case 1:
                    controller.viewEmployee();
                    
                    break;
                case 2:
                    controller.addEmployee();
                  
                    break;
                case 3:
                    controller.updateEmployee();
                    
                    break;
                case 4:
                    controller.deleteEmployee();
                    
                    break;
                case 0:
                    System.exit(0);
                    break;
                default:
                    System.out.println("Invalid Input");
                    init();

            }
        } catch (InputMismatchException ex) {
            System.err.println(ex);
            init();
        } finally {
            scanner.close();

        }

    }

}
