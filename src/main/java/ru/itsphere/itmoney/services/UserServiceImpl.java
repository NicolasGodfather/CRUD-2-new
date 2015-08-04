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

    public User getById(int id) throws Exception {
        return userDAO.getById(id);
    }

    @Override
    public User save(User user) throws Exception {
        return userDAO.save(user);
    }

    @Override
    public User update(User user) throws Exception {
        return userDAO.update(user);
    }

    @Override
    public List<User> getAll() throws Exception {
        return userDAO.getAll();
    }

    @Override
    public void deleteById(int id) throws Exception {
        userDAO.deleteById(id);
    }

    public void setUserDAO(UserDAO userDAO) {
        this.userDAO = userDAO;
    }
}
