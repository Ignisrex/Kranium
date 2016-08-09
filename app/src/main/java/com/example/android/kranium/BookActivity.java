package com.example.android.kranium;

import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.widget.ListView;

import java.io.IOException;
import java.net.URL;
import java.util.ArrayList;
import java.util.concurrent.ExecutionException;

/**
 * Created by keane on 7/28/2016.
 */
public class BookActivity extends AppCompatActivity {
    public static final String LOG_TAG = BookActivity.class.getSimpleName();
    private static final String API_KEY = "AIzaSyCignJpPRtTxUaSEVDgZuPFAgq5kkX2PtY";
    public String keyword = "";


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.book_activity);

        Bundle extras = getIntent().getExtras();
        if (extras != null) {
            keyword = extras.getString("keyword");
        }

        BookAsyncTask task = new BookAsyncTask();

        ArrayList<Book> books = null;
        try {
            books = task.execute().get();
        } catch (InterruptedException e) {
            Log.e(LOG_TAG,"Interrupted Exception Error");
        } catch (ExecutionException e) {
            Log.e(LOG_TAG,"Execution Exception Error");
        }

        BookAdapter bookAdapter = new BookAdapter(this,books);
        ListView bookListView = (ListView) findViewById(R.id.list);
        bookListView.setAdapter(bookAdapter);
    }
    private class BookAsyncTask extends AsyncTask<URL,Void,ArrayList<Book>> {




        @Override
        protected ArrayList<Book> doInBackground(URL... urls) {

            URL url = QueryUtils.createUrl(keyword);
            String jsonResponse ="";
            try{
                jsonResponse = QueryUtils.makeHttpRequest(url);
            }catch (IOException e){
                Log.e(LOG_TAG,"Error with making Http request");
            }
            ArrayList<Book> books = QueryUtils.extractBooks(jsonResponse);
            return books;
        }
    }


}