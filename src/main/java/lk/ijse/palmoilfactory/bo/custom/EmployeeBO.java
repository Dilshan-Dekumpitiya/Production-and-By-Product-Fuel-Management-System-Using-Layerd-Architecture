package lk.ijse.palmoilfactory.bo.custom;

import lk.ijse.palmoilfactory.bo.SuperBO;
import lk.ijse.palmoilfactory.dto.EmployeeDTO;
import lk.ijse.palmoilfactory.dto.SupplierDTO;

import java.sql.SQLException;
import java.util.ArrayList;

public interface EmployeeBO extends SuperBO {
    public ArrayList<EmployeeDTO> getAllEmployee() throws SQLException, ClassNotFoundException;

    public boolean addEmployee(EmployeeDTO dto) throws SQLException, ClassNotFoundException ;

    public boolean updateEmployee(EmployeeDTO dto) throws SQLException, ClassNotFoundException ;

    public boolean existEmployee(String id) throws SQLException, ClassNotFoundException;

    public boolean deleteEmployee(String id) throws SQLException, ClassNotFoundException;

    public String generateNewEmployeeID() throws SQLException, ClassNotFoundException;

    public EmployeeDTO searchEmployee(String id) throws SQLException, ClassNotFoundException;

 //   public ArrayList<String> getIDs() throws SQLException, ClassNotFoundException;

    public ArrayList<String> getSchIDs() throws SQLException, ClassNotFoundException;
    public String searchByempIdEmployeeType(String empId) throws SQLException, ClassNotFoundException;
    public String searchByempIdEmployeeSchId(String empId) throws SQLException, ClassNotFoundException;
}
