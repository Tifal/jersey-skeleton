package fr.univ_lille1.iut_info.pohlem.projetagiles4android;

import android.app.AlertDialog;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Base64;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.TextView;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;

import java.util.HashMap;
import java.util.Map;

public class connexion extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_connexion);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_page_acceuil, menu);
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

    public void onConnect(View view){
        TextView tvNom = (TextView) findViewById(R.id.nomCompte);
        TextView tvMdp = (TextView) findViewById(R.id.mdp);

        load(tvNom.getText().toString(), tvMdp.getText().toString());
    }

    private void load(final String login, final String passwd) {

        RequestQueue queue = Volley.newRequestQueue(this);

        StringRequest stringRequest = new StringRequest(Request.Method.GET, getResources().getString(R.string.url)+getResources().getString(R.string.urlLogin),
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String json) {
                        if (json!=null) {
                            if (login.equals("admin")) {
                                Intent intentMain = new Intent(connexion.this , menu_admin.class);
                                intentMain.putExtra("login", login);
                                connexion.this.startActivity(intentMain);
                            } else {
                                Intent intentMain = new Intent(connexion.this , menu.class);
                                intentMain.putExtra("login", login);
                                connexion.this.startActivity(intentMain);
                            }
                        }
                    }
                }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                AlertDialog.Builder dlgAlert  = new AlertDialog.Builder(connexion.this);
                dlgAlert.setMessage("Mauvais identifiant ou mot de passe");
                dlgAlert.setTitle("Erreur");
                dlgAlert.setPositiveButton("Ok", null);
                dlgAlert.setCancelable(true);
                dlgAlert.create().show();
            }
        }) {
            @Override
            public Map<String, String> getHeaders() throws AuthFailureError {
                Map<String,String> headers = new HashMap<>();
                String creds = String.format("%s:%s", login,passwd);
                String auth = "Basic " + Base64.encodeToString(creds.getBytes(), Base64.DEFAULT);
                headers.put("Authorization", auth);
                return headers;
            }

        };
        queue.add(stringRequest);
    }

    public void onInscrire(View view){
        Intent intentMain = new Intent(connexion.this , inscription.class);
        connexion.this.startActivity(intentMain);
    }
}
