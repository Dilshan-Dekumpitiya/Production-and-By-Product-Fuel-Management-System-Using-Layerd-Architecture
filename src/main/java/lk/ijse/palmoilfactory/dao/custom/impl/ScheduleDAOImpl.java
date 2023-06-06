package lk.ijse.palmoilfactory.dao.custom.impl;

import lk.ijse.palmoilfactory.dao.SQLUtil;
import lk.ijse.palmoilfactory.dao.custom.ScheduleDAO;
import lk.ijse.palmoilfactory.entity.Schedule;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class ScheduleDAOImpl implements ScheduleDAO {

    @Override
    public ArrayList<Schedule> getAll() throws SQLException, ClassNotFoundException {
        throw new UnsupportedOperationException("This feature is not implemented yet");
    }

    @Override
    public boolean add(Schedule entity) throws SQLException, ClassNotFoundException {
        throw new UnsupportedOperationException("This feature is not implemented yet");
    }

    @Override
    public boolean update(Schedule entity) throws SQLException, ClassNotFoundException {
        throw new UnsupportedOperationException("This feature is not implemented yet");
    }

    @Override
    public boolean exist(String id) throws SQLException, ClassNotFoundException {
        throw new UnsupportedOperationException("This feature is not implemented yet");
    }

    @Override
    public String generateNewID() throws SQLException, ClassNotFoundException {
        throw new UnsupportedOperationException("This feature is not implemented yet");
    }

    @Override
    public boolean delete(String id) throws SQLException, ClassNotFoundException {
        throw new UnsupportedOperationException("This feature is not implemented yet");
    }

    @Override
    public Schedule search(String id) throws SQLException, ClassNotFoundException {
        throw new UnsupportedOperationException("This feature is not implemented yet");
    }

    @Override
    public String searchBySchIdTimeRange(String schId) throws SQLException, ClassNotFoundException {
        String sql="SELECT timeRange from schedule WHERE schId = ? ";

        ResultSet resultSet= SQLUtil.execute(sql,schId);

        if(resultSet.next()){
            return resultSet.getString("timeRange");
        }
        return null;
    }

    @Override
    public String searchByTimeRangeSchId(String timeRange) throws SQLException, ClassNotFoundException {
        String sql="SELECT schId from schedule WHERE timeRange = ? ";

        ResultSet resultSet= SQLUtil.execute(sql,timeRange);

        if(resultSet.next()){
            return resultSet.getString("schId");
        }
        return null;
    }

    @Override
    public ArrayList<String> getTimeRange() throws SQLException, ClassNotFoundException {
        ArrayList<String> timeRanges = new ArrayList<>();

        String sql = "SELECT timeRange FROM schedule";

        ResultSet resultSet=SQLUtil.execute(sql);

        while(resultSet.next()) {
            timeRanges.add(resultSet.getString(1));
        }
        return timeRanges;
    }

    @Override
    public ArrayList<String> getScheduleIds() throws SQLException, ClassNotFoundException {
        ArrayList<String> schIds = new ArrayList<>();

        String sql = "SELECT schId FROM schedule";

        ResultSet resultSet=SQLUtil.execute(sql);

        while(resultSet.next()) {
            schIds.add(resultSet.getString(1));
        }
        return schIds;

    }
}
