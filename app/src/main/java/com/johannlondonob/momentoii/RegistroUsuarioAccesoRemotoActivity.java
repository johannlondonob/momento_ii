package com.johannlondonob.momentoii;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
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
import com.johannlondonob.momentoii.model.UsuarioAccesoRemotoModel;

public class RegistroUsuarioAccesoRemotoActivity extends AppCompatActivity {

    private ProgressBar progressBar;
    private FloatingActionButton fabRegistrarUsuarioAccesoRemoto;
    private EditText editTextUsuario, editTextClaveUsuario, editTextAsignado, editTextTelefonoAsignado;
    private boolean swAdmin;
    private Switch switchAdmin;
    private String idEntidad, idAccesoRemoto, idUsuarioAccesoRemoto, nombreEntidad, nombreEquipo, usuarioRemoto, claveRemoto, remotoAsignado, telefonoAsignado;
    private UsuarioAccesoRemotoModel model;
    private FirebaseDatabase database = FirebaseDatabase.getInstance();
    private boolean actualizar = false;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_registro_usuario_acceso_remoto);
        Bundle bundle = getIntent().getExtras();
        progressBar = findViewById(R.id.progressBarUsuarioAccesoRemoto);
        fabRegistrarUsuarioAccesoRemoto = findViewById(R.id.fabRegistrarUsuarioAccesoRemoto);
        editTextUsuario = findViewById(R.id.editTextUsuarioRegistroAccesoRemoto);
        editTextClaveUsuario = findViewById(R.id.editTextClaveUsuarioRegistroAccesoRemoto);
        editTextAsignado = findViewById(R.id.editTextAsignadoRegistroAccesoRemoto);
        editTextTelefonoAsignado = findViewById(R.id.editTextTelefonoAsignadoRegistroAccesoRemoto);
        switchAdmin = findViewById(R.id.switchAdminRegistroAccesoRemoto);

        if (bundle != null) {
            idUsuarioAccesoRemoto = getIntent().getStringExtra("idUsuarioAccesoRemoto");
            idEntidad = getIntent().getStringExtra("idEntidad");
            idAccesoRemoto = getIntent().getStringExtra("idAccesoRemoto");
            nombreEntidad = getIntent().getStringExtra("nombreEntidad");
            nombreEquipo = getIntent().getStringExtra("nombreEquipo");
            usuarioRemoto = getIntent().getStringExtra("nombreUsuario");
            claveRemoto = getIntent().getStringExtra("claveUsuario");
            remotoAsignado = getIntent().getStringExtra("asignadoA");
            telefonoAsignado = getIntent().getStringExtra("telefonoAsignado");
            swAdmin = getIntent().getExtras().getBoolean("admin");

            if (idUsuarioAccesoRemoto != null && !idUsuarioAccesoRemoto.equals("")) {
                editTextUsuario.setText(usuarioRemoto);
                editTextClaveUsuario.setText(claveRemoto);
                editTextAsignado.setText(remotoAsignado);
                editTextTelefonoAsignado.setText(telefonoAsignado);
                switchAdmin.setChecked(swAdmin);

                editTextUsuario.setEnabled(false);
                editTextClaveUsuario.setEnabled(false);
                editTextAsignado.setEnabled(false);
                editTextTelefonoAsignado.setEnabled(false);
                switchAdmin.setEnabled(false);
                actualizar = true;
            }
        }
        RegistroUsuarioAccesoRemotoActivity.this.setTitle("Nuevo usuario remoto en " + nombreEntidad);
        final DatabaseReference reference = database.getReference("accesos_remotos/" + idEntidad + "/" + idAccesoRemoto + "/usuarios_remotos");

        switchAdmin.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                swAdmin = isChecked;
            }
        });

        fabRegistrarUsuarioAccesoRemoto.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                usuarioRemoto = editTextUsuario.getText().toString();
                claveRemoto = editTextClaveUsuario.getText().toString();
                remotoAsignado = editTextAsignado.getText().toString();
                telefonoAsignado = editTextTelefonoAsignado.getText().toString();

                if (usuarioRemoto.equals("") || claveRemoto.equals("")) {
                    Toast.makeText(RegistroUsuarioAccesoRemotoActivity.this, "Hay campos obligatorios sin llenar", Toast.LENGTH_SHORT).show();
                } else {
                    if (!actualizar) {
                        idUsuarioAccesoRemoto = reference.push().getKey();
                    }
                    if (idUsuarioAccesoRemoto != null && !idUsuarioAccesoRemoto.equals("")) {
                        model = new UsuarioAccesoRemotoModel(idUsuarioAccesoRemoto, idEntidad, idAccesoRemoto, usuarioRemoto, claveRemoto, remotoAsignado, telefonoAsignado, swAdmin);
                        reference.child(idUsuarioAccesoRemoto).setValue(model)
                                .addOnSuccessListener(new OnSuccessListener<Void>() {
                                    @Override
                                    public void onSuccess(Void aVoid) {
                                        if (actualizar) {
                                            Toast.makeText(RegistroUsuarioAccesoRemotoActivity.this, "Registro actualizado", Toast.LENGTH_LONG).show();
                                        } else {
                                            Toast.makeText(RegistroUsuarioAccesoRemotoActivity.this, "Registro guardado", Toast.LENGTH_LONG).show();
                                        }
                                        Intent detalleAccesoRemoto = new Intent(RegistroUsuarioAccesoRemotoActivity.this, AccesoRemotoDetalleActivity.class);
                                        detalleAccesoRemoto.putExtra("idEntidad", idEntidad);
                                        detalleAccesoRemoto.putExtra("nombreEntidad", nombreEntidad);
                                        detalleAccesoRemoto.putExtra("idAccesoRemoto", idAccesoRemoto);
                                        startActivity(detalleAccesoRemoto);
                                        finish();
                                    }
                                })
                                .addOnFailureListener(new OnFailureListener() {
                                    @Override
                                    public void onFailure(@NonNull Exception e) {
                                        Toast.makeText(RegistroUsuarioAccesoRemotoActivity.this, "Registro guardado", Toast.LENGTH_LONG).show();
                                        onBackPressed();
                                    }
                                });

                    } else {
                        Toast.makeText(RegistroUsuarioAccesoRemotoActivity.this, "No se obtuvo el ID del elemento previamente seleccionado", Toast.LENGTH_SHORT).show();
                        onBackPressed();
                    }
                }
            }
        });
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_acciones, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        if (id == R.id.eliminar) {
/*            // Toast.makeText(AccesoRemotoDetalleActivity.this, "Presionó el botón eliminar", Toast.LENGTH_SHORT).show();
            Snackbar snackbar = Snackbar.make(getWindow().getDecorView(), "¿Está seguro de eliminar el registro?", Snackbar.LENGTH_INDEFINITE);
            snackbar.setAction("Sí, seguro", new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if (idEntidad != null && !idEntidad.equals("")) {
                        referenceAccesosRemotos = database.getReference("accesos_remotos/" + idEntidad);
                        referenceAccesosRemotos.child(idAccesoRemoto).removeValue()
                                .addOnSuccessListener(new OnSuccessListener<Void>() {
                                    @Override
                                    public void onSuccess(Void aVoid) {
                                        Toast.makeText(AccesoRemotoDetalleActivity.this, "Se eliminó el registro", Toast.LENGTH_LONG).show();
                                        Intent intent = new Intent(AccesoRemotoDetalleActivity.this, MainActivity.class);
                                        startActivity(intent);

                                    }
                                })
                                .addOnFailureListener(new OnFailureListener() {
                                    @Override
                                    public void onFailure(@NonNull Exception e) {
                                        Toast.makeText(AccesoRemotoDetalleActivity.this, "No se pudo efectuar la eliminación", Toast.LENGTH_LONG).show();
                                    }
                                });
                    } else {
                        Toast.makeText(AccesoRemotoDetalleActivity.this, "El ID del elemento no existe", Toast.LENGTH_LONG).show();
                    }
                }
            });
            snackbar.show();*/
        }

        if (id == R.id.editar) {
            if (actualizar){
                editTextUsuario.setEnabled(true);
                editTextClaveUsuario.setEnabled(true);
                editTextAsignado.setEnabled(true);
                editTextTelefonoAsignado.setEnabled(true);
                switchAdmin.setEnabled(true);
            } else {
                Toast.makeText(RegistroUsuarioAccesoRemotoActivity.this, "No gay datos que actualizar",Toast.LENGTH_LONG).show();
            }
        }

        return super.onOptionsItemSelected(item);
    }
}
