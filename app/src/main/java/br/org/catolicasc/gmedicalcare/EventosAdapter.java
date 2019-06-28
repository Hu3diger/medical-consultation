package br.org.catolicasc.gmedicalcare;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import java.util.ArrayList;

public class EventosAdapter extends ArrayAdapter<Evento> {

    public EventosAdapter(Context context, ArrayList<Evento> events) {
        super(context, 0, events);
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        Evento event = getItem(position);

        if (convertView == null) {
            convertView = LayoutInflater.from(getContext()).inflate(R.layout.list_consultas, parent, false);
        }

        TextView start = (TextView) convertView.findViewById(R.id.lvStart);
        TextView title = (TextView) convertView.findViewById(R.id.lvTitle);

        start.setText(event.getStart());
        title.setText(event.getTitle());

        return convertView;
    }
}
