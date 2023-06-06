package lk.ijse.palmoilfactory.dao;

import lk.ijse.palmoilfactory.dao.custom.impl.*;

public class DAOFactory { //Factory Method Design pattern --> Singleton + /Factory Design pattern
    //Singleton applied
    private static DAOFactory daoFactory;
    private DAOFactory() {
    }
    public static DAOFactory getInstance() {
        return (daoFactory == null) ? daoFactory = new DAOFactory() : daoFactory;
    }

    public enum DAOTypes {
        //create object types
        SUPPLIER,STOCK,STEAM,SCHEDULE,ORDER,OILPRODUCTION,BYPRODUCTFUEL
    }


    //Factory Design pattern
    public <T> T getDAO(DAOTypes type){
        switch (type) {
            case SUPPLIER:
                return (T) new SupplierDAOImpl();
            case STOCK:
                return (T) new StockDAOImpl();
            case STEAM:
                return (T) new SteamDAOImpl();
            case SCHEDULE:
                return (T) new ScheduleDAOImpl();
            case ORDER:
                return (T) new OrderDAOImpl();
            case OILPRODUCTION:
                return (T) new OilProductionDAOImpl();
            case BYPRODUCTFUEL:
                return (T) new ByProductFuelDAOImpl();
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

