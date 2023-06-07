package lk.ijse.palmoilfactory.bo;

import lk.ijse.palmoilfactory.bo.custom.impl.*;

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
        SUPPLIER,STOCK,STEAM,SCHEDULE,ORDER,OILPRODUCTION,BYPRODUCTFUEL,USER,EMPLOYEE
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
            case ORDER:
                return (T) new OrderBOImpl();
            case OILPRODUCTION:
                return (T) new OilProductionBOImpl();
            case BYPRODUCTFUEL:
                return (T) new ByProductFuelBOImpl();
            case USER:
                return (T) new UserBOImpl();
            case EMPLOYEE:
                return (T) new EmployeeBOImpl();
            default:
                return null;
        }
    }
}
