package lk.ijse.palmoilfactory.dao.custom.impl;

import lk.ijse.palmoilfactory.dao.SQLUtil;
import lk.ijse.palmoilfactory.dao.custom.OrderDAO;
import lk.ijse.palmoilfactory.entity.Order;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

public class OrderDAOImpl implements OrderDAO {
    @Override
    public boolean add(Order entity) throws SQLException, ClassNotFoundException {
        String sql="INSERT INTO orders(orderId,date,quantity,price) VALUES(?,?,?,?)";

        return SQLUtil.execute(sql, entity.getOrderId(), entity.getOrderDate(), entity.getQuantity(), entity.getPrice());

    }

    @Override
    public boolean update(Order entity) throws SQLException, ClassNotFoundException {
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
    public Order search(String id) throws SQLException, ClassNotFoundException {
        throw new UnsupportedOperationException("This feature is not implemented yet");
    }

    @Override
    public String generateNextOrderId() throws SQLException, ClassNotFoundException {
        String sql = "SELECT orderId FROM orders ORDER BY orderId DESC LIMIT 1";

        ResultSet resultSet = SQLUtil.execute(sql);
        if (resultSet.next()) {
            return splitOrderId(resultSet.getString(1));
        }
        return splitOrderId(null);
    }

    @Override
    public String splitOrderId(String currentOrderId) {
        if(currentOrderId != null) {
            String[] strings = currentOrderId.split("-"); //["D","001"]
            int id = Integer.parseInt(strings[1]) + 1; //001 + 1 --> 002
            String nextId = String.format("%03d", id); //002

            return strings[0] + "-" + nextId;
        }
        return "D-001";
    }

    @Override
    public ArrayList<Order> getAll() throws SQLException, ClassNotFoundException {
        String sql = "SELECT * FROM orders";

        ArrayList<Order> orderData = new ArrayList<>();

        ResultSet resultSet = SQLUtil.execute(sql);

        while (resultSet.next()) {
            orderData.add(new Order(
                    resultSet.getString(1),
                    resultSet.getString(2),
                    resultSet.getDouble(3),
                    resultSet.getDouble(4)
            ));
        }
        return orderData;
    }

}
