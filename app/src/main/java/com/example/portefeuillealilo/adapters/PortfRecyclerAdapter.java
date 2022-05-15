package com.example.portefeuillealilo.adapters;

import android.graphics.Color;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.appcompat.widget.AppCompatTextView;
import androidx.recyclerview.widget.RecyclerView;

import com.example.portefeuillealilo.R;
import com.example.portefeuillealilo.model.Portefeuille;

import java.util.List;

public class PortfRecyclerAdapter  extends RecyclerView.Adapter<PortfRecyclerAdapter.PortfViewHolder>{


    private List<Portefeuille> listPortf;

    public PortfRecyclerAdapter(List<Portefeuille> listPortf) {
        this.listPortf = listPortf;
    }


    @Override
    public PortfViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        // inflating recycler item view
        View itemView = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.item_portf_recycler, parent, false);

        return new PortfViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(PortfRecyclerAdapter.PortfViewHolder holder, int position) {
        holder.textViewName.setText(listPortf.get(position).getTitle());
        holder.tv_date.setText(listPortf.get(position).getDate());
        String type = listPortf.get(position).getType();
        if (type.equalsIgnoreCase("Depenses")) {
            holder.tv_solde.setText(" - " + listPortf.get(position).getSolde() + " DA");
            holder.tv_solde.setTextColor(Color.RED);

        } else {
            holder.tv_solde.setText(" + " + listPortf.get(position).getSolde() + " DA");
            holder.tv_solde.setTextColor(Color.GREEN);

        }
    }

    @Override
    public int getItemCount() {
     //   Log.v(PortfRecyclerAdapter.class.getSimpleName(),""+listPortf.size());
        return listPortf.size();
    }


    /**
     * ViewHolder class
     */
    public class PortfViewHolder extends RecyclerView.ViewHolder {

        public AppCompatTextView textViewName;
        public AppCompatTextView tv_solde;
        public AppCompatTextView tv_date;

        public PortfViewHolder(View view) {
            super(view);
            textViewName = (AppCompatTextView) view.findViewById(R.id.textViewName);
            tv_solde = (AppCompatTextView) view.findViewById(R.id.tv_solde);
            tv_date = (AppCompatTextView) view.findViewById(R.id.tv_date);
        }
    }

}
