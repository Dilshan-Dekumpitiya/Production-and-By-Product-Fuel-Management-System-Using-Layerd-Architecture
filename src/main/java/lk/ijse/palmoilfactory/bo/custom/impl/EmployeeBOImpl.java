package lk.ijse.palmoilfactory.bo.custom.impl;

import lk.ijse.palmoilfactory.bo.custom.EmployeeBO;
import lk.ijse.palmoilfactory.dao.DAOFactory;
import lk.ijse.palmoilfactory.dao.custom.EmployeeDAO;
import lk.ijse.palmoilfactory.dto.EmployeeDTO;
import lk.ijse.palmoilfactory.dto.SupplierDTO;
import lk.ijse.palmoilfactory.entity.Employee;
import lk.ijse.palmoilfactory.entity.Supplier;

import java.sql.SQLException;
import java.util.ArrayList;

public class EmployeeBOImpl implements EmployeeBO {

    private EmployeeDAO employeeDAO= DAOFactory.getInstance().getDAO(DAOFactory.DAOTypes.EMPLOYEE);
    @Override
    public ArrayList<EmployeeDTO> getAllEmployee() throws SQLException, ClassNotFoundException {
        ArrayList<EmployeeDTO> allEmployees= new ArrayList<>();
        ArrayList<Employee> all = employeeDAO.getAll();
        for (Employee employee : all) {
            allEmployees.add(
                    new EmployeeDTO(
                            employee.getEmpId(),
                            employee.getEmpName(),
                            employee.getEmpAddress(),
                            employee.getEmpContact(),
                            employee.getEmpSalary(),
                            employee.getEmpType(),
                            employee.getEmpSchId()
                    ));
        }
        return allEmployees;
    }

    @Override
    public boolean addEmployee(EmployeeDTO dto) throws SQLException, ClassNotFoundException {
        return employeeDAO.add(
                new Employee(
                        dto.getEmpId(),
                        dto.getEmpName(),
                        dto.getEmpAddress(),
                        dto.getEmpContact(),
                        dto.getEmpSalary(),
                        dto.getEmpType(),
                        dto.getEmpSchId()
                ));
    }

    @Override
    public boolean updateEmployee(EmployeeDTO dto) throws SQLException, ClassNotFoundException {
        return employeeDAO.update(
                new Employee(
                        dto.getEmpId(),
                        dto.getEmpName(),
                        dto.getEmpAddress(),
                        dto.getEmpContact(),
                        dto.getEmpSalary(),
                        dto.getEmpType(),
                        dto.getEmpSchId()
                ));
    }

    @Override
    public boolean existEmployee(String id) throws SQLException, ClassNotFoundException {
        return false;
    }

    @Override
    public boolean deleteEmployee(String id) throws SQLException, ClassNotFoundException {
        return employeeDAO.delete(id);
    }

    @Override
    public String generateNewEmployeeID() throws SQLException, ClassNotFoundException {
        return null;
    }

    @Override
    public EmployeeDTO searchEmployee(String id) throws SQLException, ClassNotFoundException {
        Employee employee=employeeDAO.search(id);
        return (employee!=null) ? new EmployeeDTO(
                employee.getEmpId(),
                employee.getEmpName(),
                employee.getEmpAddress(),
                employee.getEmpContact(),
                employee.getEmpSalary(),
                employee.getEmpType(),
                employee.getEmpSchId()) : null;

    }

    /*@Override
    public ArrayList<String> getIDs() throws SQLException, ClassNotFoundException {
        return employeeDAO.;
    }*/

    @Override
    public ArrayList<String> getSchIDs() throws SQLException, ClassNotFoundException {
        return employeeDAO.getSchIDs();
    }

    @Override
    public String searchByempIdEmployeeType(String empId) throws SQLException, ClassNotFoundException {
        return employeeDAO.searchByempIdEmployeeType(empId);
    }

    @Override
    public String searchByempIdEmployeeSchId(String empId) throws SQLException, ClassNotFoundException {
        return employeeDAO.searchByempIdEmployeeSchId(empId);
    }
}
