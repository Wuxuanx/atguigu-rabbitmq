import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class MyTest {
//    public static boolean canConstruct(String ransomNote, String magazine) {
//        List<String> r = Arrays.asList(ransomNote.split(""));
//        List a = new ArrayList(r);
//        String[] m = magazine.split("");
//        for (int i = 0; i < m.length; i++) {
//            System.out.println("m[i]:" + m[i]);
//            for (int j = 0; j < a.size(); j++) {
//                System.out.println("a.get(j): " + a.get(j));
//                if (a.contains(m[i])) {
//                    a.remove(m[i]);
//                    System.out.println("a:" + a);
//                    break;
//                } else {
//                    return false;
//                }
//            }
//        }
//        return true;
//    }
    public static void main(String[] args) {
        // System.out.println(canConstruct("bg", "efjbdfbdgfjhhaiigfhbaejahgfbbgbjagbddfgdiaigdadhcfcj"));

//        -----------------------------------------
        List<Integer> nonBlack = new ArrayList<Integer>();
        List<Integer> blacklist  = new ArrayList<Integer>();
        for (int i = 0; i < 2; i++) {
            int k = 0;
            for (int j = 0; j < blacklist.size(); j++) {
                if (blacklist.get(j) == i) {
                    k = 1;
                    break;
                }
            }
            if (k == 0) {
                nonBlack.add(i);
            }
        }
        System.out.println("blacklist:" + blacklist);
        System.out.println("nonBlack:" + nonBlack);
        System.out.println(1000000000 > 640145908);
        Random r = new Random();
        System.out.println(nonBlack.get(r.nextInt(nonBlack.size())));
    }
}
