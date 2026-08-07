package com.example.inventory.data

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase

//#3 Instancia de Banco de Dados

@Database(entities = [Item::class], version = 1, exportSchema = false)
abstract class InventoryDatabase : RoomDatabase() {
    abstract fun itemDao(): ItemDao   //função abstrata que retorne o objeto ItemDao para que o banco de dados reconheça o DAO.

    companion object {                //acesso aos métodos para criar ou obter o banco de dados e use o nome da classe como qualificador.
        @Volatile                     //ajuda que a Instance esteja sempre atualizado e seja o mesmo para todas as threads de execução.
        private var Instance: InventoryDatabase? = null

        fun getDatabase(context: Context): InventoryDatabase {
            // if the Instance is not null, return it, otherwise create a new database instance.
            return Instance ?: synchronized(this) {
                Room.databaseBuilder(context, InventoryDatabase::class.java, "item_database")
                    .fallbackToDestructiveMigration()
                    .build()
                    .also { Instance = it }
            }
        }
    }
}

//Proximo pass0 -> ItensRepository