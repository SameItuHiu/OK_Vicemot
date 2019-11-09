package com.example.e_vicemote.Adapter;

import android.app.Activity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import com.example.e_vicemote.Model.Data;
import com.example.e_vicemote.R;
import com.google.firebase.database.annotations.NotNull;

import java.util.List;


public class CustomAdapter extends ArrayAdapter<Data> {

    private Activity context;
    List<Data> ListData;

    public CustomAdapter(Activity context, List<Data> ListData) {
        super(context, R.layout.data_order, ListData);
        this.context = context;
        this.ListData = ListData;
    }

    @NotNull
    @Override
    public View getView(int position, View convertView, @NotNull ViewGroup parent) {
        LayoutInflater inflater = context.getLayoutInflater();
        View listViewItem = inflater.inflate(R.layout.data_order, null, true);

        TextView nama_toko = (TextView) listViewItem.findViewById(R.id.nama_mitra);
        TextView info_barang = (TextView) listViewItem.findViewById(R.id.info_barang);
        TextView status_order = (TextView) listViewItem.findViewById(R.id.status_order);

        Data data = ListData.get(position);
        nama_toko.setText(data.getNama_toko());
        info_barang.setText(data.getInfo_barang());
        status_order.setText(data.getStatus_order());

        return listViewItem;
    }
}