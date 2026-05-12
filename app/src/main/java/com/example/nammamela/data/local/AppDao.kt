package com.example.nammamela.data.local

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import androidx.room.Update
import com.example.nammamela.data.model.CastMember
import com.example.nammamela.data.model.FanComment
import com.example.nammamela.data.model.Play
import com.example.nammamela.data.model.Seat
import kotlinx.coroutines.flow.Flow

@Dao
interface AppDao {
    @Query("SELECT * FROM plays LIMIT 1")
    fun getPlay(): Flow<Play?>

    @Query("SELECT * FROM plays")
    fun getAllPlays(): Flow<List<Play>>

    @Query("SELECT * FROM plays WHERE id = :playId")
    fun getPlayById(playId: Int): Flow<Play?>

    @Query("SELECT * FROM cast_members")
    fun getCastMembers(): Flow<List<CastMember>>

    @Query("SELECT * FROM cast_members WHERE playId = :playId")
    fun getCastMembersForPlay(playId: Int): Flow<List<CastMember>>

    // Seats filtered by playId
    @Query("SELECT * FROM seats WHERE playId = :playId ORDER BY seatNumber ASC")
    fun getSeatsForPlay(playId: Int): Flow<List<Seat>>

    // Fan comments filtered by playId
    @Query("SELECT * FROM fan_comments WHERE playId = :playId ORDER BY timestamp DESC")
    fun getFanCommentsForPlay(playId: Int): Flow<List<FanComment>>

    @Update
    suspend fun updateSeat(seat: Seat)

    @Insert
    suspend fun insertComment(comment: FanComment)

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertPlay(play: Play)

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertCastMembers(castMembers: List<CastMember>)

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertSeats(seats: List<Seat>)
}
