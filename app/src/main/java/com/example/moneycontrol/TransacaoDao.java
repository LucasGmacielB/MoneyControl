package com.example.moneycontrol;
import androidx.room.Dao;
import androidx.room.Insert;
import androidx.room.Query;
import java.util.List;

@Dao
public interface TransacaoDao {
    @Insert
    void inserir(Transacao transacao);

    @Query("SELECT * FROM Transacao")
    List<Transacao> getTodas();

    @Query("SELECT SUM(valor) FROM Transacao WHERE tipo = 'Receita'")
    double getTotalReceitas();

    @Query("SELECT SUM(valor) FROM Transacao WHERE tipo = 'Despesa'")
    double getTotalDespesas();

    @Query("DELETE FROM transacao")
    void deletarTudo();
}
