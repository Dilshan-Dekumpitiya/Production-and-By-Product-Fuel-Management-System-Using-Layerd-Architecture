package lk.ijse.palmoilfactory.model;

import lk.ijse.palmoilfactory.util.CrudUtil;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;


public class OilProductionModel {

    public static String getUpdatedOilqty() throws SQLException, ClassNotFoundException {

        String sql="SELECT totalQty from totaloilqty";
        ResultSet resultSet= CrudUtil.execute(sql);

        if(resultSet.next()) {
            return (resultSet.getString(1));
        }
        return "-1";
    }

    public static boolean subtractionOilQtyTototalOil(double qty) throws SQLException, ClassNotFoundException {

        String sql = "UPDATE totaloilqty SET totalQty = (totalQty - ?) ";

        return CrudUtil.execute(sql,qty);

    }

    public static void subtractionOilQty(double qty) throws SQLException, ClassNotFoundException {

        String sql = "UPDATE totaloilqty SET totalQty = (totalQty - ?) ";

         CrudUtil.execute(sql,qty);

    }

    public static void addOilQtyTototalOil(double qty) throws SQLException, ClassNotFoundException {
        String sql = "UPDATE totaloilqty SET totalQty = (totalQty + ?) ";

        CrudUtil.execute(sql,qty);
    }

    public static String getTotalOileveryStock(String stockId) throws SQLException, ClassNotFoundException {
        double ffbinput = StockModel.searchByStockIdFFBInput(stockId);

        double totalPressLiquid=ffbinput*0.3*0.88;

        double totalEBLiquidOutput=ffbinput*0.7*0.72;

        String totalOilOutput = Double.toString(totalPressLiquid+totalEBLiquidOutput);

        return totalOilOutput;
    }

    public static String getTotalFueleveryStock(String stockId) throws SQLException, ClassNotFoundException {
        double ffbinput = StockModel.searchByStockIdFFBInput(stockId);

        double totalPressFiber=ffbinput*0.135;
       // txtTotalPressFiber.setText(String.valueOf(totalPressFiber));
        double totalShell=ffbinput*0.03;
     //   txtTotalShell.setText(String.valueOf(totalShell));
        double totalEBFiber=ffbinput*0.03;
      //  txtTotalEBFiber.setText(String.valueOf(totalEBFiber));

        String totalFuel = String.valueOf(totalPressFiber+totalShell+totalEBFiber);

        return totalFuel;
    }

    public static boolean addOilProduction(String stockId, double totalEBLiquid, double totalPressLiquid, String date, String time) throws SQLException, ClassNotFoundException {
        String sql="INSERT INTO oilproduction(stockId,totalEBLiquid,totalPressLiquid,date,time) VALUES(?,?,?,?,?)";

        return CrudUtil.execute(sql,stockId,totalEBLiquid,totalPressLiquid,date,time);


    }
}
