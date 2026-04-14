package seedu.address.logic.commands;

import static seedu.address.logic.parser.CliSyntax.PREFIX_CLEAR_ALL;
import static seedu.address.logic.parser.CliSyntax.PREFIX_CLEAR_LINE;
import static seedu.address.logic.parser.CliSyntax.PREFIX_CLEAR_OLDEST;
import static seedu.address.logic.parser.CliSyntax.PREFIX_EDIT_LINE;

/**
 * Parent class for all note-related commands.
 */
public abstract class NoteCommand extends Command {

    public static final String COMMAND_WORD = "note";

    public static final String MESSAGE_DUPLICATE_NOTE = "This note already exists for this contact.";

    public static final String MESSAGE_USAGE = COMMAND_WORD + ": Edits the notes of the contact identified "
            + "by the index number used in the last contact listing. "
            + "New notes will be stacked underneath existing ones.\n"
            + "Format [Add]: " + COMMAND_WORD + " INDEX NOTE\n"
            + "Format [Edit line]: " + COMMAND_WORD + " INDEX " + PREFIX_EDIT_LINE + "NOTE_INDEX NEW_NOTE\n"
            + "Format [Clear line]: " + COMMAND_WORD + " INDEX " + PREFIX_CLEAR_LINE + "NOTE_INDEX\n"
            + "Format [Clear oldest]: " + COMMAND_WORD + " INDEX " + PREFIX_CLEAR_OLDEST + "COUNT\n"
            + "Format [Clear all]: " + COMMAND_WORD + " INDEX " + PREFIX_CLEAR_ALL + "\n"
            + "Parameters:\n"
            + "- INDEX (must be a positive integer)\n"
            + "- NOTE (should be a non-empty string)\n"
            + "- NOTE_INDEX (must be a positive integer)\n"
            + "- COUNT (must be a non-negative integer)\n"
            + "Example [Add]: " + COMMAND_WORD + " 1 " + "Likes to swim.\n"
            + "Example [Edit line]: " + COMMAND_WORD + " 1 " + PREFIX_EDIT_LINE + "1 Updated note text.\n"
            + "Example [Clear line]: " + COMMAND_WORD + " 1 " + PREFIX_CLEAR_LINE + "2\n"
            + "Example [Clear oldest]: " + COMMAND_WORD + " 1 " + PREFIX_CLEAR_OLDEST + "1\n"
            + "Example [Clear all]: " + COMMAND_WORD + " 1 " + PREFIX_CLEAR_ALL + "\n";
}
