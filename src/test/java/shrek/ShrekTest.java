package shrek;

import static org.junit.jupiter.api.Assertions.assertEquals;

import java.nio.file.Path;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.io.TempDir;

public class ShrekTest {

    @TempDir
    Path tempDir;

    @Test
    public void tagAndUntag_multipleTagsUpdateTaskAtomically() {
        Shrek shrek = new Shrek(tempDir.resolve("shrek.txt").toString());
        shrek.getResponse("todo play game");

        assertEquals("Got it. I've added #fun and #school to this task:\n"
                + "  [T][ ] play game #fun #school",
                shrek.getResponse("tag 1 #fun #school"));
        assertEquals("OK, I've removed #fun and #school from this task:\n"
                + "  [T][ ] play game",
                shrek.getResponse("untag 1 #fun #school"));
    }

    @Test
    public void tag_invalidBatchDoesNotMutateTask() {
        Shrek shrek = new Shrek(tempDir.resolve("shrek.txt").toString());
        shrek.getResponse("todo play game #fun");

        assertEquals("OOPS!!! This task already has the tag #fun.",
                shrek.getResponse("tag 1 #school #fun"));
        assertEquals("Here are the tasks in your list:\n  1. [T][ ] play game #fun",
                shrek.getResponse("list"));
    }

    @Test
    public void find_matchesTags() {
        Shrek shrek = new Shrek(tempDir.resolve("shrek.txt").toString());
        shrek.getResponse("todo play game #weekend");

        assertEquals("Here are the matching tasks in your list:\n  1. [T][ ] play game #weekend",
                shrek.getResponse("find week"));
    }
}
