package com.example.moneycontrol;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;
import androidx.room.Ignore;
import androidx.room.Room;

import java.util.List;


public class MainActivity extends AppCompatActivity {
    TextView textoSaldo, receitasSaldo, despesasSaldo;
    AppDatabase db;
    TransacaoDao dao;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_main);
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });
        Button buttonTransacao = findViewById(R.id.btnAdicionar);
        buttonTransacao.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                Intent intent = new Intent(MainActivity.this, AddTransacoes.class);
                startActivity(intent);
            }

        });
        despesasSaldo = findViewById(R.id.despesasSaldo);
        receitasSaldo = findViewById(R.id.receitasSaldo);
        textoSaldo = findViewById(R.id.saldoGeral);
        AppDatabase db = Room.databaseBuilder(
                getApplicationContext(),
                AppDatabase.class,
                "moneycontrol-db"
        ).allowMainThreadQueries().build();

        dao = db.transacaoDao();

        atualizarSaldo(); // chama aqui também quando inicia pela primeira vez

        Button btnDeletarTudo = findViewById(R.id.btnDeletar);
        btnDeletarTudo.setOnClickListener(v -> {
            dao.deletarTudo();
            atualizarSaldo();
        });

        btnDeletarTudo.setOnClickListener(v -> {
            new AlertDialog.Builder(MainActivity.this)
                    .setTitle("Apagar tudo?")
                    .setMessage("Tem certeza que deseja apagar todas as transações?")
                    .setPositiveButton("Sim", (dialog, which) -> {
                        dao.deletarTudo();
                        atualizarSaldo();
                    })
                    .setNegativeButton("Cancelar", null)
                    .show();
        });
    }

    @Override
    protected void onResume() {
        super.onResume();
        atualizarSaldo(); // chama aqui sempre que voltar pra essa tela
    }

    private void atualizarSaldo() {
        List<Transacao> lista = dao.getTodas();

        double saldo = 0.0;
        double totalDespesas = 0.0;
        double totalReceitas = 0.0;

        for (Transacao t : lista) {
            if (t.getTipo().equalsIgnoreCase("Receita")) {
                saldo += t.getValor();
                totalReceitas +=t.getValor();
            } else if (t.getTipo().equalsIgnoreCase("Despesa")) {
                saldo -= t.getValor();
                totalDespesas += t.getValor();
            }
        }

        textoSaldo.setText(String.format("R$ %.2f", saldo));
        despesasSaldo.setText(String.format("R$ %.2f", totalDespesas));
        receitasSaldo.setText(String.format("R$ %.2f", totalReceitas));
    }

}