package seedu.address.ui;

import java.util.Comparator;

import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.control.ScrollPane;
import javafx.scene.layout.FlowPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Region;
import javafx.scene.layout.VBox;
import seedu.address.commons.core.timepoint.DateTimeUtil;
import seedu.address.model.contact.Contact;
import seedu.address.model.tag.RankedTag;

/**
 * A UI component that displays information of a {@code Contact}.
 */
public class ContactCard extends UiPart<Region> {

    private static final String FXML = "ContactListCard.fxml";

    /**
     * Note: Certain keywords such as "location" and "resources" are reserved keywords in JavaFX.
     * As a consequence, UI elements' variable names cannot be set to such keywords
     * or an exception will be thrown by JavaFX during runtime.
     *
     * @see <a href="https://github.com/se-edu/addressbook-level4/issues/336">The issue on AddressBook level 4</a>
     */

    public final Contact contact;

    @FXML
    private HBox cardPane;
    @FXML
    private Label name;
    @FXML
    private FlowPane info;
    @FXML
    private Label id;
    @FXML
    private Label phone;
    @FXML
    private Label address;
    @FXML
    private Label email;
    @FXML
    private Label lastUpdated;
    @FXML
    private VBox notesContainer;
    @FXML
    private ScrollPane notesScrollPane;
    @FXML
    private FlowPane tags;

    /**
     * Creates a {@code ContactCode} with the given {@code Contact} and index to display.
     */
    public ContactCard(Contact contact, int displayedIndex, ObservableList<Contact> allContacts) {
        super(FXML);
        this.contact = contact;
        id.setText(displayedIndex + ". ");
        name.setText(contact.getName().fullName);
        contact.getPhone().ifPresentOrElse(phone -> {
            this.phone.setText(phone.value);
            UiUtil.show(this.phone);
        }, () -> {
            this.phone.setText("");
            UiUtil.hide(this.phone);
        });
        contact.getAddress().ifPresentOrElse(address -> {
            this.address.setText(address.value);
            UiUtil.show(this.address);
        }, () -> {
            this.address.setText("");
            UiUtil.hide(this.address);
        });
        contact.getEmail().ifPresentOrElse(email -> {
            this.email.setText(email.value);
            UiUtil.show(this.email);
        }, () -> {
            this.email.setText("");
            UiUtil.hide(this.email);
        });
        this.lastUpdated.setText("Last Updated: " + DateTimeUtil.toDisplayString(contact.getLastUpdated().value));
        UiUtil.show(this.lastUpdated);
        if (!(contact.getNotes().isEmpty())) {
            NotesTextFlow notes = new NotesTextFlow(contact.getNotes(), allContacts);
            notesScrollPane.setContent(notes);
        } else {
            UiUtil.hide(notesContainer);
        }

        // Set up info flow pane
        if (!(contact.getReminders().isEmpty() && contact.getLastContacted().isEmpty())) {
            contact.getLastContacted().ifPresent(lc -> {
                Label lastContactedLabel = new Label("Last Contacted: " + lc);
                info.getChildren().add(lastContactedLabel);
            });
            if (!contact.getReminders().isEmpty()) {
                Label reminderLabel = new Label("Reminder");
                if (contact.hasDueReminders()) {
                    reminderLabel.getStyleClass().add("warning-label");
                }
                info.getChildren().add(reminderLabel);
            }
        } else {
            UiUtil.hide(info);
        }

        // Set up tags flow pane
        if (!contact.getTags().isEmpty()) {
            contact.getTags().stream()
                    .sorted(Comparator.comparing(tag -> tag.name))
                    .forEach(tag -> tags.getChildren().add(
                        tag instanceof RankedTag
                            ? new RankedTagLabel((RankedTag) tag)
                            : new Label(tag.name)));
        } else {
            UiUtil.hide(tags);
        }
    }
}
