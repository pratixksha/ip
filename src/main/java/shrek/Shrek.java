package shrek;

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

    public void run() {
        ui.showWelcome();
        while (true) {
            String input = ui.readCommand();
            String commandArgs = Parser.parseArgs(input);
            CommandType command = Parser.parseCommandType(input);

            try {
                switch (command) {
                    case BYE:
                        ui.showBye();
                        ui.close();
                        return;
                    case LIST:
                        ui.showTaskList(tasks);
                        break;
                    case MARK: {
                        int index = Parser.parseTaskIndex(commandArgs, "mark", tasks.size());
                        tasks.get(index).markAsDone();
                        storage.save(tasks.getAll());
                        ui.showTaskMarked(tasks.get(index));
                        break;
                    }
                    case UNMARK: {
                        int index = Parser.parseTaskIndex(commandArgs, "unmark", tasks.size());
                        tasks.get(index).markAsNotDone();
                        storage.save(tasks.getAll());
                        ui.showTaskUnmarked(tasks.get(index));
                        break;
                    }
                    case DELETE: {
                        int index = Parser.parseTaskIndex(commandArgs, "delete", tasks.size());
                        Task removed = tasks.remove(index);
                        storage.save(tasks.getAll());
                        ui.showTaskDeleted(removed, tasks.size());
                        break;
                    }
                    case TODO: {
                        Task newTask = Parser.parseTodo(commandArgs);
                        tasks.add(newTask);
                        storage.save(tasks.getAll());
                        ui.showTaskAdded(newTask, tasks.size());
                        break;
                    }
                    case DEADLINE: {
                        Task newTask = Parser.parseDeadline(commandArgs);
                        tasks.add(newTask);
                        storage.save(tasks.getAll());
                        ui.showTaskAdded(newTask, tasks.size());
                        break;
                    }
                    case EVENT: {
                        Task newTask = Parser.parseEvent(commandArgs);
                        tasks.add(newTask);
                        storage.save(tasks.getAll());
                        ui.showTaskAdded(newTask, tasks.size());
                        break;
                    }
                    case UNKNOWN:
                    default:
                        throw new ShrekException("OOPS!!! I'm sorry, but I don't know what that means :-(");
                }
            } catch (ShrekException e) {
                ui.showError(e.getMessage());
            }
        }
    }

    public static void main(String[] args) {
        new Shrek("./data/shrek.txt").run();
    }
}
