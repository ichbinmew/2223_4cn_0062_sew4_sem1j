package csv;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

public class CSVReader {
    public static void main(String[] args) {
        String[] result = split("\"ok\",ok\"\"ok, ok");
        for (String s : result) {
            System.out.println(s);
        }
    }

    public enum State {
        START {
            @Override
            public State process(char c, StringBuilder currentField) {
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
            @Override
            public State process(char c, StringBuilder currentField) {
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
            @Override
            public State process(char c, StringBuilder currentField) {
                if (c == '"') {
                    return QUOTE_STRING;
                } else {
                    currentField.append(c);
                    return INSIDE_STRING;
                }
            }
        },
        QUOTE_STRING {
            @Override
            public State process(char c, StringBuilder currentField) {
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
            @Override
            public State process(char c, StringBuilder currentField) {
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
            @Override
            public State process(char c, StringBuilder currentField) {
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
