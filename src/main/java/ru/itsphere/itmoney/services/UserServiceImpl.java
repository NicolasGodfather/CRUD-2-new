package ru.itsphere.itmoney.services;

import ru.itsphere.itmoney.dao.UserDAO;
import ru.itsphere.itmoney.domain.User;

import java.util.List;

/**
 * Реализация UserService
 * <p>
 * http://it-channel.ru/
 *
 * @author Budnikov Aleksandr
 */
public class UserServiceImpl implements UserService {
    private UserDAO userDAO;

    public User getById(int id) {
        try {
            return userDAO.getById(id);
        } catch (Exception e) {
            // TODO add code
        }
        return null;
    }

    @Override
    public void save(User user) {
        try {
            userDAO.save(user);
        } catch (Exception e) {
            // TODO add code
        }
    }

    @Override
    public void update(User user) {
        try {
            userDAO.update(user);
        } catch (Exception e) {
            // TODO add code
        }
    }

    @Override
    public List<User> getAll() {
        try {
            return userDAO.getAll();
        } catch (Exception e) {
            // TODO add code
        }
        return null;
    }

    @Override
    public void deleteById(int id) {
        try {
            userDAO.deleteById(id);
        } catch (Exception e) {
            // TODO add code
        }
    }

    public void setUserDAO(UserDAO userDAO) {
        this.userDAO = userDAO;
    }
}
