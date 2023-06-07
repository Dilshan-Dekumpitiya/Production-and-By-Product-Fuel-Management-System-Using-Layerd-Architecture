package lk.ijse.palmoilfactory.dao.custom;

import lk.ijse.palmoilfactory.dao.CrudDAO;
import lk.ijse.palmoilfactory.entity.Employee;

import java.sql.SQLException;
import java.util.ArrayList;

public interface EmployeeDAO extends CrudDAO<Employee> {
    public ArrayList<String> getSchIDs() throws SQLException, ClassNotFoundException;
    public String searchByempIdEmployeeType(String empId) throws SQLException, ClassNotFoundException;
    public String searchByempIdEmployeeSchId(String empId) throws SQLException, ClassNotFoundException;
}
