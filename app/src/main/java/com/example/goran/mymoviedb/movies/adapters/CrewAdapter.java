package com.example.goran.mymoviedb.movies.adapters;

import android.net.Uri;
import android.support.constraint.ConstraintLayout;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.ViewGroup;
import android.widget.TextView;

import com.example.goran.mymoviedb.R;
import com.example.goran.mymoviedb.data.model.details.Crew;
import com.example.goran.mymoviedb.movies.util.BaseUrl;
import com.facebook.drawee.view.SimpleDraweeView;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Goran on 8.11.2017..
 */

public class CrewAdapter extends RecyclerView.Adapter<CrewAdapter.ViewHolder> {

    private List<Crew> crew;
    private ItemClickListener listener;

    public CrewAdapter(List<Crew> crew) {
        this.crew = crew;
    }

    public CrewAdapter() {
        crew = new ArrayList<>();
    }

    public void setDataSource(List<Crew> crew) {
        this.crew = crew;
    }

    public interface ItemClickListener {
        void onClick(int personId);
    }

    public void setListener(ItemClickListener listener) {
        this.listener = listener;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {

        ConstraintLayout listItem = (ConstraintLayout) LayoutInflater.from(
                parent.getContext()).inflate(R.layout.list_item_cast, parent, false);

        return new ViewHolder(listItem);
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {

        ConstraintLayout listItem = holder.listItem;

        Crew movieCrew = crew.get(position);

        SimpleDraweeView imgPoster = listItem.findViewById(R.id.img_item_profile);
        TextView txtName = listItem.findViewById(R.id.txt_item_name);
        TextView txtCharacter = listItem.findViewById(R.id.txt_item_character);

        imgPoster.setImageURI(Uri.parse(BaseUrl.IMG_SMALL + movieCrew.getProfilePath()));
        txtName.setText((movieCrew.getName()));
        txtCharacter.setText(movieCrew.getJob());
    }

    @Override
    public int getItemCount() {
        return crew.size();
    }

    class ViewHolder extends RecyclerView.ViewHolder {

        private ConstraintLayout listItem;

        ViewHolder(ConstraintLayout constraintLayout) {
            super(constraintLayout);

            listItem = constraintLayout;

            listItem.setOnClickListener(v -> {

                int id = crew.get(getAdapterPosition()).getId();

                if (listener != null) {
                    listener.onClick(id);
                }
            });
        }
    }
}