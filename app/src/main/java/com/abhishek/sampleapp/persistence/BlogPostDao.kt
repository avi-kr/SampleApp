package com.abhishek.sampleapp.persistence

import androidx.lifecycle.LiveData
import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.abhishek.sampleapp.models.BlogPost
import com.abhishek.sampleapp.util.Constants.Companion.PAGINATION_PAGE_SIZE

/**
 * Created by Abhishek Kumar on 08/08/20.
 * (c)2020 VMock. All rights reserved.
 */

@Dao
interface BlogPostDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insert(blogPost: BlogPost): Long

    @Query("""
        SELECT * FROM blog_post 
        WHERE title LIKE '%' || :query || '%' 
        OR body LIKE '%' || :query || '%' 
        OR username LIKE '%' || :query || '%' 
        LIMIT (:page * :pageSize)
        """)
    fun getAllBlogPosts(
        query: String,
        page: Int,
        pageSize: Int = PAGINATION_PAGE_SIZE
    ): LiveData<List<BlogPost>>
}