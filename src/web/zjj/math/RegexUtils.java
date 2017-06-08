package web.zjj.math;

import java.util.regex.Pattern;

public class RegexUtils {
	
	public static boolean test(String regex, String content){
		 if(content!=null){
			for(int i=0; i<content.length(); i++) {
				Character ch = content.charAt(i);
				if(Pattern.matches(regex, ""+ch)){
					return true;
					
				} 
			}
		 } 
		
		return false;
	}

}
