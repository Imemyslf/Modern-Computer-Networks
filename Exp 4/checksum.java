import java.util.ArrayList;
import java.util.Collections;
import java.util.Scanner;

public class checksum {
    
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
        // To make it human readable.
        Collections.reverse(remainder);
        return remainder;
    }
    @SuppressWarnings("unchecked")
    public static void main(String[] args) {

        checksum cs = new checksum();
        Scanner s = new Scanner(System.in);
        int i,j,sum,ptr = 0,numberofblocks = 0,blocksize = 0,bs=0;
        
        ArrayList<Integer> remainder1 = new ArrayList<>();  //To keep the main remainder for calculations.
        ArrayList<Integer> remainder2 = new ArrayList<>(); // Temporary remainder for calculations.
        ArrayList<Integer> newsegment1 = new ArrayList<>(); // Toggle arounds segments.
        ArrayList<Integer> newsegment2 = new ArrayList<>(); // Toggle arounds segments.
        ArrayList<Integer> buffer = new ArrayList<>();  // buffer array storing 0 0 0 0 0 0 0 0 1 to add to remainder if carry == 1 at the end.
        ArrayList<Integer> code = new ArrayList<>(); // Storing the actual codeword.

        //Taking user input for size of the data
        System.out.print("\nEnter the size of the data: ");
        int sizeofdata = s.nextInt();

        // Declaring and initializing variable data to the length of the size of the data entered by the user.
        int data[] = new int[sizeofdata];
        
        // taking input from the user about the data
        System.out.println("\nEnter the data: ");
        for (i = 0 ; i < sizeofdata; i++){
            int k = s.nextInt();
            data[i] = k;
        }

        // Declaring sum = sizeofdata for future opreations.
        sum = sizeofdata;

        // Finding the numbers of blocks and block size.
        for (i = 1; i < sizeofdata; i++)
        {
            if (sizeofdata % i == 0)
            {
                j = sizeofdata / i;
                if (sum > i + j)
                {
                    sum = i + j;
                    code.clear();
                    code.add(i);
                    code.add(j);
                }
            }
        }

        //Initializing the number of blocks.
        numberofblocks = code.get(0);
        // Initializing the block size.
        blocksize = code.get(1);
        // Clearing the code list for not having any garbaze value where in future we add the actual code.
        code.clear();

        //Adding numberofblocks of  sub-list in segment Example if data in bit is 32 den number of block  = 4 so 4 sub-list to be created.
        ArrayList<Integer>[] segment = new ArrayList[numberofblocks];
        
        //Displaying the data before ading the codeword
        System.out.print("\nEntered data is:\n");
        for (i = 0 ; i < sizeofdata; i++){
            System.out.print(data[i]+" ");
        }

        //Displaying Number of blocks in the segment.
        System.out.println("\nNumber of blocks:  " + numberofblocks);

        //Displaying the number of bits in each block respectively.
        System.out.println("\nBits in a block:  "+ blocksize);

        
        
        // Initializing buffer list.
        buffer.add(1);
        for ( i = 0; i < blocksize - 1; i++) {
            buffer.add(0);
        }
        
        // Initializing segment list into 4 sub-list of segment.
        for ( i = 0; i < numberofblocks; i++) {
            segment[i] = new ArrayList<Integer>();
        }

        //Adding 8 bit data to each segment list.
        for ( i = 0; i < numberofblocks; i++) {
            for ( j = ptr; j < ptr + blocksize; j++) {
                segment[i].add(data[j]);
            }
            code.addAll(segment[i]);
            ptr += blocksize;
        }

        j = 1;
        //Displaying segments before code generation.
        // for ( i = 0; i < numberofblocks; i++){
            //     System.out.print("Segment["+ (i + 1) +"]: "+segment[i]+"\n");    
            // }
        System.out.print("\nSegments before code generation:");
        for ( i =0; i < code.size(); i++){
            if (i == bs) {
                System.out.print("\nSegment["+j+"]\t");
                bs = bs + 8 ;
                j++;
            }
            System.out.print(code.get(i)+" ");    
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

                //calling remain function and assigning the value to remainder1.
                remainder1 = cs.remain(newsegment1, newsegment2, remainder1, blocksize);
                
                // If after the operation if any carry is left then add the buffer to the newly obtained remainder1 to get final remainder1.
                if (cs.carry == 1) {
                    // Reversing remainnder for calculation purpose.
                    Collections.reverse(remainder1);

                    //Clearing the temporary reaminder for no garbez data in it.
                    remainder2 = new ArrayList<>();

                    //Initializing carry = 0 since we are gonna calculate from start.
                    cs.carry = 0;

                    //Calling remain function and assigning the value to remainder1.
                    remainder1 = cs.remain(remainder1,buffer,remainder2,blocksize);

                }
                
                // Increment ptr to get next segment.
                ptr++;

                // If ptr == numberofblocks i.e segment size that is in the segment there are 4 sub-list present if ptr == 4 den stop the reinitialization 
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

        //Complimenting the Final remainder1 bits to get the actual code.
        for (i = 0; i < remainder1.size(); i++){
            remainder1.set(i,remainder1.get(i) == 1? 0 : 1 );
        }

        code.clear();
        //Combing all the 4 sub-list of segment and remainder1 to get the Final Codeword.
        for (  i  = 0; i < numberofblocks + 1; i++ ){
            if (i < numberofblocks ){
                    code.addAll(segment[i]);
                }
                else{
                    code.addAll(remainder1);
                }
            }
        
        // Helps in numbering the segments.
        j = 1;
        bs = 0;
        //Displaying the Segments.
        System.out.print("\n\nSegments after code generation:");
        for ( i =0; i < code.size(); i++){
            if (i == bs) {
                System.out.print("\nSegment["+j+"]\t");
                bs = bs + 8 ;
                j++;
            }
            System.out.print(code.get(i)+" ");    
        }
        
        //Displaying the Codeword as String.
        System.out.println("\n\nCodeword: " + code.toString().replaceAll("[\\[\\],]","")+"\n");
    }
}

/*
=> Example:-

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