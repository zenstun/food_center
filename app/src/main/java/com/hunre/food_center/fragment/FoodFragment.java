package com.hunre.food_center.fragment;

/**
 * Created by Administrator on 10/01/2017.
 */

import android.graphics.Movie;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.GridView;
import android.widget.ListView;

import com.google.firebase.database.ChildEventListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.hunre.food_center.FoodData;
import com.hunre.food_center.R;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

import static android.R.attr.data;
import static android.R.attr.id;
import static android.R.id.list;


public class FoodFragment extends Fragment {
    private GridView mGridView;
    private ArrayList<FoodData> mFoodList;
    DatabaseReference fData;

    public FoodFragment() {
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View rootView = inflater.inflate(R.layout.fragment_food, container, false);
        mGridView = (GridView) rootView.findViewById(R.id.gv_food);
        fData = FirebaseDatabase.getInstance().getReference();
        fData.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                Iterable<DataSnapshot> food = dataSnapshot.getChildren();
                mFoodList = new ArrayList<FoodData>();
                for(DataSnapshot foodz: food){
                    FoodData foodData = foodz.getValue(FoodData.class);
                    mFoodList.add(foodData);
                }
                Log.e("foodData",""+mFoodList.get(0).getUrlImg());
                AdapterFood movieAdapter = new AdapterFood(getActivity(), mFoodList);
                mGridView.setAdapter(movieAdapter);
            }
            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });

        return rootView;
    }
}
