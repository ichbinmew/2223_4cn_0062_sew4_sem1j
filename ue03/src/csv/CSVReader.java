package csv;

import java.util.ArrayList;
import java.util.List;

public class CSVReader {
    public static void main(String[] args) {
        String[] result = split("a,b,c");
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
                } else {
                    return INSIDE_FIELD;
                }
            }
        };

        public abstract State process(char c);
    }

    public static String[] split(String input) {
        List<String> results = new ArrayList<>();
        StringBuilder currentField = new StringBuilder();
        State currentState = State.START;

        for (char c : input.toCharArray()) {
            currentState = currentState.process(c);

            if (currentState == State.START) {
                results.add(currentField.toString());
                currentField.setLength(0);
            } else {
                currentField.append(c);
            }
        }

        results.add(currentField.toString());
        return results.toArray(new String[0]);
    }
}
