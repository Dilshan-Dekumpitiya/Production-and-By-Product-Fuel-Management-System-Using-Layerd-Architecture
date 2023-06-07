package lk.ijse.palmoilfactory.bo.custom;

import lk.ijse.palmoilfactory.bo.SuperBO;
import lk.ijse.palmoilfactory.dto.UserDTO;
import lk.ijse.palmoilfactory.entity.User;

import java.sql.SQLException;

public interface UserBO extends SuperBO {
    public boolean userCheckedInDB(UserDTO userDTO) throws SQLException, ClassNotFoundException;
}
