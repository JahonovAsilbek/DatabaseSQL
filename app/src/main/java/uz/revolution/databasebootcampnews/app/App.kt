package uz.revolution.databasebootcampnews.app

import android.app.Application
import uz.revolution.databasebootcampnews.daos.BootcampDao
import uz.revolution.databasebootcampnews.database.AppDatabase
import uz.revolution.databasebootcampnews.models.Category

class App : Application() {

    private var database: AppDatabase? = null
    private var bootcampDao: BootcampDao? = null

    override fun onCreate() {
        super.onCreate()
        AppDatabase.initDatabase(this)
        database = AppDatabase.get.getDatabase()
        bootcampDao = database!!.getBootcampDao()

        if (bootcampDao!!.getAllCategory().isEmpty()) {
            bootcampDao?.insertCategory(Category("Asosiy"))
            bootcampDao?.insertCategory(Category("Dunyo"))
            bootcampDao?.insertCategory(Category("Ijtimoiy"))
        }
    }
}