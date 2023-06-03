package lk.ijse.palmoilfactory.util;


public class Regex {

    private static final String USERNAME_REGEX = "^[A-Za-z0-9]{3,}$";

    private static final String PASSWORD_REGEX = "[aA-zZ0-9]{8,20}$";

    private static final String MOBILE_REGEX = "^\\+?\\d{10}$";

    private static final String SUPPLIERID_REGEX = "^SUP(0[0-9]{3}|[1-9][0-9]{3})$";

    private static final String STOCKID_REGEX = "FFB[0-9]{3}[0-9]{0,2}";

    private static final String FFBINPUT_REGEX = "^(?:100|[1-9][0-9]?)?(?:\\.\\d+)?$";

    private static final String EMPID_REGEX = "EMP(0{3}[1-9]|[1-9]\\d{3})$";

    public static boolean validateUsername(String username) {

        return username.matches(USERNAME_REGEX);
    }

    public static boolean validatePassword(String password) {

        return password.matches(PASSWORD_REGEX);
    }

    public static boolean validateSupplierId(String supId){

        return supId.matches(SUPPLIERID_REGEX);
    }

    public static boolean validateContact(String contact) {

        return contact.matches(MOBILE_REGEX);
    }

    public static boolean validateStockId(String stockId){

        return stockId.matches(STOCKID_REGEX);
    }

    public static boolean validateFFBInput(String ffbinput){

        return ffbinput.matches(FFBINPUT_REGEX);
    }

    public static boolean validateEMPID(String empId){
        return empId.matches(EMPID_REGEX);
    }

}