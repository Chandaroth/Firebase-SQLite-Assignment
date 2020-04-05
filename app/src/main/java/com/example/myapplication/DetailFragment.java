package com.example.myapplication;

import android.content.ContentResolver;
import android.content.DialogInterface;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.os.Handler;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.webkit.MimeTypeMap;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AlertDialog;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

import com.example.myapplication.firebase.Upload;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.storage.OnProgressListener;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.StorageTask;
import com.google.firebase.storage.UploadTask;
import com.squareup.picasso.Picasso;

import static android.app.Activity.RESULT_OK;

public class DetailFragment extends Fragment {

    private static final int PICK_IMAGE_REQUEST = 1;
    private ImageView DetailImageView;
    private Button BtnDetailUpdate;
    private Button BtnEditDetail;
    private Button BtnBrowseImage;
    private TextView DetailName;
    private EditText DetailPrice;
    private Uri mImageUri;

   /* private StorageReference mStorageRef;
    private DatabaseReference mDatabaseRef;
    private StorageTask mUploadTask;
*/
    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_detail, container, false);
        DetailName = view.findViewById(R.id.txtNameDetail);
        DetailImageView = view.findViewById(R.id.imageDetail);
        DetailPrice=view.findViewById(R.id.txtPriceDetail);
        BtnDetailUpdate = view.findViewById(R.id.btnUpdateDetail);
        BtnEditDetail = view.findViewById(R.id.btnEditDetail);
        BtnBrowseImage = view.findViewById(R.id.btnBrowseDetail);

        String IName = getArguments().getString("IName");
        String IUrl = getArguments().getString("IUrl");
        String IPrice=getArguments().getString("IPrice");
        final String IKey = getArguments().getString("IKey");
        DetailName.setText(IName);
        DetailPrice.setText(IPrice);

        Picasso.with(getActivity())
                .load(IUrl)
                .placeholder(R.drawable.ic_menu_gallery)
                .fit()
                .centerCrop()
                .into(DetailImageView);


        BtnBrowseImage.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                openFileChooser();
            }
        });

        BtnEditDetail.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(DetailPrice.isEnabled()==false) {
                    DetailPrice.setEnabled(true);
                    DetailPrice.requestFocus();
                }else{
                    DetailPrice.setEnabled(false);
                }
            }
        });

        BtnDetailUpdate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                try {
                    FirebaseDatabase.getInstance().getReference().child("uploads").child(IKey)
                            .child("name").setValue(DetailName.getText().toString());
                    FirebaseDatabase.getInstance().getReference().child("uploads").child(IKey)
                            .child("price").setValue(DetailPrice.getText().toString());
                    ShowAll();
                    Toast.makeText(getActivity(), "Update Successfully", Toast.LENGTH_SHORT).show();
                } catch (Exception e) {
                    Toast.makeText(getActivity(), "Error: " + e.toString(), Toast.LENGTH_SHORT).show();
                }
            }
        });
        return view;
    }

    private void openFileChooser() {
        Intent intent = new Intent();
        intent.setType("image/*");
        intent.setAction(Intent.ACTION_GET_CONTENT);
        startActivityForResult(intent, PICK_IMAGE_REQUEST);
    }

 /*   private String getFileExtension(Uri uri) {
        ContentResolver cR = getContext().getContentResolver();
        MimeTypeMap mime = MimeTypeMap.getSingleton();
        return mime.getExtensionFromMimeType(cR.getType(uri));
    }*/


    private void ShowAll() {
        FragmentManager fragmentManager = getFragmentManager();
        FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
        MainFragment mainFragment = new MainFragment();
        fragmentTransaction.replace(R.id.container_fragment,mainFragment);
        fragmentTransaction.commit();
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == PICK_IMAGE_REQUEST && resultCode == RESULT_OK
                && data != null && data.getData() != null) {
            mImageUri = data.getData();
            Picasso.with(getActivity()).load(mImageUri).into(DetailImageView);
        }
    }
}
