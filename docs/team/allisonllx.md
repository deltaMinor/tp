---
  layout: default.md
  title: "allisonllx's Project Portfolio Page"
---

<img src="../images/allisonllx.png" width="200px">

[[github](http://github.com/allisonllx)]

## Overview: B2B4U

B2B4U is a desktop contact management application made for business consultants, optimized for use via a Command Line Interface (CLI) while still having the benefits of a Graphical User Interface (GUI).
If you can type fast, B2B4U can get your human resource management tasks done faster than traditional GUI apps.

Given below are my contributions to the project.

## New Features
- Added the ability to find contacts by fields
  - Function: Allow users to combine field-specific keywords (e.g. name, email, address, last contacted, tags) to quickly narrow down matching contacts.
  - Justification: This helps consultants retrieve contacts even when they only remember partial information such as an email fragment or location keyword.
  - Highlights: The feature extends the existing find flow by composing predicates across multiple fields while preserving intuitive CLI usage and command feedback.
  - Non-trivial pull-requests: [#74](https://github.com/AY2526S2-CS2103T-T08-1/tp/pull/74), [#77](https://github.com/AY2526S2-CS2103T-T08-1/tp/pull/77), [#88](https://github.com/AY2526S2-CS2103T-T08-1/tp/pull/88)

## Enhancements to existing features
- Added a `Last Updated` field with sorting support
  - Function: Track when each contact profile was most recently modified, display it in the UI, and allow sorting by this field.
  - Justification: Users with large contact lists can identify stale profiles and prioritise records that likely need refresh.
  - Highlights: Introduced model, storage, parser, UI, and comparator updates so `lastUpdated` is persisted and can be used consistently in sort and display.
  - Non-trivial pull-requests: [#113](https://github.com/AY2526S2-CS2103T-T08-1/tp/pull/113), [#116](https://github.com/AY2526S2-CS2103T-T08-1/tp/pull/116)
- Added a `Last Contacted` field
  - Function: Store the latest known contact point (including flexible user input formats) for each profile.
  - Justification: This supports follow-up planning by making cold contacts easy to identify.
  - Highlights: Implemented end-to-end support from parsing and validation to model/storage integration and display.
  - Non-trivial pull-requests: [#109](https://github.com/AY2526S2-CS2103T-T08-1/tp/pull/109)
- Updated default sort
  - Function: Updated the default ordering used in list/load/reset views so high-priority contacts (recently contacted/updated and urgent reminders) surface earlier.
  - Justification: Instead of adding a separate dashboard, this gives users an immediate overview in their primary contact list with no extra navigation.
  - Highlights: Centralised default comparator logic in the model so command-driven sorts, resets, and persisted state transitions remain predictable.
  - Non-trivial pull-requests: [#197](https://github.com/AY2526S2-CS2103T-T08-1/tp/pull/197)

### Code contribution
[[RepoSense report](https://nus-cs2103-ay2526-s2.github.io/tp-dashboard/?search=t08&sort=groupTitle&sortWithin=title&timeframe=commit&mergegroup=&groupSelect=groupByRepos&breakdown=true&checkedFileTypes=docs~functional-code~test-code~other&since=2026-02-20T00%3A00%3A00&filteredFileName=&tabOpen=true&tabType=authorship&tabAuthor=allisonllx&tabRepo=AY2526S2-CS2103T-T08-1%2Ftp%5Bmaster%5D&authorshipIsMergeGroup=false&authorshipFileTypes=docs~functional-code~test-code&authorshipIsBinaryFileTypeChecked=false&authorshipIsIgnoredFilesChecked=false)]
<iframe src="https://nus-cs2103-ay2526-s2.github.io/tp-dashboard/?search=allisonllx&breakdown=true&sort=groupTitle%20dsc&sortWithin=title&since=2026-02-20T00%3A00%3A00&timeframe=commit&mergegroup=&groupSelect=groupByRepos&checkedFileTypes=docs~functional-code~test-code~other&filteredFileName=" frameBorder="0" width="800px" height="147px"></iframe>

### Documentation
- User Guide
  - Updated `find` command usage: [#259](https://github.com/AY2526S2-CS2103T-T08-1/tp/pull/259)
- Developer's Guide
  - Updated UML and description for `Model` component: [#261](https://github.com/AY2526S2-CS2103T-T08-1/tp/pull/261)

### Community
- Reviewed various PRs: [#99](https://github.com/AY2526S2-CS2103T-T08-1/tp/pull/99), [#298](https://github.com/AY2526S2-CS2103T-T08-1/tp/pull/298), [#299](https://github.com/AY2526S2-CS2103T-T08-1/tp/pull/299)
- Conducted some bug-testing: [#172](https://github.com/AY2526S2-CS2103T-T08-1/tp/issues/172), [#253](https://github.com/AY2526S2-CS2103T-T08-1/tp/issues/253), [#256](https://github.com/AY2526S2-CS2103T-T08-1/tp/issues/256)
