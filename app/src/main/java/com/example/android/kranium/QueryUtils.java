package com.example.android.kranium;

import android.text.TextUtils;
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

/**
 * Created by keane on 7/29/2016.
 */
public final class QueryUtils {
    public static final String LOG_TAG = BookActivity.class.getSimpleName();
    public QueryUtils(){
    }

    public static String readFromStream(InputStream inputStream) throws IOException {
        StringBuilder output = new StringBuilder();
        if (inputStream != null) {
            InputStreamReader inputStreamReader = new InputStreamReader(inputStream, Charset.forName("UTF-8"));
            BufferedReader reader = new BufferedReader(inputStreamReader);
            String line = reader.readLine();
            while (line != null) {
                output.append(line);
                line = reader.readLine();
            }
        }
        return output.toString();
    }


    public static String makeHttpRequest(URL url) throws IOException {
        String jsonResponse = "";
        if (url ==null){
            return jsonResponse;
        }
        HttpURLConnection urlConnection = null;
        InputStream inputStream = null;
        try {
            urlConnection = (HttpURLConnection) url.openConnection();
            urlConnection.setRequestMethod("GET");
            urlConnection.setReadTimeout(10000 );
            urlConnection.setConnectTimeout(15000 );
            urlConnection.connect();
            if (urlConnection.getResponseCode() == 200){
                inputStream = urlConnection.getInputStream();
                jsonResponse = readFromStream(inputStream);
            }
        } catch (IOException e) {
            Log.e(LOG_TAG,"I/O error while trying to read from server");
        } finally {
            if (urlConnection != null) {
                urlConnection.disconnect();
            }
            if (inputStream != null) {
                inputStream.close();
            }
        }
        return jsonResponse;
    }

    public static URL createUrl(String keyword) {
        String stringUrl = "https://www.googleapis.com/books/v1/volumes?q=" + keyword + "&maxResults=10";
        URL url = null;
        try {
            url = new URL(stringUrl);
        } catch (MalformedURLException exception) {
            Log.e(LOG_TAG, "Error with creating URL", exception);
            return null;
        }
        return url;
    }

    public static ArrayList<Book> extractBooks(String jsonResponse){
        if (TextUtils.isEmpty(jsonResponse)){
            return null;
        }
        ArrayList<Book> books = new ArrayList<>();

        try{
            JSONObject baseJsonResponse = new JSONObject(jsonResponse);
            JSONArray bookArray = baseJsonResponse.getJSONArray("items");
            for (int i = 0; i<bookArray.length(); i++){
                JSONObject currentBook = bookArray.getJSONObject(i);
                JSONObject volumeInfo = currentBook.getJSONObject("volumeInfo");
                String name = volumeInfo.getString("title");
                JSONArray authorArray = volumeInfo.getJSONArray("authors");
                String author = "";
                for (int n =0; n<authorArray.length(); n++){
                    if (n>0){
                        author = author + ", ";
                    }
                    author = author +authorArray.getString(n);

                }
                books.add(new Book(name,author));

            }

        }catch (JSONException e) {

            Log.e("QueryUtils", "Problem parsing the earthquake JSON results", e);
        }
        return books;
    }
}
