/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package DAO;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class DBConnect {

    protected Connection cnn;

    public DBConnect() {
        try {
            // Đăng ký driver
            Class.forName("com.mysql.cj.jdbc.Driver");

            // Thông tin kết nối MySQL
            String url = "jdbc:mysql://localhost:3306/isp392_3";
            String user = "root";
            String password = "123456789";

            // Kết nối tới MySQL
            cnn = DriverManager.getConnection(url, user, password);
            System.out.println("success");
        } catch (ClassNotFoundException e) {
            System.out.println("Driver not found: " + e.getMessage());
        } catch (SQLException e) {
            System.out.println("Connection failed: " + e.getMessage());
        }
    }

    // Phương thức để kiểm tra kết nối có null hay không
    public Connection getConnection() {
        return cnn;
    }

}