package csv;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

public class CSVReader {
    public static void main(String[] args) {
        String[] result = split("\"ok\",\"ok\"\"ok\",ok");
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
                    return INSIDE_STRING;
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
                    return QUOTE;
                } else {
                    currentField.append(c);
                    return INSIDE_STRING;
                }
            }
        },
        QUOTE {
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
        };

        public abstract State process(char c, StringBuilder currentField);
    }

    public static String[] split(String input) {
        List<String> results = new ArrayList<>();
        StringBuilder currentField = new StringBuilder();
        State currentState = State.START;

        for (char c : input.toCharArray()) {
            currentState = currentState.process(c, currentField);

            if (Objects.requireNonNull(currentState) == State.START) {
                addField(results, currentField);
            }
        }

        addField(results, currentField);
        return results.toArray(new String[0]);
    }

    private static void addField(List<String> results, StringBuilder field) {
        results.add(field.toString());
        field.setLength(0);
    }
}
