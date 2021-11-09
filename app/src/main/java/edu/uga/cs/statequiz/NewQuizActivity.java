package edu.uga.cs.statequiz;

import androidx.appcompat.app.AppCompatActivity;

import android.database.Cursor;
import android.os.Bundle;
import android.widget.Button;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Iterator;
import java.util.LinkedHashSet;
import java.util.Random;
import java.util.Set;

public class NewQuizActivity extends AppCompatActivity {
    //RadioButton radio1 = findViewById(R.id.radio1);
    //RadioButton radio2 = findViewById(R.id.radio2);
    //RadioButton radio3 = findViewById(R.id.radio3);
    private StateData jobLeadsData = null;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_new_quiz);
        RadioGroup radioGroup = (RadioGroup)findViewById(R.id.group);
        TextView question = findViewById(R.id.textView);
        Cursor cursor = null;

        jobLeadsData = new StateData( this );
        jobLeadsData.open();
        int random_int = (int)Math.floor(Math.random()*(49-1+1)+1);
        ArrayList<String> arrayList = new ArrayList<String>();
        arrayList.add(jobLeadsData.retrieveAllJobLeads().get(random_int).getCapital());
        arrayList.add(jobLeadsData.retrieveAllJobLeads().get(random_int).getSecondCity());
        arrayList.add(jobLeadsData.retrieveAllJobLeads().get(random_int).getThirdCity());
        Collections.shuffle(arrayList);
        question.setText("What is the capital of "+jobLeadsData.retrieveAllJobLeads().get(random_int).getState());
        for (int i = 0; i < radioGroup .getChildCount(); i++) {

            ((RadioButton) radioGroup.getChildAt(i)).setText(arrayList.get(i));

        }

    }
}