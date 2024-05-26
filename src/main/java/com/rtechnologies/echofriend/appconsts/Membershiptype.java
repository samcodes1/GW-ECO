package com.rtechnologies.echofriend.appconsts;

public enum Membershiptype{
    FREE("Free"),PAID("Paid");
    private final String label;

    Membershiptype(String label) {
        this.label = label;
    }

    public String getLabel() {
        return label;
    }
};
