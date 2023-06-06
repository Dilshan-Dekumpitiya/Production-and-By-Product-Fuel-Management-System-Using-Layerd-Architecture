package lk.ijse.palmoilfactory.bo.custom.impl;

import lk.ijse.palmoilfactory.bo.custom.OrderBO;
import lk.ijse.palmoilfactory.dao.DAOFactory;
import lk.ijse.palmoilfactory.dao.custom.OrderDAO;
import lk.ijse.palmoilfactory.db.DBConnection;
import lk.ijse.palmoilfactory.dto.OrderDTO;
import lk.ijse.palmoilfactory.entity.Order;
import lk.ijse.palmoilfactory.model.OilProductionModel;

import java.sql.Connection;
import java.sql.SQLException;
import java.util.ArrayList;

public class OrderBOImpl implements OrderBO {

    private OrderDAO orderDAO= DAOFactory.getInstance().getDAO(DAOFactory.DAOTypes.ORDER);

    @Override
    public boolean addOrder(OrderDTO orderDTO) throws SQLException, ClassNotFoundException {
        return orderDAO.add(new Order(orderDTO.getOrderId(),orderDTO.getOrderDate(),orderDTO.getQuantity(),orderDTO.getPrice()));
    }

    @Override
    public String generateNextOrderId() throws SQLException, ClassNotFoundException {
        return orderDAO.generateNextOrderId();
    }

    @Override
    public String splitOrderId(String currentOrderId) {
        return orderDAO.splitOrderId(currentOrderId);
    }

    @Override
    public ArrayList<OrderDTO> getAll() throws SQLException, ClassNotFoundException {
        ArrayList<Order> allOrder=orderDAO.getAll();
        ArrayList<OrderDTO> orderDTOS=new ArrayList<>();
        for (Order order:allOrder) {
            orderDTOS.add(new OrderDTO(order.getOrderId(),order.getOrderDate(),order.getQuantity(),order.getPrice()));
        }
        return orderDTOS;
    }

    @Override
    public boolean placeOrder(OrderDTO orderDTO) throws SQLException {
        Connection con = null;
        try {
            con = DBConnection.getInstance().getConnection();

            con.setAutoCommit(false);

            boolean isAdded = orderDAO.add(new Order(orderDTO.getOrderId(),orderDTO.getOrderDate(),orderDTO.getQuantity(),orderDTO.getPrice()));
            if (isAdded) {
                boolean isUpdated = OilProductionModel.subtractionOilQtyTototalOil(orderDTO.getQuantity());
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
