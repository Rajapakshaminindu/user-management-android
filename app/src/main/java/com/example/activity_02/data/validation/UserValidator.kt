package com.example.activity_02.data.validation

data class ValidationResult(
    val isValid: Boolean,
    val errorMessage: String? = null
)

object UserValidator {
    private val EMAIL_REGEX = "^[A-Za-z0-9+_.-]+@[A-Za-z0-9.-]+\\.[A-Za-z]{2,}$".toRegex()
    private val PHONE_REGEX = "^[+]?[0-9]{8,15}$".toRegex()

    fun validateName(name: String): ValidationResult {
        if (name.isBlank()) {
            return ValidationResult(false, "Name cannot be empty")
        }
        if (name.trim().length < 2) {
            return ValidationResult(false, "Name must be at least 2 characters")
        }
        return ValidationResult(true)
    }

    fun validateEmail(email: String): ValidationResult {
        if (email.isBlank()) {
            return ValidationResult(false, "Email cannot be empty")
        }
        if (!email.trim().matches(EMAIL_REGEX)) {
            return ValidationResult(false, "Invalid email address format")
        }
        return ValidationResult(true)
    }

    fun validatePhone(phone: String): ValidationResult {
        if (phone.isBlank()) {
            return ValidationResult(false, "Phone cannot be empty")
        }
        if (!phone.trim().matches(PHONE_REGEX)) {
            return ValidationResult(false, "Phone must contain 8-15 digits")
        }
        return ValidationResult(true)
    }

    fun validatePassword(password: String): ValidationResult {
        if (password.isBlank()) {
            return ValidationResult(false, "Password cannot be empty")
        }
        if (password.length < 6) {
            return ValidationResult(false, "Password must be at least 6 characters")
        }
        return ValidationResult(true)
    }
}
