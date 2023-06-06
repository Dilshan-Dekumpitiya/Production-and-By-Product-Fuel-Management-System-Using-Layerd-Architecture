package lk.ijse.palmoilfactory.bo.custom.impl;

import lk.ijse.palmoilfactory.bo.custom.ByProductFuelBO;
import lk.ijse.palmoilfactory.dao.DAOFactory;
import lk.ijse.palmoilfactory.dao.custom.ByProductionFuelDAO;
import lk.ijse.palmoilfactory.dto.ByProductFuelDTO;
import lk.ijse.palmoilfactory.entity.ByProductFuel;

import java.sql.SQLException;

public class ByProductFuelBOImpl implements ByProductFuelBO {

    private ByProductionFuelDAO byProductionFuelDAO= DAOFactory.getInstance().getDAO(DAOFactory.DAOTypes.BYPRODUCTFUEL);
    @Override
    public boolean addByProductFuel(ByProductFuelDTO dto) throws SQLException, ClassNotFoundException {
        return byProductionFuelDAO.add(new ByProductFuel(dto.getStockId(),dto.getBunchFiber(),dto.getShell(),dto.getPressFiber(),dto.getDate(),dto.getTime()));
    }
}
