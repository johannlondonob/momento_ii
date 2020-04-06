package com.johannlondonob.momentoii.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.johannlondonob.momentoii.R;
import com.johannlondonob.momentoii.model.UsuarioAccesoRemotoModel;

import java.util.ArrayList;

public class UsuarioAccesoRemotoAdapter extends BaseAdapter {
    private ArrayList<UsuarioAccesoRemotoModel> list;
    private UsuarioAccesoRemotoModel model;
    private Context context;

    public UsuarioAccesoRemotoAdapter(Context context, ArrayList<UsuarioAccesoRemotoModel> list) {
        this.list = list;
        this.context = context;
    }

    @Override
    public int getCount() {
        return list.size();
    }

    @Override
    public Object getItem(int position) {
        return list.get(position);
    }

    @Override
    public long getItemId(int position) {
        return 0;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        View itemView = convertView;
        if (itemView == null) {
            LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            itemView = inflater.inflate(R.layout.item_list_usuario_acceso_remoto, parent, false);
        }

        model = list.get(position);
        TextView textViewUsuario = itemView.findViewById(R.id.textViewUsuarioRemotoListaDetalle);
        TextView textViewClave = itemView.findViewById(R.id.textViewClaveRemotoListaDetalle);
        TextView textViewAsignado = itemView.findViewById(R.id.textViewAsignadoRemotoListaDetalle);
        TextView textViewtelefonoAsignado = itemView.findViewById(R.id.textViewTelefonoAsignadoRemotoListaDetalle);
        TextView textViewAdmin = itemView.findViewById(R.id.textViewAdminRemotoListaDetalle);

        textViewUsuario.setText(model.get_nombreUsuario());
        textViewClave.setText(model.get_claveUsuario());
        textViewAsignado.setText((model.get_asignadoA().equals("") ? "Sin asignar" : model.get_asignadoA()));
        textViewtelefonoAsignado.setText((model.get_telefonoAsignado().equals("") ? "Sin asignar" : model.get_telefonoAsignado()));
        textViewAdmin.setText((model.get_admin() ? "Usuario administrador" : "Usuario normal"));

        return itemView;
    }
}
