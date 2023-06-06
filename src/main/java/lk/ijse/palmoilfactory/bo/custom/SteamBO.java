package lk.ijse.palmoilfactory.bo.custom;

import lk.ijse.palmoilfactory.bo.SuperBO;
import lk.ijse.palmoilfactory.dto.SteamDTO;
import lk.ijse.palmoilfactory.dto.StockDTO;
import lk.ijse.palmoilfactory.dto.SupplierDTO;

import java.sql.SQLException;
import java.util.ArrayList;

public interface SteamBO extends SuperBO {
    public boolean addSteam(SteamDTO dto) throws SQLException, ClassNotFoundException;

}
