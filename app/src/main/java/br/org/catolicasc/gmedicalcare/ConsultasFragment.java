package br.org.catolicasc.gmedicalcare;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

public class ConsultasFragment extends Fragment {

    private TextView texto;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.consultas_fragment, container, false);

        texto = view.findViewById(R.id.textoTESTE);

        texto.setText("Aqui vai uma lista no futuro");
        return view;
    }
}
