package lk.ijse.palmoilfactory.entity;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class OilProduction implements SuperEntity{
    private String stockID;
    private double totalEBLiquid;
    private double totalPressLiquid;
    private String date;
    private String time;
}
