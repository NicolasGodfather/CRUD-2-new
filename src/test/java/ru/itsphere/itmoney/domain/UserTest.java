package ru.itsphere.itmoney.domain;

import org.junit.Assert;
import org.junit.Test;

/**
 * В этом классе тесты для класса "пользователь"
 * <p>
 * http://it-channel.ru/
 *
 * @author Budnikov Aleksandr
 */
public class UserTest {

    @Test
    public void testEqualsTrue1() {
        User user1 = new User(0, "Alex");
        User user2 = new User(0, "Alex");
        Assert.assertEquals("User " + user1 + " != " + user2, user1, user2);
    }

    @Test
    public void testEqualsTrue2() {
        User user = new User(0, "Alex");
        Assert.assertEquals("User " + user + " != " + user, user, user);
    }

    @Test
    public void testEqualsFalse1() {
        User user1 = new User(0, "Alex_test");
        User user2 = new User(0, "Alex");
        Assert.assertNotEquals("User " + user1 + " == " + user2, user1, user2);
    }

    @Test
    public void testEqualsFalse2() {
        User user1 = new User(1, "Alex");
        User user2 = new User(0, "Alex");
        Assert.assertNotEquals("User " + user1 + " == " + user2, user1, user2);
    }

    @Test
    public void testEqualsFalse3() {
        User user1 = new User(0, "Alex");
        Assert.assertNotEquals("User " + user1 + " == " + null, user1, null);
    }

    @Test
    public void testHashCode() {
        int user1hashCode = new User(0, "Alex").hashCode();
        int user2hashCode = new User(0, "Alex").hashCode();
        Assert.assertEquals("HashCode " + user1hashCode + " != " + user2hashCode, user1hashCode, user2hashCode);
    }
}
