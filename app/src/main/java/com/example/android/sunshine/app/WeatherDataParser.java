package com.example.android.sunshine.app;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

/**
 * Created by Valeri on 20.10.2015.
 */
public class WeatherDataParser {

//    String jsonStr = "{\"city\":{\"id\":2950159,\"name\":\"Berlin\",\"coord\":{\"lon\":13.41053,\"lat\":52.524368},\"country\":\"DE\",\"population\":0},\"cod\":\"200\",\"message\":0.0071,\"cnt\":7,\"list\":[{\"dt\":1445335200,\"temp\":{\"day\":10.78,\"min\":8.14,\"max\":12.01,\"night\":8.14,\"eve\":11.63,\"morn\":10.78},\"pressure\":1024.86,\"humidity\":100,\"weather\":[{\"id\":500,\"main\":\"Rain\",\"description\":\"light rain\",\"icon\":\"10d\"}],\"speed\":2.32,\"deg\":348,\"clouds\":80,\"rain\":0.31},{\"dt\":1445421600,\"temp\":{\"day\":10.68,\"min\":8.08,\"max\":11.6,\"night\":9.24,\"eve\":10.87,\"morn\":9.03},\"pressure\":1024.52,\"humidity\":100,\"weather\":[{\"id\":500,\"main\":\"Rain\",\"description\":\"light rain\",\"icon\":\"10d\"}],\"speed\":3.16,\"deg\":292,\"clouds\":36,\"rain\":0.32},{\"dt\":1445508000,\"temp\":{\"day\":9.66,\"min\":8.07,\"max\":12.72,\"night\":11.76,\"eve\":12.72,\"morn\":8.29},\"pressure\":1015.97,\"humidity\":92,\"weather\":[{\"id\":500,\"main\":\"Rain\",\"description\":\"light rain\",\"icon\":\"10d\"}],\"speed\":6.46,\"deg\":217,\"clouds\":80,\"rain\":1.27},{\"dt\":1445594400,\"temp\":{\"day\":12.26,\"min\":8.78,\"max\":14.13,\"night\":8.78,\"eve\":13.57,\"morn\":11.05},\"pressure\":1019.65,\"humidity\":80,\"weather\":[{\"id\":803,\"main\":\"Clouds\",\"description\":\"broken clouds\",\"icon\":\"04d\"}],\"speed\":8.62,\"deg\":288,\"clouds\":64},{\"dt\":1445680800,\"temp\":{\"day\":10.01,\"min\":7.13,\"max\":11.32,\"night\":8.77,\"eve\":11.32,\"morn\":7.13},\"pressure\":1022.9,\"humidity\":87,\"weather\":[{\"id\":500,\"main\":\"Rain\",\"description\":\"light rain\",\"icon\":\"10d\"}],\"speed\":4.56,\"deg\":194,\"clouds\":92},{\"dt\":1445767200,\"temp\":{\"day\":12.9,\"min\":8.9,\"max\":12.9,\"night\":11.12,\"eve\":11.8,\"morn\":8.9},\"pressure\":1020.81,\"humidity\":0,\"weather\":[{\"id\":502,\"main\":\"Rain\",\"description\":\"heavy intensity rain\",\"icon\":\"10d\"}],\"speed\":5.37,\"deg\":227,\"clouds\":51,\"rain\":15.22},{\"dt\":1445853600,\"temp\":{\"day\":11.13,\"min\":8.31,\"max\":11.13,\"night\":8.31,\"eve\":9.55,\"morn\":10.18},\"pressure\":1007.2,\"humidity\":0,\"weather\":[{\"id\":501,\"main\":\"Rain\",\"description\":\"moderate rain\",\"icon\":\"10d\"}],\"speed\":5.36,\"deg\":205,\"clouds\":92,\"rain\":8.95}]}\n";

    public static double getMaxTemperatureForDay(String weatherJsonStr, int dayInt)
        throws JSONException{

        JSONObject jsonObj = new JSONObject(weatherJsonStr);
        JSONArray jsonArr = jsonObj.getJSONArray("list");

        JSONObject jsonDayObj = jsonArr.getJSONObject(dayInt);
        JSONObject temperObject = jsonDayObj.getJSONObject("temp");
        double maxTemp = temperObject.getDouble("max");

        return maxTemp;
    }
}
