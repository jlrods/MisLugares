package mislugares.example.mislugares;

import android.os.Bundle;
import android.preference.PreferenceFragment;

import mislugares.example.mislugares.R;

/**
 * Created by Jose on 26/01/2018.
 */

public class PreferenciasFragment extends PreferenceFragment {
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        addPreferencesFromResource(R.xml.preferencias);
    }
}
