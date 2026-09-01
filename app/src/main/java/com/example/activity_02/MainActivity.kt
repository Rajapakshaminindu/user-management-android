package com.example.activity_02

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.viewModels
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.ui.Modifier
import com.example.activity_02.data.User
import com.example.activity_02.ui.UserListScreen
import com.example.activity_02.ui.UserViewModel
import com.example.activity_02.ui.theme.Activity_02Theme

class MainActivity : ComponentActivity() {
    private val viewModel: UserViewModel by viewModels()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        
        // Seeding dummy data for demonstration if needed
        // viewModel.insert(User(name = "John Doe", email = "john@example.com", phone = "1234567890", password = "password123"))
        
        setContent {
            Activity_02Theme {
                Surface(
                    modifier = Modifier.fillMaxSize(),
                    color = MaterialTheme.colorScheme.background
                ) {
                    UserListScreen(viewModel = viewModel)
                }
            }
        }
    }
}
