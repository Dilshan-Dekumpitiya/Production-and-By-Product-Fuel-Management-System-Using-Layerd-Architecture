package lk.ijse.palmoilfactory.dao.custom;

import lk.ijse.palmoilfactory.dao.CrudDAO;
import lk.ijse.palmoilfactory.dto.OilProductionDTO;
import lk.ijse.palmoilfactory.entity.OilProduction;

import java.sql.SQLException;

public interface OilProductionDAO extends CrudDAO<OilProduction> {
    public String getUpdatedOilqty() throws SQLException, ClassNotFoundException;
    public boolean subtractionOilQtyTototalOil(double qty) throws SQLException, ClassNotFoundException;
    public boolean addOilQtyTototalOil(double qty) throws SQLException, ClassNotFoundException;

}
