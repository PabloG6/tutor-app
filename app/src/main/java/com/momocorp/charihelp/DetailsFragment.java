package com.momocorp.charihelp;

import android.content.Context;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;



public class DetailsFragment extends Fragment {


    private static final String RATINGS = "RATINGS";
    private static final String NAME = "name";
    private static final String IMAGE = "images";
    private OnFragmentInteractionListener mListener;

    public DetailsFragment() {
    }

    // TODO: Rename and change types and number of parameters
    public static DetailsFragment newInstance(String name, String uri, float ratings) {
        DetailsFragment fragment = new DetailsFragment();
        Bundle args = new Bundle();
        args.putFloat(RATINGS, ratings);
        args.putString(NAME, name);
        args.putString(IMAGE, uri);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if(getArguments()!=null)
        {
            Bundle bundle = getArguments();
        }

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_details, container, false);
        RecyclerView recycler = view.findViewById(R.id.reviews_recycler);
        LinearLayoutManager layoutManager = new LinearLayoutManager(view.getContext());
        recycler.setLayoutManager(layoutManager);
        //comment adapter
        CommentAdapter commentAdapter = new CommentAdapter();
        recycler.setAdapter(commentAdapter);

        return view;

    }

    // TODO: Rename method, update argument and hook method into UI event



    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        if (context instanceof OnFragmentInteractionListener) {
            mListener = (OnFragmentInteractionListener) context;
        } else {
            throw new RuntimeException(context.toString()
                    + " must implement OnFragmentInteractionListener");
        }
    }

    @Override
    public void onDetach() {
        super.onDetach();
        mListener = null;
    }

    public interface OnFragmentInteractionListener {
        // TODO: Update argument type and name
        void onFragmentInteraction(Uri uri);

        void onShowFragment();
    }
}
