package lk.ijse.palmoilfactory.dao.custom.impl;

import lk.ijse.palmoilfactory.dao.SQLUtil;
import lk.ijse.palmoilfactory.dao.custom.StockDAO;
import lk.ijse.palmoilfactory.entity.FFBStock;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

public class StockDAOImpl implements StockDAO {
    @Override
    public ArrayList<FFBStock> getAll() throws SQLException, ClassNotFoundException {
        String sql = "SELECT * FROM ffbstock";

        ArrayList<FFBStock> stockData = new ArrayList<>();

        ResultSet resultSet = SQLUtil.execute(sql);
        while (resultSet.next()) {
            stockData.add(new FFBStock(
                    resultSet.getString(1),
                    resultSet.getDouble(2),
                    resultSet.getString(3),
                    resultSet.getString(4),
                    resultSet.getString(5)
            ));
        }
        return stockData;
    }

    @Override
    public boolean add(FFBStock entity) throws SQLException, ClassNotFoundException {
        String sql="INSERT INTO ffbstock(stockId,ffbInput,date,time,supId)"+"VALUES(?,?,?,?,?)";

        return SQLUtil.execute(sql,entity.getStockId(),entity.getFfbInput(),entity.getDate(),entity.getTime(),entity.getSupId());
    }

    @Override
    public boolean update(FFBStock entity) throws SQLException, ClassNotFoundException {
        String sql="UPDATE ffbstock SET ffbInput = ?, date = ?, time = ? , supId = ? WHERE stockId = ?";

        return SQLUtil.execute(sql,entity.getFfbInput(),entity.getDate(),entity.getTime(),entity.getSupId(),entity.getStockId());
    }

    @Override
    public boolean exist(String id) throws SQLException, ClassNotFoundException {
        return false;
    }

    @Override
    public String generateNewID() throws SQLException, ClassNotFoundException {
        return null;
    }

    @Override
    public boolean delete(String id) throws SQLException, ClassNotFoundException {
        String sql="DELETE FROM ffbstock WHERE stockId=? ";

        return SQLUtil.execute(sql,id);
    }

    @Override
    public FFBStock search(String id) throws SQLException, ClassNotFoundException {
        String sql="SELECT * FROM ffbstock WHERE stockId=?";

        ResultSet resultSet = SQLUtil.execute(sql, id);

        if(resultSet.next()) {
            String  stkId = resultSet.getString(1);
            double ffbInput = resultSet.getDouble(2);
            String date = resultSet.getString(3);
            String time = resultSet.getString(4);
            String supId = resultSet.getString(5);

            return new FFBStock(stkId,ffbInput, date,time,supId);
        }
        return null;
    }

    @Override
    public String searchByStockIdSupId(String stockId) throws SQLException, ClassNotFoundException {
        String sql="SELECT supId FROM ffbstock WHERE stockId = ?";

        ResultSet resultSet=SQLUtil.execute(sql,stockId);

        if(resultSet.next()){
            return resultSet.getString("supId");
        }
        return null;
    }

    @Override
    public double searchByStockIdFFBInput(String stockId) throws SQLException, ClassNotFoundException {
        String sql="SELECT ffbInput from ffbstock WHERE stockId = ? ";

        ResultSet resultSet=SQLUtil.execute(sql,stockId);

        if(resultSet.next()){
            return resultSet.getDouble("ffbInput");
        }
        return 0;
    }

    @Override
    public String searchByStockIdDate(String stockId) throws SQLException, ClassNotFoundException {
        String sql="SELECT date FROM ffbstock WHERE stockId = ?";

        ResultSet resultSet=SQLUtil.execute(sql,stockId);

        if(resultSet.next()){
            return resultSet.getString("date");
        }
        return null;
    }

    @Override
    public String searchByStockIdTime(String stockId) throws SQLException, ClassNotFoundException {
        String sql="SELECT time FROM ffbstock WHERE stockId = ?";

        ResultSet resultSet=SQLUtil.execute(sql,stockId);

        if(resultSet.next()){
            return resultSet.getString("time");
        }
        return null;

    }

    @Override
    public double getTotalFFBInput() throws SQLException, ClassNotFoundException {
        String sql = "SELECT SUM(ffbInput) AS total FROM ffbstock";

        ResultSet resultSet=SQLUtil.execute(sql);

        if(resultSet.next()){
            return Double.parseDouble(String.valueOf(resultSet.getDouble("total")));
        }
        return -1;
    }

    @Override
    public ArrayList<String> getStockIds() throws SQLException, ClassNotFoundException {
        ArrayList<String> stockIds = new ArrayList<>();

        String sql = "SELECT stockId FROM ffbstock";

        ResultSet resultSet = SQLUtil.execute(sql);

        while (resultSet.next()) {
            stockIds.add(resultSet.getString(1));
        }
        return stockIds;
    }


}
