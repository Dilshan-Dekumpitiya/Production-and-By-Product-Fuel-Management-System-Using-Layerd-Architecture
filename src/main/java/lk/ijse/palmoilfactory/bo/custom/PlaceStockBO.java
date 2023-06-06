package lk.ijse.palmoilfactory.bo.custom;

import lk.ijse.palmoilfactory.bo.SuperBO;
import lk.ijse.palmoilfactory.entity.Stock;

import java.sql.SQLException;

public interface PlaceStockBO extends SuperBO {
    public boolean placeStock(Stock stock) throws SQLException;
}
