package com.example.moneycontrol;
import androidx.room.Database;
import androidx.room.RoomDatabase;

@Database(entities = {Transacao.class}, version = 1)
public abstract class AppDatabase extends RoomDatabase {
    public abstract TransacaoDao transacaoDao();

}