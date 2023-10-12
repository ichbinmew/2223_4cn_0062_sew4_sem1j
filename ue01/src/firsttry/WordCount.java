package firsttry;

/**
 * @author Jacob Schrott
 * Die Klasse `WordCount` bietet Methoden zur Verarbeitung von Texten, einschließlich einer Methode zum Zählen von Wörtern
 */
public class WordCount {

    /**
     * Zählt die Wörter in einem HTML-Text
     *
     * @param s Der HTML-Text
     * @return Die Anzahl der Wörter
     */
    public static long count(String s) {
        long count = 0;      // Zähler für die Anzahl der Wörter
        boolean isWord = false; // Gibt an, ob wir uns innerhalb eines Worts befinden

        // Wenn ein \" gefunden wird, entfernen wir es
        for (int i = 0; i < s.length(); i++) {
            if (s.charAt(i) == '"' && s.charAt(i - 1) == '\\') {
                s = s.replace(s.substring(i - 1, i + 1), "");
            }
        }

        // Wenn ein " gefunden wird, überprüfen wir, ob es in einem Tag ist
        for (int i = 0; i < s.length(); i++) {
            if (s.charAt(i) == '"') {
                int tagEnd = s.indexOf('"', i + 1);
                if (tagEnd != -1) {
                    String content = s.substring(i + 1, tagEnd);
                    if (content.contains("<") || content.contains(">")) {
                        s = s.replace(s.substring(i, tagEnd + 1), "\"\"");
                        i = tagEnd + 1;
                    }
                }
            }
        }

        int end = s.length() - 1;
        for (int i = 0; i < s.length(); i++) {
            // Wenn ein Buchstabe gefunden wird (möglicher Beginn oder Teil eines Worts)
            if (Character.isLetter(s.charAt(i)) && i != end) {
                isWord = true;
            } else if (s.charAt(i) == '<' && s.indexOf('>', i) > s.indexOf('<', i + 1) && s.indexOf('<', i + 1) != -1) {
                // Wenn ein "<" gefunden wird und das schließende ">" in einem späteren Tag ist
                if (isWord) {
                    count++; // Wenn wir uns in einem Wort befanden, erhöhen wir den Zähler
                    isWord = false;
                }
                int a = s.indexOf('>', i + 1);
                int b = s.indexOf('>', a + 1);
                if (b == -1){
                    break;
                }
                i = b;
            } else if (s.charAt(i) == '<' && s.indexOf('>', i) != -1) {
                // Wenn ein "<" gefunden wird und ein schließendes ">" vorhanden ist
                if (isWord) {
                    count++; // Wenn wir uns in einem Wort befanden, erhöhen wir den Zähler
                    isWord = false;
                }
                int tagEnd = s.indexOf('>', i);
                i = tagEnd;
            } else if (s.charAt(i) == '<' && s.indexOf('>', i) == -1) {
                // Wenn ein "<" gefunden wird, aber kein schließendes ">" im aktuellen Tag
                if (isWord) {
                    count++; // Wenn wir uns in einem Wort befanden, erhöhen wir den Zähler
                    isWord = false;
                }
                break;
            } else if (!Character.isLetter(s.charAt(i)) && isWord) {
                // Wenn kein Buchstabe gefunden wird, nachdem wir uns in einem Wort befunden haben
                count++; // Erhöhen Sie den Zähler
                isWord = false;
            } else if (Character.isLetter(s.charAt(i)) && i == end) {
                // Wenn ein Buchstabe am Ende des Texts gefunden wird
                count++; // Erhöhen Sie den Zähler
            } else if (s.charAt(i) == '<' && s.indexOf('>', i) == -1) {
                // Wenn ein "<" gefunden wird, aber kein schließendes ">" im aktuellen Tag
                i++;
            } else if (!Character.isLetter(s.charAt(i)) && isWord) {
                // Wenn kein Buchstabe gefunden wird, nachdem wir uns in einem Wort befunden haben
                count++; // Erhöhen Sie den Zähler
                isWord = false;
            } else if (Character.isLetter(s.charAt(i)) && i == end) {
                // Wenn ein Buchstabe am Ende des Texts gefunden wird
                count++; // Erhöhen Sie den Zähler
            }
        }
        return count;
    }
}
