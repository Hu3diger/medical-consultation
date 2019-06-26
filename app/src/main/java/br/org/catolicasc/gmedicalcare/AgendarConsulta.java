package br.org.catolicasc.gmedicalcare;

import android.app.DatePickerDialog;
import android.content.Intent;
import android.os.Bundle;
import android.provider.CalendarContract;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.Spinner;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Locale;

public class AgendarConsulta extends AppCompatActivity {

    final Calendar myCalendar = Calendar.getInstance();
    private EditText sintomas, dataConsulta;
    private Spinner spinner;
    private Button btnSchedule, btnDate;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_agendar_consulta);
        btnSchedule = findViewById(R.id.btnSchedule);
        spinner = findViewById(R.id.spinner);
        sintomas = findViewById(R.id.sintomas);
        dataConsulta = findViewById(R.id.data_consulta);
        btnDate = findViewById(R.id.btnDate);
        dataConsulta.setEnabled(false);

        ArrayAdapter<CharSequence> adapter = ArrayAdapter.createFromResource(this,
                R.array.teste, android.R.layout.simple_spinner_item);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinner.setAdapter(adapter);


        final DatePickerDialog.OnDateSetListener date = new DatePickerDialog.OnDateSetListener() {

            @Override
            public void onDateSet(DatePicker view, int year, int monthOfYear,
                                  int dayOfMonth) {
                myCalendar.set(Calendar.YEAR, year);
                myCalendar.set(Calendar.MONTH, monthOfYear);
                myCalendar.set(Calendar.DAY_OF_MONTH, dayOfMonth);
                updateLabel();
            }

        };

        btnDate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                new DatePickerDialog(AgendarConsulta.this, date, myCalendar
                        .get(Calendar.YEAR), myCalendar.get(Calendar.MONTH),
                        myCalendar.get(Calendar.DAY_OF_MONTH)).show();
            }
        });

        btnSchedule.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                insertEvent();
            }
        });
    }

    private void updateLabel() {
        String myFormat = "dd/MM/yyyy"; //In which you need put here
        SimpleDateFormat sdf = new SimpleDateFormat(myFormat, new Locale("pt", "BR"));

        dataConsulta.setText(sdf.format(myCalendar.getTime()));
    }

    public void insertEvent() {
        String[] beginTimeSplitted = spinner.getSelectedItem().toString().split("\\:");
        int[] endTimeSplitted = new int[2];
        if (Integer.parseInt(beginTimeSplitted[1]) == 30) {
            endTimeSplitted[0] = Integer.parseInt(beginTimeSplitted[0]) + 1;
            endTimeSplitted[1] = 00;
        } else {
            endTimeSplitted[0] = Integer.parseInt(beginTimeSplitted[0]);
            endTimeSplitted[1] = 30;
        }


        Calendar beginTime = Calendar.getInstance();
        beginTime.set((myCalendar.getTime().getYear() + 1900), (myCalendar.getTime().getMonth()), myCalendar.getTime().getDate(), Integer.parseInt(beginTimeSplitted[0]), Integer.parseInt(beginTimeSplitted[1]));
        Calendar endTime = Calendar.getInstance();
        endTime.set((myCalendar.getTime().getYear() + 1900), (myCalendar.getTime().getMonth()), myCalendar.getTime().getDate(), endTimeSplitted[0], endTimeSplitted[1]);
        Intent intent = new Intent(Intent.ACTION_INSERT)
                .setData(CalendarContract.Events.CONTENT_URI)
                .putExtra(CalendarContract.EXTRA_EVENT_BEGIN_TIME, beginTime.getTimeInMillis())
                .putExtra(CalendarContract.EXTRA_EVENT_END_TIME, endTime.getTimeInMillis())
                .putExtra(CalendarContract.Events.TITLE, "Conulta Médica \n Dr. Luiz de Alencar Alface")
                .putExtra(CalendarContract.Events.DESCRIPTION, "Sintomas: " + sintomas.getText())
                .putExtra(CalendarContract.Events.EVENT_LOCATION, "Clínica Alface's - Rua José Teodoro Brasileiro, 2574, Sala 2")
                .putExtra(CalendarContract.Events.AVAILABILITY, CalendarContract.Events.AVAILABILITY_BUSY)
                .putExtra(Intent.EXTRA_EMAIL, "meegamc@gmail.com");

        startActivity(intent);

    }

}
