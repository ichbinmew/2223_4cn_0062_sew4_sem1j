package firsttry;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

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
    @Test
    void mitHtml() {
        // mit html
        assertEquals(1, WordCount.count(" eins  <html> "));
        assertEquals(1, WordCount.count(" eins  < html> "));
        assertEquals(1, WordCount.count(" eins  <html > "));
        assertEquals(1, WordCount.count(" eins  < html > "));
        assertEquals(4, WordCount.count(" eins <html> zwei<html>drei <html> vier"));

        assertEquals(2, WordCount.count(" eins <html> zwei "));
        assertEquals(2, WordCount.count(" eins <html>zwei "));
        assertEquals(2, WordCount.count(" eins<html> zwei "));
        assertEquals(2, WordCount.count(" eins<html>zwei "));
        assertEquals(2, WordCount.count(" eins<img alt=\"xxx\" > zwei"));
        assertEquals(2, WordCount.count(" eins<img alt=\"xxx yyy\" > zwei"));

        assertEquals(2, WordCount.count(" eins \"zwei\" "));
        assertEquals(2, WordCount.count(" eins\"zwei\" "));
        assertEquals(2, WordCount.count(" eins \"zwei\""));
        assertEquals(3, WordCount.count(" eins \"zwei\"drei"));
        assertEquals(3, WordCount.count(" eins \"zwei\" drei"));
    }
    @Test
    void htmlTrickreich() {
        // html - trickreich
        assertEquals(1, WordCount.count(" eins<html"));

        assertEquals(2, WordCount.count(" eins<img alt=\"<bild>\" > zwei"));
        assertEquals(2, WordCount.count(" eins<img alt=\"bild>\" > zwei"));
        assertEquals(2, WordCount.count(" eins<img alt=\"<bild>\" keinwort> zwei"));
        assertEquals(2, WordCount.count(" eins<img alt=\"<bild>\" src=\"bild.png\" >zwei"));
        assertEquals(2, WordCount.count(" eins<img alt=\"<bild\" keinwort>zwei"));

        assertEquals(1, WordCount.count(" eins<img alt=\"<bild\" keinwort"));
        assertEquals(2, WordCount.count(" eins<img alt=\"<bild\" keinwort> zwei"));
        assertEquals(1, WordCount.count(" eins<img alt=\"<bild keinwort> keinwort"));
        assertEquals(2, WordCount.count(" eins<img alt=\"<bild keinwort keinwort\">zwei"));
        assertEquals(2, WordCount.count(" eins<img alt=\"<bild keinwort< keinwort\">zwei"));
    }
}