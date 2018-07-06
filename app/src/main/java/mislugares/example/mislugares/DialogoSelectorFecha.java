package mislugares.example.mislugares;

import android.app.DatePickerDialog;
import android.app.Dialog;
import android.app.TimePickerDialog;
import android.os.Bundle;
import android.support.v4.app.DialogFragment;
import android.text.format.DateFormat;

import java.util.Calendar;

/**
 * Created by rodjose1 on 03/05/2018.
 */

public class DialogoSelectorFecha extends DialogFragment {
    private DatePickerDialog.OnDateSetListener escuchador;
    public void setOnDateSetListener(DatePickerDialog.OnDateSetListener escuchador){
        this.escuchador = escuchador;
    }
    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {
        Calendar calendario = Calendar.getInstance();
        Bundle args = this.getArguments();
        if (args != null) {
            long fecha = args.getLong("fecha");
            calendario.setTimeInMillis(fecha);
        }
        int anio = calendario.get(Calendar.YEAR);
        int mes = calendario.get(Calendar.MONTH);
        int dia = calendario.get(Calendar.DAY_OF_MONTH);
        return new DatePickerDialog(getActivity(), escuchador, anio,
                mes,dia);
    }
}