package com.johannlondonob.momentoii.adapter;

import android.content.Context;
import android.graphics.drawable.Drawable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.johannlondonob.momentoii.R;
import com.johannlondonob.momentoii.model.CopiaSeguridadModel;

import java.util.ArrayList;

public class CopiaSeguridadAdapter extends BaseAdapter {
    private ArrayList<CopiaSeguridadModel> list;
    private CopiaSeguridadModel model;
    private Context context;

    public CopiaSeguridadAdapter(Context context, ArrayList<CopiaSeguridadModel> list) {
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
            itemView = inflater.inflate(R.layout.item_list_copia_seguridad, parent, false);
        }

        model = list.get(position);
        ImageView imageViewLogoServicio = itemView.findViewById(R.id.imageViewIconoServicioItemLista);
        TextView textViewNombreServicio = itemView.findViewById(R.id.textViewNombreServicioItemLista);
        TextView textViewEmailServicio = itemView.findViewById(R.id.textViewEmailServicioItemLista);
        TextView textViewActivoServicio = itemView.findViewById(R.id.textViewActivoServicioItemLista);

        textViewNombreServicio.setText(model.get_nombreServicio());
        textViewEmailServicio.setText(model.get_email());

        switch (model.get_nombreServicio().toLowerCase()) {
            case "dropbox":
                imageViewLogoServicio.setImageResource(R.drawable.ic_dropbox_medium);
                break;
            case "google drive":
                imageViewLogoServicio.setImageResource(R.drawable.ic_google_drive_medium);
                break;
            case "one drive":
                imageViewLogoServicio.setImageResource(R.drawable.ic_onedrive);
                break;
            default:
                imageViewLogoServicio.setImageResource(R.drawable.ic_backup);
                break;
        }

        Drawable ic_power_on = context.getDrawable(R.drawable.ic_power_on_small);
        Drawable ic_power_off = context.getDrawable(R.drawable.ic_power_off_small);

        if (model.is_activo()) {
            textViewActivoServicio.setCompoundDrawablesWithIntrinsicBounds(ic_power_on, null, null, null);
            textViewActivoServicio.setText("Activo");
        } else {
            textViewActivoServicio.setCompoundDrawablesWithIntrinsicBounds(ic_power_off, null, null, null);
            textViewActivoServicio.setText("Inactivo");
        }

        return itemView;
    }
}
