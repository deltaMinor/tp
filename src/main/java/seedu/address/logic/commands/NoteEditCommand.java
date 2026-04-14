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
 * Edits an existing note of a contact in the address book.
 */
public class NoteEditCommand extends NoteCommand {

    public static final String MESSAGE_EDIT_NOTE_SUCCESS = "Edited note";
    public static final String MESSAGE_INVALID_NOTE_INDEX = "The note index provided is invalid";

    private final Index contactIndex;
    private final Index noteIndex;
    private final Note newNote;

    /**
     * Creates a NoteEditCommand to replace a specific note of a contact.
     *
     * @param contactIndex Index of the contact in the displayed contact list.
     * @param noteIndex    Index of the note to edit (1-based).
     * @param newNote      The new note to replace the existing one.
     */
    public NoteEditCommand(Index contactIndex, Index noteIndex, Note newNote) {
        requireAllNonNull(contactIndex, noteIndex, newNote);

        this.contactIndex = contactIndex;
        this.noteIndex = noteIndex;
        this.newNote = newNote;
    }

    @Override
    public CommandResult execute(Model model) throws CommandException {
        List<Contact> lastShownList = model.getDisplayedContactList();

        if (contactIndex.getZeroBased() >= lastShownList.size()) {
            throw new CommandException(Messages.getIndexOutOfRangeMessage(lastShownList.size()));
        }

        Contact contactToEdit = lastShownList.get(contactIndex.getZeroBased());

        if (noteIndex.getZeroBased() >= contactToEdit.getNotes().size()) {
            throw new CommandException(MESSAGE_INVALID_NOTE_INDEX);
        }

        // Resolve @INDEX references in note text to @{UUID}
        Note resolvedNote = resolveContactReferences(newNote, lastShownList);

        int noteZeroBased = noteIndex.getZeroBased();
        List<Note> existingNotes = contactToEdit.getNotes();
        for (int i = 0; i < existingNotes.size(); i++) {
            if (i != noteZeroBased && existingNotes.get(i).equals(resolvedNote)) {
                throw new CommandException(NoteCommand.MESSAGE_DUPLICATE_NOTE);
            }
        }

        List<Note> newNotes = new ArrayList<>(contactToEdit.getNotes());
        newNotes.set(noteZeroBased, resolvedNote);

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

    private String generateSuccessMessage(Contact contactToEdit, Model model) {
        return Messages.formatNoteOutput(MESSAGE_EDIT_NOTE_SUCCESS, contactToEdit,
                model.getDisplayedContactList(), model.getAddressBook().getContactList());
    }

    @Override
    public boolean equals(Object other) {
        if (other == this) {
            return true;
        }

        if (!(other instanceof NoteEditCommand e)) {
            return false;
        }

        return contactIndex.equals(e.contactIndex)
                && noteIndex.equals(e.noteIndex)
                && newNote.equals(e.newNote);
    }
}
