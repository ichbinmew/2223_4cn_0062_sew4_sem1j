package secondtry;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;

/**
 * Die Klasse `WordCount` bietet Methoden zur Verarbeitung von HTML-Daten, einschließlich einer Methode zum Zählen von Wörtern
 */
public class WordCount {
    /**
     * Die Main-Methode zum Testen der HTML-Verarbeitung
     *
     * @param args Die Kommandozeilenargumente
     */
    public static void main(String[] args) {
        int count1 = 0;
        try {
            // Öffnen und lesen der Datei "crsto12.html"
            FileReader fileReader = new FileReader("C:\\Users\\jacob\\OneDrive - HTL Wien 3 Rennweg\\SJ2324\\SEW\\sew4_sem1j\\ue02\\src\\crsto12.html");
            BufferedReader bufferedReader = new BufferedReader(fileReader);
            String line;
            while ((line = bufferedReader.readLine()) != null) {
                count1 += count(line); // Verarbeiten der Zeile und Zählen der Wörter
            }
            bufferedReader.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
        System.out.println(count1);
        System.out.println(count(" eins<img alt=\"<bild\" keinwort>zwei")); // Testen der count-Methode mit einem Beispieltext
    }

    /**
     * Ein Enumerator, der verschiedene Zustände für die HTML-Analyse darstellt
     */
    public enum State {
        /**
         * Ist der Startzustand oder der Zustand, wenn ein Wort beendet wurde
         */
        NOWORD,
        /**
         * Ist der Zustand, wenn das aktuelle Zeichen in einem Wort ist
         */
        INWORD,
        /**
         * Ist der Zustand, wenn das aktuelle Zeichen in einem Tag ist
         */
        INTAG,
        /**
         * Ist der Zustand, wenn das aktuelle Zeichen in einem String ist
         */
        VERSCHACHTELT,
        /**
         * Ist der Zustand, wenn das aktuelle Zeichen in einem String in einem Tag ist
         */
        INSTRINGTAG,
        /**
         * Ist der Zustand, wenn das aktuelle Zeichen ein Backslash ist
         */
        BACKSLASH
    }

    /**
     * Zählt die Wörter in einem HTML-Text
     *
     * @param text Der HTML-Text
     * @return Die Anzahl der Wörter
     */
    public static int count(String text) {
        text = text.replace("\\\"", ""); // Wenn ein \\\" gefunden wird ist es das gleiche wie \" und das bedeutet für unseren count nichts -> entfernen
        if (text.isEmpty()) {
            return 0; // Wenn der Text leer ist, gibt es keine Wörter
        }

        State state = State.NOWORD; // Der Startzustand
        int counter = 0; // Der Zähler für die Wörter im Text

        for (int i = 0; i < text.length(); i++) {
            switch (state) {
                case INSTRINGTAG -> {
                    // Wenn wir uns in einem String in einem Tag befinden
                    switch (text.charAt(i)) {
                        case '<' -> i++; // Wenn ein "<" gefunden wird, überspringen
                        case '"' -> state = State.INTAG; // Wenn ein Anführungszeichen gefunden wird, wechseln in den Tag-Zustand
                    }
                }
                case VERSCHACHTELT -> {
                    // Wenn wir uns in einem verschachtelten Tag-Zustand befinden
                    switch (text.charAt(i)) {
                        case '>' -> state = State.INTAG; // Wenn ein ">" gefunden wird, wechseln in den Tag-Zustand
                    }
                }
                case INTAG -> {
                    // Wenn wir uns im Tag-Zustand befinden
                    switch (text.charAt(i)) {
                        case '<' -> state = State.VERSCHACHTELT; // Wenn ein "<" gefunden wird, wechseln in den verschachtelten Tag-Zustand
                        case '>' -> state = State.NOWORD; // Wenn ein ">" gefunden wird, wechseln in den Startzustand
                        case '"' -> state = State.INSTRINGTAG; // Wenn ein Anführungszeichen gefunden wird, wechseln in den String-in-Tag-Zustand
                        case '\\' -> state = State.BACKSLASH; // Wenn ein Backslash gefunden wird, wechseln in den Backslash-Zustand
                    }
                }
                case NOWORD -> {
                    // Wenn wir uns im Startzustand oder nach einem beendeten Wort befinden
                    if (text.charAt(i) == '<') {
                        state = State.INTAG; // Wenn ein "<" gefunden wird, wechseln in den Tag-Zustand
                    } else {
                        if (Character.isAlphabetic(text.charAt(i))) {
                            state = State.INWORD; // Wenn ein Buchstabe gefunden wird, wechseln in den Wort-Zustand
                            counter++; // Erhöhen des Zählers, da ein neues Wort beginnt
                        }
                    }
                }
                case INWORD -> {
                    // Wenn wir uns im Wort-Zustand befinden
                    switch (text.charAt(i)) {
                        case '<' -> state = State.INTAG; // Wenn ein "<" gefunden wird, wechseln in den Tag-Zustand
                        case '>' -> state = State.NOWORD; // Wenn ein ">" gefunden wird, wechseln in den Startzustand
                        default -> {
                            if (!Character.isAlphabetic(text.charAt(i))) {
                                state = State.NOWORD; // Wenn kein Buchstabe gefunden wird, wechseln in den Startzustand
                            }
                        }
                    }
                }
            }
        }
        return counter; // Die Anzahl der gezählten Wörter im HTML-Text
    }
}
