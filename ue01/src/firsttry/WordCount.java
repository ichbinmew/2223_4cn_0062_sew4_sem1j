package firsttry;

public class WordCount {

    public static void main(String[] args) {
        System.out.println(count(" eins<img alt=\"<bild\" keinwort>zwei"));
    }
    public static long count(String s){
        long count = 0;
        boolean isWord = false;
        int end = s.length() - 1;

        for (int i = 0; i < s.length(); i++) {
            //System.out.println(i+" "+isWord);
            if (Character.isLetter(s.charAt(i)) && i != end) {
                isWord = true;
                System.out.println(s.charAt(i));
            /*}else if (s.charAt(i) == '"' && s.indexOf('"', i) != -1){
                System.out.println(i);
                System.out.println(s.indexOf('"', i));
                if (isWord) {
                    count++;
                    //System.out.println(i+" "+s.charAt(i));
                    isWord = false;
                }
                int tagEnd = s.indexOf('"', i);
                //System.out.println(tagEnd);
                i = tagEnd;*/
            } else if (s.charAt(i) == '<' && s.indexOf('>', i) != -1) {
                if (isWord) {
                    count++;
                    //System.out.println(i+" "+s.charAt(i));
                    isWord = false;
                }
                if (s.charAt(i) == '<' && s.indexOf('>', i) > s.indexOf('<', i + 1) && s.indexOf('<', i + 1) != -1) {
                    int a = s.indexOf('>', i + 1);
                    /*int b = s.indexOf('>', a + 1);
                    System.out.println(a+" "+b);
                    if(b == -1){
                        break;
                    }*/
                    //i = b;
                    i = a;
                    //System.out.println(s.indexOf('>', a+1));
                    //System.out.println(a);
                }
                int tagEnd = s.indexOf('>', i);
                //System.out.println(tagEnd);
                i = tagEnd;
            }else if (s.charAt(i) == '<' && s.indexOf('>', i) == -1) {
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
                //System.out.println(i+" "+s.charAt(i));
            }
        }
        return count;
    }
}
