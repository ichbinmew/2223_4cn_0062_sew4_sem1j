package firsttry;

public class WordCount {

    public static void main(String[] args) {
        System.out.println(count(" eins<img alt=\"<bild keinwort> keinwort"));
    }
    public static long count(String s){
        long count = 0;
        boolean isWord = false;

        for (int i = 0; i < s.length(); i++) {
            if (s.charAt(i) == '"') {
                int tagEnd = s.indexOf('"', i + 1);
                if (tagEnd != -1) {
                    String content = s.substring(i + 1, tagEnd);
                    if (content.contains("<") || content.contains(">")) {
                        s = s.replace(s.substring(i, tagEnd + 1), "\"\"");
                        i = tagEnd + 1;
                    }
                }
            }
        }
        int end = s.length() - 1;
        System.out.println(s);
        for (int i = 0; i < s.length(); i++) {
            //System.out.println(i+" "+s.charAt(i));
            //System.out.println(i+" "+isWord);
            if (Character.isLetter(s.charAt(i)) && i != end) {
                isWord = true;
                //System.out.println(s.charAt(i));
                //System.out.println(i+" "+s.charAt(i));
            } else if (s.charAt(i) == '<' && s.indexOf('>', i) > s.indexOf('<', i + 1) && s.indexOf('<', i + 1) != -1) {
                if (isWord) {
                    count++;
                    isWord = false;
                }
                int a = s.indexOf('>', i + 1);
                int b = s.indexOf('>', a + 1);
                if (b == -1){
                    break;
                }
                i = b;
            } else if (s.charAt(i) == '<' && s.indexOf('>', i) != -1) {
                if (isWord) {
                    count++;
                    //System.out.println(i+" "+s.charAt(i));
                    isWord = false;
                }
                int tagEnd = s.indexOf('>', i);
                i = tagEnd;
            } else if (s.charAt(i) == '<' && s.indexOf('>', i) == -1) {
                if (isWord) {
                    count++;
                    //System.out.println(i+" "+s.charAt(i));
                    isWord = false;
                }
                break;
            } else if (!Character.isLetter(s.charAt(i)) && isWord) {
                count++;
                //System.out.println(isWord);
                //System.out.println(i+" "+s.charAt(i));
                isWord = false;
            } else if (Character.isLetter(s.charAt(i)) && i == end) {
                count++;
                //System.out.println(i+" "+s.charAt(i));
            } else if (s.charAt(i) == '<' && s.indexOf('>', i) == -1) {
                i++;
            } else if (!Character.isLetter(s.charAt(i)) && isWord) {
                count++;
                //System.out.println(isWord);
                //System.out.println(i+" "+s.charAt(i));
                isWord = false;
            } else if (Character.isLetter(s.charAt(i)) && i == end) {
                count++;
            }
        }
        return count;
    }
}
