package edu.uga.cs.statequiz;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

public class results extends AppCompatActivity {
    private TextView textView4;
    private Button button;
    private Button button2;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.results);
        button = findViewById(R.id.button);
        button.setOnClickListener(new QuizButtonClickListener());
        button2 = findViewById(R.id.button2);
        button2.setOnClickListener(new QuizButtonClickListener());
        // Calculate the quiz result and set it in the database
        // textView4.setText();

    }
    private class QuizButtonClickListener implements View.OnClickListener {
        @Override
        public void onClick(View view) {
            Intent intent = null;
            switch(view.getId()){
                case R.id.button2:
                    intent = new Intent(view.getContext(), PastQuizScores.class);
                    break;
                case R.id.button:
                    intent = new Intent(view.getContext(), MainActivity.class);
            }
            startActivity( intent );
        }
    }
}
