package com.example.intentimplicitoapi23;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.Manifest;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

public class MainActivity extends AppCompatActivity {

    private Button btnContactos, btnLlamar, btnDial, btnNavegador, btnMapa;
    private static final int LLAMADA_TELEFONO = 1;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
    }

    public void onClickBtn(View v) {
        Intent i;

        switch (v.getId()) {
            case R.id.btnContactos:
                i = new Intent(Intent.ACTION_VIEW, Uri.parse("content://contacts/people/"));
                if (i.resolveActivity(getPackageManager()) != null) {
                    startActivity(i);
                } else {
                    Toast.makeText(this, "IRRESOLUBLE", Toast.LENGTH_SHORT).show();
                }
                break;
            case R.id.btnDial:
                i = new Intent(Intent.ACTION_DIAL);
                startActivity(i);
                break;
            case R.id.btnLlamar:
                if (Build.VERSION.SDK_INT >= 23) {
                    if (checkSelfPermission(android.Manifest.permission.CALL_PHONE) == PackageManager.PERMISSION_GRANTED) {
                        i = new Intent(Intent.ACTION_CALL, Uri.parse("tel: (+34)986112233"));
                        startActivity(i);
                    } else {
                        requestPermissions(new String[]{android.Manifest.permission.CALL_PHONE}, LLAMADA_TELEFONO);
                    }
                } else {
                    i = new Intent(Intent.ACTION_CALL, Uri.parse("tel: (+34)986112233"));
                    startActivity(i);
                }
                break;
            case R.id.btnNavegador:
                i = new Intent(Intent.ACTION_VIEW, Uri.parse("http://www.edu.xunta.gal/portal"));
                startActivity(i);
                break;
            case R.id.btnMapa:
                i = new Intent(Intent.ACTION_VIEW, Uri.parse("geo: 42.25,-8.68"));
                startActivity(i);
                break;
        }
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        if (requestCode == LLAMADA_TELEFONO && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
            //Permiso concedido
            Intent i = new Intent(Intent.ACTION_CALL, Uri.parse("tel: (+34)986112233"));
            startActivity(i);
        } else {
            Toast.makeText(MainActivity.this, "No se ha podido realizar la llamada", Toast.LENGTH_SHORT).show();
        }
    }
}
