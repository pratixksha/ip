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

        Task[] tasks = new Task[100];
        int taskCount = 0;

        Scanner scanner = new Scanner(System.in);
        while (true) {
            String input = scanner.nextLine();
            if (input.equals("bye")) {
                System.out.println("     Bye. Hope to see you again soon!");
                System.out.println(separator);
                break;
            } else if (input.equals("list")) {
                for (int i = 0; i < taskCount; i++) {
                    System.out.println("     " + (i + 1) + ". " + tasks[i]);
                }
                System.out.println(separator);
            } else if (input.startsWith("mark ")) {
                int index = Integer.parseInt(input.substring(5).trim()) - 1;
                tasks[index].markAsDone();
                System.out.println("     Nice! I've marked this task as done:");
                System.out.println("       " + tasks[index]);
                System.out.println(separator);
            } else if (input.startsWith("unmark ")) {
                int index = Integer.parseInt(input.substring(7).trim()) - 1;
                tasks[index].markAsNotDone();
                System.out.println("     OK, I've marked this task as not done yet:");
                System.out.println("       " + tasks[index]);
                System.out.println(separator);
            } else {
                tasks[taskCount] = new Task(input);
                taskCount++;
                System.out.println("     added: " + input);
                System.out.println(separator);
            }
        }
        scanner.close();
    }
}