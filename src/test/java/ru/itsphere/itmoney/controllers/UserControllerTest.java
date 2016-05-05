package ru.itsphere.itmoney.controllers;

import com.google.gson.Gson;
import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Answers;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.runners.MockitoJUnitRunner;
import ru.itsphere.itmoney.domain.User;
import ru.itsphere.itmoney.services.UserService;

import java.io.Serializable;
import java.util.*;

import static org.mockito.Mockito.doAnswer;
import static org.mockito.Mockito.when;

/**
 * Тесты для UserController
 */

@RunWith(MockitoJUnitRunner.class)
public class UserControllerTest {
    public static final String INEXISTENT_USER_ID_STRING = null;
    public static final int INEXISTENT_USER_ID = 2;
    public static final String QUERY = "s";
    private String userSashaId = "0";
    private String userSashaName = "Sasha";
    private String userDimaId = "1";
    private String userDimaName = "Dima";
    private User userSasha = new User(0, "Sasha");
    private User userDima = new User(1, "Dima");
    private User newUser = new User(2, "Igor");
    private List<User> allUsers = Arrays.asList(new User[]{userSasha, userDima});
    private List<User> emptyList = new ArrayList<>();
    private Map<String, String> params = new HashMap<>();

    @Mock(answer = Answers.RETURNS_SMART_NULLS)
    private UserService userService;

    @InjectMocks
    private UserController userController;

    private String wrap(Object object) {
        return new Gson().toJson(object);
    }
    private Object unwrap(String str) {return new Gson().fromJson(str, Object.class);}

    @Test
    public void testGetByIdSuccessfully() {
        params.put("id", userDimaId);
        when(userService.getById(userDima.getId())).thenReturn(userDima);
        Serializable userTest = userController.getById(params);
        Serializable user = wrap(userDima);
        Assert.assertEquals(user, userTest);
    }
    @Test
    public void testGetByIdNotExistedUser() {
        params.put("id", INEXISTENT_USER_ID_STRING);
        Serializable testUser = userController.getById(params);
        Assert.assertNull(testUser);
    }

    @Test
    public void testSaveNewUserSuccessfully() {
        List<User> users = new ArrayList<>();
        params.put("name", newUser.getName());
        newUser.setId(0);
        doAnswer(invocation -> {
            User newUser = (User) invocation.getArguments()[0];
            users.add(newUser);
            Assert.assertNotNull(newUser);
            Assert.assertEquals(newUser, this.newUser);
            return null;
        }).when(userService).save(newUser);
        userController.save(params);
        Assert.assertTrue(users.contains(newUser));
    }

    @Test
    public void testSaveUpdateUserSuccefully() {
        List<User> users = new ArrayList<>();
        params.put("id", String.valueOf(userDima.getId()));
        params.put("name", userDima.getName());
        doAnswer(invocation -> {
            User user = (User) invocation.getArguments()[0];
            users.add(user);
            Assert.assertNotNull(user);
            Assert.assertEquals(user, this.userDima);
            return null;
        }).when(userService).update(userDima);
        userController.save(params);
        Assert.assertTrue(users.contains(userDima));
    }

    @Test
    public void testGetAllSuccessfully() {
        when(userService.getAll()).thenReturn(allUsers);
        Serializable actualUsers = userController.getAll(params);
        Assert.assertEquals(wrap(allUsers), actualUsers);
    }
    @Test
    public void testGetAllEmpty() {
        List<User> expectedUsers = new ArrayList<>();
        when(userService.getAll()).thenReturn(expectedUsers);
        Serializable actualUsers = userController.getAll(params);
        Assert.assertEquals(wrap(expectedUsers), actualUsers);
    }

    @Test
    public void testGetCountSuccessfully() {
        when(userService.getCount()).thenReturn(allUsers.size());
        Serializable actualCount = userController.getCount(params);
        Serializable expectedCount = wrap(allUsers.size());
        Assert.assertEquals(expectedCount, actualCount);
    }
    @Test
    public void testGetCountEmpty() {
        when(userService.getCount()).thenReturn(emptyList.size());
        Serializable actualCount = userController.getCount(params);
        Serializable expectedCount = wrap(emptyList.size());
        Assert.assertEquals(expectedCount, actualCount);
    }

    @Test
    public void testFindUsersByQuerySuccessfully() {
        params.put("query", QUERY);
        List<User> usersForQuery = new ArrayList<>();
        usersForQuery.add(userSasha);
        when(userService.findUsersByQuery(QUERY)).thenReturn(usersForQuery);
        Serializable actualUsers = userController.findUsersByQuery(params);
        Serializable expectedUsers = wrap(usersForQuery);
        Assert.assertEquals(expectedUsers, actualUsers);
    }
    @Test
    public void testFindUsersByQueryEmpty() {
        params.put("query", QUERY);
        List<User> usersForQuery = new ArrayList<>();
        when(userService.findUsersByQuery(QUERY)).thenReturn(usersForQuery);
        Serializable actualUsers = userController.findUsersByQuery(params);
        Serializable expectedUsers = wrap(usersForQuery);
        Assert.assertEquals(expectedUsers, actualUsers);
    }
    @Test
    public void testFindUsersByQueryNull() {
        List<User> usersForQuery = new ArrayList<>();
        when(userService.findUsersByQuery(QUERY)).thenReturn(usersForQuery);
        Serializable actualUsers = userController.findUsersByQuery(params);
        Serializable expectedUsers = wrap(usersForQuery);
        Assert.assertEquals(expectedUsers, actualUsers);
    }

    @Test
    public void testDeleteByIdSuccessfully() {
        params.put("id", userDimaId);
        List<User> actualUsers = new ArrayList<>();
        actualUsers.add(userDima);
        doAnswer(invocation -> {
            int id = (int) invocation.getArguments()[0];
            actualUsers.remove(0);
            Assert.assertEquals(id, userDima.getId());
            return null;
        }).when(userService).deleteById(userDima.getId());
        userController.deleteById(params);
        Assert.assertTrue(actualUsers.isEmpty());
    }
    @Test
    public void testDeleteByIdInexistentUser() {
        params.put("id", String.valueOf(INEXISTENT_USER_ID));
        List<User> actualUsers = new ArrayList<>();
        actualUsers.add(userDima);
        doAnswer(invocation -> {
            int id = (int) invocation.getArguments()[0];
            actualUsers.remove(0);
            Assert.assertEquals(id, INEXISTENT_USER_ID);
            return null;
        }).when(userService).deleteById(INEXISTENT_USER_ID);
        userController.deleteById(params);
        Assert.assertTrue(actualUsers.isEmpty());
    }
    @Test
    public void testDeleteByIdWithNullId() {
        Serializable response = userController.deleteById(params);
        Assert.assertNull(response);
    }
}
