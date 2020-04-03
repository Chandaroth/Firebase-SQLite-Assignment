package com.example.myapplication;

import android.content.DialogInterface;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AlertDialog;
import androidx.fragment.app.Fragment;

import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.squareup.picasso.Picasso;

public class DetailFragment extends Fragment {

    private ImageView DetailImageView;
    private Button BtnDetailUpdate;
    private TextView DetailTextView;
    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_detail, container, false);
        DetailTextView= view.findViewById(R.id.txtNameDetail);
        DetailImageView=view.findViewById(R.id.imageDetail);
        BtnDetailUpdate=view.findViewById(R.id.btnUpdateDetail);
        String IName = getArguments().getString("IName");
        String IUrl = getArguments().getString("IUrl");
        DetailTextView.setText(IName);
        Picasso.with(getActivity())
                .load(IUrl)
                .placeholder(R.drawable.ic_menu_gallery)
                .fit()
                .centerCrop()
                .into(DetailImageView);

        DetailTextView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(getActivity(),"Select DetailEditView",Toast.LENGTH_SHORT).show();
            }
        });



        BtnDetailUpdate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                final EditText resetMail=new EditText(v.getContext());
                final AlertDialog.Builder updateDialog=new AlertDialog.Builder(v.getContext());
                updateDialog.setTitle("Update Dialog");
                updateDialog.setMessage("Enter Your Update Data Here");
                updateDialog.setView(resetMail);
                updateDialog.setPositiveButton("Yes", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {

                    }
                });

                updateDialog.setNegativeButton("No", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {

                    }
                });
            }
        });
        return view;
    }
}
