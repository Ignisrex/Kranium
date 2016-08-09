package com.example.android.kranium;

import android.content.Context;
import android.content.Intent;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

public class MainActivity extends AppCompatActivity {
    public String keyword="";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        Button searchButton = (Button) findViewById(R.id.search_button);



        searchButton.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View view) {
                ConnectivityManager cm = (ConnectivityManager)MainActivity.this.getSystemService(Context.CONNECTIVITY_SERVICE);
                NetworkInfo activeNetwork = cm.getActiveNetworkInfo();
                final boolean isConnected = activeNetwork != null &&
                        activeNetwork.isConnectedOrConnecting();

                if(isConnected) {
                    EditText searchBar = (EditText) findViewById(R.id.search_bar);
                    keyword = searchBar.getText().toString();
                    if (TextUtils.isEmpty(keyword)) {
                        String message = getString(R.string.no_searchterm);
                        Toast.makeText(MainActivity.this, message, Toast.LENGTH_SHORT).show();
                    } else {
                        Intent bookIntent = new Intent(MainActivity.this, BookActivity.class);
                        bookIntent.putExtra("keyword", keyword);
                        startActivity(bookIntent);
                    }
                }else {
                    Toast.makeText(MainActivity.this, getString(R.string.no_wifi), Toast.LENGTH_SHORT).show();
                }
            }
        });
    }


}
