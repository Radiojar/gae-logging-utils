package com.javawords.logging;

import ch.qos.logback.classic.spi.ILoggingEvent;
import ch.qos.logback.core.filter.Filter;
import ch.qos.logback.core.spi.FilterReply;

/**
 *
 * @author Christos Fragoulides
 */
public class SystemPropertyFilter extends Filter<ILoggingEvent> {
    
    String propertyName;
    String acceptedValue;
    String actualValue;

    @Override
    public FilterReply decide(ILoggingEvent event) {
        if (!isStarted()) return FilterReply.NEUTRAL;
        
        if (acceptedValue.equals(actualValue)) return FilterReply.ACCEPT;
        else return FilterReply.DENY;
    }

    public void setAcceptedValue(String acceptedValue) {
        this.acceptedValue = acceptedValue;
    }

    public void setPropertyName(String propertyName) {
        this.propertyName = propertyName;
    }

    @Override
    public void start() {
        if (this.propertyName != null && this.acceptedValue != null) {
            actualValue = System.getProperty(propertyName);
            if (actualValue != null) super.start();
        }
    }    
    
}
