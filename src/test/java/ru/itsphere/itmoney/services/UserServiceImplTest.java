package ru.itsphere.itmoney.services;

/**
 * Created by User on 16.02.2016.
 */

import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.runners.MockitoJUnitRunner;
import ru.itsphere.itmoney.dao.UserDAO;
import ru.itsphere.itmoney.domain.User;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import static org.mockito.Mockito.doAnswer;
import static org.mockito.Mockito.doThrow;
import static org.mockito.Mockito.when;

/**
 * Тесты для UserServiceImpl
 * <p>
 * http://it-channel.ru/
 *
 * @author Budnikov Aleksandr
 */
@RunWith(MockitoJUnitRunner.class)
public class UserServiceImplTest {

    public static final int INEXISTENT_USER_ID = 2;
    public static final String QUERY = "s";
    private User userSasha = new User(0, "Sasha");
    private User userDima = new User(1, "Dima");
    private User newUser = new User(2, "Igor");
    private List<User> allUsers = Arrays.asList(new User[]{userSasha, userDima});

    @Mock
    private UserDAO userDAO;

    @InjectMocks
    private UserServiceImpl userService;

    // тесты

    @Test
    public void testGetByIdSuccessfully() {
        when(userDAO.getById(userSasha.getId())).thenReturn(userSasha);
        User expectedUser = userSasha;
        User actualUser = userService.getById(expectedUser.getId());
        Assert.assertNotNull(actualUser);
        Assert.assertEquals(expectedUser, actualUser);
    }

    @Test
    public void testGetByIdNotExistedUser() {
        int id = INEXISTENT_USER_ID;
        User actualUser = userService.getById(id);
        when(userDAO.getById(id)).thenReturn(actualUser);
        Assert.assertNull(actualUser);
    }

    @Test
    public void testSaveSuccessfully() {
        List<User> users = new ArrayList<>();
        doAnswer(invocation -> {
            User newUser = (User) invocation.getArguments()[0];
            users.add(newUser);
            Assert.assertNotNull(newUser);
            Assert.assertEquals(newUser, this.newUser);
            return null;
        }).when(userDAO).save(newUser);
        userService.save(newUser);
        Assert.assertTrue(users.contains(newUser));
    }

    @Test
    public void testDeleteByIdSuccessfully(){
        List<User> users = new ArrayList<>();
        users.add(userDima);
        doAnswer(invocation -> {
            int userId = (int) invocation.getArguments()[0];
            users.remove(0);
            Assert.assertEquals(userId, userDima.getId());
            return null;
        }).when(userDAO).deleteById(userDima.getId());
        userService.deleteById(userDima.getId());
        Assert.assertTrue(users.isEmpty());
    }
    @Test(expected = RuntimeException.class)
    public void testDeleteByIdFail() {
        doThrow(new RuntimeException()).when(userDAO).deleteById(INEXISTENT_USER_ID);
        userService.deleteById(INEXISTENT_USER_ID);
    }
    @Test
    public void testDeleteByIdInexistent() {
        List<User> users = new ArrayList<>();
        users.add(userDima);
        doAnswer(invocation -> {
            int userId = (int) invocation.getArguments()[0];
            users.remove(0);
            Assert.assertEquals(userId, newUser.getId());
            return null;
        }).when(userDAO).deleteById(newUser.getId());
        userService.deleteById(newUser.getId());
        Assert.assertTrue(users.isEmpty());
    }

    @Test
    public void testGetAllSuccessfully(){
        when(userDAO.getAll()).thenReturn(allUsers);
        List<User> users = userService.getAll();
        Assert.assertEquals(allUsers, users);
    }
    @Test
    public void testGetAllEmpty(){
        List<User> users = new ArrayList<>();
        when(userDAO.getAll()).thenReturn(users);
        List<User> usersTest = userService.getAll();
        Assert.assertTrue("List isn't empty", usersTest.isEmpty());
    }

//    @Test
//    public void testGetCountSuccessfully(){
//        when(userDAO.getCount()).thenReturn(allUsers.size());
//        int count = userService.getCount();
//        Assert.assertEquals(allUsers.size(), count);
//    }
//    @Test
//    public void testGetCountEmpty(){
//        List<User> users = new ArrayList<>();
//        when(userDAO.getCount()).thenReturn(users.size());
//        int count = userService.getCount();
//        Assert.assertEquals(allUsers.size(), count);
//    }

    @Test
    public void testFindUsersByQuerySuccessfully(){
        List<User> users = new ArrayList<>();
        users.add(userSasha);
        when(userDAO.findUsersByQuery(QUERY)).thenReturn(users);
        List<User> testUsers = userService.findUsersByQuery(QUERY);
        Assert.assertEquals(users,testUsers);
    }
    @Test
    public void testFindUsersByQueryEmpty(){
        List<User> users = new ArrayList<>();
        when(userDAO.findUsersByQuery(QUERY)).thenReturn(users);
        List<User> testUsers = userService.findUsersByQuery(QUERY);
        Assert.assertTrue("Users isn't empty", testUsers.isEmpty());
    }
}
