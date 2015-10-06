package ru.itsphere.itmoney.controllers;

import org.junit.Assert;
import org.junit.Test;

import java.io.Serializable;

/**
 * Тесты для ResponseCreator
 * <p>
 * http://it-channel.ru/
 *
 * @author Budnikov Aleksandr
 */
public class ResponseCreatorTest {

    @Test
    public void testProcessWithResult() {
        String text = "some text";
        Serializable actual = ResponseCreator.process(text);
        String expected = "{\"status\":\"ok\",\"result\":\"" + text + "\"}";
        Assert.assertNotNull("ResponseCreator returned null", actual);
        Assert.assertEquals("ResponseCreator returned unexpected value. " + expected + " != " + actual, expected, actual);
    }

    @Test
    public void testProcessWithoutResult() {
        Serializable actual = ResponseCreator.process(null);
        String expected = "{\"status\":\"ok\"}";
        Assert.assertNotNull("ResponseCreator returned null", actual);
        Assert.assertEquals("ResponseCreator returned unexpected value. " + expected + " != " + actual, expected, actual);
    }

    @Test
    public void testProcessFail() {
        String actual = ResponseCreator.processError(new Exception());
        String expectedStart = "{\"status\":\"fail\",\"error\":\"java.lang.Exception";
        String expectedEnd = "\"}";
        Assert.assertNotNull("ResponseCreator returned null", actual);
        Assert.assertTrue("ResponseCreator returned unexpected value. (" + expectedStart + " != " + actual + ")", actual.startsWith(expectedStart));
        Assert.assertTrue("ResponseCreator returned unexpected value. (" + expectedEnd + " != " + actual + ")", actual.endsWith(expectedEnd));
    }
}
