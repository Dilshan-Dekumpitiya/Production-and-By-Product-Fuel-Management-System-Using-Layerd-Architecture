package lk.ijse.palmoilfactory.entity;

import lombok.*;

@Data
@AllArgsConstructor
public class Stock {
    private String stockId;
    private Double ffbInput;
    private String date;
    private String time;
    private String supId;
}
