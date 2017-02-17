package com.hunre.food_center.fragment;

/**
 * Created by Administrator on 10/01/2017.
 */

import android.app.Activity;
import android.content.Intent;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.provider.MediaStore;
import android.support.annotation.NonNull;
import android.support.v4.app.Fragment;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.GridView;
import android.widget.ImageView;

import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;
import com.hunre.food_center.FoodData;
import com.hunre.food_center.R;
import com.hunre.food_center.RealPathUtil;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.Calendar;

import static android.R.attr.data;


public class AdminFragment extends Fragment {
    EditText edt_name, edt_addr, edt_des, edt_price, edt_url;
    String name, addr,des,price,url,path;
    ImageView img;
    View rootView;
    Button btn_add,btn_img;
    DatabaseReference fData;
    FoodData foodData;
    FirebaseStorage rootRef = FirebaseStorage.getInstance();
    public AdminFragment() {
        // Required empty public constructorS
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        rootView = inflater.inflate(R.layout.fragment_admin, container, false);
        fData = FirebaseDatabase.getInstance().getReference();
        final StorageReference storageRef = rootRef.getReferenceFromUrl("gs://food-center-fe5ac.appspot.com");
        edt_name = (EditText) rootView.findViewById(R.id.edt_name);
        edt_addr = (EditText) rootView.findViewById(R.id.edt_addr);
        edt_des = (EditText) rootView.findViewById(R.id.edt_des);
        edt_price = (EditText) rootView.findViewById(R.id.edt_price);
        //edt_url = (EditText) rootView.findViewById(R.id.edt_url);
        btn_add = (Button) rootView.findViewById(R.id.btn_send);
        btn_img = (Button) rootView.findViewById(R.id.btn_addimage);
        img = (ImageView) rootView.findViewById(R.id.imgView_add);
        btn_img.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(Intent.ACTION_GET_CONTENT);
                // 2. pick image only
                intent.setType("image/*");
                // 3. start activity
                startActivityForResult(intent, 0);
            }
        });

        btn_add.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                name = edt_name.getText().toString();
                addr = edt_addr.getText().toString();
                des = edt_des.getText().toString();
                price = edt_price.getText().toString();
                //url = edt_url.getText().toString();
                Log.e("path",""+path);

                StorageReference mountainsRef = storageRef.child("img_"+name.trim());
                Uri file = Uri.fromFile(new File(path));
                UploadTask uploadTask = mountainsRef.putFile(file);

                // Register observers to listen for when the download is done or if it fails
                uploadTask.addOnFailureListener(new OnFailureListener() {
                    @Override
                    public void onFailure(@NonNull Exception exception) {
                        // Handle unsuccessful uploads
                    }
                }).addOnSuccessListener(new OnSuccessListener<UploadTask.TaskSnapshot>() {
                    @Override
                    public void onSuccess(UploadTask.TaskSnapshot taskSnapshot) {
                        // taskSnapshot.getMetadata() contains file metadata such as size, content-type, and download URL.
                        url = taskSnapshot.getDownloadUrl().toString();


                    }
                });
                foodData = new FoodData(name,addr,des,Integer.valueOf(price),url);
                Log.e("downloadUrl",""+url);
                fData.push().setValue(foodData);
            }
        });

        return rootView;
    }
    @Override
    public void onActivityResult(int reqCode, int resCode, Intent data) {
        path = getRealPathFromURI(data.getData());
        setImg(path);

    }
    private String getRealPathFromURI(Uri contentURI) {
        String result;
        Cursor cursor = getActivity().getContentResolver().query(contentURI, null, null, null, null);
        if (cursor == null) { // Source is Dropbox or other similar local file path
            result = contentURI.getPath();
        } else {
            cursor.moveToFirst();
            int idx = cursor.getColumnIndex(MediaStore.Images.ImageColumns.DATA);
            result = cursor.getString(idx);
            cursor.close();
        }
        return result;
    }
    private void setImg(String realPath){

        Uri uriFromPath = Uri.fromFile(new File(realPath));

        // you have two ways to display selected image

        // ( 1 ) imageView.setImageURI(uriFromPath);

        // ( 2 ) imageView.setImageBitmap(bitmap);
        Bitmap bitmap = null;
        try {
            bitmap = BitmapFactory.decodeStream(getActivity().getContentResolver().openInputStream(uriFromPath));
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }
        img.setImageBitmap(bitmap);
    }


}
