# Java Utilities - Standard Project Structure

This project follows the standard Maven/Gradle Java project structure:

```
src/
├── main/
│   ├── java/         # Java source files
│   │   └── utils/    # Utility classes
│   └── resources/    # Resource files (properties, configs, etc.)
└── test/
    └── java/         # Test source files
        └── utils/    # Test classes for utilities
```

## Available Utilities

The `utils` package provides several utility classes for common operations:

### 1. GeneralUtils

General-purpose utility methods:

- `formatDate()` - Format dates in different formats
- `generateRandomString()` - Generate random strings
- `deepClone()` - Deep clone serializable objects
- `Debouncer` - Class for debouncing operations

### 2. FileUtils

File operation utilities:

- `fileExists()` - Check if a file exists
- `directoryExists()` - Check if a directory exists
- `createDirectory()` - Create directory and parents
- `readFile()` - Read file contents
- `writeFile()` - Write content to a file
- `appendToFile()` - Append content to a file
- `getAllFiles()` - Get all files in a directory recursively
- `getFilesByExtension()` - Get files with specific extension
- `deleteFile()` - Delete a file
- `copyFile()` - Copy a file from source to destination

### 3. GitHubUtils

GitHub-specific utilities:

- `parseGitHubUrl()` - Parse GitHub repository URLs
- `getGitHubProfileUrl()` - Generate GitHub profile URLs
- `getGitHubRepoUrl()` - Generate GitHub repository URLs
- `getGitHubAvatarUrl()` - Get GitHub avatar URLs
- `formatGitHubUserLink()` - Format GitHub usernames as markdown links
- `getGitHubApiUrl()` - Get GitHub API URL for a repository
- `getGitHubIssuesUrl()` - Get GitHub issues URL
- `getGitHubPullsUrl()` - Get GitHub pull requests URL

### 4. PresentationUtils

Utilities for working with presentation slides:

- `parseFrontMatter()` - Parse Jekyll front matter
- `createNewSlide()` - Create a new slide with proper front matter
- `getAllSlides()` - Get all slides ordered by position
- `getSlideByPosition()` - Get a specific slide by position

### 5. StringUtils

String manipulation utilities:

- `isEmpty()/isNotEmpty()` - Check if string is empty
- `isBlank()/isNotBlank()` - Check if string is blank
- `truncate()` - Truncate strings to a specific length
- `extractMatches()` - Extract regex matches
- `matches()` - Check if string matches pattern
- `replaceAll()` - Replace all occurrences in a string
- `camelToSnakeCase()/snakeToCamelCase()` - Convert between naming conventions
- `capitalize()` - Capitalize first letter

## How to Use

### Example Usage

```java
import utils.GeneralUtils;
import utils.GitHubUtils;
import utils.StringUtils;

public class Example {
    public static void main(String[] args) {
        // Format today's date
        String today = GeneralUtils.formatDate("yyyy-MM-dd");
        System.out.println("Today: " + today);
        
        // Generate a random string
        String random = GeneralUtils.generateRandomString(12);
        System.out.println("Random: " + random);
        
        // Get GitHub profile URL
        String profileUrl = GitHubUtils.getGitHubProfileUrl("username");
        System.out.println("Profile: " + profileUrl);
        
        // Truncate a long string
        String longText = "This is a very long text that needs to be truncated...";
        String truncated = StringUtils.truncate(longText, 20);
        System.out.println("Truncated: " + truncated);
    }
}
```

## Compilation

To compile this project with Maven:

```bash
mvn compile
```

Or with Gradle:

```bash
gradle build
```

Or manually with javac:

```bash
javac -d target/classes src/main/java/utils/*.java
```
