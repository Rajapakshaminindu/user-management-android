# Contributing Guidelines

Thank you for contributing to the User Management Android application!

## Development Workflow

1. **Branching Strategy**
   - Create feature or fix branches from `main` using descriptive names:
     - `feature/<feature-name>`
     - `fix/<bug-name>`
     - `chore/<task-name>`

2. **Coding Standards**
   - Write idiomatic Kotlin conforming to official Android guidelines.
   - Keep Jetpack Compose composables modular and state-driven.
   - Ensure input fields perform proper validation via `UserValidator`.
   - Maintain the Room repository and MVVM architecture separation.

3. **Testing**
   - Write unit tests for new business logic and validation rules in `app/src/test`.
   - Run tests before opening a pull request:
     ```bash
     ./gradlew test
     ```

4. **Pull Requests**
   - Provide a clear PR title summarizing the change.
   - Describe what was changed and why in the PR description.
   - Ensure continuous integration checks pass before merging.
