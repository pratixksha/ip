package shrek.task;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

import org.junit.jupiter.api.Test;

public class TaskTest {

    @Test
    public void markAsDone_notDoneTask_marksDone() {
        Task task = new Todo("read book");
        assertFalse(task.isDone());
        task.markAsDone();
        assertTrue(task.isDone());
    }

    @Test
    public void markAsNotDone_doneTask_marksNotDone() {
        Task task = new Todo("read book");
        task.markAsDone();
        task.markAsNotDone();
        assertFalse(task.isDone());
    }

    @Test
    public void getStatusIcon_notDone_returnsSpace() {
        Task task = new Todo("read book");
        assertEquals(" ", task.getStatusIcon());
    }

    @Test
    public void getStatusIcon_done_returnsX() {
        Task task = new Todo("read book");
        task.markAsDone();
        assertEquals("X", task.getStatusIcon());
    }

    @Test
    public void toString_notDone_showsDescriptionWithEmptyBox() {
        Task task = new Todo("read book");
        assertEquals("[T][ ] read book", task.toString());
    }

    @Test
    public void toString_done_showsDescriptionWithCheckedBox() {
        Task task = new Todo("read book");
        task.markAsDone();
        assertEquals("[T][X] read book", task.toString());
    }
}
