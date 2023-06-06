package lk.ijse.palmoilfactory.dao.custom.impl;

import lk.ijse.palmoilfactory.dao.SQLUtil;
import lk.ijse.palmoilfactory.dao.custom.OilProductionDAO;
import lk.ijse.palmoilfactory.dto.OilProductionDTO;
import lk.ijse.palmoilfactory.entity.OilProduction;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

public class OilProductionDAOImpl implements OilProductionDAO {
    @Override
    public ArrayList<OilProduction> getAll() throws SQLException, ClassNotFoundException {
        throw new UnsupportedOperationException("This feature is not implemented yet");
    }

    @Override
    public boolean add(OilProduction entity) throws SQLException, ClassNotFoundException {
        String sql="INSERT INTO oilproduction(stockId,totalEBLiquid,totalPressLiquid,date,time) VALUES(?,?,?,?,?)";

        return SQLUtil.execute(sql,entity.getStockID(),entity.getTotalEBLiquid(),entity.getTotalPressLiquid(),entity.getDate(),entity.getTime());


    }

    @Override
    public boolean update(OilProduction entity) throws SQLException, ClassNotFoundException {
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
    public OilProduction search(String id) throws SQLException, ClassNotFoundException {
        throw new UnsupportedOperationException("This feature is not implemented yet");
    }

    @Override
    public String getUpdatedOilqty() throws SQLException, ClassNotFoundException {
        String sql="SELECT totalQty from totaloilqty";
        ResultSet resultSet= SQLUtil.execute(sql);

        if(resultSet.next()) {
            return (resultSet.getString(1));
        }
        return "-1";
    }

    @Override
    public boolean subtractionOilQtyTototalOil(double qty) throws SQLException, ClassNotFoundException {
        String sql = "UPDATE totaloilqty SET totalQty = (totalQty - ?) ";

        return SQLUtil.execute(sql,qty);

    }

    @Override
    public boolean addOilQtyTototalOil(double qty) throws SQLException, ClassNotFoundException {
        String sql = "UPDATE totaloilqty SET totalQty = (totalQty + ?) ";

        return SQLUtil.execute(sql,qty);
    }

}
