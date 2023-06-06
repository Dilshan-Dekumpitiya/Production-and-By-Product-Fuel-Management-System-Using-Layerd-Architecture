package lk.ijse.palmoilfactory.bo.custom;

import lk.ijse.palmoilfactory.bo.SuperBO;
import lk.ijse.palmoilfactory.dto.ByProductFuelDTO;
import lk.ijse.palmoilfactory.entity.ByProductFuel;

import java.sql.SQLException;

public interface ByProductFuelBO extends SuperBO {
    public boolean addByProductFuel(ByProductFuelDTO dto) throws SQLException, ClassNotFoundException;
}
