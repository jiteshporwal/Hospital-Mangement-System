package HospitalManagementSystem;

import java.sql.*;
import java.util.Scanner;

public class HospitalManagementSystem {
    private static final String url = "jdbc:mysql://127.0.0.1:3306/hospital";
    private static final String username = "root";
    private static final String password = "JITESH1810";

    public static void main(String[] args) {
        try {
            Class.forName("com.mysql.cj.jdbc.Driver");
        } catch (ClassNotFoundException e) {
            e.printStackTrace();

        }
        Scanner scanner = new Scanner(System.in);
        try (Connection connection = DriverManager.getConnection(url, username, password)) {
            Patient patient = new Patient(connection, scanner);
            Doctor doctor = new Doctor(connection);
            while (true) {
                System.out.println("hospital management System");
                System.out.println("1 . add patient");
                System.out.println("2 . view patient");
                System.out.println("3 . view doctors");
                System.out.println("4 . book appointment");
                System.out.println("5 . exit");
                System.out.println("enter your choice ");
                int choice = scanner.nextInt();
                switch (choice) {
                    case 1:
                        //
                        patient.addPatient();
                        System.out.println();
                        break;
                    case 2:
                        //
                        patient.viewPatients();
                        System.out.println();
                        break;
                    case 3:
                        //
                        doctor.viewDoctors();
                        System.out.println();
                        break;
                    case 4:
                        //
                        bookAppointment(patient , doctor , connection , scanner);
                        System.out.println();
                        break;

                    case 5:
                        return;

                    default:
                        System.out.println("enter a valid choice !!!");
                }

            }

            // System.out.println("Connected to the database.");

// Perform database operations here
        } catch (SQLException e) {
            System.err.println("Connection failed: " + e.getMessage());

        }
    }

    public static void bookAppointment(Patient patient, Doctor doctor, Connection connection, Scanner scanner) {
        System.out.println("Enter patient id: ");
        int patient_id = scanner.nextInt();

        System.out.println("Enter doctor id: ");
        int doctor_id = scanner.nextInt();

        System.out.print("Enter Appointment  date(yyyy-mm-dd): ");
        String appointmentDate = scanner.next();

        if (patient.getPatientById(patient_id) && doctor.getDoctorById(doctor_id)) {

            if (checkDoctorAvailability(doctor_id, appointmentDate , connection)) {
                String appointmentQuery = "INSERT INTO appointments(patient_id , doctors_id , appointment_date) VALUES (? , ? , ?)";
                try {
                    PreparedStatement preparedStatement = connection.prepareStatement(appointmentQuery);
                    preparedStatement.setInt(1, patient_id);
                    preparedStatement.setInt(2, doctor_id);
                    preparedStatement.setString(3, appointmentDate);


                    int rowsAffected = preparedStatement.executeUpdate();
                    if (rowsAffected > 0) {
                        System.out.println("Appointment booked successfully!");
                    } else {
                        System.out.println("Failed to book appointment.");
                    }

                } catch (SQLException e) {
                    e.printStackTrace();
                }
            }
             else{
                    System.out.println("doctor not found");
                }

            } else {
                System.out.print("Enter doctor  or  patient  doesnt exist!!!");
            }


        }
        public static boolean checkDoctorAvailability(int doctor_id ,String appointmentDate , Connection connection) {
            String query = "SELECT COUNT(*) FROM appointments WHERE doctors_id = ? AND appointment_date = ?";
            try {
                PreparedStatement preparedStatement = connection.prepareStatement(query);
                preparedStatement.setInt(1, doctor_id);
                preparedStatement.setString(2, appointmentDate);
                ResultSet resultSet = preparedStatement.executeQuery();
                if (resultSet.next()) {
                    int count = resultSet.getInt(1);
                    if (count == 0) {
                        return true;
                    } else {
                        return true;
                    }
                }
            } catch (SQLException e) {
                e.printStackTrace();
            }

            return false;

        }
    }


