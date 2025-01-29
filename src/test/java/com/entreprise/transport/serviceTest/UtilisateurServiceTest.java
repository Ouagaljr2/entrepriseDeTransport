package com.entreprise.transport.serviceTest;

import static org.mockito.Mockito.*;
import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.*;

import com.entreprise.transport.model.Utilisateur;
import com.entreprise.transport.repository.UtilisateurRepository;
import com.entreprise.transport.service.UtilisateurService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.core.userdetails.UsernameNotFoundException;

class UtilisateurServiceTest {

    @Mock
    private UtilisateurRepository userRepository;

    @Mock
    private BCryptPasswordEncoder bCryptPasswordEncoder;

    @InjectMocks
    private UtilisateurService utilisateurService;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    // Test pour la méthode findByUsername
    @Test
    void testFindByUsername() {
        String username = "testUser";
        Utilisateur user = new Utilisateur();
        user.setUsername(username);
        when(userRepository.findByUsername(username)).thenReturn(user);

        Utilisateur foundUser = utilisateurService.findByUsername(username);

        assertNotNull(foundUser);
        assertEquals(username, foundUser.getUsername());
        verify(userRepository, times(1)).findByUsername(username);
    }

    // Test pour la méthode saveUser (cas d'utilisateur existant)
    @Test
    void testSaveUser_UsernameExists() {
        String username = "existingUser";
        Utilisateur user = new Utilisateur();
        user.setUsername(username);

        when(userRepository.findByUsername(username)).thenReturn(user);

        UsernameNotFoundException exception = assertThrows(UsernameNotFoundException.class, () -> {
            utilisateurService.saveUser(user);
        });

        assertEquals("Le nom d'utilisateur existe déjà.", exception.getMessage());
        verify(userRepository, times(1)).findByUsername(username);
        verify(userRepository, times(0)).save(user);  // On ne doit pas appeler save dans ce cas
    }

    // Test pour la méthode saveUser (cas où l'utilisateur n'existe pas)
    @Test
    void testSaveUser_NewUser() {
        String username = "newUser";
        Utilisateur user = new Utilisateur();
        user.setUsername(username);
        user.setPassword("password123");

        when(userRepository.findByUsername(username)).thenReturn(null);  // Utilisateur non existant
        when(bCryptPasswordEncoder.encode(user.getPassword())).thenReturn("encodedPassword");
        when(userRepository.save(user)).thenReturn(user);

        Utilisateur savedUser = utilisateurService.saveUser(user);
        assertNotNull(savedUser);
        assertEquals(username, savedUser.getUsername());
        assertEquals("encodedPassword", savedUser.getPassword());
        verify(userRepository, times(1)).findByUsername(username);
        verify(userRepository, times(1)).save(user);
    }

    // Test pour la méthode authenticate (authentification réussie)
    @Test
    void testAuthenticate_Success() {
        String username = "testUser";
        String password = "password123";
        Utilisateur user = new Utilisateur();
        user.setUsername(username);
        user.setPassword("$2a$10$encodedPassword123");

        when(userRepository.findByUsername(username)).thenReturn(user);
        when(bCryptPasswordEncoder.matches(password, user.getPassword())).thenReturn(true);

        boolean result = utilisateurService.authenticate(username, password);

        assertTrue(result);
        verify(userRepository, times(1)).findByUsername(username);
        verify(bCryptPasswordEncoder, times(1)).matches(password, user.getPassword());
    }

    // Test pour la méthode authenticate (échec de l'authentification)
    @Test
    void testAuthenticate_Failure() {
        String username = "testUser";
        String password = "wrongPassword";
        Utilisateur user = new Utilisateur();
        user.setUsername(username);
        user.setPassword("$2a$10$encodedPassword123");

        when(userRepository.findByUsername(username)).thenReturn(user);
        when(bCryptPasswordEncoder.matches(password, user.getPassword())).thenReturn(false);

        boolean result = utilisateurService.authenticate(username, password);

        assertFalse(result);
        verify(userRepository, times(1)).findByUsername(username);
        verify(bCryptPasswordEncoder, times(1)).matches(password, user.getPassword());
    }
}
