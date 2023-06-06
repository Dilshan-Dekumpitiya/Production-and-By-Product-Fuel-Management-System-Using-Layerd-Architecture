package lk.ijse.palmoilfactory.dto;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class OrderDTO {
    private String orderId;
    private String orderDate;
    private Double quantity;
    private Double price;
}
