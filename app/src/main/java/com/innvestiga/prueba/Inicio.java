package com.innvestiga.prueba;


import android.app.FragmentTransaction;
import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.app.Fragment;
import android.os.Handler;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.Button;
import android.widget.TextView;
import android.widget.ViewFlipper;

import com.innvestiga.prueba.Canales.VistaCanal;
import com.innvestiga.prueba.UI.ControlCanales;
import com.innvestiga.prueba.UI.OnLoginListener;

import java.util.ArrayList;


/**
 * A simple {@link Fragment} subclass.
 */
public class Inicio extends Fragment {
    private ViewFlipper simpleViewFlipper;
    Bundle bundle = new Bundle();
    String TAG = "DATA";
    VistaCanal vistaCanal = new VistaCanal();
    int flag = 0;
    public Inicio() {
        // Required empty public constructor

    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_inicio, container, false);
        return view;

    }


    @Override
    public void onViewCreated(View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        SharedPreferences prefe = getActivity().getSharedPreferences("datos", Context.MODE_PRIVATE);
        if (prefe.getString("Session", "").equals("iniciado")) {
            String cliente = prefe.getString("Cliente", "");
            TextView txtnombrecliente = (TextView)getView().findViewById(R.id.txtnombreCliente);
            txtnombrecliente.setText(cliente);
            //Vista general de totales
            ControlCanales controlCanales = (ControlCanales)getView().findViewById(R.id.vistacanales);
            controlCanales.ShowTitulo(false);
            controlCanales.numerodesecciones(0);
            controlCanales.setOnLoginListener(new OnLoginListener() {
                @Override
                public void onClic() {

                }
            });
            // get The references of ViewFlipper
            simpleViewFlipper = (ViewFlipper) getView().findViewById(R.id.simpleViewFlipper); // get the reference of ViewFlipper

            final Animation in = AnimationUtils.loadAnimation(getView().getContext(), R.anim.in_from_right);
            final Animation out = AnimationUtils.loadAnimation(getView().getContext(),  R.anim.out_to_left);

            final Animation in2 = AnimationUtils.loadAnimation(getView().getContext(), R.anim.in_from_left);
            final Animation out2 = AnimationUtils.loadAnimation(getView().getContext(),  R.anim.out_to_right);
            //Control de canales

            ControlCanales canal = new ControlCanales(getView().getContext());
            canal.setTitulo("Mesa");
            canal.numerodesecciones(5);
            simpleViewFlipper.addView(canal);
            canal.setOnLoginListener(new OnLoginListener() {
                @Override
                public void onClic() {
                    android.app.FragmentManager mFragmentManager = getActivity().getFragmentManager();
                    FragmentTransaction trans = mFragmentManager.beginTransaction();
                    if (!vistaCanal.isVisible()) {
                        System.out.println("VistaCanal no visible");
                        trans.replace(R.id.container, vistaCanal, "Inicio");
                        getActivity().setTitle("Mesas");// que canal debe enviar ???
                        trans.commit();
                    }
                }
            });
            ControlCanales canal2 = new ControlCanales(getView().getContext());
            canal2.setTitulo("Self Service");
            canal2.numerodesecciones(4);
            simpleViewFlipper.addView(canal2);
            canal2.setOnLoginListener(new OnLoginListener() {
                @Override
                public void onClic() {
                    Log.i(TAG,"Toco2");
                }
            });
            ControlCanales canal3 = new ControlCanales(getView().getContext());
            canal3.setTitulo("Auto Servicio");
            canal3.numerodesecciones(4);
            simpleViewFlipper.addView(canal3);
            canal3.setOnLoginListener(new OnLoginListener() {
                @Override
                public void onClic() {
                    Log.i(TAG,"Toco3");
                }
            });
            ControlCanales canal4 = new ControlCanales(getView().getContext());
            canal4.setTitulo("CAD Call Center");
            canal4.numerodesecciones(3);
            simpleViewFlipper.addView(canal4);
            canal4.setOnLoginListener(new OnLoginListener() {
                @Override
                public void onClic() {
                    Log.i(TAG,"Toco4");
                }
            });
            ControlCanales canal5 = new ControlCanales(getView().getContext());
            canal5.setTitulo("CAD OP");
            canal5.numerodesecciones(2);
            simpleViewFlipper.addView(canal5);
            canal5.setOnLoginListener(new OnLoginListener() {
                @Override
                public void onClic() {
                    Log.i(TAG,"Toco5");
                }
            });
            ControlCanales canal6 = new ControlCanales(getView().getContext());
            canal6.setTitulo("Llevar");
            canal6.numerodesecciones(4);
            simpleViewFlipper.addView(canal6);
            canal6.setOnLoginListener(new OnLoginListener() {
                @Override
                public void onClic() {
                    Log.i(TAG,"Toco6");
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


        }else{
            //Deberia de regresar a login
        }

    }
}
