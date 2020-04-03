package com.example.myapplication;

import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import com.squareup.picasso.Picasso;

public class DetailFragement extends Fragment {
    private TextView DetailTextView;
    private ImageView DetailImageView;
    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragement_detail, container, false);
        DetailTextView= view.findViewById(R.id.txtNameDetail);
        DetailImageView=view.findViewById(R.id.imageDetail);
        String IName = getArguments().getString("IName");
        String IUrl = getArguments().getString("IUrl");
        DetailTextView.setText("Product Name: "+IName);
        Picasso.with(getActivity())
                .load(IUrl)
                .placeholder(R.drawable.ic_menu_gallery)
                .fit()
                .centerCrop()
                .into(DetailImageView);
        return view;
    }
}
