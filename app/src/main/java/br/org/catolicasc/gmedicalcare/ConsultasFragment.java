package br.org.catolicasc.gmedicalcare;

import android.content.ContentResolver;
import android.database.Cursor;
import android.net.Uri;
import android.os.Bundle;
import android.provider.CalendarContract;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

public class ConsultasFragment extends Fragment {

    // Projection array. Creating indices for this array instead of doing
// dynamic lookups improves performance.
    public static final String[] EVENT_PROJECTION = new String[] {
            CalendarContract.Events._ID,                           // 0
            CalendarContract.Events.TITLE,                         // 1
            CalendarContract.Events.DTSTART,                       // 2
            CalendarContract.Events.DTEND                          // 3

    };

    // The indices for the projection array above.
    private static final int PROJECTION_ID_INDEX = 0;
    private static final int PROJECTION_TITLE = 1;
    private static final int PROJECTION_START = 2;
    private static final int PROJECTION_END = 3;


    private TextView texto;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.consultas_fragment, container, false);

        texto = view.findViewById(R.id.textoTESTE);
        texto.setText("Aqui vai uma lista no futuro");

        selectCalendar();
        return view;
    }

    public void selectCalendar(){
        // Run query
        Cursor cur = null;
        ContentResolver cr = getActivity().getApplicationContext().getContentResolver();
        Uri uri = CalendarContract.Events.CONTENT_URI;
        String selection = "((" + CalendarContract.Events.ACCOUNT_NAME + " = ?) AND ("
                + CalendarContract.Events.ACCOUNT_TYPE + " = ?) AND ("
                + CalendarContract.Events.OWNER_ACCOUNT + " = ?))";
        String[] selectionArgs = new String[] {"martinruediger2010@gmail.com", "com.google",
                "martinruediger2010@gmail.com"};
        // Submit the query and get a Cursor object back.
        cur = cr.query(uri, EVENT_PROJECTION, selection, selectionArgs, null);

        while (cur.moveToNext()) {
            System.out.println("Teste =>>" + cur.getString(PROJECTION_ID_INDEX) + " - " + cur.getString(PROJECTION_TITLE) + " - " + cur.getString(PROJECTION_START) + " - " + cur.getString(PROJECTION_END));
        }
    }
}
