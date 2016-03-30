package fr.univ_lille1.iut_info.pohlem.projetagiles4android;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.reflect.TypeToken;

import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import fr.univ_lille1.iut_info.pohlem.projetagiles4android.bean.User;

public class admin_clients extends AppCompatActivity {

    private ListView lv;
    private List<User> users;
    private ArrayAdapter<String> mAdapter;
    private List<String> values;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_admin_clients);

        lv = (ListView) findViewById(R.id.listViewClient);
        users = new ArrayList<>();
        values = new ArrayList<>();
        mAdapter = new ArrayAdapter<>(admin_clients.this,
                android.R.layout.simple_list_item_1, values);
        lv.setAdapter(mAdapter);

        load();
    }

    private void load() {

        RequestQueue queue = Volley.newRequestQueue(this);

        StringRequest stringRequest = new StringRequest(Request.Method.GET, getResources().getString(R.string.url) + getResources().getString(R.string.urlUser) +"?t=" + new Date().getTime(),
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String json) {
                        final Gson gson = new GsonBuilder().create();

                        Type listType = new TypeToken<List<User>>() {
                        }.getType();
                        users = gson.fromJson(json, listType);

                        values.clear();
                        for (User user : users) {
                            values.add(user.toString());
                        }

                        lv.setVisibility(View.VISIBLE);

                        lv.invalidateViews();
                        mAdapter.notifyDataSetChanged();
                    }
                }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Toast.makeText(admin_clients.this, "ERROR: " + error, Toast.LENGTH_SHORT).show();
            }
        });
        queue.add(stringRequest);
    }
}