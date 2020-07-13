package eu.acme.demo.exception;

import lombok.Getter;

@Getter
public enum ResourceType {

    ORDER("Order"),
    CUSTOMER("Customer");

    private final String resource;

    ResourceType(String resource) {
        this.resource = resource;
    }
}
