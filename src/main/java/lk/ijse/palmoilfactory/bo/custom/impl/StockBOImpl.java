package lk.ijse.palmoilfactory.bo.custom.impl;

import javafx.scene.control.Alert;
import lk.ijse.palmoilfactory.bo.BOFactory;
import lk.ijse.palmoilfactory.bo.custom.PlaceStockBO;
import lk.ijse.palmoilfactory.bo.custom.StockBO;
import lk.ijse.palmoilfactory.dao.DAOFactory;
import lk.ijse.palmoilfactory.dao.custom.StockDAO;
import lk.ijse.palmoilfactory.dto.StockDTO;
import lk.ijse.palmoilfactory.dto.SupplierDTO;
import lk.ijse.palmoilfactory.entity.Stock;
import lk.ijse.palmoilfactory.entity.Supplier;

import java.sql.SQLException;
import java.util.ArrayList;

public class StockBOImpl implements StockBO {

    private StockDAO stockDAO= DAOFactory.getInstance().getDAO(DAOFactory.DAOTypes.STOCK);

    @Override
    public ArrayList<StockDTO> getAllStocks() throws SQLException, ClassNotFoundException {
        ArrayList<StockDTO> allStocks= new ArrayList<>();
        ArrayList<Stock> all = stockDAO.getAll();
        for (Stock stock : all) {
            allStocks.add(new StockDTO(stock.getStockId(),stock.getFfbInput(),stock.getDate(),stock.getTime(),stock.getSupId()));
        }
        return allStocks;
    }

    @Override
    public boolean addStock(StockDTO dto) throws SQLException, ClassNotFoundException {
        return stockDAO.add(new Stock(dto.getStockId(),dto.getFfbInput(),dto.getDate(),dto.getTime(),dto.getSupId()));
    }

    @Override
    public boolean updateStock(StockDTO dto) throws SQLException, ClassNotFoundException {
        return stockDAO.update(new Stock(dto.getStockId(),dto.getFfbInput(),dto.getDate(),dto.getTime(),dto.getSupId()));

    }

    @Override
    public boolean existStock(String id) throws SQLException, ClassNotFoundException {
        return false;
    }

    @Override
    public boolean deleteStock(String id) throws SQLException, ClassNotFoundException {
        return stockDAO.delete(id);
    }

    @Override
    public String generateNewStockID() throws SQLException, ClassNotFoundException {
        return null;
    }

    @Override
    public StockDTO searchStock(String id) throws SQLException, ClassNotFoundException {
        Stock stock=stockDAO.search(id);
        return stock!=null ? new StockDTO(stock.getStockId(),stock.getFfbInput(),stock.getDate(),stock.getTime(),stock.getSupId()) : null;

    }

    @Override
    public ArrayList<String> getStockIDs() throws SQLException, ClassNotFoundException {
        return stockDAO.getStockIds();
    }

    /*@Override
    public boolean placeStock(StockDTO stockDTO) throws SQLException {

        return stockDAO.placeStock(new Stock(stockDTO.getStockId(),stockDTO.getFfbInput(),stockDTO.getDate(),stockDTO.getTime(),stockDTO.getSupId()));
    }*/

    @Override
    public String searchByStockIdSupId(String stockId) throws SQLException, ClassNotFoundException {
        return stockDAO.searchByStockIdSupId(stockId);
    }

    @Override
    public double searchByStockIdFFBInput(String stockId) throws SQLException, ClassNotFoundException {
        return stockDAO.searchByStockIdFFBInput(stockId);
    }

    @Override
    public String searchByStockIdDate(String stockId) throws SQLException, ClassNotFoundException {
       return stockDAO.searchByStockIdDate(stockId);
    }

    @Override
    public String searchByStockIdTime(String stockId) throws SQLException, ClassNotFoundException {
        return stockDAO.searchByStockIdTime(stockId);
    }

    @Override
    public double getTotalFFBInput() throws SQLException, ClassNotFoundException {
        return stockDAO.getTotalFFBInput();
    }
}
