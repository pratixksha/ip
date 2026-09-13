# Shrek User Guide

Shrek is a desktop task manager for todos, deadlines, and events. Tasks can be
labelled with up to three tags so that related tasks are easy to find.

Commands ignore leading and trailing whitespace and treat repeated whitespace
as a single separator. The `list` and `bye` commands do not accept arguments,
and task-status commands such as `mark`, `unmark`, and `delete` require exactly
one task number.

## Adding tagged tasks

Tags are optional suffixes on task-creation commands. A tag starts with `#` and
contains 1–10 letters, digits, hyphens, or underscores. Tags are
case-insensitive and displayed in lowercase alphabetical order.

Examples:

```
todo play game #fun #weekend
deadline return book /by 2019-10-15 #school
event party /from 2pm /to 4pm #fun
```

Event times may be time-only values such as `2pm`, `2:30pm`, or `14:00`, or
ISO local date-times such as `2026-09-13T14:00`. The start and end must use the
same format, and the end must be later than the start. Event commands reject
duplicate `/from` or `/to` markers. Deadline commands reject duplicate `/by`
markers.

The first command produces:

```
Got it. I've added this task:
  [T][ ] play game #fun #weekend
```

Tags must be a contiguous suffix. For example, `todo play #fun later` is
rejected. Invalid, duplicate, and excessive tags reject the entire command.
Adding a task that has the same type, description, date/time details, and tags
as an existing task is rejected as a duplicate, regardless of completion status.

## Adding and removing tags

Use `tag` or `untag`, followed by a task number and one or more tags:

```
tag 1 #fun #school
untag 1 #fun #school
```

The operation is atomic: if any requested tag is invalid, duplicated, already
present, absent, or would exceed the three-tag limit, no tags are changed.

## Finding tagged tasks

`find` performs a case-insensitive substring search over both descriptions and
tags:

```
find week
```

This finds a task tagged `#weekend`.

## Saved data

Untagged records keep the existing format. Tagged records append one
comma-separated tag field:

```
T | 0 | play game | #fun,#weekend
D | 0 | return book | 2019-10-15 | #school
E | 1 | party | 2pm | 4pm | #fun
```

Existing untagged records remain readable. Malformed records continue to be
skipped when the data file is loaded, consistent with the existing storage
behavior.
