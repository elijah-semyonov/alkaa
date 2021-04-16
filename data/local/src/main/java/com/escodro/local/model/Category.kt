package com.escodro.local.model

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

/**
 * [Entity] to represent a category.
 *
 * @param id category id
 * @param name category name
 * @param color category color
 */
@Entity
data class Category(
    @ColumnInfo(name = "category_id") @PrimaryKey(autoGenerate = true) var id: Long = 0,
    @ColumnInfo(name = "category_name") var name: String,
    @ColumnInfo(name = "category_color") var color: String
)
