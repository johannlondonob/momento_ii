package com.johannlondonob.momentoii;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.ProgressBar;
import android.widget.Toast;

import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.android.material.snackbar.Snackbar;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.johannlondonob.momentoii.adapter.ContactoAdapter;
import com.johannlondonob.momentoii.model.ContactoModel;

import java.util.ArrayList;

public class ContactoActivity extends AppCompatActivity {

    private ContactoModel model;
    private ListView listView;
    private ArrayList list;
    private FloatingActionButton fabAgregarContacto;
    private ProgressBar progressBar;
    private String idEntidad, nombreEntidad, idContacto, nombresApellidos, cargo, telefono1, telefono2;
    private FirebaseDatabase database = FirebaseDatabase.getInstance();
    private DatabaseReference reference = database.getReference("contactos");

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_contacto);
        Bundle bundle = getIntent().getExtras();
        model = new ContactoModel();
        listView = findViewById(R.id.listViewContacto);
        fabAgregarContacto = findViewById(R.id.fabAgregarContacto);
        progressBar = findViewById(R.id.progressBarContactoActivity);

        if (bundle != null) {
            idEntidad = bundle.getString("idEntidad");
            nombreEntidad = bundle.getString("nombreEntidad");
            reference = database.getReference("contactos/" + idEntidad);
            ContactoActivity.this.setTitle(nombreEntidad);
        } else {
            Toast.makeText(ContactoActivity.this, "No se ha recibido el ID de la entidad. Te devolveremos.", Toast.LENGTH_LONG).show();
            onBackPressed();
        }

        reference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                list = new ArrayList();

                for (DataSnapshot child : dataSnapshot.getChildren()) {
                    model = child.getValue(ContactoModel.class);
                    list.add(model);
                }

                if (list.size() <= 0) {
                    progressBar.setVisibility(View.GONE);
                    Snackbar snackbar = Snackbar.make(getWindow().getDecorView(), "No hay contactos por mostrar. ¿Desea crear uno?", Snackbar.LENGTH_INDEFINITE)
                            .setAction("Sí, seguro", new View.OnClickListener() {
                                @Override
                                public void onClick(View v) {
                                    Intent intent = new Intent(ContactoActivity.this, RegistroContactoActivity.class);
                                    intent.putExtra("idEntidad", idEntidad);
                                    intent.putExtra("nombreEntidad", nombreEntidad);
                                    startActivity(intent);
                                }
                            });
                    snackbar.show();
                } else {
                    listView.setAdapter(new ContactoAdapter(ContactoActivity.this, list));
                    progressBar.setVisibility(View.GONE);
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {
                Toast.makeText(ContactoActivity.this, "Se presentó un problema con su solicitud. Regresará dónde estaba", Toast.LENGTH_LONG).show();
                finish();
            }
        });

        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                model = (ContactoModel) parent.getItemAtPosition(position);
                idContacto = model.get_id();
                nombresApellidos = model.get_nombresApellidos();
                cargo = model.get_cargo();
                telefono1 = model.get_telefonoUno();
                telefono2 = model.get_telefonoDos();

                Intent intent = new Intent(ContactoActivity.this, ContactoDetalleActivity.class);
                intent.putExtra("idEntidad", idEntidad);
                intent.putExtra("nombreEntidad", nombreEntidad);
                intent.putExtra("idContacto", idContacto);
                intent.putExtra("nombresApellidos", nombresApellidos);
                intent.putExtra("cargo", cargo);
                intent.putExtra("telefono1", telefono1);
                intent.putExtra("telefono2", telefono2);
                startActivity(intent);
                onBackPressed();
            }
        });

        fabAgregarContacto.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(ContactoActivity.this, RegistroContactoActivity.class);
                intent.putExtra("idEntidad", idEntidad);
                intent.putExtra("nombreEntidad", nombreEntidad);
                intent.putExtra("nombresApellidos", nombresApellidos);
                startActivity(intent);
                finish();
            }
        });

    }
}
