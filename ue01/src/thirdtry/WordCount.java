package thirdtry;

public class WordCount {
    public static void main(String[] args) {

    }
    public enum State {
        START {
            @Override
            public State process(char c, int counter) {
                return null;
            }
        };
        public abstract State process(char c, int counter);
    }
    public static long count (String s){
        return 0;
    }
}
