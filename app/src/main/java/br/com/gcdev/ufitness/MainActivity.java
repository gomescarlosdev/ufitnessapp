package br.com.gcdev.ufitness;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import br.com.gcdev.ufitness.activity.HomeActivity;
import br.com.gcdev.ufitness.activity.InitialActivity;

public class MainActivity extends AppCompatActivity {

    private Button fragmentButton;
    private int countFrag = 1;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        countFrag = 1;
        fragmentButton = findViewById(R.id.fragment_button);
        fragmentButton.setOnClickListener(view -> {
            replaceFragment();
        });

    }

    private void replaceFragment() {

        if(countFrag == 1){
            FragmentManager fragmentManager = getSupportFragmentManager();
            FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
            fragmentTransaction.replace(R.id.frame_layout, new FirstOngoingFragment());
            fragmentTransaction.commit();
            fragmentButton.setText("Avançar");
            countFrag += 1;
            return;
        }
        if(countFrag == 2){
            FragmentManager fragmentManager = getSupportFragmentManager();
            FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
            fragmentTransaction.replace(R.id.frame_layout, new SecondOngoingFragment());
            fragmentTransaction.commit();
            fragmentButton.setText("Avançar");
            countFrag += 1;
            return;
        }
        if(countFrag == 3){
            FragmentManager fragmentManager = getSupportFragmentManager();
            FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
            fragmentTransaction.replace(R.id.frame_layout, new ThirdOngoingFragment());
            fragmentTransaction.commit();
            fragmentButton.setText("Avançar");
            countFrag += 1;
            return;
        }
        if(countFrag == 4){
            openInitialActivity();
        }
    }

    private void openInitialActivity() {
        startActivity(new Intent(this, InitialActivity.class));
        finish();
    }
}