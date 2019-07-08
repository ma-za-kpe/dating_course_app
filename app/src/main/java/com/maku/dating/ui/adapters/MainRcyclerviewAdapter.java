package com.maku.dating.ui.adapters;

import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.request.RequestOptions;
import com.maku.dating.R;
import com.maku.dating.ui.interfaces.IMainActivity;
import com.maku.dating.ui.models.User;

import java.util.ArrayList;

public class MainRcyclerviewAdapter extends RecyclerView.Adapter<MainRcyclerviewAdapter.ViewHolder> {

    private static final String TAG = "MainRcyclerviewAdapter";

//    vars
    private ArrayList<User> mArraylist = new ArrayList<>();
    private Context mContext;

    private IMainActivity mInterface;

    public MainRcyclerviewAdapter(ArrayList<User> mArraylist, Context mContext) {
        this.mArraylist = mArraylist;
        this.mContext = mContext;
    }

    @NonNull
    @Override
    public MainRcyclerviewAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {

        View view = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.main_feed, viewGroup, false);
        ViewHolder holder = new ViewHolder(view);
        return holder;
    }

    @Override
    public void onBindViewHolder(@NonNull MainRcyclerviewAdapter.ViewHolder holder, final int position) {
        Log.d(TAG, "onBindViewHolder: called.");

        RequestOptions requestOptions = new RequestOptions()
                .placeholder(R.drawable.ic_launcher_background);

        Glide.with(mContext)
                .load(mArraylist.get(position).getProfile_image())
                .apply(requestOptions)
                .into(holder.image);

        holder.name.setText(mArraylist.get(position).getName());
        holder.interested_in.setText(mArraylist.get(position).getInterested_in());
        holder.status.setText(mArraylist.get(position).getStatus());

        holder.cardView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Log.d(TAG, "onClick: clicked on: " + mArraylist.get(position).getName());

                mInterface.inflateViewProfileFragment(mArraylist.get(position));
            }
        });
    }

    @Override
    public void onAttachedToRecyclerView(RecyclerView recyclerView) {
        super.onAttachedToRecyclerView(recyclerView);
        mInterface = (IMainActivity) mContext;
    }

    @Override
    public int getItemCount() {
        return mArraylist.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {

        ImageView image;
        TextView name;
        TextView interested_in;
        TextView status;
        CardView cardView;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);

            image = itemView.findViewById(R.id.profile_image);
            name = itemView.findViewById(R.id.name);
            interested_in = itemView.findViewById(R.id.interested_in);
            status = itemView.findViewById(R.id.status);
            cardView = itemView.findViewById(R.id.card_view);
        }
    }
}
