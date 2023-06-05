package lk.ijse.palmoilfactory.dao.custom;

import lk.ijse.palmoilfactory.dao.CrudDAO;
import lk.ijse.palmoilfactory.entity.Supplier;

import java.sql.SQLException;
import java.util.ArrayList;

//Use unique functions to Supplier (SearchSupplier,....) define SupplierDAO interface
public interface SupplierDAO extends CrudDAO<Supplier> {
    public ArrayList<String> getIDs() throws SQLException, ClassNotFoundException;
}
