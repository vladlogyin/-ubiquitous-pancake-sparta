package future.legends.pancake.logger;

import org.apache.logging.log4j.LogManager;

public class Logger {

    private static org.apache.logging.log4j.Logger LOGGER = LogManager.getLogger("Logger");

    public static void trace(String log){LOGGER.trace(log);}

    public static void debug(String log){LOGGER.debug(log);}

    public static void info(String log){LOGGER.info(log);}

    public static void warn(String log){LOGGER.warn(log);}

    public static void error(String log){LOGGER.error(log);}

    public static void fatal(String log){LOGGER.fatal(log);}

}
