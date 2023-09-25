package enum1;

public class Main {
    public static void main(String[] args) {
        State s = State.WORD;
        switch (s) {
            case WORD -> System.out.println("WORD");
            case NOWORD -> System.out.println("NOWORD");
        }
    }
    enum State{
        WORD,
        NOWORD
    }
}
