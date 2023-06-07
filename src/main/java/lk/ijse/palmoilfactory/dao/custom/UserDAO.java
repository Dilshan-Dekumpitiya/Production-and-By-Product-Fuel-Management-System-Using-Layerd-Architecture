package lk.ijse.palmoilfactory.dao.custom;

import lk.ijse.palmoilfactory.dao.CrudDAO;
import lk.ijse.palmoilfactory.entity.User;

import java.sql.SQLException;

public interface UserDAO extends CrudDAO<User> {
    public boolean checkedInDB(User user) throws SQLException, ClassNotFoundException;
}
