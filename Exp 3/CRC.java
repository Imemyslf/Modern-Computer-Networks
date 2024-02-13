import java.util.*;

class CRC 
{
   
    //Initialization of ptr as global variable to toggle around the errordetection() function and remain() function effectively.
    private int ptr;
    
    // Initialization of dividend and divisor as gloabl list.
    public ArrayList<Integer> dividend = new ArrayList<Integer>();
    public ArrayList<Integer> divisor = new ArrayList<Integer>();

    // remain() function.
    public ArrayList<Integer> remain(ArrayList<Integer> remainder,ArrayList<Integer> divid) 
    {
        //Remove the first elemnt of remainder.        
        remainder.remove(0);
        // Add ptr to remainder.
        remainder.add(dividend.get(ptr));
        // Clearing divid list for further operations.
        divid.clear();
        // Initialize divid = remainder for further opreations.
        divid = new ArrayList<>(remainder);
        return divid;
    }

    // CRC_codegenerator() function.
    public void CRC_codegenerator(int m,int n)
    {
        int i;
        ArrayList<Integer> divid = new ArrayList<Integer> (dividend); // Initializing temporary dividend list for calculation purpose.
        ArrayList<Integer> divir = new ArrayList<Integer> (divisor); // Initializing temporary dividend list for calculation purpose.
        ArrayList<Integer> remainder = new ArrayList<>(); // creating a remainder list to add the remainder elements.
        ArrayList<Integer> quotient = new ArrayList<>(); // creating a quotient list to add the quotient elements.
        
        // The loop will run till (dividend.size() - divisor.size()) + 1 i.e Example (8 - 4) + 1 = 5 times.
        for (int k = 0; k < (( dividend.size() - divisor.size() ) + 1); k++)
        {
            quotient.add(divid.get(0)); // Adding the first element of divid to quotient.
            
            // If first element is 1 the go inside the if statement else if first element divid = 0 then in else part.
            if (divid.get(0) == 1)
            {
                
                // Initialize divir = dividend. Since we will clear divir multiple times so we have initialize divir = divisor when first element of divid is 1.
                divir = new ArrayList<>(divisor);
                
                // Applying xor opreation on divid and divr and add it to remainder.
                for ( i=0; i < divisor.size(); i++)
                {
                    
                    remainder.add(divid.get(i) ^ divir.get(i));
                }
                
                //if it is first iteration when divid = 1 then...
                if ( k == 0)
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

                    // If ptr != dividend.size() then i.e ptr != 8(example) then do the opreations. When ptr == 8 dont do  anything.
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

            else 
            {
                // If the first element of divid is 0 then initialize divir to zero.
                for ( i=0; i < divisor.size(); i++)
                {
                    divir.add(0);
                }
                
                // Applying xor opreation on divid and divr and add it to remainder.
                for ( i=0; i < divisor.size(); i++)
                {
                    remainder.add(divid.get(i) ^ divir.get(i));
                }

                //if it is first iteration when divid = 0 then...
                if ( k == 0)
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
                    
                    // If ptr != dividend.size() then i.e ptr != 8(example) then do the opreations. When ptr == 8 dont do  anything.
                    if (ptr != dividend.size()){                            
                        //Calling remain() function and assigning the value to newly obtained divid.
                        divid = remain(remainder,divid);
                        //Clearing remainder and divid to initialize the new values for further opreations.
                        remainder.clear();
                        divir.clear();
                    }  
                }
               
            }
        }
    
        //Initializing crccode list to finnal remainder.
        ArrayList<Integer> crccode = new ArrayList <Integer> (remainder);
        crccode.remove(0);

        // Removing the k - 1  bits that were added in the beginnging.
        for ( i = (dividend.size() -1) ; i > m -1 ; i--) 
        {            
            dividend.remove(i);
        }
        
        // Addinng the crccode to the dividend.
        for ( i = 0; i < (divisor.size() - 1); i++) {
                
            dividend.add(crccode.get(i));
        }
        
        // Displaying Codeword,Quotient, Remainder,and CRC code.. 
        System.out.println("| Codeword:  | "+ dividend.toString().replaceAll("[\\[\\],]","")+"\t |");
        System.out.println("| Quotient:  | "+ quotient.toString().replaceAll("[\\[\\],]","")+"\t\t |");
        System.out.println("| Remainder: | "+ remainder.toString().replaceAll("[\\[\\],]","")+"\t\t\t |");
        System.out.println("| CRC code:  | "+ crccode.toString().replaceAll("[\\[\\],]","")+"\t\t\t |");
        System.out.println(" ----------------------------------------");
        System.out.println("\n");
    }
   
    @SuppressWarnings("resource")
    public static void main(String[] args) {
       
        CRC k = new CRC();
        Scanner scanner = new Scanner(System.in);
        int i;
        
        // Intialize the size of dividend.
        System.out.print("\n Enter the size of dividend:\t");
        int sizeofdividend = scanner.nextInt();

        //  Initialize the size of divisor.
        System.out.print("\n Enter the size of divisor:\t");
        int sizeofdivisor = scanner.nextInt();
        
        // Initial the dividend list.
        System.out.print("\n Enter the dividend:\t");
        for ( i = 0; i < sizeofdividend; i++){
            
            int s1 = scanner.nextInt();
            k.dividend.add(s1);
        }
        
        // Initialize the divisor list.
        System.out.print("\n Enter the divisor:\t");
        for ( i = 0; i < sizeofdivisor; i++){
            
            int s2 = scanner.nextInt();
            k.divisor.add(s2);
        }
        
        // Adding (divisor.size() - 1) zeros to the dividend. 
        for ( i = 0; i < (k.divisor.size() - 1); i++){
            
            k.dividend.add(0);
        }
        
        //Display dividend and divisor.
        System.out.println("\n");
        System.out.println(" ----------------------------------------");
        System.out.println("| Dividend:  | "+ k.dividend.toString().replaceAll("[\\[\\],]","")+"\t |");
        System.out.println("| Divisor:   | "+ k.divisor.toString().replaceAll("[\\[\\],]","")+"\t\t\t |");
        
        // Calling CRC_codegenerator() function.
        k.CRC_codegenerator(sizeofdividend,sizeofdivisor);
        scanner.close();
    }
}

/*
=> Example:- 


 Enter the size of dividend:    8

 Enter the size of divisor:     4

 Enter the dividend:    1 0 0 1 1 1 0 1

 Enter the divisor:    1 0 0 1


 ----------------------------------------
| Dividend:  | 1 0 0 1 1 1 0 1 0 0 0     |
| Divisor:   | 1 0 0 1                   |
| Codeword:  | 1 0 0 1 1 1 0 1 1 0 0     |
| Quotient:  | 1 0 0 0 1 1 0 0           |
| Remainder: | 0 1 0 0                   |
| CRC code:  | 1 0 0                     |
 ----------------------------------------


 */