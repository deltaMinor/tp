# Finding contacts: `find`

Finds contacts whose fields match the specified search criteria.

Format: `find [KEYWORD]… [n/NAME] [p/PHONE] [e/EMAIL] [a/ADDRESS] [lc/LAST_CONTACTED] [t/TAG]…`
or: `find @INDEX` to find contacts associated with the contact at INDEX

* The search is case-insensitive. e.g. `hans` will match `Hans`.
* Unprefixed `KEYWORD`s search across all fields (name, phone, email, address, notes, tags) using partial matching. Each keyword must appear somewhere in the contact.
  * Note: As months are abbreviated in reminders, a search with the full name of a month exceeding 3 letters in length will not successfully filter for reminders with that month.
  * Example: Given contact "Alex Yeoh" with note "to meet _on_ Jun 19, 2026"
    * `find Jun` will display contact "Alex Yeoh"
    * `find June` **will not** display contact "Alex Yeoh"
* Prefixed searches (`n/`, `p/`, `e/`, `a/`, `lc/`) filter by the specified field using partial matching.
* `t/TAG` filters by tag using **exact** matching (e.g. `t/friend` will not match a tag named `friends`).
* All search conditions are combined with **AND** logic — only contacts satisfying **every** condition are returned.
* At least one search condition must be provided.
* `find @INDEX` performs a **bidirectional** cross-reference lookup on the contact at the given index:
  * It finds all contacts that are **referenced by** the target contact's notes (via `@INDEX` references).
  * It also finds all contacts whose notes **reference** the target contact.
  * This allows you to see all associations regardless of which direction the reference was made.

Examples:
* `find John` returns contacts containing `john` in any field.
* `find n/Alex` returns contacts with `Alex` in their name.
* `find p/94` returns contacts with `94` in their phone number.
* `find a/street t/friends` returns contacts that have `street` in their address **and** the exact tag `friends`.
* `find @1` shows all contacts associated with the 1st contact — both contacts referenced in their notes and contacts that reference them.
* If Contact 1's notes contain `@2` (a reference to Contact 2), both `find @1` and `find @2` will show the association between them.

![find contacts]({{ baseUrl }}/images/findContacts.png)

### Clearing filters

To remove the current filters and display every contact, use the `find` command without any additional keywords.

<box type="info" seamless>

**Note:** Changing or resetting the `find` filters has no impact on the current sort order. To reset both the applied filters and sort order at the same time, use the [`list` command]({{ baseUrl }}/user-guide/list-contacts.html) instead.

</box>
