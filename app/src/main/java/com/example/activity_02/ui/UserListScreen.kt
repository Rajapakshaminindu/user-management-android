package com.example.activity_02.ui

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.icons.filled.Delete
import androidx.compose.material.icons.filled.Edit
import androidx.compose.material.icons.filled.Key
import androidx.compose.material.icons.filled.Search
import androidx.compose.material.icons.filled.Visibility
import androidx.compose.material.icons.filled.VisibilityOff
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.text.input.VisualTransformation
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.activity_02.data.User
import com.example.activity_02.data.validation.UserValidator
import kotlin.random.Random

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun UserListScreen(viewModel: UserViewModel) {
    val users by viewModel.filteredUsers.collectAsState()
    val searchQuery by viewModel.searchQuery.collectAsState()

    var showAddUserDialog by remember { mutableStateOf(false) }
    var userToEdit by remember { mutableStateOf<User?>(null) }
    var userToDelete by remember { mutableStateOf<User?>(null) }
    var userToUpdatePwd by remember { mutableStateOf<User?>(null) }
    var newPassword by remember { mutableStateOf("") }
    var newPasswordError by remember { mutableStateOf<String?>(null) }
    var isNewPasswordVisible by remember { mutableStateOf(false) }

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
                onClick = { showAddUserDialog = true }
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
                        onEditClick = { userToEdit = user },
                        onDeleteClick = { userToDelete = user },
                        onUpdatePwdClick = { 
                            userToUpdatePwd = user
                            newPassword = ""
                            newPasswordError = null
                            isNewPasswordVisible = false
                        }
                    )
                }
            }
        }
    }

    // Add User Dialog
    if (showAddUserDialog) {
        AddUserDialog(
            onDismiss = { showAddUserDialog = false },
            onConfirm = { newUser ->
                viewModel.insert(newUser)
                showAddUserDialog = false
            },
            onGenerateRandom = {
                val randomId = Random.nextInt(100, 999)
                viewModel.insert(
                    User(
                        name = "User $randomId",
                        email = "user$randomId@example.com",
                        phone = "9876543$randomId",
                        password = "pass$randomId"
                    )
                )
                showAddUserDialog = false
            }
        )
    }

    // Edit User Dialog
    userToEdit?.let { user ->
        EditUserDialog(
            user = user,
            onDismiss = { userToEdit = null },
            onConfirm = { updatedUser ->
                viewModel.update(updatedUser)
                userToEdit = null
            }
        )
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
    userToUpdatePwd?.let { user ->
        AlertDialog(
            onDismissRequest = { 
                userToUpdatePwd = null
                newPassword = ""
                newPasswordError = null
            },
            title = { Text("Update Password") },
            text = {
                Column {
                    Text("Enter new password for ${user.name}:")
                    Spacer(modifier = Modifier.height(8.dp))
                    OutlinedTextField(
                        value = newPassword,
                        onValueChange = { 
                            newPassword = it 
                            newPasswordError = null
                        },
                        modifier = Modifier.fillMaxWidth(),
                        singleLine = true,
                        isError = newPasswordError != null,
                        visualTransformation = if (isNewPasswordVisible) VisualTransformation.None else PasswordVisualTransformation(),
                        keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Password),
                        trailingIcon = {
                            IconButton(onClick = { isNewPasswordVisible = !isNewPasswordVisible }) {
                                Icon(
                                    imageVector = if (isNewPasswordVisible) Icons.Default.VisibilityOff else Icons.Default.Visibility,
                                    contentDescription = if (isNewPasswordVisible) "Hide password" else "Show password"
                                )
                            }
                        }
                    )
                    if (newPasswordError != null) {
                        Text(
                            text = newPasswordError ?: "",
                            color = MaterialTheme.colorScheme.error,
                            fontSize = 12.sp,
                            modifier = Modifier.padding(start = 4.dp, top = 4.dp)
                        )
                    }
                }
            },
            confirmButton = {
                TextButton(
                    onClick = {
                        val validation = UserValidator.validatePassword(newPassword)
                        if (validation.isValid) {
                            viewModel.updatePassword(user.id, newPassword)
                            userToUpdatePwd = null
                            newPassword = ""
                            newPasswordError = null
                        } else {
                            newPasswordError = validation.errorMessage
                        }
                    }
                ) {
                    Text("UPDATE")
                }
            },
            dismissButton = {
                TextButton(onClick = { 
                    userToUpdatePwd = null
                    newPassword = ""
                    newPasswordError = null
                }) {
                    Text("CANCEL")
                }
            }
        )
    }
}

@Composable
fun AddUserDialog(
    onDismiss: () -> Unit,
    onConfirm: (User) -> Unit,
    onGenerateRandom: () -> Unit
) {
    var name by remember { mutableStateOf("") }
    var email by remember { mutableStateOf("") }
    var phone by remember { mutableStateOf("") }
    var password by remember { mutableStateOf("") }
    var isPasswordVisible by remember { mutableStateOf(false) }

    var nameError by remember { mutableStateOf<String?>(null) }
    var emailError by remember { mutableStateOf<String?>(null) }
    var phoneError by remember { mutableStateOf<String?>(null) }
    var passwordError by remember { mutableStateOf<String?>(null) }

    AlertDialog(
        onDismissRequest = onDismiss,
        title = { Text("Add New User") },
        text = {
            Column(
                modifier = Modifier.fillMaxWidth(),
                verticalArrangement = Arrangement.spacedBy(8.dp)
            ) {
                OutlinedTextField(
                    value = name,
                    onValueChange = {
                        name = it
                        nameError = null
                    },
                    label = { Text("Full Name") },
                    modifier = Modifier.fillMaxWidth(),
                    singleLine = true,
                    isError = nameError != null,
                    supportingText = nameError?.let { { Text(it) } }
                )

                OutlinedTextField(
                    value = email,
                    onValueChange = {
                        email = it
                        emailError = null
                    },
                    label = { Text("Email Address") },
                    modifier = Modifier.fillMaxWidth(),
                    singleLine = true,
                    keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Email),
                    isError = emailError != null,
                    supportingText = emailError?.let { { Text(it) } }
                )

                OutlinedTextField(
                    value = phone,
                    onValueChange = {
                        phone = it
                        phoneError = null
                    },
                    label = { Text("Phone Number") },
                    modifier = Modifier.fillMaxWidth(),
                    singleLine = true,
                    keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Phone),
                    isError = phoneError != null,
                    supportingText = phoneError?.let { { Text(it) } }
                )

                OutlinedTextField(
                    value = password,
                    onValueChange = {
                        password = it
                        passwordError = null
                    },
                    label = { Text("Password") },
                    modifier = Modifier.fillMaxWidth(),
                    singleLine = true,
                    visualTransformation = if (isPasswordVisible) VisualTransformation.None else PasswordVisualTransformation(),
                    keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Password),
                    trailingIcon = {
                        IconButton(onClick = { isPasswordVisible = !isPasswordVisible }) {
                            Icon(
                                imageVector = if (isPasswordVisible) Icons.Default.VisibilityOff else Icons.Default.Visibility,
                                contentDescription = if (isPasswordVisible) "Hide password" else "Show password"
                            )
                        }
                    },
                    isError = passwordError != null,
                    supportingText = passwordError?.let { { Text(it) } }
                )

                OutlinedButton(
                    onClick = onGenerateRandom,
                    modifier = Modifier.fillMaxWidth()
                ) {
                    Text("Auto-generate Random User")
                }
            }
        },
        confirmButton = {
            TextButton(
                onClick = {
                    val nameVal = UserValidator.validateName(name)
                    val emailVal = UserValidator.validateEmail(email)
                    val phoneVal = UserValidator.validatePhone(phone)
                    val pwdVal = UserValidator.validatePassword(password)

                    nameError = nameVal.errorMessage
                    emailError = emailVal.errorMessage
                    phoneError = phoneVal.errorMessage
                    passwordError = pwdVal.errorMessage

                    if (nameVal.isValid && emailVal.isValid && phoneVal.isValid && pwdVal.isValid) {
                        onConfirm(
                            User(
                                name = name.trim(),
                                email = email.trim(),
                                phone = phone.trim(),
                                password = password
                            )
                        )
                    }
                }
            ) {
                Text("SAVE")
            }
        },
        dismissButton = {
            TextButton(onClick = onDismiss) {
                Text("CANCEL")
            }
        }
    )
}

@Composable
fun EditUserDialog(
    user: User,
    onDismiss: () -> Unit,
    onConfirm: (User) -> Unit
) {
    var name by remember { mutableStateOf(user.name) }
    var email by remember { mutableStateOf(user.email) }
    var phone by remember { mutableStateOf(user.phone) }

    var nameError by remember { mutableStateOf<String?>(null) }
    var emailError by remember { mutableStateOf<String?>(null) }
    var phoneError by remember { mutableStateOf<String?>(null) }

    AlertDialog(
        onDismissRequest = onDismiss,
        title = { Text("Edit User Profile") },
        text = {
            Column(
                modifier = Modifier.fillMaxWidth(),
                verticalArrangement = Arrangement.spacedBy(8.dp)
            ) {
                OutlinedTextField(
                    value = name,
                    onValueChange = {
                        name = it
                        nameError = null
                    },
                    label = { Text("Full Name") },
                    modifier = Modifier.fillMaxWidth(),
                    singleLine = true,
                    isError = nameError != null,
                    supportingText = nameError?.let { { Text(it) } }
                )

                OutlinedTextField(
                    value = email,
                    onValueChange = {
                        email = it
                        emailError = null
                    },
                    label = { Text("Email Address") },
                    modifier = Modifier.fillMaxWidth(),
                    singleLine = true,
                    keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Email),
                    isError = emailError != null,
                    supportingText = emailError?.let { { Text(it) } }
                )

                OutlinedTextField(
                    value = phone,
                    onValueChange = {
                        phone = it
                        phoneError = null
                    },
                    label = { Text("Phone Number") },
                    modifier = Modifier.fillMaxWidth(),
                    singleLine = true,
                    keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Phone),
                    isError = phoneError != null,
                    supportingText = phoneError?.let { { Text(it) } }
                )
            }
        },
        confirmButton = {
            TextButton(
                onClick = {
                    val nameVal = UserValidator.validateName(name)
                    val emailVal = UserValidator.validateEmail(email)
                    val phoneVal = UserValidator.validatePhone(phone)

                    nameError = nameVal.errorMessage
                    emailError = emailVal.errorMessage
                    phoneError = phoneVal.errorMessage

                    if (nameVal.isValid && emailVal.isValid && phoneVal.isValid) {
                        onConfirm(
                            user.copy(
                                name = name.trim(),
                                email = email.trim(),
                                phone = phone.trim()
                            )
                        )
                    }
                }
            ) {
                Text("SAVE")
            }
        },
        dismissButton = {
            TextButton(onClick = onDismiss) {
                Text("CANCEL")
            }
        }
    )
}

@Composable
fun UserCard(
    user: User,
    onEditClick: () -> Unit,
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
                TextButton(onClick = onEditClick) {
                    Icon(Icons.Default.Edit, contentDescription = null)
                    Spacer(modifier = Modifier.width(4.dp))
                    Text("EDIT")
                }
                Spacer(modifier = Modifier.width(4.dp))
                TextButton(onClick = onUpdatePwdClick) {
                    Icon(Icons.Default.Key, contentDescription = null)
                    Spacer(modifier = Modifier.width(4.dp))
                    Text("PWD")
                }
                Spacer(modifier = Modifier.width(4.dp))
                TextButton(onClick = onDeleteClick) {
                    Icon(Icons.Default.Delete, contentDescription = null, tint = Color.Red)
                    Spacer(modifier = Modifier.width(4.dp))
                    Text("DELETE", color = Color.Red)
                }
            }
        }
    }
}
