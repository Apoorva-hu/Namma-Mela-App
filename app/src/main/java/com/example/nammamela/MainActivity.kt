package com.example.nammamela

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import com.example.nammamela.data.local.AppDatabase
import com.example.nammamela.data.repository.AppRepository
import com.example.nammamela.ui.navigation.AppNavigation
import com.example.nammamela.ui.theme.NammaMelaTheme
import com.example.nammamela.ui.viewmodel.ViewModelFactory

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        val database = AppDatabase.getDatabase(this)
        val repository = AppRepository(database.appDao())
        val viewModelFactory = ViewModelFactory(repository)

        setContent {
            NammaMelaTheme {
                AppNavigation(viewModelFactory = viewModelFactory)
            }
        }
    }
}
