package lk.ijse.palmoilfactory.model;

import lk.ijse.palmoilfactory.db.DBConnection;
import lk.ijse.palmoilfactory.dto.Orders;
import lk.ijse.palmoilfactory.util.CrudUtil;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

public class OrderModel {

    public static String generateNextOrderId() throws SQLException, ClassNotFoundException {

        String sql = "SELECT orderId FROM orders ORDER BY orderId DESC LIMIT 1";

        ResultSet resultSet = CrudUtil.execute(sql);
        if(resultSet.next()) {
            return splitOrderId(resultSet.getString(1));
        }
        return splitOrderId(null);
    }

    public static String splitOrderId(String currentOrderId) { //D-001
        if(currentOrderId != null) {
            String[] strings = currentOrderId.split("-"); //["D","001"]
            int id = Integer.parseInt(strings[1]) + 1; //001 + 1 --> 002
            String nextId = String.format("%03d", id); //002

            return strings[0] + "-" + nextId;
        }
        return "D-001";
    }

    public static List<Orders> getAll() throws SQLException, ClassNotFoundException {

        String sql = "SELECT * FROM orders";

        List<Orders> orderData = new ArrayList<>();

        ResultSet resultSet = CrudUtil.execute(sql);

        while (resultSet.next()) {
            orderData.add(new Orders(
                    resultSet.getString(1),
                    resultSet.getString(2),
                    resultSet.getDouble(3),
                    resultSet.getDouble(4)
            ));
        }
        return orderData;
    }

    public static boolean addOrder(String orderId, String orderDate, double qty, double price) throws SQLException, ClassNotFoundException {
        String sql="INSERT INTO orders(orderId,date,quantity,price) VALUES(?,?,?,?)";

        return CrudUtil.execute(sql, orderId, orderDate, qty, price);

    }

    public static boolean placeOrder(String orderId, String orderDate , double qty , double price) throws SQLException {

        Connection con = null;
        try {
            con = DBConnection.getInstance().getConnection();

            con.setAutoCommit(false);

            boolean isAdded = OrderModel.addOrder(orderId,orderDate,qty,price);
            if (isAdded) {
                boolean isUpdated = OilProductionModel.subtractionOilQtyTototalOil(qty);
                if (isUpdated) {
                        con.commit();
                        return true;
                }
            }
            return false;
        } catch (SQLException | ClassNotFoundException er) {
            er.printStackTrace();
            con.rollback();
            return false;

        } finally { //update or not AutoCommit should true
            con.setAutoCommit(true);
        }
    }
}
