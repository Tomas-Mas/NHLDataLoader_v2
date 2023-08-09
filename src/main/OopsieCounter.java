package main;

public class OopsieCounter {

	public static int alerts = 0;
	public static int errors = 0;
	
	public static void addAlert() {
		alerts++;
	}
	
	public static void addError() {
		errors++;
	}
	
	public static void print() {
		if(alerts > 0) {
			System.out.println("\n" + alerts + " allerts have been logged");
		}
		if(errors > 0) {
			System.out.println("\n" + errors + " errors have been logged");
		}
	}
}
