package lk.ijse.palmoilfactory.model;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import lk.ijse.palmoilfactory.db.DBConnection;
import lk.ijse.palmoilfactory.dto.Supplier;
import lk.ijse.palmoilfactory.util.CrudUtil;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class SupplierModel {

    public static boolean addSupplier(String supId,String supName,String supAddress,String supContact) throws SQLException, ClassNotFoundException {

        String sql="INSERT INTO supplier(supId,name,address,contact) VALUES(?,?,?,?)";

        return CrudUtil.execute(sql, supId, supName, supAddress, supContact);
    }

    public static Supplier searchSupplier(String id) throws SQLException, ClassNotFoundException {

       String sql="SELECT * FROM supplier WHERE supId= ?";

       ResultSet resultSet = CrudUtil.execute(sql, id);

        if(resultSet.next()) {
            String  supId = resultSet.getString(1);
            String name = resultSet.getString(2);
            String address = resultSet.getString(3);
            String contact = resultSet.getString(4);

            return new Supplier(supId, name, address,contact);
        }
        return null;

    }

    public static boolean updateSupplier(String supId, String supName, String supAddress, String supContact) throws SQLException, ClassNotFoundException {

        String sql="UPDATE supplier SET name = ?, address = ?, contact = ? WHERE supId = ?";

        return CrudUtil.execute(sql,  supName, supAddress, supContact,supId);

    }

    public static boolean deleteSupplier(String supId) throws SQLException, ClassNotFoundException {

        String sql="DELETE FROM supplier WHERE supId=? ";

        return CrudUtil.execute(sql,supId);
    }

    public static List<String> getIDs() throws SQLException, ClassNotFoundException {

        List<String> ids = new ArrayList<>();

        String sql = "SELECT supId FROM supplier";

        ResultSet resultSet=CrudUtil.execute(sql);

        while(resultSet.next()) {
            ids.add(resultSet.getString(1));
        }
        return ids;
    }

    public static List<Supplier> getAll() throws SQLException, ClassNotFoundException {

        String sql = "SELECT * FROM supplier";

        List<Supplier> supData = new ArrayList<>();

        ResultSet resultSet = CrudUtil.execute(sql);
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
}
