package utils;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * Presentation utilities for working with slides and Jekyll
 */
public class PresentationUtils {
    
    /**
     * Class representing front matter metadata and content
     */
    public static class MarkdownWithFrontMatter {
        private Map<String, Object> frontMatter;
        private String content;
        
        public MarkdownWithFrontMatter() {
            this.frontMatter = new HashMap<>();
            this.content = "";
        }
        
        public Map<String, Object> getFrontMatter() {
            return frontMatter;
        }
        
        public void setFrontMatter(Map<String, Object> frontMatter) {
            this.frontMatter = frontMatter;
        }
        
        public String getContent() {
            return content;
        }
        
        public void setContent(String content) {
            this.content = content;
        }
        
        @Override
        public String toString() {
            return "MarkdownWithFrontMatter{frontMatter=" + frontMatter + ", content=" + content + "}";
        }
    }
    
    /**
     * Class representing a slide with metadata
     */
    public static class Slide {
        private String filename;
        private String path;
        private String title;
        private String author;
        private int position;
        
        public Slide(String filename, String path, String title, String author, int position) {
            this.filename = filename;
            this.path = path;
            this.title = title;
            this.author = author;
            this.position = position;
        }
        
        public String getFilename() {
            return filename;
        }
        
        public String getPath() {
            return path;
        }
        
        public String getTitle() {
            return title;
        }
        
        public String getAuthor() {
            return author;
        }
        
        public int getPosition() {
            return position;
        }
        
        @Override
        public String toString() {
            return "Slide{" +
                    "filename='" + filename + '\'' +
                    ", title='" + title + '\'' +
                    ", author='" + author + '\'' +
                    ", position=" + position +
                    '}';
        }
    }
    
    /**
     * Parse front matter from a markdown file
     * 
     * @param content The markdown content
     * @return Object containing front matter and content
     */
    public static MarkdownWithFrontMatter parseFrontMatter(String content) {
        MarkdownWithFrontMatter result = new MarkdownWithFrontMatter();
        
        if (content == null || content.isEmpty()) {
            return result;
        }
        
        // Check for Jekyll front matter (between --- markers)
        Pattern pattern = Pattern.compile("^---\\n([\\s\\S]*?)\\n---\\n([\\s\\S]*)$");
        Matcher matcher = pattern.matcher(content);
        
        if (!matcher.find()) {
            result.setContent(content);
            return result;
        }
        
        try {
            // Simple YAML parsing for front matter
            String frontMatterText = matcher.group(1);
            String[] frontMatterLines = frontMatterText.split("\\n");
            
            for (String line : frontMatterLines) {
                line = line.trim();
                if (line.isEmpty() || !line.contains(":")) {
                    continue;
                }
                
                int colonPos = line.indexOf(':');
                String key = line.substring(0, colonPos).trim();
                String value = line.substring(colonPos + 1).trim();
                
                if (value.startsWith("\"") && value.endsWith("\"")) {
                    // Handle quoted strings
                    value = value.substring(1, value.length() - 1);
                }
                
                if (value.startsWith("[") && value.endsWith("]")) {
                    // Handle array values
                    List<String> arrayValues = new ArrayList<>();
                    String arrayContent = value.substring(1, value.length() - 1);
                    String[] items = arrayContent.split(",");
                    
                    for (String item : items) {
                        item = item.trim();
                        if (item.startsWith("\"") && item.endsWith("\"")) {
                            item = item.substring(1, item.length() - 1);
                        }
                        arrayValues.add(item);
                    }
                    
                    result.getFrontMatter().put(key, arrayValues);
                } else {
                    result.getFrontMatter().put(key, value);
                }
            }
            
            // Set the content without front matter
            result.setContent(matcher.group(2));
        } catch (Exception e) {
            e.printStackTrace();
            result.setContent(content);
        }
        
        return result;
    }
    
    /**
     * Create a new slide with proper front matter
     * 
     * @param title    Slide title
     * @param author   Slide author
     * @param position Slide position/order (optional)
     * @return Markdown content for the new slide
     */
    public static String createNewSlide(String title, String author, Integer position) {
        String slideNumber = position != null ? 
                String.format("%02d", position) : 
                "XX";
        
        return "---\n" +
                "layout: slide\n" +
                "title: \"" + title + "\"\n" +
                "author: \"" + author + "\"\n" +
                "---\n\n" +
                "## " + title + "\n\n" +
                "### By " + author + "\n\n" +
                "* Bullet point 1\n" +
                "* Bullet point 2\n" +
                "* Bullet point 3\n\n" +
                "---\n\n" +
                "### More Content\n\n" +
                "* Use horizontal rules (---) to separate slide content\n" +
                "* This creates a new slide in the presentation\n";
    }
    
    /**
     * Create a new slide with default position
     * 
     * @param title  Slide title
     * @param author Slide author
     * @return Markdown content for the new slide
     */
    public static String createNewSlide(String title, String author) {
        return createNewSlide(title, author, null);
    }
    
    /**
     * Get all slides ordered by their numbering
     * 
     * @param slidesDir Directory containing slides
     * @return Array of slide objects with metadata
     */
    public static List<Slide> getAllSlides(String slidesDir) {
        if (slidesDir == null || slidesDir.isEmpty()) {
            return Collections.emptyList();
        }
        
        File dir = new File(slidesDir);
        if (!dir.exists() || !dir.isDirectory()) {
            return Collections.emptyList();
        }
        
        List<Slide> slides = new ArrayList<>();
        File[] files = dir.listFiles((d, name) -> name.endsWith(".md"));
        
        if (files == null) {
            return Collections.emptyList();
        }
        
        for (File file : files) {
            try {
                // Read file content
                StringBuilder content = new StringBuilder();
                try (BufferedReader reader = new BufferedReader(new FileReader(file))) {
                    String line;
                    while ((line = reader.readLine()) != null) {
                        content.append(line).append("\n");
                    }
                }
                
                // Parse front matter
                MarkdownWithFrontMatter parsed = parseFrontMatter(content.toString());
                
                // Extract slide position from filename
                String filename = file.getName();
                int position = 999;
                
                if (filename.matches("^\\d+.*\\.md$")) {
                    String posStr = filename.split("-")[0];
                    try {
                        position = Integer.parseInt(posStr);
                    } catch (NumberFormatException e) {
                        // Use default position
                    }
                }
                
                // Get title and author from front matter
                String title = (String) parsed.getFrontMatter().getOrDefault("title", "");
                String author = (String) parsed.getFrontMatter().getOrDefault("author", "");
                
                slides.add(new Slide(
                        filename, 
                        file.getAbsolutePath(), 
                        title, 
                        author, 
                        position
                ));
                
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        
        // Sort by position
        Collections.sort(slides, (a, b) -> Integer.compare(a.getPosition(), b.getPosition()));
        return slides;
    }
    
    /**
     * Get a specific slide by position
     * 
     * @param slidesDir Directory containing slides
     * @param position  Position to look for
     * @return Slide object or null if not found
     */
    public static Slide getSlideByPosition(String slidesDir, int position) {
        List<Slide> slides = getAllSlides(slidesDir);
        
        for (Slide slide : slides) {
            if (slide.getPosition() == position) {
                return slide;
            }
        }
        
        return null;
    }
}
