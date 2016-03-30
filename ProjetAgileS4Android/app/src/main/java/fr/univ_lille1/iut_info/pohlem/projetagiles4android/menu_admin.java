package fr.univ_lille1.iut_info.pohlem.projetagiles4android;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;

public class menu_admin extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_menu_admin);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_menu_admin, menu);
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

    public void voirClients(View view){
        Intent intentMain = new Intent(this, admin_clients.class);
        this.startActivity(intentMain);
    }

    public void voirCommandes(View view){
        Intent intentMain = new Intent(this, admin_commandes.class);
        this.startActivity(intentMain);
    }

    public void voirPlanning(View view){
        Intent intentMain = new Intent(this, admin_planning.class);
        this.startActivity(intentMain);
    }

    public void voirServices(View view){
        Intent intentMain = new Intent(this, admin_services.class);
        this.startActivity(intentMain);
    }

    public void VoirPointsRetraits(View view){
        Intent intentMain = new Intent(this, admin_points_retraits.class);
        this.startActivity(intentMain);
    }

    public void voirTableauBord(View view){
        Intent intentMain = new Intent(this, admin_tableau_bord.class);
        this.startActivity(intentMain);
    }

    public void voirCompte(View view){
        Intent intentMain = new Intent(this, monCompte.class);
        intentMain.putExtra("login", getIntent().getStringExtra("login"));
        this.startActivity(intentMain);
    }
    public void doDeco(View view){
        Intent intentMain = new Intent(menu_admin.this, connexion.class);
        menu_admin.this.startActivity(intentMain);
    }
}
