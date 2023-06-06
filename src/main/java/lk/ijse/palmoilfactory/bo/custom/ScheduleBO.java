package lk.ijse.palmoilfactory.bo.custom;

import lk.ijse.palmoilfactory.bo.SuperBO;

import java.sql.SQLException;
import java.util.ArrayList;

public interface ScheduleBO extends SuperBO {
    public String searchBySchIdTimeRange(String schId) throws SQLException, ClassNotFoundException;
    public String searchByTimeRangeSchId(String timeRange) throws SQLException, ClassNotFoundException;
    public ArrayList<String> getTimeRange() throws SQLException, ClassNotFoundException;
    public ArrayList<String> getScheduleIds() throws SQLException, ClassNotFoundException;
}
