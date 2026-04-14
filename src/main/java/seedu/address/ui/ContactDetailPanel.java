package seedu.address.ui;

import java.util.Comparator;

import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.layout.FlowPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Region;
import javafx.scene.layout.VBox;
import seedu.address.commons.core.timepoint.DateTimeUtil;
import seedu.address.model.contact.Contact;

/**
 * A UI component that displays detailed information of a {@code Contact}.
 */
public class ContactDetailPanel extends UiPart<Region> {

    private static final String FXML = "ContactDetailPanel.fxml";

    @FXML
    private VBox detailPane;
    @FXML
    private Label name;
    @FXML
    private Label phone;
    @FXML
    private Label email;
    @FXML
    private Label address;
    @FXML
    private Label lastContacted;
    @FXML
    private Label lastUpdated;
    @FXML
    private FlowPane tags;
    @FXML
    private VBox tagsContainer;
    @FXML
    private VBox phoneContainer;
    @FXML
    private VBox emailContainer;
    @FXML
    private VBox addressContainer;
    @FXML
    private VBox lastContactedContainer;
    @FXML
    private VBox lastUpdatedContainer;
    @FXML
    private VBox notesContainer;
    @FXML
    private VBox notes;

    private ObservableList<Contact> allContacts;

    /**
     * Creates an empty {@code ContactDetailPanel}.
     */
    public ContactDetailPanel(ObservableList<Contact> allContacts) {
        super(FXML);
        this.allContacts = allContacts;
        clearContact();
    }

    /**
     * Updates the panel to display the given contact's details.
     */
    public void setContact(Contact contact) {
        if (contact == null) {
            clearContact();
            return;
        }

        name.setText(contact.getName().fullName);

        // Phone
        if (contact.getPhone().isPresent()) {
            phone.setText(contact.getPhone().get().value);
            UiUtil.show(phoneContainer);
        } else {
            UiUtil.hide(phoneContainer);
        }

        // Email
        if (contact.getEmail().isPresent()) {
            email.setText(contact.getEmail().get().value);
            UiUtil.show(emailContainer);
        } else {
            UiUtil.hide(emailContainer);
        }

        // Address
        if (contact.getAddress().isPresent()) {
            address.setText(contact.getAddress().get().value);
            UiUtil.show(addressContainer);
        } else {
            UiUtil.hide(addressContainer);
        }

        // Last Contacted
        if (contact.getLastContacted().isPresent()) {
            lastContacted.setText(contact.getLastContacted().get().toString());
            UiUtil.show(lastContactedContainer);
        } else {
            UiUtil.hide(lastContactedContainer);
        }

        // Last Updated
        lastUpdated.setText(DateTimeUtil.toDisplayString(contact.getLastUpdated().value));
        UiUtil.show(lastUpdatedContainer);

        // Notes
        if (!contact.getNotes().isEmpty()) {
            notes.getChildren().clear();
            contact.getNotes()
                    .forEach(note -> {
                        NoteLabel noteLabel = new NoteLabel(note, notes.getStyleClass().toString(), allContacts);
                        noteLabel.hideHeader();
                        Label indexLabel = new Label((notes.getChildren().size() + 1) + ". ");
                        indexLabel.getStyleClass().add("note-index-label");
                        HBox indexedNoteLabel = new HBox(indexLabel, noteLabel);
                        notes.getChildren().add(indexedNoteLabel); });
            UiUtil.show(notesContainer);
        } else {
            UiUtil.hide(notesContainer);
        }

        // Tags
        tags.getChildren().clear();
        if (!contact.getTags().isEmpty()) {
            contact.getTags().stream()
                    .sorted(Comparator.comparing(tag -> tag.name))
                    .forEach(tag -> tags.getChildren().add(new Label(tag.name)));
            UiUtil.show(tagsContainer);
        } else {
            UiUtil.hide(tagsContainer);
        }

        UiUtil.show(detailPane);
    }

    /**
     * Clears the contact details panel.
     */
    public void clearContact() {
        name.setText("");
        phone.setText("");
        email.setText("");
        address.setText("");
        lastContacted.setText("");
        lastUpdated.setText("");
        tags.getChildren().clear();

        UiUtil.hide(phoneContainer);
        UiUtil.hide(emailContainer);
        UiUtil.hide(addressContainer);
        UiUtil.hide(lastContactedContainer);
        UiUtil.hide(lastUpdatedContainer);
        UiUtil.hide(notesContainer);
        UiUtil.hide(tagsContainer);

        UiUtil.hide(detailPane);
    }
}
