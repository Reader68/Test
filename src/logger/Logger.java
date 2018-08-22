package logger;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.nio.file.StandardOpenOption;
import java.text.SimpleDateFormat;
import java.util.Date;

public class Logger {
	final static String fileName = "log.txt";
	private Class<?> logClass;
	
	public static enum logType { 
		INFO{
			@Override
			public String toString() {
				return "[INFO]";
			}
		},
		ERROR{
			@Override
			public String toString() {
				return "[ERROR]";
			}
		}
	}
	
	public Logger(Class<?> klass) {
		logClass = klass;
	}

	public void logError( String msg ) {
		log( logType.ERROR, msg );
	}
	
	public void logInfo( String msg ) {
		log( logType.INFO, msg );
	}
	
	private void log( logType type, String msg ) {
		StringBuilder sb = new StringBuilder();
		sb.toString().getBytes();
		try {
			Files.write(Paths.get(fileName),
					new StringBuilder().append(type).
					append(new SimpleDateFormat("[yyyy-MM-dd hh:mm]").format(new Date())).
					append(logClass.getSimpleName()).append(" : ").
					append(msg).append("\n").toString().getBytes(),
				StandardOpenOption.CREATE, StandardOpenOption.APPEND);
		}
		catch (IOException e){
			System.out.println(e.getLocalizedMessage());
		}
	}
}
