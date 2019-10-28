package com.example.appquiz.model;

public class Question {

    private static int questionCounter = 0;
    private String questionText;
    private String answer;
    private String textHint;
    private String imageUriHint;
    private int questionID;

    public Question(String argQuestionText, String argAnswer, String textHint, String imageURIHint ){
        this.questionText = argQuestionText;
        this.questionID = questionCounter++;
        this.answer = argAnswer;
        this.textHint = textHint;
        this.imageUriHint = imageURIHint;
    }

    public int getQuestionID() {
        return questionID;
    }

    public String getQuestionText() {
        return questionText;
    }

    public String getAnswer() {
        return answer;
    }

    public String getTextHint() {
        return textHint;
    }

    public String getImageUriHint() {
        return imageUriHint;
    }
}
