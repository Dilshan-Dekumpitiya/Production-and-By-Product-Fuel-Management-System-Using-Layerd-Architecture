package lk.ijse.palmoilfactory.dao.custom.impl;

import lk.ijse.palmoilfactory.dao.SQLUtil;
import lk.ijse.palmoilfactory.dao.custom.EmployeeDAO;
import lk.ijse.palmoilfactory.entity.Employee;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class EmployeeDAOImpl implements EmployeeDAO {
    @Override
    public ArrayList<Employee> getAll() throws SQLException, ClassNotFoundException {
        String sql = "SELECT * FROM employee";

        ArrayList<Employee> empData = new ArrayList<>();

        ResultSet resultSet = SQLUtil.execute(sql);
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

    @Override
    public boolean add(Employee entity) throws SQLException, ClassNotFoundException {
        String sql="INSERT INTO employee(empId,empName,empAddress,contact,salary,type,schId) VALUES(?,?,?,?,?,?,?)";

        return SQLUtil.execute(sql,entity.getEmpId(),entity.getEmpName(),entity.getEmpAddress(),entity.getEmpContact(),entity.getEmpSalary(),entity.getEmpType(),entity.getEmpSchId());
    }

    @Override
    public boolean update(Employee entity) throws SQLException, ClassNotFoundException {
        String sql="UPDATE employee SET empName = ?, empAddress = ?, contact = ? , salary = ?, type = ? , schId=? WHERE empId = ?";

        return SQLUtil.execute(sql,entity.getEmpName(),entity.getEmpAddress(),entity.getEmpContact(),entity.getEmpSalary(),entity.getEmpType(),entity.getEmpSchId(),entity.getEmpId());
    }

    @Override
    public boolean exist(String id) throws SQLException, ClassNotFoundException {
        return false;
    }

    @Override
    public String generateNewID() throws SQLException, ClassNotFoundException {
        return null;
    }

    @Override
    public boolean delete(String id) throws SQLException, ClassNotFoundException {
        String sql="DELETE FROM employee WHERE empId=? ";

        return SQLUtil.execute(sql,id);

    }

    @Override
    public Employee search(String id) throws SQLException, ClassNotFoundException {
        String sql="SELECT * FROM employee WHERE empId=?";

        ResultSet resultSet = SQLUtil.execute(sql, id);

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

    @Override
    public ArrayList<String> getSchIDs() throws SQLException, ClassNotFoundException {
        ArrayList<String> schIds = new ArrayList<>();

        String sql = "SELECT schId FROM schedule";

        ResultSet resultSet= SQLUtil.execute(sql);

        while(resultSet.next()) {
            schIds.add(resultSet.getString(1));
        }
        return schIds;
    }

    @Override
    public String searchByempIdEmployeeType(String empId) throws SQLException, ClassNotFoundException {
        String sql="SELECT type FROM employee WHERE empId = ?";

        ResultSet resultSet=SQLUtil.execute(sql,empId);

        if(resultSet.next()){
            return resultSet.getString("type");
        }
        return null;
    }

    @Override
    public String searchByempIdEmployeeSchId(String empId) throws SQLException, ClassNotFoundException {
        String sql="SELECT schId FROM employee WHERE empId = ?";

        ResultSet resultSet=SQLUtil.execute(sql,empId);

        if(resultSet.next()){
            return resultSet.getString("schId");
        }
        return null;

    }
}
