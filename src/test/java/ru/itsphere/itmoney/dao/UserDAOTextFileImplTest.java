package ru.itsphere.itmoney.dao;

import org.junit.After;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import ru.itsphere.itmoney.domain.User;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.PrintWriter;
import java.util.List;

/**
 * Тесты для UserDAOTextFileImpl
 * <p>
 * http://it-channel.ru/
 *
 * @author Budnikov Aleksandr
 */
public class UserDAOTextFileImplTest {
    public static final String TEMP_DIR_PATH = "temp";
    public static final String PATH_TO_TEST_STORE = TEMP_DIR_PATH + "/testStore.txt";

    public static final String SEPARATOR = UserDAOTextFileImpl.SEPARATOR;

    public static final String USER_ID_TITLE = "userId";
    public static final String USER_NAME_TITLE = "userName";
    public static final int USER_1_ID = 0;
    public static final String USER_1_NAME = "Sasha";
    public static final int USER_2_ID = 1;
    public static final String USER_2_NAME = "Dima";

    public static final int FIRST_USER_ID = USER_1_ID;
    public static final int LAST_USER_ID = USER_2_ID;
    public static final int INEXISTENT_USER_ID = 2;

    private UserDAO userDAO = new UserDAOTextFileImpl(PATH_TO_TEST_STORE);

    @Before
    public void setUp() {
        createTempDir();
        createTestTextFileAndWriteTestData();
    }

    private void createTempDir() {
        new File(TEMP_DIR_PATH).mkdir();
    }

    private void createTestTextFileAndWriteTestData() {
        try (PrintWriter writer = new PrintWriter(PATH_TO_TEST_STORE)) {
            writer.println(USER_ID_TITLE + SEPARATOR + USER_NAME_TITLE);
            writer.println(USER_1_ID + SEPARATOR + USER_1_NAME);
            writer.println(USER_2_ID + SEPARATOR + USER_2_NAME);
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }
    }

    @After
    public void tearDown() {
        deleteTestTextFile();
        deleteTempDir();
    }

    private void deleteTestTextFile() {
        new File(PATH_TO_TEST_STORE).delete();
    }

    private void deleteTempDir() {
        new File(TEMP_DIR_PATH).delete();
    }

    @Test
    public void testGetByIdExistedUser() throws Exception {
        int id = USER_1_ID;
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
    public void testUpdateSuccessfully() throws Exception {
        User originalUser = userDAO.getById(USER_1_ID);
        String newName = "Alex";
        userDAO.update(new User(originalUser.getId(), newName));
        User updatedUser = userDAO.getById(USER_1_ID);
        Assert.assertNotNull("User was not updated", updatedUser);
        Assert.assertEquals("Saved user id " + updatedUser.getId() + " != " + originalUser.getId(), updatedUser.getId(), originalUser.getId());
        Assert.assertEquals("Saved user name " + updatedUser.getName() + " != " + newName, updatedUser.getName(), newName);
        Assert.assertNotEquals("Saved user name " + updatedUser.getName() + " == " + originalUser.getName(), updatedUser.getName(), originalUser.getName());
    }

    // ожидаем что метод выбросит иксл
    @Test(expected = RuntimeException.class)
    public void testUpdateFail() throws Exception {
        // пытаемся обновить несуществующего пользователя
        userDAO.update(new User(INEXISTENT_USER_ID, ""));
    }

    @Test
    public void testSaveSuccessfully() throws Exception{
        int expectedUserId = INEXISTENT_USER_ID;
        User testUser = new User("Nico");
        User returnedUser = userDAO.save(testUser);
        User expectedUser = new User(expectedUserId, "Nico");
        Assert.assertNotNull("Method returns null", returnedUser);
        Assert.assertEquals("User name != " + returnedUser, expectedUser, returnedUser);
        Assert.assertEquals("User id" + expectedUser + "!="  + returnedUser, expectedUser, returnedUser);
    }

    @Test
    public void testGetAllSuccessFully() throws Exception {
        int expectedUserId = INEXISTENT_USER_ID;
        List<User> actualList = userDAO.getAll();
        Assert.assertNotNull("Method returns null", actualList);
        Assert.assertEquals("Actual and expected lists have different size", expectedUserId, actualList.size());
        Assert.assertEquals("Incorrect user from returned list", new  User(USER_1_ID, USER_1_NAME), actualList.get(0));
        Assert.assertEquals("Incorrect user from returned list", new  User(USER_2_ID, USER_2_NAME), actualList.get(1));
    }

    @Test
    public void testGetAllEmpty() throws Exception {
        clearTextFile();
        List<User> actualUserList = userDAO.getAll();
        Assert.assertNotNull("List = null", actualUserList);
        Assert.assertTrue("List is not empty", actualUserList.isEmpty());
    }

    private void clearTextFile() {
        deleteTestTextFile();
        createEmptyTestTextFile();
    }

    private void createEmptyTestTextFile() {
        try (PrintWriter writer = new PrintWriter(PATH_TO_TEST_STORE)){
            writer.println(USER_ID_TITLE + SEPARATOR + USER_NAME_TITLE);
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }
    }

    @Test
    public void testDeleteByIdSuccessfully() throws Exception {
        int id1 = USER_1_ID;
        int id2 = USER_2_ID;
        userDAO.deleteById(id1);
        userDAO.deleteById(id2);
        User user1 = userDAO.getById(id1);
        User user2 = userDAO.getById(id2);
        Assert.assertNull("User != null", user1);
        Assert.assertNull("User != null", user2);
//        userDAO.deleteById(USER_1_ID);
//        Assert.assertNull("User is not deleted", userDAO.getById(USER_1_ID));
//        userDAO.deleteById(USER_2_ID);
//        Assert.assertNull("User is not deleted", userDAO.getById(USER_2_ID));
    }
    @Test(expected = RuntimeException.class)
    public void testDeleteByIdFail() throws Exception {
        userDAO.deleteById(INEXISTENT_USER_ID);
    }


    /**
     * Эти тест не удалять и не трогать!
     * Нужен для контроля ваших ошибок
     *
     * @throws Exception
     */
    @Test
    public void testDeleteByIdDeleteFirstDeleteLast() throws Exception {
        // Был баг. После удаления первого пользователя, удаление последнего выбросит исключение.
        userDAO.deleteById(FIRST_USER_ID);
        userDAO.deleteById(LAST_USER_ID);
    }

    /**
     * Эти тест не удалять и не трогать!
     * Нужен для контроля ваших ошибок
     *
     * @throws Exception
     */
    @Test
    public void testDeleteByIdSaveFirstUser() throws Exception {
        // Был баг. После удаления всех пользователей, сохранение нового - выбрасывает исключение.
        // Или когда просто сохраняешь первого пользователя.
        userDAO.deleteById(USER_1_ID);
        userDAO.deleteById(USER_2_ID);
        userDAO.save(new User(USER_1_ID, USER_1_NAME));
    }

    /**
     * Эти тест не удалять и не трогать!
     * Нужен для контроля ваших ошибок
     *
     * @throws Exception
     */
    @Test
    public void testUpdateDeleteFirstUpdateLast() throws Exception {
        // Был баг. После удаления первого пользователя, изменение последнего выбросит исключение.
        userDAO.deleteById(FIRST_USER_ID);
        userDAO.update(new User(LAST_USER_ID, ""));
    }
}
