import java.util.*;

class CRC {
   
    private int ptr;
    public ArrayList<Integer> dividend = new ArrayList<Integer>();
    public ArrayList<Integer> divisor = new ArrayList<Integer>();

    public ArrayList<Integer> remain(ArrayList<Integer> remainder,ArrayList<Integer> divid) {
                remainder.remove(0);
                remainder.add(dividend.get(ptr));
                divid.clear();
                divid = new ArrayList<>(remainder);
                return divid;
    }
    public void CRC_codegenerator(int m,int n){
        int i;
        ArrayList<Integer> divid = new ArrayList<Integer> (dividend);
        ArrayList<Integer> divir = new ArrayList<Integer> (divisor);
        ArrayList<Integer> remainder = new ArrayList<>();
        ArrayList<Integer> quotient = new ArrayList<>();
        
        for (int k = 0; k < (( dividend.size() - divisor.size() ) + 1); k++){
            quotient.add(divid.get(0));
            if (divid.get(0) == 1){
                
                divir = new ArrayList<>(divisor);
                
                for ( i=0; i < divisor.size(); i++){
                    
                    remainder.add(divid.get(i) ^ divir.get(i));
                }
                
                if ( k == 0){
                    
                    ptr = i;                    
                    divid = remain(remainder,divid);
                    remainder.clear();
                    divir.clear();
                }

                else{
                    
                    ptr = this.ptr + 1;
                    if (ptr != dividend.size()){                            
                    divid = remain(remainder,divid);
                    remainder.clear();
                    divir.clear();
                    }
                }    
            }

            else {
                divir.clear();
                
                for ( i=0; i < divisor.size(); i++){
                    
                    divir.add(0);
                }
                
                for ( i=0; i < divisor.size(); i++){
                    
                    remainder.add(divid.get(i) ^ divir.get(i));
                }

                if ( k == 0){
                    
                    ptr = i;
                    divid = remain(remainder,divid);
                    remainder.clear();
                    divir.clear();
                }
                
                else {
                    
                    ptr = this.ptr + 1;
                    
                    if (ptr != dividend.size()){                            
                        
                        divid = remain(remainder,divid);
                        remainder.clear();
                        divir.clear();
                    }  
                }
               
            }
        }
    
        ArrayList<Integer> crccode = new ArrayList <Integer> (remainder);
        crccode.remove(0);

        for ( i = (dividend.size() -1) ; i > m -1 ; i--) {
            
            dividend.remove(i);
        }
    
        for ( i = 0; i < (divisor.size() - 1); i++) {
                
            dividend.add(crccode.get(i));
        }
        
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
        

        System.out.print("\n Enter the size of dividend:\t");
        int sizeofdividend = scanner.nextInt();

        System.out.print("\n Enter the size of divisor:\t");
        int sizeofdivisor = scanner.nextInt();
        
        System.out.print("\n Enter the dividend:\t");
        for ( i = 0; i < sizeofdividend; i++){
            
            int s1 = scanner.nextInt();
            k.dividend.add(s1);
        }
        
        System.out.print("\n Enter the divisor:\t");
        for ( i = 0; i < sizeofdivisor; i++){
            
            int s2 = scanner.nextInt();
            k.divisor.add(s2);
        }
        
        for ( i = 0; i < (k.divisor.size() - 1); i++){
            
            k.dividend.add(0);
        }
        
        System.out.println("\n");
        System.out.println(" ----------------------------------------");
        System.out.println("| Dividend:  | "+ k.dividend.toString().replaceAll("[\\[\\],]","")+"\t |");
        System.out.println("| Divisor:   | "+ k.divisor.toString().replaceAll("[\\[\\],]","")+"\t\t\t |");
        k.CRC_codegenerator(sizeofdividend,sizeofdivisor);
        scanner.close();
    }
}

/*
Example:- 


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