package lk.ijse.palmoilfactory.dto.tm;

import com.jfoenix.controls.JFXButton;
import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class SupplierTM {
    private String supId;
    private String supName;
    private String supAddress;
    private String supContact;
    private JFXButton btn;
}
