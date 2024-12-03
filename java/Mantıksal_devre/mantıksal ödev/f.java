import java.util.*; 
import java.lang.*; 
import java.io.*; 
  
class TruthTable 
{ 
	public static void main (String[] args) throws java.lang.Exception 
	{ 
		int A,B,C,D; 
		System.out.println("A | B | C | D | A+B+C+D"); 
		for(A=0;A<=1;A++) 
		{ 
			for(B=0;B<=1;B++) 
			{ 
                for(C=0;C<=1;C++) 
                { 
                    for(D=0;D<=1;D++) 
                    { 
                        System.out.println(A+" | "+B+" | "+C+" | "+D+" | "+ (A|B|C|D)); 
                    } 
                } 
			} 
		} 
	} 
} 