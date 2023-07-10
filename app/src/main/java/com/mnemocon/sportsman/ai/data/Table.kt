package com.mnemocon.sportsman.ai.data

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey


@Entity(
    tableName = "table"
)
data class Table(
    @PrimaryKey(autoGenerate = true)
    @ColumnInfo(name = "id")
    var id: Int = 0,

    @ColumnInfo(name = "dateTime")
    val dateTime: String,

    @ColumnInfo(name = "duration")
    val duration: String,

    @ColumnInfo(name = "pushups")
    val pushups: String,

    @ColumnInfo(name = "squats")
    val squats: String
) {

}