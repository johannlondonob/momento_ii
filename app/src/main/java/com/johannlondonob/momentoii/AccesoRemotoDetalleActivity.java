package com.johannlondonob.momentoii;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;
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
import com.johannlondonob.momentoii.adapter.UsuarioAccesoRemotoAdapter;
import com.johannlondonob.momentoii.model.AccesoRemotoModel;
import com.johannlondonob.momentoii.model.UsuarioAccesoRemotoModel;

import java.util.ArrayList;

public class AccesoRemotoDetalleActivity extends AppCompatActivity {

    private FloatingActionButton fabRegistrarUsuarioAccesoRemoto;
    private ListView listViewUsuariosRemotos;
    private TextView textViewNombreEquipo, textViewIpPublica, textViewIpPrivada, textViewSinUsuarios;
    private String idEntidad, idAccesoRemoto, nombreEntidad, nombreEquipo, ipPublica, ipPrivada;
    private ArrayList<UsuarioAccesoRemotoModel> listUsuarioAccesoRemoto;
    private AccesoRemotoModel model;
    private UsuarioAccesoRemotoModel usuarioAccesoRemotoModel;
    private FirebaseDatabase database = FirebaseDatabase.getInstance();
    private DatabaseReference referenceAccesosRemotos = database.getReference("accesos_remotos/" + idEntidad);

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_acceso_remoto_detalle);
        idEntidad = getIntent().getStringExtra("idEntidad");
        idAccesoRemoto = getIntent().getStringExtra("idAccesoRemoto");
        nombreEntidad = getIntent().getStringExtra("nombreEntidad");
        AccesoRemotoDetalleActivity.this.setTitle(nombreEntidad);

        // Referencia a todos los accesos remotos
        // final DatabaseReference referenceAccesosRemotos = database.getReference("accesos_remotos/" + idEntidad);
        // Referencia a todos los usuarios del acceso remoto entontrado
        final DatabaseReference referenceUsuariosAccesosRemotos = database.getReference("accesos_remotos/" + idEntidad + "/" + idAccesoRemoto + "/usuarios_remotos");
        model = new AccesoRemotoModel();
        fabRegistrarUsuarioAccesoRemoto = findViewById(R.id.fabRegistrarUsuarioAccesoRemotoDetalle);
        textViewNombreEquipo = findViewById(R.id.textViewNombreEquipoAccesoRemotoDetalle);
        textViewIpPublica = findViewById(R.id.textViewIpPublicaAccesoRemotoDetalle);
        textViewIpPrivada = findViewById(R.id.textViewIpPrivadaAccesoRemotoDetalle);
        textViewSinUsuarios = findViewById(R.id.textViewMensajeUsuariosAccesoRemotoDetalle);
        listViewUsuariosRemotos = findViewById(R.id.listViewUsuarioAccesoRemoto);

        if (idAccesoRemoto != null && !idAccesoRemoto.equals("")) {
            referenceAccesosRemotos = database.getReference("accesos_remotos/" + idEntidad);
            referenceAccesosRemotos.child(idAccesoRemoto).addValueEventListener(new ValueEventListener() {
                @Override
                public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                    model = dataSnapshot.getValue(AccesoRemotoModel.class);
                    if (model != null) {
                        nombreEquipo = model.get_nombreEquipo();
                        ipPublica = model.get_ipPublica();
                        ipPrivada = model.get_ipPrivada();

                        if (ipPrivada.equals("")) {
                            ipPrivada = "Sin registro";
                        }

                        textViewNombreEquipo.setText(nombreEquipo);
                        textViewIpPublica.setText(ipPublica);
                        textViewIpPrivada.setText(ipPrivada);
                    }
                }

                @Override
                public void onCancelled(@NonNull DatabaseError databaseError) {
                    Toast.makeText(AccesoRemotoDetalleActivity.this, "Imposible conectar con la base de datos", Toast.LENGTH_SHORT).show();
                    onBackPressed();
                }
            });
        } else {
            Toast.makeText(AccesoRemotoDetalleActivity.this, "No se obtuvo el ID del elemento previamente seleccionado", Toast.LENGTH_SHORT).show();
            onBackPressed();
        }

        referenceUsuariosAccesosRemotos.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                usuarioAccesoRemotoModel = new UsuarioAccesoRemotoModel();
                listUsuarioAccesoRemoto = new ArrayList<>();

                for (DataSnapshot child : dataSnapshot.getChildren()) {
                    usuarioAccesoRemotoModel = child.getValue(UsuarioAccesoRemotoModel.class);
                    listUsuarioAccesoRemoto.add(usuarioAccesoRemotoModel);
                }

                if (listUsuarioAccesoRemoto.size() <= 0) {
                    textViewSinUsuarios.setVisibility(View.VISIBLE);
                } else {
                    textViewSinUsuarios.setVisibility(View.GONE);
                }

                listViewUsuariosRemotos.setAdapter(new UsuarioAccesoRemotoAdapter(AccesoRemotoDetalleActivity.this, listUsuarioAccesoRemoto));
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {
                Toast.makeText(AccesoRemotoDetalleActivity.this, "No se obtuvo el ID del elemento previamente seleccionado", Toast.LENGTH_SHORT).show();
                onBackPressed();
            }
        });

        fabRegistrarUsuarioAccesoRemoto.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent registrarUsuarioRemoto = new Intent(AccesoRemotoDetalleActivity.this, RegistroUsuarioAccesoRemotoActivity.class);
                registrarUsuarioRemoto.putExtra("idEntidad", idEntidad);
                registrarUsuarioRemoto.putExtra("idAccesoRemoto", idAccesoRemoto);
                registrarUsuarioRemoto.putExtra("nombreEntidad", nombreEntidad);
                registrarUsuarioRemoto.putExtra("nombreEquipo", nombreEquipo);
                startActivity(registrarUsuarioRemoto);
                finish();
            }
        });

        listViewUsuariosRemotos.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                usuarioAccesoRemotoModel = (UsuarioAccesoRemotoModel) parent.getItemAtPosition(position);

                String idUsuarioAccesoRemoto, usuario, clave, asignadoA, telefonoAsignado;
                boolean admin;

                idUsuarioAccesoRemoto = usuarioAccesoRemotoModel.get_id();
                usuario = usuarioAccesoRemotoModel.get_nombreUsuario();
                clave = usuarioAccesoRemotoModel.get_claveUsuario();
                asignadoA = usuarioAccesoRemotoModel.get_asignadoA();
                telefonoAsignado = usuarioAccesoRemotoModel.get_telefonoAsignado();
                admin = usuarioAccesoRemotoModel.get_admin();

                Intent intent = new Intent(AccesoRemotoDetalleActivity.this, RegistroUsuarioAccesoRemotoActivity.class);
                intent.putExtra("idEntidad", idEntidad);
                intent.putExtra("idAccesoRemoto", idAccesoRemoto);
                intent.putExtra("nombreEntidad", nombreEntidad);
                intent.putExtra("nombreEquipo", nombreEquipo);
                intent.putExtra("idUsuarioAccesoRemoto", idUsuarioAccesoRemoto);
                intent.putExtra("nombreUsuario", usuario);
                intent.putExtra("claveUsuario", clave);
                intent.putExtra("asignadoA", asignadoA);
                intent.putExtra("telefonoAsignado", telefonoAsignado);
                intent.putExtra("admin", admin);

                startActivity(intent);
                finish();
            }
        });
    }

    // Lo que sigue, dibuja un menú en la parte superior derecha que puede personalizarse
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
            snackbar.show();
        }

        if (id == R.id.editar) {
            Intent intent = new Intent(AccesoRemotoDetalleActivity.this, RegistroAccesoRemotoActivity.class);
            intent.putExtra("idEntidad", idEntidad);
            intent.putExtra("idAccesoRemoto", idAccesoRemoto);
            intent.putExtra("nombreEntidad", nombreEntidad);
            intent.putExtra("nombreEquipo", nombreEquipo);
            intent.putExtra("ipPublica", ipPublica);
            intent.putExtra("ipPrivada", ipPrivada);
            startActivity(intent);
        }

        return super.onOptionsItemSelected(item);
    }
}
