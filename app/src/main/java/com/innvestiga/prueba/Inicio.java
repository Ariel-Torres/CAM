package com.innvestiga.prueba;


import android.app.FragmentTransaction;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;
import android.widget.ViewFlipper;

import com.android.volley.AuthFailureError;
import com.android.volley.NetworkError;
import com.android.volley.NoConnectionError;
import com.android.volley.ParseError;
import com.android.volley.Request.Method;
import com.android.volley.Response;
import com.android.volley.ServerError;
import com.android.volley.TimeoutError;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.innvestiga.prueba.Canales.VistaCanal;
import com.innvestiga.prueba.Modelo.Server;
import com.innvestiga.prueba.Modelo.Total;
import com.innvestiga.prueba.Singleton.AppSingleton;
import com.innvestiga.prueba.UI.ControlCanales;
import com.innvestiga.prueba.UI.OnLoginListener;
import com.innvestiga.prueba.UI.UIToast;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.HashMap;
import java.util.Map;


/**
 * A simple {@link Fragment} subclass.
 */
public class Inicio extends Fragment {
    private ViewFlipper simpleViewFlipper;
    ArrayList<Total> totalfinal;
    Bundle bundle = new Bundle();
    String TAG = "DATA",Base;
    TextView txtcontadorinicial;
    VistaCanal vistaCanal;
    int flag = 0;
    ControlCanales controlCanales;
    Context context;
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
    public  String getFecha(){
        Calendar c = Calendar.getInstance();
        System.out.println("Current time => " + c.getTime());

        SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd");
        String formattedDate = df.format(c.getTime());
        Log.i(TAG,"Enviando Fecha: " + formattedDate);
        return  formattedDate;
    }


    @Override
    public void onViewCreated(View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        Log.i("COUNT",""+getFragmentManager().getBackStackEntryCount());
        vistaCanal = new VistaCanal();
        context = getActivity().getBaseContext();
        controlCanales = (ControlCanales)getView().findViewById(R.id.vistacanales);
        controlCanales.numerodesecciones(0);
        SharedPreferences prefe = getActivity().getSharedPreferences("datos", Context.MODE_PRIVATE);
        if (prefe.getString("Session", "").equals("iniciado")) {
            String cliente = prefe.getString("Cliente", "");
            Base = prefe.getString("Base","");
            TextView txtnombrecliente = (TextView)getView().findViewById(R.id.txtnombreCliente);
            txtnombrecliente.setText("Total Canales");

            //Llamada a servicio Total General
            final ProgressDialog pDialog = new ProgressDialog(getActivity());
            pDialog.setMessage(getResources().getString(R.string.cargandoTotal));
            pDialog.setCancelable(false);
            pDialog.show();
            StringRequest request = new StringRequest(Method.POST,
                    Server.serverUrl + Server.TotalGeneral,
                    new Response.Listener<String>() {
                        @Override
                        public void onResponse(String response) {
                            Log.i(TAG,"RESPUESTA: " + response);
                            response = response.trim();
                            String[] Datos = response.split("\\*");
                            if(Datos.length < 2 ){
                                UIToast toast = new UIToast(context, Toast.LENGTH_LONG);
                                toast.show("No se pudo leer la información del servidor");
                                return;
                            }
                            if(Datos[0].equals("1")){
                                //Respuesta correcta de json
                                JSONObject obj = null;
                                try {
                                    obj = new JSONObject(Datos[1]);
                                    totalfinal = parseJson(obj);
                                    pDialog.dismiss();
                                    if(totalfinal.size()>0){
                                        //Vista general de totales
                                        controlCanales.ShowTitulo(true);
                                        controlCanales.setTitulo(totalfinal.get(0).getVisitas());
                                        controlCanales.setTotal(controlCanales.OrdenarTotal(totalfinal.get(0).getTotal(),totalfinal.get(0).getAño()));
                                        controlCanales.setOnLoginListener(new OnLoginListener() {
                                            @Override
                                            public void onClic() {

                                            }
                                        });
                                        Continuar();
                                    }
                                } catch (JSONException e) {
                                    e.printStackTrace();
                                }
                            }else{
                                //Error controlado
                                pDialog.dismiss();
                                UIToast toast = new UIToast(context, Toast.LENGTH_LONG);
                                toast.show(Datos[1]);
                            }
                        }
                    }, new Response.ErrorListener() {
                @Override
                public void onErrorResponse(VolleyError error) {
                    pDialog.dismiss();
                    if (error instanceof TimeoutError || error instanceof NoConnectionError) {
                        Toast.makeText(context,
                                context.getString(R.string.error_network_timeout),
                                Toast.LENGTH_LONG).show();
                    } else if (error instanceof AuthFailureError) {
                        Toast.makeText(context,
                                context.getString(R.string.error_auth_fail),
                                Toast.LENGTH_LONG).show();
                    } else if (error instanceof ServerError) {
                        Toast.makeText(context,
                                context.getString(R.string.error_server),
                                Toast.LENGTH_LONG).show();
                    } else if (error instanceof NetworkError) {
                        Toast.makeText(context,
                                context.getString(R.string.error_network),
                                Toast.LENGTH_LONG).show();
                    } else if (error instanceof ParseError) {
                        Toast.makeText(context,
                                context.getString(R.string.error_no_id) + " "+ error,
                                Toast.LENGTH_LONG).show();
                    }
                }
            }){
                @Override
                protected Map<String, String> getParams() throws AuthFailureError {
                    Map<String, String> params = new HashMap<String, String>();
                    params.put("FECHA",getFecha());
                    params.put("AO","2016");
                    params.put("MES","07");
                    params.put("ACT","1");
                    params.put("BASE",Base);
                    params.put("CANAL","total");
                    return params;
                }
            };
            AppSingleton.getInstance(context).addToRequestQueue(request);


        }else{
            //Deberia de regresar a login
        }

    }

    public void Continuar(){
        // get The references of ViewFlipper
        simpleViewFlipper = (ViewFlipper) getView().findViewById(R.id.simpleViewFlipper); // get the reference of ViewFlipper
        txtcontadorinicial = (TextView)getView().findViewById(R.id.txtcontadorinicial);
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
                    //Cargar en SharedPreferences los datos para que puedan leerse desde cualquier lugar
                    SharedPreferences preferencias = getActivity().getSharedPreferences("datos", Context.MODE_PRIVATE);
                    SharedPreferences.Editor editor = preferencias.edit();
                    editor.putString("NombreCanal", "Mesas");
                    editor.commit();
                    //----------------------------------------------------------------------------------
                    System.out.println("VistaCanal no visible");
                    trans.replace(R.id.container, vistaCanal, "VistaCanal");
                    getActivity().setTitle("Canal Mesas");// que canal debe enviar ???
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
                //Cargar en SharedPreferences los datos para que puedan leerse desde cualquier lugar
                SharedPreferences preferencias = getActivity().getSharedPreferences("datos", Context.MODE_PRIVATE);
                SharedPreferences.Editor editor = preferencias.edit();
                editor.putString("NombreCanal", "Self Service");
                editor.commit();
                //----------------------------------------------------------------------------------
                android.app.FragmentManager mFragmentManager = getActivity().getFragmentManager();
                FragmentTransaction trans = mFragmentManager.beginTransaction();
                if (!vistaCanal.isVisible()) {
                    System.out.println("VistaCanal no visible");
                    trans.replace(R.id.container, vistaCanal, "VistaCanal");
                    trans.commit();
                }
            }
        });
        ControlCanales canal3 = new ControlCanales(getView().getContext());
        canal3.setTitulo("Auto Servicio");
        canal3.numerodesecciones(4);
        simpleViewFlipper.addView(canal3);
        canal3.setOnLoginListener(new OnLoginListener() {
            @Override
            public void onClic() {
                //Cargar en SharedPreferences los datos para que puedan leerse desde cualquier lugar
                SharedPreferences preferencias = getActivity().getSharedPreferences("datos", Context.MODE_PRIVATE);
                SharedPreferences.Editor editor = preferencias.edit();
                editor.putString("NombreCanal", "Auto Servicio");
                editor.commit();
                //----------------------------------------------------------------------------------
                android.app.FragmentManager mFragmentManager = getActivity().getFragmentManager();
                FragmentTransaction trans = mFragmentManager.beginTransaction();
                if (!vistaCanal.isVisible()) {
                    System.out.println("VistaCanal no visible");
                    trans.replace(R.id.container, vistaCanal, "VistaCanal");
                    trans.commit();
                }
            }
        });
        ControlCanales canal4 = new ControlCanales(getView().getContext());
        canal4.setTitulo("CAD Call Center");
        canal4.numerodesecciones(3);
        simpleViewFlipper.addView(canal4);
        canal4.setOnLoginListener(new OnLoginListener() {
            @Override
            public void onClic() {
                //Cargar en SharedPreferences los datos para que puedan leerse desde cualquier lugar
                SharedPreferences preferencias = getActivity().getSharedPreferences("datos", Context.MODE_PRIVATE);
                SharedPreferences.Editor editor = preferencias.edit();
                editor.putString("NombreCanal", "CAD Call Center");
                editor.commit();
                //----------------------------------------------------------------------------------
                android.app.FragmentManager mFragmentManager = getActivity().getFragmentManager();
                FragmentTransaction trans = mFragmentManager.beginTransaction();
                if (!vistaCanal.isVisible()) {
                    System.out.println("VistaCanal no visible");
                    trans.replace(R.id.container, vistaCanal, "VistaCanal");
                    trans.commit();
                }
            }
        });
        ControlCanales canal5 = new ControlCanales(getView().getContext());
        canal5.setTitulo("CAD OP");
        canal5.numerodesecciones(2);
        simpleViewFlipper.addView(canal5);
        canal5.setOnLoginListener(new OnLoginListener() {
            @Override
            public void onClic() {
                //Cargar en SharedPreferences los datos para que puedan leerse desde cualquier lugar
                SharedPreferences preferencias = getActivity().getSharedPreferences("datos", Context.MODE_PRIVATE);
                SharedPreferences.Editor editor = preferencias.edit();
                editor.putString("NombreCanal", "CAD OP");
                editor.commit();
                //----------------------------------------------------------------------------------
                android.app.FragmentManager mFragmentManager = getActivity().getFragmentManager();
                FragmentTransaction trans = mFragmentManager.beginTransaction();
                if (!vistaCanal.isVisible()) {
                    System.out.println("VistaCanal no visible");
                    trans.replace(R.id.container, vistaCanal, "VistaCanal");
                    trans.commit();
                }

            }
        });
        ControlCanales canal6 = new ControlCanales(getView().getContext());
        canal6.setTitulo("Llevar");
        canal6.numerodesecciones(4);
        simpleViewFlipper.addView(canal6);
        canal6.setOnLoginListener(new OnLoginListener() {
            @Override
            public void onClic() {
                //Cargar en SharedPreferences los datos para que puedan leerse desde cualquier lugar
                SharedPreferences preferencias = getActivity().getSharedPreferences("datos", Context.MODE_PRIVATE);
                SharedPreferences.Editor editor = preferencias.edit();
                editor.putString("NombreCanal", "Llevar");
                editor.commit();
                //----------------------------------------------------------------------------------
                android.app.FragmentManager mFragmentManager = getActivity().getFragmentManager();
                FragmentTransaction trans = mFragmentManager.beginTransaction();
                if (!vistaCanal.isVisible()) {
                    System.out.println("VistaCanal no visible");
                    trans.replace(R.id.container, vistaCanal, "VistaCanal");
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
                    txtcontadorinicial.setText((simpleViewFlipper.getDisplayedChild()+1)+"/"+simpleViewFlipper.getChildCount());
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
                    txtcontadorinicial.setText((simpleViewFlipper.getDisplayedChild()+1)+"/"+simpleViewFlipper.getChildCount());
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

    //Clase parseadora de JSON
    public ArrayList<Total> parseJson(JSONObject jsonObject) {
        // Variables locales
        ArrayList<Total> total = new ArrayList<>();
        JSONArray jsonArray = null;

        try {
            // Obtener el array del objeto
            jsonArray = jsonObject.getJSONArray("General");

            for (int i = 0; i < jsonArray.length(); i++) {
                try {
                    JSONObject objeto = jsonArray.getJSONObject(i);

                    Total clt = new Total(
                            objeto.getString("TOTAL"),
                            objeto.getString("Año"),
                            objeto.getString("Visitas"));

                    total.add(clt);


                } catch (JSONException e) {
                    Log.e(TAG, "Error de parsing json: " + e.getMessage());
                }
            }

        } catch (JSONException e) {
            e.printStackTrace();
        }
        return total;
    }

}
