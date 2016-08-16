package com.example.android.kranium;

import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.PopupWindow;
import android.widget.RelativeLayout;
import android.widget.TextView;

import java.io.IOException;
import java.net.URL;
import java.util.ArrayList;
import java.util.concurrent.ExecutionException;

/**
 * Created by keane on 7/28/2016.
 */
public class BookActivity extends AppCompatActivity {
    public static final String LOG_TAG = BookActivity.class.getSimpleName();
    private LayoutInflater layoutInflater;
    private PopupWindow popupWindow;
    private RelativeLayout relativeLayout;
    public String keyword = "";
    private BookAdapter bookAdapter;


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
        relativeLayout = (RelativeLayout) findViewById(R.id.relative);
        bookAdapter = new BookAdapter(this,books);
        ListView bookListView = (ListView) findViewById(R.id.list);
        bookListView.setAdapter(bookAdapter);

        bookListView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {

                layoutInflater = (LayoutInflater) getApplicationContext().getSystemService(LAYOUT_INFLATER_SERVICE);
                ViewGroup container = (ViewGroup) layoutInflater.inflate(R.layout.popup_window,null);

                Book currentBook = bookAdapter.getItem(i);
                TextView nameView = (TextView) findViewById(R.id.name);
                nameView.setText(currentBook.getTitle());
                TextView authorView = (TextView) findViewById(R.id.author);
                authorView.setText(currentBook.getAuthor());
                TextView descView = (TextView) findViewById(R.id.desc);
                descView.setText(currentBook.getDescription());
                popupWindow = new PopupWindow(container,600,600,true);
                popupWindow.showAtLocation(relativeLayout, Gravity.CENTER,700,700);

                container.setOnTouchListener(new View.OnTouchListener() {
                    @Override
                    public boolean onTouch(View view, MotionEvent motionEvent) {
                        popupWindow.dismiss();
                        return true;
                    }
                });

            }
        });
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