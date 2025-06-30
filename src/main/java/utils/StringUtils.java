package utils;

import java.util.ArrayList;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * String utility class for common string operations
 */
public class StringUtils {
    
    /**
     * Check if a string is null or empty
     * 
     * @param str String to check
     * @return True if the string is null or empty, false otherwise
     */
    public static boolean isEmpty(String str) {
        return str == null || str.isEmpty();
    }
    
    /**
     * Check if a string is not null and not empty
     * 
     * @param str String to check
     * @return True if the string is not null and not empty, false otherwise
     */
    public static boolean isNotEmpty(String str) {
        return !isEmpty(str);
    }
    
    /**
     * Check if a string is null, empty, or contains only whitespace
     * 
     * @param str String to check
     * @return True if the string is null, empty, or contains only whitespace
     */
    public static boolean isBlank(String str) {
        return str == null || str.trim().isEmpty();
    }
    
    /**
     * Check if a string is not null, not empty, and contains non-whitespace characters
     * 
     * @param str String to check
     * @return True if the string is not null, not empty, and contains non-whitespace characters
     */
    public static boolean isNotBlank(String str) {
        return !isBlank(str);
    }
    
    /**
     * Truncate a string to a specified length
     * 
     * @param str    String to truncate
     * @param length Maximum length
     * @param suffix Suffix to add if truncated (e.g., "...")
     * @return Truncated string
     */
    public static String truncate(String str, int length, String suffix) {
        if (isEmpty(str) || length <= 0) {
            return str;
        }
        
        if (suffix == null) {
            suffix = "";
        }
        
        if (str.length() <= length) {
            return str;
        }
        
        int truncateLength = length - suffix.length();
        if (truncateLength < 0) {
            truncateLength = 0;
        }
        
        return str.substring(0, truncateLength) + suffix;
    }
    
    /**
     * Truncate a string with default suffix "..."
     * 
     * @param str    String to truncate
     * @param length Maximum length
     * @return Truncated string
     */
    public static String truncate(String str, int length) {
        return truncate(str, length, "...");
    }
    
    /**
     * Extract all matches from a string using a regex pattern
     * 
     * @param text    Text to search
     * @param pattern Regex pattern
     * @return List of matched strings
     */
    public static List<String> extractMatches(String text, String pattern) {
        List<String> matches = new ArrayList<>();
        
        if (isEmpty(text) || isEmpty(pattern)) {
            return matches;
        }
        
        Pattern regex = Pattern.compile(pattern);
        Matcher matcher = regex.matcher(text);
        
        while (matcher.find()) {
            matches.add(matcher.group());
        }
        
        return matches;
    }
    
    /**
     * Extract a specific capturing group from all matches in a string
     * 
     * @param text    Text to search
     * @param pattern Regex pattern
     * @param group   Capturing group number (0 for full match)
     * @return List of matched strings
     */
    public static List<String> extractMatchGroup(String text, String pattern, int group) {
        List<String> matches = new ArrayList<>();
        
        if (isEmpty(text) || isEmpty(pattern)) {
            return matches;
        }
        
        Pattern regex = Pattern.compile(pattern);
        Matcher matcher = regex.matcher(text);
        
        while (matcher.find()) {
            if (group >= 0 && group <= matcher.groupCount()) {
                matches.add(matcher.group(group));
            }
        }
        
        return matches;
    }
    
    /**
     * Check if a string matches a regex pattern
     * 
     * @param text    Text to check
     * @param pattern Regex pattern
     * @return True if the string matches the pattern, false otherwise
     */
    public static boolean matches(String text, String pattern) {
        if (isEmpty(text) || isEmpty(pattern)) {
            return false;
        }
        
        return text.matches(pattern);
    }
    
    /**
     * Replace all occurrences of a substring in a string
     * 
     * @param text    Text to process
     * @param search  Substring to search for
     * @param replace Replacement string
     * @return Processed string
     */
    public static String replaceAll(String text, String search, String replace) {
        if (isEmpty(text) || isEmpty(search)) {
            return text;
        }
        
        if (replace == null) {
            replace = "";
        }
        
        return text.replace(search, replace);
    }
    
    /**
     * Convert a camelCase string to snake_case
     * 
     * @param camelCase String in camelCase
     * @return String in snake_case
     */
    public static String camelToSnakeCase(String camelCase) {
        if (isEmpty(camelCase)) {
            return camelCase;
        }
        
        String regex = "([a-z])([A-Z]+)";
        String replacement = "$1_$2";
        return camelCase.replaceAll(regex, replacement).toLowerCase();
    }
    
    /**
     * Convert a snake_case string to camelCase
     * 
     * @param snakeCase String in snake_case
     * @return String in camelCase
     */
    public static String snakeToCamelCase(String snakeCase) {
        if (isEmpty(snakeCase)) {
            return snakeCase;
        }
        
        StringBuilder result = new StringBuilder();
        boolean nextUpper = false;
        
        for (char c : snakeCase.toCharArray()) {
            if (c == '_') {
                nextUpper = true;
            } else {
                if (nextUpper) {
                    result.append(Character.toUpperCase(c));
                    nextUpper = false;
                } else {
                    result.append(Character.toLowerCase(c));
                }
            }
        }
        
        return result.toString();
    }
    
    /**
     * Capitalize the first letter of a string
     * 
     * @param str String to capitalize
     * @return Capitalized string
     */
    public static String capitalize(String str) {
        if (isEmpty(str)) {
            return str;
        }
        
        if (str.length() == 1) {
            return str.toUpperCase();
        }
        
        return Character.toUpperCase(str.charAt(0)) + str.substring(1);
    }
}
