package com.example.joshua.caloriecounter;

        import java.util.ArrayList;
        import java.util.HashMap;
        import java.util.List;
        import android.app.Activity;
        import android.net.Uri;
        import android.os.Bundle;
        import android.view.View;
        import android.view.View.OnClickListener;
        import android.widget.ArrayAdapter;
        import android.widget.Button;
        import android.widget.EditText;
        import android.widget.Spinner;
        import android.widget.TextView;
        import android.widget.Toast;

        import com.google.android.gms.appindexing.Action;
        import com.google.android.gms.appindexing.AppIndex;
        import com.google.android.gms.appindexing.Thing;
        import com.google.android.gms.common.api.GoogleApiClient;

public class CalorieConverter extends Activity {

    // conversions here AMOUNT TO BURN 100 CALORIES
    public static final double PUSHUP = 350; //REP
    public static final double SITUP = 200; //REP
    public static final double SQUAT = 225; //REP
    public static final double LEGLIFT = 25;
    public static final double PLANK = 25;
    public static final double JUMPING = 10;
    public static final double PULLUP = 100; // rep!
    public static final double CYCLING = 12;
    public static final double WALKING = 20;
    public static final double JOGGING = 12;
    public static final double SWIMMING = 13;
    public static final double STAIR = 15;
    public HashMap<String, Double> exercises;

    private Spinner spinner1;
    private EditText exerciseCount;
    private Button btnSubmit;
    private TextView calorieDisplay, pushupEquiv, situpEquiv, jumpingEquiv, joggingEquiv;
    private double calories;
    /**
     * ATTENTION: This was auto-generated to implement the App Indexing API.
     * See https://g.co/AppIndexing/AndroidStudio for more information.
     */
    private GoogleApiClient client;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_calorie_converter);

        addListenerOnButton();
        addListenerOnSpinnerItemSelection();
        // ATTENTION: This was auto-generated to implement the App Indexing API.
        // See https://g.co/AppIndexing/AndroidStudio for more information.
        client = new GoogleApiClient.Builder(this).addApi(AppIndex.API).build();
        exercises = new HashMap<String, Double>();
        exercises.put("Pushups (reps)", PUSHUP);
        exercises.put("Situps (reps)", SITUP);
        exercises.put("Squats (reps)", SQUAT);
        exercises.put("Leg-lifts (min)", LEGLIFT);
        exercises.put("Planks (min)", PLANK);
        exercises.put("Jumping Jacks (min)", JUMPING);
        exercises.put("Pullups (reps)", PULLUP);
        exercises.put("Cycling (min)", CYCLING);
        exercises.put("Walking (min)", WALKING);
        exercises.put("Jogging (min)", JOGGING);
        exercises.put("Swimming (min)", SWIMMING);
        exercises.put("Stair-Climbing (min)", STAIR);
    }



    public void addListenerOnSpinnerItemSelection() {
        spinner1 = (Spinner) findViewById(R.id.spinner1);
        spinner1.setOnItemSelectedListener(new CustomOnItemSelectedListener());
    }

    /*
    take a number of an exercise (or time) and return calorie count
     */
    public double countToCal(String exercise, String strCount) {
        double count;
        if (strCount.isEmpty())
            return 0.0;
        if (exercise.isEmpty())
            return -0.01;
        else
            count = Double.parseDouble(strCount);

        if (exercises.containsKey(exercise)) {
            double temp = (count * 100) / exercises.get(exercise);
            return Math.floor(temp * 10) / 10;
        }
        else
            return -1.0;

    }
    // get the selected dropdown list value
    public void addListenerOnButton() {


        exerciseCount = (EditText) findViewById(R.id.exerciseCount);
        btnSubmit = (Button) findViewById(R.id.btnSubmit);
        calorieDisplay = (TextView) findViewById(R.id.calorieDisplay);
        pushupEquiv = (TextView) findViewById(R.id.pushupEquiv);
        situpEquiv = (TextView) findViewById(R.id.situpEquiv);
        jumpingEquiv = (TextView) findViewById(R.id.jumpingEquiv);
        joggingEquiv = (TextView) findViewById(R.id.joggingEquiv);

        btnSubmit.setOnClickListener(new OnClickListener() {

            @Override
            public void onClick(View v) {
                String strCount = String.valueOf(exerciseCount.getText());
                spinner1 = (Spinner) findViewById(R.id.spinner1);
                String exercise = String.valueOf(spinner1.getSelectedItem());
                calories = countToCal(exercise, strCount);
                calorieDisplay.setText("Total Calories: " + calories);
                pushupEquiv.setText("Pushups (reps): " + Math.floor(calories/100*PUSHUP));
                situpEquiv.setText("Situps (reps): " + Math.floor(calories/100*SITUP));
                jumpingEquiv.setText("Jumping Jacks (min): " + Math.floor(calories/100*JUMPING));
                joggingEquiv.setText("Jogging (min): " + Math.floor(calories/100*JOGGING));
                Toast.makeText(CalorieConverter.this,
                        "Converted!" +
                                "\nSpinner 1 : " + String.valueOf(spinner1.getSelectedItem()) +
                                "\nCalorie Count : " + calories,
                        Toast.LENGTH_SHORT).show();
            }

        });
    }

    /**
     * ATTENTION: This was auto-generated to implement the App Indexing API.
     * See https://g.co/AppIndexing/AndroidStudio for more information.
     */
    public Action getIndexApiAction() {
        Thing object = new Thing.Builder()
                .setName("CalorieConverter Page") // TODO: Define a title for the content shown.
                // TODO: Make sure this auto-generated URL is correct.
                .setUrl(Uri.parse("http://[ENTER-YOUR-URL-HERE]"))
                .build();
        return new Action.Builder(Action.TYPE_VIEW)
                .setObject(object)
                .setActionStatus(Action.STATUS_TYPE_COMPLETED)
                .build();
    }

    @Override
    public void onStart() {
        super.onStart();

        // ATTENTION: This was auto-generated to implement the App Indexing API.
        // See https://g.co/AppIndexing/AndroidStudio for more information.
        client.connect();
        AppIndex.AppIndexApi.start(client, getIndexApiAction());
    }

    @Override
    public void onStop() {
        super.onStop();

        // ATTENTION: This was auto-generated to implement the App Indexing API.
        // See https://g.co/AppIndexing/AndroidStudio for more information.
        AppIndex.AppIndexApi.end(client, getIndexApiAction());
        client.disconnect();
    }
}