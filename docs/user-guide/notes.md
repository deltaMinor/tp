# Adding notes/reminders to a contact: `note`

Manages notes and reminders for an existing contact in the contact list.

### Add a note

Format: `note INDEX NOTE [on/TIME]`

* Appends `NOTE` to the contact at the specified `INDEX`. The index **must be a positive integer** 1, 2, 3, …​
* New notes are stacked underneath existing ones.
* `TIME` can accept most conventional date/time formats and may omit the year. If unable to parse as a date, it will be saved as a plain string.
* Filling the `on/TIME` field turns the note into a [reminder](#reminders). The system will warn of reminders due within 1 week.
* Notes support **contact references** using the `@INDEX` syntax. When you include `@INDEX` in a note, it creates a link to the contact at that index. The reference is displayed as the contact's name in **bold and underlined** text.
* If a referenced contact's name changes, the displayed name updates automatically.
* If a referenced contact is deleted, the reference is replaced with the contact's name as plain text.
* If a contact reference is corrupted (e.g. changed to an invalid value in the data file), it will be displayed as `@Unknown` in the note.
* You cannot add a note that is **identical** to one already on that contact (same text **and** the same reminder time, if any). If you try, the command fails and no change is made.

Examples:
* `note 1 Likes to swim.`
* `note 2 Follow up call on/15 Apr`
* `note 1 Worked with @2` — creates a reference to the 2nd contact in the note.

![add note]({{ baseUrl }}/images/addNote.png)

### Edit a specific note

Format: `note INDEX el/NOTE_INDEX NEW_NOTE [on/TIME]`

* Replaces the note at position `NOTE_INDEX` of the contact at the specified `INDEX` with `NEW_NOTE`.
* `NOTE_INDEX` refers to the position of the note as displayed (starting from 1).
* Optionally include `on/TIME` to set or update the reminder for the edited note.
* After editing, the note must not match **another** note on the same contact (same text **and** the same reminder time, if any). Otherwise the command fails and the note is left unchanged.

Examples:
* `note 1 el/1 Updated note text.` replaces the 1st note of the 1st contact.
* `note 2 el/3 Follow up call on/15 Apr` replaces the 3rd note for the 2nd contact and sets a reminder.

![edit note]({{ baseUrl }}/images/editNote.png)

### Remove a specific note

Format: `note INDEX cl/NOTE_INDEX`

* Removes the note at position `NOTE_INDEX` from the contact at the specified `INDEX`.
* `NOTE_INDEX` refers to the position of the note as displayed (starting from 1).

Example:
* `note 1 cl/2` removes the 2nd note from the 1st contact.

![remove specific note]({{ baseUrl }}/images/removeSpecificNote.png)

### Remove the first N notes

Format: `note INDEX co/LINES_TO_REMOVE`

* Removes the first `LINES_TO_REMOVE` notes from the contact at the specified `INDEX`.
* `LINES_TO_REMOVE` must be a non-negative integer.
* If `LINES_TO_REMOVE` exceeds the number of existing notes, all notes are removed.

Examples:
* `note 1 co/1` removes the first note from the 1st contact.
* `note 2 co/3` removes the first 3 notes from the 2nd contact.

![remove notes]({{ baseUrl }}/images/removeNotes.png)

### Clear all notes

Format: `note INDEX ca/`

* Removes all notes from the contact at the specified `INDEX`.

Example:
* `note 1 ca/`

![remove all notes]({{ baseUrl }}/images/removeAllNotes.png)

## Reminders

By including a `/on` prefix and a time afterwards in a `note`, users can create reminders attached to a contact, which is useful to scheduling meetings and events relating to those contacts. <br>
Contacts with a reminder will gain a special `Reminder` tag and automatically be placed towards the top of the contact list. <br>
The input format for the time is [flexible]({{ baseUrl }}/UserGuide.html#flexible-time-input).

![Reminder]({{ baseUrl }}/images/notes-reminder.png)

Users will be notified that the reminder of a contact is due within 7 days in the following ways:
- The `Reminder` tag of the contact will turn reddish
- The contact will be placed at the very top of the contact list, above other contacts without a due reminder
- A reminder window will pop-up during startup

![Due Reminder]({{ baseUrl }}/images/notes-dueReminder.png)
