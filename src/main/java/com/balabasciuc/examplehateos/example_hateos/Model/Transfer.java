package com.balabasciuc.examplehateos.example_hateos.Model;

public class Transfer {

    private Long toAccountNumber;
    private Long fromAccountNumber;
    private Long amount;
    private String mode;

    public Transfer() {}

    public Transfer(Long toAccountNumber, Long fromAccountNumber, Long amount, String mode) {
        this.toAccountNumber = toAccountNumber;
        this.fromAccountNumber = fromAccountNumber;
        this.amount = amount;
        this.mode = mode;
    }

    public Long getToAccountNumber() {
        return toAccountNumber;
    }

    public void setToAccountNumber(Long toAccountNumber) {
        this.toAccountNumber = toAccountNumber;
    }

    public Long getFromAccountNumber() {
        return fromAccountNumber;
    }

    public void setFromAccountNumber(Long fromAccountNumber) {
        this.fromAccountNumber = fromAccountNumber;
    }

    public Long getAmount() {
        return amount;
    }

    public void setAmount(Long amount) {
        this.amount = amount;
    }

    public String getMode() {
        return mode;
    }

    public void setMode(String mode) {
        this.mode = mode;
    }
}
