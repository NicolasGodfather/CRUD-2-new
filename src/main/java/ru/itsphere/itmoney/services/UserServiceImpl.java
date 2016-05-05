package ru.itsphere.itmoney.services;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import ru.itsphere.itmoney.dao.DAOException;
import ru.itsphere.itmoney.dao.UserDAO;
import ru.itsphere.itmoney.domain.User;

import java.util.List;

/**
 * Реализация UserService
 */
//add
@Service
public class UserServiceImpl implements UserService {
    /**
     * Подключили логгер к текущему классу
     */
    private static final Logger logger = LogManager.getLogger(UserServiceImpl.class);
    //add
    @Autowired
    private UserDAO userDAO;

    public User getById(int id) {
        try {
            User user = userDAO.getById(id);
            if(user == null){
                logger.warn("User with id '{}' was not found", id);
            }
            return user;
//            return userDAO.getById(id); // было так, поменяли выше, но это не обязательно,
        } catch (DAOException e) {
            throw new ServiceException(String.format("Getting user by id %s", id), e);
        }
    }

    @Override
    public void save(User user) {
        try {
            userDAO.save(user);
        } catch (DAOException e) {
            // TODO add code
            throw new ServiceException(String.format("Saving user %s", user.getId()), e);
        }
    }

    @Override
    public void update(User user) {
        try {
            userDAO.update(user);
        } catch (DAOException e) {
            // TODO add code
            throw new ServiceException(String.format("Updating user %s", user.getName()), e);
        }
    }

    @Override
    public List<User> getAll() {
        try {
            return userDAO.getAll();
        } catch (DAOException e) {
            // TODO add code
            throw new ServiceException(("Getting all users"), e);
        }
    }

    @Override
    public void deleteById(int id) {
        try {
            userDAO.deleteById(id);
        } catch (DAOException e) {
            // TODO add code
        }
    }

    @Override
    public int getCount() {
        try {
            return userDAO.getAll().size();
        } catch (Exception e) {
            throw new ServiceException("Getting count of users in list", e);
        }
    }

    //add task 16
    @Override
    public List<User> findUsersByQuery(String query) {
        try {
            return userDAO.findUsersByQuery(query);
        } catch (Exception e) {
            throw new ServiceException(String.format("Problem in findUsersByQuery %s", query), e);
        }
    }

    public void setUserDAO(UserDAO userDAO) {
        this.userDAO = userDAO;
    }
}
