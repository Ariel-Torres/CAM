package com.innvestiga.prueba.UI;

import android.content.Context;
import android.content.res.TypedArray;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;
import android.widget.GridLayout;
import android.widget.TextView;

import com.innvestiga.prueba.R;

/**
 * Created by Ariel on 26/07/2016.
 */
public class ControlSucursal extends GridLayout {
    TextView txttitulo;
    GridLayout gridSucursal;
    private OnSucursalListener listener;
    public ControlSucursal(Context context, AttributeSet attrs) {
        super(context, attrs);
        inicializar();

        // Procesamos los atributos XML personalizados
        TypedArray a =
                getContext().obtainStyledAttributes(attrs,
                        R.styleable.ControlSucursal);

        String textoBoton = a.getString(
                R.styleable.ControlSucursal_sucursal_text);

        //btnLogin.setText(textoBoton);

        a.recycle();
    }
    private void inicializar()
    {
        //Utilizamos el layout 'control_login' como interfaz del control
        String infService = Context.LAYOUT_INFLATER_SERVICE;
        LayoutInflater li =
                (LayoutInflater)getContext().getSystemService(infService);
        li.inflate(R.layout.control_sucursal, this, true);

        //Obtenemoslas referencias a los distintos control
        txttitulo = (TextView) findViewById(R.id.txttitulo);
        gridSucursal = (GridLayout)findViewById(R.id.gridSucursal);
        //Asociamos los eventos necesarios
        asignarEventos();
    }
    public void setOnLoginListener(OnSucursalListener l)
    {
        listener = l;
    }
    private void asignarEventos()
    {
        gridSucursal.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {
                listener.onClic();
            }
        });

    }
}
