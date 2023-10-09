package csv;

import java.util.ArrayList;
import java.util.List;

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
            public State process(char c) {
                if (c == ',') {
                    return START;
                } else if (c == '"') {
                    return INSIDE_STRING;
                } else {
                    return INSIDE_FIELD;
                }
            }
        },
        INSIDE_FIELD {
            @Override
            public State process(char c) {
                if (c == ',') {
                    return START;
                } else if (c == '"') {
                    return INSIDE_STRING;
                } else {
                    return INSIDE_FIELD;
                }
            }
        },
        INSIDE_STRING {
            @Override
            public State process(char c) {
                if (c == '"') {
                    return STRING_END;
                } else {
                    return INSIDE_STRING;
                }
            }
        },
        STRING_END {
            @Override
            public State process(char c) {
                if(c == ',') {
                    return START;
                } else if (c == '"') {
                    return INSIDE_STRING;
                } else {
                    throw new IllegalStateException("String not ended");
                }
            }
        };
        public abstract State process(char c);
    }

    public static String[] split(String input) {
        List<String> results = new ArrayList<>();
        StringBuilder currentField = new StringBuilder();
        State currentState = State.START;
        int count = 0;
        for (int i = 0; i < input.length(); i++) {
            char c = input.charAt(i);
            currentState = currentState.process(c);
            switch (currentState) {
                case START -> {
                    results.add(currentField.toString());
                    currentField = new StringBuilder();
                }
                case INSIDE_STRING -> {
                    if (input.indexOf('"', i) == -1) {
                        throw new IllegalStateException("String not ended");
                    } else  if (c != '"'){
                        currentField.append(c);
                    }
                }
                default -> {
                    if (c != '"'){
                        currentField.append(c);
                    }
                }
            }
        }

        results.add(currentField.toString());
        return results.toArray(new String[0]);
    }
}
