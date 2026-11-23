package com.example.activity_02.validation

import com.example.activity_02.data.validation.UserValidator
import org.junit.Assert.assertFalse
import org.junit.Assert.assertTrue
import org.junit.Test

class UserValidatorTest {

    @Test
    fun validName_returnsTrue() {
        val result = UserValidator.validateName("Jane Doe")
        assertTrue(result.isValid)
    }

    @Test
    fun blankOrShortName_returnsFalse() {
        assertFalse(UserValidator.validateName("").isValid)
        assertFalse(UserValidator.validateName("   ").isValid)
        assertFalse(UserValidator.validateName("A").isValid)
    }

    @Test
    fun validEmail_returnsTrue() {
        assertTrue(UserValidator.validateEmail("user@example.com").isValid)
        assertTrue(UserValidator.validateEmail("first.last@domain.co").isValid)
    }

    @Test
    fun invalidEmail_returnsFalse() {
        assertFalse(UserValidator.validateEmail("").isValid)
        assertFalse(UserValidator.validateEmail("plainaddress").isValid)
        assertFalse(UserValidator.validateEmail("missing@domain").isValid)
    }

    @Test
    fun validPhone_returnsTrue() {
        assertTrue(UserValidator.validatePhone("1234567890").isValid)
        assertTrue(UserValidator.validatePhone("+1234567890").isValid)
    }

    @Test
    fun invalidPhone_returnsFalse() {
        assertFalse(UserValidator.validatePhone("").isValid)
        assertFalse(UserValidator.validatePhone("123").isValid)
        assertFalse(UserValidator.validatePhone("abc1234567").isValid)
    }

    @Test
    fun validPassword_returnsTrue() {
        assertTrue(UserValidator.validatePassword("secret123").isValid)
    }

    @Test
    fun shortPassword_returnsFalse() {
        assertFalse(UserValidator.validatePassword("").isValid)
        assertFalse(UserValidator.validatePassword("12345").isValid)
    }
}
