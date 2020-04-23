package com.example.gocorona;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.nfc.Tag;
import android.os.Bundle;
import android.util.Log;

import com.github.mikephil.charting.charts.BarChart;
import com.github.mikephil.charting.components.AxisBase;
import com.github.mikephil.charting.components.Description;
import com.github.mikephil.charting.components.XAxis;
import com.github.mikephil.charting.data.BarData;
import com.github.mikephil.charting.data.BarDataSet;
import com.github.mikephil.charting.data.BarEntry;
import com.github.mikephil.charting.formatter.IAxisValueFormatter;
import com.github.mikephil.charting.formatter.IndexAxisValueFormatter;
import com.github.mikephil.charting.utils.ColorTemplate;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.Map;

public class Score_Board extends AppCompatActivity {

    BarChart barChart;
    ArrayList<BarEntry> barEntryArrayList;
    ArrayList<String> labelsNames;
    DatabaseReference reff;
    //int IndiaScore;

    ArrayList<countryScore> countryScoreDataArrayList = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_score__board);

        barChart = (BarChart)findViewById(R.id.barChart);

        //get the score
        reff = FirebaseDatabase.getInstance().getReference(); //creating reffernec to selected country
        reff.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {

                String value = String.valueOf(dataSnapshot.child("CountryName").getValue());
                //Log.i("Our Value", value);

                Map<String,Integer > data = (Map<String,Integer>) dataSnapshot.getValue();
                //Log.d("COUNTRY: "+data.get("India"));
                    //Log.i("Our country",data.getKey()+" "+data.getValue());




//                String IndiaCount = dataSnapshot.child("India").getValue().toString();
//                String USACount = dataSnapshot.child("USA").getValue().toString();
//                String ChinaCount = dataSnapshot.child("China").getValue().toString();
//
//                int IndiaScore = Integer.parseInt(IndiaCount);
//                int USAScore = Integer.parseInt(USACount);
//                int ChinaScore = Integer.parseInt(ChinaCount);
//
//
//                //bar chart code
//                //create new object of bar entries arrayList and labels arrayList
//                barEntryArrayList = new ArrayList<>();
//                labelsNames = new ArrayList<>();
//                fillCountryScore(IndiaScore, ChinaScore, USAScore);
//
//                for (int i = 0; i < countryScoreDataArrayList.size(); i++){
//                    String country = countryScoreDataArrayList.get(i).getCountry();
//                    int score = countryScoreDataArrayList.get(i).getScore();
//
//                    barEntryArrayList.add(new BarEntry(i,score));
//                    labelsNames.add(country);
//                }
//
//                BarDataSet barDataSet = new BarDataSet(barEntryArrayList, "Country Scores");
//                barDataSet.setColors(ColorTemplate.COLORFUL_COLORS);
//                Description description = new Description();
//                description .setText("Countries");
//                barChart.setDescription(description);
//                BarData barData = new BarData(barDataSet);
//                barChart.setData(barData);
//
//                //we need to set xaxis value formater
//                XAxis xAxis = barChart.getXAxis();
//                xAxis.setValueFormatter(new IndexAxisValueFormatter(labelsNames));
//
//                //set position of labels(country names)
//                xAxis.setPosition(XAxis.XAxisPosition.TOP);
//                xAxis.setDrawGridLines(false);
//                xAxis.setDrawAxisLine(false);
//                xAxis.setGranularity(1f);
//                xAxis.setLabelCount(labelsNames.size());
//                xAxis.setLabelRotationAngle(270);
//                barChart.animateY(2000);
//                barChart.invalidate();



            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });



    }

//    private void firebaseDataRetrievalFuckUp(String score) {
//        int IndiaScore1 = Integer.parseInt(score);
//        IndiaScore = IndiaScore1;
//    }

    private void  fillCountryScore(int IndiaScore, int ChinaScore, int USAScore ){
        countryScoreDataArrayList.clear();
        countryScoreDataArrayList.add(new countryScore("India",IndiaScore));
        countryScoreDataArrayList.add(new countryScore("China",ChinaScore));
        countryScoreDataArrayList.add(new countryScore("USA",USAScore));


    }

}
