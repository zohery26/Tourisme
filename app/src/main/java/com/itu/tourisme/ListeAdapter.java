package com.itu.tourisme;

import android.os.Bundle;
import androidx.annotation.NonNull;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;
import androidx.cardview.widget.CardView;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;
import java.util.List;

public class ListeAdapter extends RecyclerView.Adapter<ListeAdapter.ViewHolder> {
    private List<ListeDestination> destinationList;

    public ListeAdapter(List<ListeDestination> destinationList) {
        this.destinationList = destinationList;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.my_row, parent, false);
        return new ListeAdapter.ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        ListeDestination destination = destinationList.get(position);
        AfficheImageUrl afficheImageUrl = new AfficheImageUrl();
        afficheImageUrl.loadImage(destination.getImageResId(), holder.imageView);
        System.out.println("imaage="+destination.getImageResId());
        holder.titleTextView.setText(destination.getTitle());
        holder.descriptionTextView.setText(destination.getDescription());

        holder.cardView.setOnClickListener(v -> {

            FragmentManager fragmentManager = ((AppCompatActivity) v.getContext()).getSupportFragmentManager();
            FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();

            DetailsFragment detailsFragment = new DetailsFragment();
            Bundle args = new Bundle();
            args.putString("image",destination.getImageResId());
            args.putString("title", destination.getTitle());
            args.putString("description", destination.getDescription());
            args.putString("details", destination.getDetails());
            args.putString("prix", destination.getPrix());
            args.putString("telephone", destination.getTelephone());
            detailsFragment.setArguments(args);

            fragmentTransaction.replace(R.id.frame_layout, detailsFragment);
            fragmentTransaction.addToBackStack(null);
            fragmentTransaction.commit();
        });
    }

    @Override
    public int getItemCount() {
        return destinationList.size();
    }

    public static class ViewHolder extends RecyclerView.ViewHolder {
        private ImageView imageView;
        private TextView titleTextView;
        private TextView descriptionTextView;
        private CardView cardView;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            imageView = itemView.findViewById(R.id.item_image);
            titleTextView = itemView.findViewById(R.id.item_title);
            descriptionTextView = itemView.findViewById(R.id.item_description);
            cardView = itemView.findViewById(R.id.mainLayout);
        }
    }

    public void filterList(ArrayList<ListeDestination> filteredList) {
        destinationList = filteredList;
        notifyDataSetChanged();
    }
}