package com.example.android.quizapp;

import android.content.Context;
import android.databinding.DataBindingUtil;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.Toast;

import com.example.android.quizapp.databinding.ActivityMainBinding;

public class MainActivity extends AppCompatActivity {
    final int NO_OF_QUESTIONS = 4;
    Question[] questions;
    Context context;

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
                {"Airbus A319", "Airbus A318", "Boeing 737-600"},
        };
        // Define correct answer for each question
        String[] correctAnswers = {"Boeing 787-800", "Boeing 737-800", "Boeing 777-300", "Airbus A319"};
        String[] imgNames = {"lot788", "qfa738", "klm773", "sas319"};
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
        int correctAnswers = 0;
        boolean flag = false; // determines whether all questions have been answered
        for (int i = 0; i < NO_OF_QUESTIONS; i++) {
            int radioGroupID = getResources().getIdentifier("question" + (i + 1), "id", getPackageName());
            int answerID = getCheckedRadioBtnId(radioGroupID);
            if (answerID < 0) {
                // if at least one radio button has not been selected return message to user
                text = "You did not answered all questions!";
                flag = true;
                break;
            }
            if (checkAnswer(questions[i], answerID)) {
                correctAnswers++;
            }
            ;
        }
        if (!flag) {
            text = "You have scored " + correctAnswers + " out of " + NO_OF_QUESTIONS + " points.";
        }
        Toast.makeText(context, text, Toast.LENGTH_SHORT).show();
    }

    /**
     * Helper method used to look up value of selected radio button for particular radio group
     *
     * @param radioGroupID ID of RadioGroup we are interested in
     * @return selected RadioButton ID
     */
    public int getCheckedRadioBtnId(int radioGroupID) {
        return ((RadioGroup) findViewById(radioGroupID)).getCheckedRadioButtonId();
    }

    /**
     * Helper method used to determine whether question has been answered correctly
     *
     * @param question Question object which contains correct answer
     * @param answerID ID of selected RadioButton
     * @return true if the selected RadioButton corresponds to correct answer, false otherwise
     */
    public boolean checkAnswer(Question question, int answerID) {
        return question.getCorrectAnswer().equals(getAnswerText(answerID));
    }

    /**
     * Helper method used to get text associated with selected RadioButton
     *
     * @param answerID ID of selected RadioButton
     * @return text value of the selected RadioButton
     */
    public CharSequence getAnswerText(int answerID) {
        return ((RadioButton) findViewById(answerID)).getText();
    }
}
