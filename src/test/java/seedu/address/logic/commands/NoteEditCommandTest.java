package seedu.address.logic.commands;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static seedu.address.logic.commands.CommandTestUtil.assertCommandFailure;
import static seedu.address.logic.commands.CommandTestUtil.assertCommandSuccess;
import static seedu.address.testutil.TypicalContacts.getTypicalAddressBook;
import static seedu.address.testutil.TypicalIndexes.INDEX_FIRST_CONTACT;
import static seedu.address.testutil.TypicalIndexes.INDEX_FIRST_NOTE;
import static seedu.address.testutil.TypicalIndexes.INDEX_SECOND_CONTACT;
import static seedu.address.testutil.TypicalIndexes.INDEX_SECOND_NOTE;

import java.util.List;

import org.junit.jupiter.api.Test;

import seedu.address.commons.core.index.Index;
import seedu.address.logic.Messages;
import seedu.address.model.AddressBook;
import seedu.address.model.Model;
import seedu.address.model.ModelManager;
import seedu.address.model.UserPrefs;
import seedu.address.model.contact.Contact;
import seedu.address.model.contact.Note;

public class NoteEditCommandTest {
    private static final Note NOTE_A = new Note("Lorem ipsum");
    private static final Note NOTE_B = new Note("Dolor sit amet");
    private static final Note NEW_NOTE = new Note("Updated note text");
    private static final List<Note> NOTES = List.of(NOTE_A, NOTE_B);

    private Model model = new ModelManager(getTypicalAddressBook(), new UserPrefs());

    @Test
    public void execute_editFirstNote_success() {
        NoteEditCommand command = new NoteEditCommand(INDEX_FIRST_CONTACT, INDEX_FIRST_NOTE, NEW_NOTE);

        Contact contactToEdit = model.getDisplayedContactList().get(0);
        Contact contactWithNotes = new Contact(contactToEdit.getName(), contactToEdit.getPhone(),
                contactToEdit.getEmail(), contactToEdit.getAddress(), contactToEdit.getLastContacted(),
                NOTES, contactToEdit.getTags());

        Contact expectedContact = new Contact(contactToEdit.getId(), contactToEdit.getName(),
                contactToEdit.getPhone(), contactToEdit.getEmail(), contactToEdit.getAddress(),
                contactToEdit.getLastContacted(), List.of(NEW_NOTE, NOTE_B), contactToEdit.getTags());
        String expectedMessage = Messages.formatNoteOutput(NoteEditCommand.MESSAGE_EDIT_NOTE_SUCCESS, expectedContact);
        CommandResult expectedCommandResult =
                new ScrollToIndexCommandResult(expectedMessage, INDEX_FIRST_CONTACT);

        Model testModel = new ModelManager(new AddressBook(model.getAddressBook()), new UserPrefs());
        testModel.setContact(model.getDisplayedContactList().get(0), contactWithNotes);
        testModel.resetDisplayedContactList();

        Model expectedModel = new ModelManager(new AddressBook(model.getAddressBook()), new UserPrefs());
        expectedModel.setContact(model.getDisplayedContactList().get(0), expectedContact);
        expectedModel.resetDisplayedContactList();

        assertCommandSuccess(command, testModel, expectedCommandResult, expectedModel);
    }

    @Test
    public void execute_editSecondNote_success() {
        NoteEditCommand command = new NoteEditCommand(INDEX_FIRST_CONTACT, INDEX_SECOND_NOTE, NEW_NOTE);

        Contact contactToEdit = model.getDisplayedContactList().get(0);
        Contact contactWithNotes = new Contact(contactToEdit.getName(), contactToEdit.getPhone(),
                contactToEdit.getEmail(), contactToEdit.getAddress(), contactToEdit.getLastContacted(),
                NOTES, contactToEdit.getTags());

        Contact expectedContact = new Contact(contactToEdit.getId(), contactToEdit.getName(),
                contactToEdit.getPhone(), contactToEdit.getEmail(), contactToEdit.getAddress(),
                contactToEdit.getLastContacted(), List.of(NOTE_A, NEW_NOTE), contactToEdit.getTags());
        String expectedMessage = Messages.formatNoteOutput(NoteEditCommand.MESSAGE_EDIT_NOTE_SUCCESS, expectedContact);
        CommandResult expectedCommandResult =
                new ScrollToIndexCommandResult(expectedMessage, INDEX_FIRST_CONTACT);

        Model testModel = new ModelManager(new AddressBook(model.getAddressBook()), new UserPrefs());
        testModel.setContact(model.getDisplayedContactList().get(0), contactWithNotes);
        testModel.resetDisplayedContactList();

        Model expectedModel = new ModelManager(new AddressBook(model.getAddressBook()), new UserPrefs());
        expectedModel.setContact(model.getDisplayedContactList().get(0), expectedContact);
        expectedModel.resetDisplayedContactList();

        assertCommandSuccess(command, testModel, expectedCommandResult, expectedModel);
    }

    @Test
    public void execute_invalidContactIndex_failure() {
        Index outOfBoundIndex = Index.fromOneBased(model.getDisplayedContactList().size() + 1);
        NoteEditCommand command = new NoteEditCommand(outOfBoundIndex, INDEX_FIRST_NOTE, NEW_NOTE);

        assertCommandFailure(command, model,
                Messages.getIndexOutOfRangeMessage(model.getDisplayedContactList().size()));
    }

    @Test
    public void execute_invalidNoteIndex_failure() {
        Index outOfBoundNoteIndex = Index.fromOneBased(
                model.getDisplayedContactList().get(0).getNotes().size() + 1);
        NoteEditCommand command = new NoteEditCommand(INDEX_FIRST_CONTACT, outOfBoundNoteIndex, NEW_NOTE);

        assertCommandFailure(command, model, NoteEditCommand.MESSAGE_INVALID_NOTE_INDEX);
    }

    @Test
    public void execute_editToDuplicateNote_failure() {
        Contact contactToEdit = model.getDisplayedContactList().get(0);
        Contact contactWithNotes = new Contact(contactToEdit.getName(), contactToEdit.getPhone(),
                contactToEdit.getEmail(), contactToEdit.getAddress(), contactToEdit.getLastContacted(),
                NOTES, contactToEdit.getTags());

        Model testModel = new ModelManager(new AddressBook(model.getAddressBook()), new UserPrefs());
        testModel.setContact(model.getDisplayedContactList().get(0), contactWithNotes);
        testModel.resetDisplayedContactList();

        NoteEditCommand command = new NoteEditCommand(INDEX_FIRST_CONTACT, INDEX_SECOND_NOTE, NOTE_A);
        assertCommandFailure(command, testModel, NoteCommand.MESSAGE_DUPLICATE_NOTE);
    }

    @Test
    public void equals() {
        final NoteEditCommand standardCommand = new NoteEditCommand(INDEX_FIRST_CONTACT, INDEX_FIRST_NOTE, NEW_NOTE);

        // same object -> returns true
        assertTrue(standardCommand.equals(standardCommand));

        // null -> returns false
        assertFalse(standardCommand.equals(null));

        // different types -> returns false
        assertFalse(standardCommand.equals(new ClearCommand()));

        // different contact index -> returns false
        assertFalse(standardCommand.equals(new NoteEditCommand(INDEX_SECOND_CONTACT, INDEX_FIRST_NOTE, NEW_NOTE)));

        // different note index -> returns false
        assertFalse(standardCommand.equals(new NoteEditCommand(INDEX_FIRST_CONTACT, INDEX_SECOND_NOTE, NEW_NOTE)));

        // different note -> returns false
        assertFalse(standardCommand.equals(
                new NoteEditCommand(INDEX_FIRST_CONTACT, INDEX_FIRST_NOTE, new Note("Different"))));
    }
}
