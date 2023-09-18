package firsttry;

public class WordCount {

    public static void main(String[] args) {
        System.out.println(count(" eins  <html> "));
    }
    public static long count(String s){
        long count = 0;
        boolean isWord = false;
        int end = s.length() - 1;

        for (int i = 0; i < s.length(); i++) {
            if (Character.isLetter(s.charAt(i)) && i != end) {
                isWord = true;
            } else if (!Character.isLetter(s.charAt(i)) && isWord) {
                count++;
                isWord = false;
            } else if (Character.isLetter(s.charAt(i)) && i == end) {
                count++;
            }
            if (s.charAt(i) == '<') {
                int tagEnd = s.indexOf('>', i);
                if (tagEnd != -1) {
                    i = tagEnd;
                }
            }
        }
        return count;
    }
}
