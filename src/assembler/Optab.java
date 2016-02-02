package assembler;

import java.util.HashMap;

public class Optab {
	 HashMap< String , String > optab ;	
	public Optab(){
		optab = new HashMap< >();
		
		optab.put( "ADD" ,  "18" );
		optab.put( "ADDF" ,  "58" );
		optab.put( "AND" , "40" );
		optab.put( "COMP" , "28" );
		optab.put( "DIV" , "24" );
		optab.put(  "J" , "3C" );
		optab.put( "JEQ" , "30" );
		optab.put( "JGT" , "34" );
		optab.put( "JLT" , "38" );
		optab.put( "JSUB" , "48" );
		optab.put( "LDA" , "00" );
		optab.put(  "LDB" , "68" );
		optab.put( "LDCH" , "50" );
		optab.put( "LDF" , "70" );
		optab.put( "LDL" , "08" );
		optab.put( "LDS" , "6C" );
		optab.put( "LDX" , "04" );
		optab.put( "LPS" , "D0" );
		optab.put( "MUL" , "20" );
		optab.put( "MULF" , "60" );
		optab.put( "OR" , "44" );
		optab.put( "RD" , "D8" );
		optab.put( "RSUB" , "4C" );
		optab.put( "SSK" , "EC" );
		optab.put( "STA" , "0C" );
		optab.put( "STB" , "78" );
		optab.put( "STCH" , "54" );
		optab.put( "STF" , "80" );
		optab.put( "STI" , "D4" );
		optab.put( "STL" , "14" );
		optab.put( "STS" , "7C" );
		optab.put( "STSW" , "E8" );
		optab.put( "STT" , "84" );
		optab.put( "STX" , "10" );
		optab.put( "SUB" , "1C" );
		optab.put( "SUBF" , "5C" );
		optab.put( "TD" , "E0" );
		optab.put( "TIX" , "2C" );
		optab.put( "WD" , "DC" );
	}
	
}
