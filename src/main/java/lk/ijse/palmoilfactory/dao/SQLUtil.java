package lk.ijse.palmoilfactory.dao;

import lk.ijse.palmoilfactory.db.DBConnection;

import java.sql.PreparedStatement;
import java.sql.SQLException;

public class SQLUtil {
    //T --> Type --> boolean,result set (Return type)
    //Object... params --> var args
    public static <T>T execute(String sql, Object... params) throws SQLException, ClassNotFoundException { //Object --> Customer details,Item details,...
        PreparedStatement statement = DBConnection.getInstance().getConnection().prepareStatement(sql);

        for (int i = 0; i < params.length; i++) { //set objects(Customer details,Item details,...) to statements
            statement.setObject((i+1), params[i]);
        }

        if(sql.startsWith("SELECT") || sql.startsWith("select")) {
            return (T) statement.executeQuery(); // ResultSet
        }
        return (T) (Boolean)(statement.executeUpdate() > 0); //Boolean
    }
}

