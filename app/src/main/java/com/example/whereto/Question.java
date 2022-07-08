package com.example.whereto;

public class Question {
    private String prompt;

    public String getPrompt() {
        return prompt;
    }

    public void setPrompt(String p) {
        this.prompt = p;
    }

    public Question(String p) {
        this.prompt = p;
    }
}
