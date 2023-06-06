package lk.ijse.palmoilfactory.dao.custom;

import lk.ijse.palmoilfactory.entity.Steam;

import java.sql.SQLException;
import java.util.List;

public interface SteamDAO {
   public boolean addSteam(Steam steam) throws SQLException, ClassNotFoundException;
}
