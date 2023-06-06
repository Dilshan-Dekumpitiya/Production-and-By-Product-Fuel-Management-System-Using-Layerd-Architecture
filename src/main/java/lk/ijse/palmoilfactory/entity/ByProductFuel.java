package lk.ijse.palmoilfactory.entity;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class ByProductFuel implements SuperEntity{
    private String stockId;
    private double bunchFiber;
    private double shell;
    private double pressFiber;
    private String date;
    private String time;
}
