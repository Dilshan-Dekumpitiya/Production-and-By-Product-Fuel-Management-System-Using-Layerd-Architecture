package lk.ijse.palmoilfactory.bo.custom.impl;

import lk.ijse.palmoilfactory.bo.custom.ScheduleBO;
import lk.ijse.palmoilfactory.dao.DAOFactory;
import lk.ijse.palmoilfactory.dao.custom.ScheduleDAO;

import java.sql.SQLException;
import java.util.ArrayList;

public class ScheduleBOImpl implements ScheduleBO {
    private ScheduleDAO scheduleDAO=DAOFactory.getInstance().getDAO(DAOFactory.DAOTypes.SCHEDULE);
    @Override
    public String searchBySchIdTimeRange(String schId) throws SQLException, ClassNotFoundException {
        return scheduleDAO.searchBySchIdTimeRange(schId);
    }

    @Override
    public String searchByTimeRangeSchId(String timeRange) throws SQLException, ClassNotFoundException {
        return scheduleDAO.searchByTimeRangeSchId(timeRange);
    }

    @Override
    public ArrayList<String> getTimeRange() throws SQLException, ClassNotFoundException {
        return scheduleDAO.getTimeRange();
    }

    @Override
    public ArrayList<String> getScheduleIds() throws SQLException, ClassNotFoundException {
        return scheduleDAO.getScheduleIds();
    }
}
