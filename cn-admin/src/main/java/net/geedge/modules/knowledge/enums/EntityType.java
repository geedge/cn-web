package net.geedge.modules.knowledge.enums;


public enum EntityType {
    IP("IP"),DOMAIN("Domain"),SUBSCRIBER("Subscriber");

    private String value;

    public String getValue() {
        return value;
    }

    private EntityType(String value) {
        this.value = value;
    }
}