package com.example.activity_02.ui

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.icons.filled.Delete
import androidx.compose.material.icons.filled.Edit
import androidx.compose.material.icons.filled.Search
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.activity_02.data.User
import kotlin.random.Random

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun UserListScreen(viewModel: UserViewModel) {
    val users by viewModel.filteredUsers.collectAsState()
    val searchQuery by viewModel.searchQuery.collectAsState()

    var userToDelete by remember { mutableStateOf<User?>(null) }
    var userToUpdate by remember { mutableStateOf<User?>(null) }
    var newPassword by remember { mutableStateOf("") }

    Scaffold(
        topBar = {
            TopAppBar(
                title = { Text("Registered Users") },
                colors = TopAppBarDefaults.topAppBarColors(
                    containerColor = MaterialTheme.colorScheme.primaryContainer,
                    titleContentColor = MaterialTheme.colorScheme.primary
                )
            )
        },
        floatingActionButton = {
            FloatingActionButton(
                onClick = {
                    val randomId = Random.nextInt(100, 999)
                    viewModel.insert(
                        User(
                            name = "User $randomId",
                            email = "user$randomId@example.com",
                            phone = "9876543${randomId}",
                            password = "pass$randomId"
                        )
                    )
                }
            ) {
                Icon(Icons.Default.Add, contentDescription = "Add User")
            }
        }
    ) { padding ->
        Column(
            modifier = Modifier
                .padding(padding)
                .fillMaxSize()
        ) {
            OutlinedTextField(
                value = searchQuery,
                onValueChange = { viewModel.searchQuery.value = it },
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(16.dp),
                placeholder = { Text("Search users...") },
                leadingIcon = { Icon(Icons.Default.Search, contentDescription = null) },
                singleLine = true
            )

            LazyColumn(
                modifier = Modifier.fillMaxSize(),
                contentPadding = PaddingValues(16.dp),
                verticalArrangement = Arrangement.spacedBy(8.dp)
            ) {
                items(users) { user ->
                    UserCard(
                        user = user,
                        onDeleteClick = { userToDelete = user },
                        onUpdatePwdClick = { userToUpdate = user }
                    )
                }
            }
        }
    }

    // Delete Confirmation Dialog
    userToDelete?.let { user ->
        AlertDialog(
            onDismissRequest = { userToDelete = null },
            title = { Text("Delete User") },
            text = { Text("Are you sure you want to delete ${user.name}?") },
            confirmButton = {
                TextButton(
                    onClick = {
                        viewModel.delete(user)
                        userToDelete = null
                    }
                ) {
                    Text("DELETE", color = Color.Red)
                }
            },
            dismissButton = {
                TextButton(onClick = { userToDelete = null }) {
                    Text("CANCEL")
                }
            }
        )
    }

    // Update Password Dialog
    userToUpdate?.let { user ->
        AlertDialog(
            onDismissRequest = { 
                userToUpdate = null
                newPassword = ""
            },
            title = { Text("Update Password") },
            text = {
                Column {
                    Text("Enter new password for ${user.name}:")
                    Spacer(modifier = Modifier.height(8.dp))
                    OutlinedTextField(
                        value = newPassword,
                        onValueChange = { newPassword = it },
                        modifier = Modifier.fillMaxWidth(),
                        singleLine = true
                    )
                }
            },
            confirmButton = {
                TextButton(
                    onClick = {
                        if (newPassword.isNotBlank()) {
                            viewModel.updatePassword(user.id, newPassword)
                            userToUpdate = null
                            newPassword = ""
                        }
                    }
                ) {
                    Text("UPDATE")
                }
            },
            dismissButton = {
                TextButton(onClick = { 
                    userToUpdate = null
                    newPassword = ""
                }) {
                    Text("CANCEL")
                }
            }
        )
    }
}

@Composable
fun UserCard(
    user: User,
    onDeleteClick: () -> Unit,
    onUpdatePwdClick: () -> Unit
) {
    Card(
        modifier = Modifier.fillMaxWidth(),
        elevation = CardDefaults.cardElevation(defaultElevation = 4.dp)
    ) {
        Column(
            modifier = Modifier
                .padding(16.dp)
                .fillMaxWidth()
        ) {
            Text(text = user.name, fontSize = 20.sp, fontWeight = FontWeight.Bold)
            Text(text = "Email: ${user.email}", color = Color.Gray)
            Text(text = "Phone: ${user.phone}", color = Color.Gray)
            
            Spacer(modifier = Modifier.height(8.dp))
            
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.End
            ) {
                TextButton(onClick = onUpdatePwdClick) {
                    Icon(Icons.Default.Edit, contentDescription = null)
                    Spacer(modifier = Modifier.width(4.dp))
                    Text("UPDATE PWD")
                }
                Spacer(modifier = Modifier.width(8.dp))
                TextButton(onClick = onDeleteClick) {
                    Icon(Icons.Default.Delete, contentDescription = null, tint = Color.Red)
                    Spacer(modifier = Modifier.width(4.dp))
                    Text("DELETE", color = Color.Red)
                }
            }
        }
    }
}
