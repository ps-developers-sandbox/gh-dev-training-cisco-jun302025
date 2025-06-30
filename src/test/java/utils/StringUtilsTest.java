package utils;

import static org.junit.Assert.*;
import org.junit.Test;

/**
 * Test class for StringUtils
 */
public class StringUtilsTest {
    
    @Test
    public void testIsEmpty() {
        assertTrue(StringUtils.isEmpty(null));
        assertTrue(StringUtils.isEmpty(""));
        assertFalse(StringUtils.isEmpty(" "));
        assertFalse(StringUtils.isEmpty("text"));
    }
    
    @Test
    public void testIsBlank() {
        assertTrue(StringUtils.isBlank(null));
        assertTrue(StringUtils.isBlank(""));
        assertTrue(StringUtils.isBlank(" "));
        assertTrue(StringUtils.isBlank("\t\n"));
        assertFalse(StringUtils.isBlank("text"));
    }
    
    @Test
    public void testTruncate() {
        assertEquals("Hello...", StringUtils.truncate("Hello World", 8));
        assertEquals("Hello World", StringUtils.truncate("Hello World", 20));
        assertEquals("Hi", StringUtils.truncate("Hi", 2));
        assertEquals("", StringUtils.truncate("", 10));
        assertNull(StringUtils.truncate(null, 10));
    }
    
    @Test
    public void testCamelToSnakeCase() {
        assertEquals("hello_world", StringUtils.camelToSnakeCase("helloWorld"));
        assertEquals("hello_world_test", StringUtils.camelToSnakeCase("helloWorldTest"));
        assertEquals("hello", StringUtils.camelToSnakeCase("hello"));
        assertEquals("", StringUtils.camelToSnakeCase(""));
        assertNull(StringUtils.camelToSnakeCase(null));
    }
    
    @Test
    public void testSnakeToCamelCase() {
        assertEquals("helloWorld", StringUtils.snakeToCamelCase("hello_world"));
        assertEquals("helloWorldTest", StringUtils.snakeToCamelCase("hello_world_test"));
        assertEquals("hello", StringUtils.snakeToCamelCase("hello"));
        assertEquals("", StringUtils.snakeToCamelCase(""));
        assertNull(StringUtils.snakeToCamelCase(null));
    }
    
    @Test
    public void testCapitalize() {
        assertEquals("Hello", StringUtils.capitalize("hello"));
        assertEquals("Hello world", StringUtils.capitalize("hello world"));
        assertEquals("H", StringUtils.capitalize("h"));
        assertEquals("", StringUtils.capitalize(""));
        assertNull(StringUtils.capitalize(null));
    }
}
