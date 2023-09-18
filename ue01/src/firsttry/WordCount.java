package firsttry;

public class WordCount {

    public static void main(String[] args) {
        System.out.println(count("eins"));
    }
    public static long count(String s){
        long count = 0;
        boolean word = false;
        int endOfLine = s.length() - 1;

        for (int i = 0; i < s.length(); i++) {
            if (Character.isLetter(s.charAt(i)) && i != endOfLine) {
                word = true;
            } else if (!Character.isLetter(s.charAt(i)) && word) {
                count++;
                word = false;
            } else if (Character.isLetter(s.charAt(i)) && i == endOfLine) {
                count++;
            }
        }
        return count;
    }
}
