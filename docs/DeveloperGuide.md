---
  layout: default.md
  title: "Developer Guide"
  pageNav: 3
---

# B2B4U Developer Guide

<!-- * Table of Contents -->
<page-nav-print />


--------------------------------------------------------------------------------------------------------------------

## **Acknowledgements**

* This project is based on the AddressBook-Level3 project created by the [SE-EDU initiative](https://se-education.org).

--------------------------------------------------------------------------------------------------------------------

## **Setting up, getting started**

Refer to the guide [_Setting up and getting started_](SettingUp.md).

--------------------------------------------------------------------------------------------------------------------

## **Design**

### Architecture

<puml src="{{ baseUrl }}/diagrams/ArchitectureDiagram.puml" width="280" />

The ***Architecture Diagram*** given above explains the high-level design of the App.

Given below is a quick overview of main components and how they interact with each other.

**Main components of the architecture**

**`Main`** (consisting of classes
[`Main`](https://github.com/AY2526S2-CS2103T-T08-1/tp/blob/master/src/main/java/seedu/address/Main.java) and
[`MainApp`](https://github.com/AY2526S2-CS2103T-T08-1/tp/blob/master/src/main/java/seedu/address/MainApp.java)) is in
charge of the app launch and shut down.

* At app launch, it initializes the other components in the correct sequence, and connects them up with each other.
* At shut down, it shuts down the other components and invokes cleanup methods where necessary.

The bulk of the app's work is done by the following four components:

* [**`UI`**](#ui-component): The UI of the App.
* [**`Logic`**](#logic-component): The command executor.
* [**`Model`**](#model-component): Holds the data of the App in memory.
* [**`Storage`**](#storage-component): Reads data from, and writes data to, the hard disk.

[**`Commons`**](#common-classes) represents a collection of classes used by multiple other components.

**How the architecture components interact with each other**

The *Sequence Diagram* below shows how the components interact with each other for the scenario where the user issues
the command `delete 1`.

<puml src="{{ baseUrl }}/diagrams/ArchitectureSequenceDiagram.puml" width="574" />

Each of the four main components (also shown in the diagram above),

* defines its *API* in an `interface` with the same name as the Component.
* implements its functionality using a concrete `{Component Name}Manager` class (which follows the corresponding API
  `interface` mentioned in the previous point.

For example, the `Logic` component defines its API in the `Logic.java` interface and implements its functionality using
the `LogicManager.java` class which follows the `Logic` interface. Other components interact with a given component
through its interface rather than the concrete class (reason: to prevent outside component's being coupled to the
implementation of a component), as illustrated in the (partial) class diagram below.

<puml src="{{ baseUrl }}/diagrams/ComponentManagers.puml" width="300" />

The sections below give more details of each component.

### UI component

The **API** of this component is specified in
[`Ui.java`](https://github.com/AY2526S2-CS2103T-T08-1/tp/blob/master/src/main/java/seedu/address/ui/Ui.java)

<puml src="{{ baseUrl }}/diagrams/UiClassDiagram.puml" alt="Structure of the UI Component"/>

The UI consists of a `MainWindow` that is made up of parts e.g.`CommandBox`, `ResultDisplay`, `ContactListPanel`,
`StatusBarFooter` etc. All these, including the `MainWindow`, inherit from the abstract `UiPart` class which captures
the commonalities between classes that represent parts of the visible GUI.

The `UI` component uses the JavaFx UI framework. The layout of these UI parts are defined in matching `.fxml` files that
are in the `src/main/resources/view` folder. For example, the layout of the
[`MainWindow`](https://github.com/AY2526S2-CS2103T-T08-1/tp/blob/master/src/main/java/seedu/address/ui/MainWindow.java)
is specified in
[`MainWindow.fxml`](https://github.com/AY2526S2-CS2103T-T08-1/tp/blob/master/src/main/resources/view/MainWindow.fxml)

The `UI` component,

* executes user commands using the `Logic` component.
* listens for changes to `Model` data so that the UI can be updated with the modified data.
* keeps a reference to the `Logic` component, because the `UI` relies on the `Logic` to execute commands.
* depends on some classes in the `Model` component, as it displays `Contact` object residing in the `Model`.

### Logic component

**API** :
[`Logic.java`](https://github.com/AY2526S2-CS2103T-T08-1/tp/blob/master/src/main/java/seedu/address/logic/Logic.java)

Here's a (partial) class diagram of the `Logic` component:

<puml src="{{ baseUrl }}/diagrams/LogicClassDiagram.puml" width="550"/>

The sequence diagram below illustrates the interactions within the `Logic` component, taking `execute("delete 1")` API
call as an example.

<puml src="{{ baseUrl }}/diagrams/DeleteSequenceDiagram.puml" alt="Interactions Inside the Logic Component for the `delete 1` Command" />

<box type="info" seamless>

**Note:** The lifeline for `DeleteCommandParser` should end at the destroy marker (X) but due to a limitation of
PlantUML, the lifeline continues till the end of diagram.
</box>

How the `Logic` component works:

1. When `Logic` is called upon to execute a command, it is passed to an `AddressBookParser` object which in turn creates
   a parser that matches the command (e.g., `DeleteCommandParser`) and uses it to parse the command.
1. This results in a `Command` object (more precisely, an object of one of its subclasses e.g., `DeleteCommand`) which
   is executed by the `LogicManager`.
1. The command can communicate with the `Model` when it is executed (e.g. to delete a contact).<br>
   Note that although this is shown as a single step in the diagram above (for simplicity), in the code it can take
   several interactions (between the command object and the `Model`) to achieve.
1. The result of the command execution is encapsulated as a `CommandResult` object which is returned back from `Logic`.

Here are the other classes in `Logic` (omitted from the class diagram above) that are used for parsing a user command:

<puml src="{{ baseUrl }}/diagrams/ParserClasses.puml" width="600"/>

How the parsing works:

* When called upon to parse a user command, the `AddressBookParser` class creates an `XYZCommandParser` (`XYZ` is a
  placeholder for the specific command name e.g., `AddCommandParser`) which uses the other classes shown above to parse
  the user command and create a `XYZCommand` object (e.g., `AddCommand`) which the `AddressBookParser` returns back as a
  `Command` object.
* All `XYZCommandParser` classes (e.g., `AddCommandParser`, `DeleteCommandParser`, ...) inherit from the `Parser`
  interface so that they can be treated similarly where possible e.g, during testing.

### Model component

**API** :
[`Model.java`](https://github.com/AY2526S2-CS2103T-T08-1/tp/blob/master/src/main/java/seedu/address/model/Model.java)

<puml src="{{ baseUrl }}/diagrams/ModelClassDiagram.puml" width="520" />


The `Model` component,

* stores the contact list data i.e., all `Contact` objects (which are contained in a `UniqueContactList` object).
* stores the currently 'selected' `Contact` objects (e.g., results of a search query) as a separate _displayed_ list
  which is exposed to outsiders as an unmodifiable `ObservableList<Contact>` that can be 'observed' e.g. the UI can be
  bound to this list so that the UI automatically updates when the data in the list change.
* stores a `UserPrefs` object that represents the user’s preferences. This is exposed to the outside as a
  `ReadOnlyUserPrefs` object.
* does not depend on any of the other three components (as the `Model` represents data entities of the domain, they
  should make sense on their own without depending on other components)

Each `Contact` also carries:

* a stable **`UUID`** (persisted in JSON), used when notes store cross-references to other contacts; commands that
  resolve `@INDEX` in note text use the **displayed** contact list at parse time.
* optional **`LastContacted`** for when the contact was last reached (flexible user phrasing, e.g. relative or absolute
  dates).
* **`LastUpdated`**, always present (`LocalDateTime`), typically set to “now” when a contact is added or edited through
  the usual code paths; used for ordering and display logic.
* a list of **`Note`** values: plain text plus an optional reminder; after parsing, stored text may contain `@{UUID}`
  cross-references to other contacts. Note command feedback uses `Messages.formatNoteOutput` (which uses
  `Contact.getNotesString()`); the GUI can resolve references when rendering (e.g. `NoteLabel`).
* a set of **`Tag`** instances; in practice some entries are **`RankedTag`** (a subclass of `Tag`) for ranked friend
  tags—the class diagram shows only `Tag` to keep the overview simple.

**Implementation detail (omitted from the model diagram):** both **`LastContacted`** and optional note reminders are
represented using **`TimePoint`** from `seedu.address.commons.core.timepoint` for parsing and comparing those time
phrases.

`JsonAdaptedContact` persists `lastUpdated`, `lastContacted`, and `notes` alongside the other contact fields.

<box type="info" seamless>

**Note:** The diagram below is an **alternative** (arguably more OOP) design: a central `Tag` list on `AddressBook` that
`Contact` references, so each unique tag exists once. **The running app does not use this structure**—`AddressBook` only
contains a `UniqueContactList`, and each `Contact` owns its own `Tag` instances as in the main model diagram above.

<puml src="{{ baseUrl }}/diagrams/BetterModelClassDiagram.puml" width="450" />

</box>

### Storage component

**API** :
[`Storage.java`](https://github.com/AY2526S2-CS2103T-T08-1/tp/blob/master/src/main/java/seedu/address/storage/Storage.java)

<puml src="{{ baseUrl }}/diagrams/StorageClassDiagram.puml" width="550" />

The `Storage` component,

* saves both contact list data and user preference data in JSON format, and reads them back into corresponding
  objects.
* inherits from both `AddressBookStorage` and `UserPrefStorage`, which means it can be treated as either one (if only
  the functionality of only one is needed).
* depends on some classes in the `Model` component (because the `Storage` component's job is to save/retrieve objects
  that belong to the `Model`)

### Common classes

Classes used by multiple components are in the `seedu.address.commons` package.

--------------------------------------------------------------------------------------------------------------------

## **Implementation**

This section describes some noteworthy details on how certain features are implemented.

### Remove field on empty edit

#### Overview

The edit command supports removing optional fields (phone, email, address, last contacted date) by supplying the field prefix with no argument. For example, `edit 1 p/` removes the phone number from the first contact.

#### Implementation

The following sequence diagram shows how the edit field removal mechanism works when the user executes `edit 1 p/`:

<puml src="{{ baseUrl }}/diagrams/EditSequenceDiagram.puml" alt="EditSequenceDiagram" />

1. `EditCommandParser` detects the empty value for the `p/` prefix and sets `clearPhone = true` in the `EditContactDescriptor`.
2. `EditCommand#createEditedContact()` checks the clear flag — if `clearPhone` is true, `updatedPhone` is set to `Optional.empty()`.
3. Before applying the edit, the command validates that the resulting contact retains at least a phone number or email address.
4. The updated contact is saved to the model.

#### Design considerations

**Aspect: How empty prefix values are handled:**

* **Alternative 1 (current choice):** Dedicated boolean clear flags (`clearPhone`, `clearEmail`, etc.) in `EditContactDescriptor`.
  * Pros: Explicit intent — distinguishes "remove field" from "no change". Avoids null/sentinel confusion.
  * Cons: Adds extra fields and logic to the descriptor.

* **Alternative 2:** Use a sentinel value (e.g. empty string) to represent removal.
  * Pros: Fewer fields — reuses the existing `Optional<Phone>` field.
  * Cons: Sentinel values are error-prone and harder to reason about. Validation logic in `Phone`, `Email`, etc. would need special-case handling.

### Undo/redo feature

The undo/redo mechanism is facilitated by `Snapshot`. It stores key information regarding a `Model`, stored internally as an `List<Pair<String, Snapshot>>` named `snapshots` and an `int snapshotPosition` is used to indicate the `Snapshot` being used. Additionally, `Model` implements the following methods:

* `saveSnapshot(String description)` — Saves the current `Model` state with a name for user reference.
* `moveSnapshot(int offset)` — Moves the `Model` by `offset` number of snapshots in its history.


Given below is an example usage scenario and how the undo/redo mechanism behaves at each step.

Step 1. The user launches the application for the first time. The `ModelManager`, the instantiable version of `Model`, will be initialized with the initial `snapshotPosition` of `0`, and a singular `snapshot` in `snapshots` representing the `ModelManager`'s current state.

<puml src="{{ baseUrl }}/diagrams/UndoRedoState0.puml" alt="UndoRedoState0" />

Step 2. The user executes `add n/John …​` command to add a new contact. The `add` command calls `Model#saveSnapshot(feedback)`, where `feedback` is the string in the `CommandResult` from executing the `AddCommand`, "New contact added: John…​", thus a `snapshot` of the `ModelManager` after the `add n/John…​` command executes to be saved in `snapshot`, and the `snapshotPosition` is incremented.

<puml src="{{ baseUrl }}/diagrams/UndoRedoState1.puml" alt="UndoRedoState1" />

Step 3. The user executes `delete 7` to delete the 7th contact which happens to be the most recently added contact. The `delete` command also calls `Model#saveSnapshot(feedback)`, causing another `snapshot` to be saved into `snapshots`.

<puml src="{{ baseUrl }}/diagrams/UndoRedoState2.puml" alt="UndoRedoState2" />

<box type="info" seamless>

**Note:** If a command fails its execution, it will not call `Model#saveSnapshot(feedback)`, so no new `snapshot` will be created.

</box>

Step 4. The user now decides that deleting the contact was a mistake, and decides to undo that action by executing the `undo` command. The `undo` command will call `Model#moveSnapshot(-1)`, which will decrement `snapshotPosition`, and restores `ModelManager` with data given by the `snapshotPosition`-th `snapshot`.

<puml src="{{ baseUrl }}/diagrams/UndoRedoState3.puml" alt="UndoRedoState3" />


<box type="info" seamless>

**Note:** If `snapshotPosition` is 0, pointing to the initial `Model`'s `snapshot`, then there are no previous `snapshot` to restore to. In this case an `IndexOutOfBoundsException` will be thrown.

</box>

The following sequence diagram shows how an undo operation goes through the `Logic` component:

<puml src="{{ baseUrl }}/diagrams/UndoSequenceDiagram-Logic.puml" alt="UndoSequenceDiagram-Logic" />

<box type="info" seamless>

**Note:** The lifeline for `UndoCommand` should end at the destroy marker (X) but due to a limitation of PlantUML, the lifeline reaches the end of diagram.

</box>

Similarly, how an undo operation goes through the `Model` component is shown below:

<puml src="{{ baseUrl }}/diagrams/UndoSequenceDiagram-Model.puml" alt="UndoSequenceDiagram-Model" />

The `redo` command does the opposite — it calls `Model#moveSnapshot(1)`. If `snapshotPosition` is less than `snapshots.size() - 1`, `snapshotPosition` is incremented, then the `snapshotPosition`-th `snapshot` is retrieved to restores the `ModelManager` to the state it represents.

<box type="info" seamless>

**Note:** If the `currentStatePointer` is at index `addressBookStateList.size() - 1`, pointing to the latest address book state, then there are no undone AddressBook states to restore. The `redo` command uses `Model#canRedoAddressBook()` to check if this is the case. If so, it will return an error to the user rather than attempting to perform the redo.

</box>

Step 5. The user then decides to execute the command `list`. Commands that do not modify the address book, such as `list`, will usually not call `Model#saveSnapshot()` or `Model#moveSnapshot()`. Thus, the `snapshotPosition` remains unchanged.

<puml src="{{ baseUrl }}/diagrams/UndoRedoState4.puml" alt="UndoRedoState4" />

Step 6. The user executes `clear`, which calls `Model#saveSnapshot()`. Since the `snapshotPosition` is not equal to `snapshots.size() - 1`, all snapshots after the `snapshotPosition`-th `snapshot` will be purged. Reason: It no longer makes sense to redo the `delate 7` command. This is the behavior that most modern desktop applications follow.

<puml src="{{ baseUrl }}/diagrams/UndoRedoState5.puml" alt="UndoRedoState5" />

The following activity diagram summarizes what happens when a user executes a new command:

<puml src="{{ baseUrl }}/diagrams/CommitActivityDiagram.puml" width="250" />

#### Design considerations:

**Aspect: How undo & redo executes:**

* **Alternative 1 (current choice):** Saves a compact copy of the model.
  * Pros: Easy to implement.
  * Cons: May have performance issues in terms of memory usage even with reduced object size.

* **Alternative 2:** Save an 'undo/redo' version of each command.
  * Pros: Will use less memory (e.g. for `delete`, just save the contact being deleted).
  * Cons: We must ensure that the implementation of each individual command are correct.

_{more aspects and alternatives to be added}_

### \[Proposed\] Data archiving

_{Explain here how the data archiving feature will be implemented}_

--------------------------------------------------------------------------------------------------------------------

## **Documentation, logging, testing, configuration, dev-ops**

* [Documentation guide](Documentation.md)
* [Testing guide](Testing.md)
* [Logging guide](Logging.md)
* [Configuration guide](Configuration.md)
* [DevOps guide](DevOps.md)

--------------------------------------------------------------------------------------------------------------------

## **Appendix: Requirements**

### Product scope

**Target user profile**:

* consultant of a medium-sized consulting or PR agency
* has a need to manage long term relationships with big companies and clients
* has a need to manage long term relationships with various services in order to best assist their clients
* prefer desktop apps over other types
* can type fast
* prefers typing to mouse interactions
* is reasonably comfortable using CLI apps
* tends to work by themselves
* does not share their computer with others

**Value proposition**: Allow better management of long term relationships with big companies and clients through a simple, unified interface. Provide users with quick, up-to-date access to clients’ data and relevant services necessary for quick and on-point responses.


### User stories

Priorities: High (must have) - `* * *`, Medium (nice to have) - `* *`, Low (unlikely to have) - `*`

| Priority | As a …​                 | I want to …​                                                                                                | So that I can…​                                                                                         |
|----------|-------------------------|-------------------------------------------------------------------------------------------------------------|---------------------------------------------------------------------------------------------------------|
| `* * *`  | consultant              | record clients' contact details                                                                             | retrieve them later                                                                                     |
| `* * *`  | consultant              | retrieve clients' contact details                                                                           | contact them again                                                                                      |
| `* * *`  | consultant              | delete client's contact details                                                                             | curate contacts and comply with data protection laws                                                    |
| `* * *`  | existing user           | check how to do a task / action                                                                             | use the app                                                                                             |
| `* *`    | consultant              | view an individual client's contact details                                                                 | look at their information in detail                                                                     |
| `* *`    | user with many clients  | find a person's contact by entering their name                                                              | easily contact them again even amongst an ocean of contacts                                             |
| `* *`    | user with many clients  | manually organise clients by importance / relevance                                                         | check up on them first                                                                                  |
| `* *`    | user with many clients  | sort profile data by last updated                                                                           | check for outdated profiles that require updating                                                       |
| `* *`    | security-conscious user | password-protect some sensitive information                                                                 | ensure privacy for profiles                                                                             |
| `* *`    | potential user          | review its functionalities without spending too much time                                                   | understand how the app will help me                                                                     |
| `* *`    | migrating user          | load existing client data as a file                                                                         | easily migrate clients over to the app                                                                  |
| `* *`    | migrating user          | create incomplete data profiles to be updated later                                                         | migrate existing clients over more gradually                                                            |
| `* *`    | consultant              | search for profiles by tags such as industry, occupation, company etc.                                      | quickly find contacts of services that may be useful to my client                                       |
| `* *`    | consultant              | search for profiles by fields such as location and email keywords                                           | quickly find contacts with limited information                                                          |
| `* *`    | consultant              | be reminded of upcoming appointments                                                                        | do not miss them                                                                                        |
| `* *`    | consultant              | edit client's contact details                                                                               | correct mistakes and comply with data protection laws                                                   |
| `* *`    | consultant              | sort contacts by relationship level or commission fees                                                      | find the most suitable help for my clients while balancing other factors such as budget                 |
| `* *`    | consultant              | update existing business information                                                                        | correct mistakes / obsolete data and comply with data protection laws                                   |
| `* *`    | consultant              | delete existing business information                                                                        | remove obsolete data and comply with data protection laws                                               |
| `* *`    | consultant              | store relevant business information to a client                                                             | save time looking it up in a separate app / physically                                                  |
| `* *`    | consultant              | retrieve relevant business information to a client                                                          | save time looking it up in a separate app / physically                                                  |
| `* *`    | consultant              | sort contacts by last contacted                                                                             | keep track of cold contacts                                                                             |
| `* *`    | busy consultant         | see a clear dashboard overview of recently contacted clients                                                | get straight to work without navigating through complex menus.                                          |
| `*`      | user                    | undo accidental actions                                                                                     | prevent permanent data loss from mistakes                                                               |
| `*`      | user                    | attach PDF contracts or brand guidelines to a client profile                                                | quickly retrieve them for reference                                                                     |
| `*`      | typing user             | easily input clients' data through file-editing                                                             | speed up my workflow                                                                                    |
| `*`      | proficient user         | archive old projects                                                                                        | my active workspace remains uncluttered                                                                 |
| `*`      | power user              | use keyboard shortcuts or task automation                                                                   | efficiently execute repeated tasks                                                                      |
| `*`      | potential user          | simulate my usual workflow with mock data                                                                   | get hands-on experience with the functionalities                                                        |
| `*`      | new user                | follow a simple guided setup                                                                                | customize the app to my needs and preferences                                                           |
| `*`      | forgetful user          | utilise fuzzy search even within commands                                                                   | easily use the retrieve data without clear memory of the commands or target details                     |
| `*`      | dark-mode user          | set the app to dark mode                                                                                    | reduce eye strain during late-night event planning                                                      |
| `*`      | light-mode user         | set the app to light mode                                                                                   | improve readability in well-lit environments                                                            |
| `*`      | consultant              | take down minutes during a discussion                                                                       | remember and reference them later                                                                       |
| `*`      | consultant              | cross reference tool which vendors have worked with which clients before                                    | understand relationships quickly                                                                        |
| `*`      | consultant              | automatically send emails to old clients                                                                    | follow up and keep contacts warm                                                                        |
| `*`      | consultant              | save availability information for selected contacts as a calendar and filter contacts based on availability | later on, I know when they can be contacted in person rather than needing to double-check ahead of time |
| `*`      | beginner user           | pick up advanced functionalities gradually                                                                  | utilize more of the features provided                                                                   |

### Use cases

(For all use cases below, the **System** is the `B2B4U` and the **Actor** is the `user`, unless specified otherwise)

**Use case: UC1 - Check User Guide**

**MSS**

1. User requests user guide
2. B2B4U provides user guide

    Use case ends

**Use case: UC2 - Add a contact**

**MSS**

1. User requests to create a new contact with certain parameters
2. B2B4U saves the new contact
3. B2B4U displays confirmation that the new contact is saved

    Use case ends

**Extensions**

* 1a. The new contact is invalid
  * 1a1. B2B4U shows an error message

    Use case ends.

**Use case: UC3 - View all contacts**

**MSS**

1. User requests to list contacts
2. B2B4U shows a list of all stored contacts

    Use case ends.

**Use case: UC4 - Delete a contact**

**MSS**

1. User requests to <u>view contacts (UC3)</u>
2. User requests to delete a specific contact in the list
3. B2B4U deletes the contact

    Use case ends.

**Extensions**

* 1a. The list is empty.

  Use case ends.

* 2a. The given index is invalid.
  * 2a1. B2B4U shows an error message.

    Use case resumes at step 1.

**Use case: UC5 - Filter contacts by criterion**

**MSS**
1. User requests to list contacts which fulfill a given criterion
2. B2B4U shows a list of all contacts which fulfill the given criterion

    Use case ends.

**Use case: UC6 - Sort contacts by criterion**

**MSS**
1. User requests to list contacts sorted by a given criterion
2. B2B4U shows a list of all contacts in order of the given criterion

    Use case ends.

### Non-Functional Requirements

1. Should work on any _mainstream OS_ as long as it has Java `17` or above installed.
2. Should be able to hold up to 1000 contacts in storage.
3. Should always respond to user input within 250 milliseconds.
4. A user with above average typing speed for regular English text (i.e. not code, not system admin commands) should be able to accomplish most of the tasks faster using commands than using the mouse.
5. Should only be accessible to a single user.
6. Data should be stored locally and in a human editable text file.
7. Should work without requiring an installer.
8. Should work without reliance on a remote server.
9. Should only contain third-party frameworks/libraries/services which are free, open-source, and have permissive license terms (if any).
10. GUI should work well for standard screen resolutions 1920x1080 and higher, and for screen scales 100% and 125%.
11. GUI should be usable for resolutions 1280x720 and higher, and for screen scales 150%.
12. Should be packaged in a single JAR file of 100MB or less in size.

### Glossary

* **Mainstream OS**: Windows, Linux, Unix, MacOS
* **JSON (JavaScript Object Notation)**: A lightweight text format used to store and transfer structured data.
* **API (Application Programming Interface)**: A defined set of methods that allows one component of the system to interact with another component.
* **GUI (Graphical User Interface)**: The visual interface through which the user interacts with the application, built using JavaFX.
* **CLI (Command Line Interface)**: A text-based interface where users interact with the application by typing commands.
* **Private contact detail**: A contact detail that is not meant to be shared with others
* **Command**: A structured line of text entered by the user to instruct the application to perform a specific action.
* **Contact**: A single entry stored in the contact list, containing one or more pieces of contact information such as name, phone number, email, or address.
* **Displayed list**: A subset of contacts displayed after applying a search, filtering, or sorting operation.

--------------------------------------------------------------------------------------------------------------------

## **Appendix: Instructions for Manual Testing**

Given below are instructions to test the app manually.

<box type="info" seamless>

**Note:** These instructions only provide a starting point for testers to work on;
testers are expected to do more *exploratory* testing.

</box>

### Launch and shutdown

1. Initial launch

   1. Download the jar file and copy into an empty folder

   2. Double-click the jar file <br>
     Expected: Shows the GUI with a set of sample contacts. The window size may not be optimum.

1. Saving window preferences

   1. Resize the window to an optimum size. Move the window to a different location. Close the window.

   2. Re-launch the app by double-clicking the jar file.<br>
     Expected: The most recent window size and location is retained.

### Deleting a contact

1. Deleting a contact while all contacts are being shown

   1. Prerequisites: List all contacts using the `list` command. Multiple contacts in the list.

   2. Test case: `delete 1`<br>
    Expected: First contact is deleted from the list. Details of the deleted contact shown in the status message. Timestamp in the status bar is updated.

   3. Test case: `delete 0`<br>
    Expected: No contact is deleted. Error details shown in the status message. Status bar remains the same.

   4. Other incorrect delete commands to try: `delete`, `delete x`, `...` (where x is larger than the list size)<br>
    Expected: Similar to previous.

### Adding a contact

1. Adding a contact with all fields

   1. Test case: `add n/John Doe p/98765432 e/johnd@example.com a/311, Clementi Ave 2, #02-25 t/friends`<br>
    Expected: New contact is added to the list. Details of the added contact shown in the status message.

   2. Test case: `add n/Jane Doe`<br>
    Expected: No contact is added. Error details shown in the status message, as at least a phone number or email is required.

   3. Test case: `add n/John Doe p/98765432` followed by `add n/John Doe p/98765432`<br>
    Expected: Second command fails with a duplicate contact error message.

### Editing a contact

1. Editing a contact while all contacts are being shown

   1. Prerequisites: List all contacts using the `list` command. Multiple contacts in the list.

   2. Test case: `edit 1 p/91234567`<br>
    Expected: Phone number of the first contact is updated. Details of the edited contact shown in the status message.

   3. Test case: `edit 1 t/`<br>
    Expected: All tags of the first contact are removed.

   4. Test case: `edit 0 p/91234567`<br>
    Expected: No contact is edited. Error details shown in the status message.

### Adding a note to a contact

1. Adding notes and reminders

   1. Prerequisites: List all contacts using the `list` command. At least one contact in the list.

   2. Test case: `note 1 Follow up with client`<br>
    Expected: Note is added to the first contact. Status message confirms the note was added.

   3. Test case: `note 1 Meeting on/25/12/2026`<br>
    Expected: A reminder note is added. The contact gains a `Reminder` tag.

   4. Test case: `note 1 ca/`<br>
    Expected: All notes are cleared from the first contact.

### Finding contacts

1. Finding contacts by keyword

   1. Test case: `find alex`<br>
    Expected: Contacts with "alex" in any field are displayed.

   2. Test case: `find n/Alex t/friends`<br>
    Expected: Contacts matching both name "Alex" and tag "friends" are displayed.

   3. Test case: `find` (no arguments)<br>
    Expected: All filters are cleared and all contacts are shown.

### Sorting contacts

1. Sorting contacts by field

   1. Test case: `sort n/asc`<br>
    Expected: Contacts are sorted by name in ascending order.

   2. Test case: `sort lc/desc`<br>
    Expected: Contacts are sorted by last contacted date, newest first.

   3. Test case: `sort` (no arguments)<br>
    Expected: Sort order is reset to default.

### Saving data

1. Dealing with missing/corrupted data files

   1. Prerequisites: Ensure that app is closed. `preferences.json` and `config.json` files are present in the same folder as the jar file.

   2. Test case: Delete data file (if present) and launch the app. <br>
    Expected: App loads with no contacts. A new data file is created when a contact is added.

   3. Test case: Corrupt data file (e.g. by adding random text to the file) and launch the app. <br>
    Expected: App loads with no contacts. The data file is overwritten when a contact is added.

--------------------------------------------------------------------------------------------------------------------

## **Appendix: Effort**

### Difficulty Level
Compared to AB3, our project had a greater degree of complexity, as it involved functionality beyond the minimal requirements of a basic contact management application,
notably a file management system and the undo/redo feature. Alongside the changes to the user interface, this project required a great amount of refactoring to allow for the features we planned.

### Challenges faced
**Command structure:**
While implementing commands that affected elements beyond the contact list (i.e. file management commands, setting the theme, opening/closing the view panel),
we have had to reevaluate what can be done within the scope of the `Command`'s `execute` function which could not directly access the UI elements,
and took care to not break software engineering principles while altering the behaviour of classes outside of those directly related to command-execution.

**GUI implementation:**
Implementing GUI elements that displayed information with clarity within the constraints of JavaFX's provided tools proved to be a struggle.
For example, while JavaFX's `Label` can truncate text with ellipses, it was not versatile enough to contain highlighted text seamlessly, unlike `TextFlow` which could not truncate text.
In the end, some compromises had to be made to create an interface that was as user-friendly as we can feasibly put together.

### Effort required
**Design and Refactoring:**
Restructuring the GUI to be more user-friendly and allow for features such as custom themes,
as well as altering the functionality of commands and field constraints of contacts required careful refactoring and the creation of new class structures.

**Testing and Debugging:**
To ensure the reliability of our system, we wrote comprehensive test cases to verify that each feature and command functioned correctly.

### Achievements
In conclusion, our team successfully designed and implemented key features, resolved bugs, and navigated potential integration challenges.
While we initially encountered difficulties with more complex components such as file access and GUI restructuring,
effective collaboration allowed us to overcome these hurdles and accomplish our objectives for B2B4U.

--------------------------------------------------------------------------------------------------------------------

## **Appendix: Planned Enhancements**

Team size: 5

1. **Allow user to select "AND" and "OR" basis for `find` command:** The current `find` command applies all the search filters on an "AND" basis, meaning only contacts that match every parameter in the command will be displayed. We plan to add the option for users to search on an "OR" basis as well, such that any contact that matches at least one of the parameter will be displayed.

2. **Allow user to set reminder alert time:** Currently, users will only be notified of reminders that are due within 7 days. We plan to add functionality for the user to adjust when they would like to be reminded for their reminders, such that they can be notified earlier or later than a week prior.
