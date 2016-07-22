package com.innvestiga.prueba.Activities;

import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.android.volley.AuthFailureError;
import com.android.volley.NetworkError;
import com.android.volley.NoConnectionError;
import com.android.volley.ParseError;
import com.android.volley.Request.Method;
import com.android.volley.ServerError;
import com.android.volley.TimeoutError;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.innvestiga.prueba.Modelo.Canales;
import com.innvestiga.prueba.Modelo.Cliente;
import com.innvestiga.prueba.Modelo.Server;
import com.innvestiga.prueba.R;
import com.innvestiga.prueba.Singleton.AppSingleton;
import com.innvestiga.prueba.UI.UIToast;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

public class Login extends Activity {
    Button btnIngresar;
    EditText edtUsuario, edtPsw;
    ArrayList<Cliente> items;
    ArrayList<Canales> canales;
    private static final String TAG = "LOGIN";
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        //StrictMode.enableDefaults();
        overridePendingTransition(R.anim.left_in,R.anim.left_in);
        final Context context = Login.this;
        //INSTANCIA A CONTROLES
        btnIngresar     =   (Button)findViewById(R.id.btnIngresar       );
        edtUsuario      =   (EditText)findViewById(R.id.txtUsuario      );
        edtPsw          =   (EditText)findViewById(R.id.txtContraseña   );
        //EVENTOS
        btnIngresar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //Validaciones
                if (validar()) {
                    final ProgressDialog pDialog = new ProgressDialog(Login.this);
                    pDialog.setMessage(getResources().getString(R.string.IniciarSesion));
                    pDialog.setCancelable(false);
                    pDialog.show();
                    StringRequest myReq = new StringRequest(Method.POST,
                            Server.serverUrl+""+Server.Login,
                            new com.android.volley.Response.Listener<String>() {
                                @Override
                                public void onResponse(String response) {
                                    response = response.trim();
                                    Log.i(TAG,response);
                                    //Recibiendo del servidor
                                    String datos[] = response.split("\\*");
                                    if (datos[0].equals("1")) {
                                        JSONObject obj = null;
                                        try {
                                            obj = new JSONObject(datos[1]);
                                            items = parseJson(obj);// Carga todos los productos que tiene el cliente.
                                            //POR AHORA SE TOMARA ESI COMO DEFECTO
                                            Log.i(TAG, items.get(0).getNOMBRE_PRODUCTO());
                                            if (items.size()>0 && items.get(0).getNOMBRE_PRODUCTO().equals("ESI")) {
                                                SharedPreferences preferencias = getSharedPreferences("datos", Context.MODE_PRIVATE);
                                                SharedPreferences.Editor editor = preferencias.edit();
                                                editor.putString("Session", "iniciado");
                                                editor.putString("Usuario", items.get(0).getUSUARIO());
                                                editor.putString("Activo", items.get(0).getACTIVO());
                                                editor.putString("Base", items.get(0).getBASE());
                                                editor.putString("Logo", items.get(0).getLOGO());
                                                editor.putString("Cliente", items.get(0).getCLIENTE());
                                                editor.commit();
                                                //Charge the Channels from Service Canales.php
                                                pDialog.setMessage("Cargando sus canales...");
                                                pDialog.setCancelable(false);
                                                StringRequest myReq = new StringRequest(Method.POST,
                                                        Server.serverUrl+""+Server.Canal,
                                                        new com.android.volley.Response.Listener<String>() {
                                                            @Override
                                                            public void onResponse(String response) {
                                                                response = response.trim();
                                                                Log.i(TAG,response);
                                                                //Recibiendo del servidor
                                                                String datos[] = response.split("\\*");
                                                                if (datos[0].equals("1")) {
                                                                    JSONObject obj = null;
                                                                    try {
                                                                        obj = new JSONObject(datos[1]);
                                                                        canales = parseJsonCanales(obj);// Carga todos los canales del cliente
                                                                        Log.i(TAG, ""+canales.size());
                                                                        if(canales.size()>0){
                                                                            ArrayList<String> nombreCanales = new ArrayList<String>();
                                                                            for(int i=0; i<canales.size(); i++){
                                                                                nombreCanales.add(canales.get(i).getNombre());
                                                                            }
                                                                            startActivity(new Intent(Login.this, MainActivity.class).putStringArrayListExtra("Canales",nombreCanales));
                                                                            finish();
                                                                        }else{

                                                                        }
                                                                    } catch (JSONException e) {
                                                                        e.printStackTrace();
                                                                    }
                                                                } else {
                                                                    pDialog.dismiss();
                                                                    //Error controlado
                                                                    UIToast toast = new UIToast(context, Toast.LENGTH_LONG);
                                                                    toast.show(datos[1]);
                                                                }
                                                            }
                                                        },
                                                        new com.android.volley.Response.ErrorListener() {
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
                                                        }) {
                                                    protected Map<String, String> getParams() throws com.android.volley.AuthFailureError {
                                                        Map<String, String> params = new HashMap<String, String>();
                                                        params.put("BASE", items.get(0).getBASE());
                                                        return params;
                                                    }
                                                };
                                                AppSingleton.getInstance(context).addToRequestQueue(myReq);
                                            } else {
                                                pDialog.dismiss();
                                                UIToast toast = new UIToast(Login.this, Toast.LENGTH_LONG);
                                                toast.show(getResources().getString(R.string.NoProductos));
                                            }
                                        } catch (JSONException e) {
                                            e.printStackTrace();
                                        }
                                    } else {
                                        pDialog.dismiss();
                                        //Error controlado
                                        UIToast toast = new UIToast(Login.this, Toast.LENGTH_LONG);
                                        toast.show(datos[1]);
                                    }
                                }
                            },
                            new com.android.volley.Response.ErrorListener() {
                                @Override
                                public void onErrorResponse(VolleyError error) {
                                    pDialog.dismiss();
                                    if (error instanceof TimeoutError || error instanceof NoConnectionError) {
                                        Toast.makeText(Login.this,
                                                Login.this.getString(R.string.error_network_timeout),
                                                Toast.LENGTH_LONG).show();
                                    } else if (error instanceof AuthFailureError) {
                                        Toast.makeText(Login.this,
                                                Login.this.getString(R.string.error_auth_fail),
                                                Toast.LENGTH_LONG).show();
                                    } else if (error instanceof ServerError) {
                                        Toast.makeText(Login.this,
                                                Login.this.getString(R.string.error_server),
                                                Toast.LENGTH_LONG).show();
                                    } else if (error instanceof NetworkError) {
                                        Toast.makeText(Login.this,
                                                Login.this.getString(R.string.error_network),
                                                Toast.LENGTH_LONG).show();
                                    } else if (error instanceof ParseError) {
                                        Toast.makeText(Login.this,
                                                Login.this.getString(R.string.error_no_id) + " "+ error,
                                                Toast.LENGTH_LONG).show();
                                    }
                                }
                            }) {
                        protected Map<String, String> getParams() throws com.android.volley.AuthFailureError {
                            Map<String, String> params = new HashMap<String, String>();
                            params.put("USUARIO", edtUsuario.getText().toString().trim());
                            params.put("PSW", edtPsw.getText().toString().trim());
                            return params;
                        }
                    };
                    AppSingleton.getInstance(Login.this).addToRequestQueue(myReq);
                }
            }
        });
    }

    public boolean validar(){
        boolean valido = true;
            if(edtUsuario.getText().toString().equals("") || edtPsw.getText().toString().equals("")){
                UIToast toast = new UIToast(this, Toast.LENGTH_LONG);
                toast.show(getResources().getString(R.string.completar_campos));
                valido =false;
            }
        return valido;

    }
    //Clase parseadora de JSON
    public ArrayList<Cliente> parseJson(JSONObject jsonObject) {
        // Variables locales
        ArrayList<Cliente> cliente = new ArrayList<>();
        JSONArray jsonArray = null;

        try {
            // Obtener el array del objeto
            jsonArray = jsonObject.getJSONArray("Cliente");

            for (int i = 0; i < jsonArray.length(); i++) {
                try {
                    JSONObject objeto = jsonArray.getJSONObject(i);

                    Cliente clt = new Cliente(
                            objeto.getString("ID_USUARIO"),
                            objeto.getString("USUARIO"),
                            objeto.getString("ACTIVO"),
                            objeto.getString("ID_PRODUCTO"),
                            objeto.getString("NOMBRE_PRODUCTO"),
                            objeto.getString("BASE"),
                            objeto.getString("LOGO"),
                            objeto.getString("NOMBRE_CLIENTE"));

                    cliente.add(clt);


                } catch (JSONException e) {
                    Log.e(TAG, "Error de parsing json: " + e.getMessage());
                }
            }

        } catch (JSONException e) {
            e.printStackTrace();
        }
        return cliente;
    }
    //Clase parseadora de JSON
    public ArrayList<Canales> parseJsonCanales(JSONObject jsonObject) {
        // Variables locales
        ArrayList<Canales> canales = new ArrayList<>();
        JSONArray jsonArray = null;

        try {
            // Obtener el array del objeto
            jsonArray = jsonObject.getJSONArray("Canales");

            for (int i = 0; i < jsonArray.length(); i++) {
                try {
                    JSONObject objeto = jsonArray.getJSONObject(i);

                    Canales clt = new Canales(
                            objeto.getString("ID_NOMENCLATURA"),
                            objeto.getString("CANAL"),
                            objeto.getString("NOMBRE"));

                    canales.add(clt);


                } catch (JSONException e) {
                    Log.e(TAG, "Error de parsing json: " + e.getMessage());
                }
            }

        } catch (JSONException e) {
            e.printStackTrace();
        }
        return canales;
    }
}

