package csv;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class CSVReaderTest {

    @Test
    void B1() {
        assertArrayEquals(new String[]{"ok", "ok"}, CSVReader.split("ok,ok"));
        assertArrayEquals(new String[]{"a", "b"}, CSVReader.split("a,b"));

    }
    @Test
    void B2() {
        assertArrayEquals(new String[]{"ok", "ok, ok", "okok"}, CSVReader.split("\"ok\",\"ok, ok\",ok\"ok\""));
    }
    @Test
    void B3() {
        assertArrayEquals(new String[]{"ok", "ok\"ok", "ok"}, CSVReader.split("\"ok\",\"ok\"\"ok\",ok"));
    }
    @Test
    void B4() {
        assertArrayEquals(new String[]{"ok", "okok", "ok"}, CSVReader.split("\"ok\",  okok,ok"));
    }
}