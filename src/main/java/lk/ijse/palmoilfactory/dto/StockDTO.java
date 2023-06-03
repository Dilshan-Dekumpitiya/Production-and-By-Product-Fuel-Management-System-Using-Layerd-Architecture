package lk.ijse.palmoilfactory.dto;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class StockDTO {
    private String stockId;
    private Double ffbInput;
    private String date;
    private String time;
    private String supId;
}
