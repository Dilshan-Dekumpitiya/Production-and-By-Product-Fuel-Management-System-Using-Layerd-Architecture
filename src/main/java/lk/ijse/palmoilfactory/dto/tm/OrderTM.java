package lk.ijse.palmoilfactory.dto.tm;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class OrderTM {
    private String orderId;
    private String date;
    private Double qty;
    private Double price;
}
