package com.momocorp.charihelp;

import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.ms.square.android.expandabletextview.ExpandableTextView;

/**
 * Created by OWO Technologies on 11/20/2017.
 */

class CommentAdapter extends RecyclerView.Adapter<CommentAdapter.ViewHolder> {


    @Override
    public CommentAdapter.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        return null;
    }

    @Override
    public void onBindViewHolder(CommentAdapter.ViewHolder holder, int position) {

    }

    @Override
    public int getItemCount() {
        return 0;
    }

     static class ViewHolder extends RecyclerView.ViewHolder{
         ExpandableTextView exPTextView;
         TextView reviewText;

         public ViewHolder(View itemView) {
             super(itemView);
         }


     }
}
