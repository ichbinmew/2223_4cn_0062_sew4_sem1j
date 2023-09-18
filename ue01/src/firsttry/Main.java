package firsttry;

public class Main {

    public static void main(String[] args) {
        System.out.println(count(" eins"));
    }
    public static long count(String s){
        s = s.trim();
        if(s.isEmpty()){
            return 0;
        }
        String[] words = s.split(" ");
        return words.length;
    }
}
