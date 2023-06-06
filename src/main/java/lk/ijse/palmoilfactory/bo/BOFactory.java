package lk.ijse.palmoilfactory.bo;

import lk.ijse.palmoilfactory.bo.custom.impl.ScheduleBOImpl;
import lk.ijse.palmoilfactory.bo.custom.impl.SteamBOImpl;
import lk.ijse.palmoilfactory.bo.custom.impl.StockBOImpl;
import lk.ijse.palmoilfactory.bo.custom.impl.SupplierBOImpl;

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
        SUPPLIER,STOCK,STEAM,SCHEDULE
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
            case SCHEDULE:
                return (T) new ScheduleBOImpl();
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
