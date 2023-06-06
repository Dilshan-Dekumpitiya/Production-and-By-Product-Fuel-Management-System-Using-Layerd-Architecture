package lk.ijse.palmoilfactory.dao.custom.impl;

import lk.ijse.palmoilfactory.dao.SQLUtil;
import lk.ijse.palmoilfactory.dao.custom.SteamDAO;
import lk.ijse.palmoilfactory.entity.Steam;

import java.sql.SQLException;

public class SteamDAOImpl implements SteamDAO {
    @Override
    public boolean addSteam(Steam steam) throws SQLException, ClassNotFoundException {
        String sql="INSERT INTO steam(stockId,fruit,emptyBunch,date,time) VALUES(?,?,?,?,?)";

        return SQLUtil.execute(sql, steam.getStockId(), steam.getFruitOutput(), steam.getEmptyBunchoutput(), steam.getDate(),steam.getTime());

    }
}
