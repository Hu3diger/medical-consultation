package br.org.catolicasc.gmedicalcare;

import android.Manifest;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.TabLayout;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Toast;

import com.google.android.gms.auth.api.signin.GoogleSignIn;
import com.google.android.gms.auth.api.signin.GoogleSignInAccount;

import br.org.catolicasc.gmedicalcare.AgendarConsulta;
import br.org.catolicasc.gmedicalcare.ConsultasFragment;
import br.org.catolicasc.gmedicalcare.PerfilFragment;
import br.org.catolicasc.gmedicalcare.R;
import br.org.catolicasc.gmedicalcare.ui.main.SectionsPagerAdapter;

public class MainActivity extends AppCompatActivity {

    private SectionsPagerAdapter mSectionsPageAdapter;
    private ViewPager mViewPager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        checkPermission();
    }

    public void initializeApp() {
        FloatingActionButton fab = findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(MainActivity.this, AgendarConsulta.class));
            }
        });

        mSectionsPageAdapter = new SectionsPagerAdapter(getSupportFragmentManager());
        mViewPager = findViewById(R.id.view_pager);
        setupViewPager(mViewPager);

        TabLayout tabLayout = findViewById(R.id.tabs);
        tabLayout.setupWithViewPager(mViewPager);
    }

    private void setupViewPager(ViewPager viewPager) {
        SectionsPagerAdapter adapter = new SectionsPagerAdapter(getSupportFragmentManager());
        adapter.addFragment(new ConsultasFragment(), "Consultas");
        adapter.addFragment(new PerfilFragment(), "Perfil");
        viewPager.setAdapter(adapter);
    }

    public GoogleSignInAccount getUserData() {
        return GoogleSignIn.getLastSignedInAccount(this);
    }

    public void checkPermission() {
        if (ContextCompat.checkSelfPermission(this,
                Manifest.permission.READ_CALENDAR)
                != PackageManager.PERMISSION_GRANTED) {

            ActivityCompat.requestPermissions(MainActivity.this,
                    new String[]{Manifest.permission.READ_CALENDAR, Manifest.permission.WRITE_CALENDAR},
                    1);
        } else {
            initializeApp();
        }

    }

    @Override
    public void onRequestPermissionsResult(int requestCode,
                                           String permissions[], int[] grantResults) {
        switch (requestCode) {
            case 1: {
                if (grantResults.length > 0
                        && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                    initializeApp();
                } else {
                    Toast.makeText(MainActivity.this, "Permission denied to read your External storage", Toast.LENGTH_SHORT).show();
                }
                return;
            }
        }
    }
}