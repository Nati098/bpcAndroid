package com.example.mainactivity.data.database;

import java.util.List;

import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.OnConflictStrategy;
import androidx.room.Query;

@Dao
public interface NewsDao {

    @Query("SELECT * FROM NewsEntity")
    List<NewsEntity> getAll();

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    void insert(NewsEntity newsEntity);

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    void insertAll(NewsEntity ... items);

    @Delete
    void delete(NewsEntity item);

    @Query("DELETE FROM NewsEntity")
    void deleteAll();


    @Query("SELECT * FROM NewsEntity WHERE id = :id")
    NewsEntity getNewsById(int id);


}
