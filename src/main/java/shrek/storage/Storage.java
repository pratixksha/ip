package shrek.storage;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.time.LocalDate;
import java.time.format.DateTimeParseException;
import java.util.ArrayList;
import java.util.List;

import shrek.task.Deadline;
import shrek.task.Event;
import shrek.task.Task;
import shrek.task.Todo;

/**
 * Handles loading tasks from and saving tasks to the hard disk.
 */
public class Storage {
    private final Path filePath;

    /**
     * Creates a new Storage pointing at the given relative file path.
     *
     * @param relativePath the relative path to the data file.
     */
    public Storage(String relativePath) {
        this.filePath = Paths.get(relativePath);
    }

    /**
     * Loads tasks from the data file. If the file or its parent folder
     * doesn't exist, returns an empty list instead of failing.
     *
     * @return the list of tasks loaded from disk.
     */
    public ArrayList<Task> load() {
        ArrayList<Task> tasks = new ArrayList<>();
        File file = filePath.toFile();

        if (!file.exists()) {
            return tasks;
        }

        try {
            List<String> lines = Files.readAllLines(filePath);
            for (String line : lines) {
                Task task = parseLine(line);
                if (task != null) {
                    tasks.add(task);
                }
            }
        } catch (IOException e) {
            System.out.println("     Warning: could not read the data file. Starting with an empty list.");
        }

        return tasks;
    }

    /**
     * Parses a single saved line into a Task. Returns null if the line
     * is corrupted or malformed, so it can be skipped rather than crash.
     *
     * @param line the raw line read from the data file.
     * @return the parsed task, or null if the line couldn't be parsed.
     */
    private Task parseLine(String line) {
        try {
            String[] parts = line.split(" \\| ");
            String type = parts[0].trim();
            boolean isDone = parts[1].trim().equals("1");
            String description = parts[2].trim();

            Task task;
            switch (type) {
                case "T":
                    task = new Todo(description);
                    break;
                case "D":
                    LocalDate by = LocalDate.parse(parts[3].trim());
                    task = new Deadline(description, by);
                    break;
                case "E":
                    String from = parts[3].trim();
                    String to = parts[4].trim();
                    task = new Event(description, from, to);
                    break;
                default:
                    return null;
            }

            if (isDone) {
                task.markAsDone();
            }
            return task;
        } catch (ArrayIndexOutOfBoundsException | DateTimeParseException e) {
            // Corrupted or malformed line — skip it rather than crash.
            return null;
        }
    }

    /**
     * Saves the given list of tasks to the data file, creating the
     * parent folder first if it doesn't exist.
     *
     * @param tasks the tasks to save.
     */
    public void save(ArrayList<Task> tasks) {
        try {
            File parentDir = filePath.toFile().getParentFile();
            if (parentDir != null && !parentDir.exists()) {
                parentDir.mkdirs();
            }

            FileWriter writer = new FileWriter(filePath.toFile());
            for (Task task : tasks) {
                writer.write(task.toSaveFormat() + System.lineSeparator());
            }
            writer.close();
        } catch (IOException e) {
            System.out.println("     Warning: could not save tasks to disk.");
        }
    }
}
