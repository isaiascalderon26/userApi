package com.example.user.service;

import com.example.user.exception.UsuarioNotFoundException;
import com.example.user.model.Usuario;
import com.example.user.repository.UsuarioRepository;
import com.example.user.exception.CorreoExistenteException;
import com.example.user.exception.FormatoInvalidoException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.regex.Pattern;
import java.time.LocalDateTime;
import java.util.Optional;

@Service
public class UsuarioService {
    @Autowired
    private UsuarioRepository usuarioRepository;

    public Usuario registrarUsuario(Usuario usuario) {

        if (usuarioRepository.findByCorreo(usuario.getCorreo()).isPresent()) {
            throw new CorreoExistenteException("El correo ya está registrado");
        }


        if (!validarFormatoCorreo(usuario.getCorreo()) || !validarFormatoContraseña(usuario.getContraseña())) {
            throw new FormatoInvalidoException("Formato de correo o contraseña inválido");
        }


        LocalDateTime now = LocalDateTime.now();
        usuario.setCreated(now);
        usuario.setModified(now);
        usuario.setLastLogin(now);
        usuario.setToken(generarToken());


        usuario.setActive(true);
        return usuarioRepository.save(usuario);
    }

    private boolean validarFormatoCorreo(String correo) {
        // Usamos una expresión regular para verificar el formato del correo
        // Esta expresión regular permite correos en el formato "usuario@dominio.cl"
        String regex = "^[A-Za-z0-9+_.-]+@(.+)$";
        return Pattern.matches(regex, correo);
    }

    private boolean validarFormatoContraseña(String contraseña) {

        boolean contieneMayuscula = false;
        boolean contieneMinuscula = false;
        int contadorNumeros = 0;

        for (char c : contraseña.toCharArray()) {
            if (Character.isUpperCase(c)) {
                contieneMayuscula = true;
            } else if (Character.isLowerCase(c)) {
                contieneMinuscula = true;
            } else if (Character.isDigit(c)) {
                contadorNumeros++;
            }
        }

        return contieneMayuscula && contieneMinuscula && contadorNumeros >= 2;
    }

    private String generarToken() {

        return "token_de_prueba";
    }

    public Usuario obtenerUsuarioPorId(Long id) {
        Optional<Usuario> usuario = usuarioRepository.findById(id);
        return usuario.orElseThrow(() -> new UsuarioNotFoundException("Usuario no encontrado"));
    }


    public List<Usuario> obtenerTodosLosUsuarios() {
        return usuarioRepository.findAll();
    }

    public Usuario actualizarUsuario(Long id, Usuario usuarioActualizado) {
        Usuario usuarioExistente = obtenerUsuarioPorId(id);

        // Actualiza los campos necesarios del usuarioExistente con los datos de usuarioActualizado
        // ...

        return usuarioRepository.save(usuarioExistente);
    }

    public boolean eliminarUsuario(Long id) {
        Usuario usuario = obtenerUsuarioPorId(id);
        usuarioRepository.delete(usuario);
        return true;
    }

}
