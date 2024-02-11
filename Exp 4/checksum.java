import java.util.*;

public class checksum {
    
    public int carry = 0;
    // @SuppressWarnings("unchecked")
    public ArrayList<Integer> remain(ArrayList<Integer> newsegment1, ArrayList<Integer> newsegment2, ArrayList<Integer> remainder, int blocksize) {
        
        for (int j = 0; j < blocksize; j++) {
            if (newsegment1.get(j) == 0 && newsegment2.get(j) == 0 && carry == 0) {
                remainder.add(0);
                carry = 0;
            } else if (newsegment1.get(j) == 0 && newsegment2.get(j) == 0 && carry == 1) {
                remainder.add(1);
                carry = 0;
            } else if (newsegment1.get(j) == 0 && newsegment2.get(j) == 1 && carry == 0) {
                remainder.add(1);
                carry = 0;
            } else if (newsegment1.get(j) == 0 && newsegment2.get(j) == 1 && carry == 1) {
                remainder.add(0);
                carry = 1;
            } else if (newsegment1.get(j) == 1 && newsegment2.get(j) == 0 && carry == 0) {
                remainder.add(1);
                carry = 0;
            } else if (newsegment1.get(j) == 1 && newsegment2.get(j) == 0 && carry == 1) {
                remainder.add(0);
                carry = 1;
            } else if (newsegment1.get(j) == 1 && newsegment2.get(j) == 1 && carry == 0) {
                remainder.add(0);
                carry = 1;
            } else {
                remainder.add(1);
                carry = 1;
            }
        }
        Collections.reverse(remainder);
        return remainder;
    }
    
    public static void main(String[] args) {

        checksum cs = new checksum();
        int ptr = 0;
        int blocksize = 8;
        int numberofblocks = 4;
        int[] data = {1,0,1,1,0,0,1,1,1,0,1,0,1,0,1,1,0,1,0,1,1,0,1,0,1,1,0,1,0,1,0,1}; 
        ArrayList<Integer>[] segment = (ArrayList<Integer>[]) new ArrayList[numberofblocks];
        ArrayList<Integer> remainder1 = new ArrayList<>(); 
        ArrayList<Integer> buffer = new ArrayList<>(); 
        ArrayList<Integer> newsegment1 = new ArrayList<>(); 
        ArrayList<Integer> newsegment2 = new ArrayList<>(); 

        for (int i = 0; i < blocksize - 1; i++) {
            buffer.add(0);
        }
        buffer.add(1);
        
        for (int i = 0; i < numberofblocks; i++) {
            segment[i] = new ArrayList<Integer>();
        }

        for (int i = 0; i < numberofblocks; i++) {
            for (int j = ptr; j < ptr + blocksize; j++) {
                segment[i].add(data[j]);
            }
            ptr += blocksize;
        }

        for (int i = 0; i < numberofblocks; i++) {
            System.out.println(segment[i] + "\n");
        }
        
        newsegment1 = new ArrayList<Integer>(segment[0]);
        newsegment2 = new ArrayList<Integer>(segment[1]);
        
        int flag = 1;
        ptr = 1;
        while(flag == 1){
            Collections.reverse(newsegment1);
            Collections.reverse(newsegment2);
            System.out.println(newsegment1);
            System.out.println(newsegment2);
            
            if (ptr == 2){
                break;
            } 
            else 
            {
                remainder1.clear();
                remainder1 = cs.remain(newsegment1, newsegment2, remainder1, blocksize); 
                System.out.println(remainder1);
                System.out.println(cs.carry);
                
                // if (cs.carry == 1) {
                //     // Handle carry if needed
                // }
            }
            ptr++;
        }
    }
}
