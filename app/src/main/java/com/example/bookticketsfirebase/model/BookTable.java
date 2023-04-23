package com.example.bookticketsfirebase.model;

public class BookTable {
    private String nameBookTable;
    private String status;

    public BookTable(String nameBookTable, String status) {
        this.nameBookTable = nameBookTable;
        this.status = status;
    }

    public String getNameBookTable() {
        return nameBookTable;
    }

    public void setNameBookTable(String nameBookTable) {
        this.nameBookTable = nameBookTable;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }
}
