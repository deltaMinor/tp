package seedu.address.logic.parser;

import static seedu.address.logic.Messages.MESSAGE_INVALID_COMMAND_FORMAT;
import static seedu.address.logic.parser.CliSyntax.PREFIX_NAME;
import static seedu.address.logic.parser.CliSyntax.PREFIX_TAG;
import static seedu.address.logic.parser.CommandParserTestUtil.assertParseFailure;
import static seedu.address.logic.parser.CommandParserTestUtil.assertParseSuccess;

import java.util.Arrays;
import java.util.List;

import org.junit.jupiter.api.Test;

import seedu.address.logic.commands.FindCommand;
import seedu.address.model.contact.NameAndTagContainsKeywordsPredicate;

public class FindCommandParserTest {

    private FindCommandParser parser = new FindCommandParser();

    @Test
    public void parse_emptyArg_throwsParseException() {
        assertParseFailure(parser, "     ", String.format(MESSAGE_INVALID_COMMAND_FORMAT, FindCommand.MESSAGE_USAGE));
    }

    @Test
    public void parse_preamblePresent_throwsParseException() {
        assertParseFailure(parser, "Alice " + PREFIX_NAME + "Bob",
                String.format(MESSAGE_INVALID_COMMAND_FORMAT, FindCommand.MESSAGE_USAGE));
    }

    @Test
    public void parse_nameKeywordsOnly_returnsFindCommand() {
        FindCommand expectedFindCommand =
                new FindCommand(new NameAndTagContainsKeywordsPredicate(Arrays.asList("Alice", "Bob"), List.of()));
        assertParseSuccess(parser, " " + PREFIX_NAME + "Alice Bob", expectedFindCommand);
    }

    @Test
    public void parse_tagKeywordsOnly_returnsFindCommand() {
        FindCommand expectedFindCommand =
                new FindCommand(new NameAndTagContainsKeywordsPredicate(
                        List.of(), Arrays.asList("friends", "teammate")));
        assertParseSuccess(parser, " " + PREFIX_TAG + "friends " + PREFIX_TAG + "teammate", expectedFindCommand);
    }

    @Test
    public void parse_nameAndTagKeywords_returnsFindCommand() {
        FindCommand expectedFindCommand = new FindCommand(
                new NameAndTagContainsKeywordsPredicate(Arrays.asList("Alice", "Bob"), List.of("friends")));
        assertParseSuccess(parser, " " + PREFIX_NAME + "Alice Bob " + PREFIX_TAG + "friends", expectedFindCommand);
    }

}
