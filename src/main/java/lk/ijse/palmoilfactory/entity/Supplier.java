package lk.ijse.palmoilfactory.entity;

import lombok.*;

@Data
@AllArgsConstructor
public class Supplier implements SuperEntity{
    private String supId;
    private String supName;
    private String supAddress;
    private String supContact;

}
