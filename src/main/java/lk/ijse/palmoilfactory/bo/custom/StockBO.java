package lk.ijse.palmoilfactory.bo.custom;

import lk.ijse.palmoilfactory.bo.SuperBO;
import lk.ijse.palmoilfactory.dto.StockDTO;
import lk.ijse.palmoilfactory.dto.SupplierDTO;

import java.sql.SQLException;
import java.util.ArrayList;

public interface StockBO extends SuperBO {
    public ArrayList<StockDTO> getAllStocks() throws SQLException, ClassNotFoundException;

    public boolean addStock(StockDTO dto) throws SQLException, ClassNotFoundException ;

    public boolean updateStock(StockDTO dto) throws SQLException, ClassNotFoundException ;

    public boolean existStock(String id) throws SQLException, ClassNotFoundException;

    public boolean deleteStock(String id) throws SQLException, ClassNotFoundException;

    public String generateNewStockID() throws SQLException, ClassNotFoundException;

    public StockDTO searchStock(String id) throws SQLException, ClassNotFoundException;

    public ArrayList<String> getStockIDs() throws SQLException, ClassNotFoundException;

  //  public boolean placeStock(StockDTO dto) throws SQLException;

    public String searchByStockIdSupId(String stockId) throws SQLException, ClassNotFoundException;

    public double searchByStockIdFFBInput(String stockId) throws SQLException, ClassNotFoundException;

    public String searchByStockIdDate(String stockId) throws SQLException, ClassNotFoundException;

    public String searchByStockIdTime(String stockId) throws SQLException, ClassNotFoundException;

    public double getTotalFFBInput() throws SQLException, ClassNotFoundException;

    public boolean placeStock(StockDTO stock) throws SQLException; //transaction

}
