package lk.ijse.palmoilfactory.bo.custom.impl;

import lk.ijse.palmoilfactory.bo.custom.PlaceStockBO;
import lk.ijse.palmoilfactory.dao.DAOFactory;
import lk.ijse.palmoilfactory.dao.custom.SteamDAO;
import lk.ijse.palmoilfactory.dao.custom.StockDAO;
import lk.ijse.palmoilfactory.db.DBConnection;
import lk.ijse.palmoilfactory.entity.Steam;
import lk.ijse.palmoilfactory.entity.Stock;
import lk.ijse.palmoilfactory.model.ByProductionFuelModel;
import lk.ijse.palmoilfactory.model.OilProductionModel;

import java.sql.Connection;
import java.sql.SQLException;

public class PlaceStockBOImpl implements PlaceStockBO {

    private StockDAO stockDAO= DAOFactory.getInstance().getDAO(DAOFactory.DAOTypes.STOCK);
    private SteamDAO steamDAO=DAOFactory.getInstance().getDAO(DAOFactory.DAOTypes.STEAM);

    @Override
    public boolean placeStock(Stock stock) throws SQLException {
        Connection con = null;
        try {
            con = DBConnection.getInstance().getConnection();

            con.setAutoCommit(false);

            boolean isAddedToStock = stockDAO.add(new Stock(stock.getStockId(), stock.getFfbInput(), stock.getDate(), stock.getTime(),stock.getSupId()));

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
                boolean isAddedToSteam = steamDAO.addSteam(new Steam(stock.getStockId(),fruitOutput,emptyBunchoutput,stock.getDate(),stock.getTime()));
             //   boolean isAddedToSteam = SteamModel.addSteam(stock.getStockId(),fruitOutput,emptyBunchoutput,stock.getDate(),stock.getTime());
                boolean isAddedToOilProduction= OilProductionModel.addOilProduction(stock.getStockId(),totalEBLiquid,totalPressLiquid,stock.getDate(),stock.getTime());
                boolean isAddedToByProduct= ByProductionFuelModel.addByProductFuel(stock.getStockId(),totalEBFiber,totalShell,totalPressFiber,stock.getDate(),stock.getTime());
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
