package secondtry;

import secondtry.WordCount;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;

class WordCountTest {

    @Test
    void leicht() {
        // leicht
        assertEquals(0, WordCount.count(""));
        assertEquals(0, WordCount.count(" "));
        assertEquals(0, WordCount.count("  "));
    }
    @Test
    void normal() {
        // normal
        assertEquals(1, WordCount.count("eins"));
        assertEquals(1, WordCount.count(" eins"));
        assertEquals(1, WordCount.count("eins "));
        assertEquals(1, WordCount.count(" eins "));
        assertEquals(1, WordCount.count(" eins  "));
        assertEquals(1, WordCount.count("  eins "));
        assertEquals(1, WordCount.count("  eins  "));

        assertEquals(1, WordCount.count("eins:"));
        assertEquals(1, WordCount.count(":eins"));
        assertEquals(1, WordCount.count(":eins:"));
        assertEquals(1, WordCount.count(" eins  "));
        assertEquals(1, WordCount.count(" eins : "));
        assertEquals(1, WordCount.count(": eins :"));
        assertEquals(3, WordCount.count("ein erster Text"));
        assertEquals(3, WordCount.count(" ein  erster   Text      "));
        assertEquals(3, WordCount.count("ein:erster.Text"));
    }
    @Test
    void vielleichtFalsch() {
        // vielleicht falsch
        assertEquals(1, WordCount.count("a"));
        assertEquals(1, WordCount.count(" a"));
        assertEquals(1, WordCount.count("a "));
        assertEquals(1, WordCount.count(" a "));
    }
}