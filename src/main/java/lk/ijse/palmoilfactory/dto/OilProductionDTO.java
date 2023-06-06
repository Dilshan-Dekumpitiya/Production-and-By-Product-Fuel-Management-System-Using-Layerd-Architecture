package lk.ijse.palmoilfactory.dto;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class OilProductionDTO {
    private String stockID;
    private double totalEBLiquid;
    private double totalPressLiquid;
    private String date;
    private String time;
}
