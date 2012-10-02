package com.javawords.logging;

import ch.qos.logback.classic.jul.JULHelper;
import ch.qos.logback.classic.spi.ILoggingEvent;
import ch.qos.logback.core.AppenderBase;
import ch.qos.logback.core.Layout;
import java.util.logging.Level;
import java.util.logging.LogRecord;
import java.util.logging.Logger;

/**
 *
 * @author Christos Fragoulides
 */
public class JULLogbackAppender extends AppenderBase<ILoggingEvent> {

    Layout layout;

    public Layout getLayout() {
        return layout;
    }

    public void setLayout(Layout layout) {
        this.layout = layout;
    }

    @Override
    public void start() {
        if (this.layout == null) {
            addError("No layout set for the appender named [" + name + "].");
            return;
        }

        super.start();
    }

    @Override
    protected void append(ILoggingEvent event) {
        logEvent(event, layout);
    }

    private static void logEvent(ILoggingEvent event, Layout logLayout){

        Logger logger = JULHelper.asJULLogger(event.getLoggerName());
        
        LogRecord record = wrapEvent(event, logLayout);
        
        logger.log(record);

    }

    private static LogRecord wrapEvent(ILoggingEvent event, Layout logLayout){

        Level level = JULHelper.asJULLevel(event.getLevel());
        // output the events as formatted by our layout
        LogRecord record = new LogRecord(level, logLayout.doLayout(event));
        record.setMillis(event.getTimeStamp());
        record.setSourceClassName(event.getLoggerName());

        return record;
    }
}
