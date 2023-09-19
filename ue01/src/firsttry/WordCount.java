package firsttry;

public class WordCount {

    public static void main(String[] args) {
        System.out.println(count(" eins<img alt=\"<bild \\\" keinwort> keinwort\" keinwort>zwei"));
    }
    public static long count(String s){
        long count = 0;
        boolean isWord = false;

        for (int i = 0; i < s.length(); i++) {
            if(s.charAt(i) == '"' && s.charAt(i-1) == '\\'){
                s = s.replace(s.substring(i-1, i+1), "");
            }
        }
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
        for (int i = 0; i < s.length(); i++) {
            if (Character.isLetter(s.charAt(i)) && i != end) {
                isWord = true;
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
                    isWord = false;
                }
                int tagEnd = s.indexOf('>', i);
                i = tagEnd;
            } else if (s.charAt(i) == '<' && s.indexOf('>', i) == -1) {
                if (isWord) {
                    count++;
                    isWord = false;
                }
                break;
            } else if (!Character.isLetter(s.charAt(i)) && isWord) {
                count++;
                isWord = false;
            } else if (Character.isLetter(s.charAt(i)) && i == end) {
                count++;
            } else if (s.charAt(i) == '<' && s.indexOf('>', i) == -1) {
                i++;
            } else if (!Character.isLetter(s.charAt(i)) && isWord) {
                count++;
                isWord = false;
            } else if (Character.isLetter(s.charAt(i)) && i == end) {
                count++;
            }
        }
        return count;
    }
}
