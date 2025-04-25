package HospitalManagementSystem;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Scanner;

public class Patient {
    private Connection connection;
    private Scanner scanner;

    public Patient (Connection connection , Scanner scanner){
        this.connection = connection;
        this.scanner = scanner;

    }
   public void addPatient(){
       System.out.println("entert patient name: ");
       String name  = scanner.next();
       System.out.println("enter patient age: ");
       int age  = scanner.nextInt();
       System.out.println("enter patient gender: ");
       String gender  = scanner.next();


    try {
        String query = "INSERT INTO patients(name, age, gender) VALUES (? ,?, ?)";
        PreparedStatement   preparedStatement = connection.prepareStatement(query);
        preparedStatement.setString(1,name );
        preparedStatement.setInt(2,age );
        preparedStatement.setString(3,gender );
        int aafftectedRows = preparedStatement.executeUpdate();
        if (aafftectedRows >0){
            System.out.println("data inserted succesfully");
        }
        else {
            System.out.println("failed");
        }
    }
    catch (SQLException e) {
        e.printStackTrace();
    }

   }

   public void viewPatients() {
        String query = "select * from patients";

        try {
            PreparedStatement   preparedStatement = connection.prepareStatement(query);
            ResultSet resultset = preparedStatement.executeQuery();
            System.out.println("patients: ");
            System.out.println("+-------------+--------------+------+--------+");
            System.out.println("|  Patient_id | name         | age  | gender |");
            System.out.println("+-------------+--------------+------+--------+");
            while(resultset.next()){
                int id = resultset.getInt("id");
                String name = resultset.getString("name");
                int age = resultset.getInt("age");
                String gender = resultset.getString("gender");
                System.out.printf("|%-13s|%-14s|%-6s|%-8s|\n",id, name, age, gender);
                System.out.println("+-------------+--------------+------+--------+");
            }
        }
        catch (SQLException e) {
            e.printStackTrace();
        }
   }
   public boolean getPatientById(int id) {
       String query = "select * from patients where id = ?";

       try {
           PreparedStatement   preparedStatement = connection.prepareStatement(query);
           preparedStatement.setInt(1,id );
           ResultSet resultset = preparedStatement.executeQuery();
          if(resultset.next()){
              return true;
           }
          else {
              return false;
          }
       } catch (SQLException e) {
           e.printStackTrace();
       }
       return false;
   }
}
