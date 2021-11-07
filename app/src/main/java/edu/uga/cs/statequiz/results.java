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

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.results);
        button = findViewById(R.id.button);
        button.setOnClickListener(new QuizButtonClickListener());
        // Calculate the quiz result
        // textView4.setText();
    }
    private class QuizButtonClickListener implements View.OnClickListener {
        @Override
        public void onClick(View view) {
            Intent intent = new Intent(view.getContext(), PastQuizScores.class);
            view.getContext().startActivity( intent );
        }
    }
}
