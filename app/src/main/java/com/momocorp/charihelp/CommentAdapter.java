package com.momocorp.charihelp;

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;


/**
 * Created by OWO Technologies on 11/20/2017.
 */

class CommentAdapter extends RecyclerView.Adapter<CommentAdapter.ViewHolder> {


    @Override
    public CommentAdapter.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.tutor_review_layout,
                parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(CommentAdapter.ViewHolder holder, int position) {

    }

    @Override
    public int getItemCount() {
        return 10;
    }

    static class ViewHolder extends RecyclerView.ViewHolder {
//        ExpandableTextView exPTextView;
        TextView reviewText;
        TextView titleText;

        public ViewHolder(View itemView) {
            super(itemView);
//            exPTextView = itemView.findViewById(R.id.root_review);
//            reviewText = exPTextView.findViewById(R.id.review_information_text);
//            titleText = itemView.findViewById(R.id.review_title_text);

        }


    }
}
