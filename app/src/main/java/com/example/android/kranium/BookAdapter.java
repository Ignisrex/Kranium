package com.example.android.kranium;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import java.util.List;

/**
 * Created by keane on 7/28/2016.
 */
public class BookAdapter extends ArrayAdapter<Book> {

    public BookAdapter(Context context, List<Book> book){

        super(context,0,book);
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {

        View listItemView = convertView;
        if(listItemView == null){
            listItemView= LayoutInflater.from(getContext()).inflate(R.layout.book_list_item,parent,false);
        }

        Book currentBook = getItem(position);

        TextView titleView = (TextView) listItemView.findViewById(R.id.title);
        titleView.setText(currentBook.getTitle());

        TextView authorView = (TextView) listItemView.findViewById(R.id.authors);
        authorView.setText(currentBook.getAuthor());

        return listItemView;
    }
}
