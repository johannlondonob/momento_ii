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
import com.google.android.material.snackbar.Snackbar;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.johannlondonob.momentoii.model.ContactoModel;

public class ContactoDetalleActivity extends AppCompatActivity {

    private String idEntidad, nombreEntidad, idContacto, nombresApellidos, cargo, telefono1, telefono2;
    private TextView textViewNombres, textViewCargo, textViewTelefono1, textViewTelefono2;
    private ContactoModel model;
    private FirebaseDatabase database = FirebaseDatabase.getInstance();
    private DatabaseReference reference = database.getReference("contactos");

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_contacto_detalle);
        Bundle bundle = getIntent().getExtras();
        textViewNombres = findViewById(R.id.textViewNombresApellidosContactoDetalle);
        textViewCargo = findViewById(R.id.textViewCargoContactoDetalle);
        textViewTelefono1 = findViewById(R.id.textViewTelefonoUnoContactoDetalle);
        textViewTelefono2 = findViewById(R.id.textViewTelefonoDosContactoDetalle);

        if (bundle != null) {
            idEntidad = bundle.getString("idEntidad");
            idContacto = bundle.getString("idContacto");
            nombreEntidad = bundle.getString("nombreEntidad");
            ContactoDetalleActivity.this.setTitle(nombreEntidad);

            if (idContacto != null && !idContacto.equals("")) {
                reference = database.getReference("contactos/" + idEntidad);
            }
        } else {
            Toast.makeText(ContactoDetalleActivity.this, "No se ha recibido el ID de la entidad. Te devolveremos.", Toast.LENGTH_LONG).show();
            finish();
        }

        reference.child(idContacto).addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                model = dataSnapshot.getValue(ContactoModel.class);
                if (model != null) {
                    nombresApellidos = model.get_nombresApellidos();
                    cargo = model.get_cargo();
                    telefono1 = model.get_telefonoUno();
                    telefono2 = model.get_telefonoDos();

                    textViewNombres.setText(nombresApellidos);
                    textViewCargo.setText(cargo);
                    textViewTelefono1.setText(telefono1);
                    textViewTelefono2.setText(telefono2);
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {
                Toast.makeText(ContactoDetalleActivity.this, "Se ha cancelado la transacción. Te devolveremos", Toast.LENGTH_LONG).show();
                finish();
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
            // Toast.makeText(AccesoRemotoDetalleActivity.this, "Presionó el botón eliminar", Toast.LENGTH_SHORT).show();
            Snackbar snackbar = Snackbar.make(getWindow().getDecorView(), "¿Está seguro de eliminar el registro?", Snackbar.LENGTH_INDEFINITE);
            snackbar.setAction("Sí, seguro", new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if (idEntidad != null && !idEntidad.equals("")) {
                        reference = database.getReference("contactos/" + idEntidad);
                        reference.child(idContacto).removeValue()
                                .addOnSuccessListener(new OnSuccessListener<Void>() {
                                    @Override
                                    public void onSuccess(Void aVoid) {
                                        Toast.makeText(ContactoDetalleActivity.this, "Se eliminó el contacto", Toast.LENGTH_LONG).show();
                                        Intent intent = new Intent(ContactoDetalleActivity.this, MainActivity.class);
                                        startActivity(intent);
                                        finish();
                                    }
                                })
                                .addOnFailureListener(new OnFailureListener() {
                                    @Override
                                    public void onFailure(@NonNull Exception e) {
                                        Toast.makeText(ContactoDetalleActivity.this, "No se pudo efectuar la eliminación", Toast.LENGTH_LONG).show();
                                        finish();
                                    }
                                });
                    } else {
                        Toast.makeText(ContactoDetalleActivity.this, "El ID del elemento no existe", Toast.LENGTH_LONG).show();
                        finish();
                    }
                }
            });
            snackbar.show();
        }

        if (id == R.id.editar) {
            Intent intent = new Intent(ContactoDetalleActivity.this, RegistroContactoActivity.class);
            intent.putExtra("idEntidad", idEntidad);
            intent.putExtra("idContacto", idContacto);
            intent.putExtra("nombreEntidad", nombreEntidad);
            intent.putExtra("nombresApellidos", nombresApellidos);
            intent.putExtra("cargo", cargo);
            intent.putExtra("telefono1", telefono1);
            intent.putExtra("telefono2", telefono2);
            startActivity(intent);
            finish();
        }

        return super.onOptionsItemSelected(item);
    }

}
