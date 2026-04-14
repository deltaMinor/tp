# Undoing a command: `undo`

Reverts the last executed command that modified data.

Format: `undo`

* Only the following commands can be undone:
  * Commands that modify contact list data: `add`, `edit`, `delete`, `note`, `clear`
  * Commands that change the filter/sort patterns: `list`, `find`, `sort`
  * Commands that modify application settings: `file`, `theme`
* Commands that do not fall in the above categories (`help`, `view`, `close view`, `undo`, `redo`, `exit`) are ignored by undo.
* Displays the feedback of the undone command after execution.

Examples:
* `delete 1` followed by `undo` restores the deleted contact.
* `edit 1 n/New Name` followed by `undo` reverts the name change.

![undo command]({{ baseUrl }}/images/undoCommand.png)
