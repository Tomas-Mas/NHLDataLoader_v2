package main;

import java.text.SimpleDateFormat;
import java.util.Date;

public class ComparatorUtil {

	public static boolean nullableStringsEquals(String a, String b) {
		if(a == null && b == null)
			return true;
		if((a == null && b != null) || (a != null && b == null))
			return false;
		if(a.equals(b))
			return true;
		else
			return false;
	}
	
	public static boolean dateBeforeDate2(String date1, String date2) {
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
		boolean ret = false;
		
		try {
			Date d1 = sdf.parse(date1);
			Date d2 = sdf.parse(date2);
			ret = d1.before(d2);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return ret;
	}
}
