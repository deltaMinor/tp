package seedu.address.model.contact;

import static java.util.Objects.requireNonNull;

import java.util.List;
import java.util.function.Predicate;

import seedu.address.commons.util.ToStringBuilder;

/**
 * Tests that a {@code Contact} matches name and/or tag keywords.
 * Name matching and tag matching are both case-insensitive.
 */
public class NameAndTagContainsKeywordsPredicate implements Predicate<Contact> {
    private final List<String> nameKeywords;
    private final List<String> tagKeywords;

    private final NameContainsKeywordsPredicate namePredicate;
    private final TagContainsKeywordsPredicate tagPredicate;

    /**
     * Creates a predicate that filters by name keywords and tag keywords.
     *
     * @param nameKeywords Keywords for matching contact names.
     * @param tagKeywords Keywords for matching contact tags.
     */
    public NameAndTagContainsKeywordsPredicate(List<String> nameKeywords, List<String> tagKeywords) {
        requireNonNull(nameKeywords);
        requireNonNull(tagKeywords);
        this.nameKeywords = List.copyOf(nameKeywords);
        this.tagKeywords = List.copyOf(tagKeywords);
        this.namePredicate = new NameContainsKeywordsPredicate(this.nameKeywords);
        this.tagPredicate = new TagContainsKeywordsPredicate(this.tagKeywords);
    }

    @Override
    public boolean test(Contact contact) {
        boolean hasNameCriteria = !nameKeywords.isEmpty();
        boolean hasTagCriteria = !tagKeywords.isEmpty();

        if (hasNameCriteria && hasTagCriteria) {
            return namePredicate.test(contact) && tagPredicate.test(contact);
        }

        if (hasNameCriteria) {
            return namePredicate.test(contact);
        }

        if (hasTagCriteria) {
            return tagPredicate.test(contact);
        }

        return false;
    }

    @Override
    public boolean equals(Object other) {
        if (other == this) {
            return true;
        }

        if (!(other instanceof NameAndTagContainsKeywordsPredicate)) {
            return false;
        }

        NameAndTagContainsKeywordsPredicate otherPredicate = (NameAndTagContainsKeywordsPredicate) other;
        return nameKeywords.equals(otherPredicate.nameKeywords)
                && tagKeywords.equals(otherPredicate.tagKeywords);
    }

    @Override
    public String toString() {
        return new ToStringBuilder(this)
                .add("nameKeywords", nameKeywords)
                .add("tagKeywords", tagKeywords)
                .toString();
    }
}
