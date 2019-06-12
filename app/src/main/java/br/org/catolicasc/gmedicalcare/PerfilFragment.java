package br.org.catolicasc.gmedicalcare;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import com.google.android.gms.auth.api.signin.GoogleSignInAccount;

public class PerfilFragment extends Fragment {

    private TextView profileName, profileEmail;
    private ImageView profileImage;
    private Button signOut;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.perfil_fragment, container, false);

        MainActivity activity = (MainActivity) getActivity();
        GoogleSignInAccount account = activity.getUserData();


        profileName = view.findViewById(R.id.profile_text);
        profileEmail = view.findViewById(R.id.profile_email);
        profileImage = view.findViewById(R.id.profile_image);
        signOut = view.findViewById(R.id.sign_out);


        profileName.setText("Name: " + account.getDisplayName());
        profileEmail.setText("E-Mail: " + account.getEmail());
        profileImage.setImageURI(account.getPhotoUrl());

        return view;
    }

}
