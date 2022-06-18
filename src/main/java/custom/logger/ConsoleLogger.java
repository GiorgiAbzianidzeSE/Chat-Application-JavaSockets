package custom.logger;

public class ConsoleLogger extends AbstractLogger {

    public ConsoleLogger(){
        this(null);
    }

    public ConsoleLogger(LoggingLevel loggingLevel) {
        super(loggingLevel);
    }

    @Override
    protected void outputMessage(LoggingLevel level, String message) {
        if(level.ordinal() > LoggingLevel.INFO.ordinal()){
            System.err.println("[" + level + "]" + message);
        }else {
            System.out.println("[" + level + "]" + message);
        }

    }
}
