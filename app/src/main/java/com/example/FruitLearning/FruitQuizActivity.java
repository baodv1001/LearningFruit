package com.example.FruitLearning;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;


public class FruitQuizActivity extends AppCompatActivity implements View.OnClickListener{

    TextView totalQuestionsTextView;
    TextView questionTextView;
    TextView noQuestion;
    Button ansA, ansB, ansC, ansD;
    Button submitBtn;
    Button btnBack;

    int score=0;
    int totalQuestion = QuestionAnswer.question.length;
    int currentQuestionIndex = 0;
    String selectedAnswer = "";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_fruit_quiz);

        totalQuestionsTextView = findViewById(R.id.total_question);
        questionTextView = findViewById(R.id.question);
        ansA = findViewById(R.id.ans_A);
        ansB = findViewById(R.id.ans_B);
        ansC = findViewById(R.id.ans_C);
        ansD = findViewById(R.id.ans_D);
        submitBtn = findViewById(R.id.buttonSubmit);
        btnBack = findViewById(R.id.btnBack);
        noQuestion = findViewById(R.id.stt_question);

        ansA.setOnClickListener((View.OnClickListener) this);
        ansB.setOnClickListener((View.OnClickListener) this);
        ansC.setOnClickListener((View.OnClickListener) this);
        ansD.setOnClickListener((View.OnClickListener) this);
        submitBtn.setOnClickListener((View.OnClickListener) this);
        btnBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(FruitQuizActivity.this, MainActivity.class);
                startActivity(intent);
                finishAffinity();
            }
        });

        totalQuestionsTextView.setText("/"+totalQuestion);

        loadNewQuestion();
    }

    @Override
    public void onClick(View view) {

        ansA.setBackgroundColor(Color.TRANSPARENT);
        ansA.setCompoundDrawablesWithIntrinsicBounds(null,null,getDrawable(R.drawable.ic_baseline_panorama_fish_eye_24),null);
        ansB.setBackgroundColor(Color.TRANSPARENT);
        ansB.setCompoundDrawablesWithIntrinsicBounds(null,null,getDrawable(R.drawable.ic_baseline_panorama_fish_eye_24),null);
        ansC.setBackgroundColor(Color.TRANSPARENT);
        ansC.setCompoundDrawablesWithIntrinsicBounds(null,null,getDrawable(R.drawable.ic_baseline_panorama_fish_eye_24),null);
        ansD.setBackgroundColor(Color.TRANSPARENT);
        ansD.setCompoundDrawablesWithIntrinsicBounds(null,null,getDrawable(R.drawable.ic_baseline_panorama_fish_eye_24),null);


        Button clickedButton = (Button) view;
        if(clickedButton.getId()==R.id.buttonSubmit){
            if(selectedAnswer.equals(QuestionAnswer.correctAnswers[currentQuestionIndex])){
                score++;
            }
            currentQuestionIndex++;
            loadNewQuestion();


        } else{
                //choices button clicked
                selectedAnswer  = clickedButton.getText().toString().substring(3);
                //clickedButton.setTextColor(Integer.parseInt("#3DFD18"));
                clickedButton.setBackgroundColor(Color.BLUE);
                clickedButton.setCompoundDrawablesWithIntrinsicBounds(null,null,getDrawable(R.drawable.ic_choice),null);
        }
    }

    private void loadNewQuestion() {
        if(currentQuestionIndex == totalQuestion ){
            finishQuiz();
            return;
        }
        noQuestion.setText(String.valueOf(currentQuestionIndex+1));
        questionTextView.setText(QuestionAnswer.question[currentQuestionIndex]);
        ansA.setText("A. " + QuestionAnswer.choices[currentQuestionIndex][0]);
        ansB.setText("B. " + QuestionAnswer.choices[currentQuestionIndex][1]);
        ansC.setText("C. " + QuestionAnswer.choices[currentQuestionIndex][2]);
        ansD.setText("D. " + QuestionAnswer.choices[currentQuestionIndex][3]);
    }

    private void finishQuiz() {
        String passStatus = "";
        if(score > totalQuestion*0.60){
            passStatus = "Passed";
        }else{
            passStatus = "Failed";
        }

        new AlertDialog.Builder(this)
                .setTitle(passStatus)
                .setMessage("Score is "+ score+" out of "+ totalQuestion)
                .setPositiveButton("Restart",(dialogInterface, i) -> restartQuiz() )
                .setCancelable(false)
                .show();

    }

    private void restartQuiz() {
        score = 0;
        currentQuestionIndex =0;
        loadNewQuestion();
    }
}