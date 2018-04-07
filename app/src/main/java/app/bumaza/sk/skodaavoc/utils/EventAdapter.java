package app.bumaza.sk.skodaavoc.utils;

import android.content.Context;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;

import java.util.ArrayList;

/**
 * Created by Budy on 6.4.18.
 */

public class EventAdapter extends ArrayAdapter<Event> implements View.OnClickListener{

    public ArrayList<Event> events;
    private Context context;

    public EventAdapter(Context context, ArrayList<Event> events) {
        super(context, android.R.layout.simple_list_item_1, events);
        this.events = events;
        this.context = context;
    }

    @Override
    public int getCount() {
        return events.size();
    }

    @Override
    public Event getItem(int i) {
        return events.get(i);
    }

    @Override
    public long getItemId(int i) {
        return i;
    }

    @Override
    public View getView(int i, View view, ViewGroup viewGroup) {

        return view;
    }

    @Override
    public void onClick(View view) {

    }
}
