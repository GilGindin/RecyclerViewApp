package com.gil.recyclerviewapp;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonArrayRequest;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

public class MainActivity extends AppCompatActivity implements Adpter.OnItemClickListener {
    public static final String EXTRA_CREATOR = "creatorName";
    public static final String EXTRA_URL = "imageUrl";
    public static final String EXTRA_LIKES = "likeCount";

    private RecyclerView mRecyclerCiew;
    private Adpter mAdpter;
    private ArrayList<Item> mItems;
    private RequestQueue mRequestQueue;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        mRecyclerCiew = findViewById(R.id.rcycler_view);
        mRecyclerCiew.setHasFixedSize(true);
        mRecyclerCiew.setLayoutManager(new LinearLayoutManager(this));

        mItems = new ArrayList<>();

        mRequestQueue = Volley.newRequestQueue(this);
        parseJson();
    }

    private void parseJson() {
        String url = "https://pixabay.com/api/?key=12015816-c4c1ee713e1b4826568657cce&q=yellow+flowers&image_type=photo&pretty=true";
        JsonObjectRequest request = new JsonObjectRequest(Request.Method.GET, url, null, new Response.Listener<JSONObject>() {
            @Override
            public void onResponse(JSONObject response) {

                try {
                    JSONArray jsonArray = response.getJSONArray("hits");

                    for (int i = 0 ; i < jsonArray.length() ; i++){

                        JSONObject hit = jsonArray.getJSONObject(i);

                        String creatorName = hit.getString("user");
                        String imageUrl = hit.getString("webformatURL");
                        int likeCount = hit.getInt("likes");

                        mItems.add(new Item(imageUrl , creatorName , likeCount));
                    }
                    mAdpter = new Adpter(MainActivity.this , mItems);
                    mRecyclerCiew.setAdapter(mAdpter);
                    mAdpter.setOnItemClickListener(MainActivity.this);

                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                error.printStackTrace();
            }
        });
        mRequestQueue.add(request);
    }

    @Override
    public void onItemClcik(int position) {
        Intent detailIntent = new Intent(this , DetailActivity.class);
        Item clickedItem = mItems.get(position);
        detailIntent.putExtra(EXTRA_URL , clickedItem.getImageUrl());
        detailIntent.putExtra(EXTRA_CREATOR , clickedItem.getmCreator());
        detailIntent.putExtra(EXTRA_LIKES , clickedItem.getmLikes());
        startActivity(detailIntent);
    }
}
