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

        String[] tasks = new String[100];
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
            } else {
                tasks[taskCount] = input;
                taskCount++;
                System.out.println("     added: " + input);
                System.out.println(separator);
            }
        }
        scanner.close();
    }
}