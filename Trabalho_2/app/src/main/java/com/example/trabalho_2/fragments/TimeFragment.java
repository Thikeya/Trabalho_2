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
import com.example.trabalho_2.db.DBHelperTime;
import com.example.trabalho_2.db.DBHelperJogador;
import com.example.trabalho_2.db.Time;
import com.example.trabalho_2.R;
import java.util.ArrayList;

public class TimeFragment extends Fragment {
    private static String selectedItem;
    public ListView listTimes;
    Time time;
    private int id1, id2;
    ArrayList<Time> arrayListTime;
    ArrayAdapter<Time> timeArrayAdapter;

    public TimeFragment() {
    }

    public static TimeFragment newInstance() {
        TimeFragment fragment = new TimeFragment() ;
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_time, container, false);
    }

    public void onResume() {
        super.onResume();
    }

    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
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
            public void onItemClick(AdapterView<?> adapterView, View view, int position, long id) {
                Time timeEnviado = (Time) timeArrayAdapter.getItem(position);
                Intent it = new Intent(getActivity(), CadastroActivity.class);
                it.putExtra("chave_time", timeEnviado);
                startActivity(it);
            }
        });
        listTimes.setOnItemLongClickListener(new AdapterView.OnItemLongClickListener() {
            @Override
            public boolean onItemLongClick(AdapterView<?> adapterView, View view, int position, long id) {
                time = timeArrayAdapter.getItem(position);
                return false;
            }
        });

    }

    @Override
    public void onCreateContextMenu(ContextMenu menu, View v, ContextMenu.ContextMenuInfo menuInfo) {
        DBHelperJogador helperJogador = new DBHelperJogador(getActivity());

        int timeID = this.time.getTimeId();
        boolean jogadorExist = helperJogador.jogadorExistsOnTime(timeID);
        if (!jogadorExist) {
            MenuItem mDelete = menu.add(Menu.NONE, id1, 1, "Apague jogador");
            MenuItem mSair = menu.add(Menu.NONE, id2, 2, "Cancela");
            mDelete.setOnMenuItemClickListener(new MenuItem.OnMenuItemClickListener() {
                @Override
                public boolean onMenuItemClick(MenuItem menuItem) {
                    long retornoBD;
                    DBHelperTime helperTime = new DBHelperTime(getActivity());
                    retornoBD = helperTime.deleteTime(time);
                    helperTime.close();
                    if (retornoBD == -1) {
                        alert("Erro de exclusão!");
                    } else {
                        alert("Registro excluído com sucesso!");
                        reloadActivity(v);
                    }
                    return false;
                }
            });
        }
        else {
            MenuItem mSair = menu.add(Menu.NONE, id2, 1, "Cancela");
        }
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
}
