package com.example.trabalho_2.fragments;

import android.content.Intent;
import android.os.Bundle;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import android.view.ContextMenu;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.Toast;
import com.example.trabalho_2.CadastroActivity;
import com.example.trabalho_2.MainActivity;
import com.example.trabalho_2.R;
import com.example.trabalho_2.db.DBHelperJogador;
import com.example.trabalho_2.db.Jogador;
import java.util.ArrayList;

public class JogadorFragment extends Fragment{
    private static String selectedItem;
    public ListView listJogadores;
    View fragmentoJogador;
    Jogador jogador;
    private int id1, id2, id3;
    ArrayList<Jogador> arrayListJogadores;
    ArrayAdapter<Jogador> jogadoresArrayAdapter;

    public JogadorFragment() {
    }

    public static JogadorFragment newInstance() {
        JogadorFragment fragment = new JogadorFragment();
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_jogador, container, false);
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
    }

    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        listJogadores = (ListView) getView().findViewById(R.id.listJogadores);
        registerForContextMenu(listJogadores);
        DBHelperJogador helperJogador = new DBHelperJogador(getActivity());
        arrayListJogadores = helperJogador.selectAllJogadores();
        helperJogador.close();
        if (listJogadores != null) {
            jogadoresArrayAdapter = new ArrayAdapter<Jogador>(getActivity(), android.R.layout.simple_list_item_1, arrayListJogadores);
            listJogadores.setAdapter(jogadoresArrayAdapter);
        }
        listJogadores.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int position, long id) {
                Jogador jogadorEnviado = (Jogador) jogadoresArrayAdapter.getItem(position);
                Intent it = new Intent(getActivity(), CadastroActivity.class);
                it.putExtra("chave_jogador", jogadorEnviado);
                startActivity(it);

            }
        });

        listJogadores.setOnItemLongClickListener(new AdapterView.OnItemLongClickListener() {
            @Override
            public boolean onItemLongClick(AdapterView<?> adapterView, View view, int position, long id) {
                jogador = jogadoresArrayAdapter.getItem(position);
                return false;
            }
        });

    }
    @Override
    public void onCreateContextMenu(ContextMenu menu, View v, ContextMenu.ContextMenuInfo menuInfo) {
        MenuItem mDelete = menu.add(Menu.NONE, id1, 1, "Apague jogador");
        MenuItem mSair = menu.add(Menu.NONE, id2, 2, "Cancela");
        mDelete.setOnMenuItemClickListener(new MenuItem.OnMenuItemClickListener() {
            @Override
            public boolean onMenuItemClick(MenuItem menuItem) {
                long retornoBD;
                DBHelperJogador helperJogador = new DBHelperJogador(getActivity());
                retornoBD = helperJogador.deleteJogador(jogador);
                helperJogador.close();
                if (retornoBD == -1) {
                    alert("Erro de exclusão!");
                } else {
                    alert("Registro excluído com sucesso!");
                    reloadActivity(v);
                }
                return false;
            }
        });
        super.onCreateContextMenu(menu, v, menuInfo);
    }

    private void alert(String s) {
        Toast.makeText(getActivity(), s, Toast.LENGTH_LONG).show();
    }

    public void reloadActivity(View view) {
        Intent intent = new Intent(getActivity(), MainActivity.class);
        getActivity().finish();
        startActivity(intent);
    }

    public static String getSelectedItem() {
        return selectedItem;
    }
}
