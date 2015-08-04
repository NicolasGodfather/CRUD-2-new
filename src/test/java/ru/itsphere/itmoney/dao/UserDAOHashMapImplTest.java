package ru.itsphere.itmoney.dao;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import ru.itsphere.itmoney.domain.User;

import java.util.HashMap;
import java.util.Map;

/**
 * Тесты для UserDAOHashMapImpl
 * <p>
 * http://it-channel.ru/
 *
 * @author Budnikov Aleksandr
 */
public class UserDAOHashMapImplTest {

    public static final int USER_1_ID = 0;
    public static final String USER_1_NAME = "Sasha";
    public static final int USER_2_ID = 1;
    public static final String USER_2_NAME = "Dima";
    public static final int INEXISTENT_USER_ID = 2;
    private UserDAO userDAO;
    private Map<Integer, User> store;

    @Before
    public void setUp() {
        store = getStore();
        userDAO = getUserDAOHashMap(store);
    }

    private Map<Integer, User> getStore() {
        Map<Integer, User> store = new HashMap<>();
        User user1 = new User(USER_1_ID, USER_1_NAME);
        store.put(USER_1_ID, user1);
        User user2 = new User(USER_2_ID, USER_2_NAME);
        store.put(USER_2_ID, user2);
        return store;
    }

    private UserDAO getUserDAOHashMap(Map<Integer, User> store) {
        return new UserDAOHashMapImpl(store);
    }

    @Test
    public void testGetByIdExistedUser() throws Exception {
        int id = 0;
        String name = USER_1_NAME;
        User user = userDAO.getById(id);
        Assert.assertNotNull("User with id " + id + " is null", user);
        Assert.assertEquals("User id " + user.getId() + " != " + id, user.getId(), id);
        Assert.assertEquals("User name " + user.getName() + " != " + name, user.getName(), name);
    }

    @Test
    public void testGetByIdNotExistedUser() throws Exception {
        int id = INEXISTENT_USER_ID;
        User user = userDAO.getById(id);
        Assert.assertNull("User with id " + id + " is not null", user);
    }

    @Test
    public void testSaveSuccessfully() throws Exception {
        String name = "Alex";
        int id = 2;
        User user = userDAO.save(new User(name));
        Assert.assertNotNull("User was not saved", user);
        User actualUser = store.get(id);
        Assert.assertNotNull("User was not saved", actualUser);
        Assert.assertEquals("User id " + user.getId() + " != " + id, user.getId(), id);
        Assert.assertEquals("User name " + user.getName() + " != " + name, user.getName(), name);
        Assert.assertEquals("User id " + actualUser.getId() + " != " + id, actualUser.getId(), id);
        Assert.assertEquals("User name " + actualUser.getName() + " != " + name, actualUser.getName(), name);
    }
}
