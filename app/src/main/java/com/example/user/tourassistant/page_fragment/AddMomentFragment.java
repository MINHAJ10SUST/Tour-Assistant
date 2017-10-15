package com.example.user.tourassistant.page_fragment;


import android.app.ProgressDialog;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.Toast;

import com.example.user.tourassistant.R;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;

import static android.app.Activity.RESULT_OK;

/**
 * A simple {@link Fragment} subclass.
 */
public class AddMomentFragment extends Fragment {

    private static final int REQUEST_CAMERA = 1;
    private static final int GALLERY_REQUEST = 1;
    private ImageButton addImage;

    private EditText addTitleET, addDescriptionET;
    private Button postMomentBTN;
    private Uri imageUri = null;
    private StorageReference mStorageRef;
    private ProgressDialog progressDialog;
    private FirebaseAuth mAuth;
    private FirebaseUser mCurrentUser;
    private FirebaseAuth.AuthStateListener mAuthStateListener;
    private DatabaseReference momentDatabase, mDatabaseUser;

    public AddMomentFragment() {
        // Required empty public constructor
    }





    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_add_moment, container, false);
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        mAuth = FirebaseAuth.getInstance();

        mCurrentUser = mAuth.getCurrentUser();
        mStorageRef = FirebaseStorage.getInstance().getReference();
        momentDatabase = FirebaseDatabase.getInstance().getReference().child("moments");
        //mDatabaseUser = FirebaseDatabase.getInstance().getReference().child("users").child(mCurrentUser.getUid());

        addImage = (ImageButton) getActivity().findViewById(R.id.add_image);
        addTitleET = (EditText) getActivity().findViewById(R.id.et_add_title);
        addDescriptionET = (EditText) getActivity().findViewById(R.id.et_add_description);
        postMomentBTN = (Button) getActivity().findViewById(R.id.btn_post_moment);
        progressDialog = new ProgressDialog(getActivity());



        postMomentBTN.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startPosting();
            }
        });


        addImage.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent galleryIntent = new Intent(Intent.ACTION_GET_CONTENT);
                galleryIntent.setType("image/*");
                startActivityForResult(galleryIntent, GALLERY_REQUEST);


            }
        });


    }




    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent imageReturnedIntent) {
        super.onActivityResult(requestCode, resultCode, imageReturnedIntent);


                if(requestCode==GALLERY_REQUEST && resultCode == RESULT_OK){
                    imageUri = imageReturnedIntent.getData();
                    addImage.setImageURI(imageUri);
                }



    }



    private void startPosting() {
        progressDialog.setMessage("Uploading Post..");
        final String title = addTitleET.getText().toString().trim();
        final String description = addDescriptionET.getText().toString().trim();
        if (!TextUtils.isEmpty(title) && !TextUtils.isEmpty(description) && imageUri != null){
            progressDialog.show();

            StorageReference filepath = mStorageRef.child("Moment Image").child(imageUri.getLastPathSegment());
            filepath.putFile(imageUri)
                    .addOnSuccessListener(new OnSuccessListener<UploadTask.TaskSnapshot>() {
                        @Override
                        public void onSuccess(UploadTask.TaskSnapshot taskSnapshot) {
                            // Get a URL to the uploaded content
                            @SuppressWarnings("VisibleForTests") final Uri downloadUrl = taskSnapshot.getDownloadUrl();
                            final DatabaseReference newPost = momentDatabase.push();
                            newPost.child("title").setValue(title);
                            newPost.child("desc").setValue(description);
                            newPost.child("image").setValue(downloadUrl.toString());
                            newPost.child("userId").setValue("minhaj");

                            FragmentManager fm3 = getActivity().getSupportFragmentManager();
                            FragmentTransaction ft3 = fm3.beginTransaction();
                            MomentFragment momentFragment = new MomentFragment();
                            ft3.replace(R.id.homeFragmentView,momentFragment);
                            ft3.addToBackStack(null);
                            ft3.commit();



                            progressDialog.dismiss();



                        }
                    })
                    .addOnFailureListener(new OnFailureListener() {
                        @Override
                        public void onFailure(@NonNull Exception exception) {
                            // Handle unsuccessful uploads
                            Toast.makeText(getActivity(), "Uploading Post Failed", Toast.LENGTH_SHORT).show();
                            progressDialog.dismiss();
                        }
                    });
        }

    }



}
