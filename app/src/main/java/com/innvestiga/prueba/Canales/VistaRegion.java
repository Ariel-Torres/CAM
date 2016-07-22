package com.innvestiga.prueba.Canales;


import android.os.Bundle;
import android.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Gallery;

import com.innvestiga.prueba.R;
import com.innvestiga.prueba.UI.ControlCanales;

/**
 * A simple {@link Fragment} subclass.
 */
public class VistaRegion extends Fragment {


    public VistaRegion() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view =  inflater.inflate(R.layout.fragment_vista_region, container, false);
        return  view;

    }

}
