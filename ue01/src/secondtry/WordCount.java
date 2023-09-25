package secondtry;

public class WordCount {

    public enum State {
        NOWORD,
        INWORD
    }

    public static int count(String text) {
        if (text == null || text.isEmpty()) {
            return 0;
        }

        State state = State.NOWORD;
        int counter = 0;

        for (char c : text.toCharArray()) {
            switch (state) {
                case NOWORD -> {
                    if (Character.isAlphabetic(c)) {
                        state = State.INWORD;
                        counter++;
                    }
                }
                case INWORD -> {
                    if (!Character.isAlphabetic(c)) {
                        state = State.NOWORD;
                    }
                }
            }
        }
        return counter;
    }
}
