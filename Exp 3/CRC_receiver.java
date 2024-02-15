import java.util.*;

public class CRC_receiver 
{

    //Initialization of ptr as global variable to toggle around the errordetection() function and remain() function effectively.
    private int ptr;
    
    // Initialization of dividend and divisor as gloabl list.
    public ArrayList<Integer> dividend = new ArrayList<Integer> ();
    public ArrayList<Integer> divisor = new ArrayList<Integer> ();

    // remain() function.
    public ArrayList<Integer> remain(ArrayList<Integer> remainder,ArrayList<Integer> divid) 
    {
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
    public void errordetection(int sizeofdividend,int sizeofdivisor)
    {
        int i;
        ArrayList <Integer> divid = new ArrayList <Integer> (dividend); // Initializing temporary dividend list for calculation purpose.
        ArrayList <Integer> zeros = new ArrayList <Integer> (); // creating a zero list with empty elements.
        ArrayList <Integer> quotient = new ArrayList <Integer> (); // creating a quotient list to add the quotient elements.
        ArrayList <Integer> remainder = new ArrayList <Integer> (); // creating a remainder list to add the remainder elements.

        // Adding divisor.size() = 4 numbers of zero to zeros list.
        for (i = 0; i < divisor.size(); i++)
        {
            zeros.add(0);
        }

        // The loop will run till (dividend.size() - divisor.size()) + 1 i.e Example (15 - 6) + 1 = 10 times.
        for (int k = 0; k < (dividend.size() - divisor.size()) + 1; k++){
            quotient.add(divid.get(0)); // Adding the first element of divid to quotient.
            
            // If first element is 1 the go inside the if statement.
            if (divid.get(0) == 1)
            {
                // Applying xor opreation on divid and divisor and add it to remainder.
                for ( i = 0; i < sizeofdivisor; i++)
                {
                    remainder.add( divid.get(i) ^ divisor.get(i));
                }

                //if it is first iteration when divid = 1 then...
                if ( k == 0 )
                {
                    //initialize ptr = i to add the next elemt of dividend.
                    ptr = i;

                    //Calling remain() function and assigning the value to newly obtained divid.
                    divid = remain(remainder,divid);
                    //Clearing remainder to initialize the new values for further opreations.
                    remainder.clear();                    
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
                        //Clearing remainder to initialize the new values.
                        remainder.clear();                        
                    }
                }
            }
            else {

                // Applying xor opreation on divid and zero that add it to remainder.
                for ( i = 0; i < divisor.size(); i++) 
                {
                    remainder.add(divid.get(i) ^ 0);
                }
                
                //if it is first iteration when divid = 0 then...
                if (k == 0)
                {
                    //initialize ptr = i to add the next elemt of dividend.
                    ptr = i;
                    //Calling remain() function and assigning the value to newly obtained divid.
                    divid = remain(remainder,divid);
                    //Clearing remainder to initialize the new values for further opreations.
                    remainder.clear();
                    // divir.clear();
                    
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
                        //Clearing remainder to initialize the new values for further opreations.
                        remainder.clear();
                        // divir.clear();
                        
                    }
                }
            }
        }
        
        // If remainder = 0 then display No error message else display Error detected.
        if (remainder.equals(zeros))
        {
            System.out.println("\n\u001B[32mNo error");
            
            // Initializing the last index of dividend to j.
            int j = dividend.size() - 1;
            // Remove divisor.size() - 1  bits (Example) 6 - 1 = 5 bits from dividend.
            for (i = 0; i < divisor.size() - 1; i++)
            {
                dividend.remove(j);
                j--;
            }

            //Display the Final Data.
            System.out.println("Actual data: "+ dividend.toString().replaceAll("[\\[\\],]","")+"\u001B[39m");
        }
        else 
        {
            System.out.println("\n\u001B[31mError detected: \nPlease Retransmit The Correct Data.\u001B[39m " );
        }
    }

    @SuppressWarnings("resource")
    public static void main(String[] args) 
    {
        CRC_receiver cd = new CRC_receiver();
        Scanner s = new Scanner(System.in);
        int i;

        // Initializing the size of dividend.
        System.out.print("\nEnter the size of dividend: ");
        int sizeofdividend = s.nextInt();

        //Initializing the size of divisor.
        System.out.print("\nEnter size of divisor: ");
        int sizeofdivisor = s.nextInt();

        //Initializing the dividend list.
        System.out.print("\nEnter the dividend: ");
        for( i = 0; i < sizeofdividend; i++ )
        {
            int k = s.nextInt();
            cd.dividend.add(k);
        }

        //Initializing divisor list.
        System.out.print("\nEnter the divisor: ");
        for( i = 0; i < sizeofdivisor; i++ )
        {
            int k = s.nextInt();
            cd.divisor.add(k);
        }

        // Calling errordetection() function.
        cd.errordetection(sizeofdividend,sizeofdivisor);
        s.close();
    }
}

/*
=> Example:- (No Error) 

Enter the size of dividend: 9

Enter size of divisor: 4

Enter the dividend: 1 0 0 1 0 0 0 0 1

Enter the divisor: 1 1 0 1

No error
Actual data: 1 0 0 1 0 0

=> Example:- (Error Detected)

Enter the size of dividend: 6

Enter size of divisor: 4

Enter the dividend: 1 1 0 1 0 1

Enter the divisor: 1 0 0 1

Error detected:
Please Retransmit The Correct Data.

 */