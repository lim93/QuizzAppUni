package com.whs.quizzappuni.Model;

/**
 * Created by krispin on 25.10.15.
 */
public class Definition {

    private int id;
    private String term;
    private String definitionText;
    private Category category;
    private String source;

    public Definition(int id, String term, String definitionText, Category category, String source) {
        this.id = id;
        this.term = term;
        this.definitionText = definitionText;
        this.category = category;
        this.source = source;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getTerm() {
        return term;
    }

    public void setTerm(String term) {
        this.term = term;
    }

    public String getDefinitionText() {
        return definitionText;
    }

    public void setDefinitionText(String definitionText) {
        this.definitionText = definitionText;
    }

    public Category getCategory() {
        return category;
    }

    public void setCategory(Category category) {
        this.category = category;
    }

    public String getSource() {
        return source;
    }

    public void setSource(String source) {
        this.source = source;
    }
}
