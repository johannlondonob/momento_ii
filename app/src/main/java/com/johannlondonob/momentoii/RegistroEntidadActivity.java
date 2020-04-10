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
    private EditText editTextNombre, editTextDireccion, editTextTelefono1, editTextTelfono2, editTextEmail;
    private FloatingActionButton fabRegistrarEntidad;
    private ProgressBar progressBar;
    private FirebaseDatabase database = FirebaseDatabase.getInstance();
    private DatabaseReference reference = database.getReference("entidad");
    private boolean actualizar = false;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_registro_entidad);
        Bundle bundle = getIntent().getExtras();

        progressBar = findViewById(R.id.progressBarRegistroEntidad);
        fabRegistrarEntidad = findViewById(R.id.fabRegistrarEntidad);

        editTextNombre = findViewById(R.id.editTextNombreEntidadRegistro);
        editTextDireccion = findViewById(R.id.editTextDireccionEntidadRegistro);
        editTextTelefono1 = findViewById(R.id.editTextTelefonoUnoRegistro);
        editTextTelfono2 = findViewById(R.id.editTextTelefonoDosEntidadRegistro);
        editTextEmail = findViewById(R.id.editTextEmailEntidadRegistro);

        if (bundle != null) {
            RegistroEntidadActivity.this.setTitle("Actualización de datos");
            id = getIntent().getStringExtra("idEntidad");
            nombre = getIntent().getStringExtra("nombreEntidad");
            direccion = getIntent().getStringExtra("direccionEntidad");
            telefono1 = getIntent().getStringExtra("telefono1Entidad");
            telefono2 = getIntent().getStringExtra("telefono2Entidad");
            email = getIntent().getStringExtra("emailEntidad");

            editTextNombre.setText(nombre);
            editTextDireccion.setText(direccion);
            editTextTelefono1.setText(telefono1);
            editTextTelfono2.setText(telefono2);
            editTextEmail.setText(email);
            actualizar = true;
        }

        fabRegistrarEntidad.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(final View v) {
                nombre = editTextNombre.getText().toString();
                direccion = editTextDireccion.getText().toString();
                telefono1 = editTextTelefono1.getText().toString();
                telefono2 = editTextTelfono2.getText().toString();
                email = editTextEmail.getText().toString();

                if (nombre.equals("") || direccion.equals("") || email.equals("") || telefono1.equals("") || telefono2.equals("")) {
                    Toast.makeText(RegistroEntidadActivity.this, "Hay campos obligatorios sin llenar", Toast.LENGTH_SHORT).show();
                } else {
                    progressBar.setVisibility(View.VISIBLE);
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
                                        progressBar.setVisibility(View.GONE);
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
                                        progressBar.setVisibility(View.GONE);
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
        editTextNombre.setText("");
        editTextDireccion.setText("");
        editTextTelefono1.setText("");
        editTextTelfono2.setText("");
        editTextEmail.setText("");
    }
}
