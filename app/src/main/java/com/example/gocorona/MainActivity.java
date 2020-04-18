package com.example.gocorona;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.Toast;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.MutableData;
import com.google.firebase.database.Transaction;
import com.google.firebase.database.ValueEventListener;

import cn.pedant.SweetAlert.SweetAlertDialog;

public class MainActivity extends AppCompatActivity {

    Button Hit, sanitizer1, scoreBoard;
    DatabaseReference reff;
    CountryName countryName;
    EditText CurrentCount, personalScore, currentLevel;
    SweetAlertDialog pop;
    int personalScoreValue = 0;
    int level = 1;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        Toast.makeText(this,"firebase successful",Toast.LENGTH_LONG).show();

        //the initializations
        Hit = (Button)findViewById(R.id.btn);
        sanitizer1 = (Button)findViewById(R.id.sanitizer1);
        CurrentCount = (EditText)findViewById(R.id.CurrCounter);
        personalScore = (EditText)findViewById(R.id.persona_score);
        currentLevel = (EditText)findViewById(R.id.current_level);
        scoreBoard = (Button)findViewById(R.id.scoreBoard);
        countryName = new CountryName();
        //int personal_score = 0;



        //spinner
        final Spinner mySpinner = (Spinner)findViewById(R.id.spinner); //initializing
        ArrayAdapter<String> myAdapter = new ArrayAdapter<String>(MainActivity.this, //creating adapter
                android.R.layout.simple_list_item_1, getResources().getStringArray(R.array.Countries));
        myAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        mySpinner.setAdapter(myAdapter);

        //reff = FirebaseDatabase.getInstance().getReference().child("CountryName");


        //Hit button
        Hit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                personalScoreValue = personalScoreValue + 1;
                updatePersonalScore(personalScoreValue);
                Toast.makeText(MainActivity.this, "1 Point!", Toast.LENGTH_LONG).show();

                final String country = String.valueOf(mySpinner.getSelectedItem()); //getting the country name from spinner
                countryName.setCountry(country); //adding it to the object of CountryName class's counstructor

                //* getting current value of count *

                //initializing the reference
                reff = FirebaseDatabase.getInstance().getReference().child("CountryName").child(country);

                //making transaction and adding one point
                reff.runTransaction(new Transaction.Handler() {
                    @NonNull
                    @Override
                    public Transaction.Result doTransaction(@NonNull MutableData mutableData) {

                        //Currently no value in eventAmount
                        if(mutableData.getValue() == null){
                            mutableData.setValue(1); //making it to 1
                        }
                        else{
                            mutableData.setValue(Integer.parseInt(mutableData.getValue().toString()) + 1); //adding one point to existing points``
                        }
                        return Transaction.success(mutableData);

                    }

                    @Override
                    public void onComplete(@Nullable DatabaseError databaseError, boolean b, @Nullable DataSnapshot dataSnapshot) {

                    }
                });


            }
        });

        sanitizer1.setVisibility(View.INVISIBLE);
        sanitizer1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {


                personalScoreValue = personalScoreValue + 10;
                updatePersonalScore(personalScoreValue);
                Toast.makeText(MainActivity.this, "10 Points!!", Toast.LENGTH_LONG).show();

                final String country = String.valueOf(mySpinner.getSelectedItem()); //getting the country name from spinner
                countryName.setCountry(country); //adding it to the object of CountryName class's counstructor

                //* getting current value of count *

                //initializing the reference
                reff = FirebaseDatabase.getInstance().getReference().child("CountryName").child(country);

                //making transaction and adding one point
                reff.runTransaction(new Transaction.Handler() {
                    @NonNull
                    @Override
                    public Transaction.Result doTransaction(@NonNull MutableData mutableData) {

                        //Currently no value in eventAmount
                        if(mutableData.getValue() == null){
                            mutableData.setValue(1); //making it to 1
                        }
                        else{
                            mutableData.setValue(Integer.parseInt(mutableData.getValue().toString()) + 10); //adding one point to existing points``
                        }
                        return Transaction.success(mutableData);

                    }

                    @Override
                    public void onComplete(@Nullable DatabaseError databaseError, boolean b, @Nullable DataSnapshot dataSnapshot) {

                    }
                });

            }
        });


        //loading current count in edit box
        final String country = String.valueOf(mySpinner.getSelectedItem()); //getting the country name from spinner
        reff = FirebaseDatabase.getInstance().getReference().child("CountryName").child(country); //creating reffernec to selected country
        reff.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                String currentCount = dataSnapshot.getValue().toString();
                CurrentCount.setText(country + "'s points :" + currentCount);
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });

        //button to lauch scoreboared
        scoreBoard.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                openScoreBoared();
            }
        });




        }

    //openning scoreboared
    public void openScoreBoared() {
        Intent intent = new Intent(this, Score_Board.class);
        startActivity(intent);
    }

    //setting personal score
    public void updatePersonalScore(int score){
        String personalScoreString = Integer.toString(score);
        personalScore.setText("Personal Score : " + personalScoreString);

        checkLevel(score);
    }

    //to check level and make a new features visible
    public void checkLevel(int score){
        String levelStr = Integer.toString(level);
        currentLevel.setText("LEVEL : " + levelStr);


        if(score>50&&level==1){
            level = level+1; //upgrade level
            sanitizer1.setVisibility(View.VISIBLE); //Making sanitize visible
            //popup
            pop = new SweetAlertDialog(this, SweetAlertDialog.CUSTOM_IMAGE_TYPE);
                   pop.setTitleText("Sweet!");
                   pop.setContentText("You just upgraded a level");
                   pop.setCustomImage(R.drawable.level_reaction);
                   pop.show();
        }
        else if(score>100&&level==2){
            level = level+1; //upgrade level
            //set visibility of something else

            //popup
            pop = new SweetAlertDialog(this, SweetAlertDialog.CUSTOM_IMAGE_TYPE);
            pop.setTitleText("Sweet!");
            pop.setContentText("You just upgraded a level");
            pop.setCustomImage(R.drawable.level_reaction);
            pop.show();

        }
    }

}



