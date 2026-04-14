# Sorting contacts: `sort`

Sorts the currently displayed contacts by the specified field(s).

Format: `sort [n/asc | desc] [p/asc | desc] [e/asc | desc] [a/asc | desc] [lu/asc | desc] [lc/asc | desc] [t/TAG_NAME:asc | desc]…`

* Sorts by the fields indicated by each prefix, in the order the prefixes are given.
* `n/` — sort by name, `p/` — sort by phone, `e/` — sort by email, `a/` — sort by address, `lu/` — sort by last updated, `lc/` — sort by last contacted.
* `t/TAG_NAME` — contacts with the ranked tag `TAG_NAME` are displayed at the top.
* At least one sort criterion must be provided.
* Sort criterions from separate `sort` commands can stack.
* Older sort criterion given priority in sorting.

Examples:
* `sort n/asc` sorts all contacts alphabetically by name.
* `sort lu/desc` sorts contacts by when they were last updated.
* `sort n/asc t/vip:desc` sorts contacts alphabetically by name, with contacts tagged `vip` shown first. Contacts that are tagged `vip` will be sorted in decreasing `vip` rank.

### Sort order by field


| Field                 | Ascending: `asc`                                                                                                                                                                                                                                                                                      | Descending: `desc`                                                                                                                                                                                                                                                 |
|-----------------------|-------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------|--------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------|
| Name: `n/`            | Sort contacts by name, in alphabetical order <br> ![sort n/asc]({{ baseUrl }}/images/sort-name-asc.png)                                                                                                                                                                                               | Sort contacts by name, in reverse alphabetical order  <br> ![sort n/desc]({{ baseUrl }}/images/sort-name-desc.png)                                                                                                                                                 |
| Phone number: `p/`    | Sort contacts by phone number, in alphabetical order <br> <box type="info" seamless>**Note: ** Rather than sorting by the number value, the phone number is sorted similarly to a word. I.e. "12" will be sorted to be before "2".</box>  <br> ![sort p/asc]({{ baseUrl }}/images/sort-phone-asc.png) | Sort contacts by phone number, in reverse alphabetical order <br> ![sort p/desc]({{ baseUrl }}/images/sort-phone-desc.png)                                                                                                                                         |
| Email: `e/`           | Sort contacts by email, in alphabetical order  <br> ![sort e/asc]({{ baseUrl }}/images/sort-email-asc.png)                                                                                                                                                                                            | Sort contacts by email, in reverse alphabetical order <br> ![sort e/desc]({{ baseUrl }}/images/sort-email-desc.png)                                                                                                                                                |
| Address: `a/`         | Sort contacts by address, in alphabetical order  <br> ![sort a/asc]({{ baseUrl }}/images/sort-address-asc.png)                                                                                                                                                                                        | Sort contacts by address, in reverse alphabetical order <br> ![sort a/desc]({{ baseUrl }}/images/sort-address-desc.png)                                                                                                                                            |
| Last updated: `lu/`   | Sort contacts by the least recently updated first  <br> ![sort lu/asc]({{ baseUrl }}/images/sort-lu-asc.png)                                                                                                                                                                                          | Sort contacts by the most recently updated first <br> ![sort lu/desc]({{ baseUrl }}/images/sort-lu-desc.png)                                                                                                                                                       |
| Last contacted: `lc/` | Sort contacts by the least recently contacted first  <br> ![sort lc/asc]({{ baseUrl }}/images/sort-lc-asc.png)                                                                                                                                                                                        | Sort contacts by the most recently contacted first <br> ![sort lc/desc]({{ baseUrl }}/images/sort-lc-desc.png)                                                                                                                                                     |
| Tag: `t/TAG_NAME/`    | Sort contacts by tag `TAG_NAME` first. Contacts that have a numerical `TAG_NAME` rank will be sorted in decreasing `TAG_NAME` rank(highest rank first, followed by non-numerical ranks). <br> ![sort t/TAG_NAME:asc]({{ baseUrl }}/images/sort-tag-asc.png)                                           | Sort contacts by tag `TAG_NAME` first. Contacts that have a numerical `TAG_NAME` rank will be sorted in increasing `TAG_NAME` rank(non-numerical ranks first, followed by lowest rank first). <br> ![sort t/TAG_NAME:desc]({{ baseUrl }}/images/sort-tag-desc.png) |

### Resetting sort order

To reset the current sort order, use the `sort` command without any additional keywords.

<box type="info" seamless>

**Note:** Changing or resetting the sort order has no impact on the current `find` filters. To reset both the applied filters and sort order at the same time, use the [`list` command]({{ baseUrl }}/user-guide/list-contacts.html) instead.

</box>
