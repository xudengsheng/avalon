package test.avalon.log;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import ch.qos.logback.classic.LoggerContext;
import ch.qos.logback.core.util.StatusPrinter;

public class LogTest {
	
	  final static Logger logger = LoggerFactory.getLogger(LogTest.class);
	  final static Logger logger_info=LoggerFactory.getLogger("chapters.configuration");

	  public static void main(String[] args) {
	    // assume SLF4J is bound to logback in the current environment
	    LoggerContext lc = (LoggerContext) LoggerFactory.getILoggerFactory();
	    // print logback's internal status
	    StatusPrinter.print(lc);
	    
	    logger.debug("Entering application.");
	    logger_info.warn("this is info");
	    logger.info("Exiting application.");
	  }
}
