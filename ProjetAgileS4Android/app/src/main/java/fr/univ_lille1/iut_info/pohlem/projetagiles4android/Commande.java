package fr.univ_lille1.iut_info.pohlem.projetagiles4android;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.TextView;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.URL;
import java.net.URLConnection;

public class Commande extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_commande);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_commande, menu);
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
    public void createCommande(View view){
        TextView tvTotal = (TextView) findViewById(R.id.total);
        TextView tvNumero = (TextView) findViewById(R.id.Numéro);
        TextView tvAdresse = (TextView) findViewById(R.id.Adresse);
        TextView tvCodePostal = (TextView) findViewById(R.id.codePostal);
        AlertDialog.Builder dlgAlert = new AlertDialog.Builder(this);
        if(tvNumero.getText().toString().equals("") || tvAdresse.getText().toString().equals("")
                || tvCodePostal.getText().toString().equals("") || tvTotal.getText().toString().equals("") ) {
            dlgAlert.setMessage("Problème d'informations");
            dlgAlert.setTitle("Informations");
            dlgAlert.setPositiveButton("Ok",null);
        } else {
            dlgAlert.setMessage("La commande  a été créé !");
            dlgAlert.setTitle("Création Commande ");
            dlgAlert.setPositiveButton("Ok",
                    new DialogInterface.OnClickListener() {
                        public void onClick(DialogInterface dialog, int which) {
                            Intent intentMain = new Intent(Commande.this, menu.class);
                            Commande.this.startActivity(intentMain);
                        }
                    });
        }
        dlgAlert.setCancelable(true);
        dlgAlert.create().show();
    }
    public void calculPrix(View view) throws Exception{

        TextView tvTotal = (TextView) findViewById(R.id.total);
        TextView tvNumero = (TextView) findViewById(R.id.Numéro);
        TextView tvAdresse = (TextView) findViewById(R.id.Adresse);
        TextView tvCodePostal = (TextView) findViewById(R.id.codePostal);
        URL u = new URL("http://www.mapquestapi.com/directions/v2/route?key=43mLZhGUGd7mxngbQfGUdHsv5ECAGjev&from=Lille&to="+tvNumero.getText()+" "+tvAdresse.getText()+","+tvCodePostal.getText()+"&callback=renderNarrative&unit=k");
        URLConnection c = u.openConnection();
        BufferedReader in = new BufferedReader(new InputStreamReader(
                c.getInputStream()));
        String inputLine;
        String mapQuest= "";
        double p = 0;
        while ((inputLine = in.readLine()) != null){
            mapQuest += inputLine;
            if(inputLine.contains("distance")){
                String [] ok = inputLine.split("\"distance\":");
                ok = ok[1].split(",");
                p = Double.parseDouble(ok[0]);
            }
        }
        in.close();
        double prix = 0.00;
        prix+= (int) p*0.07;
        tvTotal.setText(prix+"€");
    }

}

