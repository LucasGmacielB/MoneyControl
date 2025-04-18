package com.example.moneycontrol;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;
import androidx.room.Room;

import java.text.BreakIterator;
import java.util.concurrent.Executors;

public class AddTransacoes extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_add_transacoes);
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });

        RadioGroup selecao = findViewById(R.id.radioGroupTipo);
        selecao.clearCheck();

        EditText editText = findViewById(R.id.editValor);
        Button botaoSalvar = findViewById(R.id.btnSalvar);

        botaoSalvar.setOnClickListener(v -> {
            String valorTexto = editText.getText().toString();
            if (!valorTexto.isEmpty()) {
                double valor = Double.parseDouble(valorTexto.replace(",", "."));
                int radioId = selecao.getCheckedRadioButtonId();
                if (radioId == -1) {
                    Toast.makeText(this, "Selecione Receita ou Despesa", Toast.LENGTH_SHORT).show();
                    return;
                }
                RadioButton radioSelecionado = findViewById(radioId);
                String tipo = radioSelecionado.getText().toString();

                Transacao nova = new Transacao(valor, tipo);

                Executors.newSingleThreadExecutor().execute(() -> {
                    AppDatabase db = Room.databaseBuilder(
                            getApplicationContext(),
                            AppDatabase.class,
                            "moneycontrol-db"
                    ).build();

                    db.transacaoDao().inserir(nova);

                    runOnUiThread(() -> finish());
                });

            }
        });

    }

}