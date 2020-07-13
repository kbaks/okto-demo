package eu.acme.demo.exception;

import org.zalando.problem.AbstractThrowableProblem;
import org.zalando.problem.Status;

import javax.annotation.Nonnull;
import java.net.URI;

/**
 * Base exception class for Demo application that inherits from {@link RuntimeException}
 */
public abstract class DemoApplicationException extends AbstractThrowableProblem {

    public DemoApplicationException(@Nonnull String messageKey, @Nonnull Status status, @Nonnull String message) {
        super(URI.create("http://api.okto-demo.eu/error/".concat(messageKey)), messageKey, status, message);
    }

    /**
     * Gets the invalid value
     * @return the invalid value
     */
    public abstract Object getInvalidValue();

    /**
     * Gets the error field
     * @return Gets the error field
     */
    public abstract String getErrorField();

    /**
     * Gets the message key
     * @return the message key
     */
    public abstract String getMessageKey();

}
