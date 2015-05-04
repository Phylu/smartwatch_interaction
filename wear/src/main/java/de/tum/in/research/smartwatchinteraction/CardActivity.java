package de.tum.in.research.smartwatchinteraction;

import android.app.Activity;
import android.app.FragmentManager;
import android.app.FragmentTransaction;
import android.os.Bundle;
import android.support.wearable.view.CardFragment;
import android.widget.TextView;

public class CardActivity extends Activity {


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_card);
        System.out.println("Started Card Activity");

        FragmentManager fragmentManager = getFragmentManager();
        FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
        CardFragment cardFragment = CardFragment.create(getString(R.string.vote),
                getString(R.string.vote),
                R.drawable.thumb_up_mini);
        fragmentTransaction.add(R.id.frame_layout, cardFragment);
        fragmentTransaction.commit();

    }

}
