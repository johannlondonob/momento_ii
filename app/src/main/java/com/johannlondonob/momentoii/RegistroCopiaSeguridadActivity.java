package com.johannlondonob.momentoii;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.media.Image;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.CompoundButton;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.Spinner;
import android.widget.Switch;
import android.widget.Toast;

import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.johannlondonob.momentoii.model.CopiaSeguridadModel;

public class RegistroCopiaSeguridadActivity extends AppCompatActivity {

    private ProgressBar progressBar;
    private FloatingActionButton fabRegistrar;
    private Spinner spinnerServicios;
    private CopiaSeguridadModel model;
    private String idEntidad, nombreEntidad, idCopiaSeguridad, nombreServicio, emailServicio, claveServicio, responsableServicio, emailResponsable;
    private EditText editTextNombre, editTextEmail, editTextClave, editTextResponsable, editTextEmailResponsable;
    private Switch swActivo;
    private ImageView imageViewLogoServicio;
    private boolean actualizar, isActivo;
    private FirebaseDatabase database = FirebaseDatabase.getInstance();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_registro_copia_seguridad);
        Bundle bundle = getIntent().getExtras();
        progressBar = findViewById(R.id.progressBarRegistroCopiaSeguridad);
        fabRegistrar = findViewById(R.id.fabRegistrarCopiaSeguridad);
        spinnerServicios = findViewById(R.id.spinnerServicios);
        editTextNombre = findViewById(R.id.editTextNombreServicioRegistroCopiaSeguridad);
        editTextClave = findViewById(R.id.editTextClaveServicioRegistroCopiaSeguridad);
        editTextEmail = findViewById(R.id.editTextEmailServicioRegistroCopiaSeguridad);
        editTextResponsable = findViewById(R.id.editTextResponsableServicioRegistroCopiaSeguridad);
        editTextEmailResponsable = findViewById(R.id.editTextEmailResponsableServicioRegistroCopiaSeguridad);
        swActivo = findViewById(R.id.switchActivoServicioRegistroCopiaSeguridad);
        imageViewLogoServicio = findViewById(R.id.imageViewLogoServicioRegCopiaSeguridad);

        if (bundle != null) {
            idEntidad = bundle.getString("idEntidad");
            nombreEntidad = bundle.getString("nombreEntidad");
            idCopiaSeguridad = bundle.getString("idCopiaSeguridad");

            if (idCopiaSeguridad != null && !idCopiaSeguridad.equals("")) {
                nombreServicio = bundle.getString("nombreServicio");
                emailServicio = bundle.getString("emailServicio");
                claveServicio = bundle.getString("claveServicio");
                responsableServicio = bundle.getString("responsableServicio");
                emailResponsable = bundle.getString("emailResponsable");
                isActivo = bundle.getBoolean("isActivo");

                editTextNombre.setText(nombreServicio);
                editTextEmail.setText(emailServicio);
                editTextClave.setText(claveServicio);
                editTextResponsable.setText(responsableServicio);
                editTextEmailResponsable.setText(emailResponsable);
                swActivo.setChecked(isActivo);
                actualizar = true;
            }
        }
        final DatabaseReference reference = database.getReference("copias_seguridad/" + idEntidad);
        final String[] servicios = new String[]{"Selecciona un servicio", "Google Drive", "Dropbox", "One Drive", "Otro"};
        ArrayAdapter<String> adaptador = new ArrayAdapter<String>(this, android.R.layout.simple_spinner_item, servicios);
        spinnerServicios = findViewById(R.id.spinnerServicios);
        adaptador.setDropDownViewResource(R.layout.support_simple_spinner_dropdown_item);
        spinnerServicios.setAdapter(adaptador);

        fabRegistrar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                nombreServicio = spinnerServicios.getSelectedItem().toString();
                emailServicio = editTextEmail.getText().toString();
                claveServicio = editTextClave.getText().toString();
                responsableServicio = editTextResponsable.getText().toString();
                emailResponsable = editTextEmailResponsable.getText().toString();

                if (nombreServicio.equals("Selecciona un servicio") || emailServicio.equals("") || claveServicio.equals("") || responsableServicio.equals("") || emailResponsable.equals("")) {
                    Toast.makeText(RegistroCopiaSeguridadActivity.this, "Hay campos obligatorios sin llenar", Toast.LENGTH_LONG).show();
                } else {
                    if (!actualizar) {
                        idCopiaSeguridad = reference.push().getKey();
                    }
                    if (idCopiaSeguridad != null && !idCopiaSeguridad.equals("")) {
                        model = new CopiaSeguridadModel(idCopiaSeguridad, idEntidad, nombreServicio, emailServicio, claveServicio, responsableServicio, emailResponsable, isActivo);
                        reference.child(idCopiaSeguridad).setValue(model)
                                .addOnSuccessListener(new OnSuccessListener<Void>() {
                                    @Override
                                    public void onSuccess(Void aVoid) {
                                        progressBar.setVisibility(View.GONE);
                                        if (actualizar) {
                                            Toast.makeText(RegistroCopiaSeguridadActivity.this, "Éxito al actualizar los datos", Toast.LENGTH_LONG).show();
                                        } else {
                                            Toast.makeText(RegistroCopiaSeguridadActivity.this, "Éxito al crear el registro", Toast.LENGTH_LONG).show();
                                        }
                                        Intent intent = new Intent(RegistroCopiaSeguridadActivity.this, CopiaSeguridadActivity.class);
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
                                        if (actualizar) {
                                            Toast.makeText(RegistroCopiaSeguridadActivity.this, "Error al actualizar los datos", Toast.LENGTH_LONG).show();
                                        } else {
                                            Toast.makeText(RegistroCopiaSeguridadActivity.this, "Error al crear el registro", Toast.LENGTH_LONG).show();
                                        }
                                        onBackPressed();
                                    }
                                });
                    }
                }
            }
        });

        swActivo.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                isActivo = isChecked;
            }
        });

        spinnerServicios.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                nombreServicio = (String) parent.getItemAtPosition(position);
                switch (nombreServicio.toLowerCase()) {
                    case "dropbox":
                        imageViewLogoServicio.setImageResource(R.drawable.ic_dropbox_large);
                        break;
                    case "google drive":
                        imageViewLogoServicio.setImageResource(R.drawable.ic_google_drive_large);
                        break;
                    case "one drive":
                        imageViewLogoServicio.setImageResource(R.drawable.ic_onedrive_large);
                        break;
                    default:
                        imageViewLogoServicio.setImageResource(R.drawable.ic_backup_large);
                        break;
                }
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {}
        });
    }
}
