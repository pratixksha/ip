package shrek.ui;

import java.util.Scanner;

import shrek.task.Task;
import shrek.task.TaskList;

/**
 * Deals with interactions with the user: reading input and printing output.
 */
public class Ui {
    private static final String SEPARATOR =
            "    ____________________________________________________________";
    private static final String BANNER = "     ____  _              _    \n"
            + "    / ___|| |__  _ __ ___| | __\n"
            + "    \\___ \\| '_ \\| '__/ _ \\ |/ /\n"
            + "     ___) | | | | | |  __/   < \n"
            + "    |____/|_| |_|_|  \\___|_|\\_\\\n";

    private final Scanner scanner;

    public Ui() {
        this.scanner = new Scanner(System.in);
    }

    public void showWelcome() {
        System.out.println(SEPARATOR);
        System.out.println(BANNER);
        System.out.println("     Hello! I'm Shrek.");
        System.out.println("     What can I do for you?");
        System.out.println(SEPARATOR);
    }

    public void showBye() {
        System.out.println("     Bye. Hope to see you again soon!");
        System.out.println(SEPARATOR);
    }

    public void showLine() {
        System.out.println(SEPARATOR);
    }

    public void showError(String message) {
        System.out.println("     " + message);
        System.out.println(SEPARATOR);
    }

    public void showLoadingError() {
        System.out.println("     Warning: could not load saved tasks. Starting with an empty list.");
        System.out.println(SEPARATOR);
    }

    public void showTaskList(TaskList tasks) {
        System.out.println("     Here are the tasks in your list:");
        for (int i = 0; i < tasks.size(); i++) {
            System.out.println("     " + (i + 1) + "." + tasks.get(i));
        }
        System.out.println(SEPARATOR);
    }

    public void showTaskAdded(Task task, int taskCount) {
        System.out.println("     Got it. I've added this task:");
        System.out.println("       " + task);
        System.out.println("     Now you have " + taskCount + " tasks in the list.");
        System.out.println(SEPARATOR);
    }

    public void showTaskMarked(Task task) {
        System.out.println("     Nice! I've marked this task as done:");
        System.out.println("       " + task);
        System.out.println(SEPARATOR);
    }

    public void showTaskUnmarked(Task task) {
        System.out.println("     OK, I've marked this task as not done yet:");
        System.out.println("       " + task);
        System.out.println(SEPARATOR);
    }

    public void showTaskDeleted(Task task, int taskCount) {
        System.out.println("     Noted. I've removed this task:");
        System.out.println("       " + task);
        System.out.println("     Now you have " + taskCount + " tasks in the list.");
        System.out.println(SEPARATOR);
    }

    public String readCommand() {
        return scanner.nextLine();
    }

    public void close() {
        scanner.close();
    }
}