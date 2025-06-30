package utils;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * GitHub-specific utility class
 */
public class GitHubUtils {
    
    /**
     * Repository information containing owner and repo name
     */
    public static class RepoInfo {
        private String owner;
        private String repo;
        
        public RepoInfo(String owner, String repo) {
            this.owner = owner;
            this.repo = repo;
        }
        
        public String getOwner() {
            return owner;
        }
        
        public String getRepo() {
            return repo;
        }
        
        @Override
        public String toString() {
            return "RepoInfo{owner='" + owner + "', repo='" + repo + "'}";
        }
    }
    
    /**
     * Parse a GitHub repository URL and extract components
     * 
     * @param url GitHub repository URL
     * @return RepoInfo object containing owner and repo or null if invalid
     */
    public static RepoInfo parseGitHubUrl(String url) {
        if (url == null || url.isEmpty()) {
            return null;
        }
        
        // Handle different GitHub URL formats
        String[][] patterns = {
            {"github\\.com/([^/]+)/([^/]+)(\\.git)?$", "https URL"},
            {"github\\.com:([^/]+)/([^/]+)(\\.git)?$", "SSH URL"},
            {"api\\.github\\.com/repos/([^/]+)/([^/]+)$", "API URL"}
        };
        
        for (String[] patternInfo : patterns) {
            Pattern pattern = Pattern.compile(patternInfo[0]);
            Matcher matcher = pattern.matcher(url);
            
            if (matcher.find()) {
                String owner = matcher.group(1);
                String repo = matcher.group(2);
                if (repo.endsWith(".git")) {
                    repo = repo.substring(0, repo.length() - 4);
                }
                return new RepoInfo(owner, repo);
            }
        }
        
        return null;
    }
    
    /**
     * Generate a GitHub profile URL
     * 
     * @param username GitHub username
     * @return GitHub profile URL
     */
    public static String getGitHubProfileUrl(String username) {
        if (username == null || username.isEmpty()) {
            return "";
        }
        return "https://github.com/" + username;
    }
    
    /**
     * Generate a GitHub repository URL
     * 
     * @param owner Repository owner
     * @param repo  Repository name
     * @return GitHub repository URL
     */
    public static String getGitHubRepoUrl(String owner, String repo) {
        if (owner == null || owner.isEmpty() || repo == null || repo.isEmpty()) {
            return "";
        }
        return "https://github.com/" + owner + "/" + repo;
    }
    
    /**
     * Generate GitHub avatar URL from username
     * 
     * @param username GitHub username
     * @param size     Avatar size in pixels
     * @return GitHub avatar URL
     */
    public static String getGitHubAvatarUrl(String username, int size) {
        if (username == null || username.isEmpty()) {
            return "";
        }
        
        if (size <= 0) {
            size = 200;
        }
        
        return "https://github.com/" + username + ".png?size=" + size;
    }
    
    /**
     * Generate GitHub avatar URL with default size
     * 
     * @param username GitHub username
     * @return GitHub avatar URL with default size
     */
    public static String getGitHubAvatarUrl(String username) {
        return getGitHubAvatarUrl(username, 200);
    }
    
    /**
     * Format a GitHub username with a link for markdown
     * 
     * @param username GitHub username
     * @return Markdown formatted link
     */
    public static String formatGitHubUserLink(String username) {
        if (username == null || username.isEmpty()) {
            return "";
        }
        return "[@" + username + "](" + getGitHubProfileUrl(username) + ")";
    }
    
    /**
     * Get GitHub API URL for a repository
     * 
     * @param owner Repository owner
     * @param repo  Repository name
     * @return GitHub API URL
     */
    public static String getGitHubApiUrl(String owner, String repo) {
        if (owner == null || owner.isEmpty() || repo == null || repo.isEmpty()) {
            return "";
        }
        return "https://api.github.com/repos/" + owner + "/" + repo;
    }
    
    /**
     * Get GitHub issues URL for a repository
     * 
     * @param owner Repository owner
     * @param repo  Repository name
     * @return GitHub issues URL
     */
    public static String getGitHubIssuesUrl(String owner, String repo) {
        if (owner == null || owner.isEmpty() || repo == null || repo.isEmpty()) {
            return "";
        }
        return getGitHubRepoUrl(owner, repo) + "/issues";
    }
    
    /**
     * Get GitHub pull requests URL for a repository
     * 
     * @param owner Repository owner
     * @param repo  Repository name
     * @return GitHub pull requests URL
     */
    public static String getGitHubPullsUrl(String owner, String repo) {
        if (owner == null || owner.isEmpty() || repo == null || repo.isEmpty()) {
            return "";
        }
        return getGitHubRepoUrl(owner, repo) + "/pulls";
    }
}
