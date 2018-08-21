package logger;

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

	}
}
