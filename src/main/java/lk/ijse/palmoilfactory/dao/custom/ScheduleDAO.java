package lk.ijse.palmoilfactory.dao.custom;

import lk.ijse.palmoilfactory.dao.CrudDAO;
import lk.ijse.palmoilfactory.entity.Schedule;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public interface ScheduleDAO extends CrudDAO<Schedule> {
    public String searchBySchIdTimeRange(String schId) throws SQLException, ClassNotFoundException;
    public String searchByTimeRangeSchId(String timeRange) throws SQLException, ClassNotFoundException;
    public ArrayList<String> getTimeRange() throws SQLException, ClassNotFoundException;
    public ArrayList<String> getScheduleIds() throws SQLException, ClassNotFoundException;
}
