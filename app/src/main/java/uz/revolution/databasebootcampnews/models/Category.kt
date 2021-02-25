package uz.revolution.databasebootcampnews.models

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.Ignore
import androidx.room.PrimaryKey
import java.io.Serializable

@Entity(tableName = "category")
class Category :Serializable{

    @PrimaryKey(autoGenerate = true)
    @ColumnInfo(name = "id")
    var id: Int? = null

    @ColumnInfo(name = "name")
    var name:String?=null



    @Ignore
    constructor(name: String?) {
        this.name = name
    }

    constructor()

    @Ignore
    constructor(id: Int?, name: String?) {
        this.id = id
        this.name = name
    }

}