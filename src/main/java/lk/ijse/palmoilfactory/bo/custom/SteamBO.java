package lk.ijse.palmoilfactory.bo.custom;

import lk.ijse.palmoilfactory.bo.SuperBO;
import lk.ijse.palmoilfactory.dto.SteamDTO;
import lk.ijse.palmoilfactory.dto.StockDTO;

import java.sql.SQLException;

public interface SteamBO extends SuperBO {
    public boolean addSteam(SteamDTO dto) throws SQLException, ClassNotFoundException;
}
