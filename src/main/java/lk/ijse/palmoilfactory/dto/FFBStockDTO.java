package lk.ijse.palmoilfactory.dto;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class FFBStockDTO {
    private String stockId;
    private Double ffbInput;
    private String date;
    private String time;
    private String supId;
}
