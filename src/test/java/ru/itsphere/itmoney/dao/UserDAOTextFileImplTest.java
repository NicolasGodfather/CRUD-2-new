package ru.itsphere.itmoney.dao;

import org.junit.After;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import ru.itsphere.itmoney.domain.User;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.PrintWriter;

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

    private UserDAO userDAO;

    @Before
    public void setUp() {
        createTempDir();
        createTestTextFileAndWriteTestData();
        userDAO = getUserDAO();
    }

    private UserDAO getUserDAO() {
        UserDAOTextFileImpl userDAO = new UserDAOTextFileImpl(PATH_TO_TEST_STORE);
        userDAO.setReaderFactory(new DataReaderFactory(PATH_TO_TEST_STORE));
        return userDAO;
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

    private void createTempDir() {
        new File(TEMP_DIR_PATH).mkdir();
    }

    @After
    public void tearDown() {
        deleteTestTextFile();
        deleteTempDir();
    }

    private void deleteTempDir() {
        new File(TEMP_DIR_PATH).delete();
    }

    private void deleteTestTextFile() {
        new File(PATH_TO_TEST_STORE).delete();
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

    @Test(expected = RuntimeException.class)
    public void testUpdateFail() throws Exception {
        userDAO.update(new User(INEXISTENT_USER_ID, ""));
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
