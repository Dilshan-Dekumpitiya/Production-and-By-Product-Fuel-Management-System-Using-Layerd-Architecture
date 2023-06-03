package lk.ijse.palmoilfactory.dto.tm;

import com.jfoenix.controls.JFXButton;
import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class StockTM {
    private String stockId;
    private Double ffbInput;
    private String date;
    private String time;
    private String supplierID;
    private JFXButton btn;
}
