package lk.ijse.palmoilfactory.dto;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class SteamDTO {
    private String stockId;
    private double fruitOutput;
    private double emptyBunchoutput;
    private String date;
    private String time;
}
