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

    @Override
    public User getById(int id) {
        return store.get(id);
    }

    @Override
    public User save(User user) throws Exception {
        user.setId(generateNewId());
        store.put(user.getId(), user);
        return user;
    }

    private int generateNewId() {
        int maxId = 0;
        for (Integer id : store.keySet()) {
            if (id > maxId) {
                maxId = id;
            }
        }
        return maxId + 1;
    }

    @Override
    public User update(User user) throws Exception {
        User targetUser = store.get(user.getId());
        checkUser(targetUser);
        targetUser.setName(user.getName());
        return targetUser;
    }

    @Override
    public List<User> getAll() throws Exception {
        return new ArrayList<>(store.values());
    }

    @Override
    public void deleteById(int id) throws Exception {
        checkUser(store.remove(id));
    }

    private void checkUser(User user) {
        if (user == null) {
            throw new RuntimeException("The user was not found");
        }
    }

    public void setStore(Map<Integer, User> store) {
        this.store = store;
    }
}
