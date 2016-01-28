package com.example.android.sunshine.app;

import android.content.Intent;
import android.content.SharedPreferences;
import android.net.Uri;
import android.preference.PreferenceManager;
import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import com.example.android.sunshine.app.ForecastFragment;

import java.net.URI;

public class MainActivity extends ActionBarActivity {
//    public class MainActivity extends AppCompatActivity {

    private final String LOG_TAG = MainActivity.class.getSimpleName();
    private final  String FORECASTFRAGMENT_TAG = "FFTAG";
    private static final String DETAILFRAGMENT_TAG = "DFTAG";

    private boolean mTwoPane;
    private String mLocation;

    @Override
    // The activity is being created.
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mLocation = Utility.getPreferredLocation(this);
        Log.d(LOG_TAG, "onCreate called");
        setContentView(R.layout.activity_main);

        if (findViewById(R.id.weather_detail_container) != null) {
            // The detail container view will be present only in the large-screen layouts
            // (res/layout-sw600dp). If this view is present, then the activity should be
            // in two-pane mode.
            mTwoPane = true;
            // In two-pane mode, show the detail view in this activity by
            // adding or replacing the detail fragment using a
            // fragment transaction.
            if (savedInstanceState == null) {
                getSupportFragmentManager().beginTransaction()
                        .replace(R.id.weather_detail_container, new DetailFragment(), DETAILFRAGMENT_TAG)
                        .commit();
            }
        } else {
            mTwoPane = false;
        }
    }

    @Override
    // The activity is about to become visible.
    protected void onStart() {
        Log.d(LOG_TAG, "onStart called");
        super.onStart();

    }

    @Override
    // The activity has become visible (it is now "resumed").
    protected void onResume() {
        Log.d(LOG_TAG, "MainActivity.onResume called");
        super.onResume();
        String location = Utility.getPreferredLocation(this);
        if(location != null && !location.equals( mLocation) ) {
            ForecastFragment ff = (ForecastFragment)getSupportFragmentManager().
                    findFragmentById(R.id.fragment_forecast);
            if(ff != null) ff.onLocationChanged();
            mLocation = location;
        }
    }

    @Override
    // Another activity is taking focus (this activity is about to be "paused").
    protected void onPause() {
        Log.d(LOG_TAG, "onPause called");
        super.onPause();

    }

    @Override
    // The activity is about to be destroyed.
    protected void onDestroy() {
        Log.d(LOG_TAG, "onDestroy called");
        super.onDestroy();

    }

    @Override
    // The activity is no longer visible (it is now "stopped")
    protected void onStop() {
        Log.d(LOG_TAG, "onStop called");
        super.onStop();

    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();
        if (id == R.id.action_settings) {
            startActivity(new Intent(this, SettingsActivity.class));
            return true;
        }

        if(id==R.id.action_map) {
            openPreferredLocationInMap();
            return true;
        }
        return super.onOptionsItemSelected(item);

    }

    private void openPreferredLocationInMap() {
        SharedPreferences sharedPrefs =
                PreferenceManager.getDefaultSharedPreferences(this);
        String location = sharedPrefs.getString(
                getString(R.string.pref_location_key),
                getString(R.string.pref_location_default));

        Uri geoLocation = Uri.parse("geo:0,0?").buildUpon()
                .appendQueryParameter("q", location).build();
        Intent intent = new Intent(Intent.ACTION_VIEW);
        intent.setData(geoLocation);
        if (intent.resolveActivity(getPackageManager()) != null) {
            startActivity(intent);
        } else {
            Log.d(LOG_TAG, "Couldn't call " + location + ", no receiving apps installed!");
        }
    }

}
