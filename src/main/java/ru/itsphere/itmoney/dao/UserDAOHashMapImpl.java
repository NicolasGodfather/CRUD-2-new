package ru.itsphere.itmoney.dao;

import ru.itsphere.itmoney.domain.User;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

/**
 * Реализация UserDAO через HashMap
 * <p>
 * http://it-channel.ru/
 *
 * @author Budnikov Aleksandr
 */
public class UserDAOHashMapImpl implements UserDAO {
    private Map<Integer, User> store;

    public UserDAOHashMapImpl(Map<Integer, User> store) {
        super();
        this.store = store;
    }

    @Override
    public User getById(int id) {
        return store.get(id);
    }

    @Override
    public User save(User user) throws Exception {
        //у сохраненного пользователя меняем id на новый
        user.setId(generateNewId());
        //add new user in HashMap
        store.put(user.getId(), user);
        //вернули
        return user;
    }

    private int generateNewId() {
        int maxId = 0;
        //перебором находим макс id среди всех users in store
        for (Integer id : store.keySet()) {
            if (id > maxId) {
                maxId = id;
            }
        }
        // max id еще не уникальный, исправляем это прибавляя 1
        return maxId + 1;
    }

    @Override
    public User update(User user) throws Exception {
        User targetUser = store.get(user.getId());
        checkUser(targetUser);
        targetUser.setName(user.getName());
        return targetUser;
    }
    // check that user didn't found
    private void checkUser(User user) {
        if (user == null) {
            throw new RuntimeException("The user was not found");
        }
    }

    @Override
    public List<User> getAll() throws Exception {
        return new ArrayList<>(store.values());
    }

    @Override
    public void deleteById(int id) throws Exception {
        checkUserById(store.remove(id));
    }

    private void checkUserById(User user) {
        if (user == null) {
            throw new RuntimeException("The user was not found");
        }
    }

}
