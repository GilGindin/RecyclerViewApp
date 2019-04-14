package com.gil.recyclerviewapp;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import com.squareup.picasso.Picasso;
import java.util.ArrayList;

public class Adpter extends RecyclerView.Adapter<Adpter.ViewHolder> {

    private Context mContext;
    private ArrayList<Item> mItems;
    private OnItemClickListener mListener;

    public interface OnItemClickListener{
        void onItemClcik(int position);
    }

    public void setOnItemClickListener(OnItemClickListener listener){
        this.mListener = listener;
    }
    public Adpter(Context context, ArrayList<Item> list) {
        this.mContext = context;
        this.mItems = list;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        View v = LayoutInflater.from(mContext).inflate(R.layout.item_view , viewGroup , false);
        return new ViewHolder(v);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder viewHolder, int position) {

        Item current = mItems.get(position);
        String imageUr = current.getImageUrl();
        String creatorName = current.getmCreator();
        int likeCount = current.getmLikes();

        viewHolder.mtextviewCreator.setText(creatorName);
        viewHolder.mtextviewLikes.setText("Likes: " +likeCount);
        Picasso.get().load(imageUr).fit().centerInside().into(viewHolder.mimageView);
    }

    @Override
    public int getItemCount() {
        return mItems.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder{

        public ImageView mimageView;
        public TextView mtextviewCreator;
        public TextView mtextviewLikes;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            mimageView = itemView.findViewById(R.id.image_view);
            mtextviewCreator = itemView.findViewById(R.id.text_view_creator);
            mtextviewLikes = itemView.findViewById(R.id.text_view_likes);
            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if (mListener != null){
                        int position = getAdapterPosition();
                        if (position != RecyclerView.NO_POSITION){
                            mListener.onItemClcik(position);
                        }
                    }
                }
            });
        }
    }
}
