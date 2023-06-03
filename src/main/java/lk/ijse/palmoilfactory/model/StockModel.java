package lk.ijse.palmoilfactory.model;

import lk.ijse.palmoilfactory.db.DBConnection;
import lk.ijse.palmoilfactory.dto.Stock;
import lk.ijse.palmoilfactory.dto.Supplier;
import lk.ijse.palmoilfactory.util.CrudUtil;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class StockModel {

    public static boolean addStock(String stockId, double ffbInput, String date, String time ,String supId) throws SQLException, ClassNotFoundException {

        String sql="INSERT INTO ffbstock(stockId,ffbInput,date,time,supId)"+"VALUES(?,?,?,?,?)";

        return CrudUtil.execute(sql,stockId,ffbInput,date,time,supId);


    }


    public static Stock searchStock(String stockId) throws SQLException, ClassNotFoundException {

        String sql="SELECT * FROM ffbstock WHERE stockId=?";

        ResultSet resultSet = CrudUtil.execute(sql, stockId);

        if(resultSet.next()) {
            String  stkId = resultSet.getString(1);
            double ffbInput = resultSet.getDouble(2);
            String date = resultSet.getString(3);
            String time = resultSet.getString(4);
            String supId = resultSet.getString(5);

            return new Stock(stkId,ffbInput, date,time,supId);
        }
        return null;
    }

    public static boolean updateStock(String stockId, double ffbInput, String date, String time, String supId) throws SQLException, ClassNotFoundException {

        String sql="UPDATE ffbstock SET ffbInput = ?, date = ?, time = ? , supId = ? WHERE stockId = ?";

        return CrudUtil.execute(sql,ffbInput,date,time,supId,stockId);

    }

    public static boolean deleteStock(String stockId) throws SQLException, ClassNotFoundException {

        String sql="DELETE FROM ffbstock WHERE stockId=? ";

        return CrudUtil.execute(sql,stockId);
    }

    public static String searchByStockIdSupId(String stockId) throws SQLException, ClassNotFoundException {

        String sql="SELECT supId FROM ffbstock WHERE stockId = ?";

        ResultSet resultSet=CrudUtil.execute(sql,stockId);

        if(resultSet.next()){
            return resultSet.getString("supId");
        }
        return null;
    }

    public static double searchByStockIdFFBInput(String stockId) throws SQLException, ClassNotFoundException {

        String sql="SELECT ffbInput from ffbstock WHERE stockId = ? ";

        ResultSet resultSet=CrudUtil.execute(sql,stockId);

        if(resultSet.next()){
            return resultSet.getDouble("ffbInput");
        }
        return 0;
    }

    public static String searchByStockIdDate(String stockId) throws SQLException, ClassNotFoundException {
        String sql="SELECT date FROM ffbstock WHERE stockId = ?";

        ResultSet resultSet=CrudUtil.execute(sql,stockId);

        if(resultSet.next()){
            return resultSet.getString("date");
        }
        return null;

    }

    public static String searchByStockIdTime(String stockId) throws SQLException, ClassNotFoundException {
        String sql="SELECT time FROM ffbstock WHERE stockId = ?";

        ResultSet resultSet=CrudUtil.execute(sql,stockId);

        if(resultSet.next()){
            return resultSet.getString("time");
        }
        return null;

    }

    public static double getTotalFFBInput() throws SQLException, ClassNotFoundException {
        String sql = "SELECT SUM(ffbInput) AS total FROM ffbstock";

        ResultSet resultSet=CrudUtil.execute(sql);

        if(resultSet.next()){
            return Double.parseDouble(String.valueOf(resultSet.getDouble("total")));
        }
        return -1;

    }

    public static List<Stock> getAll() throws SQLException, ClassNotFoundException {
        String sql = "SELECT * FROM ffbstock";

        List<Stock> stockData = new ArrayList<>();

        ResultSet resultSet = CrudUtil.execute(sql);
        while (resultSet.next()) {
            stockData.add(new Stock(
                    resultSet.getString(1),
                    resultSet.getDouble(2),
                    resultSet.getString(3),
                    resultSet.getString(4),
                    resultSet.getString(5)
            ));
        }
        return stockData;

    }

   /* public static int getStockIdsCount() throws SQLException, ClassNotFoundException {
        String sql = "SELECT COUNT(stockId) as stockIdCount from ffbstock";

        return CrudUtil.execute(sql);
    }*/

    public static List<String> getStockIds() throws SQLException, ClassNotFoundException {

        List<String> stockIds = new ArrayList<>();

        String sql = "SELECT stockId FROM ffbstock";

        ResultSet resultSet = CrudUtil.execute(sql);

        while (resultSet.next()) {
            stockIds.add(resultSet.getString(1));
        }
        return stockIds;

    }

    public static boolean placeStock(String stockId, double ffbInput, String date, String time, String supId) throws SQLException {
        Connection con = null;
        try {
            con = DBConnection.getInstance().getConnection();

            con.setAutoCommit(false);

            boolean isAddedToStock = StockModel.addStock(stockId, ffbInput, date, time,supId);

            //For steam table
            double fruitOutput=ffbInput*0.3;
            double emptyBunchoutput=ffbInput*0.7;

            //For oil production table
            double totalPressLiquid=ffbInput*0.3*0.88;
            double totalEBLiquid=ffbInput*0.7*0.72;

            //For by product fuel table
            double totalPressFiber=ffbInput*0.135;
            double totalShell=ffbInput*0.03;
            double totalEBFiber=ffbInput*0.03;

            if (isAddedToStock) {
                boolean isAddedToSteam = SteamModel.addSteam(stockId,fruitOutput,emptyBunchoutput,date,time);
                boolean isAddedToOilProduction=OilProductionModel.addOilProduction(stockId,totalEBLiquid,totalPressLiquid,date,time);
                boolean isAddedToByProduct=ByProductionFuelModel.addByProductFuel(stockId,totalEBFiber,totalShell,totalPressFiber,date,time);
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

    /*public static boolean placeStock(String stockId, double ffbInput, String valueOf, String text, String supId) {
        Connection con = null;
        try {
            con = DBConnection.getInstance().getConnection();

            con.setAutoCommit(false);

            boolean isAdded = StockModel.addStock(stockId, ffbInput, valueOf, String text, String supId);
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
    }*/

