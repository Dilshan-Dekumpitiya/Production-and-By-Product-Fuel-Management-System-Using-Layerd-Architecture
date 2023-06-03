package lk.ijse.palmoilfactory.dto.tm;

import com.jfoenix.controls.JFXButton;
import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class EmployeeTM {
    private String empId;
    private String empName;
    private String empAddress;
    private String empContact;
    private Double empSalary;
    private String empType;
    private String schId;
    private JFXButton btn;
}
