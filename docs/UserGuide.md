---
  layout: default.md
  title: "User Guide"
  pageNav: 3
---

# B2B4U User Guide

Business to Business for You (B2B4U) is a **desktop app for managing contacts, optimized for use via a Command Line Interface** (CLI) while still having the benefits of a Graphical User Interface (GUI). If you can type fast, B2B4U can get your contact management tasks done faster than traditional GUI apps.

## Table of Contents

- [B2B4U User Guide](#b2b4u-user-guide)
  - [Table of Contents](#table-of-contents)
  - [Quick Start](#quick-start)
  - [Features](#features)
    - [Command Format](#command-format)
    - [Adding Contacts](#adding-contacts)
    - [Editing Contacts](#editing-contacts)
    - [Deleting Contacts](#deleting-contacts)
      - [Delete a Specific Contact](#delete-a-specific-contact)
      - [Clear All Contacts](#clear-all-contacts)
    - [Viewing a Contact](#viewing-a-contact)
    - [Managing Notes for a Contact](#managing-notes-for-a-contact)
      - [Reminders](#reminders)
    - [Filtering and Sorting the Contact List](#filtering-and-sorting-the-contact-list)
    - [Undo and Redo](#undo-and-redo)
    - [Setting the Theme](#setting-the-theme)
    - [Saving Data](#saving-data)
    - [Maintaining Separate Data Files](#maintaining-separate-data-files)
    - [Editing the Data File Directly](#editing-the-data-file-directly)
    - [Exiting B2B4U](#exiting-b2b4u)
  - [FAQ](#faq)
  - [Known Issues](#known-issues)
  - [Command Summary](#command-summary)

--------------------------------------------------------------------------------------------------------------------

## Quick Start

1. Ensure **Java 17 or later** is installed on your device.

   > **Mac users:** Ensure you have the exact JDK version specified [here](https://se-education.org/guides/tutorials/javaInstallationMac.html).

2. Download the latest `B2B4U.jar` file from [here](https://github.com/AY2526S2-CS2103T-T08-1/tp/releases).

3. Copy the file to the folder you want to use as the _home directory_ for B2B4U.

4. Launch the application using one of the following methods:
   - **Double-click** the `B2B4U.jar` file.
   - **Terminal:** Navigate to the folder containing `B2B4U.jar` and run:
     ```
     java -jar B2B4U.jar
     ```

5. Within a few seconds, a GUI similar to the one below should appear. The app launches with **sample data** preloaded.

   ![Ui]({{ baseUrl }}/images/Ui.png)

6. Type a command in the command box and press **Enter** to execute it. For example, typing `help` and pressing Enter opens the help window.

   Here are some commands to try:

   | Command                                                                          | Description                                  |
   | -------------------------------------------------------------------------------- | -------------------------------------------- |
   | `list`                                                                           | Lists all contacts.                          |
   | `add n/John Doe p/98765432 e/johnd@example.com a/John street, block 123, #01-01` | Adds a contact named `John Doe`.             |
   | `view 1`                                                                         | Opens the detail panel for the 1st contact.  |
   | `close view`                                                                     | Closes the contact detail panel.             |
   | `delete 3`                                                                       | Deletes the 3rd contact in the current list. |
   | `clear`                                                                          | Deletes all contacts.                        |
   | `exit`                                                                           | Exits the app.                               |

7. Refer to [Features](#features) below for the full list of commands, or visit each command's subpage for additional details.

--------------------------------------------------------------------------------------------------------------------

## Features

### Command Format

<box type="info" seamless>

**Notes about the command format:**

- Words in `SCREAMING_SNAKE_CASE` are parameters to be supplied by you.
  e.g. in `add n/NAME`, `NAME` is a parameter, so you might write `add n/John Doe`.

- Items in square brackets are **optional**.
  e.g. `n/NAME [t/TAG]` can be written as `n/John Doe t/friend` or simply `n/John Doe`.

- Items followed by `…` can be used **zero or more times**.
  e.g. `[t/TAG]…` can be omitted entirely, or written as `t/friend`, `t/friend t/family`, etc.

- Prefixed parameters (e.g. `t/TAG`, `open/FILE`) can be in any order, except in the `sort` command where order affects results.
  e.g. `n/NAME p/PHONE` and `p/PHONE n/NAME` are both acceptable.

- Commands that do not take parameters (such as `list`, `exit`, and `clear`) will fail if extra parameters are provided.
  e.g. `exit 123` will fail.

- If you are copying commands from a PDF version of this document, be careful when commands span multiple lines — spaces around line breaks may be lost when pasted.

- Unrecognized commands will display an error message.
  ![unknown command]({{ baseUrl }}/images/unknownCommand.png)

</box>

### Adding Contacts

To add a new contact, use the [`add` command]({{ baseUrl }}/user-guide/add-contact.html).

Format: `add n/NAME [p/PHONE] [e/EMAIL] [a/ADDRESS] [lc/LAST_CONTACTED] [t/TAG]…​`

- At least **one** of `p/PHONE` or `e/EMAIL` must be provided.
- Names are standardized to **Title Case**.
- After adding, if similar contacts exist, the list will filter to show them.

**Examples:**
- `add n/John Doe p/98765432 e/johnd@example.com a/John street, block 123, #01-01`
- `add n/Alex Tan p/91234567`

### Editing Contacts

To edit an existing contact, use the [`edit` command]({{ baseUrl }}/user-guide/edit-contact.html).

Format: `edit INDEX [n/NAME] [p/PHONE] [e/EMAIL] [a/ADDRESS] [lc/LAST_CONTACTED] [t/TAG]…​`

- At least **one** optional field must be provided.
- Editing tags **replaces all existing tags** rather than adding to them.
- You can clear a field by entering its prefix without additional parameters directly after.

**Examples:**
- `edit 1 p/91234567 e/johndoe@example.com`
- `edit 2 n/Betsy Crower t/`

### Deleting Contacts

- To delete a specific contact, use the [`delete` command]({{ baseUrl }}/user-guide/delete-contact.html).
- To delete all contacts at once, use the [`clear` command]({{ baseUrl }}/user-guide/clear-contacts.html).

#### Delete a Specific Contact

Format: `delete INDEX`

- `INDEX` refers to the index number shown in the displayed contact list.
- The index must be a **positive integer** (e.g. 1, 2, 3…).

**Example:**
- `delete 3` — deletes the 3rd contact in the current list.

#### Clear All Contacts

Format: `clear`

<box type="warning" seamless>

**Caution:** `clear` permanently removes **all** contacts. This action can be undone with the `undo` command.

</box>

### Viewing a Contact

To view the details of a contact, use the [`view` command]({{ baseUrl }}/user-guide/view.html).

Format: `view INDEX`

- `INDEX` refers to the index number shown in the displayed contact list.
- The index must be a **positive integer** (e.g. 1, 2, 3…).

**Example:**
- `view 1` — opens the detail panel for the 1st contact.

To close the detail panel, use the [`close view` command]({{ baseUrl }}/user-guide/close-view.html).

Format: `close view`

### Managing Notes for a Contact

To manage notes and reminders for a contact, use the [`note` command]({{ baseUrl }}/user-guide/notes.html).

| Operation                                                                                | Format                                        | Description                                                                                |
| ---------------------------------------------------------------------------------------- | --------------------------------------------- | ------------------------------------------------------------------------------------------ |
| [**Add a note**]({{ baseUrl }}/user-guide/notes.html#add-a-note)                         | `note INDEX NOTE [on/TIME]`                   | Appends a note to the contact. Including `on/TIME` turns it into a [reminder](#reminders). |
| [**Edit a note**]({{ baseUrl }}/user-guide/notes.html#edit-a-specific-note)              | `note INDEX el/NOTE_INDEX NEW_NOTE [on/TIME]` | Replaces the note at the specified position.                                               |
| [**Remove a specific note**]({{ baseUrl }}/user-guide/notes.html#remove-a-specific-note) | `note INDEX cl/NOTE_INDEX`                    | Removes the note at the specified position.                                                |
| [**Remove first N notes**]({{ baseUrl }}/user-guide/notes.html#remove-the-first-n-notes) | `note INDEX c/LINES_TO_REMOVE`                | Removes the first *N* notes, where *N* = `LINES_TO_REMOVE`.                                |
| [**Clear all notes**]({{ baseUrl }}/user-guide/notes.html#clear-all-notes)               | `note INDEX ca/`                              | Removes all notes from the contact.                                                        |

Notes support **contact references** using the `@INDEX` syntax, which creates a bidirectional association between two contacts. Both contacts will appear when searching for either one using `find @INDEX`.

#### Reminders

Any note with a scheduled time (set using `on/TIME`) becomes a reminder. Contacts with active reminders gain a `Reminder` tag, which turns red when the reminder is due within **7 days**. During this window, a pop-up notification will also appear each time you launch B2B4U.

![reminder]({{ baseUrl }}/images/reminder.png)

### Filtering and Sorting the Contact List

By default, B2B4U displays all contacts sorted by **most urgent reminder** first, followed by **most recently contacted**.

B2B4U also provides commands to filter and sort the contact list, which is useful when managing a large number of contacts.

**Filtering:**
- Use the [`find` command]({{ baseUrl }}/user-guide/find-contacts.html) with keywords or field-specific prefixes to filter contacts (e.g. `find n/Alex t/friends`).
- Use `find @INDEX` to find all contacts associated with the contact at that index.
- Use [`find` without any arguments]({{ baseUrl }}/user-guide/find-contacts.html#clearing-filters) to remove all active filters.

**Sorting:**
- Use the [`sort` command]({{ baseUrl }}/user-guide/sort-contacts.html) with field prefixes and `asc` or `desc` to specify the [sort direction]({{ baseUrl }}/user-guide/sort-contacts.html#sort-order-by-field) (e.g. `sort n/asc` to sort by name A–Z, `sort lc/desc` to sort by last contacted, newest first).
- Use [`sort` without any arguments]({{ baseUrl }}/user-guide/sort-contacts.html#resetting-sort-order) to reset to the default sort order.

Active filters and sort orders are preserved independently — running `sort` will not clear your current filter, and vice versa.

Note that the following commands may also affect the active filter or sort order:
- A [`list` command]({{ baseUrl }}/user-guide/list-contacts.html) displays all contacts in the default sort order.
- An [`add` command]({{ baseUrl }}/user-guide/add-contact.html#similar-contacts) resets the sort order and may filter to show only similar contacts.

### Undo and Redo

B2B4U supports undoing and redoing commands to help recover from mistakes.

- To undo the last command, use the [`undo` command]({{ baseUrl }}/user-guide/undo-command.html).
- To redo the last undone command, use the [`redo` command]({{ baseUrl }}/user-guide/redo-command.html).

### Setting the Theme

B2B4U includes several color palettes (called "themes") to customize the look of the app.

To switch themes, use the [`theme THEME_NAME` command]({{ baseUrl }}/user-guide/set-theme.html).

Available themes:

| Theme                                                                     | Command        |
| ------------------------------------------------------------------------- | -------------- |
| [Dark mode]({{ baseUrl }}/user-guide/set-theme.html#dark-mode-dark)       | `theme dark`   |
| [Light mode]({{ baseUrl }}/user-guide/set-theme.html#light-mode-light)    | `theme light`  |
| [Reading mode]({{ baseUrl }}/user-guide/set-theme.html#reading-mode-book) | `theme book`   |
| [Sakura]({{ baseUrl }}/user-guide/set-theme.html#sakura-theme-sakura)     | `theme sakura` |
| [Grass]({{ baseUrl }}/user-guide/set-theme.html#grass-theme-grass)        | `theme grass`  |
| [Techcore]({{ baseUrl }}/user-guide/set-theme.html#techcore-tech)         | `theme tech`   |
| [Jirai Kei]({{ baseUrl }}/user-guide/set-theme.html#jirai-kei-jirai)      | `theme jirai`  |

### Saving Data

B2B4U automatically saves your data to disk after any command that modifies it. There is no need to save manually.

### Maintaining Separate Data Files

B2B4U supports multiple data files, which is useful for keeping separate contact lists (e.g. work vs. personal contacts). All data files must be stored in the data folder: `[JAR file location]/data/`.

- To view all available data files, use the [`view files` command]({{ baseUrl }}/user-guide/view.html#viewing-available-files-view-files).
- To open a data file, use the [`file open/FILE_NAME` command]({{ baseUrl }}/user-guide/file.html#open-file-file-open).
- To delete a data file, use the [`file delete/FILE_NAME` command]({{ baseUrl }}/user-guide/file.html#deleting-a-file-file-delete).
- To close the file list panel, use the [`close view` command]({{ baseUrl }}/user-guide/close-view.html). The file list shares the same panel used to view contact details.

### Editing the Data File Directly

B2B4U data is saved as JSON files in `[JAR file location]/data/`. Advanced users may edit these files directly.

<box type="warning" seamless>

**Caution:** If your edits make the data file's format invalid, B2B4U will discard all data and start with an empty file on the next launch. Always **back up the file** before making direct edits. Certain changes may also cause unexpected app behavior (e.g. if a value falls outside the acceptable range). Only edit the data file if you are confident you can do so correctly.

</box>

### Exiting B2B4U

To exit B2B4U, use the [`exit` command]({{ baseUrl }}/user-guide/exit-program.html).

--------------------------------------------------------------------------------------------------------------------

## FAQ

**Q: How do I transfer my data to another computer?**

Install B2B4U on the new computer, then replace the empty data file it creates with the data file from your previous B2B4U home folder.

--------------------------------------------------------------------------------------------------------------------

## Known Issues

1. **Multiple screens:** If you move B2B4U to a secondary screen and later switch back to using only the primary screen, the GUI may open off-screen.

   *Fix:* Delete the `preferences.json` file created by the app before relaunching.

2. **Minimized Help Window:** If the Help Window is minimized and you run `help` again (via command, the Help menu, or `F1`), the original window remains minimized and no new window appears.

   *Fix:* Manually restore the minimized Help Window.

--------------------------------------------------------------------------------------------------------------------

## Command Summary

| Action                   | Format         | Parameters                                                                                                                    | Examples                                                                                           |
| ------------------------ | -------------- | ----------------------------------------------------------------------------------------------------------------------------- | -------------------------------------------------------------------------------------------------- |
| **Help**                 | `help`         | `[COMMAND]`                                                                                                                   | `help add`                                                                                         |
| **Add contact**          | `add`          | `n/NAME [p/PHONE] [e/EMAIL] [a/ADDRESS] [lc/LAST_CONTACTED] [t/TAG]…`                                                         | `add n/James Ho p/22224444 e/jamesho@example.com a/123, Clementi Rd, 1234665 t/friend t/colleague` |
| **Edit contact**         | `edit`         | `INDEX [n/NAME] [p/PHONE] [e/EMAIL] [a/ADDRESS] [lc/LAST_CONTACTED] [t/TAG]…`                                                 | `edit 2 n/James Lee e/jameslee@example.com`                                                        |
| **Delete contact**       | `delete`       | `INDEX`                                                                                                                       | `delete 3`                                                                                         |
| **Clear all contacts**   | `clear`        |                                                                                                                               |                                                                                                    |
| **Add note**             | `note`         | `INDEX NOTE [on/TIME]`                                                                                                        | `note 1 To meet in February on/15 Apr`                                                             |
| **Edit note**            | `note`         | `INDEX el/NOTE_INDEX NEW_NOTE [on/TIME]`                                                                                      | `note 1 el/1 Updated note text.`                                                                   |
| **Remove specific note** | `note`         | `INDEX cl/NOTE_INDEX`                                                                                                         | `note 1 cl/2`                                                                                      |
| **Remove first N notes** | `note`         | `INDEX c/LINES_TO_REMOVE`                                                                                                     | `note 1 c/2`                                                                                       |
| **Clear all notes**      | `note`         | `INDEX ca/`                                                                                                                   | `note 1 ca/`                                                                                       |
| **List contacts**        | `list`         |                                                                                                                               |                                                                                                    |
| **Find contacts**        | `find`         | `[KEYWORD]… [n/NAME] [p/PHONE] [e/EMAIL] [a/ADDRESS] [t/TAG]…`                                                                | `find n/James t/friends`                                                                           |
| **Find by association**  | `find`         | `@INDEX`                                                                                                                      | `find @1`                                                                                          |
| **Clear filters**        | `find`         |                                                                                                                               |                                                                                                    |
| **Sort contacts**        | `sort`         | `[n/asc \| desc] [p/asc \| desc] [e/asc \| desc] [a/asc \| desc] [lu/asc \| desc] [lc/asc \| desc] [t/TAG_NAME:asc \| desc]…` | `sort n/asc t/friends:desc`                                                                        |
| **Reset sort order**     | `sort`         |                                                                                                                               |                                                                                                    |
| **Undo**                 | `undo`         |                                                                                                                               |                                                                                                    |
| **Redo**                 | `redo`         |                                                                                                                               |                                                                                                    |
| **View contact**         | `view`         | `INDEX`                                                                                                                       | `view 1`                                                                                           |
| **Close detail panel**   | `close view`   |                                                                                                                               |                                                                                                    |
| **Open file**            | `file open/`   | `FILE_NAME`                                                                                                                   | `file open/newContactList`                                                                         |
| **Delete file**          | `file delete/` | `FILE_NAME`                                                                                                                   | `file delete/OldContactList`                                                                       |
| **View available files** | `view files`   |                                                                                                                               |                                                                                                    |
| **Change theme**         | `theme`        | `THEME_NAME`                                                                                                                  | `theme sakura`                                                                                     |
