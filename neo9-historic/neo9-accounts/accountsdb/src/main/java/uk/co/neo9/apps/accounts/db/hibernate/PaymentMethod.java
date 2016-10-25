package uk.co.neo9.apps.accounts.db.hibernate;

//DO NOT CHANGE THE ORDER OF ENUMS IN THIS CLASS, IT WILL EFFECT DATA AND BREAK UNIT TESTS

/**
 * CalculatedValueType
 */

public enum PaymentMethod implements java.io.Serializable {

    CASH("Cash"),
    CHEQUE("Cheque"),
    DD("Direct Debit"),
    DC("Direct Credit"),
    CC("Credit Card");
    

    private String description;

    PaymentMethod(String description) {
        this.description = description;
    }

    public String toString() {
        return description;
    }

}
