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
import pl.droidsonroids.gif.GifImageButton;

public class MainActivity extends AppCompatActivity {

    Button Hit;
    DatabaseReference reff;
    CountryName countryName;
    EditText CurrentCount, personalScore, currentLevel;
    SweetAlertDialog pop;
    Spinner mySpinner;
    FloatingActionButton cussBtn, noiseBtn;
    com.google.android.material.floatingactionbutton.FloatingActionButton prayerFab, sanitizerFab, Home;
    GifImageButton virus;
    int personalScoreValue = 0;
    int level = 1;
    String country = "India";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        //only three levels in first release.. punch, curse and pray. (sanitize? idk)

        Toast.makeText(this,"firebase successful",Toast.LENGTH_LONG).show();

        //the initializations
        CurrentCount = (EditText)findViewById(R.id.CurrCounter);
        personalScore = (EditText)findViewById(R.id.persona_score);
        currentLevel = (EditText)findViewById(R.id.current_level);
        prayerFab = (com.google.android.material.floatingactionbutton.FloatingActionButton)findViewById(R.id.prayerFAB);
        sanitizerFab = (com.google.android.material.floatingactionbutton.FloatingActionButton)findViewById(R.id.sanitizerFab);
        Home = (com.google.android.material.floatingactionbutton.FloatingActionButton)findViewById(R.id.homeFab);
        virus = (GifImageButton)findViewById(R.id.gifBtn);
        countryName = new CountryName();
        //int personal_score = 0;

        //opening home as soon as the app begins
        openHome();


        //---------------------------------------------------------------------------------------------------------------


        Home.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
             openHome();
            }
        });


        virus.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                Toast.makeText(MainActivity.this, "1 Point!", Toast.LENGTH_LONG).show();
                addpoints(1);
            }
        });


        //prayer FAB
        prayerFab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                openPrayerActivity();
            }
        });
        prayerFab.setVisibility(View.INVISIBLE);

        //spinner
//        mySpinner = (Spinner)findViewById(R.id.spinner); //initializing
//        ArrayAdapter<String> myAdapter = new ArrayAdapter<String>(MainActivity.this, //creating adapter
//                android.R.layout.simple_list_item_1, getResources().getStringArray(R.array.Countries));
//        myAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
//        mySpinner.setAdapter(myAdapter);

        //sanitizerFab
        sanitizerFab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                addpoints(10);
                Toast.makeText(MainActivity.this, "10 Points!!", Toast.LENGTH_LONG).show();
            }
        });
        sanitizerFab.setVisibility(View.INVISIBLE);


        //floating action button for Cuss words
        ImageView icon1 = new ImageView(MainActivity.this);
        icon1.setImageResource(R.drawable.cuss);
        //
        cussBtn = new FloatingActionButton.Builder(this).setContentView(icon1).build();
        //creating sub action buttons
        SubActionButton.Builder builder=new SubActionButton.Builder(this);
        //
        ImageView deleteIcon=new ImageView(this);
        deleteIcon.setImageResource(R.drawable.comebackicon);
        SubActionButton comeback=builder.setContentView(deleteIcon).build();

        ImageView removeIcon=new ImageView(this);
        removeIcon.setImageResource(R.drawable.kissmyassicon);
        SubActionButton kissmyass=builder.setContentView(removeIcon).build();

        ImageView addIcon=new ImageView(this);
        addIcon.setImageResource(R.drawable.middlefinger);
        SubActionButton middlefinger=builder.setContentView(addIcon).build();
        //creating menu
        final FloatingActionMenu fam=new FloatingActionMenu.Builder(this)
                .addSubActionView(middlefinger)
                .addSubActionView(kissmyass)
                .addSubActionView(comeback)
                .attachTo(cussBtn)
                .build();

        //setting onClickListener of subaction buttons
        comeback.setOnClickListener(new View.OnClickListener(){
            public void onClick(View view){
                Toast.makeText(MainActivity.this, "Comback button", Toast.LENGTH_LONG).show();
                addpoints(10);
                //popup
                pop = new SweetAlertDialog(MainActivity.this, SweetAlertDialog.CUSTOM_IMAGE_TYPE);
                pop.setTitleText("No you");
                pop.setContentText("message");
                pop.setCustomImage(R.drawable.comebackicon);
                pop.show();
            }
        });
        kissmyass.setOnClickListener(new View.OnClickListener(){
            public void onClick(View view){
                Toast.makeText(MainActivity.this, "kissmyass button", Toast.LENGTH_LONG).show();
                addpoints(10);
                //popup
                pop = new SweetAlertDialog(MainActivity.this, SweetAlertDialog.CUSTOM_IMAGE_TYPE);
                pop.setTitleText("kissmyass");
                pop.setContentText("message");
                pop.setCustomImage(R.drawable.kissmyassicon);
                pop.show();
            }
        });
        middlefinger.setOnClickListener(new View.OnClickListener(){
            public void onClick(View view){
                Toast.makeText(MainActivity.this, "offend button", Toast.LENGTH_LONG).show();
                addpoints(10);
                //popup
                pop = new SweetAlertDialog(MainActivity.this, SweetAlertDialog.CUSTOM_IMAGE_TYPE);
                pop.setTitleText("yo mama");
                pop.setContentText("message");
                pop.setCustomImage(R.drawable.comebackicon);
                pop.show();
            }
        });
        cussBtn.setVisibility(View.INVISIBLE); //making the button invisible




//        //floating action button for noises
//        ImageView icon2 = new ImageView(MainActivity.this);
//        icon2.setImageResource(R.drawable.add);
//        //
//        noiseBtn = new FloatingActionButton.Builder(this).setContentView(icon2).setPosition(2).build();
//        //creating sub action buttons
//        SubActionButton.Builder builder1=new SubActionButton.Builder(this);
//        //
//        ImageView deleteIcon1=new ImageView(this);
//        deleteIcon1.setImageResource(R.drawable.ic_menu_vector);
//        SubActionButton deleteBtn1=builder1.setContentView(deleteIcon1).build();
//
//        ImageView removeIcon1=new ImageView(this);
//        removeIcon1.setImageResource(R.drawable.level_reaction);
//        SubActionButton removeBtn1=builder1.setContentView(removeIcon1).build();
//
//        ImageView addIcon1=new ImageView(this);
//        addIcon1.setImageResource(R.drawable.sanitizer);
//        SubActionButton addBtn1=builder1.setContentView(addIcon1).build();
//        //creating menu
//        final FloatingActionMenu fam1=new FloatingActionMenu.Builder(this)
//                .addSubActionView(addBtn1)
//                .addSubActionView(removeBtn1)
//                .addSubActionView(deleteBtn1)
//                .attachTo(noiseBtn)
//                .build();
//        //setting onClickListener of subaction buttons
//        addBtn1.setOnClickListener(new View.OnClickListener(){
//            public void onClick(View view){
//                Toast.makeText(MainActivity.this, "works", Toast.LENGTH_LONG).show();
//            }
//        });
////        //cussBtn.setVisibility(View.INVISIBLE); //making the button invisible






    }

    //opening prayeractivity
    public void openPrayerActivity() {
        Intent intent1 = new Intent(MainActivity.this, PrayerActivity.class);
        intent1.putExtra("current_country", String.valueOf(mySpinner.getSelectedItem()));//adding additional data using putExtras()
        startActivity(intent1);
    }

    //openning home
    public void openHome(){
        Intent intent2 = new Intent(MainActivity.this, Home.class);
        startActivityForResult(intent2,1);
    }


    //getting country name from Home activity
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == 1) {
            if(resultCode == RESULT_OK) {
                String strEditText = data.getStringExtra("editTextValue");
                country = strEditText;
                Toast.makeText(MainActivity.this, country, Toast.LENGTH_LONG).show();
                DatabaseReference mDatabase = FirebaseDatabase.getInstance().getReference().child("CountryName");
                //mDatabase.push().setValue(country);
                mDatabase.child(country).setValue(0);
            }
        }
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


        if(score>30&&level==1){
            level = level+1; //upgrade level
            sanitizerFab.setVisibility(View.VISIBLE); //Making sanitize visible
            //popup
            pop = new SweetAlertDialog(this, SweetAlertDialog.CUSTOM_IMAGE_TYPE);
                   pop.setTitleText("Sweet!");
                   pop.setContentText("You just upgraded a level and the ability to use sanitizer");
                   pop.setCustomImage(R.drawable.level_reaction);
                   pop.show();
        }
        else if(score>100&&level==2){
            level = level+1; //upgrade level
            //set visibility of something else
            cussBtn.setVisibility(View.VISIBLE);

            //popup
            pop = new SweetAlertDialog(this, SweetAlertDialog.CUSTOM_IMAGE_TYPE);
            pop.setTitleText("Sweet!");
            pop.setContentText("You just upgraded a level and the ability to curse");
            pop.setCustomImage(R.drawable.level_reaction);
            pop.show();
        }
        else if(score>150&&level==3){
            level = level+1; //upgrade level
            prayerFab.setVisibility(View.VISIBLE);
            //popup
            pop = new SweetAlertDialog(this, SweetAlertDialog.CUSTOM_IMAGE_TYPE);
            pop.setTitleText("Sweet!");
            pop.setContentText("You just upgraded a level and the ability to curse");
            pop.setCustomImage(R.drawable.level_reaction);
            pop.show();


        }
    }

    public void updateCurrentCountryCount(){
        //loading current count in edit box
        //final String country = String.valueOf(mySpinner.getSelectedItem()); //getting the country name from spinner
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

    public void addpoints(final int a){
        personalScoreValue = personalScoreValue + a; //updating personal score
        updatePersonalScore(personalScoreValue); //displaying personal score
        updateCurrentCountryCount(); //displaying current country's score

        //final String country = String.valueOf(mySpinner.getSelectedItem()); //getting the country name from spinner
        countryName.setCountry(country); //adding it to the object of CountryName class's counstructor

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
                    mutableData.setValue(Integer.parseInt(mutableData.getValue().toString()) + a); //adding one point to existing points``
                }
                return Transaction.success(mutableData);

            }

            @Override
            public void onComplete(@Nullable DatabaseError databaseError, boolean b, @Nullable DataSnapshot dataSnapshot) {

            }
        });
    }




}



