package com.johannlondonob.momentoii;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.android.material.snackbar.Snackbar;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.johannlondonob.momentoii.model.EntidadModel;

public class EntidadDetalleActivity extends AppCompatActivity {

    private EntidadModel model;
    private String idEntidad, nombreEntidad, nombre, direccion, telefono1, telefono2, email;
    private TextView textViewDireccion, textViewTelefonoUno, textViewTelefonoDos, textViewEmail;
    private FloatingActionButton fabBackupsEntidadDetalle, fabContactosEntidadDetalle, fabRemotosEntidadDetalle;
    private FirebaseDatabase database = FirebaseDatabase.getInstance();
    private DatabaseReference reference = database.getReference("entidad");

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_entidad_detalle);
        idEntidad = getIntent().getStringExtra("idEntidad");
        nombreEntidad = getIntent().getStringExtra("nombreEntidad");

        // Poner el nombre de la entidad en el título de la actividad
        EntidadDetalleActivity.this.setTitle(nombreEntidad);
        // Búsqueda de botones flotantes
        fabRemotosEntidadDetalle = findViewById(R.id.fabRemotosEntidadDetalle);
        fabContactosEntidadDetalle = findViewById(R.id.fabContactosEntidadDetalle);
        fabBackupsEntidadDetalle = findViewById(R.id.fabBackupsEntidadDetalle);

        fabBackupsEntidadDetalle.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Toast.makeText(EntidadDetalleActivity.this, "Presionó el botón Copias de Seguridad", Toast.LENGTH_SHORT).show();
                Intent intent = new Intent(EntidadDetalleActivity.this, CopiaSeguridadActivity.class);
                intent.putExtra("idEntidad", idEntidad);
                intent.putExtra("nombreEntidad", nombreEntidad);
                startActivity(intent);
            }
        });

        fabContactosEntidadDetalle.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //Toast.makeText(EntidadDetalleActivity.this, idEntidad, Toast.LENGTH_SHORT).show();
                Intent contactos = new Intent(EntidadDetalleActivity.this, ContactoActivity.class);
                contactos.putExtra("idEntidad", idEntidad);
                contactos.putExtra("nombreEntidad", nombreEntidad);
                startActivity(contactos);
            }
        });

        fabRemotosEntidadDetalle.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent accesosRemotos = new Intent(EntidadDetalleActivity.this, AccesoRemotoActivity.class);
                accesosRemotos.putExtra("idEntidad", idEntidad);
                accesosRemotos.putExtra("nombreEntidad", nombreEntidad);
                startActivity(accesosRemotos);
            }
        });

        // textViewNombre = findViewById(R.id.textViewNombreEntidadDetalle);
        textViewDireccion = findViewById(R.id.textViewDireccionEntidadDetalle);
        textViewTelefonoUno = findViewById(R.id.textViewTelefonoUnoEntidadDetalle);
        textViewTelefonoDos = findViewById(R.id.textViewTelefonoDosEntidadDetalle);
        textViewEmail = findViewById(R.id.textViewEmailEntidadDetalle);
        model = new EntidadModel();

        if (idEntidad != null && !idEntidad.equals("")) {
            reference.child(idEntidad).addValueEventListener(new ValueEventListener() {
                @Override
                public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                    model = dataSnapshot.getValue(EntidadModel.class);
                    if (model != null) {
                        // textViewNombre.setText(model.get_nombre());

                        nombre = model.get_nombre();
                        direccion = model.get_direccion();
                        telefono1 = model.get_telefonoUno();
                        telefono2 = model.get_telefonoDos();
                        email = model.get_email();

                        textViewDireccion.setText(direccion);
                        textViewTelefonoUno.setText(telefono1);
                        textViewTelefonoDos.setText(telefono2);
                        textViewEmail.setText(email);
                    }
                }

                @Override
                public void onCancelled(@NonNull DatabaseError databaseError) {
                    Toast.makeText(EntidadDetalleActivity.this, "Imposible conectar con la base de datos", Toast.LENGTH_SHORT).show();
                    onBackPressed();
                }
            });
        }
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
        if (id == R.id.eliminar) {
            // Toast.makeText(EntidadDetalleActivity.this, "Presionó el botón eliminar", Toast.LENGTH_SHORT).show();
            Snackbar snackbar = Snackbar.make(getWindow().getDecorView(), "¿Está seguro de eliminar el registro?", Snackbar.LENGTH_INDEFINITE);
            snackbar.setAction("Sí, seguro", new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if (idEntidad != null && !idEntidad.equals("")) {
                        reference.child(idEntidad).removeValue()
                                .addOnSuccessListener(new OnSuccessListener<Void>() {
                                    @Override
                                    public void onSuccess(Void aVoid) {
                                        Toast.makeText(EntidadDetalleActivity.this, "Se eliminó la entidad " + nombre, Toast.LENGTH_LONG).show();
                                        Intent intent = new Intent(EntidadDetalleActivity.this, MainActivity.class);
                                        startActivity(intent);
                                        finish();
                                    }
                                })
                                .addOnFailureListener(new OnFailureListener() {
                                    @Override
                                    public void onFailure(@NonNull Exception e) {
                                        Toast.makeText(EntidadDetalleActivity.this, "No se pudo efectuar la eliminación", Toast.LENGTH_LONG).show();
                                        onBackPressed();
                                    }
                                });
                    } else {
                        Toast.makeText(EntidadDetalleActivity.this, "El ID del elemento no existe", Toast.LENGTH_LONG).show();
                        onBackPressed();
                    }
                }
            });
            snackbar.show();
        }

        if (id == R.id.editar) {
            Intent intent = new Intent(EntidadDetalleActivity.this, RegistroEntidadActivity.class);
            intent.putExtra("idEntidad", idEntidad);
            intent.putExtra("nombreEntidad", nombre);
            intent.putExtra("direccionEntidad", direccion);
            intent.putExtra("telefono1Entidad", telefono2);
            intent.putExtra("telefono2Entidad", telefono2);
            intent.putExtra("emailEntidad", email);
            startActivity(intent);
            finish();
        }

        return super.onOptionsItemSelected(item);
    }
}
