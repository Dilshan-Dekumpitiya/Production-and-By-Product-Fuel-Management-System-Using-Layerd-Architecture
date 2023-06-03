package lk.ijse.palmoilfactory.dto;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class EmployeeDTO {
    private String empId;
    private String empName;
    private String empAddress;
    private String empContact;
    private double empSalary;
    private String empType;
    private String empSchId;
}
