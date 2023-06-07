package lk.ijse.palmoilfactory.dto;

import lk.ijse.palmoilfactory.entity.SuperEntity;
import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class UserDTO{
    private String userId;
    private String userName;
    private String userPassword;
}
