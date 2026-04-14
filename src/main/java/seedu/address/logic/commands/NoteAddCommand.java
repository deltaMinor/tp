package seedu.address.logic.commands;

import static seedu.address.commons.util.CollectionUtil.requireAllNonNull;

import java.util.ArrayList;
import java.util.List;
import java.util.regex.Matcher;

import seedu.address.commons.core.index.Index;
import seedu.address.logic.Messages;
import seedu.address.logic.commands.exceptions.CommandException;
import seedu.address.model.Model;
import seedu.address.model.contact.Contact;
import seedu.address.model.contact.Note;

/**
 * Changes the notes of an existing contact in the address book.
 */
public class NoteAddCommand extends NoteCommand {

    public static final String MESSAGE_ADD_NOTES_SUCCESS = "Added note";

    private final Index index;
    private final Note note;

    /**
     * @param index of the contact in the displayed contact list to edit the notes
     * @param note of the contact to be updated to
     */
    public NoteAddCommand(Index index, Note note) {
        requireAllNonNull(index, note);

        this.index = index;
        this.note = note;
    }
    @Override
    public CommandResult execute(Model model) throws CommandException {
        List<Contact> lastShownList = model.getDisplayedContactList();

        if (index.getZeroBased() >= lastShownList.size()) {
            throw new CommandException(Messages.getIndexOutOfRangeMessage(lastShownList.size()));
        }

        // Resolve @INDEX references in note text to @{UUID}
        Note resolvedNote = resolveContactReferences(note, lastShownList);

        Contact contactToEdit = lastShownList.get(index.getZeroBased());
        if (contactToEdit.getNotes().stream().anyMatch(n -> n.equals(resolvedNote))) {
            throw new CommandException(NoteCommand.MESSAGE_DUPLICATE_NOTE);
        }
        List<Note> newNotes = new ArrayList<>(contactToEdit.getNotes());
        newNotes.add(resolvedNote);

        Contact editedContact = new Contact(contactToEdit.getId(), contactToEdit.getName(),
                contactToEdit.getPhone(), contactToEdit.getEmail(),
                contactToEdit.getAddress(), contactToEdit.getLastContacted(),
                newNotes, contactToEdit.getTags());

        model.setContact(contactToEdit, editedContact);
        model.resetDisplayedContactList();

        String feedback = generateSuccessMessage(editedContact, model);
        model.saveSnapshot(feedback);
        Index editedContactIndex = model.getIndexOf(editedContact);
        return new ScrollToIndexCommandResult(feedback, editedContactIndex);
    }

    /**
     * Resolves {@code @INDEX} patterns in the note text to {@code @{UUID}} references
     * using the currently displayed contact list.
     */
    private Note resolveContactReferences(Note note, List<Contact> contactList) {
        Matcher matcher = Note.CONTACT_INDEX_PATTERN.matcher(note.value);
        StringBuffer sb = new StringBuffer();
        while (matcher.find()) {
            int oneBasedIndex = Integer.parseInt(matcher.group(1));
            if (oneBasedIndex >= 1 && oneBasedIndex <= contactList.size()) {
                Contact referenced = contactList.get(oneBasedIndex - 1);
                matcher.appendReplacement(sb, "@{" + referenced.getId().toString() + "}");
            }
        }
        matcher.appendTail(sb);
        return new Note(sb.toString(), note.timePoint.orElse(null));
    }

    /**
     * Generates a command execution success message based on whether the notes are added to or removed from
     * {@code contactToEdit}.
     */
    private String generateSuccessMessage(Contact contactToEdit, Model model) {
        return Messages.formatNoteOutput(MESSAGE_ADD_NOTES_SUCCESS, contactToEdit,
                model.getDisplayedContactList(), model.getAddressBook().getContactList());
    }

    @Override
    public boolean equals(Object other) {
        // short circuit if same object
        if (other == this) {
            return true;
        }

        // instanceof handles nulls
        if (!(other instanceof NoteAddCommand)) {
            return false;
        }

        // state check
        NoteAddCommand e = (NoteAddCommand) other;
        return index.equals(e.index)
                && note.equals(e.note);
    }
}
