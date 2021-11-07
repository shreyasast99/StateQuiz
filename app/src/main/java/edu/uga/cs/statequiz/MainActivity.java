package edu.uga.cs.statequiz;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

public class MainActivity extends AppCompatActivity {
    private Button buttonNewQuiz;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        buttonNewQuiz = findViewById(R.id.newQuiz);
        buttonNewQuiz.setOnClickListener(new ButtonClickListener());
    }
    static class ButtonClickListener implements
            View.OnClickListener {
        Intent intent;
        /**
         * Returns nothing.
         * Takes in Bundle as parameter.
         * This method performs a setup including layout creation and
         * data binding.
         */
        @Override
        public void onClick(View view) {
            intent = new
                    Intent( view.getContext(),
                    NewQuizActivity.class );
            startActivity( intent );

        }


    }

    public class QuizButtonClickListener implements View.OnClickListener {
    }
}