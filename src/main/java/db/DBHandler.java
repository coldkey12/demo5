package db;
import objects.CurrentUser;
import objects.User;

import java.sql.*;

public class DBHandler {
    Connection dbConnection;

    public Connection getDbConnection() throws ClassNotFoundException, SQLException {
        String connectionString = "jdbc:mysql://localhost:3306/demo5";
        Class.forName("com.mysql.cj.jdbc.Driver");
        dbConnection = DriverManager.getConnection(connectionString,"root","");
        return dbConnection;
    }
    public boolean login(String username, String password){
        boolean flag = false;
        String insert = "SELECT * FROM " + Const.demo5_users + " WHERE " + Const.demo5_username + " = ?" + " && " + Const.demo5_password + " = ?";
        try{
            PreparedStatement preparedStatement = getDbConnection().prepareStatement(insert);
            preparedStatement.setString(1,username);
            preparedStatement.setString(2,password);
            if(preparedStatement.executeQuery().next()){
                flag=true;
            }
        } catch (Exception exception){
            System.out.println(exception.getMessage());
        }
        return flag;
    }
    public User getUser(String username, String password) throws SQLException, ClassNotFoundException {
        ResultSet resultSet;
        String insert = "SELECT * FROM " + Const.demo5_users + " WHERE " + Const.demo5_username + " = ?" + " && " + Const.demo5_password + " = ?";
        PreparedStatement preparedStatement = getDbConnection().prepareStatement(insert);
        preparedStatement.setString(1,username);
        preparedStatement.setString(2,password);
        resultSet = preparedStatement.executeQuery();
        resultSet.next();
        User user = new User(resultSet.getInt(1),resultSet.getString(2),
                resultSet.getString(3),resultSet.getString(4),resultSet.getString(5),
                resultSet.getInt(6));
        return user;
    }

    public void registration(String username, String password, String first_name, String second_name, int gender) throws SQLException, ClassNotFoundException {
        String insert = "INSERT INTO " + Const.demo5_users + "(" + Const.demo5_username + "," + Const.demo5_password +
                "," + Const.demo5_first_name + "," + Const.demo5_second_name + "," + Const.demo5_gender + ")" + " VALUES(?,?,?,?,?)";
        PreparedStatement preparedStatement = getDbConnection().prepareStatement(insert);
        preparedStatement.setString(1,username);
        preparedStatement.setString(2,password);
        preparedStatement.setString(3,first_name);
        preparedStatement.setString(4,second_name);
        preparedStatement.setInt(5,gender);
        preparedStatement.executeUpdate();
    }
    public boolean checkExistance(String username) throws SQLException, ClassNotFoundException {
        boolean flag = false;
        String insert = "SELECT * FROM " + Const.demo5_users + " WHERE " + Const.demo5_username + " =?";
        PreparedStatement preparedStatement = getDbConnection().prepareStatement(insert);
        preparedStatement.setString(1,username);
        if(preparedStatement.executeQuery().next()){
            flag=true;
        }
        return flag;
    }
    public void addTask(String taskName, String taskDesc, int user_id) throws SQLException, ClassNotFoundException {
        String insert = String.format("insert into %s(%s,%s,%s,%s) values (?,?,?,?)",Const.demo5_tasks,Const.tasks_name,Const.tasks_task_desc,Const.tasks_status,Const.tasks_user_id);
        PreparedStatement preparedStatement = getDbConnection().prepareStatement(insert);
        preparedStatement.setString(1,taskName);
        preparedStatement.setString(2,taskDesc);
        preparedStatement.setString(3,"To-Do");
        preparedStatement.setInt(4,user_id);
        preparedStatement.executeUpdate();
    }
    public ResultSet idByUsername(String username){
        ResultSet resultSet = null;
        try{
            String insert = String.format("select %s from %s where %s = ?", Const.demo5_id,Const.demo5_users,Const.demo5_username);
            PreparedStatement preparedStatement = getDbConnection().prepareStatement(insert);
            preparedStatement.setString(1,username);
            resultSet = preparedStatement.executeQuery();
        } catch (Exception exception){
            System.out.println(exception.getMessage());
        }
        return resultSet;
    }
    public ResultSet myTasks(){
        ResultSet resultSet = null;
        try{
            String insert = "SELECT " + Const.tasks_name + " FROM " + Const.demo5_tasks + " WHERE " + Const.tasks_user_id + " =?";
            PreparedStatement preparedStatement = getDbConnection().prepareStatement(insert);
            preparedStatement.setInt(1,CurrentUser.user_id);
            resultSet = preparedStatement.executeQuery();
        } catch (Exception exception){
            System.out.println(exception.getMessage());
        }
        return resultSet;
    }
    public ResultSet returnByTaskName(String task_name) {
        ResultSet resultSet = null;
        try {
            String insert = "SELECT " + Const.tasks_task_desc + "," + Const.tasks_status + " FROM " + Const.demo5_tasks + " WHERE " + Const.tasks_name + " =?";
            PreparedStatement preparedStatement = getDbConnection().prepareStatement(insert);
            preparedStatement.setString(1,task_name);
            resultSet = preparedStatement.executeQuery();
        } catch (Exception exception){
            System.out.println(exception.getMessage());
        }
        return resultSet;
    }
}
