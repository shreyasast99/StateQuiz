package edu.uga.cs.statequiz;

import static edu.uga.cs.statequiz.MainActivity.DEBUG_TAG;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;


import androidx.appcompat.app.AppCompatActivity;

import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * This activity shows the score that you receive once you answer all the questions.
 * */
public class results extends AppCompatActivity {
    private TextView textView4;
    private Button button;
    private Button button2;
    private StateData scores = null;
    private Score quiz = null;


    /**
     * oncreate to display the results page
     * */
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        SimpleDateFormat formatter = new SimpleDateFormat("EEE, MMM d, ''yy");
        Date date = new Date();
        setContentView(R.layout.results);
        button = findViewById(R.id.button);
        button.setOnClickListener(new QuizButtonClickListener());
        button2 = findViewById(R.id.button2);
        textView4 = findViewById(R.id.textView4);
        button2.setOnClickListener(new QuizButtonClickListener());
        // Calculate the quiz result and set it in the database
        int count =0;
        for(int i=0;i<NewQuizActivity.questionAnswers.length;i++){
            if(NewQuizActivity.questionAnswers[i]==1){
                count++;
            }
        }
        quiz = new Score(formatter.format(date), count);
        scores = new StateData(this);
        scores.open();
        textView4.setText(Integer.toString(count));

    }
    /**
     * This class writes into the database your score.
     * */
    public class ScoreDBWriter extends AsyncTask<Score, Score> {

        // This method will run as a background process to write into db.
        // It will be automatically invoked by Android, when we call the execute method
        // in the onClick listener of the Save button.
        /**
         * @param jobLeads
         * This method stores the quiz result in the database.
         * */
        @Override
        protected Score doInBackground( Score... jobLeads ) {
            scores.storeQuiz( jobLeads[0] );
            return jobLeads[0];
        }

        // This method will be automatically called by Android once the writing to the database
        // in a background process has finished.  Note that doInBackground returns a JobLead object.
        // That object will be passed as argument to onPostExecute.
        // onPostExecute is like the notify method in an asynchronous method call discussed in class.
        /**
         * @param jobLead
         * This method makes a toast.
         * */
        @Override
        protected void onPostExecute( Score jobLead ) {
            // Show a quick confirmation message
            Toast.makeText( getApplicationContext(), "Job lead created for " ,
                    Toast.LENGTH_SHORT).show();



            Log.d( DEBUG_TAG, "Job lead saved: " + jobLead );
        }
    }

    /**
     * This class implements the different activities that are created based on the button you click.
     * */
    private class QuizButtonClickListener implements View.OnClickListener {
        /**
         * @param view
         * */
        @Override
        public void onClick(View view) {
            Intent intent = null;
            switch(view.getId()){
                case R.id.button2:
                    intent = new Intent(view.getContext(), PastQuizScores.class);
                    new ScoreDBWriter().execute(quiz);
                    break;
                case R.id.button:
                    intent = new Intent(view.getContext(), MainActivity.class);
                    new ScoreDBWriter().execute(quiz);
            }
            startActivity( intent );

        }
    }
}