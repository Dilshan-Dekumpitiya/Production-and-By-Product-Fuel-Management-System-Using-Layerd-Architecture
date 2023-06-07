package lk.ijse.palmoilfactory.bo.custom.impl;

import lk.ijse.palmoilfactory.bo.custom.UserBO;
import lk.ijse.palmoilfactory.dao.DAOFactory;
import lk.ijse.palmoilfactory.dao.custom.UserDAO;
import lk.ijse.palmoilfactory.dto.UserDTO;
import lk.ijse.palmoilfactory.entity.User;

import java.sql.SQLException;

public class UserBOImpl implements UserBO {

    private UserDAO userDAO= DAOFactory.getInstance().getDAO(DAOFactory.DAOTypes.USER);
    @Override
    public boolean userCheckedInDB(UserDTO userDTO) throws SQLException, ClassNotFoundException {
        return userDAO.checkedInDB(new User(userDTO.getUserId(),userDTO.getUserName(),userDTO.getUserPassword()));
    }
}
