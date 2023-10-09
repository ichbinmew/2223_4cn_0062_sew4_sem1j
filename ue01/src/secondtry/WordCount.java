package secondtry;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.nio.file.Files;

public class WordCount {
    public static void main(String[] args) {
        int count1 = 0;
        try {
            FileReader fileReader = new FileReader("C:\\Users\\jacob\\OneDrive - HTL Wien 3 Rennweg\\SJ2324\\SEW\\sew4_sem1j\\ue02\\src\\crsto12.html");
            BufferedReader bufferedReader = new BufferedReader(fileReader);
            String line;
            while ((line = bufferedReader.readLine()) != null) {
                count1 += count(line);
            }
            bufferedReader.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
        System.out.println(count1);
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
