package fr.univ_lille1.iut_info.pohlem.projetagiles4android;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.TextView;

public class voir_commande extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_voir_commande);
        if (getIntent().getIntExtra("id", -1) == -1) {
            Intent intentMain = new Intent(voir_commande.this, admin_commandes.class);
            voir_commande.this.startActivity(intentMain);
        } else {
            String s = getIntent().getStringExtra("details");
            ((TextView) (findViewById(R.id.commande))).setText(s);
        }
    }
}
