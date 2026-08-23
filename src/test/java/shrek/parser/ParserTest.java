package shrek.parser;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

import org.junit.jupiter.api.Test;

import shrek.ShrekException;
import shrek.task.Deadline;
import shrek.task.Todo;

public class ParserTest {

    @Test
    public void parseTodo_validDescription_createsTodo() throws ShrekException {
        Todo todo = Parser.parseTodo("read book");
        assertEquals("read book", todo.getDescription());
    }

    @Test
    public void parseTodo_emptyDescription_throwsShrekException() {
        assertThrows(ShrekException.class, () -> Parser.parseTodo(""));
    }

    @Test
    public void parseDeadline_validInput_createsDeadlineWithCorrectDate() throws ShrekException {
        Deadline deadline = Parser.parseDeadline("return book /by 2019-10-15");
        assertEquals("[D][ ] return book (by: Oct 15 2019)", deadline.toString());
    }

    @Test
    public void parseDeadline_missingByKeyword_throwsShrekException() {
        assertThrows(ShrekException.class, () -> Parser.parseDeadline("return book"));
    }

    @Test
    public void parseDeadline_invalidDateFormat_throwsShrekException() {
        assertThrows(ShrekException.class, () -> Parser.parseDeadline("return book /by tomorrow"));
    }

    @Test
    public void parseTaskIndex_validNumber_returnsZeroBasedIndex() throws ShrekException {
        int index = Parser.parseTaskIndex("2", "mark", 5);
        assertEquals(1, index);
    }

    @Test
    public void parseTaskIndex_outOfRange_throwsShrekException() {
        assertThrows(ShrekException.class, () -> Parser.parseTaskIndex("99", "mark", 5));
    }

    @Test
    public void parseTaskIndex_nonNumeric_throwsShrekException() {
        assertThrows(ShrekException.class, () -> Parser.parseTaskIndex("abc", "mark", 5));
    }
}