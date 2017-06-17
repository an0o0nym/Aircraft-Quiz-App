package com.example.android.quizapp;

import android.content.Context;
import android.databinding.BindingAdapter;
import android.widget.ImageView;

public class Question {
    private final Context context;
    private final String text;
    private final int imageResource;
    private final String[] answers;
    private final String correctAnswer;

    public Question(Context context, String imgName, String[] answers, String correctAnswer) {
        this.context = context;
        this.text = this.context.getString(R.string.question);
        this.answers = answers;
        this.imageResource = context.getResources().getIdentifier(imgName, "drawable", context.getPackageName());
        this.correctAnswer = correctAnswer;
    }

    public Question(Context context, String imgName, String[] answers, String correctAnswer, String text) {
        this.context = context;
        this.text = text;
        this.answers = answers;
        this.imageResource = this.context.getResources().getIdentifier(imgName, "drawable", context.getPackageName());
        this.correctAnswer = correctAnswer;
    }

    @BindingAdapter("android:src")
    public static void setImageResource(ImageView imageView, int resource) {
        imageView.setImageResource(resource);
    }

    public String getText() {
        return text;
    }

    public int getImageResource() {
        return imageResource;
    }

    public String[] getAnswers() {
        return answers;
    }

    public String getCorrectAnswer() {
        return correctAnswer;
    }


}
