package lk.ijse.palmoilfactory.bo.custom;

import lk.ijse.palmoilfactory.bo.SuperBO;
import lk.ijse.palmoilfactory.dto.FFBStockDTO;

import java.sql.SQLException;
import java.util.ArrayList;

public interface StockBO extends SuperBO {
    public ArrayList<FFBStockDTO> getAllStocks() throws SQLException, ClassNotFoundException;

    public boolean addStock(FFBStockDTO dto) throws SQLException, ClassNotFoundException ;

    public boolean updateStock(FFBStockDTO dto) throws SQLException, ClassNotFoundException ;

    public boolean existStock(String id) throws SQLException, ClassNotFoundException;

    public boolean deleteStock(String id) throws SQLException, ClassNotFoundException;

    public String generateNewStockID() throws SQLException, ClassNotFoundException;

    public FFBStockDTO searchStock(String id) throws SQLException, ClassNotFoundException;

    public ArrayList<String> getStockIDs() throws SQLException, ClassNotFoundException;

  //  public boolean placeStock(StockDTO dto) throws SQLException;

    public String searchByStockIdSupId(String stockId) throws SQLException, ClassNotFoundException;

    public double searchByStockIdFFBInput(String stockId) throws SQLException, ClassNotFoundException;

    public String searchByStockIdDate(String stockId) throws SQLException, ClassNotFoundException;

    public String searchByStockIdTime(String stockId) throws SQLException, ClassNotFoundException;

    public double getTotalFFBInput() throws SQLException, ClassNotFoundException;

    public boolean placeStock(FFBStockDTO stock) throws SQLException; //transaction

}
