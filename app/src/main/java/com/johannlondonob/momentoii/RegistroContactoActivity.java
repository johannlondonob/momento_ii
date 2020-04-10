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
import com.johannlondonob.momentoii.model.ContactoModel;

public class RegistroContactoActivity extends AppCompatActivity {

    private EditText editTextNombres, editTextCargo, editTextTelefono1, editTextTelefono2;
    private String idEntidad, nombreEntidad, idContacto, nombresApellidos, cargo, telefono1, telefono2;
    private boolean actualizar = false;
    private FloatingActionButton fabGuardarContacto;
    private ProgressBar progressBar;
    private FirebaseDatabase database = FirebaseDatabase.getInstance();
    //private DatabaseReference reference = database.getReference("contactos");

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_registro_contacto);
        Bundle bundle = getIntent().getExtras();
        editTextNombres = findViewById(R.id.editTextNombresApellidosContactoRegistro);
        editTextCargo = findViewById(R.id.editTextCargoContactoRegistro);
        editTextTelefono1 = findViewById(R.id.editTextTelefonoUnoContactoRegistro);
        editTextTelefono2 = findViewById(R.id.editTextTelefonoDosContactoRegistro);
        fabGuardarContacto = findViewById(R.id.fabGuardarContacto);
        progressBar = findViewById(R.id.progressBarRegistroContacto);

        if (bundle != null) {
            idEntidad = bundle.getString("idEntidad");
            idContacto = bundle.getString("idContacto");
            nombreEntidad = bundle.getString("nombreEntidad");
            RegistroContactoActivity.this.setTitle(nombreEntidad);

            if (idContacto != null && !idContacto.equals("")) {
                nombresApellidos = bundle.getString("nombresApellidos");
                cargo = bundle.getString("cargo");
                telefono1 = bundle.getString("telefono1");
                telefono2 = bundle.getString("telefono2");
                actualizar = true;

                editTextNombres.setText(nombresApellidos);
                editTextCargo.setText(cargo);
                editTextTelefono1.setText(telefono1);
                editTextTelefono2.setText(telefono2);
            }
        } else {
            Toast.makeText(RegistroContactoActivity.this, "No se ha recibido el ID de la entidad. Te devolveremos.", Toast.LENGTH_LONG).show();
            finish();
        }
        final DatabaseReference reference = database.getReference("contactos/" + idEntidad);

        fabGuardarContacto.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                progressBar.setVisibility(View.VISIBLE);

                if (!actualizar) {
                    idContacto = reference.push().getKey();
                }

                nombresApellidos = editTextNombres.getText().toString();
                cargo = editTextCargo.getText().toString();
                telefono1 = editTextTelefono1.getText().toString();
                telefono2 = editTextTelefono2.getText().toString();

                if (nombresApellidos.equals("") || cargo.equals("") || telefono1.equals("")) {
                    Toast.makeText(RegistroContactoActivity.this, "Hay campos obligatorios vacíos", Toast.LENGTH_LONG).show();
                } else {
                    ContactoModel model = new ContactoModel(idContacto, idEntidad, cargo, nombresApellidos, telefono1, telefono2);
                    reference.child(idContacto).setValue(model)
                            .addOnSuccessListener(new OnSuccessListener<Void>() {
                                @Override
                                public void onSuccess(Void aVoid) {
                                    if (actualizar) {
                                        Toast.makeText(RegistroContactoActivity.this, "Contacto actualizado", Toast.LENGTH_LONG).show();
                                    } else {
                                        Toast.makeText(RegistroContactoActivity.this, "Contacto creado", Toast.LENGTH_LONG).show();
                                    }
                                    LimpiarControles();

                                    Intent intent = new Intent(RegistroContactoActivity.this, ContactoActivity.class);
                                    intent.putExtra("idEntidad", idEntidad);
                                    intent.putExtra("nombreEntidad", nombreEntidad);
                                    startActivity(intent);
                                    finish();
                                }
                            })
                            .addOnFailureListener(new OnFailureListener() {
                                @Override
                                public void onFailure(@NonNull Exception e) {
                                    progressBar.setVisibility(View.GONE);
                                    Toast.makeText(RegistroContactoActivity.this, "No se pudo crear el contacto", Toast.LENGTH_LONG).show();
                                    LimpiarControles();
                                }
                            });
                }
            }
        });
    }

    public void LimpiarControles() {
        editTextNombres.setText("");
        editTextCargo.setText("");
        editTextTelefono1.setText("");
        editTextTelefono2.setText("");
    }
}
