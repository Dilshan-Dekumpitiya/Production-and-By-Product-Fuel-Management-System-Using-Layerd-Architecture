package lk.ijse.palmoilfactory.bo;

import lk.ijse.palmoilfactory.bo.custom.PlaceStockBO;
import lk.ijse.palmoilfactory.bo.custom.impl.PlaceStockBOImpl;
import lk.ijse.palmoilfactory.bo.custom.impl.SteamBOImpl;
import lk.ijse.palmoilfactory.bo.custom.impl.StockBOImpl;
import lk.ijse.palmoilfactory.bo.custom.impl.SupplierBOImpl;
import lk.ijse.palmoilfactory.dao.DAOFactory;
import lk.ijse.palmoilfactory.dao.custom.impl.SupplierDAOImpl;

public class BOFactory {//Factory Method Design pattern --> Singleton + /Factory Design pattern
    //Singleton applied
    private static BOFactory boFactory;
    private BOFactory() {
    }
    public static BOFactory getInstance() {
        return (boFactory == null) ? boFactory = new BOFactory() : boFactory;
    }

    public enum BOTypes {
        //create object types
        SUPPLIER,STOCK,STEAM,PLACESTOCK
    }


    //Factory Design pattern
    public <T> T getBO(BOTypes type){
        switch (type) {
            case SUPPLIER:
                return (T) new SupplierBOImpl();
            case STOCK:
                return (T) new StockBOImpl();
            case STEAM:
                return (T) new SteamBOImpl();
            case PLACESTOCK:
                return (T) new PlaceStockBOImpl();
            /*case ORDER:
                return (T) new OrderDAOImpl();
            case ORDER_DETAILS:
                return (T) new OrderDetailsDAOImpl();
            case QUERY_DAO:
                return (T) new QueryDAOImpl();*/
            default:
                return null;
        }
    }
}
