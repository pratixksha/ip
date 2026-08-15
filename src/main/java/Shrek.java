import java.util.ArrayList;
import java.util.Scanner;

/**
 * The entry point for the Shrek chatbot.
 */

public class Shrek {
    public static void main(String[] args) {
        String separator = "    ____________________________________________________________";
        String banner = "     ____  _              _    \n"
                + "    / ___|| |__  _ __ ___| | __\n"
                + "    \\___ \\| '_ \\| '__/ _ \\ |/ /\n"
                + "     ___) | | | | | |  __/   < \n"
                + "    |____/|_| |_|_|  \\___|_|\\_\\\n";
        
        System.out.println(separator);
        System.out.println(banner);
        System.out.println("     Hello! I'm Shrek.");
        System.out.println("     What can I do for you?");
        System.out.println(separator);

        ArrayList<Task> tasks = new ArrayList<>();

        Scanner scanner = new Scanner(System.in);
        while (true) {
            String input = scanner.nextLine();
            String commandWord = input.split(" ", 2)[0];
            String commandArgs = input.length() > commandWord.length() ? input.substring(commandWord.length()).trim() : "";
            CommandType command = parseCommand(commandWord);

            try {
                switch (command) {
                case BYE:
                    System.out.println("     Bye. Hope to see you again soon!");
                    System.out.println(separator);
                    scanner.close();
                    return;
                case LIST:
                    System.out.println("     Here are the tasks in your list:");
                    for (int i = 0; i < tasks.size(); i++) {
                        System.out.println("     " + (i + 1) + "." + tasks.get(i));
                    }
                    System.out.println(separator);
                    break;
                case MARK: {
                    int index = parseTaskIndex(commandArgs, "mark", tasks.size());
                    tasks.get(index).markAsDone();
                    System.out.println("     Nice! I've marked this task as done:");
                    System.out.println("       " + tasks.get(index));
                    System.out.println(separator);
                    break;
                }
                case UNMARK: {
                    int index = parseTaskIndex(commandArgs, "unmark", tasks.size());
                    tasks.get(index).markAsNotDone();
                    System.out.println("     OK, I've marked this task as not done yet:");
                    System.out.println("       " + tasks.get(index));
                    System.out.println(separator);
                    break;
                }
                case DELETE: {
                    int index = parseTaskIndex(commandArgs, "delete", tasks.size());
                    Task removed = tasks.remove(index);
                    System.out.println("     Noted. I've removed this task:");
                    System.out.println("       " + removed);
                    System.out.println("     Now you have " + tasks.size() + " tasks in the list.");
                    System.out.println(separator);
                    break;
                }
                case TODO: {
                    if (commandArgs.isEmpty()) {
                        throw new ShrekException("OOPS!!! The description of a todo cannot be empty.");
                    }
                    Task newTask = new Todo(commandArgs);
                    tasks.add(newTask);
                    printAddedMessage(newTask, tasks.size(), separator);
                    break;
                }
                case DEADLINE: {
                    if (commandArgs.isEmpty()) {
                        throw new ShrekException("OOPS!!! The description of a deadline cannot be empty.");
                    }
                    if (!commandArgs.contains("/by")) {
                        throw new ShrekException(
                                "OOPS!!! A deadline needs a '/by' followed by the due date/time.");
                    }
                    String[] parts = commandArgs.split("/by", 2);
                    String description = parts[0].trim();
                    String by = parts[1].trim();
                    if (description.isEmpty()) {
                        throw new ShrekException("OOPS!!! The description of a deadline cannot be empty.");
                    }
                    if (by.isEmpty()) {
                        throw new ShrekException("OOPS!!! The due date/time of a deadline cannot be empty.");
                    }
                    Task newTask = new Deadline(description, by);
                    tasks.add(newTask);
                    printAddedMessage(newTask, tasks.size(), separator);
                    break;
                }
                case EVENT: {
                    if (commandArgs.isEmpty()) {
                        throw new ShrekException("OOPS!!! The description of an event cannot be empty.");
                    }
                    if (!commandArgs.contains("/from")) {
                        throw new ShrekException(
                                "OOPS!!! An event needs a '/from' followed by the start date/time.");
                    }
                    String[] fromSplit = commandArgs.split("/from", 2);
                    String description = fromSplit[0].trim();
                    if (description.isEmpty()) {
                        throw new ShrekException("OOPS!!! The description of an event cannot be empty.");
                    }
                    if (!fromSplit[1].contains("/to")) {
                        throw new ShrekException(
                                "OOPS!!! An event needs a '/to' followed by the end date/time.");
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
                    Task newTask = new Event(description, from, to);
                    tasks.add(newTask);
                    printAddedMessage(newTask, tasks.size(), separator);
                    break;
                }
                case UNKNOWN:
                default:
                    throw new ShrekException("OOPS!!! I'm sorry, but I don't know what that means :-(");
                }
            } catch (ShrekException e) {
                System.out.println("     " + e.getMessage());
                System.out.println(separator);
            }
        }
    }

    private static CommandType parseCommand(String commandWord) {
        try {
            return CommandType.valueOf(commandWord.toUpperCase());
        } catch (IllegalArgumentException e) {
            return CommandType.UNKNOWN;
        }
    }

    private static int parseTaskIndex(String args, String command, int taskCount) throws ShrekException {
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

    private static void printAddedMessage(Task task, int taskCount, String separator) {
        System.out.println("     Got it. I've added this task:");
        System.out.println("       " + task);
        System.out.println("     Now you have " + taskCount + " tasks in the list.");
        System.out.println(separator);
    }
}