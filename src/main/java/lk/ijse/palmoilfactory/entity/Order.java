package lk.ijse.palmoilfactory.entity;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class Order implements SuperEntity{
    private String orderId;
    private String orderDate;
    private Double quantity;
    private Double price;
}
