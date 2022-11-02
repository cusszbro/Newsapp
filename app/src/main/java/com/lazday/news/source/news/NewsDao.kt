package com.lazday.news.source.news

import androidx.lifecycle.LiveData
import androidx.room.*

@Dao
interface NewsDao {

    @Query("SELECT * FROM tableArticle")
    fun findAll(): LiveData<List<ArticleModel>>

    @Insert(onConflict = OnConflictStrategy.IGNORE)
     fun save(articleModel: ArticleModel)

    @Query("SELECT COUNT(*) FROM tableArticle WHERE publishedAt=:publish")
     fun find(publish: String): Int

    @Delete
     fun remove(articleModel: ArticleModel)
}