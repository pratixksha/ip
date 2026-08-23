package shrek.parser;

import java.time.LocalDate;
import java.time.format.DateTimeParseException;

import shrek.CommandType;
import shrek.ShrekException;
import shrek.task.Deadline;
import shrek.task.Event;
import shrek.task.Todo;

/**
 * Deals with making sense of the user command.
 */
public class Parser {

    public static CommandType parseCommandType(String input) {
        String commandWord = input.split(" ", 2)[0];
        try {
            return CommandType.valueOf(commandWord.toUpperCase());
        } catch (IllegalArgumentException e) {
            return CommandType.UNKNOWN;
        }
    }

    public static String parseArgs(String input) {
        String commandWord = input.split(" ", 2)[0];
        return input.length() > commandWord.length()
                ? input.substring(commandWord.length()).trim()
                : "";
    }

    public static int parseTaskIndex(String args, String command, int taskCount) throws ShrekException {
        if (args.isEmpty()) {
            throw new ShrekException("OOPS!!! Please specify which task number to " + command + ".");
        }
        int index;
        try {
            index = Integer.parseInt(args) - 1;
        } catch (NumberFormatException e) {
            throw new ShrekException("OOPS!!! The task number must be a valid number.");
        }
        if (index < 0 || index >= taskCount) {
            throw new ShrekException("OOPS!!! That task number doesn't exist. You have "
                    + taskCount + " task(s) in your list.");
        }
        return index;
    }

    public static Todo parseTodo(String args) throws ShrekException {
        if (args.isEmpty()) {
            throw new ShrekException("OOPS!!! The description of a todo cannot be empty.");
        }
        return new Todo(args);
    }

    public static Deadline parseDeadline(String args) throws ShrekException {
        if (args.isEmpty()) {
            throw new ShrekException("OOPS!!! The description of a deadline cannot be empty.");
        }
        if (!args.contains("/by")) {
            throw new ShrekException("OOPS!!! A deadline needs a '/by' followed by the due date.");
        }
        String[] parts = args.split("/by", 2);
        String description = parts[0].trim();
        String byText = parts[1].trim();
        if (description.isEmpty()) {
            throw new ShrekException("OOPS!!! The description of a deadline cannot be empty.");
        }
        if (byText.isEmpty()) {
            throw new ShrekException("OOPS!!! The due date of a deadline cannot be empty.");
        }
        LocalDate by;
        try {
            by = LocalDate.parse(byText);
        } catch (DateTimeParseException e) {
            throw new ShrekException(
                    "OOPS!!! Please enter the deadline date in yyyy-mm-dd format, e.g. 2019-10-15.");
        }
        return new Deadline(description, by);
    }

    public static Event parseEvent(String args) throws ShrekException {
        if (args.isEmpty()) {
            throw new ShrekException("OOPS!!! The description of an event cannot be empty.");
        }
        if (!args.contains("/from")) {
            throw new ShrekException("OOPS!!! An event needs a '/from' followed by the start date/time.");
        }
        String[] fromSplit = args.split("/from", 2);
        String description = fromSplit[0].trim();
        if (description.isEmpty()) {
            throw new ShrekException("OOPS!!! The description of an event cannot be empty.");
        }
        if (!fromSplit[1].contains("/to")) {
            throw new ShrekException("OOPS!!! An event needs a '/to' followed by the end date/time.");
        }
        String[] toSplit = fromSplit[1].split("/to", 2);
        String from = toSplit[0].trim();
        String to = toSplit[1].trim();
        if (from.isEmpty()) {
            throw new ShrekException("OOPS!!! The start date/time of an event cannot be empty.");
        }
        if (to.isEmpty()) {
            throw new ShrekException("OOPS!!! The end date/time of an event cannot be empty.");
        }
        return new Event(description, from, to);
    }
}
