package lk.ijse.palmoilfactory.bo.custom;

import lk.ijse.palmoilfactory.bo.SuperBO;
import lk.ijse.palmoilfactory.dto.OilProductionDTO;
import lk.ijse.palmoilfactory.entity.OilProduction;

import java.sql.SQLException;

public interface OilProductionBO extends SuperBO {
    public String getUpdatedOilqty() throws SQLException, ClassNotFoundException;
    public boolean subtractionOilQtyTototalOil(double qty) throws SQLException, ClassNotFoundException;
    public boolean addOilQtyTototalOil(double qty) throws SQLException, ClassNotFoundException;
    public String getTotalOileveryStock(String stockId) throws SQLException, ClassNotFoundException;

    public String getTotalFueleveryStock(String stockId) throws SQLException, ClassNotFoundException;
    public boolean addOilProduction(OilProductionDTO oilProductionDTO) throws SQLException, ClassNotFoundException;



}
