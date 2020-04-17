package com.johannlondonob.momentoii;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.CompoundButton;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.Switch;
import android.widget.Toast;

import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.android.material.snackbar.Snackbar;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.johannlondonob.momentoii.model.AccesoRemotoModel;

public class RegistroAccesoRemotoActivity extends AppCompatActivity {

    private ProgressBar progressBar;
    private FloatingActionButton fabRegistrarAccesoRemoto;
    private EditText editTextNombreEquipo, editTextIpPublica, editTextIpPrivada;
    String nombreEntidad, nombreEquipo, ipPublica, ipPrivada;
    private String idEntidad, idAccesoRemoto;
    private AccesoRemotoModel model;
    private FirebaseDatabase database = FirebaseDatabase.getInstance();
    private DatabaseReference reference = database.getReference("accesos_remotos/" + idEntidad + "/" + idAccesoRemoto + "/datos_basicos");
    private boolean actualizar = false;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_registro_acceso_remoto);
        progressBar = findViewById(R.id.progressBarRegistroAccesoRemoto);
        fabRegistrarAccesoRemoto = findViewById(R.id.fabRegistrarAccesoRemoto);
        editTextNombreEquipo = findViewById(R.id.editTextNombreEquipoAccesoRemoto);
        editTextIpPublica = findViewById(R.id.editTextIpPublicaAccesoRemoto);
        editTextIpPrivada = findViewById(R.id.editTextIpPrivadaAccesoRemoto);
        Bundle bundle = getIntent().getExtras();

        if (bundle != null) {
            idEntidad = getIntent().getStringExtra("idEntidad");
            idAccesoRemoto = getIntent().getStringExtra("idAccesoRemoto");
            nombreEntidad = getIntent().getStringExtra("nombreEntidad");
            RegistroAccesoRemotoActivity.this.setTitle("Registro en:"+ nombreEntidad);

            if (idAccesoRemoto != null && !idAccesoRemoto.equals("") ) {
                nombreEntidad = getIntent().getStringExtra("nombreEntidad");
                nombreEquipo = getIntent().getStringExtra("nombreEquipo");
                ipPublica = getIntent().getStringExtra("ipPublica");
                ipPrivada = getIntent().getStringExtra("ipPrivada");

                editTextNombreEquipo.setText(nombreEquipo);
                editTextIpPublica.setText(ipPublica);
                editTextIpPrivada.setText(ipPrivada);
                actualizar = (idAccesoRemoto != null && !idAccesoRemoto.equals(""));
            }
        }

        RegistroAccesoRemotoActivity.this.setTitle("Nuevo remoto en " + nombreEntidad);

        fabRegistrarAccesoRemoto.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                nombreEquipo = editTextNombreEquipo.getText().toString();
                ipPublica = editTextIpPublica.getText().toString();
                ipPrivada = editTextIpPrivada.getText().toString();

                if (ipPrivada.equals("")) {
                    ipPrivada = "Sin asignar";
                }

                if (nombreEquipo.equals("") || ipPublica.equals("")) {
                    Toast.makeText(RegistroAccesoRemotoActivity.this, "Hay campos obligatorios sin llenar", Toast.LENGTH_SHORT).show();
                } else {
                    progressBar.setVisibility(View.VISIBLE);
                    if (!actualizar) {
                        idAccesoRemoto = reference.push().getKey();
                    }

                    if (idAccesoRemoto != null) {
                        model = new AccesoRemotoModel(idAccesoRemoto, idEntidad, nombreEquipo, ipPublica, ipPrivada);
                        reference = database.getReference("accesos_remotos/" + idEntidad + "/" + idAccesoRemoto + "/datos_basicos");
                        reference.child(idAccesoRemoto).setValue(model)
                                .addOnSuccessListener(new OnSuccessListener<Void>() {
                                    @Override
                                    public void onSuccess(Void aVoid) {
                                        if (actualizar) {
                                            Toast.makeText(RegistroAccesoRemotoActivity.this, "Datos actualizados", Toast.LENGTH_LONG).show();
                                        } else {
                                            Toast.makeText(RegistroAccesoRemotoActivity.this, "Creación exitosa", Toast.LENGTH_LONG).show();
                                        }
                                        Intent detalleEntidad = new Intent(RegistroAccesoRemotoActivity.this, AccesoRemotoDetalleActivity.class);
                                        detalleEntidad.putExtra("idEntidad", idEntidad);
                                        detalleEntidad.putExtra("nombreEntidad", nombreEntidad);
                                        detalleEntidad.putExtra("idAccesoRemoto", idAccesoRemoto);
                                        startActivity(detalleEntidad);
                                        finish();
                                    }
                                })
                                .addOnFailureListener(new OnFailureListener() {
                                    @Override
                                    public void onFailure(@NonNull Exception e) {
                                        Toast.makeText(RegistroAccesoRemotoActivity.this, "Error al guardar", Toast.LENGTH_LONG).show();
                                        onBackPressed();
                                    }
                                });
                    } else {
                        Toast.makeText(RegistroAccesoRemotoActivity.this, "No se puede crear el registro", Toast.LENGTH_LONG).show();
                        onBackPressed();
                    }
                }
            }
        });
    }
}
