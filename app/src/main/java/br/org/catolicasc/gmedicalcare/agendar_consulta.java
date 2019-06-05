package br.org.catolicasc.gmedicalcare;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.EditText;

public class agendar_consulta extends AppCompatActivity {

    private EditText dataConsulta;
    private EditText horarioConsulta;
    private EditText sintomas;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_agendar_consulta);


    }
}
