package lk.ijse.palmoilfactory.dao.custom.impl;

import lk.ijse.palmoilfactory.dao.SQLUtil;
import lk.ijse.palmoilfactory.dao.custom.SteamDAO;
import lk.ijse.palmoilfactory.entity.Steam;

import java.sql.SQLException;
import java.util.ArrayList;

public class SteamDAOImpl implements SteamDAO {

    @Override
    public ArrayList<Steam> getAll() throws SQLException, ClassNotFoundException {
        throw new UnsupportedOperationException("This feature is not implemented yet");
    }

    @Override
    public boolean add(Steam entity) throws SQLException, ClassNotFoundException {
        String sql="INSERT INTO steam(stockId,fruit,emptyBunch,date,time) VALUES(?,?,?,?,?)";

        return SQLUtil.execute(sql, entity.getStockId(), entity.getFruitOutput(), entity.getEmptyBunchoutput(), entity.getDate(),entity.getTime());

    }

    @Override
    public boolean update(Steam entity) throws SQLException, ClassNotFoundException {
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
    public Steam search(String id) throws SQLException, ClassNotFoundException {
        throw new UnsupportedOperationException("This feature is not implemented yet");
    }
}
