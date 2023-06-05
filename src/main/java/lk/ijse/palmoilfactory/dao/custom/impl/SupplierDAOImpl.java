package lk.ijse.palmoilfactory.dao.custom.impl;

import lk.ijse.palmoilfactory.dao.SQLUtil;
import lk.ijse.palmoilfactory.dao.custom.SupplierDAO;
import lk.ijse.palmoilfactory.entity.Supplier;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class SupplierDAOImpl implements SupplierDAO {
    @Override
    public ArrayList<Supplier> getAll() throws SQLException, ClassNotFoundException {
        String sql = "SELECT * FROM supplier";

        ArrayList<Supplier> supData = new ArrayList<>();

        ResultSet resultSet = SQLUtil.execute(sql);
        while (resultSet.next()) {
            supData.add(new Supplier(
                    resultSet.getString(1),
                    resultSet.getString(2),
                    resultSet.getString(3),
                    resultSet.getString(4)
            ));
        }
        return supData;
    }

    @Override
    public boolean add(Supplier entity) throws SQLException, ClassNotFoundException {
        String sql="INSERT INTO supplier(supId,name,address,contact) VALUES(?,?,?,?)";

        return SQLUtil.execute(sql, entity.getSupId(), entity.getSupName(), entity.getSupAddress(), entity.getSupContact());
    }

    @Override
    public boolean update(Supplier entity) throws SQLException, ClassNotFoundException {
        String sql="UPDATE supplier SET name = ?, address = ?, contact = ? WHERE supId = ?";

        return SQLUtil.execute(sql,  entity.getSupName(), entity.getSupAddress(), entity.getSupContact(),entity.getSupId());
    }

    @Override
    public boolean exist(String id) throws SQLException, ClassNotFoundException {
        return false;
    }

    @Override
    public String generateNewID() throws SQLException, ClassNotFoundException {
        return null;
    }

    @Override
    public boolean delete(String id) throws SQLException, ClassNotFoundException {
        String sql="DELETE FROM supplier WHERE supId=? ";

        return SQLUtil.execute(sql,id);
    }

    @Override
    public Supplier search(String id) throws SQLException, ClassNotFoundException {
        String sql="SELECT * FROM supplier WHERE supId= ?";

        ResultSet resultSet = SQLUtil.execute(sql, id);

        if(resultSet.next()) {
            String  supId = resultSet.getString(1);
            String name = resultSet.getString(2);
            String address = resultSet.getString(3);
            String contact = resultSet.getString(4);

            return new Supplier(supId, name, address,contact);
        }
        return null;
    }

    @Override
    public ArrayList<String> getIDs() throws SQLException, ClassNotFoundException {
        ArrayList<String> ids = new ArrayList<>();

        String sql = "SELECT supId FROM supplier";

        ResultSet resultSet=SQLUtil.execute(sql);

        while(resultSet.next()) {
            ids.add(resultSet.getString(1));
        }
        return ids;
    }
}
