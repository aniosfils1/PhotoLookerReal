package com.example.myfirst.photolooker;

import android.app.Activity;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.ListView;

import com.loopj.android.http.AsyncHttpClient;
import com.loopj.android.http.JsonHttpResponseHandler;

import org.apache.http.Header;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;


public class PhotosActivity extends Activity {

    public final String CLIENT_ID = "ea09e8a4b08a4235b54a4bcb97b1135e";
    public final String ACCESS_TOKEN = "373804270.1fb234f.da8398a855bc4ec9bb04e57540c4ba15";

    private SwipeRefreshLayout swipeContainer;
    private ArrayList<InstagramPhoto> photos;
    private InstagramPhotosAdapter adapter;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_photos);
        // pull to refresh

        swipeContainer = (SwipeRefreshLayout) findViewById(R.id.swipeContainer);
        // Setup refresh listener which triggers new data loading
        swipeContainer.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                // clean first
                photos.clear();
                // fetch photos
                fetchPopularPhotos();
            }
        });
        // Configure the refreshing colors
        swipeContainer.setColorSchemeResources(android.R.color.holo_blue_bright,
                android.R.color.holo_green_light,
                android.R.color.holo_orange_light,
                android.R.color.holo_red_light);

        // init ListView
        photos = new ArrayList<>();
        // create adapter to link ListView with data model
        adapter = new InstagramPhotosAdapter(this, photos);
        // find ListView
        ListView lvPhotos = (ListView) findViewById(R.id.lvPhotos);
        // bind adapter to ListView
        lvPhotos.setAdapter(adapter);

        // fetch data from API
        fetchPopularPhotos();
    }

    private void fetchPopularPhotos() {
        //String apiurl = "https://api.instagram.com/v1/tags/nofilter/media/recent?client_id=" + CLIENT_ID;
        String apiurl = "https://api.instagram.com/v1/media/popular?access_token=" + ACCESS_TOKEN;
        // do async http request
        AsyncHttpClient client = new AsyncHttpClient();
        client.get(apiurl, null, new JsonHttpResponseHandler() {
            @Override
            public void onSuccess(int statusCode, Header[] headers, JSONObject response) {
                super.onSuccess(statusCode, headers, response);
                // show refreshing indicator
                swipeContainer.setRefreshing(false);
                // decode JSON data
                JSONArray photosJSON = null;
                try {
                    photosJSON = response.getJSONArray("data");
                    for (int i = 0; i < photosJSON.length(); i++) {
                        JSONObject obj = photosJSON.getJSONObject(i);
                        // decode into model
                        InstagramPhoto photo = new InstagramPhoto();
                        photo.username = obj.getJSONObject("user").getString("username");
                        photo.userPicUrl = obj.getJSONObject("user").getString("profile_picture");
                        photo.imageUrl = obj.getJSONObject("images").getJSONObject("standard_resolution").getString("url");
                        photo.imageHeight = obj.getJSONObject("images").getJSONObject("standard_resolution").getInt("height");
                        photo.imageWidth = obj.getJSONObject("images").getJSONObject("standard_resolution").getInt("width");
                        photo.likeCount = obj.getJSONObject("likes").getInt("count");
                        photo.createdTime = obj.getLong("created_time") * 1000;
                        photo.location = obj.isNull("location") ? "" : obj.getJSONObject("location").optString("name", "");
                        photo.caption = obj.isNull("caption") ? "" : obj.getJSONObject("caption").optString("text");
                        photo.commentsCount = obj.getJSONObject("comments").getInt("count");
                        // comments
                        JSONArray aComments = obj.getJSONObject("comments").getJSONArray("data");
                        for (int c = 0; c < aComments.length(); c++) {
                            JSONObject comment = aComments.getJSONObject(c);
                            photo.comments.add(new InstagramComment(
                                    comment.optString("text"),
                                    comment.getJSONObject("from").getString("username"),
                                    comment.getJSONObject("from").getString("profile_picture")
                            ));
                        }
                        // add to list
                        photos.add(photo);
                    }
                } catch (JSONException e) {
                    e.printStackTrace();
                }
                // update ListView
                adapter.notifyDataSetChanged();
                // hide refreshing indicator
                swipeContainer.setRefreshing(false);
            }

            @Override
            public void onFailure(int statusCode, Header[] headers, String responseString, Throwable throwable) {
                super.onFailure(statusCode, headers, responseString, throwable);
                // hide refreshing indicator
                swipeContainer.setRefreshing(false);
            }
        });
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_photos, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }
}

