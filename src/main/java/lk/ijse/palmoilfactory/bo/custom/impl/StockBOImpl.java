package lk.ijse.palmoilfactory.bo.custom.impl;

import lk.ijse.palmoilfactory.bo.custom.StockBO;
import lk.ijse.palmoilfactory.dao.DAOFactory;
import lk.ijse.palmoilfactory.dao.custom.ByProductionFuelDAO;
import lk.ijse.palmoilfactory.dao.custom.OilProductionDAO;
import lk.ijse.palmoilfactory.dao.custom.SteamDAO;
import lk.ijse.palmoilfactory.dao.custom.StockDAO;
import lk.ijse.palmoilfactory.db.DBConnection;
import lk.ijse.palmoilfactory.dto.FFBStockDTO;
import lk.ijse.palmoilfactory.entity.ByProductFuel;
import lk.ijse.palmoilfactory.entity.OilProduction;
import lk.ijse.palmoilfactory.entity.Steam;
import lk.ijse.palmoilfactory.entity.FFBStock;

import java.sql.Connection;
import java.sql.SQLException;
import java.util.ArrayList;

public class StockBOImpl implements StockBO {

    private StockDAO stockDAO= DAOFactory.getInstance().getDAO(DAOFactory.DAOTypes.STOCK);
    private SteamDAO steamDAO=DAOFactory.getInstance().getDAO(DAOFactory.DAOTypes.STEAM);
    private OilProductionDAO oilProductionDAO= DAOFactory.getInstance().getDAO(DAOFactory.DAOTypes.OILPRODUCTION);
    private ByProductionFuelDAO byProductionFuelDAO= DAOFactory.getInstance().getDAO(DAOFactory.DAOTypes.BYPRODUCTFUEL);


    @Override
    public ArrayList<FFBStockDTO> getAllStocks() throws SQLException, ClassNotFoundException {
        ArrayList<FFBStockDTO> allStocks= new ArrayList<>();
        ArrayList<FFBStock> all = stockDAO.getAll();
        for (FFBStock stock : all) {
            allStocks.add(new FFBStockDTO(stock.getStockId(),stock.getFfbInput(),stock.getDate(),stock.getTime(),stock.getSupId()));
        }
        return allStocks;
    }

    @Override
    public boolean addStock(FFBStockDTO dto) throws SQLException, ClassNotFoundException {
        return stockDAO.add(new FFBStock(dto.getStockId(),dto.getFfbInput(),dto.getDate(),dto.getTime(),dto.getSupId()));
    }

    @Override
    public boolean updateStock(FFBStockDTO dto) throws SQLException, ClassNotFoundException {
        return stockDAO.update(new FFBStock(dto.getStockId(),dto.getFfbInput(),dto.getDate(),dto.getTime(),dto.getSupId()));

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
    public FFBStockDTO searchStock(String id) throws SQLException, ClassNotFoundException {
        FFBStock stock=stockDAO.search(id);
        return stock!=null ? new FFBStockDTO(stock.getStockId(),stock.getFfbInput(),stock.getDate(),stock.getTime(),stock.getSupId()) : null;

    }

    @Override
    public ArrayList<String> getStockIDs() throws SQLException, ClassNotFoundException {
        return stockDAO.getStockIds();
    }

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

    @Override
    public boolean placeStock(FFBStockDTO stock) throws SQLException { //transaction
        Connection con = null;
        try {
            con = DBConnection.getInstance().getConnection();

            con.setAutoCommit(false);

            boolean isAddedToStock = stockDAO.add(new FFBStock(stock.getStockId(), stock.getFfbInput(), stock.getDate(), stock.getTime(),stock.getSupId()));

            //For steam table
            double fruitOutput=stock.getFfbInput()*0.3;
            double emptyBunchoutput=stock.getFfbInput()*0.7;

            //For oil production table
            double totalPressLiquid=stock.getFfbInput()*0.3*0.88;
            double totalEBLiquid=stock.getFfbInput()*0.7*0.72;

            //For by product fuel table
            double totalPressFiber=stock.getFfbInput()*0.135;
            double totalShell=stock.getFfbInput()*0.03;
            double totalEBFiber=stock.getFfbInput()*0.03;

            if (isAddedToStock) {
                boolean isAddedToSteam = steamDAO.add(new Steam(stock.getStockId(),fruitOutput,emptyBunchoutput,stock.getDate(),stock.getTime()));
                //   boolean isAddedToSteam = SteamModel.addSteam(stock.getStockId(),fruitOutput,emptyBunchoutput,stock.getDate(),stock.getTime());
                boolean isAddedToOilProduction= oilProductionDAO.add(new OilProduction(stock.getStockId(),totalEBLiquid,totalPressLiquid,stock.getDate(),stock.getTime()));//OilProductionModel.addOilProduction(stock.getStockId(),totalEBLiquid,totalPressLiquid,stock.getDate(),stock.getTime());
                boolean isAddedToByProduct= byProductionFuelDAO.add(new ByProductFuel(stock.getStockId(),totalEBFiber,totalShell,totalPressFiber,stock.getDate(),stock.getTime()));//ByProductionFuelModel.addByProductFuel(stock.getStockId(),totalEBFiber,totalShell,totalPressFiber,stock.getDate(),stock.getTime());
                if (isAddedToSteam && isAddedToOilProduction && isAddedToByProduct) {
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
