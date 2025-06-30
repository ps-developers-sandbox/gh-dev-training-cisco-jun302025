# Introduction to Git and GitHub

## Table of Contents
1. [What is Git?](#what-is-git)
2. [What is GitHub?](#what-is-github)
3. [Git Configuration Levels](#git-configuration-levels)
4. [Setting Up Git](#setting-up-git)
5. [Basic Git Commands](#basic-git-commands)
6. [Branching Strategies](#branching-strategies)
7. [Working with Remote Repositories](#working-with-remote-repositories)
8. [Pull Requests Workflow](#pull-requests-workflow)
9. [Best Practices](#best-practices)
10. [Troubleshooting Common Issues](#troubleshooting-common-issues)

## What is Git?

Git is a distributed version control system designed to handle everything from small to very large projects with speed and efficiency. It was created by Linus Torvalds in 2005 for the development of the Linux kernel.

Key features of Git:
- **Distributed** - Each developer has a full copy of the repository
- **Speed** - Most operations are local, making them fast
- **Branching** - Lightweight branch creation and merging
- **Data integrity** - Uses SHA-1 hashing to ensure content integrity
- **Staging area** - Intermediate area between working directory and repository

## What is GitHub?

GitHub is a web-based hosting service for Git repositories. It provides:
- A centralized location to store Git repositories
- Collaboration features like Pull Requests and Issues
- Project management tools
- CI/CD integration through GitHub Actions
- Security features
- Social coding experience with stars, forks, and contribution graphs

## Git Configuration Levels

Git uses a three-level configuration system:

### 1. System Level (`--system`)
- Applies to all users on the system and all their repositories
- Stored in `/etc/gitconfig` (Unix/Linux) or `C:\Program Files\Git\etc\gitconfig` (Windows)
- Requires administrative privileges to modify
- Command: `git config --system <key> <value>`

### 2. Global Level (`--global`)
- Applies to a specific user and all their repositories
- Stored in `~/.gitconfig` or `~/.config/git/config` (Unix/Linux) or `C:\Users\<username>\.gitconfig` (Windows)
- Command: `git config --global <key> <value>`

### 3. Local Level (`--local`)
- Applies to a specific repository only
- Stored in `.git/config` in the repository
- Command: `git config --local <key> <value>` (or just `git config <key> <value>`)

**Precedence Order**: Local > Global > System (Local overrides Global, which overrides System)

**Viewing Configurations**:
- View all configurations: `git config --list`
- View specific configuration level: `git config --list --system`, `git config --list --global`, or `git config --list --local`
- View specific setting: `git config user.name`

## Setting Up Git

### Initial Configuration

```bash
# Set your username
git config --global user.name "Your Name"

# Set your email
git config --global user.email "your.email@example.com"

# Set default editor
git config --global core.editor "code --wait"  # For VS Code

# Set default branch name for new repositories
git config --global init.defaultBranch main

# Set line ending preferences
git config --global core.autocrlf input  # For Linux/Mac
git config --global core.autocrlf true   # For Windows
```

### Authentication Setup

For HTTPS:
```bash
# Cache credentials
git config --global credential.helper cache
# Or store permanently
git config --global credential.helper store
```

For SSH:
```bash
# Generate SSH key
ssh-keygen -t ed25519 -C "your.email@example.com"

# Add to SSH agent
eval "$(ssh-agent -s)"
ssh-add ~/.ssh/id_ed25519

# Copy the public key and add to GitHub
cat ~/.ssh/id_ed25519.pub
# Then add to GitHub at https://github.com/settings/keys
```

## Basic Git Commands

### Repository Setup

```bash
# Initialize a new repository
git init

# Clone an existing repository
git clone https://github.com/username/repository.git
git clone git@github.com:username/repository.git  # SSH
```

### Making Changes

```bash
# Check status of working directory
git status

# Add files to staging area
git add filename.txt
git add .  # Add all changes

# Commit changes
git commit -m "Descriptive commit message"
git commit -am "Add and commit in one command"  # Only works for modified files

# View commit history
git log
git log --oneline  # Compact view
git log --graph    # Graphical view
```

### Working with Branches

```bash
# List branches
git branch
git branch -a  # List all branches including remote

# Create a new branch
git branch branch-name

# Switch to a branch
git checkout branch-name
git switch branch-name  # Git 2.23+

# Create and switch in one command
git checkout -b branch-name
git switch -c branch-name  # Git 2.23+

# Merge a branch into the current branch
git merge branch-name

# Delete a branch
git branch -d branch-name  # Safe delete
git branch -D branch-name  # Force delete
```

## Branching Strategies

### 1. GitHub Flow
A simple, lightweight workflow:
- `main` branch is always deployable
- Create feature branches from `main`
- Open pull requests for discussion
- Merge to `main` after review
- Deploy immediately after merge

### 2. Git Flow
More structured for larger projects:
- `main` - Production-ready code
- `develop` - Integration branch for features
- `feature/*` - New features
- `release/*` - Preparing for a release
- `hotfix/*` - Urgent fixes for production

### 3. Trunk-Based Development
- Short-lived feature branches
- Frequent integration to the trunk (`main`)
- Emphasis on CI/CD and feature flags

## Working with Remote Repositories

```bash
# Add a remote
git remote add origin https://github.com/username/repository.git

# View remotes
git remote -v

# Fetch from remote
git fetch origin

# Pull from remote (fetch + merge)
git pull origin main

# Push to remote
git push origin branch-name

# Set upstream branch
git push -u origin branch-name
```

## Pull Requests Workflow

### Standard PR Workflow

1. **Fork the repository** (if not a direct collaborator)
2. **Clone your fork**
   ```bash
   git clone https://github.com/your-username/repository.git
   ```
3. **Create a feature branch**
   ```bash
   git checkout -b feature/my-feature
   ```
4. **Make changes and commit**
   ```bash
   git add .
   git commit -m "Implement feature X"
   ```
5. **Push the branch to your fork**
   ```bash
   git push -u origin feature/my-feature
   ```
6. **Open a Pull Request** on GitHub from your branch to the main repository
7. **Address review comments** by making additional commits
8. **Update from upstream** if needed
   ```bash
   git fetch upstream
   git merge upstream/main
   ```
9. **Merge the PR** once approved

### Branching Models for Pull Requests

#### Main-to-Development Model
- `main` branch contains production code
- `development` is the integration branch
- Feature branches are created from `development`
- PRs are submitted from feature branches to `development`
- Periodically, `development` is merged into `main` for releases

```
main        A-------------------E----G
             \                 /    /
development   B---C---D-------F----H
                  \     \    /
feature1           \     \  /
                    ------ 
feature2                 
```

#### Feature Branch Model
- All feature branches come from `main`
- PRs go directly from feature branches to `main`
- Simple but may lead to more merge conflicts

```
main        A-----B-----C-----D
             \           \
feature1      E---F       \
                    \      \
feature2              G---H
```

#### Gitflow Model
- `main` is for production releases
- `develop` is the integration branch
- Feature branches go to `develop`
- Release branches go from `develop` to `main`
- Hotfix branches go directly to both `main` and `develop`

```
main        A-----------------E----------G
             \               /          /
develop       B---C---D-----F----------H
                  \     \
feature1           \     \
                    \     \
feature2             I---J
```

## Best Practices

### Commit Messages
- Use the imperative mood ("Add feature" not "Added feature")
- First line: 50 characters or less, no period
- Leave a blank line after the first line
- Body: Wrap at 72 characters
- Explain what and why, not how

Example:
```
Add user authentication feature

Implement JWT-based authentication to allow users to sign in.
This addresses the security requirement in issue #123.
```

### Branching
- Use descriptive branch names (e.g., `feature/user-authentication`)
- Keep branches short-lived
- Regularly update from the main branch to reduce merge conflicts
- Delete branches after merging

### Pull Requests
- Create focused, single-purpose PRs
- Include a clear description of changes
- Reference related issues
- Add tests for new functionality
- Respond promptly to review comments

### General
- Commit early and often
- Don't commit generated files or dependencies
- Use `.gitignore` to exclude files
- Review your changes before committing
- Don't rewrite public history

## Troubleshooting Common Issues

### Merge Conflicts
```bash
# When a conflict occurs during merge or pull
# 1. Open the conflicted files and resolve conflicts
# 2. Add the resolved files
git add resolved-file.txt
# 3. Complete the merge
git commit

# Abort a merge with conflicts
git merge --abort
```

### Reverting Changes
```bash
# Unstage a file
git restore --staged file.txt  # Git 2.23+
git reset HEAD file.txt        # Older Git versions

# Discard changes in working directory
git restore file.txt           # Git 2.23+
git checkout -- file.txt       # Older Git versions

# Revert a commit
git revert commit-hash

# Reset to a previous commit (caution: rewrites history)
git reset --soft commit-hash   # Keep changes staged
git reset --mixed commit-hash  # Keep changes unstaged (default)
git reset --hard commit-hash   # Discard all changes
```

### Stashing Changes
```bash
# Save changes temporarily
git stash

# List stashes
git stash list

# Apply most recent stash
git stash apply

# Apply specific stash
git stash apply stash@{2}

# Apply and remove most recent stash
git stash pop

# Remove a stash
git stash drop stash@{1}
```

---

This README provides a comprehensive introduction to Git and GitHub. For more detailed information, refer to the [official Git documentation](https://git-scm.com/doc) or [GitHub Docs](https://docs.github.com/).
