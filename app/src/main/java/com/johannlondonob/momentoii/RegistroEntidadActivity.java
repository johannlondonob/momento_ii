package com.johannlondonob.momentoii;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.Toast;

import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.johannlondonob.momentoii.model.EntidadModel;

public class RegistroEntidadActivity extends AppCompatActivity {

    private EntidadModel model;
    private String id, nombre, direccion, telefono1, telefono2, email;
    private EditText editTextNombreEntidadRegistro, editTextDireccionEntidadRegistro, editTextTelefono1EntidadRegistro, editTextTelfono2EntidadRegistro, editTextEmailEntidadRegistro;
    private FloatingActionButton fabRegistrarEntidad;
    private ProgressBar progressBarRegistroEntidad;
    private FirebaseDatabase database = FirebaseDatabase.getInstance();
    private DatabaseReference reference = database.getReference("entidad");
    private boolean actualizar = false;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_registro_entidad);
        Bundle bundle = getIntent().getExtras();

        progressBarRegistroEntidad = findViewById(R.id.progressBarRegistroEntidad);
        fabRegistrarEntidad = findViewById(R.id.fabRegistrarEntidad);

        editTextNombreEntidadRegistro = findViewById(R.id.editTextNombreEntidadRegistro);
        editTextDireccionEntidadRegistro = findViewById(R.id.editTextDireccionEntidadRegistro);
        editTextTelefono1EntidadRegistro = findViewById(R.id.editTextTelefonoUnoRegistro);
        editTextTelfono2EntidadRegistro = findViewById(R.id.editTextTelefonoDosEntidadRegistro);
        editTextEmailEntidadRegistro = findViewById(R.id.editTextEmailEntidadRegistro);

        if (bundle != null) {
            RegistroEntidadActivity.this.setTitle("Actualización de datos");
            id = getIntent().getStringExtra("idEntidad");
            nombre = getIntent().getStringExtra("nombreEntidad");
            direccion = getIntent().getStringExtra("direccionEntidad");
            telefono1 = getIntent().getStringExtra("telefono1Entidad");
            telefono2 = getIntent().getStringExtra("telefono2Entidad");
            email = getIntent().getStringExtra("emailEntidad");

            editTextNombreEntidadRegistro.setText(nombre);
            editTextDireccionEntidadRegistro.setText(direccion);
            editTextTelefono1EntidadRegistro.setText(telefono1);
            editTextTelfono2EntidadRegistro.setText(telefono2);
            editTextEmailEntidadRegistro.setText(email);
            actualizar = true;
        }

        fabRegistrarEntidad.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(final View v) {
                nombre = editTextNombreEntidadRegistro.getText().toString();
                direccion = editTextDireccionEntidadRegistro.getText().toString();
                telefono1 = editTextTelefono1EntidadRegistro.getText().toString();
                telefono2 = editTextTelfono2EntidadRegistro.getText().toString();
                email = editTextEmailEntidadRegistro.getText().toString();

                if (nombre.equals("") || direccion.equals("") || email.equals("") || telefono1.equals("") || telefono2.equals("")) {
                    Toast.makeText(RegistroEntidadActivity.this, "Hay campos obligatorios sin llenar", Toast.LENGTH_SHORT).show();
                } else {
                    progressBarRegistroEntidad.setVisibility(View.VISIBLE);
                    if (!actualizar) {
                        id = reference.push().getKey();
                    }

                    if (id != null) {
                        model = new EntidadModel(id, nombre, direccion, telefono1, telefono2, email);
                        reference.child(id).setValue(model)
                                .addOnSuccessListener(new OnSuccessListener<Void>() {
                                    @Override
                                    public void onSuccess(Void aVoid) {
                                        LimpiarControles();
                                        progressBarRegistroEntidad.setVisibility(View.GONE);
                                        if (actualizar) {
                                            Toast.makeText(RegistroEntidadActivity.this, "Datos actualizados", Toast.LENGTH_LONG).show();
                                        } else {
                                            Toast.makeText(RegistroEntidadActivity.this, "Creación exitosa", Toast.LENGTH_LONG).show();
                                        }
                                        Intent intentMainActivity = new Intent(RegistroEntidadActivity.this, MainActivity.class);
                                        startActivity(intentMainActivity);
                                        finish();
                                    }
                                })
                                .addOnFailureListener(new OnFailureListener() {
                                    @Override
                                    public void onFailure(@NonNull Exception e) {
                                        progressBarRegistroEntidad.setVisibility(View.GONE);
                                        Toast.makeText(RegistroEntidadActivity.this, "No fue posible guardar el registro. Inténtalo más tarde", Toast.LENGTH_SHORT).show();
                                        onBackPressed();
                                    }
                                });
                    } else {
                        Toast.makeText(RegistroEntidadActivity.this, "No fue posible contactar con la base de datos", Toast.LENGTH_SHORT).show();
                        onBackPressed();
                    }
                }
            }
        });
    }

    public void LimpiarControles() {
        editTextNombreEntidadRegistro.setText("");
        editTextDireccionEntidadRegistro.setText("");
        editTextTelefono1EntidadRegistro.setText("");
        editTextTelfono2EntidadRegistro.setText("");
        editTextEmailEntidadRegistro.setText("");
    }
}
