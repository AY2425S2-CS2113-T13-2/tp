# Jing Yen (jing-yen) - Project Portfolio Page

## Overview

## Summary of Contributions

* **Code Contributed**: [RepoSense Link](https://nus-cs2113-ay2425s2.github.io/tp-dashboard/?search=jing-yen&breakdown=true)

**Enhancements Implemented:**

1.  **Core Feature: Multi-Group Management (`#108`)**
    *   **Description:** Implemented the foundational "Groups" feature, allowing users to manage expenses across multiple, distinct groups (e.g., "Japan Trip", "Project Alpha"). This was a core part of PayPals' value proposition.
    *   **Complexity & Implementation:**
        *   **Storage Overhaul:** Redesigned the `Storage` class (`src/main/java/paypals/util/Storage.java`) significantly. Moved from a single save file model to a multi-file system. This involved:
            *   Introducing a `master-savefile.txt` to track the names of all existing groups.
            *   Dynamically creating and managing individual `.txt` save files for each group based on user-provided names.
            *   Implementing logic in `Storage::load` to handle both loading existing groups (by name or number) and creating new groups, including updating the master file.
        *   **Filename Validation:** Implemented robust filename validation (`Storage::checkIfFilenameValid`) to prevent errors caused by invalid characters or reserved filenames across different operating systems. This required researching OS-specific limitations (e.g., `CON`, `PRN` on Windows, `/` on Unix-like systems) and writing targeted tests (`StorageTest.java`) using JUnit 5's `@EnabledOnOs` annotation to ensure cross-platform compatibility. The check involved attempting file creation and verifying the result, handling potential `IOException`s gracefully.
        *   **Integration:** Coordinated the storage changes with the group selection UI (`Group::selectGroup`) and data management (`ActivityManager`).
    *   **Impact:** This enhancement is fundamental to the application's design, enabling users to organize their expenses effectively for different contexts. The complexity lay in managing multiple file I/O operations reliably and handling OS-specific constraints.

2.  **Core Feature: Add Transaction Command (`#40`)**
    *   **Description:** Implemented the `add` command (`src/main/java/paypals/commands/AddCommand.java`), allowing users to record new expense activities.
    *   **Complexity & Implementation:**
        *   **Regex-based Parsing:** Utilized regular expressions (`Pattern`, `Matcher`) to parse the command string, extracting distinct parameters like description (`d/`), payer (`n/`), friends (`f/`), and amounts (`a/`).
        *   **Advanced Regex:** Employed lookahead and lookbehind techniques within the regex patterns (or careful splitting and trimming) to precisely extract parameter values while excluding the flags themselves (e.g., getting "Lunch" from `d/Lunch`).
        *   **Data Structure Population:** Used the extracted data to instantiate `Person` objects and construct a new `Activity` object (`src/main/java/paypals/Activity.java`).
        *   **Error Handling:** Incorporated checks for various invalid inputs, such as the payer owing themselves (`PAYER_OWES`), duplicate friend entries (`DUPLICATE_FRIEND`), invalid amounts (`INVALID_AMOUNT`), missing amounts (`NO_AMOUNT_ENTERED`), and multiple amounts for a single friend (`MULTIPLE_AMOUNTS_ENTERED`), throwing specific `PayPalsException`s. These scenarios were rigorously tested in `AddCommandTest.java`.
    *   **Impact:** This is a primary command for data input, essential for the application's core functionality. The challenge was creating a parser robust enough to handle variations in input and provide clear error messages.

3.  **Enhancement: Case-Insensitive Operations (`#194`)**
    *   **Description:** Modified the application to accept commands and flags regardless of case (e.g., `add`, `Add`, `ADD` are treated identically).
    *   **Complexity & Implementation:**
        *   **Wide-Ranging Impact:** This change affected multiple parts of the system, primarily command parsing. The core logic was implemented in `Activity.java`, `ActivityManager.java` and all children classes of `Command.java` by converting the extracted command word to lowercase before matching it against known commands.
        *   **Testing Strategy:** Due to the pervasive nature of this change, Test-Driven Development (TDD) and extensive Exploratory Testing were crucial. I wrote tests first to define the expected case-insensitive behavior and then modified the code, iteratively running tests to catch regressions in command recognition and parameter parsing across various commands (`AddCommand`, `ListCommand`, `DeleteCommand`, etc.).
    *   **Impact:** Significantly improved user experience (UX) by making the application more forgiving of user input variations. The difficulty stemmed from ensuring consistency across all commands without introducing side effects.

4.  **Enhancement: UI Improvements**
    *   **Description:** Contributed enhancements to the user interface (`UI.java`).
    *   **Implementation:** Added methods like `printGroupNames` to display available groups clearly during selection, visual feedback like `printLoading` for potentially long operations, and formatting helpers (`printBold`, `printUnderlined`) for better text presentation.
    *   **Impact:** Improved the clarity and user-friendliness of the CLI interaction, particularly during the group selection phase.

### Documentation
**Authored Project README (`#140`)**
*   **Description:** Created and maintained the main `README.md` file, which serves as the primary landing page for the project repository and the deployed GitHub Pages site (`docs/README.md`).
*   **Implementation & Value:** This involved:
    *   Summarizing PayPals' purpose and core value proposition concisely.
    *   Highlighting key features to give potential users a quick understanding of capabilities.
    *   Writing a clear and easy-to-follow "Quick Start" guide
    *   Structuring the document logically and ensuring it acts as a central hub by providing clear links to the detailed User Guide (UG), Developer Guide (DG), and About Us page.
*   **Impact:** This file is crucial for user adoption and developer onboarding, providing the essential first steps and directing users/developers to further resources. It significantly improves the project's accessibility and professional presentation.

### Bug Fixes:

*   **Fixed Illegal Flag Input:** Implemented stricter input validation/parsing logic to filter out incorrectly formatted flags (e.g., flags with prefixes other than the single designated character like `ii/IDENTIFIER` instead of `i/IDENTIFIER`). This resolved inconsistent behavior observed in v2.0 where such inputs could lead to unexpected errors or incorrect processing.
*   **User Guide Updates:** Updated the User Guide (`docs/UserGuide.md`) to clarify setup instructions, command usage, and fix typographical errors, improving its accuracy and readability.

### Community
*   Pull requests reviewed and merged: [#179](https://github.com/AY2425S2-CS2113-T13-2/tp/pull/179), [200](https://github.com/AY2425S2-CS2113-T13-2/tp/pull/200)

### Forum Contribution - GitHub Pages Deployment Conflict Resolution:
*   **Description:** Identified and asked about a common issue where a personal GitHub Pages site (`username.github.io`) conflicted with the project's site deployment ([Issue #20](https://github.com/nus-cs2113-AY2425S2/forum/issues/20)).
*   **Contribution:** After initial suggestions involved deleting the conflicting repository, I discovered and shared an alternative solution: modifying the deployment source settings of the *personal* repository (`jing-yen.github.io`) under 'Pages' to deploy from 'None'.
*   **Impact:** This effectively disabled the personal site without deleting the repository, allowing the project site to deploy correctly. Shared this finding on the forum thread to help other students facing similar deployment conflicts avoid potentially deleting their personal work.