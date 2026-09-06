package shrek;

import java.util.ArrayList;

import shrek.parser.Parser;
import shrek.storage.Storage;
import shrek.task.Task;
import shrek.task.TaskList;
import shrek.ui.Ui;

/**
 * The entry point for the Shrek chatbot.
 */
public class Shrek {
    private final Storage storage;
    private TaskList tasks;
    private final Ui ui;
    private CommandType lastCommandType = CommandType.UNKNOWN;

    /**
     * Creates a Shrek chatbot using the default save file.
     *
     * <p>This constructor is used by the JavaFX launcher, which creates the
     * chatbot before displaying the application window.</p>
     */
    public Shrek() {
        this("./data/shrek.txt");
    }

    /**
     * Creates a new Shrek chatbot, loading saved tasks from the given file path.
     *
     * @param filePath the relative path to the data file.
     */
    public Shrek(String filePath) {
        ui = new Ui();
        storage = new Storage(filePath);
        try {
            tasks = new TaskList(storage.load());
        } catch (Exception e) {
            ui.showLoadingError();
            tasks = new TaskList();
        }
    }

    /**
     * Runs the main command loop until the user types "bye".
     */
    public void run() {
        ui.showWelcome();
        while (true) {
            String input = ui.readCommand();
            String response = getResponse(input);
            ui.showResponse(response);
            if (Parser.parseCommandType(input) == CommandType.BYE) {
                ui.close();
                return;
            }
        }
    }

    /**
     * Processes one command and returns the chatbot's response.
     *
     * <p>The JavaFX interface calls this method whenever the user presses
     * Send. Keeping command processing here means both the graphical and
     * console interfaces use the same task and storage logic.</p>
     *
     * @param input the command entered by the user.
     * @return the response to display to the user.
     */
    public String getResponse(String input) {
        String commandArgs = Parser.parseArgs(input);
        CommandType command = Parser.parseCommandType(input);
        lastCommandType = command;

        try {
            switch (command) {
                case BYE:
                    return "Bye. Hope to see you again soon!";
                case LIST:
                    return formatTaskList(tasks.getAll());
                case MARK:
                    return updateTaskStatus(commandArgs, true);
                case UNMARK:
                    return updateTaskStatus(commandArgs, false);
                case DELETE: {
                    int index = Parser.parseTaskIndex(commandArgs, "delete", tasks.size());
                    Task removed = tasks.remove(index);
                    storage.save(tasks.getAll());
                    return "Noted. I've removed this task:\n  " + removed
                            + "\nNow you have " + tasks.size() + " tasks in the list.";
                }
                case TODO: {
                    Task newTask = Parser.parseTodo(commandArgs);
                    return addTaskAndGetResponse(newTask);
                }
                case DEADLINE: {
                    Task newTask = Parser.parseDeadline(commandArgs);
                    return addTaskAndGetResponse(newTask);
                }
                case EVENT: {
                    Task newTask = Parser.parseEvent(commandArgs);
                    return addTaskAndGetResponse(newTask);
                }
                case FIND: {
                    if (commandArgs.isEmpty()) {
                        throw new ShrekException("OOPS!!! Please specify a keyword to search for.");
                    }
                    return formatTaskList(tasks.find(commandArgs), "Here are the matching tasks in your list:");
                }
                case UNKNOWN:
                default:
                    throw new ShrekException("OOPS!!! I'm sorry, but I don't know what that means :-(");
            }
        } catch (ShrekException e) {
            return e.getMessage();
        }
    }

    /**
     * Updates a task's completion status, persists the change, and creates a response.
     *
     * @param commandArgs the argument text following the command.
     * @param shouldBeDone whether the task should be marked as done.
     * @return the confirmation response.
     * @throws ShrekException if the task number is invalid.
     */
    private String updateTaskStatus(String commandArgs, boolean shouldBeDone) throws ShrekException {
        String command = shouldBeDone ? "mark" : "unmark";
        int index = Parser.parseTaskIndex(commandArgs, command, tasks.size());
        Task task = tasks.get(index);

        if (shouldBeDone) {
            task.markAsDone();
        } else {
            task.markAsNotDone();
        }

        storage.save(tasks.getAll());
        String response = shouldBeDone
                ? "Nice! I've marked this task as done:"
                : "OK, I've marked this task as not done yet:";
        return response + "\n  " + task;
    }

    /**
     * Adds a parsed task, persists the updated list, and creates its confirmation response.
     *
     * @param task the task to add.
     * @return the confirmation response for the newly added task.
     */
    private String addTaskAndGetResponse(Task task) {
        tasks.add(task);
        storage.save(tasks.getAll());
        return "Got it. I've added this task:\n  " + task
                + "\nNow you have " + tasks.size() + " tasks in the list.";
    }

    /**
     * Returns the command type processed most recently.
     *
     * <p>The JavaFX controller uses this to give task additions, marks, and
     * deletions distinct visual styles.</p>
     *
     * @return the most recently parsed command type.
     */
    public CommandType getLastCommandType() {
        return lastCommandType;
    }

    /**
     * Formats a task list for display in either user interface.
     *
     * @param taskList the tasks to display.
     * @return a readable task-list response.
     */
    private String formatTaskList(ArrayList<Task> taskList) {
        return formatTaskList(taskList, "Here are the tasks in your list:");
    }

    /**
     * Formats a task list with a custom heading.
     *
     * @param taskList the tasks to display.
     * @param heading the heading to display before the tasks.
     * @return a readable task-list response.
     */
    private String formatTaskList(ArrayList<Task> taskList, String heading) {
        if (taskList.isEmpty()) {
            return heading + "\n  (There are no matching tasks.)";
        }
        StringBuilder response = new StringBuilder(heading);
        for (int i = 0; i < taskList.size(); i++) {
            response.append("\n  ").append(i + 1).append(". ").append(taskList.get(i));
        }
        return response.toString();
    }

    /**
     * Starts the Shrek chatbot.
     *
     * @param args command line arguments (unused).
     */
    public static void main(String[] args) {
        new Shrek("./data/shrek.txt").run();
    }
}
