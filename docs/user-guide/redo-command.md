# Redoing a command: `redo`

Reverses the effect of an [`undo` command]({{ baseUrl }}/user-guide/undo-command.html), effectively re-applying the previously undone action.

Format: `redo`

* Only applicable after an `undo` command has been executed.
* Restores the application state to the state prior to the previous `undo` command, as if said `undo` command was never executed at all.
* Displays the feedback of the redone command after successful execution.

Examples:
* `delete 1` then `undo` then `redo` re-deletes the 1st contact.
* `edit 1 n/New Name` then `undo` then `redo` re-applies the name change.

![redo command]({{ baseUrl }}/images/redoCommand.png)
