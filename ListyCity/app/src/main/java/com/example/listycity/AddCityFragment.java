package com.example.listycity;

import android.app.Dialog;
import android.content.Context;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.EditText;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AlertDialog;
import androidx.fragment.app.DialogFragment;

public class AddCityFragment extends DialogFragment {
    interface AddCityDialogListener {
        void addCity(City city);
        void editCity(City city, int position);
    }

    private AddCityDialogListener listener;
    private City city_editted;
    private int posicion;


    @Override
    public void onAttach(@NonNull Context context) {
        super.onAttach(context);
        if (context instanceof AddCityDialogListener) {
            listener = (AddCityDialogListener) context;
        } else {
            throw new RuntimeException(context + " must implement AddCityDialogListener");
        }
    }

    @NonNull
    @Override
    public Dialog onCreateDialog(@Nullable Bundle savedInstanceState) {
        View view = getLayoutInflater().inflate(R.layout.fragment_add_city, null);
        EditText editCityName = view.findViewById(R.id.edit_text_city_text);
        EditText editProvinceName = view.findViewById(R.id.edit_text_province_text);
        AlertDialog.Builder builder = new AlertDialog.Builder(getContext());
        Bundle args = getArguments();
        if (args != null)
        {
            city_editted = (City) args.getSerializable("city");
            posicion = args.getInt("position", -1);
            if (city_editted != null)
            {
                editCityName.setText(city_editted.getName());
                editProvinceName.setText(city_editted.getProvince());
            }
        }
        String title;
        String ok_button;
        if (city_editted == null) {
            title = "Add a city";
            ok_button = "Add";
        }
        else {
            title = "Edit " + city_editted.getName();
            ok_button = "Edit";
        }
        return builder
                .setView(view)
                .setTitle(title)
                .setNegativeButton("Cancel", null)
                .setPositiveButton(ok_button, (dialog, which) -> {
                    String cityName = editCityName.getText().toString();
                    String provinceName = editProvinceName.getText().toString();
                    if (city_editted != null && posicion != -1)
                        listener.editCity(new City(cityName, provinceName), posicion);
                    else if (city_editted == null)
                        listener.addCity(new City(cityName, provinceName));
                })
                .create();
    }
}
