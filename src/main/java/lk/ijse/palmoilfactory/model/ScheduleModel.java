package lk.ijse.palmoilfactory.model;

import lk.ijse.palmoilfactory.util.CrudUtil;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class ScheduleModel {
    public static String searchBySchIdTimeRange(String schId) throws SQLException, ClassNotFoundException {
        String sql="SELECT timeRange from schedule WHERE schId = ? ";

        ResultSet resultSet= CrudUtil.execute(sql,schId);

        if(resultSet.next()){
            return resultSet.getString("timeRange");
        }
        return null;
    }

    public static String searchByTimeRangeSchId(String timeRange) throws SQLException, ClassNotFoundException {
        String sql="SELECT schId from schedule WHERE timeRange = ? ";

        ResultSet resultSet= CrudUtil.execute(sql,timeRange);

        if(resultSet.next()){
            return resultSet.getString("schId");
        }
        return null;
    }

    public static List<String> getTimeRange() throws SQLException, ClassNotFoundException {
        List<String> timeRanges = new ArrayList<>();

        String sql = "SELECT timeRange FROM schedule";

        ResultSet resultSet=CrudUtil.execute(sql);

        while(resultSet.next()) {
            timeRanges.add(resultSet.getString(1));
        }
        return timeRanges;

    }

    public static List<String> getScheduleIds() throws SQLException, ClassNotFoundException {
        List<String> schIds = new ArrayList<>();

        String sql = "SELECT schId FROM schedule";

        ResultSet resultSet=CrudUtil.execute(sql);

        while(resultSet.next()) {
            schIds.add(resultSet.getString(1));
        }
        return schIds;

    }
}
