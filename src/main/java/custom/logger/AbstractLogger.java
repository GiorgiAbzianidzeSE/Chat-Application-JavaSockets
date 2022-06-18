package custom.logger;

public abstract class AbstractLogger {

    private LoggingLevel loggingLevel;

    public AbstractLogger(LoggingLevel loggingLevel) {
        this.loggingLevel = loggingLevel;
    }

    protected abstract void outputMessage(LoggingLevel level , String message);


    public void debug(String message){
        outputMessage(LoggingLevel.DEBUG , message);
    }

    public void INFO(String message){
        outputMessage(LoggingLevel.INFO , message);
    }

    public void WARN(String message){
        outputMessage(LoggingLevel.WARN , message);
    }

    public void ERROR(String message){
        outputMessage(LoggingLevel.ERROR , message);
    }

    public void FATAL(String message){
        outputMessage(LoggingLevel.FATAL , message);
    }

    public LoggingLevel setLogger(LoggingLevel loggingLevel){
        this.loggingLevel = loggingLevel;
        return this.loggingLevel;
    }
}
