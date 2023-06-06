package lk.ijse.palmoilfactory.dao.custom;

import lk.ijse.palmoilfactory.dao.CrudDAO;
import lk.ijse.palmoilfactory.entity.Order;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public interface OrderDAO extends CrudDAO<Order> {
    public String generateNextOrderId() throws SQLException, ClassNotFoundException;
    public String splitOrderId(String currentOrderId);
    public ArrayList<Order> getAll() throws SQLException, ClassNotFoundException;
}
