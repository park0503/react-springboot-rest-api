package com.example.gccoffee.domain;

import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class EmailTest {

    @Test
    public void testInvalidEmail() {
        Assertions.assertThatThrownBy(() -> {
            Email email = new Email("acccc");
        }).isInstanceOf(IllegalArgumentException.class)
                .hasMessage("Invalid email address.");
    }

    @Test
    public void testBlankEmail() {
        Assertions.assertThatThrownBy(() -> {
                    Email email = new Email("");
                }).isInstanceOf(IllegalArgumentException.class)
                .hasMessage("address should not be null");
    }

    @Test
    public void testValidEmail() {
        Email email = new Email("hello@gmail.com");
        assertEquals("hello@gmail.com", email.getAddress());
    }

    @Test
    public void testEqEmail() {
        Email email = new Email("hello@gmail.com");
        Email email2 = new Email("hello@gmail.com");
        assertEquals(email, email2);
    }
}