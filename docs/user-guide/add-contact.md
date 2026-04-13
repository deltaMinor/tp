# Adding a contact: `add`

Adds a contact to the contact list.

Format: `add n/NAME (p/PHONE | e/EMAIL) [a/ADDRESS] [lc/LAST_CONTACTED] [t/TAG]…​`

<box type="tip" seamless>

**Tip:** A contact can have any number of tags (including 0). You must include **at least one** of `p/PHONE` or `e/EMAIL` (the parentheses mean “one or both”); you may supply both.
</box>

Examples:
* `add n/John Doe p/98765432 e/johnd@example.com a/John street, block 123, #01-01`
* `add n/Betsy Crowe t/friend e/betsycrowe@example.com a/Newgate Prison p/1234567 t/criminal`
* `add n/Alex Tan p/91234567`
* `add n/Jane Smith e/jane@example.com lc/22/Feb/2026`

![add contact]({{ baseUrl }}/images/addContact.png)

### Duplicate contacts

A contact is considered a **duplicate** of an existing contact if all of the following criteria hold:

- Both contacts have the exact same name
- Both contacts have the exact same phone number
- Both contacts have the exact same email address

If you try to add a duplicate contact, B2B4U will reject the command with the message: "This contact already exists in the address book".

### Similar contacts
After a successful `add` command, the contact list will be reset to display every contact in the default sort order, then if there are similar contacts in the list, the contact list will be displayed to display the similar contacts.

Two contacts are similar if:

- Both contacts share the same name
- Both contacts share the same phone number
- Both contacts share the same email address

<box type="info" seamless>

**Note:** Similar contact detection uses **exact matching** only. Names that appear similar to a human (e.g. "John Doe" and "John Doe Sr.") will not be flagged as similar unless they match exactly. The same applies to phone numbers and emails.
</box>

![add contact]({{ baseUrl }}/images/addContact-similar.png)
