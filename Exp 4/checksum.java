import java.util.*;

public class checksum {
    
    public int carry = 0;
    @SuppressWarnings("unchecked")
    public ArrayList<Integer> remain(ArrayList<Integer> newsegment1, ArrayList<Integer> newsegment2, ArrayList<Integer> remainder,int blocksize) {
        int j;
        remainder.clear();
        for ( j = 0; j < blocksize; j++){
            if (newsegment1.get(j) == 0 && newsegment2.get(j) == 0 && carry == 0){
                remainder.add(0);
                carry = 0;
            }
            else if (newsegment1.get(j) == 0 && newsegment2.get(j) == 0 && carry == 1){
                remainder.add(1);
                carry = 0;
            }
            else if (newsegment1.get(j) == 0 && newsegment2.get(j) == 1 && carry == 0){
                remainder.add(1);
                carry = 0;
            }
            else if (newsegment1.get(j) == 0 && newsegment2.get(j) == 1 && carry == 1){
                remainder.add(0);
                carry = 1;
            }
            else if (newsegment1.get(j) == 1 && newsegment2.get(j) == 0 && carry == 0){
                remainder.add(1);
                carry = 0;
            }
            else if (newsegment1.get(j) == 1 && newsegment2.get(j) == 0 && carry == 1){
                remainder.add(0);
                carry = 1;
            }
            else if (newsegment1.get(j) == 1 && newsegment2.get(j) == 1 && carry == 0){
                remainder.add(0);
                carry = 1;
            }
            else
            {
                remainder.add(1);
                carry = 1;
            }
        }
        Collections.reverse(remainder);
        return remainder;
    }
    public static void main(String[] args) {

        checksum cs = new checksum();
        int i, j,ptr = 0;
        int blocksize = 8;
        int numberofblocks = 4;
        int[] data = {1,0,1,1,0,0,1,1,1,0,1,0,1,0,1,1,0,1,0,1,1,0,1,0,1,1,0,1,0,1,0,1}; 
        ArrayList<Integer>[] segment = (ArrayList<Integer>[]) new ArrayList[numberofblocks];
        ArrayList<Integer> remainder1 = new ArrayList<>(); 
        ArrayList<Integer> remainder2 = new ArrayList<>(); 
        ArrayList<Integer> buffer = new ArrayList<>(); 
        ArrayList<Integer> newsegment1 = new ArrayList<>(); 
        ArrayList<Integer> newsegment2 = new ArrayList<>(); 

        for (i = 0; i < numberofblocks - 1; i++) {
            buffer.add(0);
        }
        buffer.add(1);

        for (i = 0; i < numberofblocks; i++) {
            segment[i] = new ArrayList<Integer>();
        }

        for (i = 0; i < numberofblocks; i++) {
            for (j = ptr; j < blocksize; j++) {
                segment[i].add(data[j]);
            }
            ptr = j;
            blocksize = blocksize + 8;
        }

        for (i = 0; i < numberofblocks; i++) {
            System.out.println(segment[i] + "\n");
        }
        
        newsegment1 = new ArrayList<Integer> (segment[0]);
        newsegment2 = new ArrayList<Integer> (segment[1]);
        i = 0;
        ptr = 1;
        int flag = 1;
        while(flag == 1){
            // segment[i] = new ArrayList<Integer> (newsegment);
            // segment[ptr] = new ArrayList<Integer> (segment[ptr]);
            System.out.println(newsegment1);
            System.out.println(newsegment2);
            
            if (ptr == numberofblocks){
                flag = 0;
            }
            else
            {
                remainder1 = cs.remain(newsegment1,newsegment2,remainder1,blocksize); 
                if ( cs.carry == 1){
                    remainder1 = cs.remain(remainder1,buffer,remainder2,blocksize);
                }
            }
        }
    }
}
