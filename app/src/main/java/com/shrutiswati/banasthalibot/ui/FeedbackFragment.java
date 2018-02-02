package com.shrutiswati.banasthalibot.ui;


import android.graphics.Color;
import android.graphics.drawable.GradientDrawable;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.Toast;

import com.hsalf.smilerating.BaseRating;
import com.hsalf.smilerating.SmileRating;
import com.shrutiswati.banasthalibot.R;

import static android.content.ContentValues.TAG;


/**
 * A simple {@link Fragment} subclass.
 */

public class FeedbackFragment extends Fragment {


    public FeedbackFragment() {
        // Required empty public constructor

    }



    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_feedback, container, false);



    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        GradientDrawable gradientDrawable = new GradientDrawable(
                GradientDrawable.Orientation.LEFT_RIGHT,
                new int[] {Color.parseColor("#DA4453"), Color.parseColor("#89216B")});
        view.findViewById(R.id.container_feedback).setBackground(gradientDrawable);
        SmileRating smileRating = (SmileRating) getView().findViewById(R.id.smile_rating);
        smileRating.setOnSmileySelectionListener(new SmileRating.OnSmileySelectionListener() {
            @Override
            public void onSmileySelected(@BaseRating.Smiley int smiley, boolean reselected) {
                // reselected is false when user selects different smiley that previously selected one
                // true when the same smiley is selected.
                // Except if it first time, then the value will be false.
                switch (smiley) {
                    case SmileRating.BAD:
                        Log.i(TAG, "Bad");
                        break;
                    case SmileRating.GOOD:
                        Log.i(TAG, "Good");
                        break;
                    case SmileRating.GREAT:
                        Log.i(TAG, "Great");
                        break;
                    case SmileRating.OKAY:
                        Log.i(TAG, "Okay");
                        break;
                    case SmileRating.TERRIBLE:
                        Log.i(TAG, "Terrible");
                        break;
                }
            }
        });
        smileRating.setOnRatingSelectedListener(new SmileRating.OnRatingSelectedListener() {
            @Override
            public void onRatingSelected(int level, boolean reselected) {
                // level is from 1 to 5 (0 when none selected)
                // reselected is false when user selects different smiley that previously selected one
                // true when the same smiley is selected.
                // Except if it first time, then the value will be false.
            }
        });

    }
}
