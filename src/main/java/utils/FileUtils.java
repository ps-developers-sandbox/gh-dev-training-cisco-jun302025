package utils;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

/**
 * File utility class for common file operations
 */
public class FileUtils {
    
    /**
     * Check if a file exists
     * 
     * @param filePath Path to the file
     * @return True if file exists, false otherwise
     */
    public static boolean fileExists(String filePath) {
        if (filePath == null || filePath.isEmpty()) {
            return false;
        }
        
        File file = new File(filePath);
        return file.exists() && file.isFile();
    }
    
    /**
     * Check if a directory exists
     * 
     * @param dirPath Path to the directory
     * @return True if directory exists, false otherwise
     */
    public static boolean directoryExists(String dirPath) {
        if (dirPath == null || dirPath.isEmpty()) {
            return false;
        }
        
        File dir = new File(dirPath);
        return dir.exists() && dir.isDirectory();
    }
    
    /**
     * Create directory (and parent directories if needed)
     * 
     * @param dirPath Path to the directory
     * @return True if successful, false otherwise
     */
    public static boolean createDirectory(String dirPath) {
        if (dirPath == null || dirPath.isEmpty()) {
            return false;
        }
        
        File dir = new File(dirPath);
        return dir.mkdirs();
    }
    
    /**
     * Read a file and return its contents as a string
     * 
     * @param filePath Path to the file
     * @return File contents or null if file doesn't exist or can't be read
     */
    public static String readFile(String filePath) {
        if (!fileExists(filePath)) {
            return null;
        }
        
        StringBuilder content = new StringBuilder();
        
        try (BufferedReader reader = new BufferedReader(new FileReader(filePath))) {
            String line;
            while ((line = reader.readLine()) != null) {
                content.append(line).append(System.lineSeparator());
            }
            return content.toString();
        } catch (IOException e) {
            e.printStackTrace();
            return null;
        }
    }
    
    /**
     * Write content to a file
     * 
     * @param filePath Path to the file
     * @param content  Content to write
     * @return True if successful, false otherwise
     */
    public static boolean writeFile(String filePath, String content) {
        if (filePath == null || filePath.isEmpty()) {
            return false;
        }
        
        try {
            // Ensure directory exists
            File file = new File(filePath);
            File parent = file.getParentFile();
            if (parent != null && !parent.exists()) {
                parent.mkdirs();
            }
            
            try (BufferedWriter writer = new BufferedWriter(new FileWriter(file))) {
                writer.write(content);
                return true;
            }
        } catch (IOException e) {
            e.printStackTrace();
            return false;
        }
    }
    
    /**
     * Append content to a file
     * 
     * @param filePath Path to the file
     * @param content  Content to append
     * @return True if successful, false otherwise
     */
    public static boolean appendToFile(String filePath, String content) {
        if (filePath == null || filePath.isEmpty()) {
            return false;
        }
        
        try {
            // Ensure directory exists
            File file = new File(filePath);
            File parent = file.getParentFile();
            if (parent != null && !parent.exists()) {
                parent.mkdirs();
            }
            
            try (BufferedWriter writer = new BufferedWriter(new FileWriter(file, true))) {
                writer.write(content);
                return true;
            }
        } catch (IOException e) {
            e.printStackTrace();
            return false;
        }
    }
    
    /**
     * Get all files in a directory (recursively)
     * 
     * @param dirPath Path to the directory
     * @return List of file paths or empty list if directory doesn't exist
     */
    public static List<String> getAllFiles(String dirPath) {
        if (!directoryExists(dirPath)) {
            return new ArrayList<>();
        }
        
        try {
            return Files.walk(Paths.get(dirPath))
                    .filter(Files::isRegularFile)
                    .map(Path::toString)
                    .collect(Collectors.toList());
        } catch (IOException e) {
            e.printStackTrace();
            return new ArrayList<>();
        }
    }
    
    /**
     * Get files in a directory with a specific extension
     * 
     * @param dirPath   Path to the directory
     * @param extension File extension (e.g., ".txt", ".java")
     * @return List of file paths or empty list if directory doesn't exist
     */
    public static List<String> getFilesByExtension(String dirPath, String extension) {
        if (!directoryExists(dirPath)) {
            return new ArrayList<>();
        }
        
        if (extension == null) {
            extension = "";
        } else if (!extension.startsWith(".") && !extension.isEmpty()) {
            extension = "." + extension;
        }
        
        final String ext = extension;
        try {
            return Files.walk(Paths.get(dirPath))
                    .filter(Files::isRegularFile)
                    .map(Path::toString)
                    .filter(file -> file.endsWith(ext))
                    .collect(Collectors.toList());
        } catch (IOException e) {
            e.printStackTrace();
            return new ArrayList<>();
        }
    }
    
    /**
     * Delete a file
     * 
     * @param filePath Path to the file
     * @return True if successful, false otherwise
     */
    public static boolean deleteFile(String filePath) {
        if (!fileExists(filePath)) {
            return false;
        }
        
        File file = new File(filePath);
        return file.delete();
    }
    
    /**
     * Copy a file from source to destination
     * 
     * @param source      Source file path
     * @param destination Destination file path
     * @return True if successful, false otherwise
     */
    public static boolean copyFile(String source, String destination) {
        if (!fileExists(source)) {
            return false;
        }
        
        try {
            // Ensure destination directory exists
            File destFile = new File(destination);
            File parent = destFile.getParentFile();
            if (parent != null && !parent.exists()) {
                parent.mkdirs();
            }
            
            Files.copy(Paths.get(source), Paths.get(destination));
            return true;
        } catch (IOException e) {
            e.printStackTrace();
            return false;
        }
    }
}
