package secondtry;

public class WordCount {
    public static void main(String[] args) {
        System.out.println(count(" eins <html> zwei<html>drei <html> vier"));
    }

    public enum State {
        NOWORD,
        INWORD,
        INTAG
    }

    public static int count(String text) {
        if (text == null || text.isEmpty()) {
            return 0;
        }

        State state = State.NOWORD;
        int counter = 0;

        for (int i = 0; i < text.length(); i++) {
            switch (state) {
                case INTAG -> {
                    switch (text.charAt(i)) {
                        case '>' -> state = State.NOWORD;
                    }
                }
                case NOWORD -> {
                    switch (text.charAt(i)) {
                        case '<' -> state = State.INTAG;
                        case '>' -> state = State.NOWORD;
                        default -> {
                            if (Character.isAlphabetic(text.charAt(i))) {
                                state = State.INWORD;
                                counter++;
                            }
                        }
                    }
                }
                case INWORD -> {
                    switch (text.charAt(i)) {
                        case '<' -> state = State.INTAG;
                        case '>' -> state = State.NOWORD;
                        default -> {
                            if (!Character.isAlphabetic(text.charAt(i))) {
                                state = State.NOWORD;
                            }
                        }
                    }
                }
            }
        }
        return counter;
    }
}
