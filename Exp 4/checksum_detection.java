import java.util.*;

public class checksum_detection {

    //Initialzing carray as global variable to work around the main function and remain function effectively.
    public int carry = 0;

    // remain() function 
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
        
        checksum_detection cs = new checksum_detection();
        int ptr = 0,numberofblocks = 5,blocksize = 8,i,j;
        int[] data = {1,0,1,1,0,0,1,1,1,0,1,0,1,0,1,1,0,1,0,1,1,0,1,0,1,1,0,1,0,1,0,1,0,1,1,1,0,0,0,1};

        ArrayList<Integer>[] segment = new ArrayList[numberofblocks];//Adding 5 sub-list in segment.
        ArrayList<Integer> remainder1 = new ArrayList<>();  //To keep the main remainder for calculations.
        ArrayList<Integer> remainder2 = new ArrayList<>(); // Temporary remainder for calculations.
        ArrayList<Integer> newsegment1 = new ArrayList<>(); // Toggle arounds segments.
        ArrayList<Integer> newsegment2 = new ArrayList<>(); // Toggle arounds segments.
        ArrayList<Integer> buffer = new ArrayList<>();  // buffer array storing 0 0 0 0 0 0 0 0 1 to add to remainder if carry == 1 at the end.
        ArrayList<Integer> code = new ArrayList<>(); // Storing the actual codeword.

        // Initializing buffer list.
        buffer.add(1);
        for ( i = 0; i < blocksize - 1; i++) {
            buffer.add(0);
        }

        //Displaying the data before ading the codeword
        System.out.print("\nEntered data is:  ");
        for (i = 0 ; i < data.length; i++){
            System.out.print(data[i]);
        }
        
        //Displaying number of blocks in the segment.
        System.out.println("\nNumber of blocks:  " + numberofblocks);

        //Displaying the number of bits in each block respectively.
        System.out.println("Bits in a block:  "+ blocksize);

        //initializing segment list into 5 sub-list of segment.
        for ( i = 0; i < numberofblocks; i++) {
            segment[i] = new ArrayList<Integer>();
        }

        //Adding 8 bit data to each segment list repectively.
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
        while(true){
            //Reversing both newsegment1 and newsegment2 for calculating the remainder from right to left.
            Collections.reverse(newsegment1);
            Collections.reverse(newsegment2);
            
                //Clearing the reaminder for no garbez data in it.
                remainder1.clear();

                //Calling remain function and assigning the value to remainder1.
                remainder1 = cs.remain(newsegment1, newsegment2, remainder1, blocksize);

                
                
                // If after the operation if any carry is left then add the buffer to the newly obtained remainder1 to get final remainder1.
                if (cs.carry == 1) {
                    //Reversing remainder for calculation purpose.
                    Collections.reverse(remainder1);

                    //Clearing the temporary reaminder for no garbez data in it.
                    remainder2 = new ArrayList<>();

                    //Initializing carry = 0 since we are gonna calculate from start.
                    cs.carry = 0;

                    //Calling remain function and assigning the value to remainder1.
                    remainder1 = cs.remain(remainder1,buffer,remainder2,blocksize);

                }
                
                //Increment ptr to get next segment.
                ptr++;

                // If ptr == numberofblocks i.e segment size that is in the segment there are 4sub-list present if ptr == 4 den stop the reinitialization 
                // of newsegment1 and newsegment2 and break the while loop.
                if (ptr != numberofblocks) {
                    newsegment1 = new ArrayList<Integer> (remainder1);
                    newsegment2 = new ArrayList<Integer> (segment[ptr]);
                }
                else
                {
                    break;
                }
            
        }

        //Clearing the buffer list to make it zero for further operations.
        buffer.clear();
        for (i = 0; i < blocksize; i++){
            buffer.add(0);
        }
        
        //Complimenting the Final remainder1 bits to get the actual code.
        for (i = 0; i < remainder1.size(); i++){
            remainder1.set(i,remainder1.get(i) == 1? 0 : 1 );
        }

        // If remainder1 = 0 then display no error and proceed to discard the last segment from segment list.
        // Else display error and ask to retransmit the data again.
        if (remainder1.equals(buffer)){
            System.out.println("\n\u001B[32mNo Error Detected....\u001B[39m\n");

            for (i = 0; i < numberofblocks -1 ; i++){
                code.addAll(segment[i]);
            }

            // j helps in numbering the segments.
            j =1;
            // Displaying the 4 segment discarding the 5th segment.
            for ( i =0; i < numberofblocks - 1 ; i++){
                System.out.println("Segment["+j+"]: "+segment[i].toString().replaceAll("[\\[\\],]",""));
                j++;
            }

            //Final code after discarding the last segment.
            System.out.println("\nFinal Data: " + code.toString().replaceAll("[\\[\\],]",""));
        }
        else{
            //Displaying error message and to retransmit the data again.
            System.out.println("\n\u001B[31mError Detected....\nPlease Retransmit The Data Again. \u001B[39m");
        }

    }
}

/*
=> Example1:- (No error)
Entered data is:  1011001110101011010110101101010101110000
Number of blocks:  5
Bits in a block:  8

No Error Detected....

Segment[1]: 1 0 1 1 0 0 1 1
Segment[2]: 1 0 1 0 1 0 1 1
Segment[3]: 0 1 0 1 1 0 1 0
Segment[4]: 1 1 0 1 0 1 0 1

Final Data: 1 0 1 1 0 0 1 1 1 0 1 0 1 0 1 1 0 1 0 1 1 0 1 0 1 1 0 1 0 1 0 1


=> Example2:- (Error)

Entered data is:  1011001110101011010110101101010101110001
Number of blocks:  5
Bits in a block:  8

Error Detected....
Please Retransmit The Data Again.

 */