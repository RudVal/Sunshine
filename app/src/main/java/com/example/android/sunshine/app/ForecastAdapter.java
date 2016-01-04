package com.example.android.sunshine.app;

/**
 * Created by Valeri on 30.11.2015.
 */
 import android.content.Context;
        import android.database.Cursor;
        import android.support.v4.widget.CursorAdapter;
        import android.view.LayoutInflater;
        import android.view.View;
        import android.view.ViewGroup;
 import android.widget.ImageView;
 import android.widget.TextView;

        import com.example.android.sunshine.app.data.WeatherContract;

/**
 * {@link ForecastAdapter} exposes a list of weather forecasts
 * from a {@link android.database.Cursor} to a {@link android.widget.ListView}.
 */
public class ForecastAdapter extends CursorAdapter {
    public ForecastAdapter(Context context, Cursor c, int flags) {
        super(context, c, flags);
    }

    /*
        Remember that these views are reused as needed.
     */
    @Override
    public View newView(Context context, Cursor cursor, ViewGroup parent) {
        View view = LayoutInflater.from(context).inflate(R.layout.list_item_forecast, parent, false);

        return view;
    }

    /*
        This is where we fill-in the views with the contents of the cursor.
     */
    @Override
    public void bindView(View view, Context context, Cursor cursor) {
        // our view is pretty simple here --- just a text view
        // we'll keep the UI functional with a simple (and slow!) binding.

        // Read weather icon ID from cursor
        int weatherId = cursor.getInt(ForecastFragment.COL_WEATHER_ID);
        // Use placeholder image for now
        ImageView iconView = (ImageView) view.findViewById(R.id.list_item_icon);
        iconView.setImageResource(R.drawable.ic_launcher);

        //  Read date from cursor
        Long dateLong = cursor.getLong(ForecastFragment.COL_WEATHER_DATE);
        TextView dateView = (TextView)view.findViewById(R.id.list_item_date_textview);
        dateView.setText(Utility.getFriendlyDayString(context, dateLong));

        //  Read weather forecast from cursor
        String weatherDescrStr = cursor.getString(ForecastFragment.COL_WEATHER_DESC);
        TextView weatherView = (TextView)view.findViewById(R.id.list_item_forecast_textview);
        weatherView.setText(weatherDescrStr);

        // Read user preference for metric or imperial temperature units
        boolean isMetric = Utility.isMetric(context);

        // Read high temperature from cursor
        double high = cursor.getDouble(ForecastFragment.COL_WEATHER_MAX_TEMP);
        TextView highView = (TextView) view.findViewById(R.id.list_item_high_textview);
        highView.setText(Utility.formatTemperature(high, isMetric));

        //  Read low temperature from cursor
        double low = cursor.getDouble(ForecastFragment.COL_WEATHER_MIN_TEMP);
        TextView lowView = (TextView)view.findViewById(R.id.list_item_low_textview);
        lowView.setText(Utility.formatTemperature(low, isMetric));

    }
}