package lk.ijse.palmoilfactory.dao.custom;

import lk.ijse.palmoilfactory.dao.CrudDAO;
import lk.ijse.palmoilfactory.entity.FFBStock;

import java.sql.SQLException;
import java.util.ArrayList;

public interface StockDAO extends CrudDAO<FFBStock> {
    public String searchByStockIdSupId(String stockId) throws SQLException, ClassNotFoundException;
    public double searchByStockIdFFBInput(String stockId) throws SQLException, ClassNotFoundException;
    public String searchByStockIdDate(String stockId) throws SQLException, ClassNotFoundException;
    public String searchByStockIdTime(String stockId) throws SQLException, ClassNotFoundException;
    public double getTotalFFBInput() throws SQLException, ClassNotFoundException;
    public ArrayList<String> getStockIds() throws SQLException, ClassNotFoundException;

}
