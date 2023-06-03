package lk.ijse.palmoilfactory.model;

import lk.ijse.palmoilfactory.util.CrudUtil;

import java.sql.SQLException;

public class ByProductionFuelModel {
    public static boolean addByProductFuel(String stockId, double totalEBFiber, double totalShell, double totalPressFiber, String date, String time) throws SQLException, ClassNotFoundException {
        String sql="INSERT INTO byproductfuel(stockId,bunchFiber,shell,pressFiber,date,time) VALUES(?,?,?,?,?,?)";

        return CrudUtil.execute(sql,stockId,totalEBFiber,totalShell,totalPressFiber,date,time);


    }
}
