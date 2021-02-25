package uz.revolution.databasebootcampnews.daos

import androidx.room.*
import uz.revolution.databasebootcampnews.models.Bootcamp
import uz.revolution.databasebootcampnews.models.Category

@Dao
interface BootcampDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insertCategory(category: Category)

    @Query("SELECT * FROM category")
    fun getAllCategory(): List<Category>

    @Query("SELECT * FROM category WHERE id=:id")
    fun getCategoryByID(id: Int): Category

    /*
    *
    *
            Bootcamp Queries
    *
    *
     */

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insertBootcamp(bootcamp: Bootcamp)

    @Query("UPDATE bootcamp SET title=:title,text=:text,category_id=:categoryID WHERE id=:id")
    fun updateBootcamp(title:String,text:String,categoryID: Int,id: Int)

    @Query("SELECT * FROM bootcamp")
    fun getAllBootcamp(): List<Bootcamp>

    @Query("SELECT * FROM bootcamp WHERE id=:id")
    fun getBootcampByID(id: Int): Bootcamp

    @Query("SELECT * FROM bootcamp WHERE category_id=:categoryID")
    fun getBootcampByCategoryID(categoryID: Int): List<Bootcamp>

    @Delete
    fun deleteBootcamp(bootcamp: Bootcamp)

}