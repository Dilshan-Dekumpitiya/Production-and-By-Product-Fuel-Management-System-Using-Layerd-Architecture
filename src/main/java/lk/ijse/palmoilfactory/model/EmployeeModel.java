package lk.ijse.palmoilfactory.model;

import lk.ijse.palmoilfactory.dto.Employee;
import lk.ijse.palmoilfactory.dto.Stock;
import lk.ijse.palmoilfactory.dto.Supplier;
import lk.ijse.palmoilfactory.util.CrudUtil;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class EmployeeModel {
    public static List<String> getSchIDs() throws SQLException, ClassNotFoundException {
        List<String> schIds = new ArrayList<>();

        String sql = "SELECT schId FROM schedule";

        ResultSet resultSet= CrudUtil.execute(sql);

        while(resultSet.next()) {
            schIds.add(resultSet.getString(1));
        }
        return schIds;
    }


    public static boolean addEmployee(String empId, String empName, String empAddress, String empContact, double empSalary, String empType, String empSchId) throws SQLException, ClassNotFoundException {
        String sql="INSERT INTO employee(empId,empName,empAddress,contact,salary,type,schId) VALUES(?,?,?,?,?,?,?)";

        return CrudUtil.execute(sql,empId,empName,empAddress,empContact,empSalary,empType,empSchId);

    }

    public static Employee searchEmployee(String empId) throws SQLException, ClassNotFoundException {
        String sql="SELECT * FROM employee WHERE empId=?";

        ResultSet resultSet = CrudUtil.execute(sql, empId);

        if(resultSet.next()) {
            String  employeeId = resultSet.getString(1);
            String empName = resultSet.getString(2);
            String empAddress = resultSet.getString(3);
            String empContact = resultSet.getString(4);
            double empSalary = Double.parseDouble(resultSet.getString(5));
            String empType = resultSet.getString(6);
            String empSchId = resultSet.getString(7);

            return new Employee(employeeId,empName,empAddress,empContact,empSalary,empType,empSchId);
        }
        return null;
    }

    public static String searchByempIdEmployeeType(String empId) throws SQLException, ClassNotFoundException {
        String sql="SELECT type FROM employee WHERE empId = ?";

        ResultSet resultSet=CrudUtil.execute(sql,empId);

        if(resultSet.next()){
            return resultSet.getString("type");
        }
        return null;

    }

    public static String searchByempIdEmployeeSchId(String empId) throws SQLException, ClassNotFoundException {
        String sql="SELECT schId FROM employee WHERE empId = ?";

        ResultSet resultSet=CrudUtil.execute(sql,empId);

        if(resultSet.next()){
            return resultSet.getString("schId");
        }
        return null;

    }

    public static boolean updateEmployee(String empId, String empName, String empAddress, String empContact, double empSalary, String empType, String empSchId) throws SQLException, ClassNotFoundException {

        String sql="UPDATE employee SET empName = ?, empAddress = ?, contact = ? , salary = ?, type = ? , schId=? WHERE empId = ?";

        return CrudUtil.execute(sql,  empName, empAddress, empContact,empSalary,empType,empSchId,empId);
    }

    public static boolean deleteEmployee(String empId) throws SQLException, ClassNotFoundException {
        String sql="DELETE FROM employee WHERE empId=? ";

        return CrudUtil.execute(sql,empId);

    }

    public static List<Employee> getAll() throws SQLException, ClassNotFoundException {
        String sql = "SELECT * FROM employee";

        List<Employee> empData = new ArrayList<>();

        ResultSet resultSet = CrudUtil.execute(sql);
        while (resultSet.next()) {
            empData.add(new Employee(
                    resultSet.getString(1),
                    resultSet.getString(2),
                    resultSet.getString(3),
                    resultSet.getString(4),
                    resultSet.getDouble(5),
                    resultSet.getString(6),
                    resultSet.getString(7)
            ));
        }
        return empData;

    }
}
