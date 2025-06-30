package utils.examples;

import utils.FileUtils;
import utils.GeneralUtils;
import utils.GitHubUtils;
import utils.StringUtils;

import java.io.File;
import java.util.Date;
import java.util.List;

/**
 * Example application demonstrating the usage of the utility classes
 */
public class UtilsExample {
    
    public static void main(String[] args) {
        System.out.println("GitHub Workshop Utilities Demo");
        System.out.println("==============================");
        
        // GeneralUtils examples
        System.out.println("\n=== GeneralUtils Examples ===");
        System.out.println("Current date: " + GeneralUtils.formatDate());
        System.out.println("Formatted date (MM/dd/yyyy): " + GeneralUtils.formatDate("MM/dd/yyyy"));
        System.out.println("Random string (12 chars): " + GeneralUtils.generateRandomString(12));
        
        // StringUtils examples
        System.out.println("\n=== StringUtils Examples ===");
        String longText = "This is a very long text that needs to be truncated for display purposes.";
        System.out.println("Original text: " + longText);
        System.out.println("Truncated text: " + StringUtils.truncate(longText, 30));
        
        String camelCase = "thisIsCamelCase";
        String snakeCase = StringUtils.camelToSnakeCase(camelCase);
        System.out.println("CamelCase: " + camelCase);
        System.out.println("SnakeCase: " + snakeCase);
        System.out.println("Back to CamelCase: " + StringUtils.snakeToCamelCase(snakeCase));
        
        // GitHubUtils examples
        System.out.println("\n=== GitHubUtils Examples ===");
        String repoUrl = "https://github.com/octocat/Hello-World.git";
        GitHubUtils.RepoInfo repoInfo = GitHubUtils.parseGitHubUrl(repoUrl);
        
        if (repoInfo != null) {
            System.out.println("Repo URL: " + repoUrl);
            System.out.println("Owner: " + repoInfo.getOwner());
            System.out.println("Repository: " + repoInfo.getRepo());
            System.out.println("Profile URL: " + GitHubUtils.getGitHubProfileUrl(repoInfo.getOwner()));
            System.out.println("Issues URL: " + GitHubUtils.getGitHubIssuesUrl(repoInfo.getOwner(), repoInfo.getRepo()));
            System.out.println("Markdown link: " + GitHubUtils.formatGitHubUserLink(repoInfo.getOwner()));
        }
        
        // FileUtils examples
        System.out.println("\n=== FileUtils Examples ===");
        String tempDir = System.getProperty("java.io.tmpdir");
        String testFilePath = tempDir + File.separator + "github-workshop-test.txt";
        
        System.out.println("Creating test file at: " + testFilePath);
        boolean success = FileUtils.writeFile(testFilePath, "Hello, GitHub Workshop!\nThis is a test file.\n");
        System.out.println("File created: " + success);
        
        if (success) {
            System.out.println("File exists: " + FileUtils.fileExists(testFilePath));
            String content = FileUtils.readFile(testFilePath);
            System.out.println("File content: \n" + content);
            
            System.out.println("Appending to file...");
            FileUtils.appendToFile(testFilePath, "Appended line: " + new Date() + "\n");
            content = FileUtils.readFile(testFilePath);
            System.out.println("Updated content: \n" + content);
            
            System.out.println("Cleaning up...");
            FileUtils.deleteFile(testFilePath);
            System.out.println("File exists after deletion: " + FileUtils.fileExists(testFilePath));
        }
    }
}
