package fr.univ_lille1.iut_info.pohlem.projetagiles4android;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;

public class menu extends AppCompatActivity {

    String login;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_menu);
        Bundle bundle = getIntent().getExtras();
        login = bundle.getString("login");
        if (login == null) {
            Intent intentMain = new Intent(menu.this, connexion.class);
            menu.this.startActivity(intentMain);
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_menu, menu);
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
    public void onMyCompte(View view){
        Intent intentMain = new Intent(menu.this, monCompte.class);
        intentMain.putExtra("login", login);
        menu.this.startActivity(intentMain);
    }
    public void passerCommande(View view){
        Intent intentMain = new Intent(menu.this, Commande.class);
        intentMain.putExtra("login", login);
        menu.this.startActivity(intentMain);
    }
    public void doDeco(View view){
        Intent intentMain = new Intent(menu.this, connexion.class);
        menu.this.startActivity(intentMain);
    }
}
