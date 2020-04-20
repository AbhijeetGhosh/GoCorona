package com.example.gocorona;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Spinner;
import android.widget.Toast;

import com.github.mikephil.charting.utils.ColorTemplate;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.MutableData;
import com.google.firebase.database.Transaction;
import com.google.firebase.database.ValueEventListener;
import com.hitomi.cmlibrary.CircleMenu;
import com.hitomi.cmlibrary.OnMenuSelectedListener;
import com.oguzdev.circularfloatingactionmenu.library.FloatingActionButton;
import com.oguzdev.circularfloatingactionmenu.library.FloatingActionMenu;
import com.oguzdev.circularfloatingactionmenu.library.SubActionButton;

import cn.pedant.SweetAlert.SweetAlertDialog;

public class MainActivity extends AppCompatActivity {

    Button Hit, sanitizer1, scoreBoard, settings;
    DatabaseReference reff;
    CountryName countryName;
    EditText CurrentCount, personalScore, currentLevel;
    SweetAlertDialog pop;
    Spinner mySpinner;
    FloatingActionButton cussBtn, noiseBtn;
    com.google.android.material.floatingactionbutton.FloatingActionButton prayerFab, precaustionFab;
    int personalScoreValue = 0;
    int level = 1;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        //only three levels in first release.. punch, curse and pray. (sanitize? idk)

        Toast.makeText(this,"firebase successful",Toast.LENGTH_LONG).show();

        //the initializations
        Hit = (Button)findViewById(R.id.btn);
        sanitizer1 = (Button)findViewById(R.id.sanitizer1);
        CurrentCount = (EditText)findViewById(R.id.CurrCounter);
        personalScore = (EditText)findViewById(R.id.persona_score);
        currentLevel = (EditText)findViewById(R.id.current_level);
        scoreBoard = (Button)findViewById(R.id.scoreBoard);
        prayerFab = (com.google.android.material.floatingactionbutton.FloatingActionButton)findViewById(R.id.prayerFAB);
        precaustionFab = (com.google.android.material.floatingactionbutton.FloatingActionButton)findViewById(R.id.precautions);
        settings = (Button)findViewById(R.id.home);
        countryName = new CountryName();
        //int personal_score = 0;


        //opening home as soon as the app begins
        openHome();

        //settings/Home
        settings.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                openHome();
            }
        });

        //prayer FAB
        prayerFab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                openPrayerActivity();
            }
        });

        //spinner
        mySpinner = (Spinner)findViewById(R.id.spinner); //initializing
        ArrayAdapter<String> myAdapter = new ArrayAdapter<String>(MainActivity.this, //creating adapter
                android.R.layout.simple_list_item_1, getResources().getStringArray(R.array.Countries));
        myAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        mySpinner.setAdapter(myAdapter);

        //reff = FirebaseDatabase.getInstance().getReference().child("CountryName");


        //Hit button
        Hit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                personalScoreValue = personalScoreValue + 1; //updating personal score
                updatePersonalScore(personalScoreValue); //displaying personal score
                updateCurrentCountryCount(); //displaying current country's score
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


                personalScoreValue = personalScoreValue + 10; //updating personal score
                updatePersonalScore(personalScoreValue); //displaying personal score
                updateCurrentCountryCount(); //displaying current country's score

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


        //button to lauch scoreboared
        scoreBoard.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                openScoreBoared();
            }
        });





        //floating action button for Cuss words
        ImageView icon1 = new ImageView(MainActivity.this);
        icon1.setImageResource(R.drawable.cuss);
        //
        cussBtn = new FloatingActionButton.Builder(this).setContentView(icon1).build();
        //creating sub action buttons
        SubActionButton.Builder builder=new SubActionButton.Builder(this);
        //
        ImageView deleteIcon=new ImageView(this);
        deleteIcon.setImageResource(R.drawable.ic_menu_vector);
        SubActionButton deleteBtn=builder.setContentView(deleteIcon).build();

        ImageView removeIcon=new ImageView(this);
        removeIcon.setImageResource(R.drawable.level_reaction);
        SubActionButton removeBtn=builder.setContentView(removeIcon).build();

        ImageView addIcon=new ImageView(this);
        addIcon.setImageResource(R.drawable.sanitizer);
        SubActionButton addBtn=builder.setContentView(addIcon).build();
        //creating menu
        final FloatingActionMenu fam=new FloatingActionMenu.Builder(this)
                .addSubActionView(addBtn)
                .addSubActionView(removeBtn)
                .addSubActionView(deleteBtn)
                .attachTo(cussBtn)
                .build();
        //setting onClickListener of subaction buttons
        addBtn.setOnClickListener(new View.OnClickListener(){
            public void onClick(View view){
                Toast.makeText(MainActivity.this, "works", Toast.LENGTH_LONG).show();
            }
        });
        //cussBtn.setVisibility(View.INVISIBLE); //making the button invisible




        //floating action button for noises
        ImageView icon2 = new ImageView(MainActivity.this);
        icon2.setImageResource(R.drawable.add);
        //
        noiseBtn = new FloatingActionButton.Builder(this).setContentView(icon2).setPosition(2).build();
        //creating sub action buttons
        SubActionButton.Builder builder1=new SubActionButton.Builder(this);
        //
        ImageView deleteIcon1=new ImageView(this);
        deleteIcon1.setImageResource(R.drawable.ic_menu_vector);
        SubActionButton deleteBtn1=builder1.setContentView(deleteIcon1).build();

        ImageView removeIcon1=new ImageView(this);
        removeIcon1.setImageResource(R.drawable.level_reaction);
        SubActionButton removeBtn1=builder1.setContentView(removeIcon1).build();

        ImageView addIcon1=new ImageView(this);
        addIcon1.setImageResource(R.drawable.sanitizer);
        SubActionButton addBtn1=builder1.setContentView(addIcon1).build();
        //creating menu
        final FloatingActionMenu fam1=new FloatingActionMenu.Builder(this)
                .addSubActionView(addBtn1)
                .addSubActionView(removeBtn1)
                .addSubActionView(deleteBtn1)
                .attachTo(noiseBtn)
                .build();
        //setting onClickListener of subaction buttons
        addBtn1.setOnClickListener(new View.OnClickListener(){
            public void onClick(View view){
                Toast.makeText(MainActivity.this, "works", Toast.LENGTH_LONG).show();
            }
        });
//        //cussBtn.setVisibility(View.INVISIBLE); //making the button invisible






    }

    //openning scoreboared
    public void openScoreBoared() {
        Intent intent = new Intent(this, Score_Board.class);
        startActivity(intent);
    }

    public void openPrayerActivity() {
        Intent intent1 = new Intent(MainActivity.this, PrayerActivity.class);
        intent1.putExtra("current_country", String.valueOf(mySpinner.getSelectedItem()));//adding additional data using putExtras()
        startActivity(intent1);
    }

    //openning home
    public void openHome(){
        Intent intent2 = new Intent(MainActivity.this, Home.class);
        startActivity(intent2);
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


        if(score>5&&level==1){
            level = level+1; //upgrade level
            sanitizer1.setVisibility(View.VISIBLE); //Making sanitize visible
            //popup
            pop = new SweetAlertDialog(this, SweetAlertDialog.CUSTOM_IMAGE_TYPE);
                   pop.setTitleText("Sweet!");
                   pop.setContentText("You just upgraded a level");
                   pop.setCustomImage(R.drawable.level_reaction);
                   pop.show();
        }
        else if(score>10&&level==2){
            level = level+1; //upgrade level
            //set visibility of something else
            //cussBtn.setVisibility(View.VISIBLE);

            //popup
            pop = new SweetAlertDialog(this, SweetAlertDialog.CUSTOM_IMAGE_TYPE);
            pop.setTitleText("Sweet!");
            pop.setContentText("You just upgraded a level");
            pop.setCustomImage(R.drawable.level_reaction);
            pop.show();
        }
        else if(score>15&&level==3){
            level = level+1; //upgrade level

        }
    }

    public void updateCurrentCountryCount(){
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
    }




}



