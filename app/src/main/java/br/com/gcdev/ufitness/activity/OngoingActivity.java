package br.com.gcdev.ufitness.activity;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

import android.content.Intent;
import android.os.Bundle;
import android.widget.Button;

import java.util.ArrayList;
import java.util.List;

import br.com.gcdev.ufitness.R;
import br.com.gcdev.ufitness.activity.fragment.FirstOngoingFragment;
import br.com.gcdev.ufitness.activity.fragment.SecondOngoingFragment;
import br.com.gcdev.ufitness.activity.fragment.ThirdOngoingFragment;

public class OngoingActivity extends AppCompatActivity implements ConstantsActivities {

    private Button fragmentButton;
    private int position = 0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_ongoing);
        position = 0;
        configFragmentButton();
    }

    private void configFragmentButton() {
        fragmentButton = findViewById(R.id.fragment_button);
        fragmentButton.setOnClickListener(view -> {
            replaceFragment();
        });
    }

    private void replaceFragment() {
        List<Fragment> fragmentList = getFragments();

        FragmentManager fragmentManager = getSupportFragmentManager();
        FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();

        if(position == 0){
            changeViewFragment(fragmentList, fragmentTransaction);
            position += 1;
            return;
        }
        if(position == 1){
            changeViewFragment(fragmentList, fragmentTransaction);
            position += 1;
            return;
        }
        if(position == 2){
            changeViewFragment(fragmentList, fragmentTransaction);
            position += 1;
            return;
        }
        if(position == 3){
            openInitialActivity();
        }
    }

    private void changeViewFragment(List<Fragment> fragmentList, FragmentTransaction fragmentTransaction) {
        fragmentTransaction.replace(R.id.frame_layout, fragmentList.get(position));
        fragmentTransaction.commit();
        fragmentButton.setText(ADVANCE);
    }

    private List<Fragment> getFragments() {
        List<Fragment> fragmentList = new ArrayList<>(3);
        fragmentList.add(new FirstOngoingFragment());
        fragmentList.add(new SecondOngoingFragment());
        fragmentList.add(new ThirdOngoingFragment());
        return fragmentList;
    }

    private void openInitialActivity() {
        startActivity(new Intent(this, InitialActivity.class));
        finish();
    }
}