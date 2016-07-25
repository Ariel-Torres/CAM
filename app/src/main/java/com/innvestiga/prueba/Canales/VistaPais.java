package com.innvestiga.prueba.Canales;


import android.app.FragmentManager;
import android.app.FragmentTransaction;
import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.app.Fragment;
import android.util.Log;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.Button;
import android.widget.TextView;
import android.widget.ViewFlipper;

import com.innvestiga.prueba.Inicio;
import com.innvestiga.prueba.R;
import com.innvestiga.prueba.UI.ControlCanales;
import com.innvestiga.prueba.UI.OnLoginListener;

/**
 * A simple {@link Fragment} subclass.
 */
public class VistaPais extends Fragment {
    String TAG = "VistaPais",NombrePais,NombreCanal;
    ViewFlipper simpleViewFlipper_vp;
    TextView contador_vp;
    int flag;
    public VistaPais() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view =  inflater.inflate(R.layout.fragment_vista_pais, container, false);
        //Back pressed Logic for fragment
        view.setFocusableInTouchMode(true);
        view.requestFocus();
        view.setOnKeyListener(new View.OnKeyListener() {
            @Override
            public boolean onKey(View v, int keyCode, KeyEvent event) {
                if (event.getAction() == KeyEvent.ACTION_DOWN) {
                    if (keyCode == KeyEvent.KEYCODE_BACK) {
                        Log.i(TAG,"atras");
                        VistaCanal vistaCanal = new VistaCanal();
                        FragmentTransaction trans = getActivity().getFragmentManager().beginTransaction();
                        trans.replace(R.id.container, vistaCanal, "vistaCanal");
                        trans.commit();
                        return true;
                    }
                }
                return false;
            }
        });
        return  view;
    }


    @Override
    public void onViewCreated(View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        SharedPreferences prefe = getActivity().getSharedPreferences("datos", Context.MODE_PRIVATE);
        if (prefe.getString("Session", "").equals("iniciado")) {
            NombreCanal = prefe.getString("NombreCanal", "");
            NombrePais = prefe.getString("NombrePais", "");
        }
        //Recibiendo
        getActivity().setTitle("Canal "+NombreCanal);
        TextView txtTitulo = (TextView)getView().findViewById(R.id.txtnombreCanal_vp);
        //Tomar el ultimo pais seleccionado
        txtTitulo.setText("Total "+ NombrePais);

        //Control Canales
        ControlCanales vistaTotal_vp = (ControlCanales)getView().findViewById(R.id.vistaTotal_vp);
        vistaTotal_vp.setTitulo("63 de 104 visitas cubiertas");
        vistaTotal_vp.numerodesecciones(5);
        vistaTotal_vp.setOnLoginListener(new OnLoginListener() {
            @Override
            public void onClic() {

            }
        });
        Continuar();

    }

    public void  Continuar(){
        simpleViewFlipper_vp = (ViewFlipper)getView().findViewById(R.id.simpleViewFlipper_vp);
        //Setting
        contador_vp = (TextView)getView().findViewById(R.id.contador_vp);
        final Animation in = AnimationUtils.loadAnimation(getView().getContext(), R.anim.in_from_right);
        final Animation out = AnimationUtils.loadAnimation(getView().getContext(),  R.anim.out_to_left);

        final Animation in2 = AnimationUtils.loadAnimation(getView().getContext(), R.anim.in_from_left);
        final Animation out2 = AnimationUtils.loadAnimation(getView().getContext(),  R.anim.out_to_right);

        //Agregar Regiones
        ControlCanales control1 = new ControlCanales(getView().getContext());
        control1.setTitulo("Region 1");
        control1.numerodesecciones(5);
        simpleViewFlipper_vp.addView(control1);
        control1.setOnLoginListener(new OnLoginListener() {
            @Override
            public void onClic() {

            }
        });
        ControlCanales control2 = new ControlCanales(getView().getContext());
        control2.setTitulo("Region 2");
        control2.numerodesecciones(5);
        simpleViewFlipper_vp.addView(control2);
        control2.setOnLoginListener(new OnLoginListener() {
            @Override
            public void onClic() {

            }
        });
        //Navegacion
        final Button btnantes = (Button)getView().findViewById(R.id.BtnAntes);
        Button btndespues = (Button)getView().findViewById(R.id.BtnDespues);

        btnantes.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(flag==0) {
                    simpleViewFlipper_vp.setInAnimation(in2);
                    simpleViewFlipper_vp.setOutAnimation(out2);
                    simpleViewFlipper_vp.showPrevious();
                    contador_vp.setText((simpleViewFlipper_vp.getDisplayedChild()+1)+"/"+simpleViewFlipper_vp.getChildCount());
                    flag=1;
                    new Thread(new Runnable() {
                        public void run() {
                            //Aquí ejecutamos nuestras tareas costosas
                            try {
                                Thread.sleep(600);
                                flag = 0;

                            } catch (InterruptedException e) {
                                e.printStackTrace();
                            }

                        }
                    }).start();
                }
            }
        });

        btndespues.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(flag ==0 ) {
                    simpleViewFlipper_vp.setInAnimation(in);
                    simpleViewFlipper_vp.setOutAnimation(out);
                    simpleViewFlipper_vp.showNext();
                    contador_vp.setText((simpleViewFlipper_vp.getDisplayedChild()+1)+"/"+simpleViewFlipper_vp.getChildCount());
                    flag=1;
                    new Thread(new Runnable() {
                        public void run() {
                            //Aquí ejecutamos nuestras tareas costosas

                            try {
                                Thread.sleep(600);
                                flag = 0;

                            } catch (InterruptedException e) {
                                e.printStackTrace();
                            }

                        }
                    }).start();
                }
            }
        });
    }
}
