/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package DAO;

import Model.Account;
import Model.Users;
import java.sql.Connection;
import java.sql.Date;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.List;

/**
 *
 * @author DELL
 */
public class UserDAO extends DBConnect {

//    public static boolean isEmailAlreadyInUse(String email) {
//        throw new UnsupportedOperationException("Not supported yet."); // Generated from nbfs://nbhost/SystemFileSystem/Templates/Classes/Code/GeneratedMethodBody
//    }

    public UserDAO() {
        super();
    }

    public Account getAccountByUsername(String username, String password) {
        Account a = null;
        PreparedStatement stm = null;
        ResultSet rs = null;

        try {
            Connection conn = this.getConnection();
            if (conn == null) {
                throw new SQLException("Database connection is null");
            }

            String strSql = "SELECT * FROM isp392_3.account WHERE Username = ? AND Password = ?";
            stm = conn.prepareStatement(strSql);
            stm.setString(1, username);
            stm.setString(2, password);
            rs = stm.executeQuery();

            if (rs.next()) {
                String ID = rs.getString(1);
                String acc = rs.getString(2);
                String pass = rs.getString(3);
                String type = rs.getString(4);
                String userInfoId = rs.getString(5);
                a = new Account(ID, acc, pass, type, userInfoId);
                return a;
            }
        } catch (SQLException e) {
            System.out.println("getUsers: " + e.getMessage());
        }

        return null;
    }

     public List<Users> getAllUserWithParam(String searchParam) {
        List<Users> users = new ArrayList<>();
        List<Object> list = new ArrayList<>();
        try {
            StringBuilder query = new StringBuilder();
            query.append("SELECT * FROM isp392_3.user where 1=1");
            if (searchParam != null && !searchParam.trim().isEmpty()) {
                query.append(" AND (CONCAT(firstname, ' ', lastname) LIKE ? OR email LIKE ?)");
                list.add("%" + searchParam + "%");
                list.add("%" + searchParam + "%");
            }
            query.append(" ORDER BY id DESC");
            PreparedStatement preparedStatement;
            preparedStatement = cnn.prepareStatement(query.toString());
            mapParams(preparedStatement, list);
            try ( ResultSet resultSet = preparedStatement.executeQuery()) {
                while (resultSet.next()) {
                    Users user = new Users();
                    user.setUserId(resultSet.getInt("userid"));
                    user.setEmail(resultSet.getString("email"));
                    user.setPassword(resultSet.getString("password"));
                    user.setFirstName(resultSet.getString("firstName"));
                    user.setLastName(resultSet.getString("lastName"));
                    user.setRole(resultSet.getString("role"));
                    user.setDob(resultSet.getString("dob"));
                    user.setAddress(resultSet.getString("address"));
                    user.setPhone(resultSet.getString("phone")
                    );
                    users.add(user);
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return users;
    }



   public Users getUserById(int userId) throws SQLException {
        String query = "SELECT * FROM isp392_3.user WHERE user_id = ?";
        Users user;
        try (PreparedStatement statement = cnn.prepareStatement(query)) {
            statement.setInt(1, userId);
            ResultSet resultSet = statement.executeQuery();
            if (resultSet.next()) {
                user = new Users(
                        resultSet.getInt(1),
                        resultSet.getString(2),
                        resultSet.getString(3),
                        resultSet.getString(4),
                        resultSet.getString(5),
                        resultSet.getString(6),
                        resultSet.getString(7),
                         resultSet.getString(8),
                         resultSet.getString(9)                       
                       );
                  return  user;
            
            }
          

        }
        return null;
    }

    public boolean registerUser(Users newUser) {
        try {
            String query = "INSERT INTO Users (email,password,firstName, lastName,role, dob, address) VALUES (?, ?, ?,?, ?,?,?)";
            try ( PreparedStatement preparedStatement = cnn.prepareStatement(query)) {
                preparedStatement.setString(1, newUser.getEmail());
                preparedStatement.setString(2, newUser.getPassword());
                preparedStatement.setString(3, newUser.getFirstName());
                preparedStatement.setString(4, newUser.getLastName());
                preparedStatement.setString(5, newUser.getRole());
                preparedStatement.setString(7, newUser.getDob());
                preparedStatement.setString(8, newUser.getAddress());

                int rowsAffected = preparedStatement.executeUpdate();

                return rowsAffected > 0;
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return false;
    }

//    public boolean updateUser(Users newUser) {
//        try {
//            String query = "UPDATE users SET , role = ?, name = ? WHERE id = ?";
//            try ( PreparedStatement preparedStatement = connection.prepareStatement(query)) {
//                preparedStatement.setString(1, newUser.getEmail());
//                preparedStatement.setString(2, newUser.getPassword());
//                preparedStatement.setString(3, newUser.getFirstName());
//                preparedStatement.setString(4, newUser.getLastName());
//                preparedStatement.setInt(4, newUser.getId());
//
//                int rowsAffected = preparedStatement.executeUpdate();
//
//                return rowsAffected > 0;
//            }
//        } catch (SQLException e) {
//            e.printStackTrace();
//        }
//        return false;
//    }
    public boolean deleteUser(int id) {
        try {
            String query = "delete users WHERE id = ?";
            try ( PreparedStatement preparedStatement = cnn.prepareStatement(query)) {
                preparedStatement.setInt(1, id);

                int rowsAffected = preparedStatement.executeUpdate();

                return rowsAffected > 0;
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return false;
    }

    public boolean isEmailAlreadyInUse(String email) throws SQLException {
        String query = "SELECT COUNT(*) FROM Users WHERE email = ?";
        try ( PreparedStatement preparedStatement = cnn.prepareStatement(query)) {
            preparedStatement.setString(1, email);

            try ( ResultSet resultSet = preparedStatement.executeQuery()) {
                if (resultSet.next()) {
                    int count = resultSet.getInt(1);
                    return count > 0; // If count is greater than 0, email is already in use
                }
            }
        }
        return false;
    }

    
    public static void main(String[] args) {
        UserDAO d = new UserDAO();
        Account account = d.getAccountByUsername("manager", "123");
        System.out.println(account != null ? account.getAccountID() : "Account not found");
    }

   
    
    
}
