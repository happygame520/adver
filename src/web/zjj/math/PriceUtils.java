package web.zjj.math;

public class PriceUtils {
	
	public static double getUnitPrice(int uid, int appid){
		if(uid == 100)
			return 1.2;
		
		return 1.2;//默认单价1.2元
	}

}
