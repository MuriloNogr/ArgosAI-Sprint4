package br.com.argos.argosaisprint3.controller;

import br.com.argos.argosaisprint3.dto.UsuarioDto;
import br.com.argos.argosaisprint3.service.UsuarioService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.mock;

@ExtendWith(MockitoExtension.class)
class AuthControllerTest {

    @Mock
    private UsuarioService usuarioService;

    @InjectMocks
    private AuthController authController;

    @BeforeEach
    void setUp() {
        authController = new AuthController(usuarioService);
    }

    @Test
    void showRegistrationForm() {
        Model model = mock(Model.class);
        String result = authController.showRegistrationForm(model);
        assertEquals("register", result, "Deve retornar a view de registro");
    }

    @Test
    void registerUser_success() {
        UsuarioDto usuarioDto = new UsuarioDto();
        BindingResult result = mock(BindingResult.class);
        Model model = mock(Model.class);

        Mockito.when(result.hasErrors()).thenReturn(false);
        String viewName = authController.registerUser(usuarioDto, result, model);
        assertEquals("redirect:/login", viewName, "Deve redirecionar para login em caso de sucesso");
    }

    @Test
    void registerUser_withErrors() {
        UsuarioDto usuarioDto = new UsuarioDto();
        BindingResult result = mock(BindingResult.class);
        Model model = mock(Model.class);

        Mockito.when(result.hasErrors()).thenReturn(true);
        String viewName = authController.registerUser(usuarioDto, result, model);
        assertEquals("register", viewName, "Deve retornar para a view de registro em caso de erro de validação");
    }
}
