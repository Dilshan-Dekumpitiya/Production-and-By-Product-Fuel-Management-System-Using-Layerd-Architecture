package lk.ijse.palmoilfactory.bo.custom.impl;

import lk.ijse.palmoilfactory.bo.custom.OilProductionBO;
import lk.ijse.palmoilfactory.dao.DAOFactory;
import lk.ijse.palmoilfactory.dao.custom.OilProductionDAO;
import lk.ijse.palmoilfactory.dao.custom.StockDAO;
import lk.ijse.palmoilfactory.dto.OilProductionDTO;
import lk.ijse.palmoilfactory.entity.OilProduction;

import java.sql.SQLException;

public class OilProductionBOImpl implements OilProductionBO {

    private OilProductionDAO oilProductionDAO= DAOFactory.getInstance().getDAO(DAOFactory.DAOTypes.OILPRODUCTION);

    private StockDAO stockDAO=DAOFactory.getInstance().getDAO(DAOFactory.DAOTypes.STOCK);

    @Override
    public String getUpdatedOilqty() throws SQLException, ClassNotFoundException {
        return oilProductionDAO.getUpdatedOilqty();
    }

    @Override
    public boolean subtractionOilQtyTototalOil(double qty) throws SQLException, ClassNotFoundException {
        return oilProductionDAO.subtractionOilQtyTototalOil(qty);
    }

    @Override
    public boolean addOilQtyTototalOil(double qty) throws SQLException, ClassNotFoundException {
        return oilProductionDAO.addOilQtyTototalOil(qty);
    }

    @Override
    public String getTotalOileveryStock(String stockId) throws SQLException, ClassNotFoundException {
        double ffbinput = stockDAO.searchByStockIdFFBInput(stockId);//stockBO.searchByStockIdFFBInput(stockId);

        double totalPressLiquid=ffbinput*0.3*0.88;

        double totalEBLiquidOutput=ffbinput*0.7*0.72;

        String totalOilOutput = Double.toString(totalPressLiquid+totalEBLiquidOutput);

        return totalOilOutput;
    }

    @Override
    public String getTotalFueleveryStock(String stockId) throws SQLException, ClassNotFoundException {
        double ffbinput = stockDAO.searchByStockIdFFBInput(stockId);

        double totalPressFiber=ffbinput*0.135;
        // txtTotalPressFiber.setText(String.valueOf(totalPressFiber));
        double totalShell=ffbinput*0.03;
        //   txtTotalShell.setText(String.valueOf(totalShell));
        double totalEBFiber=ffbinput*0.03;
        //  txtTotalEBFiber.setText(String.valueOf(totalEBFiber));

        String totalFuel = String.valueOf(totalPressFiber+totalShell+totalEBFiber);

        return totalFuel;
    }

    @Override
    public boolean addOilProduction(OilProductionDTO oilProductionDTO) throws SQLException, ClassNotFoundException {
        return oilProductionDAO.add(new OilProduction(oilProductionDTO.getStockID(),oilProductionDTO.getTotalEBLiquid(),oilProductionDTO.getTotalPressLiquid(),oilProductionDTO.getDate(),oilProductionDTO.getTime()));
    }
}
