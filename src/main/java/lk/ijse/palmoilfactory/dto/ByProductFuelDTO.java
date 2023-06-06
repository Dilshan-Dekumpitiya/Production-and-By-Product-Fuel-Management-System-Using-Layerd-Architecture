package lk.ijse.palmoilfactory.dto;

import lk.ijse.palmoilfactory.entity.SuperEntity;
import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class ByProductFuelDTO {
    private String stockId;
    private double bunchFiber;
    private double shell;
    private double pressFiber;
    private String date;
    private String time;
}
