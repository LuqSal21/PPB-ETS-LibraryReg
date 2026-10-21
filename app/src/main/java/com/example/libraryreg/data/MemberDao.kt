package com.example.libraryreg.data

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import androidx.room.Update
import kotlinx.coroutines.flow.Flow

@Dao
interface MemberDao {
    @Query("SELECT * FROM members ORDER BY id DESC")
    fun getAllMembers(): Flow<List<Member>>

    @Query("SELECT * FROM members WHERE namaLengkap LIKE '%' || :query || '%' OR nomorInduk LIKE '%' || :query || '%' ORDER BY id DESC")
    fun searchMembers(query: String): Flow<List<Member>>

    @Query("SELECT * FROM members WHERE id = :id LIMIT 1")
    fun getMemberById(id: Int): Flow<Member?>

    @Insert(onConflict = OnConflictStrategy.ABORT)
    suspend fun insertMember(member: Member): Long

    @Update
    suspend fun updateMember(member: Member)

    @Delete
    suspend fun deleteMember(member: Member)

    @Query("SELECT COUNT(*) FROM members WHERE nomorInduk = :nomorInduk AND id != :excludeId")
    suspend fun checkNomorIndukExists(nomorInduk: String, excludeId: Int = 0): Int
}
