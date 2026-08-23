package shrek.task;

/**
 * Represents a task with no date or time attached to it.
 */
public class Todo extends Task {

    /**
     * Creates a new todo task.
     *
     * @param description the text describing the task.
     */
    public Todo(String description) {
        super(description);
    }

    @Override
    public String toSaveFormat() {
        return "T | " + (isDone ? "1" : "0") + " | " + description;
    }

    @Override
    public String toString() {
        return "[T]" + super.toString();
    }
}