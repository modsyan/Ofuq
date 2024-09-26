package org.ofuq.identityservice.user;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;

import static org.junit.jupiter.api.Assertions.*;

@DataJpaTest
public class UserRepositoryTest {

    @Autowired
    private UserRepository userRepository;

    private User user1;

    @BeforeEach
    public void setUp() {
        user1 = User.builder()
                    .firstName("John")
                    .lastName("Doe")
                    .username("johndoe")
                    .email("johndeo@mai.com")
                    .phoneNumber("1234567890")
                    .build();

        userRepository.save(user1);
    }

    @AfterEach
    public void tearDown() {
        if (userRepository.existsById(user1.getId())) {
            userRepository.delete(user1);
        }
    }

    @Test
    public void testSaveUser() {
        userRepository.save(user1);
        assertNotNull(user1.getId());
    }

    @Test
    public void testFindUserByUsername() {
        User foundUser = userRepository.findByUsername(user1.getUsername()).orElse(null);
        assertNotNull(foundUser);
        assertEquals(user1.getUsername(), foundUser.getUsername());
    }

    @Test
    public void testFindUserByEmail() {
        User foundUser = userRepository.findByEmail(user1.getEmail()).orElse(null);
        assertNotNull(foundUser);
        assertEquals(user1.getEmail(), foundUser.getEmail());
    }

    @Test
    public void testFindUserByUsernameNotFound() {
        assertFalse(userRepository.findByUsername("notfound").isPresent());
    }

    @Test
    public void testFindUserByEmailNotFound() {
        assertFalse(userRepository.findByEmail("notfound@mail.com").isPresent());
    }

    @Test
    public void testFindUserByUsernameNull() {
        assertFalse(userRepository.findByUsername(null).isPresent());
    }
}
