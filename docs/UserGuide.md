---
  layout: default.md
  title: "User Guide"
  pageNav: 3
---

# B2B4U User Guide

Business to Business for You (B2B4U) is a **desktop app for managing contacts, optimized for use via a Command Line Interface** (CLI) while still having the benefits of a Graphical User Interface (GUI). If you can type fast, B2B4U can get your contact management tasks done faster than traditional GUI apps.

B2B4U is designed for **consultants at medium-sized consulting or PR agencies** who need to manage long-term relationships with clients and companies. A contact in B2B4U can represent an individual person or a business entity, with optional fields to accommodate different contact types (e.g. a business may not have a physical address). It provides quick, up-to-date access to client data through a simple, unified interface — helping you deliver fast and on-point responses.

## Table of Contents

- [B2B4U User Guide](#b2b4u-user-guide)
  - [Table of Contents](#table-of-contents)
  - [Quick Start](#quick-start)
  - [Features](#features)
    - [Command Format](#command-format)
    - [Flexible Time Input](#flexible-time-input)
    - [Contact Fields and Prefixes](#contact-fields-and-prefixes)
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

### Flexible Time Input

A contact may contain fields that require a date/time as input.
For this purpose, B2B4U supports flexible date and time input formats.
To successfully input a date, the user input must fulfill the following criteria:
- The input must contain exactly one word parameter `MONTH` which represents the month:
  - Valid formats for `MONTH`: 
    - The full name of the month (e.g. `January`, `March`), case-insensitive (e.g. `january` will be accepted as `January`, `MARCH` will be accepted as `March`).
    - A three-letter abbreviation of the month (e.g. `Jan`, `Mar`), case-insensitive (e.g. `jan` will be accepted as `Jan`, `MAR` will be accepted as `Mar`).
- The input must contain exactly one numeric parameter `DAY` which represents the day of the month:
- `DAY` and `MONTH` must be separated by a valid `SEPARATOR`:
  - A `SEPARATOR` can be any one of the following characters: `/`, `\`, `-`, `,` or ` `(whitespace).
  - Additional whitespaces in between the `SEPARATOR` and the parameters are allowed.
- `DAY` and `MONTH` must in combination form a valid date with the given year(how the year may be input is explained below).

Additionally, the input may contain the following parameters:
- Exactly one 4-digit numeric parameter `YEAR` which represents the year:
  - If `YEAR` is absent, the current year will be used for the date on default.
  - As mentioned above, `YEAR` must form a valid date with the given `DAY` and `MONTH`.
- Exactly one parameter `TIME` which represents the time of day, which can be any one of the following formats:
  - 24 hour `HH:MM` format:
     - If the hour can be represented as a single digit, the first zero can be omitted. (e.g. 07:30 and 7:30 will both be accepted as 7:30AM)
     - The time must be valid: `HH` must be between 0 and 23(inclusive), `MM` must be between 00 and 59(inclusive).
  - 12 hour `HH:MM(AM/PM)` format:
    - The constraints for the `HH:MM` are similar to 24 hour `HH:MM` format, with the exception that `HH` must be between 1 and 12(inclusive).
    - There must be exactly one Meridiem suffix(`AM` or `PM`) at the end of the time.
    - The Meridiem suffix must be directly attached to the clock time(`HH:MM`) and cannot be separated by any other character(s).
    - Example: `7:30AM`, `12:00PM`, `11:59PM`
  - 12 hour simplified `HH(AM/PM)` format:
    - Similar to the 12 hour `HH:MM(AM/PM)` format with the colon and minute parameter is omitted.
    - Example: `11AM` which will be the same as `11:00` and `11:00AM`
  - `TIME` will only be parsed if provided alongside a valid date.
- All parameters must be similarly separated by a valid `SEPARATOR`.

The date/time input should fall into at least one of the following patterns, rather than randomly ordered:

<box type="info" seamless>
**Note:** In the following tables, `SEPARATOR` will be represented by `/`.
</box>

- Partial date(containing only `DAY` and `MONTH`):

| Pattern   | Format      | Example  |
|-----------|-------------|----------|
| Day-month | `DAY/MONTH` | `31 Oct` |
| Month-day | `MONTH/DAY` | `Oct 31` |

- Date only(containing only `DAY`, `MONTH` and `YEAR`):

| Pattern               | Format           | Example       |
|-----------------------|------------------|---------------|
| Little-endian         | `DAY/MONTH/YEAR` | `31 Oct 2026` |
| Middle-endian         | `MONTH/DAY/YEAR` | `Oct 31 2026` |
| Big-endian            | `YEAR/MONTH/DAY` | `2026 Oct 31` |
| Reverse middle-endian | `YEAR/DAY/MONTH` | `2026 31 Oct` |

- Partial date + time(containing only `DAY`, `MONTH` and `TIME`):

| Pattern                 | Format           | Example         |
|-------------------------|------------------|-----------------|
| Time-prefixed day-month | `TIME/DAY/MONTH` | `12:00 31 Oct`  |
| Time-prefixed month-day | `TIME/MONTH/DAY` | `12:00 Oct 31`  |
| Time-suffixed day-month | `DAY/MONTH/TIME` | `31 Oct 12:00`  |
| Time-suffixed month-day | `MONTH/DAY/TIME` | `Oct 31 12:00`  |

- Full date + time(containing `DAY`, `MONTH`, `YEAR`, and `TIME`):

| Pattern                             | Format                | Example             |
|-------------------------------------|-----------------------|---------------------|
| Time-prefixed little-endian         | `TIME/DAY/MONTH/YEAR` | `12:00 31 Oct 2026` |
| Time-prefixed middle-endian         | `TIME/MONTH/DAY/YEAR` | `12:00 Oct 31 2026` |
| Time-prefixed big-endian            | `TIME/YEAR/MONTH/DAY` | `12:00 2026 Oct 31` |
| Time-prefixed reverse middle-endian | `TIME/YEAR/DAY/MONTH` | `12:00 2026 31 Oct` |
| Time-suffixed little-endian         | `DAY/MONTH/YEAR/TIME` | `12:00 31 Oct 2026` |
| Time-suffixed middle-endian         | `MONTH/DAY/YEAR/TIME` | `Oct 31 2026 12:00` |
| Time-suffixed big-endian            | `YEAR/MONTH/DAY/TIME` | `2026 Oct 31 12:00` |
| Time-suffixed reverse middle-endian | `YEAR/DAY/MONTH/TIME` | `2026 31 Oct 12:00` |

<box type="info" seamless>
**Note:** For additional ease of input, B2B4U also allows the following input keywords which can be parsed as a date based on the current system time.

| Keyword                               | Result                                            |
|---------------------------------------|---------------------------------------------------|
| `today`                               | Current system date                               |
| `tomorrow`                            | The day after the current system date             |
| `next week`                           | 7 days after the current system date              |
| `sunday` or `sun`                     | The first Sunday after the current system date    |
| `monday` or `mon`                     | The first Monday after the current system date    |
| `tuesday`, `tues` or `tue`            | The first Tuesday after the current system date   |
| `wednesday` or `wed`                  | The first Wednesday after the current system date |
| `thursday`, `thurs`, `thur`, or `thu` | The first Thursday after the current system date  |
| `friday` or `fri`                     | The first Friday after the current system date    |
| `saturday` or `sat`                   | The first Saturday after the current system date  |

The keyword input is case-insensitive. <br>
Only one of such keywords may be used in a singular input regarding time, and if used, it can replace the above "month" and "day" input requirements entirely. <br>
Example: On 24 March 2026, inputting `next week 12PM` for a [reminder]({{ baseUrl }}/user-guide/notes.html#reminders) sets the reminder for 12:00PM 31 March 2026.
</box>

Once a valid date or time hsa been accepted as input, it will be displayed by B2B4U in the following formats:
- A date is displayed in "`Mth` `DAY`, `YEAR`" format, wherein `Mth` is the month abbreviated to three letters with only its first letter capitalised.
  - Example: 31 October 2026 will be displayed as "Oct 31, 2026".
- A date with time is displayed in "`HH:mm`, `Mth` `DAY`, `YEAR`" format.
  - Example: 12:00 noon on 31 October 2026 will be displayed as "12:00, Oct 31, 2026".
<box type="info" seamless>
**Note:** Both of the above display formats are also accepted input formats by B2B4U.
</box>

To allow for user freedom, it is not necessary that the user creates an input that is a valid date, or even an input that resembles a time for a field related to time. <br>
This allows freedom for the following use cases:
- If the user cannot remember clearly the last time they had contacted someone but wants to note down that is has been a while since they were in contact, they can edit their contact's `LastContacted` field to the phrase "over a year ago".
- If the user is planning to meet someone sometime in December, however it is currently January and much of their other plans are yet to be set in stone, they can add a reminder with its time set to "sometime in December", then edit its date once they have finalised on a more concrete timing.

This does mean that if an attempt to input a date contains an input that cannot be successfully parsed as one, it will be recorded as is.
This can be identified to be the case when the field's data appears just like its input, rather than in the standard format of representing a date/time in B2B4U, explained above. <br>
If an incorrectly input date/time is indeed recorded, the user should use one of the edit commands or the [`undo`](#undo-and-redo) command to correct their mistake.

### Contact Fields and Prefixes

**Name(**`n/`**):**
- The contact's name.
- Can contain symbols `.` `'` `-` `_` `&` `/` and letter characters from any language.
- First character must be a letter character.
- Must be non-empty.

**Phone number(**`p/`**):**
- The contact's phone number.
- Can only contain '+' and digits.
- Without a country code, should be 5-14 digits long. 
- With a country code (starting with '+'), should be 8-15 digits long.

**Email(**`e/`**):**
- The contact's email address.
- Should be of the format `local-part@domain`
  - Constraints of `local-part`:
    - Must contain only alphanumeric characters and the special characters `.`, `_`, `%`, `+`, and `-`.
    - Must start and end with an alphanumeric character.
    - Is at least 1 character long.
    - Cannot have consecutive periods.
  - `domain` consists of one or more `label`s separated by periods. Constraints of `label`:
    - Must start and end with an alphanumeric character.
    - Must contain only alphanumeric characters or hyphens.
    - Is at least 1 character long
  - The last domain label (top-level domain) must be at least 2 characters long, and must be alphabetic.

**Address(**`a/`**):**
- The contact's address.
- Can take any values, but should not be blank.

**Last Contacted(**`lc/`**):**
- The last time the user has contacted this person.
- A non-blank [time-related input](#flexible-time-input).

**Tag(**`t/`**):**
- A tag to add onto the contact.
- Can only contain alphanumeric characters.

**Notes:**
- See [Managing Notes for a Contact](#managing-notes-for-a-contact).

### Adding Contacts

To add a new contact, use the [`add` command]({{ baseUrl }}/user-guide/add-contact.html).

Format: `add n/NAME (p/PHONE | e/EMAIL) [a/ADDRESS] [lc/LAST_CONTACTED] [t/TAG]…​`

- At least **one** (or both) of `p/PHONE` or `e/EMAIL` must be provided.
- After adding, if similar contacts exist, the list will filter to show them.

**Examples:**
- `add n/John Doe p/98765432 e/johnd@example.com a/John street, block 123, #01-01`
- `add n/Alex Tan p/91234567`

### Editing Contacts

To edit an existing contact, use the [`edit` command]({{ baseUrl }}/user-guide/edit-contact.html).

Format: `edit INDEX [n/NAME] [p/PHONE] [e/EMAIL] [a/ADDRESS] [lc/LAST_CONTACTED] [t/TAG]…​`

- At least **one** optional field must be provided.
- Editing tags **replaces all existing tags** rather than adding to them.
- You can remove an optional field by providing its prefix with no value (e.g. `t/` to clear all tags, `p/` to remove the phone number).

**Examples:**
- `edit 1 p/91234567 e/johndoe@example.com`
- `edit 2 n/Betsy Crower t/` — renames and clears all tags

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
| [**Remove first N notes**]({{ baseUrl }}/user-guide/notes.html#remove-the-first-n-notes) | `note INDEX co/LINES_TO_REMOVE`               | Removes the first *N* notes, where *N* = `LINES_TO_REMOVE`.                                |
| [**Clear all notes**]({{ baseUrl }}/user-guide/notes.html#clear-all-notes)               | `note INDEX ca/`                              | Removes all notes from the contact.                                                        |

Notes support **contact references** using the `@INDEX` syntax, which creates a bidirectional association between two contacts. Both contacts will appear when searching for either one using `find @INDEX`.

#### Reminders

Any note with a scheduled time (set using `on/TIME`) becomes a reminder. Contacts with active reminders gain a `Reminder` tag, which turns red when the reminder is due within **7 days**. During this window, a pop-up notification will also appear each time you launch B2B4U. <br>
The input format of `TIME` is [flexible](#flexible-time-input).

![reminder]({{ baseUrl }}/images/reminder.png)

### Filtering and Sorting the Contact List

By default, B2B4U displays all contacts sorted by **most urgent reminder** first, followed by **most recently contacted**.

B2B4U also provides commands to filter and sort the contact list, which is useful when managing a large number of contacts.

**Filtering:**

Format: `find [KEYWORD]… [n/NAME] [p/PHONE] [e/EMAIL] [a/ADDRESS] [lc/LAST_CONTACTED] [t/TAG]…`

- Use the [`find` command]({{ baseUrl }}/user-guide/find-contacts.html) with keywords or field-specific prefixes to filter contacts (e.g. `find n/Alex t/friends`).
- Use `find @INDEX` to find all contacts associated with the contact at that index.
- Use [`find` without any arguments]({{ baseUrl }}/user-guide/find-contacts.html#clearing-filters) to remove all active filters.

**Sorting:**

Format: `sort [n/asc | desc] [p/asc | desc] [e/asc | desc] [a/asc | desc] [lu/asc | desc] [lc/asc | desc] [t/TAG_NAME:asc | desc]…`

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
| **Add contact**          | `add`          | `n/NAME (p/PHONE \| e/EMAIL) [a/ADDRESS] [lc/LAST_CONTACTED] [t/TAG]…`                                                         | `add n/James Ho p/22224444 e/jamesho@example.com a/123, Clementi Rd, 1234665 t/friend t/colleague` |
| **Edit contact**         | `edit`         | `INDEX [n/NAME] [p/PHONE] [e/EMAIL] [a/ADDRESS] [lc/LAST_CONTACTED] [t/TAG]…`                                                 | `edit 2 n/James Lee e/jameslee@example.com`                                                        |
| **Delete contact**       | `delete`       | `INDEX`                                                                                                                       | `delete 3`                                                                                         |
| **Clear all contacts**   | `clear`        |                                                                                                                               |                                                                                                    |
| **Add note**             | `note`         | `INDEX NOTE [on/TIME]`                                                                                                        | `note 1 To meet in February on/15 Apr`                                                             |
| **Edit note**            | `note`         | `INDEX el/NOTE_INDEX NEW_NOTE [on/TIME]`                                                                                      | `note 1 el/1 Updated note text.`                                                                   |
| **Remove specific note** | `note`         | `INDEX cl/NOTE_INDEX`                                                                                                         | `note 1 cl/2`                                                                                      |
| **Remove first N notes** | `note`         | `INDEX co/LINES_TO_REMOVE`                                                                                                    | `note 1 co/2`                                                                                      |
| **Clear all notes**      | `note`         | `INDEX ca/`                                                                                                                   | `note 1 ca/`                                                                                       |
| **List contacts**        | `list`         |                                                                                                                               |                                                                                                    |
| **Find contacts**        | `find`         | `[KEYWORD]… [n/NAME] [p/PHONE] [e/EMAIL] [a/ADDRESS] [lc/LAST_CONTACTED] [t/TAG]…`                                            | `find n/James t/friends`                                                                           |
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
