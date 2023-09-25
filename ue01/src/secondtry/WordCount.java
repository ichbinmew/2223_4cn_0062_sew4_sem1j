package secondtry;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;

public class WordCount {
    public static void main(String[] args) {
        ///read all lines in a html file
        File file = new File("C:\\Users\\jacob\\OneDrive - HTL Wien 3 Rennweg\\SJ2324\\SEW\\sew4_sem1j\\ue02\\src\\crsto12.html");
        String text;
        try {
            text = Files.readString(file.toPath());
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
        System.out.println(count(text));

        System.out.println(count(" eins<img alt=\"<bild\" keinwort>zwei"));
    }

    public enum State {
        NOWORD,
        INWORD,
        INTAG,
        VERSCHACHTELT,
        INSTRINGTAG,
        BACKSLASH
    }

    public static int count(String text) {
        text = text.replace("\\\"", "");
        if (text.isEmpty()) {
            return 0;
        }

        State state = State.NOWORD;
        int counter = 0;

        for (int i = 0; i < text.length(); i++) {
            switch (state) {
                case INSTRINGTAG -> {
                    switch (text.charAt(i)) {
                        case '<' -> i++;
                        case '"' -> state = State.INTAG;
                    }
                }
                case VERSCHACHTELT -> {
                    switch (text.charAt(i)) {
                        case '>' -> state = State.INTAG;
                    }
                }
                case INTAG -> {
                    switch (text.charAt(i)) {
                        case '<' -> state = State.VERSCHACHTELT;
                        case '>' -> state = State.NOWORD;
                        case '"' -> state = State.INSTRINGTAG;
                        case '\\' -> state = State.BACKSLASH;
                    }
                }
                case NOWORD -> {
                    if (text.charAt(i) == '<') {
                        state = State.INTAG;
                    } else {
                        if (Character.isAlphabetic(text.charAt(i))) {
                            state = State.INWORD;
                            counter++;
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
