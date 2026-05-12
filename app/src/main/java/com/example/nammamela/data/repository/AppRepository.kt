package com.example.nammamela.data.repository

import com.example.nammamela.data.local.AppDao
import com.example.nammamela.data.model.FanComment
import com.example.nammamela.data.model.Seat
import kotlinx.coroutines.flow.Flow

class AppRepository(private val appDao: AppDao) {
    val play = appDao.getPlay()
    val allPlays = appDao.getAllPlays()
    val castMembers = appDao.getCastMembers()

    fun getPlayById(playId: Int) = appDao.getPlayById(playId)
    fun getCastMembersForPlay(playId: Int) = appDao.getCastMembersForPlay(playId)
    fun getSeatsForPlay(playId: Int) = appDao.getSeatsForPlay(playId)
    fun getFanCommentsForPlay(playId: Int) = appDao.getFanCommentsForPlay(playId)

    suspend fun reserveSeat(seat: Seat) {
        appDao.updateSeat(seat.copy(reserved = true))
    }

    suspend fun addComment(playId: Int, userName: String, comment: String) {
        appDao.insertComment(FanComment(playId = playId, userName = userName, comment = comment))
    }
}
