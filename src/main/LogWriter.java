package main;

import java.io.FileWriter;
import java.io.IOException;
import java.util.Date;

public class LogWriter {

	public static void writeLog(LogType logType, String error) {
		try {
			FileWriter errLog = new FileWriter(stringifyLogType(logType) + ".txt", true);
			errLog.write(new Date().toString() + " - " + error + "\n\n");
			errLog.close();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	
	private static String stringifyLogType(LogType type) {
		switch(type) {
		case ERROR:
			OopsieCounter.addError();
			return "errLog";
		case ALERT:
			OopsieCounter.addAlert();
			return "alertLog";
		default:
			return "unknownLog";
		}
	}
}