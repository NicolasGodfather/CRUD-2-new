import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import ru.itsphere.itmoney.dao.UserDAO;
import ru.itsphere.itmoney.dao.UserDAOHashMapImpl;
import ru.itsphere.itmoney.domain.User;

import java.util.HashMap;
import java.util.List;
import java.util.Map;


public class UserDAOHashMapImplTest {

    public static final int USER_1_ID = 0;
    public static final String USER_1_NAME = "Nicolas";
    public static final int USER_2_ID = 1;
    public static final String USER_2_NAME = "Dima";
    public static final int INEXISTENT_USER_ID = 2;
    private UserDAO userDAO;
    private Map<Integer, User> store;

    // аннотация значит что метод будет вызываться каждый раз перед выполн. теста
    @Before
    public void setUp() {
        // create HashMap
        store = getStore();
        userDAO = getUserDAOHashMap(store);
    }

    private Map<Integer, User> getStore() {
        Map<Integer, User> store = new HashMap<>();
        User user1 = new User(USER_1_ID, USER_1_NAME);
        User user2 = new User(USER_2_ID, USER_2_NAME);
        // разместили user1 и user1 в хранилище
        store.put(USER_1_ID, user1);
        store.put(USER_2_ID, user2);
        return store;
    }

    private UserDAO getUserDAOHashMap(Map<Integer, User> store) {
        return new UserDAOHashMapImpl(store);
    }

    @Test
    public void testGetByIdExistedUser() throws Exception{
        // id & name взяли с потолка
        int id = USER_1_ID;
        String name = USER_1_NAME;
        // нашли пользователя
        User user = userDAO.getById(id);
        // делаем проверки
        // вернувшийся объект не null
        Assert.assertNotNull("User with id " + id + " is null", user);
        // вернувшийся объект содержит те знач. кот мы ожидали
        Assert.assertEquals("User id " + user.getId() + " != " + id, user.getId(), id);
        Assert.assertEquals("User name" + user.getName() + " != " + id, user.getName(), name);
    }

    @Test
    public void testGetByIdNotExistedUser() throws Exception {
        // объявили id несущ. user
        int id = INEXISTENT_USER_ID;
        // пытались найти его
        User user = userDAO.getById(id);
        // убеждаемся в том что getById вернул null
        Assert.assertNull("User with id " + id + " is not null", user);
    }

    @Test
    public void testSaveSuccessfully() throws Exception{
        //Придумали имя
        String name = "Alina";
        // уже знаем что id new user is 2 первых 2 добавились в setUp
        int id = 2;
        //save document
        User user = userDAO.save(new User(name));
        //убедились что user ссылается не на null
        Assert.assertNotNull("User was not saved", user);
        //напрямую обращаемся к      "хранилищу" чтобы получить юзера
        User actualUser = store.get(id);
        //check that not null
        Assert.assertNotNull("User was not saved", actualUser);
        //check that user & actualUser такие как мы предпологали
        Assert.assertEquals("User id " + user.getId() + " != " + id, user.getId(), id);
        Assert.assertEquals("User name" + user.getName() + " != " + name, user.getName(), name);
        Assert.assertEquals("User id " + actualUser.getId() + " != " + id, actualUser.getId(), id);
        Assert.assertEquals("User name" + actualUser.getName() + " != " + name, actualUser.getName(), name);
    }

    @Test
    public void testGetAllSuccessfully() throws Exception {
        List<User> users = userDAO.getAll();
        Assert.assertNotNull("Method return null", users);
        Assert.assertEquals("Size of returned list is incorrect", store.size(), users.size());
        for (User user: users){
            Assert.assertEquals("Method return list with incorrect values ", store.get(user.getId()), user);
        }
    }

    @Test
    public void testGetAllEmpty() throws Exception {
        store.clear();
        Assert.assertNotNull("List isn't empty", userDAO);
    }

    @Test
    public void testUpdateSuccessfully() throws Exception {
        User originalUser = new User(USER_1_ID, USER_2_NAME);
        User userWithChangedName = new User(originalUser.getId(), "Alex");
        User returnedUser = userDAO.update(userWithChangedName);
        Assert.assertNotNull("User was not updated", returnedUser);

        User actualUser = store.get(originalUser.getId());

        Assert.assertEquals("User id " + returnedUser.getId() + " != "
                + actualUser.getId(), returnedUser.getId(), actualUser.getId());
        Assert.assertEquals("User name " + returnedUser.getName() + " != "
                + actualUser.getName(), returnedUser.getName(), actualUser.getName());

        Assert.assertEquals("User id " + originalUser.getId() + " != "
                + actualUser.getId(), originalUser.getId(), actualUser.getId());
        Assert.assertNotEquals("User name " + originalUser.getName() + " == "
                + actualUser.getName(), originalUser.getName(), actualUser.getName());
    }

    @Test(expected = RuntimeException.class)
    public void testUpdateFail() throws Exception{
        userDAO.update(new User(INEXISTENT_USER_ID, "test_name"));
    }

    @Test
    public void testDeleteByIdSuccessfully() throws Exception {
        userDAO.deleteById(USER_1_ID);
        Assert.assertFalse("Not delete " + USER_1_NAME, store.containsKey(USER_1_ID));
        userDAO.deleteById(USER_2_ID);
        Assert.assertFalse("Not delete " + USER_2_NAME, store.containsKey(USER_2_ID));
    }

    @Test(expected = RuntimeException.class)
    public void testDeleteByIdFail() throws Exception{
        userDAO.deleteById(INEXISTENT_USER_ID);
    }
}
