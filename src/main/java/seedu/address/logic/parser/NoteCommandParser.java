package seedu.address.logic.parser;

import static java.util.Objects.requireNonNull;
import static seedu.address.logic.Messages.MESSAGE_INVALID_COMMAND_FORMAT;
import static seedu.address.logic.parser.CliSyntax.PREFIX_CLEAR_ALL;
import static seedu.address.logic.parser.CliSyntax.PREFIX_CLEAR_LINE;
import static seedu.address.logic.parser.CliSyntax.PREFIX_CLEAR_OLDEST;
import static seedu.address.logic.parser.CliSyntax.PREFIX_EDIT_LINE;
import static seedu.address.logic.parser.CliSyntax.PREFIX_ON;

import seedu.address.commons.core.index.Index;
import seedu.address.commons.exceptions.IllegalValueException;
import seedu.address.logic.Messages;
import seedu.address.logic.commands.NoteAddCommand;
import seedu.address.logic.commands.NoteClearAllCommand;
import seedu.address.logic.commands.NoteClearCommand;
import seedu.address.logic.commands.NoteCommand;
import seedu.address.logic.commands.NoteEditCommand;
import seedu.address.logic.commands.NoteRemoveCommand;
import seedu.address.logic.parser.exceptions.ParseException;
import seedu.address.model.contact.Note;

/**
 * Parses input arguments and creates a new {@code NoteCommand} object
 */
public class NoteCommandParser implements Parser<NoteCommand> {
    /**
     * Parses the given {@code String} of arguments in the context of the {@code NotesCommand}
     * and returns a {@code NotesCommand} object for execution.
     *
     * @throws ParseException if the user input does not conform the expected format
     */
    public NoteCommand parse(String args) throws ParseException {
        requireNonNull(args);
        ArgumentMultimap argMultimap =
                ArgumentTokenizer.tokenize(args, PREFIX_ON, PREFIX_CLEAR_OLDEST, PREFIX_CLEAR_ALL,
                        PREFIX_CLEAR_LINE, PREFIX_EDIT_LINE);

        boolean isClearOldestPresent = argMultimap.getValue(PREFIX_CLEAR_OLDEST).isPresent();
        boolean isClearAllPrefixPresent = argMultimap.getValue(PREFIX_CLEAR_ALL).isPresent();
        boolean isClearLinePresent = argMultimap.getValue(PREFIX_CLEAR_LINE).isPresent();
        boolean isEditLinePresent = argMultimap.getValue(PREFIX_EDIT_LINE).isPresent();
        boolean isPreamblePresent = !argMultimap.getPreamble().isEmpty();

        if (!isPreamblePresent) {
            throw new ParseException(Messages.getCommandErrorWithUsage(
                    Messages.MESSAGE_MISSING_INDEX, NoteAddCommand.MESSAGE_USAGE));
        }

        if (argMultimap.getArguments().size() > 1) {
            // Allow edit + on/ combination (e.g. note 1 el/1 New text on/March 11)
            boolean isEditWithOn = isEditLinePresent && argMultimap.getValue(PREFIX_ON).isPresent()
                    && argMultimap.getArguments().size() == 2;
            if (!isEditWithOn) {
                throw new ParseException(String.format(MESSAGE_INVALID_COMMAND_FORMAT, NoteAddCommand.MESSAGE_USAGE));
            }
        }

        if (isClearOldestPresent) {
            return parseNoteClearCommand(argMultimap);
        }

        if (isClearAllPrefixPresent) {
            return parseNoteClearAllCommand(argMultimap);
        }

        if (isClearLinePresent) {
            return parseNoteRemoveCommand(argMultimap);
        }

        if (isEditLinePresent) {
            return parseNoteEditCommand(argMultimap);
        }

        return parseNoteAddCommand(argMultimap);
    }

    /**
     * Parses the given {@code ArgumentMultimap} in the context of the {@code NoteClearCommand}
     * and returns a {@code NoteClearCommand} object for execution.
     *
     * @param argMultimap The ArgumentMultimap containing an {@code Index} preamble
     *                    and a prefixed number of lines to remove.
     * @return A {@code NoteClearCommand} object with the specified index and number of lines to remove.
     */
    private NoteClearCommand parseNoteClearCommand(ArgumentMultimap argMultimap) throws ParseException {
        Index index = parseIndex(argMultimap.getPreamble());
        int numLines = parseNumLines(argMultimap.getValue(PREFIX_CLEAR_OLDEST).get());
        return new NoteClearCommand(index, numLines);
    }

    /**
     * Parses the given {@code ArgumentMultimap} in the context of the {@code NoteClearCommand}
     * and returns a {@code NoteClearCommand} object for execution.
     *
     * @param argMultimap The ArgumentMultimap containing an {@code Index} preamble.
     * @return A {@code NoteClearCommand} object with the specified index.
     */
    private NoteClearAllCommand parseNoteClearAllCommand(ArgumentMultimap argMultimap) throws ParseException {
        if (!argMultimap.getValue(PREFIX_CLEAR_ALL).get().isEmpty()) {
            throw new ParseException(String.format(MESSAGE_INVALID_COMMAND_FORMAT, NoteAddCommand.MESSAGE_USAGE));
        }
        Index index = parseIndex(argMultimap.getPreamble());
        return new NoteClearAllCommand(index);
    }

    /**
     * Parses the given {@code ArgumentMultimap} in the context of the {@code NoteRemoveCommand}
     * and returns a {@code NoteRemoveCommand} object for execution.
     *
     * @param argMultimap The ArgumentMultimap containing an {@code Index} preamble
     *                    and a prefixed note {@code Index} to remove.
     * @return A {@code NoteRemoveCommand} object with the specified index and note index to remove.
     */
    private NoteRemoveCommand parseNoteRemoveCommand(ArgumentMultimap argMultimap) throws ParseException {
        Index contactIndex = parseIndex(argMultimap.getPreamble());
        Index noteIndex = parseIndex(argMultimap.getValue(PREFIX_CLEAR_LINE).get());
        return new NoteRemoveCommand(contactIndex, noteIndex);
    }

    /**
     * Parses the given {@code ArgumentMultimap} in the context of the {@code NoteEditCommand}
     * and returns a {@code NoteEditCommand} object for execution.
     *
     * @param argMultimap The ArgumentMultimap containing an {@code Index} preamble,
     *                    a prefixed note {@code Index}, and the new note text.
     * @return A {@code NoteEditCommand} object with the specified contact index, note index, and new note.
     */
    private NoteEditCommand parseNoteEditCommand(ArgumentMultimap argMultimap) throws ParseException {
        Index contactIndex = parseIndex(argMultimap.getPreamble());
        String editValue = argMultimap.getValue(PREFIX_EDIT_LINE).get().trim();
        String[] editArgs = editValue.split(" ", 2);

        if (editArgs.length < 2 || editArgs[1].isBlank()) {
            throw new ParseException(Messages.getCommandErrorWithUsage(
                    Messages.MESSAGE_MISSING_KEYWORD, NoteCommand.MESSAGE_USAGE),
                    new ArrayIndexOutOfBoundsException());
        }

        Index noteIndex = parseIndex(editArgs[0]);
        String newNoteText = editArgs[1];

        if (argMultimap.getValue(PREFIX_ON).isPresent()) {
            return new NoteEditCommand(contactIndex, noteIndex,
                    new Note(newNoteText, TimePointParser.toTimePoint(argMultimap.getValue(PREFIX_ON).get())));
        }

        return new NoteEditCommand(contactIndex, noteIndex, new Note(newNoteText));
    }

    /**
     * Parses the given {@code String} in the context of the {@code NoteAddCommand}
     * and returns a {@code NoteAddCommand} object for execution.
     *
     * @param argMultimap The ArgumentMultimap containing an {@code Index} preamble.
     * @return A {@code NoteAddCommand} object with the specified index, a new note, and potentially a time.
     */
    private NoteAddCommand parseNoteAddCommand(ArgumentMultimap argMultimap) throws ParseException {
        String[] noteArgs = argMultimap.getPreamble().trim().split(" ", 2);

        if (noteArgs.length < 2) {
            throw new ParseException(Messages.getCommandErrorWithUsage(
                    Messages.MESSAGE_MISSING_KEYWORD, NoteAddCommand.MESSAGE_USAGE),
                new ArrayIndexOutOfBoundsException());
        }

        Index index = parseIndex(noteArgs[0]);

        if (argMultimap.getValue(PREFIX_ON).isPresent()) {
            return new NoteAddCommand(
                    index,
                    new Note(noteArgs[1], TimePointParser.toTimePoint(argMultimap.getValue(PREFIX_ON).get())));
        }

        return new NoteAddCommand(index, new Note(noteArgs[1]));
    }

    /**
     * Parses a {@code String} into an {@code Index}.
     *
     * @param index A {@code String} index.
     * @return A parsed {@code Index}.
     */
    private Index parseIndex(String index) throws ParseException {
        try {
            return ParserUtil.parseIndex(index);
        } catch (IllegalValueException ive) {
            throw new ParseException(Messages.getCommandErrorWithUsage(
                    ive.getMessage(), NoteAddCommand.MESSAGE_USAGE), ive);
        }
    }

    /**
     * Parses a {@code String} into an {@code int} number of lines.
     *
     * @param numLines A {@code String} number of lines.
     * @return A parsed {@code int} number of lines.
     */
    private int parseNumLines(String numLines) throws ParseException {
        try {
            int result = Integer.parseInt(numLines);
            if (result <= 0) {
                throw new ParseException(
                        String.format(MESSAGE_INVALID_COMMAND_FORMAT, NoteAddCommand.MESSAGE_USAGE));
            }
            return result;
        } catch (NumberFormatException nfe) {
            throw new ParseException(String.format(MESSAGE_INVALID_COMMAND_FORMAT, NoteAddCommand.MESSAGE_USAGE), nfe);
        }
    }
}
