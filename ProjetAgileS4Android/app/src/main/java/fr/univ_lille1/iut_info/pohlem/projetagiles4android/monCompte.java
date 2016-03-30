package fr.univ_lille1.iut_info.pohlem.projetagiles4android;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Base64;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.Date;

import fr.univ_lille1.iut_info.pohlem.projetagiles4android.bean.User;

public class monCompte extends AppCompatActivity {

    private User currentUser;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_mon_compte);
        if (getIntent().getStringExtra("login") == null) {
            Intent intentMain = new Intent(monCompte.this, connexion.class);
            monCompte.this.startActivity(intentMain);
        } else {
            ((TextView) (findViewById(R.id.login_text))).setText(getIntent().getStringExtra("login"));
            load(getIntent().getStringExtra("login"));
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_mon_compte, menu);
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
    public void enregistrer(View v) {
        String name = (((TextView) (findViewById(R.id.editTextName))).getText().toString());
        String address = (((TextView) (findViewById(R.id.editTextAdress))).getText().toString());
        String passwd = Base64.encodeToString((((TextView) (findViewById(R.id.editTextMdp))).getText().toString()).getBytes(), Base64.DEFAULT);

        if (!name.equals("")) {
            currentUser.setName(name);
        }
        if (!address.equals("")) {
            currentUser.setAddress(address);
        }
        if (!passwd.equals("")) {
            currentUser.setPasswdHash(passwd);
        }

        final Gson gson = new GsonBuilder().create();
        final String json = gson.toJson(currentUser);

       RequestQueue queue = Volley.newRequestQueue(this);
        /*
        StringRequest stringRequest = new StringRequest(Request.Method.PUT, getResources().getString(R.string.url) + getResources().getString(R.string.urlUser) + currentUser.getAlias(),
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String json) {
                        Toast.makeText(monCompte.this, "Modification enregistré", Toast.LENGTH_SHORT).show();
                        //load(currentUser.alias, );
                    }
                }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Toast.makeText(monCompte.this, "ERROR: " + error, Toast.LENGTH_SHORT).show();
            }
        }) {

            @Override
            public byte[] getBody() throws AuthFailureError {
                return json.getBytes();
            }

            @Override
            public String getBodyContentType() {
                return "application/json";
            }
        };

        queue.add(stringRequest);
        */

        JSONObject js = new JSONObject();
        try {
            js.put("name", currentUser.getName());
            js.put("alias", currentUser.getAlias());
            js.put("password", Base64.encodeToString(currentUser.getPasswdHash().getBytes(), Base64.DEFAULT));
            js.put("address", currentUser.getAddress());
            js.put("id", "0");
        }catch (JSONException e) {
            e.printStackTrace();
        }
        JsonObjectRequest jsonObjReq = new JsonObjectRequest(
                Request.Method.POST, getResources().getString(R.string.url) + getResources().getString(R.string.urlUser) +"?t=" + new Date().getTime(),
                js,
                new Response.Listener<JSONObject>() {
                    @Override
                    public void onResponse(JSONObject response) {
                        Toast.makeText(monCompte.this, "Compte modifié", Toast.LENGTH_SHORT).show();
                    }
                }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Toast.makeText(monCompte.this, "ERROR: " + error.getMessage(), Toast.LENGTH_SHORT).show();
            }
        }
        );

        queue.add(jsonObjReq);
    }

    private void load(final String login) {

        RequestQueue queue = Volley.newRequestQueue(this);

        StringRequest request = new StringRequest(Request.Method.GET, getResources().getString(R.string.url) + getResources().getString(R.string.urlUser) +login,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        final TextView tvLogin = (TextView) (findViewById(R.id.login_text));
                        final TextView tvName = (TextView) (findViewById(R.id.editTextName));
                        final TextView tvAdress = (TextView) (findViewById(R.id.editTextAdress));

                        final Gson gson = new GsonBuilder().create();

                        currentUser = gson.fromJson(response, User.class);
                            if (currentUser.getAlias().equals(tvLogin.getText().toString())) {
                                tvName.setHint(currentUser.getName());
                                tvAdress.setHint(currentUser.getAddress());
                            }

                    }
                }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Toast.makeText(monCompte.this, "ERROR: " + error, Toast.LENGTH_SHORT).show();
            }
        });

        queue.add(request);
    }

}
