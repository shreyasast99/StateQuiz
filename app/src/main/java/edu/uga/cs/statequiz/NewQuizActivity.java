package edu.uga.cs.statequiz;
import android.content.Intent;
import android.os.Bundle;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentPagerAdapter;
import androidx.viewpager.widget.ViewPager;
import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.Collections;

public class NewQuizActivity extends AppCompatActivity {
    SectionsPagerAdapter mSectionsPagerAdapter;
    ViewPager mViewPager;
    ActionBar mActionBar;
    public final static State [] questionArr = new State[5];
    ArrayList<String> arrayList = new ArrayList<String>();
    public static ArrayList<Integer> vals = new ArrayList<Integer>();
    public static int [] questionAnswers = new int[5];
    private StateData jobLeadsData = null;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_new_quiz);
        jobLeadsData = new StateData( this );
        jobLeadsData.open();
        int count=0;
        while(vals.size()<5){
            int random_int = (int)Math.floor(Math.random()*(49-1+1)+1);
            if(!(vals.contains(random_int))){
                questionArr[count]=jobLeadsData.retrieveAllJobLeads().get(random_int);
                vals.add(random_int);
                count++;
            }

        }

        mActionBar = getSupportActionBar();
        mSectionsPagerAdapter = new SectionsPagerAdapter(getSupportFragmentManager(), vals.size());
        mActionBar.setTitle(mSectionsPagerAdapter.getPageTitle(0));
        mViewPager = (ViewPager) findViewById(R.id.pager);
        mViewPager.setAdapter(mSectionsPagerAdapter);



        mViewPager.addOnPageChangeListener(new ViewPager.OnPageChangeListener() {
            @Override
            public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {
            }

            @Override
            public void onPageSelected(int position) {
                mActionBar.setTitle(mSectionsPagerAdapter.getPageTitle(position));

            }

            @Override
            public void onPageScrollStateChanged(int state) {
            }
        });
    }
    //THIS IS WHERE EVERYTHING IS HAPPENING
    public void loadView(TextView questions, int question ,RadioGroup radioGroup) {
        //int random_int = (int)Math.floor(Math.random()*(49-1+1)+1);
        ArrayList<String> arrayList = new ArrayList<String>();
        arrayList.add(questionArr[question].getCapital());
        arrayList.add(questionArr[question].getSecondCity());
        arrayList.add(questionArr[question].getThirdCity());
        Collections.shuffle(arrayList);
        questions.setText("What is the capital of "+questionArr[question].getState());
        for (int i = 0; i < radioGroup .getChildCount(); i++) {

            ((RadioButton) radioGroup.getChildAt(i)).setText(arrayList.get(i));

        }
        if (radioGroup.getCheckedRadioButtonId() == -1)
        {
            // no radio buttons are checked
        }
        else
        {
            int radioButtonID = radioGroup.getCheckedRadioButtonId();

            RadioButton radioButton = (RadioButton) radioGroup.findViewById(radioButtonID);

            String selectedText = (String) radioButton.getText();

            if(selectedText.matches(questionArr[question].getCapital()))
            {
                questionAnswers[question] = 1;//if its correct the index of this question is set to 1
            }
            else
            {
                questionAnswers[question] = 0;//if its wrong then set to 0
            }
        }
    }

    public class SectionsPagerAdapter extends FragmentPagerAdapter {
        private final int mSize;

        public SectionsPagerAdapter(FragmentManager fm, int size) {
            super(fm);
            this.mSize = size;
        }

        @Override
        public Fragment getItem(int position) {
            return PlaceholderFragment.newInstance(position + 1);
        }

        @Override
        public int getCount() {
            return mSize;
        }

        @Override
        public CharSequence getPageTitle(int position) {
            int imageNum = position + 1;
            return String.valueOf("Question " + imageNum);
        }
    }

    public static class PlaceholderFragment extends Fragment {
        private static final String ARG_SECTION_NUMBER = "section_number";
        private int mImageNum;
        private RadioGroup radioGroup;
        private TextView mTextView;
        private TextView actualQuestion;


        public static PlaceholderFragment newInstance(int sectionNumber) {
            PlaceholderFragment fragment = new PlaceholderFragment();
            Bundle args = new Bundle();
            args.putInt(ARG_SECTION_NUMBER, sectionNumber);
            fragment.setArguments(args);
            return fragment;
        }

        public PlaceholderFragment() {
        }

        @Override
        public void onCreate(Bundle savedInstanceState) {
            super.onCreate(savedInstanceState);
            if (getArguments() != null) {
                mImageNum = getArguments().getInt(ARG_SECTION_NUMBER);
            } else {
                mImageNum = -1;
            }
        }

        @Override
        public View onCreateView(LayoutInflater inflater, ViewGroup container,
                                 Bundle savedInstanceState) {
            View rootView = inflater.inflate(R.layout.fragment_new_quiz, container, false);
            mTextView = (TextView) rootView.findViewById(R.id.section_label);
            radioGroup = (RadioGroup) rootView.findViewById(R.id.group);
            actualQuestion = (TextView) rootView.findViewById(R.id.textView);
            return rootView;
        }

        @Override
        public void onActivityCreated(Bundle savedInstanceState) {
            super.onActivityCreated(savedInstanceState);
            if (NewQuizActivity.class.isInstance(getActivity())) {
                final int resId = mImageNum - 1;
                Log.e("the resid?: ", Integer.toString(resId));

                ((NewQuizActivity) getActivity()).loadView(actualQuestion,resId,radioGroup);

            }
        }
    }
}
