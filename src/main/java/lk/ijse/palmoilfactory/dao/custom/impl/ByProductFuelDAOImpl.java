package lk.ijse.palmoilfactory.dao.custom.impl;

import lk.ijse.palmoilfactory.dao.SQLUtil;
import lk.ijse.palmoilfactory.dao.custom.ByProductionFuelDAO;
import lk.ijse.palmoilfactory.entity.ByProductFuel;

import java.sql.SQLException;
import java.util.ArrayList;

public class ByProductFuelDAOImpl implements ByProductionFuelDAO {
    @Override
    public ArrayList<ByProductFuel> getAll() throws SQLException, ClassNotFoundException {
        throw new UnsupportedOperationException("This feature is not implemented yet");
    }

    @Override
    public boolean add(ByProductFuel entity) throws SQLException, ClassNotFoundException {
        String sql="INSERT INTO byproductfuel(stockId,bunchFiber,shell,pressFiber,date,time) VALUES(?,?,?,?,?,?)";

        return SQLUtil.execute(sql,entity.getStockId(),entity.getBunchFiber(),entity.getShell(),entity.getPressFiber(),entity.getDate(),entity.getTime());


    }

    @Override
    public boolean update(ByProductFuel entity) throws SQLException, ClassNotFoundException {
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
    public ByProductFuel search(String id) throws SQLException, ClassNotFoundException {
        throw new UnsupportedOperationException("This feature is not implemented yet");
    }

}
