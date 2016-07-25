package com.innvestiga.prueba.UI;

import android.annotation.TargetApi;
import android.content.Context;
import android.content.res.TypedArray;
import android.os.Build;
import android.text.Layout;
import android.util.AttributeSet;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;
import android.widget.GridLayout;
import android.widget.TextView;

import com.innvestiga.prueba.R;

/**
 * Created by ariel on 18/07/2016.
 */
public class ControlCanales extends GridLayout {
    //Controles nativos
    private Button c1,c2,c3,c4,c5,c6,ctotal;
    private TextView txtCanal;
    private GridLayout gridCanales;
    private OnLoginListener listener;
    public ControlCanales(Context context) {
        super(context);
        inicializar();
    }
    public ControlCanales(Context context, AttributeSet attrs) {
        super(context, attrs);
        inicializar();

        // Procesamos los atributos XML personalizados
        TypedArray a =
                getContext().obtainStyledAttributes(attrs,
                        R.styleable.ControlLogin);

        String textoBoton = a.getString(
                R.styleable.ControlLogin_login_text);

        //btnLogin.setText(textoBoton);

        a.recycle();
    }
    private void inicializar()
    {
        //Utilizamos el layout 'control_login' como interfaz del control
        String infService = Context.LAYOUT_INFLATER_SERVICE;
        LayoutInflater li =
                (LayoutInflater)getContext().getSystemService(infService);
        li.inflate(R.layout.control_canales, this, true);

        //Obtenemoslas referencias a los distintos control
        c1 = (Button) findViewById(R.id.C1);
        c2 = (Button) findViewById(R.id.C2);
        c3 = (Button) findViewById(R.id.C3);
        c4 = (Button) findViewById(R.id.C4);
        c5 = (Button) findViewById(R.id.C5);
        c6 = (Button) findViewById(R.id.C6);
        ctotal = (Button) findViewById(R.id.Ctotal);
        txtCanal = (TextView) findViewById(R.id.txtCanal);
        gridCanales = (GridLayout)findViewById(R.id.gridcanales);
        //Asociamos los eventos necesarios
        asignarEventos();
    }
    public void setOnLoginListener(OnLoginListener l)
    {
        listener = l;
    }

    private void asignarEventos()
    {
        gridCanales.setOnClickListener(new OnClickListener() {
                @Override
                public void onClick(View v) {
                    listener.onClic();
                }
        });
        c1.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {
                listener.onClic();
            }
        });
        c2.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {
                listener.onClic();
            }
        });
        c3.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {
                listener.onClic();
            }
        });
        c4.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {
                listener.onClic();
            }
        });
        c5.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {
                listener.onClic();
            }
        });
        c6.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {
                listener.onClic();
            }
        });
        ctotal.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {
                listener.onClic();
            }
        });
    }
    public void setTitulo(String txt){
        txtCanal.setText(txt);
    }
    public void setTotal(String txt) {ctotal.setText(txt);}
    public void ShowTitulo(boolean mostrar){
        if(mostrar){
            txtCanal.setVisibility(VISIBLE);
        }else{
            txtCanal.setVisibility(GONE);
        }
    }

    public void setShowCanales(boolean set){
        if(set){
            c1.setVisibility(VISIBLE);
            c2.setVisibility(VISIBLE);
            c3.setVisibility(VISIBLE);
            c4.setVisibility(VISIBLE);
            c5.setVisibility(VISIBLE);
            c6.setVisibility(VISIBLE);
        }
    }
    public String OrdenarTotal(String total, String año){
        String devolver = "";
        devolver = año+"\n"+total+"%"+"\n"+"EXPERIENCIA TOTAL";
        return  devolver;
    }


    @TargetApi(Build.VERSION_CODES.JELLY_BEAN_MR1)
    public void numerodesecciones(int secciones){
        GridLayout.LayoutParams params;
        switch (secciones){
            case 0:
                c1.setVisibility(GONE);
                c2.setVisibility(GONE);
                c3.setVisibility(GONE);
                c4.setVisibility(GONE);
                c5.setVisibility(GONE);
                c6.setVisibility(GONE);
                break;
            case 1:
                params = new GridLayout.LayoutParams(c1.getLayoutParams());
                params.rowSpec = GridLayout.spec(0, 3);    // First cell in first row use rowSpan 2.
                params.columnSpec = GridLayout.spec(3, 0); // First cell in first column use columnSpan 2.
                c1.setLayoutParams(params);
                c2.setVisibility(GONE);
                c3.setVisibility(GONE);
                c4.setVisibility(GONE);
                c5.setVisibility(GONE);
                c6.setVisibility(GONE);
                break;
            case 2:
                GridLayout.LayoutParams itemLP2 = (GridLayout.LayoutParams)c1.getLayoutParams();
                itemLP2.rowSpec = GridLayout.spec(0, 2);
                itemLP2.setGravity(Gravity.FILL);
                c1.setLayoutParams(itemLP2);
                itemLP2 = (GridLayout.LayoutParams)c2.getLayoutParams();
                itemLP2.rowSpec = GridLayout.spec(0, 2);
                itemLP2.setGravity(Gravity.FILL);
                c2.setLayoutParams(itemLP2);
                c3.setVisibility(INVISIBLE);
                c4.setVisibility(INVISIBLE);
                c5.setVisibility(GONE);
                c6.setVisibility(GONE);
                break;
            case 3:
                GridLayout.LayoutParams itemLP3 = (GridLayout.LayoutParams)c1.getLayoutParams();
                itemLP3.rowSpec = GridLayout.spec(0, 2);
                itemLP3.setGravity(Gravity.FILL);
                c1.setLayoutParams(itemLP3);
                itemLP3 = (GridLayout.LayoutParams)c2.getLayoutParams();
                itemLP3.rowSpec = GridLayout.spec(0, 2);
                itemLP3.setGravity(Gravity.FILL);
                c2.setLayoutParams(itemLP3);
                c4.setVisibility(INVISIBLE);
                c5.setVisibility(GONE);
                c6.setVisibility(GONE);
                break;
            case 4:
                GridLayout.LayoutParams itemLP4 = (GridLayout.LayoutParams)c1.getLayoutParams();
                itemLP4.rowSpec = GridLayout.spec(0, 2);
                itemLP4.setGravity(Gravity.FILL);
                c1.setLayoutParams(itemLP4);
                 itemLP4 = (GridLayout.LayoutParams)c2.getLayoutParams();
                itemLP4.rowSpec = GridLayout.spec(0, 2);
                itemLP4.setGravity(Gravity.FILL);
                c2.setLayoutParams(itemLP4);
                //-------------------------------------------------
                //Desplazar el ultimo
                c5.setVisibility(GONE);
                c6.setVisibility(GONE);
                break;
            case 5:
                GridLayout.LayoutParams itemLP5 = (GridLayout.LayoutParams)c1.getLayoutParams();
                itemLP5.rowSpec = GridLayout.spec(0, 2);
                itemLP5.setGravity(Gravity.FILL);
                c1.setLayoutParams(itemLP5);
                //-------------------------------------------------
                //Desplazar el ultimo
                c6.setVisibility(GONE);
                break;
        }
    }
}



