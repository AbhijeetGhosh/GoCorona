package com.example.gocorona;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import android.graphics.Color;
import android.os.Bundle;
import android.widget.Toast;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.MutableData;
import com.google.firebase.database.Transaction;
import com.google.firebase.database.ValueEventListener;
import com.hitomi.cmlibrary.CircleMenu;
import com.hitomi.cmlibrary.OnMenuSelectedListener;

import cn.pedant.SweetAlert.SweetAlertDialog;

public class PrayerActivity extends AppCompatActivity {

    CircleMenu prayer;
    SweetAlertDialog pop;
    DatabaseReference reff;
    Bundle extras;
    String country;
    CountryName countryName;

    int c1 = 0;

    String[] sm = {"In the Name of the Pasta, and of the Sauce, and of the Holy Meatballs…",
            "Accept His Noodly Magnificence into your heart, into your soul, and ye shall forever be free. R'Amen.",
            "In the Name of the Pasta, and of the Sauce, and of the Holy Meatballs…"};
    String[] buddhism = {"do you even Lyft bro",
                          "Brah chill the hell out will ya? ",
                            "sounds like a plan"};
    String[] eminem = {"1","2","3"};
    String[] greek = {"1","2","3"};
    String[] random = {"1","2","3"};



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_prayer);

        //getting current country
        extras = getIntent().getExtras();
        country = extras.getString("current_country");

        countryName = new CountryName();

        prayer = (CircleMenu)findViewById(R.id.prayer);

        //Circle Menu for prayer
        prayer.setMainMenu(Color.parseColor("#CDCDCD"),R.drawable.godicon,R.drawable.satanicon);
        //adding sub menu
        prayer.addSubMenu(Color.parseColor("#CDCDCD"),R.drawable.spaghettimonstericon);
        prayer.addSubMenu(Color.parseColor("#CDCDCD"),R.drawable.buddhismicon);
        prayer.addSubMenu(Color.parseColor("#CDCDCD"),R.drawable.eminemicon);
        prayer.addSubMenu(Color.parseColor("#CDCDCD"),R.drawable.greekicon);
        prayer.addSubMenu(Color.parseColor("#CDCDCD"),R.drawable.random);
        //adding functionality to sub menu
        prayer.setOnMenuSelectedListener(new OnMenuSelectedListener() {
            @Override
            public void onMenuSelected(int i) {
                if(i==0){
                    //add points
                    addPoints();
                    //popup
                    pop = new SweetAlertDialog(PrayerActivity.this, SweetAlertDialog.CUSTOM_IMAGE_TYPE);
                    pop.setTitleText("Spaghtti Monster!");
                    pop.setContentText(sm[c1]);
                    pop.setCustomImage(R.drawable.spaghettimonster1);
                    pop.setConfirmClickListener(new SweetAlertDialog.OnSweetClickListener() {
                        @Override
                        public void onClick(SweetAlertDialog sweetAlertDialog) {
                            finish();
                        }
                    });
                    pop.show();
                    c1++; //upgrading counter
                }
                else if(i==1){
                    //add points
                    addPoints();
                    //popup
                    pop = new SweetAlertDialog(PrayerActivity.this, SweetAlertDialog.CUSTOM_IMAGE_TYPE);
                    pop.setTitleText("Buddhism!");
                    pop.setContentText(buddhism[c1]);
                    pop.setCustomImage(R.drawable.buddism1);
                    pop.setConfirmClickListener(new SweetAlertDialog.OnSweetClickListener() {
                        @Override
                        public void onClick(SweetAlertDialog sweetAlertDialog) {
                            finish();
                        }
                    });
                    pop.show();
                    c1++;//upgrading counter
                }
                else if(i==2){
                    //add points
                    addPoints();
                    //popup
                    pop = new SweetAlertDialog(PrayerActivity.this, SweetAlertDialog.CUSTOM_IMAGE_TYPE);
                    pop.setTitleText("Eminem!");
                    pop.setContentText(eminem[c1]);
                    pop.setCustomImage(R.drawable.eminem1);
                    pop.setConfirmClickListener(new SweetAlertDialog.OnSweetClickListener() {
                        @Override
                        public void onClick(SweetAlertDialog sweetAlertDialog) {
                            finish();
                        }
                    });
                    pop.show();
                    c1++;//upgrading counter
                }
                else if(i==3){
                    //add points
                    addPoints();
                    //popup
                    pop = new SweetAlertDialog(PrayerActivity.this, SweetAlertDialog.CUSTOM_IMAGE_TYPE);
                    pop.setTitleText("Greek Gods eh!");
                    pop.setContentText(greek[c1]);
                    pop.setCustomImage(R.drawable.greek1);
                    pop.setConfirmClickListener(new SweetAlertDialog.OnSweetClickListener() {
                        @Override
                        public void onClick(SweetAlertDialog sweetAlertDialog) {
                            finish();
                        }
                    });
                    pop.show();
                    c1++;//upgrading counter
                }
                else if(i==4){
                    //add points
                    addPoints();
                    //popup
                    pop = new SweetAlertDialog(PrayerActivity.this, SweetAlertDialog.CUSTOM_IMAGE_TYPE);
                    pop.setTitleText("Choosing at random (secular much)!");
                    pop.setContentText(random[c1]);
                    pop.setCustomImage(R.drawable.level_reaction);
                    pop.setConfirmClickListener(new SweetAlertDialog.OnSweetClickListener() {
                        @Override
                        public void onClick(SweetAlertDialog sweetAlertDialog) {
                            finish();
                        }
                    });
                    pop.show();
                    c1++;//upgrading counter
                }
            }
        });

    }

    //adding points
    public void addPoints(){
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
                    mutableData.setValue(Integer.parseInt(mutableData.getValue().toString()) + 100); //adding one point to existing points``
                }
                return Transaction.success(mutableData);

            }

            @Override
            public void onComplete(@Nullable DatabaseError databaseError, boolean b, @Nullable DataSnapshot dataSnapshot) {

            }
        });
    }
}
