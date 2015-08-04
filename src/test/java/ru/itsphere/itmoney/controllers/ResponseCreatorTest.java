package ru.itsphere.itmoney.controllers;

import org.junit.Assert;
import org.junit.Test;

import java.io.Serializable;
import java.util.HashMap;

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
        ResponseCreator creator = new ResponseCreator((params) -> text);
        Serializable actual = creator.process(new HashMap<>());
        String expected = "{\"status\":\"ok\",\"result\":\"" + text + "\"}";
        Assert.assertNotNull("ResponseCreator returned null", actual);
        Assert.assertEquals("ResponseCreator returned unexpected value. " + expected + " != " + actual, expected, actual);
    }

    @Test
    public void testProcessWithoutResult() {
        ResponseCreator creator = new ResponseCreator((params) -> null);
        Serializable actual = creator.process(new HashMap<>());
        String expected = "{\"status\":\"ok\"}";
        Assert.assertNotNull("ResponseCreator returned null", actual);
        Assert.assertEquals("ResponseCreator returned unexpected value. " + expected + " != " + actual, expected, actual);
    }

    @Test
    public void testProcessWithResultFromParams() {
        String text = "some text";
        ResponseCreator creator = new ResponseCreator((params) -> params.get("text"));
        HashMap<String, String> params = new HashMap<>();
        params.put("text", text);
        Serializable actual = creator.process(params);
        String expected = "{\"status\":\"ok\",\"result\":\"" + text + "\"}";
        Assert.assertNotNull("ResponseCreator returned null", actual);
        Assert.assertEquals("ResponseCreator returned unexpected value. " + expected + " != " + actual, expected, actual);
    }

    @Test
    public void testProcessFail() {
        ResponseCreator creator = new ResponseCreator((params) -> {
            throw new Exception();
        });
        String actual = creator.process(new HashMap<>());
        String expectedStart = "{\"status\":\"fail\",\"error\":\"java.lang.Exception";
        String expectedEnd = "\"}";
        Assert.assertNotNull("ResponseCreator returned null", actual);
        Assert.assertTrue("ResponseCreator returned unexpected value. (" + expectedStart + " != " + actual + ")", actual.startsWith(expectedStart));
        Assert.assertTrue("ResponseCreator returned unexpected value. (" + expectedEnd + " != " + actual + ")", actual.endsWith(expectedEnd));
    }
}
