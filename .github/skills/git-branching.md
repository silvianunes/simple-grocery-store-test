---
name: git-branching
description: Mandatory Git branching and setup protocol. Use before starting any new feature, bug fix, or refactoring task. Triggers on "create branch", "start feature", "new task", or "/git-branching".
---

# Git Feature Branching Protocol

## Pre-Implementation Steps
Before generating or modifying any application code, perform the following actions:

1. Verify the workspace has no uncommitted changes:
   `git status`
2. Switch to `main` and pull latest changes:
   `git checkout main && git pull origin main`
3. Create and checkout a new descriptive branch using kebab-case:
   `git checkout -b feature/<descriptive-feature-name>`

## Rules
- Never write or modify feature code while on the `main` branch.
- Warn the user if uncommitted changes exist before attempting to switch branches.
- Use standard prefixes: `feature/` for new capabilities, `fix/` for bug fixes.
