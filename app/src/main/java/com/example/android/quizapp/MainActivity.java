package com.example.android.quizapp;

import android.content.Context;
import android.databinding.DataBindingUtil;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.Toast;

import com.example.android.quizapp.databinding.ActivityMainBinding;

public class MainActivity extends AppCompatActivity {
    final int NO_OF_QUESTIONS = 4;
    final int TOTAL_POINTS = 5;
    Question[] questions;
    String[] questionTypes;
    Context context;
    int correctAnswers = 0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        ActivityMainBinding binding = DataBindingUtil.setContentView(this, R.layout.activity_main);
        context = getApplicationContext();
        questions = new Question[NO_OF_QUESTIONS];
        // Define answers for each question
        String[][] answers = {
                {"Boeing 767-300", "Boeing 787-800", "Airbus A350-800"},
                {"Boeing 737-300", "Boeing 737-400", "Boeing 737-800"},
                {"Boeing 777-300", "Boeing 747-400", "Boeing 757-200"},
                {"Airbus A319", "SAS Airlines Aircraft", "Boeing 737-600"},
        };
        // Define correct answer for each question
        String[] correctAnswers = {"Boeing 787-800", "Boeing 737-800", "Boeing 777-300", "Airbus A319"};
        String[] imgNames = {"lot788", "qfa738", "klm773", "sas319"};
        questionTypes = new String[]{"radio", "radio", "text", "checkbox"};
        // populate questions Array with newly created Question Objects
        for (int i = 0; i < NO_OF_QUESTIONS; i++) {
            questions[i] = new Question(context, imgNames[i], answers[i], correctAnswers[i]);
        }
        binding.setQuestion1(questions[0]);
        binding.setQuestion2(questions[1]);
        binding.setQuestion3(questions[2]);
        binding.setQuestion4(questions[3]);

    }

    public void gradeQuiz(View view) {
        CharSequence text = null;
        boolean flag = false; // determines whether all questions have been answered
        for (int i = 0; i < NO_OF_QUESTIONS; i++) {
            if (questionTypes[i].equals("radio")) {
                if (checkRadioAnswer(i)) {
                    correctAnswers++;
                } else {
                    flag = true;
                    break;
                }
            } else if (questionTypes[i].equals("text")) {
                String editTextAnswer = ((EditText) findViewById(R.id.question3)).getText().toString();
                if (editTextAnswer.length() > 0) {
                    if (editTextAnswer.toLowerCase().equals(questions[i].getCorrectAnswer())) {
                        correctAnswers++;
                    }
                } else {
                    flag = true;
                    break;
                }
            } else if (questionTypes[i].equals("checkbox")) {
                if (((CheckBox) findViewById(R.id.answer4_0)).isChecked() || ((CheckBox) findViewById(R.id.answer4_1)).isChecked() ||
                        ((CheckBox) findViewById(R.id.answer4_2)).isChecked()) {
                    if (((CheckBox) findViewById(R.id.answer4_0)).isChecked()) {
                        correctAnswers++;
                    }
                    if (((CheckBox) findViewById(R.id.answer4_1)).isChecked()) {
                        correctAnswers++;
                    }
                } else {
                    flag = true;
                    break;
                }
            }
        }

        if (!flag) {
            text = "You have scored " + correctAnswers + " out of " + TOTAL_POINTS + " points.";
        } else {
            text = "You did not answered all questions!";
        }
        Toast.makeText(context, text, Toast.LENGTH_SHORT).show();
        correctAnswers = 0;
    }

    public boolean checkRadioAnswer(int questionNo) {
        int radioGroupID = getResources().getIdentifier("question" + (questionNo + 1), "id", getPackageName());
        int answerID = ((RadioGroup) findViewById(radioGroupID)).getCheckedRadioButtonId();
        if (answerID < 0) {
            return false;
        }
        CharSequence answerText = ((RadioButton) findViewById(answerID)).getText();

        return questions[questionNo].getCorrectAnswer().equals(answerText);
    }


}
