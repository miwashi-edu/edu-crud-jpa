package net.miwashi.crud.model;

import lombok.Data;
@Data
public class Book {

    public Book(String title){
        this.title = title;
    }
    private int id;
    private int author_id;
    private String title;
    private String isbn;
    private String published; //Should be date, but doesn't matter in this solution.
}
