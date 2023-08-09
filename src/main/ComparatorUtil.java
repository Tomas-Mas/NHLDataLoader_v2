package main;

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
}
