import java.util.ArrayList;

public class tut {
    public static void main(String[] args) {
        ArrayList<Integer> bitrev = new ArrayList<Integer>();
        ArrayList<Integer> bitrev2 = new ArrayList<Integer>();
        bitrev.add(0);
        bitrev.add(1);
        bitrev.add(0);
        bitrev.add(1);
        bitrev.add(1);
        bitrev.add(0);
        bitrev.add(1);
        bitrev.add(0);

        for (int i=0; i < bitrev.size(); i++){
            if (bitrev.get(i) == 1){
                bitrev2.add(0);
            }
            else{
                bitrev2.add(1);                
            }
        }

        System.out.println(bitrev);
        System.out.println(bitrev2);
    }
}
