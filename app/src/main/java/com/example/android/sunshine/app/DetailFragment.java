package com.example.android.sunshine.app;

import android.content.Intent;
import android.database.Cursor;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.LoaderManager;
import android.support.v4.content.CursorLoader;
import android.support.v4.content.Loader;
import android.support.v4.view.MenuItemCompat;
import android.support.v7.widget.ShareActionProvider;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.android.sunshine.app.data.WeatherContract;

/**
 * Created by Valeri on 16.01.2016.
 */

public class DetailFragment extends Fragment implements LoaderManager.LoaderCallbacks<Cursor> {

    private static final  String LOG_TAG = DetailFragment.class.getName();
    private  static final String FORECAST_SHARE_HASHTAG = " #SunshineApp";
    private String mForecastStr;
    private ShareActionProvider mShareActionProvider;

    private static final int DETAIL_LOADER = 0;

    private static final String[] FORECAST_COLUMNS = {
            WeatherContract.WeatherEntry.TABLE_NAME + "." + WeatherContract.WeatherEntry._ID,
            WeatherContract.WeatherEntry.COLUMN_DATE,
            WeatherContract.WeatherEntry.COLUMN_SHORT_DESC,
            WeatherContract.WeatherEntry.COLUMN_MAX_TEMP,
            WeatherContract.WeatherEntry.COLUMN_MIN_TEMP,
            WeatherContract.WeatherEntry.COLUMN_HUMIDITY,
            WeatherContract.WeatherEntry.COLUMN_PRESSURE,
            WeatherContract.WeatherEntry.COLUMN_WIND_SPEED,
            WeatherContract.WeatherEntry.COLUMN_DEGREES,
            WeatherContract.WeatherEntry.COLUMN_WEATHER_ID,
            WeatherContract.LocationEntry.COLUMN_LOCATION_SETTING
    };

    private static final int COL_WEATHER_ID = 0;
    private static final int COL_WEATHER_DATE = 1;
    private static final int COL_WEATHER_DESC =2;
    private static final int COL_WEATHER_MAX_TEMP = 3;
    private static final int COL_WEATHER_MIN_TEMP = 4;
    private static final int COL_WEATHER_HUMIDITY = 5;
    private static final int COL_WEATHER_PRESSURE = 6;
    private static final int COL_WEATHER_WIND_SPEED = 7;
    private static final int COL_WEATHER_DEGREES = 8;
    private static final int COL_WEATHER_CONDITION_ID = 9;

    private ImageView mIconView;
    private TextView mFriendlyDateView;
    private TextView mDateView;
    private TextView mDescriptionView;
    private TextView mHighTempView;
    private TextView mLowTempView;
    private TextView mHumidityView;
    private TextView mWindView;
    private TextView mPressureView;


    public DetailFragment() {
        setHasOptionsMenu(true);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        View rootView = inflater.inflate(R.layout.fragment_detail, container, false);
        mIconView = (ImageView) rootView.findViewById(R.id.detail_icon);
        mDateView = (TextView) rootView.findViewById(R.id.detail_date_textview);
        mFriendlyDateView = (TextView)rootView.findViewById(R.id.detail_day_textview);
        mDescriptionView = (TextView)rootView.findViewById(R.id.detail_forecast_textview);
        mHighTempView = (TextView)rootView.findViewById(R.id.detail_high_textview);
        mLowTempView = (TextView)rootView.findViewById(R.id.detail_low_textview);
        mHumidityView = (TextView)rootView.findViewById(R.id.detail_humidity_textview);
        mWindView = (TextView)rootView.findViewById(R.id.detail_wind_textview);
        mPressureView = (TextView)rootView.findViewById(R.id.detail_pressure_textview);

        return rootView;
    }

    @Override
    public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {
        inflater.inflate(R.menu.detailfragment, menu);

        // Retrieve the share menu item
        MenuItem menuItem = menu.findItem(R.id.action_share);
        // Get the provider and hold onto it to set/change the share intent
        mShareActionProvider =
                (ShareActionProvider) MenuItemCompat.getActionProvider(menuItem);

        // Attach an intent to this ShareActionProvider.
        if (mForecastStr != null) {
            mShareActionProvider.setShareIntent(createShareForecastIntent());
        } else {
            Log.d(LOG_TAG, "Share Action Provider is null?");
        }
    }


    private Intent createShareForecastIntent() {
        Intent shareIntent = new Intent(Intent.ACTION_SEND);
        shareIntent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_WHEN_TASK_RESET);
        shareIntent.setType("text/plain");
        shareIntent.putExtra(Intent.EXTRA_TEXT, mForecastStr + FORECAST_SHARE_HASHTAG);
        return shareIntent;
    }

    @Override
    public void onActivityCreated(Bundle savedInstanceState) {
        getLoaderManager().initLoader(DETAIL_LOADER, null, this);
        super.onActivityCreated(savedInstanceState);
    }


    @Override
    public Loader<Cursor> onCreateLoader(int i, Bundle bundle) {
        Log.v(LOG_TAG, "In DetailActivity.onCreation");
        Intent intent = getActivity().getIntent();
        if(intent==null) {
            return null;
        }
        return new CursorLoader(getActivity(),
                intent.getData(),
                FORECAST_COLUMNS,
                null,
                null,
                null);
    }
    @Override
    public  void  onLoadFinished(Loader<Cursor> loader, Cursor cursor) {
        Log.v(LOG_TAG, "In DetailActivity.onLoadFinished");
        if (cursor.getCount() == 0) {
            Log.e(LOG_TAG, "In DetailActivity.onLoadFinished NO DATA FOUND");
            return;
        }

        if(cursor != null && cursor.moveToFirst()) {
            // Read weather condition ID from cursor
        int weatherId = cursor.getInt(COL_WEATHER_CONDITION_ID);
            mIconView.setImageResource(R.drawable.ic_launcher);

            // read date from cursor and update views for day and date
            long date = cursor.getLong(COL_WEATHER_DATE);
            String friendlyDateText = Utility.getDayName(getActivity(), date);
            String dateText = Utility.getFormattedMonthDay(getActivity(), date);
            mFriendlyDateView.setText(friendlyDateText);
            mDateView.setText(dateText);

            // read description from cursor und update view
            String description = cursor.getString(COL_WEATHER_DESC);
            mDescriptionView.setText(description);

            // read high temperature from cursor and update view
            boolean isMetric = Utility.isMetric(getActivity());

            double highTemp = cursor.getDouble(COL_WEATHER_MAX_TEMP);
            String strHighTemp = Utility.formatTemperature(getActivity(), highTemp, isMetric);
            mHighTempView.setText(strHighTemp);

            // read low temperature and update view
            double lowTemp = cursor.getDouble(COL_WEATHER_MIN_TEMP);
            String strLowTemp = Utility.formatTemperature(getActivity(), lowTemp, isMetric);
            mLowTempView.setText(strLowTemp);

            // read humidity and update view
            float humid = cursor.getFloat(COL_WEATHER_HUMIDITY);
            mHumidityView.setText(getActivity().getString(R.string.format_humidity, humid));

            //read wind speed and direction
            float speed = cursor.getFloat(COL_WEATHER_WIND_SPEED);
            float direction = cursor.getFloat(COL_WEATHER_DEGREES);
            mWindView.setText(Utility.getFormattedWind(getActivity(), speed, direction));

            // read pressure
            float press = cursor.getFloat(COL_WEATHER_PRESSURE);
            mPressureView.setText(getActivity().getString(R.string.format_pressure, press));


            mForecastStr = String.format("%s - %s - %s/%s", dateText,
                    description, highTemp, lowTemp);

            // if onCreateOptionsMenu has already happened, we need to update the share intent
            if (mShareActionProvider != null) {
                mShareActionProvider.setShareIntent(createShareForecastIntent());
            }
        }else {
            Log.e(LOG_TAG, "In DetailActivity.onLoadFinished ERROR READ DATA");
            return;
        }
    }
    @Override
    public void onLoaderReset(Loader<Cursor> loader) {
//            mForecastAdapter.swapCursor(null);
    }

}