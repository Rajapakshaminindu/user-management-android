package com.example.activity_02.data

import org.junit.Assert.assertEquals
import org.junit.Assert.assertNotEquals
import org.junit.Test

class UserTest {

    @Test
    fun userCreation_setsFieldsProperly() {
        val user = User(
            id = 1,
            name = "Alice Smith",
            email = "alice@example.com",
            phone = "1234567890",
            password = "securePassword"
        )
        assertEquals(1, user.id)
        assertEquals("Alice Smith", user.name)
        assertEquals("alice@example.com", user.email)
        assertEquals("1234567890", user.phone)
        assertEquals("securePassword", user.password)
    }

    @Test
    fun userCopy_updatesSpecifiedFields() {
        val user = User(
            id = 2,
            name = "Bob",
            email = "bob@example.com",
            phone = "9876543210",
            password = "oldPassword"
        )
        val updated = user.copy(name = "Robert", email = "robert@example.com")
        assertEquals(2, updated.id)
        assertEquals("Robert", updated.name)
        assertEquals("robert@example.com", updated.email)
        assertEquals("9876543210", updated.phone)
        assertNotEquals(user.name, updated.name)
    }
}
