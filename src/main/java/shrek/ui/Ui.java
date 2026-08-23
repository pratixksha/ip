package shrek.ui;

import java.util.Scanner;

import shrek.task.Task;
import shrek.task.TaskList;

/**
 * Deals with interactions with the user: reading input and printing output.
 */
public class Ui {
    private static final String SEPARATOR = "    ____________________________________________________________";
    private static final String BANNER = "     ____  _              _    \n"
            + "    / ___|| |__  _ __ ___| | __\n"
            + "    \\___ \\| '_ \\| '__/ _ \\ |/ /\n"
            + "     ___) | | | | | |  __/   < \n"
            + "    |____/|_| |_|_|  \\___|_|\\_\\\n";

    private final Scanner scanner;

    /**
     * Creates a new Ui with a Scanner reading from standard input.
     */
    public Ui() {
        this.scanner = new Scanner(System.in);
    }

    /**
     * Prints the welcome banner and greeting.
     */
    public void showWelcome() {
        System.out.println(SEPARATOR);
        System.out.println(BANNER);
        System.out.println("     Hello! I'm Shrek.");
        System.out.println("     What can I do for you?");
        System.out.println(SEPARATOR);
    }

    /**
     * Prints the farewell message.
     */
    public void showBye() {
        System.out.println("     Bye. Hope to see you again soon!");
        System.out.println(SEPARATOR);
    }

    /**
     * Prints a horizontal separator line.
     */
    public void showLine() {
        System.out.println(SEPARATOR);
    }

    /**
     * Prints an error message.
     *
     * @param message the error message to display.
     */
    public void showError(String message) {
        System.out.println("     " + message);
        System.out.println(SEPARATOR);
    }

    /**
     * Prints a warning that saved tasks could not be loaded.
     */
    public void showLoadingError() {
        System.out.println("     Warning: could not load saved tasks. Starting with an empty list.");
        System.out.println(SEPARATOR);
    }

    /**
     * Prints all tasks in the given task list.
     *
     * @param tasks the task list to display.
     */
    public void showTaskList(TaskList tasks) {
        System.out.println("     Here are the tasks in your list:");
        for (int i = 0; i < tasks.size(); i++) {
            System.out.println("     " + (i + 1) + "." + tasks.get(i));
        }
        System.out.println(SEPARATOR);
    }

    /**
     * Prints a confirmation that a task was added.
     *
     * @param task the task that was added.
     * @param taskCount the new total number of tasks.
     */
    public void showTaskAdded(Task task, int taskCount) {
        System.out.println("     Got it. I've added this task:");
        System.out.println("       " + task);
        System.out.println("     Now you have " + taskCount + " tasks in the list.");
        System.out.println(SEPARATOR);
    }

    /**
     * Prints a confirmation that a task was marked as done.
     *
     * @param task the task that was marked.
     */
    public void showTaskMarked(Task task) {
        System.out.println("     Nice! I've marked this task as done:");
        System.out.println("       " + task);
        System.out.println(SEPARATOR);
    }

    /**
     * Prints a confirmation that a task was marked as not done.
     *
     * @param task the task that was unmarked.
     */
    public void showTaskUnmarked(Task task) {
        System.out.println("     OK, I've marked this task as not done yet:");
        System.out.println("       " + task);
        System.out.println(SEPARATOR);
    }

    /**
     * Prints a confirmation that a task was deleted.
     *
     * @param task the task that was removed.
     * @param taskCount the new total number of tasks.
     */
    public void showTaskDeleted(Task task, int taskCount) {
        System.out.println("     Noted. I've removed this task:");
        System.out.println("       " + task);
        System.out.println("     Now you have " + taskCount + " tasks in the list.");
        System.out.println(SEPARATOR);
    }

    /**
     * Reads one line of user input.
     *
     * @return the raw input line.
     */
    public String readCommand() {
        return scanner.nextLine();
    }

    /**
     * Closes the input scanner.
     */
    public void close() {
        scanner.close();
    }
}
