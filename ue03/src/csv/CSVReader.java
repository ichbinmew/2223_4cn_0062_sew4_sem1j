package csv;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

/**
 * Die Klasse `CSVReader` bietet Methoden zur Verarbeitung von CSV-Daten, einschließlich einer Methode zum Aufteilen von CSV-Zeilen in einzelne Felder
 */
public class CSVReader {
    public static void main(String[] args) {
        String[] result = split("\"ok\",ok\"\"ok, ok");
        for (String s : result) {
            System.out.println(s);
        }
    }
    /**
     * Ein Enumerator, der verschiedene Zustände für die CSV-Analyse darstellt
     */
    public enum State {
        START {
            /**
             * Ist der startende Zustand, der beim ersten Zeichen einer CSV-Zeile aufgerufen wird
             *
             * @param c Das aktuelle Zeichen
             * @param currentField Das aktuelle Feld, das gerade verarbeitet wird
             * @return Der nächste Zustand
             */
            @Override
            public State process(char c, StringBuilder currentField) {
                //System.out.println(c + ": START");
                if (c == ',') {
                    return START;
                }else if(Character.isWhitespace(c)){
                    return FUEHREND_WHITESPACE;
                } else if (c == '"') {
                    return INSIDE_STRING;
                } else {
                    currentField.append(c);
                    return INSIDE_FIELD;
                }
            }
        },
        INSIDE_FIELD {
            /**
             * Ist der Zustand, wenn das aktuelle Zeichen innerhalb eines Feldes ist
             *
             * @param c Das aktuelle Zeichen
             * @param currentField Das aktuelle Feld, das gerade verarbeitet wird
             * @return Der nächste Zustand
             */
            @Override
            public State process(char c, StringBuilder currentField) {
                //System.out.println(c + ": INSIDE_FIELD");
                if (c == ',') {
                    return START;
                } else if (c == '"') {
                    return QUOTE_FIELD;
                } else {
                    currentField.append(c);
                    return INSIDE_FIELD;
                }
            }
        },
        INSIDE_STRING {
            /**
             * Ist der Zustand, wenn das aktuelle Zeichen in einem String ist
             *
             * @param c Das aktuelle Zeichen
             * @param currentField Das aktuelle Feld, das gerade verarbeitet wird
             * @return Der nächste Zustand
             */
            @Override
            public State process(char c, StringBuilder currentField) {
                //System.out.println(c + ": INSIDE_STRING");
                if (c == '"') {
                    return QUOTE_STRING;
                } else {
                    currentField.append(c);
                    return INSIDE_STRING;
                }
            }
        },
        QUOTE_STRING {
            /**
             * Ist der Zustand, wenn das aktuelle Zeichen in einem String ist und ein Anführungszeichen gefunden wurde
             *
             * @param c Das aktuelle Zeichen
             * @param currentField Das aktuelle Feld, das gerade verarbeitet wird
             * @return Der nächste Zustand
             */
            @Override
            public State process(char c, StringBuilder currentField) {
                //System.out.println(c + ": QUOTE_STRING");
                if (c == ',') {
                    return START;
                } else if (c == '"') {
                    currentField.append('"');
                    return INSIDE_STRING;
                } else {
                    currentField.append(c);
                    return INSIDE_FIELD;
                }
            }
        },
        FUEHREND_WHITESPACE {
            /**
             * Ist der Zustand, wenn das Aktuelle Zeichen ein Whitespace ist und das Feld noch nicht begonnen hat
             *
             * @param c Das aktuelle Zeichen
             * @param currentField Das aktuelle Feld, das gerade verarbeitet wird
             * @return Der nächste Zustand
             */
            @Override
            public State process(char c, StringBuilder currentField) {
                //System.out.println(c + ": FUEREND_WHITESPACE");
                if (c == ',') {
                    return START;
                } else if (c == '"') {
                    return INSIDE_STRING;
                } else if (Character.isWhitespace(c)) {
                    return FUEHREND_WHITESPACE;
                } else {
                    currentField.append(c);
                    return INSIDE_FIELD;
                }
            }
        },
        QUOTE_FIELD {
            /**
             * Ist Zustand, wenn das aktuelle Zeichen in einem Feld ist und ein Anführungszeichen gefunden wurde
             *
             * @param c Das aktuelle Zeichen
             * @param currentField Das aktuelle Feld, das gerade verarbeitet wird
             * @return Der nächste Zustand
             */
            @Override
            public State process(char c, StringBuilder currentField) {
                //System.out.println(c + ": QUOTE_FIELD");
                if (c == ',') {
                    return START;
                } else if (c == '"') {
                    currentField.append('"');
                    return INSIDE_FIELD;
                } else {
                    currentField.append(c);
                    return INSIDE_FIELD;
                }
            }
        };

        public abstract State process(char c, StringBuilder currentField);
    }

    /**
     * Teilt eine CSV-Zeile in einzelne Felder auf und gibt sie als String-Array zurück
     *
     * @param input Die CSV-Zeile, die aufgeteilt werden soll
     * @return Ein String-Array mit den einzelnen CSV-Feldern
     */
    public static String[] split(String input) {
        List<String> results = new ArrayList<>();
        StringBuilder currentField = new StringBuilder();
        State currentState = State.START;

        for (char c : input.toCharArray()) {
            currentState = currentState.process(c, currentField);

            if (Objects.equals(currentState, State.START)) {
                results.add(currentField.toString());
                currentField.setLength(0);
            }
        }

        results.add(currentField.toString());
        currentField.setLength(0);
        return results.toArray(new String[0]);
    }
}
