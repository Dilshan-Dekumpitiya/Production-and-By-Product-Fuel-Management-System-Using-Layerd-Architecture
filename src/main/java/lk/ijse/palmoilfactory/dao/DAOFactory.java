package lk.ijse.palmoilfactory.dao;

import lk.ijse.palmoilfactory.dao.custom.impl.SupplierDAOImpl;

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
        SUPPLIER
    }


    //Factory Design pattern
    public <T> T getDAO(DAOTypes type){
        switch (type) {
            case SUPPLIER:
                return (T) new SupplierDAOImpl();
            /*case ITEM:
                return (T) new ItemDAOImpl();
            case ORDER:
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

