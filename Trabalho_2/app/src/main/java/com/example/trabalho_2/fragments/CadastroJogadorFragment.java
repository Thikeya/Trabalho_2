package com.example.trabalho_2.fragments;

import android.content.Intent;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.TextView;
import com.example.trabalho_2.CadastroActivity;
import com.example.trabalho_2.R;
import com.example.trabalho_2.db.DBHelperTime;
import com.example.trabalho_2.db.DBHelperJogador;
import com.example.trabalho_2.db.Jogador;
import com.example.trabalho_2.db.Time;

import java.util.ArrayList;

public class CadastroJogadorFragment extends Fragment {
    private static String selectedItem;
    public ListView listTimes;
    private EditText edtNome, edtCpf, edtAnoNascimento;
    private TextView edtID;
    private Button btnVariavel;
    View fragmentoJogador;
    Jogador jogador, altJogador;
    ArrayList<Time> arrayListTime;
    ArrayAdapter<Time> timeArrayAdapter;

    public CadastroJogadorFragment() {
    }

    public static CadastroJogadorFragment newInstance() {
        CadastroJogadorFragment fragment = new CadastroJogadorFragment();
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        fragmentoJogador = inflater.inflate(R.layout.fragment_cadastro_jogador, container, false);
        return fragmentoJogador;
    }

    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        Intent it = getActivity().getIntent();
        altJogador = (Jogador) it.getSerializableExtra("chave_jogador");
        jogador = new Jogador();
        DBHelperJogador helperJogador = new DBHelperJogador(getActivity());
        edtNome = getView().findViewById(R.id.textJogadorNome);
        edtCpf = getView().findViewById(R.id.textJogadorCpf);
        edtAnoNascimento = getView().findViewById(R.id.textJogadorAnoNascimento);
        edtID = (TextView) getView().findViewById(R.id.textJogadorID);
        btnVariavel = getView().findViewById(R.id.buttonJogador);
        listTimes = (ListView) getView().findViewById(R.id.listTimes);
        registerForContextMenu(listTimes);
        DBHelperTime helperTime = new DBHelperTime(getActivity());
        arrayListTime = helperTime.selectAllTimes();
        helperTime.close();
        if (listTimes != null) {
            timeArrayAdapter = new ArrayAdapter<Time>(getActivity(), android.R.layout.simple_list_item_1, arrayListTime);
            listTimes.setAdapter(timeArrayAdapter);
        }
        listTimes.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                selectedItem = listTimes.getItemAtPosition(i).toString();
            }
        });

        if (altJogador != null) {
            ((CadastroActivity)getActivity()).navigateFragment(0);
            btnVariavel.setText("Atualizar jogador");
            edtNome.setText(altJogador.getJogadorNome());
            edtCpf.setText(altJogador.getJogadorCpf());
            edtAnoNascimento.setText(altJogador.getJogadorAnoNascimento());
            int id = altJogador.getJogadorId();
            edtID.setText(String.valueOf(id));
            jogador.setJogadorId(altJogador.getJogadorId());
        }
        else {
            btnVariavel.setText("Cadastrar Jogador!");
        }
    }

    public static String getSelectedItem() {
        return selectedItem;
    }

    public static void setSelectedItemNull(){
        selectedItem = null;
    }

}
