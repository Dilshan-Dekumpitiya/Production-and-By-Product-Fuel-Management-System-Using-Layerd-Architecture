package lk.ijse.palmoilfactory.bo.custom.impl;

import lk.ijse.palmoilfactory.bo.custom.SteamBO;
import lk.ijse.palmoilfactory.dao.DAOFactory;
import lk.ijse.palmoilfactory.dao.custom.SteamDAO;
import lk.ijse.palmoilfactory.dto.SteamDTO;
import lk.ijse.palmoilfactory.entity.Steam;

import java.sql.SQLException;
import java.util.ArrayList;

public class SteamBOImpl implements SteamBO {

    private SteamDAO steamDAO= DAOFactory.getInstance().getDAO(DAOFactory.DAOTypes.STEAM);
    @Override
    public boolean addSteam(SteamDTO dto) throws SQLException, ClassNotFoundException {
        return steamDAO.addSteam(new Steam(
                dto.getStockId(), dto.getFruitOutput(),dto.getEmptyBunchoutput(),dto.getDate(),dto.getTime()
        ));
    }

}
