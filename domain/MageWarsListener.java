package us.heptet.magewars.test;

import ch.qos.logback.classic.LoggerContext;
import ch.qos.logback.classic.layout.TTLLLayout;
import ch.qos.logback.classic.spi.ILoggingEvent;
import ch.qos.logback.core.ConsoleAppender;
import ch.qos.logback.core.encoder.LayoutWrappingEncoder;
import ch.qos.logback.core.util.StatusPrinter;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.slf4j.MDC;
import org.slf4j.bridge.SLF4JBridgeHandler;
import org.testng.*;
//import uk.org.lidalia.sysoutslf4j.context.SysOutOverSLF4J;

/* Created by jade on 07/07/2016. */

public class MageWarsListener extends TestListenerAdapter implements ISuiteListener {
    private static Logger logger = LoggerFactory.getLogger(MageWarsListener.class);

    public MageWarsListener() {
        super();

        LoggerContext lc = (LoggerContext) LoggerFactory.getILoggerFactory();

        // print logback's internal status
        //StatusPrinter.print(lc);

        ConsoleAppender<ILoggingEvent> ca = new ConsoleAppender<ILoggingEvent>();
        ca.setContext(lc);
        ca.setName("console");
        LayoutWrappingEncoder<ILoggingEvent> encoder = new LayoutWrappingEncoder<ILoggingEvent>();
        encoder.setContext(lc);

        // same as
        // PatternLayout layout = new PatternLayout();
        // layout.setPattern("%d{HH:mm:ss.SSS} [%thread] %-5level %logger{36} - %msg%n");
        TestLayout layout = new TestLayout();

        layout.setContext(lc);
        layout.start();
        encoder.setLayout(layout);

        ca.setEncoder(encoder);
        ca.start();

        ch.qos.logback.classic.Logger rootLogger = lc.getLogger(ch.qos.logback.classic.Logger.ROOT_LOGGER_NAME);
        rootLogger.detachAndStopAllAppenders();
        rootLogger.addAppender(ca);

        SLF4JBridgeHandler.removeHandlersForRootLogger();
        SLF4JBridgeHandler.install();

        SysOutOverSLF4J.sendSystemOutAndErrToSLF4J();

    }


    @Override
    public void onTestStart(ITestResult result) {


        logger.info("Test Start: {} {}", result.getTestClass().getName(), result.getName());
        MDC.put("TestNGTestClass", result.getTestClass().getName());
        MDC.put("TestNGName", result.getName());
    }

    @Override
    public void onStart(ITestContext testContext) {
        logger.info("start");
    }

    @Override
    public void onConfigurationSkip(ITestResult itr) {
        logger.info("configuration skip");
    }

    @Override
    public void onTestSuccess(ITestResult tr) {
        logger.info("Test success");
        clearTest();
    }

    @Override
    public void onTestSkipped(ITestResult tr) {
        logger.info("Test skipped");
        clearTest();
    }

    @Override
    public void onTestFailure(ITestResult tr) {
        logger.info("Test failure");
        clearTest();
    }

    @Override
    public void onTestFailedButWithinSuccessPercentage(ITestResult tr) {
        logger.info("Test failed within success %");
        clearTest();
    }

    private void clearTest() {

        MDC.remove("TestNGTestClass");
        MDC.remove("TestNGName");
    }

    @Override
    public void onFinish(ITestContext testContext) {
        logger.info("on finish", testContext.getName());

    }

    @Override
    public void onStart(ISuite iSuite) {
        logger.info("Test Suite Start: {}", iSuite.getName());
    }

    @Override
    public void onFinish(ISuite iSuite) {
        logger.info("Test Suite Finish: {}", iSuite.getName());
    }
}
