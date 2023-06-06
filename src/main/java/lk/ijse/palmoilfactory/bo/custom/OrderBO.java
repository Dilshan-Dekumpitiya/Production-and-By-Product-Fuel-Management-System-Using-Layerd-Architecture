package lk.ijse.palmoilfactory.bo.custom;

import lk.ijse.palmoilfactory.bo.SuperBO;
import lk.ijse.palmoilfactory.dto.OrderDTO;
import lk.ijse.palmoilfactory.entity.Order;

import java.sql.SQLException;
import java.util.ArrayList;

public interface OrderBO extends SuperBO {
    public String generateNextOrderId() throws SQLException, ClassNotFoundException;
    public String splitOrderId(String currentOrderId);
    public ArrayList<OrderDTO> getAll() throws SQLException, ClassNotFoundException;
    public boolean placeOrder(OrderDTO orderDTO) throws SQLException;

    public boolean addOrder(OrderDTO orderDTO) throws SQLException, ClassNotFoundException;
}
