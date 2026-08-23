package shrek.task;

import java.util.ArrayList;

/**
 * Contains the task list and operations to add, delete, and access tasks.
 */
public class TaskList {
    private final ArrayList<Task> tasks;

    /**
     * Creates a new, empty task list.
     */
    public TaskList() {
        this.tasks = new ArrayList<>();
    }

    /**
     * Creates a task list wrapping an already-loaded list of tasks.
     *
     * @param loadedTasks the tasks loaded from disk, or elsewhere.
     */
    public TaskList(ArrayList<Task> loadedTasks) {
        this.tasks = loadedTasks;
    }

    /**
     * Adds a task to the end of the list.
     *
     * @param task the task to add.
     */
    public void add(Task task) {
        tasks.add(task);
    }

    /**
     * Removes and returns the task at the given index.
     *
     * @param index the zero-based index of the task to remove.
     * @return the removed task.
     */
    public Task remove(int index) {
        return tasks.remove(index);
    }

    /**
     * Returns the task at the given index.
     *
     * @param index the zero-based index of the task.
     * @return the task at that index.
     */
    public Task get(int index) {
        return tasks.get(index);
    }

    /**
     * Returns the number of tasks in the list.
     *
     * @return the task count.
     */
    public int size() {
        return tasks.size();
    }

    /**
     * Returns the underlying list of all tasks.
     *
     * @return the full list of tasks.
     */
    public ArrayList<Task> getAll() {
        return tasks;
    }
}