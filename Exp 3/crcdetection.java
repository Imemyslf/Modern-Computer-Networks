import java.util.*;

public class crcdetection {

    private int ptr;
    public ArrayList<Integer> dividend = new ArrayList<Integer> ();
    public ArrayList<Integer> divisor = new ArrayList<Integer> ();

    public ArrayList<Integer> remain(ArrayList<Integer> remainder,ArrayList<Integer> divid) {
        remainder.remove(0);
        remainder.add(dividend.get(ptr));
        divid = new ArrayList<Integer> (remainder);
        return divid;
    }

    public void errordetection(int sizeofdividend,int sizeofdivisor){
        int i;
        ArrayList <Integer> divid = new ArrayList <Integer> (dividend);
        ArrayList <Integer> divir = new ArrayList <Integer> (divisor);
        ArrayList <Integer> zeros = new ArrayList <Integer> (4);
        ArrayList <Integer> quotient = new ArrayList <Integer> ();
        ArrayList <Integer> remainder = new ArrayList <Integer> ();

        for (i = 0; i < divisor.size(); i++){
            zeros.add(0);
        }

        for (int k = 0; k < (dividend.size() - divisor.size()) + 1; k++){
            quotient.add(divid.get(0));
            if (divid.get(0) == 1){
                divir = new ArrayList <Integer> (divisor);

                for ( i = 0; i < sizeofdivisor; i++){
                    remainder.add( divid.get(i) ^ divir.get(i) );
                }

                if ( k == 0 ){
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
                for (i = 0; i< divisor.size(); i++){
                    divir.add(0);
                }

                for ( i = 0; i < divisor.size(); i++) {
                    remainder.add(divid.get(i) ^ divir.get(i));
                }
                
                if (k == 0){
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
        }
        
        if (remainder.equals(zeros)){
            System.out.println("\u001B[32mNo error");
            
            int j = dividend.size() - 1;
            for (i = 0; i < divisor.size() - 1; i++){
                dividend.remove(j);
                j--;
            }

            System.out.println("Actual data: "+ dividend.toString().replaceAll("[\\[\\],]","")+"\u001B[37m");
        }
        else {
            System.out.println(" \u001B[31mError detected: \n Please Retransmit The Data.\u001B[37m " );
        }
    }

    @SuppressWarnings("resource")
    public static void main(String[] args) {
        crcdetection cd = new crcdetection();
        Scanner s = new Scanner(System.in);
        int i;

        System.out.println("Enter the size of dividend: ");
        int sizeofdividend = s.nextInt();

        System.out.println("Enter size of divisor: ");
        int sizeofdivisor = s.nextInt();

        System.out.println("Enter the dividend: ");
        for( i = 0; i < sizeofdividend; i++ ){
            int k = s.nextInt();
            cd.dividend.add(k);
        }

        System.out.println("Enter the divisor: ");
        for( i = 0; i < sizeofdivisor; i++ ){
            int k = s.nextInt();
            cd.divisor.add(k);
        }

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