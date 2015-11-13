package com.whs.quizzappuni.Model;

/**
 * Created by krispin on 25.10.15.
 */
public abstract class Question {

    private int id;
    private String questionText;
    private Definition definition;
    private Category category;
    protected QuestionAnswer[] answers;


    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getQuestionText() {
        return questionText;
    }

    public void setQuestionText(String questionText) {
        this.questionText = questionText;
    }

    public Definition getDefinition() {
        return definition;
    }

    public void setDefinition(Definition definition) {
        this.definition = definition;
    }

    public Category getCategory() {
        return category;
    }

    public void setCategory(Category category) {
        this.category = category;
    }
    public QuestionAnswer[] getAnswers() {
        return answers;
    }

}
