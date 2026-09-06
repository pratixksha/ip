package shrek.task;

/**
 * Represents a task that starts and ends at specific date/times.
 */
public class Event extends Task {

    protected String from;
    protected String to;

    /**
     * Creates a new event task.
     *
     * @param description the text describing the task.
     * @param from        the start date/time of the event.
     * @param to          the end date/time of the event.
     */
    public Event(String description, String from, String to) {
        super(description);
        // Parser supplies both times, and both are required by the save format.
        assert from != null && !from.isBlank() : "An event must have a start time.";
        assert to != null && !to.isBlank() : "An event must have an end time.";
        this.from = from;
        this.to = to;
    }

    @Override
    public String toSaveFormat() {
        return "E | " + (isDone ? "1" : "0") + " | " + description + " | " + from + " | " + to;
    }

    @Override
    public String toString() {
        return "[E]" + super.toString() + " (from: " + from + " to: " + to + ")";
    }
}
