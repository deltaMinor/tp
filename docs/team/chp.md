---
  layout: default.md
  title: "Chen Ping's Project Portfolio Page"
---

### Project: Business to Business for You

Business to Business for You (B2B4U) is a desktop application for managing B2B contacts, optimised for use via a Command Line Interface (CLI). The user interacts with it using a CLI, and it has a GUI created with JavaFX. It is written in Java, and has about 10 kLoC.

Given below are my contributions to the project.

* **New Feature**: Added the `note edit` sub-command to edit existing notes. [\#199](https://github.com/AY2526S2-CS2103T-T08-1/tp/pull/199)
  * What it does: Allows the user to edit a specific existing note on a contact using `note INDEX el/NOTE_INDEX NEW_TEXT [on/TIME]`, replacing the note at the given index with new content. Supports setting or updating reminders inline.
  * Justification: Previously, users had to remove and re-add notes to correct mistakes or update information. This feature provides a direct and efficient way to modify notes without losing the rest of the contact's note history.
  * Highlights: The implementation required extending `NoteCommandParser` to distinguish between the new `el/` prefix and existing prefixes, and ensuring that contact references (`@INDEX`) in the new note text are resolved correctly. The note prefix naming was also standardised across all note sub-commands in this PR.

* **New Feature**: Added field removal on empty edit prefix. [\#174](https://github.com/AY2526S2-CS2103T-T08-1/tp/pull/174)
  * What it does: When a user inputs a field prefix with no argument during an `edit` command (e.g. `edit 1 p/`), the corresponding optional field is removed from the contact. Supported for phone, email, address, and lastContacted fields.
  * Justification: Users often need to remove outdated optional information from a contact. This feature offers an intuitive shorthand instead of requiring a separate delete-field command.
  * Highlights: Validation was added to ensure the resulting contact still retains at least a phone number or email address, preventing contacts from being left with no reachable information.

* **New Feature**: Enhanced `help` command to support per-command usage info. [\#223](https://github.com/AY2526S2-CS2103T-T08-1/tp/pull/223)
  * What it does: `help COMMAND` (e.g. `help add`) now shows usage information and a link to the relevant User Guide page for that specific command directly in the Help Window. Using `help` with no arguments continues to open the general Help Window.
  * Justification: The previous `help` command only opened a generic help window. Users had no quick way to look up command syntax without navigating the full User Guide.
  * Highlights: Required restructuring `HelpCommand`, `HelpWindow`, and `MainWindow` to support dynamic content injection. A new `HelpInfo` class was introduced to pair command usage strings with their User Guide URLs. Menu items for all available commands were also added to the Help menu in the GUI.

* **Bug Fix**: Fixed `find @INDEX` to support bidirectional cross-referencing. [\#268](https://github.com/AY2526S2-CS2103T-T08-1/tp/pull/268)
  * What it does: `find @INDEX` now returns both contacts that the target references and contacts whose notes reference the target, making the association lookup fully bidirectional.
  * Justification: The original implementation only performed a forward lookup, so users could miss half of the associations between contacts.
  * Highlights: `FindAssociationsCommand` was extended with a reverse-lookup pass that checks all contacts' notes for UUID references to the target. The nested loop was later refactored to improve readability after review feedback.

* **Code contributed**: [RepoSense link](https://nus-cs2103-ay2526s2.github.io/tp-dashboard/#breakdown=true&search=p12010304)

* **Project management**:
  * Managed merging of PRs and resolved merge conflicts across multiple feature branches.

* **Enhancements to existing features**:
  * Fixed `Contact.equals()` and `hashCode()` to include notes field, ensuring correct deduplication behaviour. [\#247](https://github.com/AY2526S2-CS2103T-T08-1/tp/pull/247)
  * Fixed `Note.equals()` and `hashCode()` to include the `timePoint` field. [\#246](https://github.com/AY2526S2-CS2103T-T08-1/tp/pull/246)
  * Fixed `parseName` crashing on empty name input. [\#224](https://github.com/AY2526S2-CS2103T-T08-1/tp/pull/224)
  * Fixed `NoteClearCommand` and `NoteClearAllCommand` success messages. [\#240](https://github.com/AY2526S2-CS2103T-T08-1/tp/pull/240)
  * Fixed `note c/` command accepting negative numbers. [\#245](https://github.com/AY2526S2-CS2103T-T08-1/tp/pull/245)
  * Fixed `JsonAdaptedContactTest` failures after `Note.equals` update. [\#249](https://github.com/AY2526S2-CS2103T-T08-1/tp/pull/249)

* **Documentation**:
  * User Guide:
    * Added documentation for `note edit` and `note remove` sub-commands, and the `help` command. [\#248](https://github.com/AY2526S2-CS2103T-T08-1/tp/pull/248)
    * Updated the User Guide with command summaries for the `add`, `edit`, `note`, `find`, and `sort` commands. [\#288](https://github.com/AY2526S2-CS2103T-T08-1/tp/pull/288)
  * Developer Guide:
    * Added a UML sequence diagram for the edit field removal feature. [\#190](https://github.com/AY2526S2-CS2103T-T08-1/tp/pull/190)

* **Community**:
  * PRs reviewed (with non-trivial review comments): [\#286](https://github.com/AY2526S2-CS2103T-T08-1/tp/pull/286), [\#292](https://github.com/AY2526S2-CS2103T-T08-1/tp/pull/292), [\#293](https://github.com/AY2526S2-CS2103T-T08-1/tp/pull/293), [\#295](https://github.com/AY2526S2-CS2103T-T08-1/tp/pull/295), [\#301](https://github.com/AY2526S2-CS2103T-T08-1/tp/pull/301)
