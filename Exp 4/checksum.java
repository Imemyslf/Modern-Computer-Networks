import java.util.*;

public class checksum {
    
    public int carry = 0;
    public ArrayList<Integer> remain(ArrayList<Integer> newsegment1, ArrayList<Integer> newsegment2, ArrayList<Integer> remainder, int blocksize) {
        remainder.clear();
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
        // System.out.println("Remainder insider the function: " + remainder);
        return remainder;
    }
    @SuppressWarnings("unchecked")
    public static void main(String[] args) {

        checksum cs = new checksum();
        int ptr = 0,numberofblocks = 4,blocksize = 8,flag = 1,i,j,bs=0;
        int[] data = {1,0,1,1,0,0,1,1,1,0,1,0,1,0,1,1,0,1,0,1,1,0,1,0,1,1,0,1,0,1,0,1}; 
        ArrayList<Integer>[] segment = new ArrayList[numberofblocks];
        ArrayList<Integer> remainder1 = new ArrayList<>(); 
        ArrayList<Integer> remainder2 = new ArrayList<>(); 
        ArrayList<Integer> newsegment1 = new ArrayList<>(); 
        ArrayList<Integer> newsegment2 = new ArrayList<>(); 
        ArrayList<Integer> buffer = new ArrayList<>(); 
        ArrayList<Integer> code = new ArrayList<>(); 

        System.out.print("Entered data is:  ");
        for (i = 0 ; i < 32; i++){
            System.out.print(data[i]);
        }
        System.out.println("\nNumber of blocks:  " + numberofblocks);
        System.out.println("Bits in a block:  "+ blocksize);
        
        for ( i = 0; i < blocksize - 1; i++) {
            buffer.add(0);
        }
        buffer.add(1);
        
        for ( i = 0; i < numberofblocks; i++) {
            segment[i] = new ArrayList<Integer>();
        }

        for ( i = 0; i < numberofblocks; i++) {
            for ( j = ptr; j < ptr + blocksize; j++) {
                segment[i].add(data[j]);
            }
            ptr += blocksize;
        }

        // for ( i = 0; i < numberofblocks; i++) {
        //     System.out.println(segment[i] + "\n");
        // }
        
        newsegment1 = new ArrayList<Integer>(segment[0]);
        newsegment2 = new ArrayList<Integer>(segment[1]);
        
        ptr = 1;
        while(flag == 1){
            Collections.reverse(newsegment1);
            Collections.reverse(newsegment2);
            // System.out.println("seg1"+newsegment1);
            // System.out.println("seg2"+newsegment2);
            // System.out.println("Ptr: "+ptr);
        
                remainder1.clear();
                remainder1 = cs.remain(newsegment1, newsegment2, remainder1, blocksize); 
                // System.out.println("rem before carry add"+remainder1);
                // System.out.println(cs.carry);
                
                if (cs.carry == 1) {
                    // Collections.reverse(remainder1);
                    buffer = new ArrayList <> (buffer);
                    // System.out.println(remainder1);
                    
                    if (ptr == ((segment.length) - 1)){
                        buffer.clear();
                        buffer.add(1);
                        for ( i = 0; i < blocksize - 1; i++){
                            buffer.add(0);
                        }
                        // System.out.println(buffer);
                    }
                    else{
                        Collections.reverse(buffer);
                        // System.out.println(buffer);
                    }

                    remainder2 = new ArrayList<>();
                    cs.carry = 0;
                    remainder1 = cs.remain(remainder1,buffer,remainder2,blocksize);
                    // System.out.println("rem after carry add: "+remainder1);
                    // System.out.println(cs.carry);
                }
                
                ptr++;
                if (ptr != 4){
                    newsegment1 = new ArrayList<Integer> (remainder1);
                    newsegment2 = new ArrayList<Integer> (segment[ptr]);
                }
                else{
                    break;
                }
            
        }
        
        for (  i  = 0; i < numberofblocks + 1; i++ ){
            if (i < numberofblocks ){
                    code.addAll(segment[i]);
                }
                else{
                    code.addAll(remainder1);
                }
            }
        
        
        ptr = 0;
        j = 1;
        for ( i =0; i < code.size(); i++){
            if (i == bs) {
                System.out.print("\nSegment["+j+"]\t");
                bs = bs + 8 ;
                j++;
            }
            System.out.print(code.get(i));
            ptr++;    
        }
        
        System.out.println("\n\nCodeword: " + code.toString().replaceAll("[\\[\\],]","")+"\n");
    }
}

/*
 Example:-

Entered data is:  10110011101010110101101011010101
Number of blocks:  4
Bits in a block:  8

Segment[1]      10110011
Segment[2]      10101011
Segment[3]      01011010
Segment[4]      11010101
Segment[5]      01010110

Codeword: 1 0 1 1 0 0 1 1 1 0 1 0 1 0 1 1 0 1 0 1 1 0 1 0 1 1 0 1 0 1 0 1 0 1 0 1 0 1 1 0

 */