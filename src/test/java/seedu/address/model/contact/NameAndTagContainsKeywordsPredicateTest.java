package seedu.address.model.contact;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.util.Arrays;
import java.util.Collections;
import java.util.List;

import org.junit.jupiter.api.Test;

import seedu.address.testutil.ContactBuilder;

public class NameAndTagContainsKeywordsPredicateTest {

    @Test
    public void equals() {
        NameAndTagContainsKeywordsPredicate firstPredicate =
                new NameAndTagContainsKeywordsPredicate(Collections.singletonList("Alice"), List.of("friends"));
        NameAndTagContainsKeywordsPredicate secondPredicate =
                new NameAndTagContainsKeywordsPredicate(Collections.singletonList("Bob"), List.of("family"));

        assertTrue(firstPredicate.equals(firstPredicate));

        NameAndTagContainsKeywordsPredicate firstPredicateCopy =
                new NameAndTagContainsKeywordsPredicate(Collections.singletonList("Alice"), List.of("friends"));
        assertTrue(firstPredicate.equals(firstPredicateCopy));

        assertFalse(firstPredicate.equals(1));
        assertFalse(firstPredicate.equals(null));
        assertFalse(firstPredicate.equals(secondPredicate));
    }

    @Test
    public void test_nameAndTagCriteria_returnsTrueOnlyWhenBothMatch() {
        NameAndTagContainsKeywordsPredicate predicate =
                new NameAndTagContainsKeywordsPredicate(List.of("Alice"), List.of("friends"));
        assertTrue(predicate.test(new ContactBuilder().withName("Alice Bob").withTags("friends").build()));
        assertFalse(predicate.test(new ContactBuilder().withName("Alice Bob").withTags("family").build()));
        assertFalse(predicate.test(new ContactBuilder().withName("Charlie").withTags("friends").build()));
    }

    @Test
    public void test_nameOnlyCriteria_returnsNameMatch() {
        NameAndTagContainsKeywordsPredicate predicate =
                new NameAndTagContainsKeywordsPredicate(Arrays.asList("Alice", "Bob"), Collections.emptyList());
        assertTrue(predicate.test(new ContactBuilder().withName("Bob Carol").build()));
        assertFalse(predicate.test(new ContactBuilder().withName("Daniel").build()));
    }

    @Test
    public void test_tagOnlyCriteria_returnsTagMatch() {
        NameAndTagContainsKeywordsPredicate predicate =
                new NameAndTagContainsKeywordsPredicate(Collections.emptyList(), Arrays.asList("friends", "teammates"));
        assertTrue(predicate.test(new ContactBuilder().withTags("teammates").build()));
        assertFalse(predicate.test(new ContactBuilder().withTags("family").build()));
    }

    @Test
    public void toStringMethod() {
        NameAndTagContainsKeywordsPredicate predicate =
                new NameAndTagContainsKeywordsPredicate(List.of("Alice"), List.of("friends"));
        String expected = NameAndTagContainsKeywordsPredicate.class.getCanonicalName()
                + "{nameKeywords=[Alice], tagKeywords=[friends]}";
        assertEquals(expected, predicate.toString());
    }
}
