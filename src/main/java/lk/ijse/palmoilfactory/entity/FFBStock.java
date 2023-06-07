package lk.ijse.palmoilfactory.entity;

import lombok.*;

@Data
@AllArgsConstructor
public class FFBStock implements SuperEntity{
    private String stockId;
    private Double ffbInput;
    private String date;
    private String time;
    private String supId;
}
