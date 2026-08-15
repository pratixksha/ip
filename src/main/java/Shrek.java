import java.util.Scanner;

public class Shrek {
    public static void main(String[] args) {
        String separator = "____________________________________________________________";
        String banner = " ____  _              _    \n"
                + "/ ___|| |__  _ __ ___| | __\n"
                + "\\___ \\| '_ \\| '__/ _ \\ |/ /\n"
                + " ___) | | | | | |  __/   < \n"
                + "|____/|_| |_|_|  \\___|_|\\_\\\n";

        System.out.println(separator);
        System.out.println(banner);
        System.out.println("Hello! I'm Shrek.");
        System.out.println("What can I do for you?");
        System.out.println(separator);
        
        Scanner scanner = new Scanner(System.in);
        while (true) {
            String input = scanner.nextLine();
            if (input.equals("bye")) {
                System.out.println("Bye. Hope to see you again soon!");
                System.out.println(separator);
                break;
            } else {
                System.out.println(input);
                System.out.println(separator);
            }
        }
        scanner.close();
    }
}