package ru.itsphere.itmoney.dao;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import ru.itsphere.itmoney.domain.User;

import java.util.ArrayList;
import java.util.List;

/**
 * Тесты для UserDAOTextFileImpl
 * <p>
 * http://it-channel.ru/
 *
 * @author Budnikov Aleksandr
 */
public class UserDAOTextFileImplTest {

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
    private List<String> lines;

    @Before
    public void setUp() {
        lines = getLines();
        userDAO = getUserDAOTextFile(lines);
    }

    private UserDAO getUserDAOTextFile(List<String> lines) {
        ReaderFactory readerFactory = new MockDataReaderFactory(lines);
        WriterFactory writerFactory = new MockDataWriterFactory(lines);
        UserDAOTextFileImpl userDAOTextFile = new UserDAOTextFileImpl();
        userDAOTextFile.setReaderFactory(readerFactory);
        userDAOTextFile.setWriterFactory(writerFactory);
        return userDAOTextFile;
    }

    private List<String> getLines() {
        List<String> lines = new ArrayList<>();
        lines.add(USER_ID_TITLE + SEPARATOR + USER_NAME_TITLE);
        lines.add(USER_1_ID + SEPARATOR + USER_1_NAME);
        lines.add(USER_2_ID + SEPARATOR + USER_2_NAME);
        return lines;
    }

    private User convertLineToUser(String line) {
        String[] constructorArgs = line.split(SEPARATOR);
        int id = Integer.parseInt(constructorArgs[0]);
        String name = constructorArgs[1];
        return new User(id, name);
    }

    @Test
    public void testGetByIdExistedUser() throws Exception {
        String firstLineFromStore = lines.get(USER_1_ID + 1);
        User firstReturnedUser = userDAO.getById(USER_1_ID);
        Assert.assertNotNull("User with id = " + USER_1_ID + " is null", firstReturnedUser);
        Assert.assertEquals("Method returns incorrect user", firstLineFromStore, firstReturnedUser.getId()
                            + SEPARATOR + firstReturnedUser.getName());

        String secondLineFromStore = lines.get(USER_2_ID + 1);
        User secondReturnedUser = userDAO.getById(USER_2_ID);
        Assert.assertNotNull("User with id = " + USER_2_ID + " is null", secondReturnedUser);
        Assert.assertEquals("Method returns incorrect user", secondLineFromStore, firstReturnedUser.getId()
                            + SEPARATOR + firstReturnedUser.getName());
    }

    @Test
    public void testGetByIdNotExistedUser() throws Exception {
        int id = INEXISTENT_USER_ID;
        User user = userDAO.getById(id);
        Assert.assertNull("User with id " + id + " is not null", user);
    }

    @Test
    public void testUpdateSuccessfully() throws Exception {
        User originalUser = new User(USER_1_ID, USER_2_NAME);
        User userWithChangeName = new User(originalUser.getId(), "Nico");
        User returnedUser = userDAO.update(userWithChangeName);
        // users мы получаем теперь напрямую из списка, +1 т.к. первая строка - шапка
        String actual = lines.get(USER_1_ID + 1);
        String expected1 = returnedUser.getId() + SEPARATOR + returnedUser.getName();
        String unexpected = originalUser.getId() + SEPARATOR + originalUser.getName();

        Assert.assertNotNull("User was not updated", returnedUser);
        Assert.assertEquals(expected1 + "!=" + actual, expected1, actual);
        Assert.assertNotEquals(unexpected + "==" + actual, unexpected, actual);
    }

//    @Test
//    public void testUpdateSuccessfully() throws Exception {
//        String originalUserLine = lines.get(USER_1_ID + 1);
//        String newName = "Alex";
//        String updatedUserString = lines.get(USER_1_ID + 1);
//        User originalUser = convertLineToUser(originalUserLine);
//        User newUser = new User(originalUser.getId(), newName);
//        User returnedUser = userDAO.update(newUser);
//        User updatedUser = convertLineToUser(updatedUserString);
//
//        Assert.assertNotNull("Method returns null", returnedUser);
//        Assert.assertEquals("Method returns incorrect user", newUser, returnedUser);
//        Assert.assertEquals("Method not updates user", newUser, updatedUser);
//    }

    @Test(expected = RuntimeException.class)
    public void testUpdateFail() throws Exception {
        userDAO.update(new User(INEXISTENT_USER_ID, ""));
    }

    @Test
    public void testSave() throws Exception {
        User newUser = new User(0, "Nico");
        User returnedUser = userDAO.save(newUser);
        User originalUser = convertLineToUser(lines.get(lines.size() - 1));
        Assert.assertNotNull("Method returns null", returnedUser);
        Assert.assertEquals("Method do not return correct user (" + originalUser + "!=" + returnedUser + ")", originalUser, returnedUser);
        Assert.assertEquals("Name of saved user is incorrect", originalUser.getName(), newUser.getName());
    }

    @Test
    public void testGetAllFromNotEmptyStore() throws Exception {
        List<User> users = userDAO.getAll();
        Assert.assertNotNull("Method returns null", users);
        User firstOriginalUser = convertLineToUser(lines.get(1));
        User secondOriginalUser = convertLineToUser(lines.get(2));
        Assert.assertEquals("Method returns list with incorrect user", firstOriginalUser, users.get(0));
        Assert.assertEquals("Method returns list with incorrect user", secondOriginalUser, users.get(1));
    }

    @Test
    public void testGetAllFromEmptyStore() throws Exception {
        lines.clear();
        lines.add(USER_ID_TITLE + SEPARATOR + USER_NAME_TITLE);
        List<User> users = userDAO.getAll();
        Assert.assertNotNull("Method returns null", users);
        Assert.assertTrue("Returned list is not empty", users.isEmpty());
    }

    @Test
    public void testDeleteByIdExistUser() throws Exception {
        Assert.assertTrue("Expected user in test store not exist", lines.contains(USER_1_ID + SEPARATOR + USER_1_NAME));
        userDAO.deleteById(USER_1_ID);
        Assert.assertFalse("User was not deleted", lines.contains(USER_1_ID + SEPARATOR + USER_1_NAME));
    }

    @Test(expected = RuntimeException.class)
    public void testDeleteByIdNotExistUser() throws Exception {
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
