package mislugares.example.mislugares;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;

/**
 * Created by Jose on 26/01/2018.
 */

public class PreferenciasActivity extends AppCompatActivity {
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getFragmentManager().beginTransaction()
                .replace(android.R.id.content, new PreferenciasFragment())
                .commit();
    }
}