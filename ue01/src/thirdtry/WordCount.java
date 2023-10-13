package thirdtry;

import java.util.Objects;

public class WordCount {
    public static void main(String[] args) {
        System.out.println(count("ein:erster.Text"));
    }
    public enum State {
        NOWORD {
            @Override
            public State process(char c) {
                if (c == ' '){
                    return NOWORD;
                } else if (c == '<'){
                    return INTAG;
                } else {
                    if(Character.isAlphabetic(c)){
                        return NEWWORD;
                    } else {
                        return NOWORD;
                    }
                }
            }
        },
        NEWWORD{
            @Override
            public State process(char c) {
                if (c == ' '){
                    return NOWORD;
                } else if (c == '<'){
                    return INTAG;
                } else {
                    return INWORD;
                }
            }
        },
        INWORD {
            @Override
            public State process(char c) {
                if (!Character.isAlphabetic(c)){
                    return NOWORD;
                } else if (c == '<'){
                    return INTAG;
                } else {
                    return INWORD;
                }
            }
        },
        INTAG {
            @Override
            public State process(char c) {
                if (c == ' '){
                    return NOWORD;
                } else if (c == '<'){
                    return INTAG;
                } else {
                    return INWORD;
                }
            }
        };
        public abstract State process(char c);
    }
    public static long count (String s){
        long counter = 0;
        State state = State.NOWORD;
        for (int i = 0; i < s.length(); i++) {
            state = state.process(s.charAt(i));

            if (Objects.equals(state, State.NEWWORD)) {
                counter++;
            }
        }
        return counter;
    }
}
