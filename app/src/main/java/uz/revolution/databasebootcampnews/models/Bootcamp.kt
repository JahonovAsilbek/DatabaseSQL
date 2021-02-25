package uz.revolution.databasebootcampnews.models

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.Ignore
import androidx.room.PrimaryKey
import java.io.Serializable

@Entity(tableName = "bootcamp")
class Bootcamp:Serializable {

    @PrimaryKey(autoGenerate = true)
    @ColumnInfo(name = "id")
    var id: Int? = null

    @ColumnInfo(name = "title")
    var title: String? = null

    @ColumnInfo(name = "text")
    var text: String? = null

    @ColumnInfo(name = "category_id")
    var categoryID:Int?=null

    @Ignore
    constructor(title: String?, text: String?, categoryID: Int?) {
        this.title = title
        this.text = text
        this.categoryID = categoryID
    }

    constructor()

}