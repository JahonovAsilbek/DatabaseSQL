package uz.revolution.databasebootcampnews.database

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import uz.revolution.databasebootcampnews.daos.BootcampDao
import uz.revolution.databasebootcampnews.models.Bootcamp
import uz.revolution.databasebootcampnews.models.Category

@Database(entities = [Bootcamp::class,Category::class],version = 1,exportSchema = false)
abstract class AppDatabase :RoomDatabase(){

    abstract fun getBootcampDao():BootcampDao?

    companion object{

        @Volatile
        private var database:AppDatabase?=null

        fun initDatabase(context: Context) {
            synchronized(this){
                if (database == null) {
                    database= Room.databaseBuilder(context.applicationContext,AppDatabase::class.java,"bootcamp.db")
                        .allowMainThreadQueries()
                        .build()
                }
            }
        }
    }

    object get{
        fun getDatabase():AppDatabase{
            return database!!
        }
    }
}