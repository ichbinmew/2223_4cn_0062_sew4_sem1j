package firsttry;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class MainTest {

    @Test
    public void leicht() {
        // leicht
        assertEquals(0, Main.count(""));
        assertEquals(0, Main.count(" "));
        assertEquals(0, Main.count("  "));
    }
    @Test
    public void normal() {
        // normal
        assertEquals(1, Main.count("eins"));
        assertEquals(1, Main.count(" eins"));
        assertEquals(1, Main.count("eins "));
        assertEquals(1, Main.count(" eins "));
        assertEquals(1, Main.count(" eins  "));
        assertEquals(1, Main.count("  eins "));
        assertEquals(1, Main.count("  eins  "));

        assertEquals(1, Main.count("eins:"));
        assertEquals(1, Main.count(":eins"));
        assertEquals(1, Main.count(":eins:"));
        assertEquals(1, Main.count(" eins  "));
        assertEquals(1, Main.count(" eins : "));
        assertEquals(1, Main.count(": eins :"));
        assertEquals(3, Main.count("ein erster Text"));
        assertEquals(3, Main.count(" ein  erster   Text      "));
        assertEquals(3, Main.count("ein:erster.Text"));
    }
}