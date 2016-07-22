package com.innvestiga.prueba.UI;

import android.content.Context;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import com.innvestiga.prueba.R;

/**
 * Created by ariel on 13/07/2016.
 */
public class UIToast extends Toast {
    private Context context;


    public UIToast(Context cont, int duration) {

        super(cont);
        context = cont;
        this.setDuration(duration);
        this.setGravity(Gravity.TOP, 0, 300);
    }

    public void show(CharSequence text) {
        LayoutInflater li = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);

        View vi = (View) li.inflate(R.layout.toast, null);

        TextView tv = (TextView) vi.findViewById(R.id.text_toast);

        this.setView(vi);

        tv.setText(text);

        super.show();

    }
}
