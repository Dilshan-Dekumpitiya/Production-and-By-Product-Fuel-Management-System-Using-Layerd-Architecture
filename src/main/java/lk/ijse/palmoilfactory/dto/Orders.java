package lk.ijse.palmoilfactory.dto;

import lombok.AllArgsConstructor;
import lombok.Data;

import java.util.Date;

@Data
@AllArgsConstructor
public class Orders {
    private String orderId;
    private String orderDate;
    private Double quantity;
    private Double price;
}
