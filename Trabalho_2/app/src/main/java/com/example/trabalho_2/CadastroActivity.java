package com.example.trabalho_2;

import android.content.Intent;
import android.os.Bundle;
import com.example.trabalho_2.db.DBHelperTime;
import com.example.trabalho_2.db.DBHelperJogador;
import com.example.trabalho_2.db.Jogador;
import com.example.trabalho_2.db.Time;
import com.example.trabalho_2.fragments.CadastroJogadorFragment;
import com.example.trabalho_2.ui.main.CadastroSectionsPagerAdapter;
import com.google.android.material.snackbar.Snackbar;
import com.google.android.material.tabs.TabLayout;
import androidx.viewpager.widget.ViewPager;
import androidx.appcompat.app.AppCompatActivity;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.TextView;
import com.example.trabalho_2.databinding.ActivityCadastroBinding;
import java.util.ArrayList;

public class CadastroActivity extends AppCompatActivity {
    private ViewPager viewPager;
    private ActivityCadastroBinding binding;
    private DBHelperTime helperTime = new DBHelperTime(this);
    private DBHelperJogador helperJogador = new DBHelperJogador(this);
    private EditText edtNome;
    private EditText edtDescricao;
    private EditText edtCpf;
    private EditText edtAnoNascimento;
    private TextView edtID;
    private Button btnVariavel;
    private ListView listTimes;
    ArrayList<Time> arrayListTime;
    ArrayAdapter<Time> timeArrayAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityCadastroBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());
        CadastroSectionsPagerAdapter sectionsPagerAdapter = new CadastroSectionsPagerAdapter(this, getSupportFragmentManager());
        viewPager = binding.viewPager;
        viewPager.setAdapter(sectionsPagerAdapter);
        TabLayout tabs = binding.tabs;
        tabs.setupWithViewPager(viewPager);
    }

    public void onResume(){
        super.onResume();
    }

    public void cadastrarJogador(View view) {
        edtNome = findViewById(R.id.textJogadorNome);
        edtCpf = findViewById(R.id.textJogadorCpf);
        edtAnoNascimento = findViewById(R.id.textJogadorAnoNascimento);
        String nome = edtNome.getText().toString();
        String cpf = edtCpf.getText().toString();
        String ano = edtAnoNascimento.getText().toString();
        String selectedItem = CadastroJogadorFragment.getSelectedItem();
        CadastroJogadorFragment.setSelectedItemNull();
        btnVariavel = findViewById(R.id.buttonJogador);
        if (!nome.equals("") && !cpf.equals("") && !ano.equals("") && selectedItem != null) {
            String timeIDStr = selectedItem.substring(selectedItem.indexOf("ID: ") + 4);
            int timeID = Integer.parseInt(timeIDStr);
            Jogador e = new Jogador();
            e.setJogadorNome(nome);
            e.setJogadorCpf(cpf);
            e.setJogadorAnoNascimento(Integer.parseInt(ano));
            e.setTimeId(timeID);
            if (btnVariavel.getText().toString().equals("Cadastrar Jogador!")) {
                helperJogador.inserirJogador(e);
                Snackbar.make(view, "Jogador cadastrado!", Snackbar.LENGTH_LONG).setAction("Action", null).show();
                reloadActivity(view);
            }
            else {
                edtID = (TextView) findViewById(R.id.textJogadorID);
                int id = Integer.parseInt(edtID.getText().toString());
                e.setJogadorId(id);
                helperJogador.updateJogador(e);
                Snackbar.make(view, "Jogador atualizado!", Snackbar.LENGTH_LONG).setAction("Action", null).show();
                reloadActivity(view);
            }
        }
        else {
            Snackbar.make(view, "Preencha os campos corretamente", Snackbar.LENGTH_LONG).setAction("Action", null).show();
        }
    }

    public void cadastrarTime(View view) {
        edtDescricao = findViewById(R.id.textTimeDescricao);
        String descricao = edtDescricao.getText().toString();
        btnVariavel = findViewById(R.id.buttonTime);
        if (!descricao.equals("")) {
            Time e = new Time();
            e.setTimeDescricao(descricao);
            if (btnVariavel.getText().toString().equals("Cadastrar time!")){
                helperTime.inserirTime(e);
                Snackbar.make(view, "Time Cadastrado!", Snackbar.LENGTH_LONG).setAction("Action", null).show();
                reloadActivity(view);
            }
            else {
                edtID = (TextView) findViewById(R.id.textTimeID);
                int id = Integer.parseInt(edtID.getText().toString());
                e.setTimeId(id);
                helperTime.updateTime(e);
                Snackbar.make(view, "Time atualizado!", Snackbar.LENGTH_LONG).setAction("Action", null).show();
                reloadActivity(view);
            }
        }
        else {
            Snackbar.make(view, "Preencha os campos corretamente", Snackbar.LENGTH_LONG).setAction("Action", null).show();
        }
        helperTime.close();
    }

    public void navigateFragment(int position){
        viewPager.setCurrentItem(position, true);
    }

    public void reloadActivity(View view) {
        Intent intent = new Intent(CadastroActivity.this, MainActivity.class);
        finish();
        startActivity(intent);
    }
}
