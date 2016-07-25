package com.innvestiga.prueba.Activities;


import android.app.FragmentManager;
import android.app.FragmentTransaction;
import android.content.Context;
import android.content.SharedPreferences;
import android.graphics.Bitmap;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.os.SystemClock;
import android.util.Log;
import android.util.SparseIntArray;
import android.view.KeyEvent;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageView;

import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.ImageRequest;
import com.innvestiga.prueba.Canales.VistaCanal;
import com.innvestiga.prueba.Canales.VistaRegion;
import com.innvestiga.prueba.Inicio;
import com.innvestiga.prueba.Modelo.Server;
import com.innvestiga.prueba.R;
import com.innvestiga.prueba.Singleton.AppSingleton;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import br.liveo.interfaces.NavigationLiveoListener;
import br.liveo.navigationliveo.NavigationLiveo;

public class MainActivity extends NavigationLiveo implements NavigationLiveoListener {

    List<String> mListNameItem = new ArrayList<>();
    android.app.FragmentManager mFragmentManager = getFragmentManager();
    private static final String TAG = "LOGO";
    Bundle bundle = new Bundle();
    //Fragments
    Inicio inicio;
    VistaCanal vistaCanal;
    ArrayList<String> menu;
    Context context = MainActivity.this;
    String Base;
    @Override
    public void onUserInformation() {
        //Obtener los canales del menu
        menu= getIntent().getExtras().getStringArrayList("Canales");
        //Obtener la informacion del cliente
        View mCustomHeader = getLayoutInflater().inflate(R.layout.header_personalizado, this.getListView(), false);
        final ImageView imageView = (ImageView) mCustomHeader.findViewById(R.id.logoCliente);
        //Verificar que haya iniciado sesion
        SharedPreferences prefe = getSharedPreferences("datos", Context.MODE_PRIVATE);
        if (prefe.getString("Session", "").equals("iniciado")) {
            String urlLogo= prefe.getString("Logo", "");
             Base = prefe.getString("Base","");
            final String Usuario = prefe.getString("Usuario","");
            // Petición para obtener la imagen
            ImageRequest request = new ImageRequest(
                    Server.ServerLogo+urlLogo,
                    new Response.Listener<Bitmap>() {
                        @Override
                        public void onResponse(Bitmap bitmap) {
                            imageView.setImageBitmap(bitmap);
                        }
                    }, 0, 0, null,null,
                    new Response.ErrorListener() {
                        public void onErrorResponse(VolleyError error) {
                            imageView.setImageResource(R.drawable.top_vectorized2);
                            Log.d(TAG, "Error en respuesta Bitmap: "+ error.getMessage());
                        }
                    }){
                protected Map<String, String> getParams() throws com.android.volley.AuthFailureError {
                    Map<String, String> params = new HashMap<String, String>();
                    params.put("BASE", Base);
                    params.put("USUARIO",Usuario);
                    return params;
                }
            };
            // Añadir petición a la cola
            AppSingleton.getInstance(MainActivity.this).addToRequestQueue(request);
        }
        this.addCustomHeader(mCustomHeader);
    }

    @Override
    public void onInt(Bundle bundle) {
        //Creation of the list items is here
        // set listener {required}
        this.setNavigationListener(this);
        List<Integer> mListIconItem = new ArrayList<>();
        mListIconItem.add(0, 0);
        mListNameItem.add(0, "");//header
        mListIconItem.add(1, R.drawable.ic_home_black_24dp);
        mListNameItem.add(1, getString(R.string.inicio));//Agregar el inicio
        if(menu.size()>0) {
            for (int i = 0; i < menu.size(); i++) {
                mListNameItem.add(i + 2, menu.get(i));
                mListIconItem.add(i + 2, 0); //Item no icon set 0
            }
        }
        //{optional} - Among the names there is some subheader, you must indicate it here
        List<Integer> mListHeaderItem = new ArrayList<>();
        mListHeaderItem.add(0);
        // mListHeaderItem.add(4);

        //{optional} - Among the names there is any item counter, you must indicate it (position) and the value here
        SparseIntArray mSparseCounterItem = new SparseIntArray(); //indicate all items that have a counter
        mSparseCounterItem.put(0, 3);
        //mSparseCounterItem.put(6, 250);

        //If not please use the FooterDrawer use the setFooterVisible(boolean visible) method with value false
        this.setFooterInformationDrawer(R.string.cerrar_sesion, R.drawable.key);

        this.setNavigationAdapter(mListNameItem, mListIconItem, mListHeaderItem, mSparseCounterItem);

    }



    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        //getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        //if (id == R.id.action_settings) {
          //  return true;
        //}

        return super.onOptionsItemSelected(item);
    }

    @Override //The "layoutContainerId" should be used in "beginTransaction (). Replace"
    public void onItemClickNavigation(int position, int layoutContainerId) {
        inicio = new Inicio();
        vistaCanal= new VistaCanal();
        FragmentTransaction trans = mFragmentManager.beginTransaction();
        android.support.v7.app.ActionBar bar = getSupportActionBar();
        switch (position) {
            case 1:
                if (bar != null) {
                    bar.setBackgroundDrawable(new ColorDrawable(getResources().getColor(R.color.colorPrimary)));
                    bar.setDisplayShowTitleEnabled(false);
                    bar.setDisplayShowTitleEnabled(true);
                }
                if (!inicio.isVisible()) {
                    System.out.println("INICIO no visible");
                    trans.replace(layoutContainerId, inicio, "Inicio");
                }
                setTitle(getString(R.string.inicio));
                break;
            case 2:
                if (bar != null) {
                    bar.setBackgroundDrawable(new ColorDrawable(getResources().getColor(R.color.colorPrimary)));
                    bar.setDisplayShowTitleEnabled(false);
                    bar.setDisplayShowTitleEnabled(true);
                }
                if (!vistaCanal.isVisible()) {
                    bundle.putString("NombreCanal","Mesas");
                    System.out.println("VistaCanal no visible");
                    vistaCanal.setArguments(bundle);
                    trans.replace(layoutContainerId, vistaCanal, "VistaCanal");
                }
                setTitle(mListNameItem.get(position));
                break;
        }
        trans.commit();
    }

    @Override
    public void onPrepareOptionsMenuNavigation(Menu menu, int i, boolean b) {

    }

    @Override
    public void onClickFooterItemNavigation(View view) {
        onBackPressed();
    }

    @Override
    public void onClickUserPhotoNavigation(View view) {

    }

}
