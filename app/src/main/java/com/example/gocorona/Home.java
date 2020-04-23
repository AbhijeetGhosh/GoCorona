package com.example.gocorona;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.media.MediaPlayer;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;
import android.widget.VideoView;

import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.mukesh.countrypicker.fragments.CountryPicker;
import com.mukesh.countrypicker.interfaces.CountryPickerListener;
import com.ramotion.directselect.DSDefaultPickerBox;
import com.ramotion.directselect.DSListView;
import com.ybs.countrypicker.Country;

import java.util.ArrayList;
import java.util.List;

public class Home extends AppCompatActivity {


    // Create a VideoView variable, a MediaPlayer variable, and an int to hold the current
    // video position.
    private VideoView videoBG;
    MediaPlayer mMediaPlayer;
    int mCurrentVideoPosition;
    //DSListView dsListView;
    String currentCountry;
    Button play1, scoreboard, selectCountry;
    String currCountry = "India";
    TextView countryHolder;
    //Button c;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);

        setTitle("HOME (since quarantine duh)");

        // Hook up the VideoView to our UI.
        videoBG = (VideoView) findViewById(R.id.video);
        //dsListView = (DSListView)findViewById(R.id.ds_picker);
        //c = (Button)findViewById(R.id.ct);
        play1 = (Button)findViewById(R.id.play);
        scoreboard = (Button)findViewById(R.id.scoreBoard2);
        selectCountry = (Button)findViewById(R.id.selectCountry);
        countryHolder = (TextView) findViewById(R.id.countryHolder);


        countryHolder.setText("India");

        final ArrayList<String> list=new ArrayList<String>();//Creating arraylist
        list.add("India");//Adding object in arraylist
        list.add("USA");
        list.add("China");

        final CountryName countryName = new CountryName();
        countryName.setCountry("India");
        countryName.setCountry("USA");
        countryName.setCountry("China");


        selectCountry.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

//                CountryPicker picker = CountryPicker.newInstance("Select Country");  // dialog title
//
//                //CountryPicker picker = CountryPicker.newInstance("Select Country");
//                List<Country> countries = picker.countrie//Get all countries
//                Country country = picker.getUserCountryInfo(this); //Get user country based on sim
//                Country country = picker.getCountryByLocale(context , local); //Get country based on Locale
//                Country country = picker.getCountryByName(context , country_name); //Get country by country name
//
//                picker.setListener(new CountryPickerListener() {
//                    @Override
//                    public void onSelectCountry(String name, String code, String dialCode, int flagDrawableResID) {
//                        countryHolder.setText(name);
//                    }
//                });
//                picker.show(getSupportFragmentManager(), "COUNTRY_PICKER");

                CountryPicker picker = CountryPicker.newInstance("Select Country");
                picker.show(getSupportFragmentManager(), "COUNTRY_PICKER");
                picker.setListener(new CountryPickerListener() {
                    @Override
                    public void onSelectCountry(String s, String s1, String s2, int i) {
                        countryHolder.setText(s);
                        String country1 = countryHolder.getText().toString();
                        Intent intent = new Intent();
                        intent.putExtra("editTextValue", country1);
                        setResult(RESULT_OK, intent);
                        finish();
                    }
                });


            }
        });



        scoreboard.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                openScoreBoared();
            }
        });

        play1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String country1 = countryHolder.getText().toString();
                Intent intent = new Intent();
                intent.putExtra("editTextValue", country1);
                setResult(RESULT_OK, intent);
                finish();
            }
        });


//        c.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View view) {
//                String country1 = dsListView.getSelectedItem().toString();
//                if(country1!=null) {
//                    Toast.makeText(Home.this, country1, Toast.LENGTH_LONG).show();
//                }
//            }
//        });


        // Build your video Uri
        Uri uri = Uri.parse("android.resource://" // First start with this,
                + getPackageName() // then retrieve your package name,
                + "/" // add a slash,
                + R.raw.dope); // and then finally add your video resource. Make sure it is stored
        // in the raw folder.

        // Set the new Uri to our VideoView
        videoBG.setVideoURI(uri);
        // Start the VideoView
        videoBG.start();

        // Set an OnPreparedListener for our VideoView. For more information about VideoViews,
        // check out the Android Docs: https://developer.android.com/reference/android/widget/VideoView.html
        videoBG.setOnPreparedListener(new MediaPlayer.OnPreparedListener() {
            @Override
            public void onPrepared(MediaPlayer mediaPlayer) {
                mMediaPlayer = mediaPlayer;
                // We want our video to play over and over so we set looping to true.
                mMediaPlayer.setLooping(true);
                // We then seek to the current posistion if it has been set and play the video.
                if (mCurrentVideoPosition != 0) {
                    mMediaPlayer.seekTo(mCurrentVideoPosition);
                    mMediaPlayer.start();
                }
            }
        });





    }

    //We must override onPause(), onResume(), and onDestroy() to properly handle our
    //VideoView.
     //*/

    @Override
    protected void onPause() {
        super.onPause();
        // Capture the current video position and pause the video.
        mCurrentVideoPosition = mMediaPlayer.getCurrentPosition();
        videoBG.pause();
    }

    @Override
    protected void onResume() {
        super.onResume();
        // Restart the video when resuming the Activity
        videoBG.start();
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        // When the Activity is destroyed, release our MediaPlayer and set it to null.
        mMediaPlayer.release();
        mMediaPlayer = null;
    }

    //openning scoreboared
    public void openScoreBoared() {
        Intent intent = new Intent(this, Score_Board.class);
        startActivity(intent);
    }

}
