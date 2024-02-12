import java.util.*;

public class crcdetection {

    //Initialization of ptr as global variable to toggle around the errordetection() function and remain() function effectively.
    private int ptr;
    
    // Initialization of dividend and divisor as gloabl list.
    public ArrayList<Integer> dividend = new ArrayList<Integer> ();
    public ArrayList<Integer> divisor = new ArrayList<Integer> ();

    // remain() function.
    public ArrayList<Integer> remain(ArrayList<Integer> remainder,ArrayList<Integer> divid) {
        //Remove the first elemnt of remainder.
        remainder.remove(0);
        // Add ptr to remainder.
        remainder.add(dividend.get(ptr));
        // Clearing divid list for further opearations.
        divid.clear();
        // Initialize divid = remainder for further opreations.
        divid = new ArrayList<Integer> (remainder);

        return divid;
    }

    // errordetection() function.
    public void errordetection(int sizeofdividend,int sizeofdivisor){
        int i;
        ArrayList <Integer> divid = new ArrayList <Integer> (dividend); // Initializing temporary dividend list for calculation purpose.
        ArrayList <Integer> divir = new ArrayList <Integer> (divisor); // Initializing temporary dividend list for calculation purpose.
        ArrayList <Integer> zeros = new ArrayList <Integer> (); // creating a zero list with empty elements.
        ArrayList <Integer> quotient = new ArrayList <Integer> (); // creating a quotient list to add the quotient elements.
        ArrayList <Integer> remainder = new ArrayList <Integer> (); // creating a remainder list to add the remainder elements.

        // Adding divisor.size() = 4 numbers of zero to zeros list.
        for (i = 0; i < divisor.size(); i++){
            zeros.add(0);
        }

        // The loop will run till (dividend.size() - divisor.size()) + 1 i.e Example (15 - 6) + 1 = 10 times.
        for (int k = 0; k < (dividend.size() - divisor.size()) + 1; k++){
            quotient.add(divid.get(0)); // Adding the first element of divid to quotient.
            
            // If first element is 1 the go inside the if statement.
            if (divid.get(0) == 1){
                // Initialize divir = dividend. Since we will clear divir multiple times so we have initialize divir = divisor when first element of divid is 1.
                divir = new ArrayList <Integer> (divisor);

                // Applying xor opreation on divid and divr and add it to remainder.
                for ( i = 0; i < sizeofdivisor; i++){
                    remainder.add( divid.get(i) ^ divir.get(i) );
                }

                //if it is first iteration when divid = 1 then...
                if ( k == 0 ){
                    //initialize ptr = i to add the next elemt of dividend.
                    ptr = i;

                    //Calling remain() function and assigning the value to newly obtained divid.
                    divid = remain(remainder,divid);
                    //Clearing remainder and divid to initialize the new values for further opreations.
                    remainder.clear();
                    divir.clear();
                    
                }
                else
                {
                    // Incrementing ptr.
                    ptr = this.ptr + 1;

                    // If ptr != dividend.size() then i.e ptr != 15(example) then do the opreations. When ptr == 15 dont do  anything.
                    if (ptr != dividend.size()){
                        //Calling remain() function and assigning the value to newly obtained divid.
                        divid = remain(remainder,divid);
                        //Clearing remainder and divid to initialize the new values.
                        remainder.clear();
                        divir.clear();
                        
                    }
                }
            }
            else {
                // If the first element of divid is 0 then initialize divir to zero.
                for (i = 0; i< divisor.size(); i++){
                    divir.add(0);
                }

                // Applying xor opreation on divid and divr and add it to remainder.
                for ( i = 0; i < divisor.size(); i++) {
                    remainder.add(divid.get(i) ^ divir.get(i));
                }
                
                //if it is first iteration when divid = 0 then...
                if (k == 0)
                {
                    //initialize ptr = i to add the next elemt of dividend.
                    ptr = i;
                    //Calling remain() function and assigning the value to newly obtained divid.
                    divid = remain(remainder,divid);
                    //Clearing remainder and divid to initialize the new values for further opreations.
                    remainder.clear();
                    divir.clear();
                    
                }
                else
                {
                    // Incrementing ptr.
                    ptr = this.ptr + 1;

                    // If ptr != dividend.size() then i.e ptr != 15(example) then do the opreations. When ptr == 15 dont do  anything.
                    if (ptr != dividend.size())
                    {
                        //Calling remain() function and assigning the value to newly obtained divid.
                        divid = remain(remainder,divid);
                        //Clearing remainder and divid to initialize the new values for further opreations.
                        remainder.clear();
                        divir.clear();
                        
                    }
                }
            }
        }
        
        // If remainder = 0 then display No error message else display Error detected.
        if (remainder.equals(zeros)){
            System.out.println("\u001B[32mNo error");
            
            // Initializing the last index of dividend to j.
            int j = dividend.size() - 1;
            // Remove divisor.size() - 1  bits (Example) 6 - 1 = 5 bits from dividend.
            for (i = 0; i < divisor.size() - 1; i++){
                dividend.remove(j);
                j--;
            }

            //Display the Final Data.
            System.out.println("Actual data: "+ dividend.toString().replaceAll("[\\[\\],]","")+"\u001B[39m");
        }
        else {
            System.out.println(" \u001B[31mError detected: \n Please Retransmit The Data.\u001B[39m " );
        }
    }

    @SuppressWarnings("resource")
    public static void main(String[] args) {
        crcdetection cd = new crcdetection();
        Scanner s = new Scanner(System.in);
        int i;

        // Initializing the size of dividend.
        System.out.println("Enter the size of dividend: ");
        int sizeofdividend = s.nextInt();

        //Initializing the size of divisor.
        System.out.println("Enter size of divisor: ");
        int sizeofdivisor = s.nextInt();

        //Initializing the dividend list.
        System.out.println("Enter the dividend: ");
        for( i = 0; i < sizeofdividend; i++ ){
            int k = s.nextInt();
            cd.dividend.add(k);
        }

        //Initializing divisor list.
        System.out.println("Enter the divisor: ");
        for( i = 0; i < sizeofdivisor; i++ ){
            int k = s.nextInt();
            cd.divisor.add(k);
        }

        // Calling errordetection() function.
        cd.errordetection(sizeofdividend,sizeofdivisor);
        s.close();
    }
}

/*
 
Enter the size of dividend: 
15
Enter size of divisor:
6
Enter the dividend:
1 0 1 0 0 0 1 1 0 1 0 1 1 1 0
Enter the divisor:
1 1 0 1 0 1
No error
Actual data: 1 0 1 0 0 0 1 1 0 1

 */