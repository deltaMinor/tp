package seedu.address.model.util;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.Optional;
import java.util.Set;
import java.util.UUID;
import java.util.stream.Collectors;

import seedu.address.logic.parser.TimePointParser;
import seedu.address.model.AddressBook;
import seedu.address.model.ReadOnlyAddressBook;
import seedu.address.model.contact.Address;
import seedu.address.model.contact.Contact;
import seedu.address.model.contact.Email;
import seedu.address.model.contact.LastContacted;
import seedu.address.model.contact.Name;
import seedu.address.model.contact.Note;
import seedu.address.model.contact.Phone;
import seedu.address.model.tag.RankedTag;
import seedu.address.model.tag.Tag;

/**
 * Contains utility methods for populating {@code AddressBook} with sample data.
 */
public class SampleDataUtil {
    public static final List<Note> EMPTY_NOTES = new ArrayList<>();
    public static final Set<Tag> EMPTY_TAGS = Collections.emptySet();


    public static Contact[] getSampleContacts() {
        return new Contact[] {
            new Contact(UUID.fromString("110e8400-e29b-41d4-a716-446655440000"),
                    new Name("Alex & Co."), Optional.of(new Phone("64443287")),
                    Optional.of(new Email("service@alexco.com")),
                    Optional.of(new Address("Blk 30 Geylang Street 29, #06-40")),
                    Optional.of(new LastContacted(TimePointParser.toTimePoint("12 March 2026"))),
                    List.of(new Note("CEO: @{220e8400-e29b-41d4-a716-446655440000}"),
                            new Note("Currently employing @{330e8400-e29b-41d4-a716-446655440000} and"
                                    + "@{550e8400-e29b-41d4-a716-446655440000}"),
                            new Note("Worked with @{440e8400-e29b-41d4-a716-446655440000} before")),
                    getTagSet("client")),
            new Contact(UUID.fromString("220e8400-e29b-41d4-a716-446655440000"),
                    new Name("Alex Yeoh"), Optional.of(new Phone("87438807")),
                    Optional.of(new Email("alexyeoh@example.com")),
                    Optional.empty(),
                    Optional.of(new LastContacted(TimePointParser.toTimePoint("12 March 2026"))),
                    List.of(new Note("CEO of @{110e8400-e29b-41d4-a716-446655440000}"),
                            new Note("To meet", TimePointParser.toTimePoint("15 July 2026")),
                            new Note("Planning to have a company event in 2027 January"),
                            new Note("First met in 2025 Metro Gala"),
                            new Note("Good friends with @{550e8400-e29b-41d4-a716-446655440000}")),
                    getTagSet("CEO")),
            new Contact(UUID.fromString("330e8400-e29b-41d4-a716-446655440000"),
                    new Name("Bernice Yu"), Optional.of(new Phone("99272758")),
                    Optional.empty(),
                    Optional.empty(),
                    Optional.of(new LastContacted(TimePointParser.toTimePoint("12 March 2026"))),
                    EMPTY_NOTES,
                    EMPTY_TAGS),
            new Contact(UUID.fromString("440e8400-e29b-41d4-a716-446655440000"),
                new Name("Charlotte Designs"), Optional.of(new Phone("93210283")),
                Optional.of(new Email("char.marketing@example.com")),
                Optional.of(new Address("Blk 11 Ang Mo Kio Street 74, #11-04")),
                    Optional.of(new LastContacted(TimePointParser.toTimePoint("25 Feb 2026"))),
                EMPTY_NOTES,
                getTagSet("commission:600", "marketing", "service")),
            new Contact(UUID.fromString("445e8400-e29b-41d4-a716-446655440000"),
                    new Name("Charlotte Oliverio"), Optional.of(new Phone("93210283")),
                    Optional.of(new Email("charlotte@example.com")),
                    Optional.of(new Address("Blk 11 Ang Mo Kio Street 74, #11-04")),
                    Optional.empty(),
                    List.of(new Note("To meet", TimePointParser.toTimePoint("3 Aug 2026"))),
                    getTagSet("CEO")),
            new Contact(UUID.fromString("550e8400-e29b-41d4-a716-446655440000"),
                new Name("David Li"), Optional.empty(),
                Optional.of(new Email("lidavid@example.com")),
                Optional.empty(),
                Optional.empty(),
                EMPTY_NOTES,
                getTagSet("accounting")),
            new Contact(UUID.fromString("660e8400-e29b-41d4-a716-446655440000"),
                new Name("Irfan Ibrahim"), Optional.of(new Phone("92492021")),
                Optional.of(new Email("irfan@example.com")),
                Optional.of(new Address("Blk 47 Tampines Street 20, #17-35")),
                Optional.empty(),
                List.of(new Note("Is currently self-employed"),
                        new Note("Knows @{880e8400-e29b-41d4-a716-446655440000}")),
                getTagSet("commission:250", "marketing", "service", "contractor")),
            new Contact(UUID.fromString("770e8400-e29b-41d4-a716-446655440000"),
                    new Name("Service4U"), Optional.of(new Phone("92624417")),
                    Optional.of(new Email("royb@example.com")),
                    Optional.of(new Address("Blk 45 Aljunied Street 85, #11-31")),
                    Optional.empty(),
                    List.of(new Note("CEO: @{880e8400-e29b-41d4-a716-446655440000}")),
                    getTagSet("commission:400", "service")),
            new Contact(UUID.fromString("880e8400-e29b-41d4-a716-446655440000"),
                    new Name("Roy Balakrishnan"), Optional.of(new Phone("92624417")),
                    Optional.of(new Email("royb@example.com")),
                    Optional.of(new Address("Blk 20 Aljunied Street 81, #19-17")),
                    Optional.empty(),
                    EMPTY_NOTES,
                    getTagSet("CEO"))
        };
    }

    public static ReadOnlyAddressBook getSampleAddressBook() {
        AddressBook sampleAb = new AddressBook();
        for (Contact sampleContact : getSampleContacts()) {
            sampleAb.addContact(sampleContact);
        }
        return sampleAb;
    }

    /**
     * Returns a tag set containing the list of strings given.
     */
    public static Set<Tag> getTagSet(String... strings) {
        return Arrays.stream(strings)
                .map((String s) -> s.contains(":")
                    ? new RankedTag(s.split(":")[0], s.split(":")[1])
                    : new Tag(s))
                .collect(Collectors.toSet());
    }

    /**
     * Returns a notes list containing the list of strings given.
     */
    public static List<Note> getNoteList(String... strings) {
        return Arrays.stream(strings)
                .map(Note::fromJsonString)
                .collect(Collectors.toList());
    }
}
