package br.org.catolicasc.gmedicalcare;

import android.Manifest;
import android.app.Activity;
import android.content.ContentResolver;
import android.content.pm.PackageManager;
import android.database.Cursor;
import android.net.Uri;
import android.os.Bundle;
import android.provider.CalendarContract;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.design.widget.Snackbar;
import android.support.v4.app.ActivityCompat;
import android.support.v4.app.Fragment;
import android.support.v4.content.ContextCompat;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.auth.api.signin.GoogleSignInAccount;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;

public class ConsultasFragment extends Fragment {

    // Projection array. Creating indices for this array instead of doing
// dynamic lookups improves performance.
    public static final String[] EVENT_PROJECTION = new String[]{
            CalendarContract.Events.TITLE,
            CalendarContract.Events.DTSTART,
            CalendarContract.Events.DTEND

    };

    private static final int PROJECTION_TITLE = 0;
    private static final int PROJECTION_START = 1;
    private static final int PROJECTION_END = 2;


    private ListView listView;
    private ArrayList<Evento> eventos;
    private GoogleSignInAccount user;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.consultas_fragment, container, false);
        eventos = new ArrayList<Evento>();
        user = ((MainActivity) getActivity()).getUserData();
        getConsultations();

        EventosAdapter adapter = new EventosAdapter(this.getContext(), eventos);
        listView = view.findViewById(R.id.listView);
        listView.setAdapter(adapter);
        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Evento e = eventos.get(position);
                Snackbar.make(view, "Começa as: " + e.getStart() + "\nTermina em: " + e.getEnd(), Snackbar.LENGTH_LONG)
                        .setAction("Action", null).show();
            }
        });

        return view;
    }

    public void getConsultations() {

        Cursor cur = null;
        ContentResolver cr = getActivity().getApplicationContext().getContentResolver();
        Uri uri = CalendarContract.Events.CONTENT_URI;
        String selection = "((" + CalendarContract.Events.ACCOUNT_NAME + " = ?) AND ("
                + CalendarContract.Events.ACCOUNT_TYPE + " = ?) AND ("
                + CalendarContract.Events.OWNER_ACCOUNT + " = ?))";
        String[] selectionArgs = new String[]{user.getEmail(), "com.google", user.getEmail()};
        cur = cr.query(uri, EVENT_PROJECTION, selection, selectionArgs, null);

        while (cur.moveToNext()) {
            Evento e = new Evento();
            String rawStart = cur.getString(PROJECTION_START);
            String rawEnd = cur.getString(PROJECTION_END);

            e.setTitle(cur.getString(PROJECTION_TITLE));
            e.setStart(getDate(Long.parseLong(rawStart)));
            e.setEnd(getDate(Long.parseLong(rawEnd)));
            eventos.add(e);
        }
    }


    public static String getDate(long timestampInMilliSeconds) {
        Date date = new Date();
        date.setTime(timestampInMilliSeconds);
        String formattedDate = new SimpleDateFormat("dd/MM/yy hh:mm").format(date);
        return formattedDate;
    }


}
