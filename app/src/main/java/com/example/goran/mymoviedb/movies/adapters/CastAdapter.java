package com.example.goran.mymoviedb.movies.adapters;

import android.net.Uri;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.example.goran.mymoviedb.R;
import com.example.goran.mymoviedb.data.model.singlemovie.Cast;
import com.facebook.drawee.view.SimpleDraweeView;

import java.util.List;

/**
 * Created by Goran on 8.11.2017..
 */

public class CastAdapter extends RecyclerView.Adapter<CastAdapter.ViewHolder> {

    private static final String IMG_BASE_URL = "https://image.tmdb.org/t/p/w300";

    private List<Cast> cast;
    private ItemClickListener listener;

    public CastAdapter(List<Cast> cast) {
        this.cast = cast;
    }

    public CastAdapter() {

    }

    public void setDataSource(List<Cast> cast) {
        this.cast = cast;
    }

    public interface ItemClickListener {
        void onClick(int position);
    }

    public void setListener(ItemClickListener listener) {
        this.listener = listener;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {

        LinearLayout listItem = (LinearLayout) LayoutInflater.from(
                parent.getContext()).inflate(R.layout.list_item_cast, parent, false);

        return new ViewHolder(listItem);
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {

        LinearLayout listItem = holder.listItem;

        Cast movieCast = cast.get(position);

        SimpleDraweeView imgPoster = listItem.findViewById(R.id.img_item_profile);
        TextView txtName = listItem.findViewById(R.id.txt_item_name);
        TextView txtCharacter = listItem.findViewById(R.id.txt_item_character);

        imgPoster.setImageURI(Uri.parse(IMG_BASE_URL + movieCast.getProfilePath()));
        txtName.setText((movieCast.getName()));
        txtCharacter.setText(movieCast.getCharacter());
    }

    @Override
    public int getItemCount() {
        return cast.size();
    }

    class ViewHolder extends RecyclerView.ViewHolder {

        private LinearLayout listItem;

        ViewHolder(LinearLayout linearLayout) {
            super(linearLayout);

            listItem = linearLayout;

            listItem.setOnClickListener(v -> {

                int position = getAdapterPosition();

                if (listener != null) {
                    listener.onClick(position);
                }
            });
        }
    }
}