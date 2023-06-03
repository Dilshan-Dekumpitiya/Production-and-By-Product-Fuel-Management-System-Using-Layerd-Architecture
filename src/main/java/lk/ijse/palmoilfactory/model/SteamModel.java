package lk.ijse.palmoilfactory.model;

import lk.ijse.palmoilfactory.util.CrudUtil;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class SteamModel {
    public static List<String> getStockIDs() throws SQLException, ClassNotFoundException {
        List<String> ids = new ArrayList<>();

        String sql = "SELECT stockId FROM ffbstock";

        ResultSet resultSet= CrudUtil.execute(sql);

        while(resultSet.next()) {
            ids.add(resultSet.getString(1));
        }
        return ids;

    }


    public static boolean addSteam(String stockId, double fruitOutput, double emptyBunchoutput, String date, String time) throws SQLException, ClassNotFoundException {
        String sql="INSERT INTO steam(stockId,fruit,emptyBunch,date,time) VALUES(?,?,?,?,?)";

        return CrudUtil.execute(sql, stockId, fruitOutput, emptyBunchoutput, date,time);

    }
}
