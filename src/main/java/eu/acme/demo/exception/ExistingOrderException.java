package eu.acme.demo.exception;

import eu.acme.demo.error.ValidationMessageKeys;

import static org.zalando.problem.Status.BAD_REQUEST;

public class ExistingOrderException extends DemoApplicationException {

    private final String clientReferenceCode;

    public ExistingOrderException(final String clientReferenceCode) {
        super(ValidationMessageKeys.ORDER_ALREADY_EXISTS, BAD_REQUEST,
                String.format("An order with the same client reference code %s has already been requested", clientReferenceCode));
        this.clientReferenceCode = clientReferenceCode;
    }

    @Override
    public Object getInvalidValue() {
        return clientReferenceCode;
    }

    @Override
    public String getErrorField() {
        return "clientReferenceCode";
    }

    @Override
    public String getMessageKey() {
        return ValidationMessageKeys.ORDER_ALREADY_EXISTS;
    }

}
