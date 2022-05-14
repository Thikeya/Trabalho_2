package com.example.trabalho_2.fragments;

import android.content.Intent;
import android.os.Bundle;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import com.example.trabalho_2.CadastroActivity;
import com.example.trabalho_2.R;
import com.example.trabalho_2.db.DBHelperTime;
import com.example.trabalho_2.db.Time;

public class CadastroTimeFragment extends Fragment{
    Time time, altTime;
    private EditText edtDescricao;
    private Button btnVariavel;
    private TextView edtID;

    public static CadastroTimeFragment newInstance() {
        CadastroTimeFragment fragment = new CadastroTimeFragment();
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_cadastro_time, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        Intent it = getActivity().getIntent();
        altTime = (Time) it.getSerializableExtra("chave_time");
        time = new Time();
        DBHelperTime helperTime = new DBHelperTime(getActivity());
        edtDescricao = getView().findViewById(R.id.textTimeDescricao);
        edtID = (TextView) getView().findViewById(R.id.textTimeID);
        btnVariavel = getView().findViewById(R.id.buttonTime);
        if (altTime != null) {
            ((CadastroActivity)getActivity()).navigateFragment(1);
            btnVariavel.setText("Atualizar time");
            edtDescricao.setText(altTime.getTimeDescricao());
            int id = altTime.getTimeId();
            edtID.setText(String.valueOf(id));
            time.setTimeId(altTime.getTimeId());
        }
        else {
            btnVariavel.setText("Cadastrar time!");
        }
    }
    public void onResume(){
        super.onResume();
    }

}
