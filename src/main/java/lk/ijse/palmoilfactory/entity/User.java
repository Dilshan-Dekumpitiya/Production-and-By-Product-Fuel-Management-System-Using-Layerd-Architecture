package lk.ijse.palmoilfactory.entity;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class User implements SuperEntity{
    private String userId;
    private String userName;
    private String userPassword;
}
