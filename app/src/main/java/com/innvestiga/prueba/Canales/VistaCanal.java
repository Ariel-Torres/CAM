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

public class VistaCanal extends Fragment {
    VistaPais vistaPais;
    TextView contador_vc;
    String TAG = "VistaPais",NombreCanal;
    Bundle bundle = new Bundle();
    int flag = 0;
    ViewFlipper simpleViewFlipper;
    public VistaCanal() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View v =  inflater.inflate(R.layout.fragment_vista_canal, container, false);
        //Back pressed Logic for fragment
        v.setFocusableInTouchMode(true);
        v.requestFocus();
        v.setOnKeyListener(new View.OnKeyListener() {
            @Override
            public boolean onKey(View v, int keyCode, KeyEvent event) {
                if (event.getAction() == KeyEvent.ACTION_DOWN) {
                    if (keyCode == KeyEvent.KEYCODE_BACK) {
                        Log.i(TAG,"atras");
                        Inicio inicio = new Inicio();
                        FragmentTransaction trans = getActivity().getFragmentManager().beginTransaction();
                        trans.replace(R.id.container, inicio, "Inicio");
                        trans.commit();
                        return true;
                    }
                }
                return false;
            }
        });
        return v;
    }

    @Override
    public void onViewCreated(View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        //agregar todo lo que se necesista para cocntrolar el view
        vistaPais = new VistaPais();
        TextView txtCanal  =  (TextView)getView().findViewById(R.id.txtnombreCanal_vc);
        //Llamar a Prefererences los datos de canal
        SharedPreferences prefe = getActivity().getSharedPreferences("datos", Context.MODE_PRIVATE);
        if (prefe.getString("Session", "").equals("iniciado")) {
            NombreCanal = prefe.getString("NombreCanal", "");
        }

        getActivity().setTitle("Canal "+NombreCanal);
        //RECIBIENDO
        txtCanal.setText("Total "+ NombreCanal);
        ControlCanales controlCanalTotal = (ControlCanales)getView().findViewById(R.id.vistaTotal_vc);
        controlCanalTotal.numerodesecciones(5);
        controlCanalTotal.setTitulo("25 de 25 visitas cubiertas");
        controlCanalTotal.setOnLoginListener(new OnLoginListener() {
            @Override
            public void onClic() {

            }
        });
        Continuar();
    }
    public void Continuar(){
        // get The references of ViewFlipper
        simpleViewFlipper = (ViewFlipper) getView().findViewById(R.id.simpleViewFlipper_vc); // get the reference of ViewFlipper
        contador_vc = (TextView)getView().findViewById(R.id.contador_vc);
        final Animation in = AnimationUtils.loadAnimation(getView().getContext(), R.anim.in_from_right);
        final Animation out = AnimationUtils.loadAnimation(getView().getContext(),  R.anim.out_to_left);

        final Animation in2 = AnimationUtils.loadAnimation(getView().getContext(), R.anim.in_from_left);
        final Animation out2 = AnimationUtils.loadAnimation(getView().getContext(),  R.anim.out_to_right);
        //Control de canales

        ControlCanales canal = new ControlCanales(getView().getContext());
        canal.setTitulo("Guatemala");
        canal.numerodesecciones(5);
        simpleViewFlipper.addView(canal);
        canal.setOnLoginListener(new OnLoginListener() {
            @Override
            public void onClic() {
                //Cargar en SharedPreferences los datos para que puedan leerse desde cualquier lugar
                SharedPreferences preferencias = getActivity().getSharedPreferences("datos", Context.MODE_PRIVATE);
                SharedPreferences.Editor editor = preferencias.edit();
                editor.putString("NombrePais", "Guatemala");
                editor.commit();
                //----------------------------------------------------------------------------------
                android.app.FragmentManager mFragmentManager = getActivity().getFragmentManager();
                FragmentTransaction trans = mFragmentManager.beginTransaction();
                if (!vistaPais.isVisible()) {
                    System.out.println("VistaPais no visible");
                    trans.replace(R.id.container, vistaPais, "Pais");
                    getActivity().setTitle("Pais");// que canal debe enviar ???
                    trans.commit();
                }
            }
        });
        ControlCanales canal2 = new ControlCanales(getView().getContext());
        canal2.setTitulo("El Salvador");
        canal2.numerodesecciones(5);
        simpleViewFlipper.addView(canal2);
        canal2.setOnLoginListener(new OnLoginListener() {
            @Override
            public void onClic() {
                //Cargar en SharedPreferences los datos para que puedan leerse desde cualquier lugar
                SharedPreferences preferencias = getActivity().getSharedPreferences("datos", Context.MODE_PRIVATE);
                SharedPreferences.Editor editor = preferencias.edit();
                editor.putString("NombrePais", "El Salvador");
                editor.commit();
                //----------------------------------------------------------------------------------
                android.app.FragmentManager mFragmentManager = getActivity().getFragmentManager();
                FragmentTransaction trans = mFragmentManager.beginTransaction();
                if (!vistaPais.isVisible()) {
                    System.out.println("VistaPais no visible");
                    trans.replace(R.id.container, vistaPais, "Pais");
                    getActivity().setTitle("Pais");// que canal debe enviar ???
                    trans.commit();
                }
            }
        });
        final Button btnantes = (Button)getView().findViewById(R.id.BtnAntes);
        Button btndespues = (Button)getView().findViewById(R.id.BtnDespues);

        btnantes.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(flag==0) {
                    simpleViewFlipper.setInAnimation(in2);
                    simpleViewFlipper.setOutAnimation(out2);
                    simpleViewFlipper.showPrevious();
                    contador_vc.setText((simpleViewFlipper.getDisplayedChild()+1)+"/"+simpleViewFlipper.getChildCount());
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
                    simpleViewFlipper.setInAnimation(in);
                    simpleViewFlipper.setOutAnimation(out);
                    simpleViewFlipper.showNext();
                    contador_vc.setText((simpleViewFlipper.getDisplayedChild()+1)+"/"+simpleViewFlipper.getChildCount());
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
