package eu.acme.demo.exception;

import eu.acme.demo.error.ValidationMessageKeys;

import static org.zalando.problem.Status.NOT_FOUND;

public class ResourceNotFoundException extends DemoApplicationException {

    private final ResourceType resourceType;
    private final String value;

    public ResourceNotFoundException(final ResourceType resourceType, final String value) {
        super(ValidationMessageKeys.RESOURCE_NOT_FOUND, NOT_FOUND,
                String.format("%s %s is not found", resourceType.getResource(), value));
        this.resourceType = resourceType;
        this.value = value;
    }

    @Override
    public Object getInvalidValue() {
        return value;
    }

    @Override
    public String getErrorField() {
        return resourceType.getResource();
    }

    @Override
    public String getMessageKey() {
        return ValidationMessageKeys.RESOURCE_NOT_FOUND;
    }

}
