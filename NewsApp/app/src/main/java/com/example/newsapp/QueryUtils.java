package com.example.newsapp;

import android.util.Log;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.nio.charset.Charset;
import java.util.ArrayList;
import java.util.List;

public class QueryUtils {

    private QueryUtils() {
    }


    public static List<News> fetchNewsData(String requestUrl) {

        URL url = createUrl(requestUrl);
        List<News> news = null;

        try {
            String jsonResponse = makeHttpRequest(url);
            news = extractFeaturesFromJson(jsonResponse);

        } catch (IOException e) {
            e.printStackTrace();
        }

        return news;
    }

    public static ArrayList<News> extractFeaturesFromJson(String jsonResponse) {

        if(jsonResponse == null) {
            return null;
        }

        ArrayList<News> news = new ArrayList<>();

        try {
            JSONObject object = new JSONObject(jsonResponse);
            JSONObject newsJson = object.getJSONObject("response");
            JSONArray results = newsJson.getJSONArray("results");

            for(int i = 0; i < results.length(); i++) {
                JSONObject properties = results.getJSONObject(i);
                JSONArray tags = properties.getJSONArray("tags");
                JSONObject name = tags.getJSONObject(0);

                String Title = properties.getString("webTitle");
                String Section = properties.getString("sectionName");
                String url = properties.getString("webUrl");
                String date = properties.getString("webPublicationDate");
                String Author_name = name.getString("firstName") + " " + name.getString("lastName");

                News news1 = new News(Title, Section, date, url , Author_name);

                news.add(news1);
            }

        } catch (JSONException e) {
            Log.e("QueryUtils", "Problem parsing the News JSON results", e);
        }

        return news;
    }

    private static URL createUrl(String stringUrl) {

        URL url = null;

        if(stringUrl == null) {
            return url;
        }
        try {
            url = new URL(stringUrl);
        } catch (MalformedURLException e) {
            e.printStackTrace();
        }
        return url;
    }

    private static String makeHttpRequest(URL url) throws IOException {
        String jsonResponse = "";
        HttpURLConnection urlConnection = null;
        InputStream inputStream = null;


        if(url == null) {
            return jsonResponse;
        }

        try {
            urlConnection = (HttpURLConnection) url.openConnection();
            urlConnection.setRequestMethod("GET");
            urlConnection.connect();

            if(urlConnection.getResponseCode() == 200) {
                inputStream = urlConnection.getInputStream();
                jsonResponse = readFromStream(inputStream);
            }
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            if(urlConnection != null) {
                urlConnection.disconnect();
            }
            if(inputStream != null) {
                inputStream.close();
            }
        }

        return jsonResponse;
    }

    private static String readFromStream (InputStream inputStream) {
        InputStreamReader streamReader = null;
        BufferedReader reader = null;
        StringBuilder result = new StringBuilder();

        if(inputStream == null) {
            return null;
        }

        streamReader = new InputStreamReader(inputStream, Charset.forName("UTF-8"));
        reader = new BufferedReader(streamReader);
        try {
            String line = reader.readLine();
            while (line != null) {

                result.append(line);
                line = reader.readLine();
            }
        } catch (IOException e) {
            e.printStackTrace();
        }

        return result.toString();
    }


}
