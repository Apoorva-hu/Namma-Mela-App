package com.example.nammamela.data.local

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import androidx.sqlite.db.SupportSQLiteDatabase
import com.example.nammamela.data.model.CastMember
import com.example.nammamela.data.model.FanComment
import com.example.nammamela.data.model.Play
import com.example.nammamela.data.model.Seat
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

@Database(
    entities = [Play::class, CastMember::class, Seat::class, FanComment::class],
    version = 4,
    exportSchema = false
)
abstract class AppDatabase : RoomDatabase() {
    abstract fun appDao(): AppDao

    companion object {
        @Volatile
        private var INSTANCE: AppDatabase? = null

        fun getDatabase(context: Context): AppDatabase {
            return INSTANCE ?: synchronized(this) {
                val instance = Room.databaseBuilder(
                    context.applicationContext,
                    AppDatabase::class.java,
                    "namma_mela_database"
                )
                    .fallbackToDestructiveMigration()
                    .addCallback(DatabaseCallback())
                    .build()
                INSTANCE = instance
                instance
            }
        }
    }

    private class DatabaseCallback : RoomDatabase.Callback() {
        override fun onCreate(db: SupportSQLiteDatabase) {
            super.onCreate(db)
            INSTANCE?.let { database ->
                CoroutineScope(Dispatchers.IO).launch {
                    populateDatabase(database.appDao())
                }
            }
        }

        suspend fun populateDatabase(dao: AppDao) {

            // --- PLAYS ---
            val plays = listOf(
                Play(
                    id = 1,
                    title = "Mahabharata: The Great War",
                    duration = "2h 30m",
                    description = "Experience the epic saga of the Mahabharata, a tale of dharma, duty, and the ultimate battle between good and evil, performed by our esteemed local theatre group.",
                    posterUrl = "https://images.unsplash.com/photo-1667162862361-e2dbce58db05?w=800&auto=format&fit=crop",
                    date = "May 20, 2025",
                    time = "7:00 PM",
                    venue = "Harapanahalli Town Ground, Harapanahalli"
                ),
                Play(
                    id = 2,
                    title = "Ramayana: Sita's Quest",
                    duration = "2h 00m",
                    description = "A breathtaking retelling of the Ramayana focusing on Sita's courage, devotion and inner strength. A visual spectacle with classical dance and music.",
                    posterUrl = "https://images.unsplash.com/photo-1582510003544-4d00b7f74220?w=800&auto=format&fit=crop",
                    date = "May 25, 2025",
                    time = "6:30 PM",
                    venue = "Harihara Nagarabhavi Grounds, Harihara"
                ),
                Play(
                    id = 3,
                    title = "Village Festival Dance",
                    duration = "1h 45m",
                    description = "A vibrant celebration of rural Karnataka's folk traditions through colorful costumes, energetic dances and folk music that will transport you to the heartland.",
                    posterUrl = "https://images.unsplash.com/photo-1563245372-f21724e3856d?w=800&auto=format&fit=crop",
                    date = "June 1, 2025",
                    time = "5:00 PM",
                    venue = "Hiriyur Taluk Maidan, Hiriyur"
                ),
                Play(
                    id = 4,
                    title = "Yakshagana Night",
                    duration = "3h 00m",
                    description = "Witness the traditional Tulu Nadu art form of Yakshagana in all its glory — elaborate costumes, face paint, percussion beats and mythological storytelling.",
                    posterUrl = "https://images.unsplash.com/photo-1545128485-c400ce7b23d5?w=800&auto=format&fit=crop",
                    date = "June 8, 2025",
                    time = "8:00 PM",
                    venue = "Channagiri Utsav Bhumi, Channagiri"
                ),
                Play(
                    id = 5,
                    title = "Historical Drama Night",
                    duration = "2h 15m",
                    description = "Journey through the glorious chapters of Karnataka history — from Vijayanagara empire to Rani Abbakka — in this grand historical drama with stunning sets.",
                    posterUrl = "https://images.unsplash.com/photo-1599661046289-e31897846e41?w=800&auto=format&fit=crop",
                    date = "June 15, 2025",
                    time = "7:30 PM",
                    venue = "Jagalur Panchayat Open Stage, Jagalur"
                )
            )
            plays.forEach { dao.insertPlay(it) }

            // --- CAST MEMBERS ---
            val castMembers = listOf(
                CastMember(playId = 1, actorName = "Ravi Kumar", roleName = "Arjuna", imageUrl = "https://images.unsplash.com/photo-1506794778202-cad84cf45f1d?w=200&auto=format&fit=crop"),
                CastMember(playId = 1, actorName = "Anjali Devi", roleName = "Draupadi", imageUrl = "https://images.unsplash.com/photo-1534528741775-53994a69daeb?w=200&auto=format&fit=crop"),
                CastMember(playId = 1, actorName = "Vikram Singh", roleName = "Karna", imageUrl = "https://images.unsplash.com/photo-1507003211169-0a1dd7228f2d?w=200&auto=format&fit=crop"),
                CastMember(playId = 1, actorName = "Suresh Babu", roleName = "Krishna", imageUrl = "https://images.unsplash.com/photo-1492562080023-ab3db95bfbce?w=200&auto=format&fit=crop"),
                CastMember(playId = 1, actorName = "Meera Rao", roleName = "Kunti", imageUrl = "https://images.unsplash.com/photo-1531123897727-8f129e1bfa8ea?w=200&auto=format&fit=crop"),
                CastMember(playId = 1, actorName = "Prakash Nair", roleName = "Duryodhana", imageUrl = "https://images.unsplash.com/photo-1500648767791-00dcc994a43e?w=200&auto=format&fit=crop"),
                CastMember(playId = 2, actorName = "Deepak Sharma", roleName = "Lord Rama", imageUrl = "https://images.unsplash.com/photo-1519085360753-af0119f7cbe7?w=200&auto=format&fit=crop"),
                CastMember(playId = 2, actorName = "Priya Nair", roleName = "Sita", imageUrl = "https://images.unsplash.com/photo-1488426862026-3ee34a7d66df?w=200&auto=format&fit=crop"),
                CastMember(playId = 2, actorName = "Kiran Raj", roleName = "Lakshmana", imageUrl = "https://images.unsplash.com/photo-1463453091185-61582044d556?w=200&auto=format&fit=crop"),
                CastMember(playId = 2, actorName = "Arjun Menon", roleName = "Hanuman", imageUrl = "https://images.unsplash.com/photo-1472099645785-5658abf4ff4e?w=200&auto=format&fit=crop"),
                CastMember(playId = 3, actorName = "Kavitha Gowda", roleName = "Lead Dancer", imageUrl = "https://images.unsplash.com/photo-1438761681033-6461ffad8d80?w=200&auto=format&fit=crop"),
                CastMember(playId = 3, actorName = "Ramesh Naik", roleName = "Drummer", imageUrl = "https://images.unsplash.com/photo-1507591064344-4c6ce005b128?w=200&auto=format&fit=crop"),
                CastMember(playId = 3, actorName = "Suma Hegde", roleName = "Folk Singer", imageUrl = "https://images.unsplash.com/photo-1489424731084-a5d8b219a5bb?w=200&auto=format&fit=crop"),
                CastMember(playId = 4, actorName = "Govinda Bhat", roleName = "Bhima", imageUrl = "https://images.unsplash.com/photo-1544723795-3fb6469f5b39?w=200&auto=format&fit=crop"),
                CastMember(playId = 4, actorName = "Shantha Kumari", roleName = "Shakti Devi", imageUrl = "https://images.unsplash.com/photo-1508214751196-bcfd4ca60f91?w=200&auto=format&fit=crop"),
                CastMember(playId = 5, actorName = "Mahesh Shetty", roleName = "Krishnadevaraya", imageUrl = "https://images.unsplash.com/photo-1547425260-76bcadfb4f2c?w=200&auto=format&fit=crop"),
                CastMember(playId = 5, actorName = "Rekha Nair", roleName = "Rani Abbakka", imageUrl = "https://images.unsplash.com/photo-1479936343636-73cdc5aae0c3?w=200&auto=format&fit=crop")
            )
            dao.insertCastMembers(castMembers)

            // --- SEATS: separate seats per event ---
            val rows = listOf("A", "B", "C", "D", "E")
            val playIds = listOf(1, 2, 3, 4, 5)
            val seats = mutableListOf<Seat>()
            for (pid in playIds) {
                for (row in rows) {
                    for (i in 1..8) {
                        seats.add(Seat(playId = pid, seatNumber = "$row$i", reserved = false))
                    }
                }
            }
            dao.insertSeats(seats)
        }
    }
}
