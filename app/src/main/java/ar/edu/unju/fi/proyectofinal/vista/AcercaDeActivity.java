package ar.edu.unju.fi.proyectofinal.vista;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import ar.edu.unju.fi.proyectofinal.R;

public class AcercaDeActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_acerca_de);
        //startActivity(new Intent(this, MenuAdmin.class));
        overridePendingTransition(R.anim.fade_in, R.anim.fade_out);
    }
}
