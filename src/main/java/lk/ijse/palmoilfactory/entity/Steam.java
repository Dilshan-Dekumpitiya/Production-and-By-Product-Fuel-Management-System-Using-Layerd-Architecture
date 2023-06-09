package lk.ijse.palmoilfactory.entity;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class Steam implements SuperEntity{
    private String stockId;
    private double fruitOutput;
    private double emptyBunchoutput;
    private String date;
    private String time;
}
