package lk.ijse.palmoilfactory.bo.custom.impl;

import lk.ijse.palmoilfactory.bo.custom.SupplierBO;
import lk.ijse.palmoilfactory.dao.DAOFactory;
import lk.ijse.palmoilfactory.dao.SQLUtil;
import lk.ijse.palmoilfactory.dao.custom.SupplierDAO;
import lk.ijse.palmoilfactory.dto.SupplierDTO;
import lk.ijse.palmoilfactory.entity.Supplier;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

public class SupplierBOImpl implements SupplierBO {

    private SupplierDAO supplierDAO= DAOFactory.getInstance().getDAO(DAOFactory.DAOTypes.SUPPLIER);
    @Override
    public ArrayList<SupplierDTO> getAllSuppliers() throws SQLException, ClassNotFoundException {
        ArrayList<SupplierDTO> allSuppliers= new ArrayList<>();
        ArrayList<Supplier> all = supplierDAO.getAll();
        for (Supplier supplier : all) {
            allSuppliers.add(new SupplierDTO(supplier.getSupId(),supplier.getSupName(),supplier.getSupAddress(),supplier.getSupContact()));
        }
        return allSuppliers;
    }

    @Override
    public boolean addSupplier(SupplierDTO dto) throws SQLException, ClassNotFoundException {
        return supplierDAO.add(new Supplier(
                dto.getSupId(),
                dto.getSupName(),
                dto.getSupAddress(),
                dto.getSupContact()
        ));
    }

    @Override
    public boolean updateSupplier(SupplierDTO dto) throws SQLException, ClassNotFoundException {
        return supplierDAO.update(new Supplier(
                dto.getSupId(),
                dto.getSupName(),
                dto.getSupAddress(),
                dto.getSupContact()

        ));
    }

    @Override
    public boolean existSupplier(String id) throws SQLException, ClassNotFoundException {
        return false;
    }

    @Override
    public boolean deleteSupplier(String id) throws SQLException, ClassNotFoundException {
        return supplierDAO.delete(id);
    }

    @Override
    public String generateNewSupplierID() throws SQLException, ClassNotFoundException {
        return null;
    }

    @Override
    public SupplierDTO searchSupplier(String id) throws SQLException, ClassNotFoundException {
        String sql="SELECT * FROM supplier WHERE supId= ?";

        ResultSet resultSet = SQLUtil.execute(sql, id);

        if(resultSet.next()) {
            String  supId = resultSet.getString(1);
            String name = resultSet.getString(2);
            String address = resultSet.getString(3);
            String contact = resultSet.getString(4);

            return new SupplierDTO(supId, name, address,contact);
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
