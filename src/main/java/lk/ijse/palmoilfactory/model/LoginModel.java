package lk.ijse.palmoilfactory.model;

import lk.ijse.palmoilfactory.dao.SQLUtil;

import java.sql.ResultSet;
import java.sql.SQLException;

public class LoginModel {
    public static boolean userCheckedInDB(String username, String password) throws SQLException, ClassNotFoundException {
        String sql = "SELECT * FROM user WHERE name= ? AND password=?";
        ResultSet resultSet = SQLUtil.execute(sql, username, password);
        if(resultSet.next()){
            return true;
        }
        return false;
    }
}
