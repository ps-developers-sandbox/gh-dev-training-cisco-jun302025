package utils;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Random;
import java.util.Timer;
import java.util.TimerTask;
import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.Serializable;

/**
 * General utility class for common operations
 */
public class GeneralUtils {
    
    /**
     * Format a date in a user-friendly way
     * 
     * @param date   The date to format (uses current date if null)
     * @param format The format pattern (default: "yyyy-MM-dd")
     * @return Formatted date string
     */
    public static String formatDate(Date date, String format) {
        if (date == null) {
            date = new Date();
        }
        if (format == null || format.isEmpty()) {
            format = "yyyy-MM-dd";
        }
        
        SimpleDateFormat formatter = new SimpleDateFormat(format);
        return formatter.format(date);
    }
    
    /**
     * Overloaded method to format current date
     * 
     * @param format The format pattern
     * @return Formatted date string
     */
    public static String formatDate(String format) {
        return formatDate(new Date(), format);
    }
    
    /**
     * Default format for current date
     * 
     * @return Formatted date string with default pattern
     */
    public static String formatDate() {
        return formatDate(new Date(), "yyyy-MM-dd");
    }
    
    /**
     * Generate a random string with specified length
     * 
     * @param length Length of the string to generate
     * @return Random string
     */
    public static String generateRandomString(int length) {
        if (length <= 0) {
            length = 8;
        }
        
        String chars = "ABCDEFGHIJKLMNOPQRSTUVWXYZabcdefghijklmnopqrstuvwxyz0123456789";
        StringBuilder result = new StringBuilder(length);
        Random random = new Random();
        
        for (int i = 0; i < length; i++) {
            result.append(chars.charAt(random.nextInt(chars.length())));
        }
        
        return result.toString();
    }
    
    /**
     * Generate random string with default length (8)
     * 
     * @return Random string with default length
     */
    public static String generateRandomString() {
        return generateRandomString(8);
    }
    
    /**
     * Deep clone a serializable object
     * 
     * @param <T> Type of object
     * @param obj Object to clone
     * @return Cloned object or null if cloning failed
     */
    @SuppressWarnings("unchecked")
    public static <T extends Serializable> T deepClone(T obj) {
        try {
            if (obj == null) {
                return null;
            }
            
            ByteArrayOutputStream baos = new ByteArrayOutputStream();
            ObjectOutputStream oos = new ObjectOutputStream(baos);
            oos.writeObject(obj);
            oos.close();
            
            ByteArrayInputStream bais = new ByteArrayInputStream(baos.toByteArray());
            ObjectInputStream ois = new ObjectInputStream(bais);
            return (T) ois.readObject();
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }
    
    /**
     * A debounce implementation for limiting how often a runnable can execute
     */
    public static class Debouncer {
        private Timer timer;
        
        /**
         * Debounce a runnable
         * 
         * @param runnable The runnable to debounce
         * @param delayMs  Delay in milliseconds
         */
        public void debounce(Runnable runnable, long delayMs) {
            if (timer != null) {
                timer.cancel();
            }
            
            timer = new Timer();
            timer.schedule(new TimerTask() {
                @Override
                public void run() {
                    runnable.run();
                }
            }, delayMs);
        }
        
        /**
         * Cancel any pending executions
         */
        public void cancel() {
            if (timer != null) {
                timer.cancel();
                timer = null;
            }
        }
    }
}
