package com.example.e_vicemote.Adapter;

import android.app.Activity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import com.example.e_vicemote.Model.Order;
import com.example.e_vicemote.R;
import com.google.firebase.database.annotations.NotNull;

import java.util.List;


public class CustomAdapter2 extends ArrayAdapter<Order> {

    private Activity context;
    List<Order> ListData;

    public CustomAdapter2(Activity context, List<Order> ListData) {
        super(context, R.layout.data_order2, ListData);
        this.context = context;
        this.ListData = ListData;
    }

    @NotNull
    @Override
    public View getView(int position, View convertView, @NotNull ViewGroup parent) {
        LayoutInflater inflater = context.getLayoutInflater();
        View listViewItem = inflater.inflate(R.layout.data_order2, null, true);

        TextView nama_pelanggan = (TextView) listViewItem.findViewById(R.id.nama_pelanggan);
        //TextView waktu = (TextView) listViewItem.findViewById(R.id.waktu);
        TextView status_order = (TextView) listViewItem.findViewById(R.id.status_order);

        Order data = ListData.get(position);
        nama_pelanggan.setText(data.getNama_pelanggan());
        status_order.setText(data.getStatus_order());
        //waktu.setText(data.getHour()+":"+data.getMinute()+"WIB"+" / "+data.getDay()+"-"+data.getMonth()+"-"+data.getYear());

        return listViewItem;
    }
}