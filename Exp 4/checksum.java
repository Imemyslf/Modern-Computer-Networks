import java.util.*;

public class checksum {
    
    //Initialzing carray as global variable to work around the main function and remain function effectively.
    public int carry = 0;

    // remain function 
    public ArrayList<Integer> remain(ArrayList<Integer> newsegment1, ArrayList<Integer> newsegment2, ArrayList<Integer> remainder, int blocksize) {
        
        //Clearing remainder ones again just to be safe :).
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
        return remainder;
    }
    @SuppressWarnings("unchecked")
    public static void main(String[] args) {

        checksum cs = new checksum();
        int ptr = 0,numberofblocks = 4,blocksize = 8,flag = 1,i,j,bs=0;
        int[] data = {1,0,1,1,0,0,1,1,1,0,1,0,1,0,1,1,0,1,0,1,1,0,1,0,1,1,0,1,0,1,0,1}; 
        ArrayList<Integer>[] segment = new ArrayList[numberofblocks];
        ArrayList<Integer> remainder1 = new ArrayList<>();  //To keep the main remainder for calculations.
        ArrayList<Integer> remainder2 = new ArrayList<>(); // Temporary remainder for calculations.
        ArrayList<Integer> newsegment1 = new ArrayList<>(); // Toggle arounds segments.
        ArrayList<Integer> newsegment2 = new ArrayList<>(); // Toggle arounds segments.
        ArrayList<Integer> buffer = new ArrayList<>();  // buffer array storing 0 0 0 0 0 0 0 0 1 to add to remainder if carry == 1 at the end.
        ArrayList<Integer> code = new ArrayList<>(); // Storing the actual codeword.

        //Displaying the data before ading the codeword
        System.out.print("Entered data is:  ");
        for (i = 0 ; i < 32; i++){
            System.out.print(data[i]);
        }
        System.out.println("\nNumber of blocks:  " + numberofblocks);
        System.out.println("Bits in a block:  "+ blocksize);
        
        // initializing buffer array.
        buffer.add(1);
        for ( i = 0; i < blocksize - 1; i++) {
            buffer.add(0);
        }
        
        //initializing segment array into 4 sub-arrays of segment.
        for ( i = 0; i < numberofblocks; i++) {
            segment[i] = new ArrayList<Integer>();
        }

        //Adding 8 bit data to each segment array.
        for ( i = 0; i < numberofblocks; i++) {
            for ( j = ptr; j < ptr + blocksize; j++) {
                segment[i].add(data[j]);
            }
            ptr += blocksize;
        }

        
        
        //Initialize the newsegment1 & newsegment2 first segment and second segment respectively
        newsegment1 = new ArrayList<Integer>(segment[0]);
        newsegment2 = new ArrayList<Integer>(segment[1]);
        
        // ptr helps with iterating through segment.
        ptr = 1;
        while(flag == 1){
            //reversing both newsegment1 and newsegment2 for calculating the remainder from right to left.
            Collections.reverse(newsegment1);
            Collections.reverse(newsegment2);
            
                //claering the reaminder for no garbez data in it.
                remainder1.clear();

                //calling remain function and assigning the value to remainder1.
                remainder1 = cs.remain(newsegment1, newsegment2, remainder1, blocksize);

                
                
                // If after the operation if any carry if left then add the buffer to the newly obtained remainder1 to get final remainder1.
                if (cs.carry == 1) {
                    Collections.reverse(remainder1);

                    //claering the reaminder for no garbez data in it.
                    remainder2 = new ArrayList<>();

                    //Initializing carry = 0 since we are gonna calculate from start.
                    cs.carry = 0;

                    //calling remain function and assigning the value to remainder1.
                    remainder1 = cs.remain(remainder1,buffer,remainder2,blocksize);

                }
                
                //increment ptr to get next segment.
                ptr++;

                //if ptr == numberofblocks i.e segment size that is in the segment there are 4 sub arrays present if ptr == 4 den stop the reinitialization of newsegment1 and newsegment2 and break.
                if (ptr != numberofblocks) {
                    newsegment1 = new ArrayList<Integer> (remainder1);
                    newsegment2 = new ArrayList<Integer> (segment[ptr]);
                }
                else{
                    break;
                }
            
        }

        //complimenting the final remainder1 bits to get the actual code.
        for (i = 0; i < remainder1.size(); i++){
            remainder1.set(i,remainder1.get(i) == 1? 0 : 1 );
        }

        //Combing all the 4 sub-arrays of segment and remainder1 to get the Final Codeword
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
        
        //Displaying the Segments.
        for ( i =0; i < code.size(); i++){
            if (i == bs) {
                System.out.print("\nSegment["+j+"]\t");
                bs = bs + 8 ;
                j++;
            }
            System.out.print(code.get(i));
            ptr++;    
        }
        
        //Displaying the Codeword as String.
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
Segment[5]      01110000

Codeword: 1 0 1 1 0 0 1 1 1 0 1 0 1 0 1 1 0 1 0 1 1 0 1 0 1 1 0 1 0 1 0 1 0 1 1 1 0 0 0 0

 */