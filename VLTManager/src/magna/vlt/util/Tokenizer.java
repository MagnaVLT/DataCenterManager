package magna.vlt.util;

import java.util.Vector;

public class Tokenizer {

	public static String getToken(String str, String delim, int loc){
		String[] tokens = str.split(delim);
		if(tokens.length< loc) return null;
		else return tokens[loc];
	}
	
	public static String concatWithDelim(String delim, Vector<String> str){
		String result = "";
		if(str.size()>1){
			result+=str.get(0);
			for(int i = 1 ; i < str.size() ; i++)
				result+= delim + str.get(i);
		}else if (str.size()==1){
			result+=str.get(0);	
		}
		return result;
	}
	
	public static String concatWithDelim(String delim, String str1, String str2){
		return str1 + delim + str2;
	}
	
}
