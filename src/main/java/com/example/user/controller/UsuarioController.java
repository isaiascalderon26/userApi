package com.example.user.controller;

import com.example.user.service.UsuarioService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import com.example.user.model.Usuario;
import com.example.user.exception.CorreoExistenteException;
import com.example.user.exception.FormatoInvalidoException;

import java.util.List;


@RestController
@RequestMapping("/api/usuarios")
public class UsuarioController {
    @Autowired
    private UsuarioService usuarioService;

    @PostMapping("/registro")
    public ResponseEntity<?> registrarUsuario(@RequestBody Usuario usuario) {
        try {
            Usuario registrado = usuarioService.registrarUsuario(usuario);
            return new ResponseEntity<>(registrado, HttpStatus.CREATED);
        } catch (CorreoExistenteException ex) {
            return new ResponseEntity<>("{\"mensaje\": \"El correo ya registrado\"}", HttpStatus.BAD_REQUEST);
        } catch (FormatoInvalidoException ex) {
            return new ResponseEntity<>("{\"mensaje\": \"Formato de correo o contraseña inválido\"}", HttpStatus.BAD_REQUEST);
        }
    }

    @GetMapping("/{id}")
    public ResponseEntity<?> obtenerUsuarioPorId(@PathVariable Long id) {
        try {
            Usuario usuario = usuarioService.obtenerUsuarioPorId(id);
            if (usuario != null) {
                return new ResponseEntity<>(usuario, HttpStatus.OK);
            } else {
                return new ResponseEntity<>("{\"mensaje\": \"Usuario no encontrado\"}", HttpStatus.NOT_FOUND);
            }
        } catch (Exception ex) {
            return new ResponseEntity<>("{\"mensaje\": \"Error al obtener usuario\"}", HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @GetMapping("/todos")
    public ResponseEntity<?> obtenerTodosLosUsuarios() {
        try {
            List<Usuario> usuarios = usuarioService.obtenerTodosLosUsuarios();
            return new ResponseEntity<>(usuarios, HttpStatus.OK);
        } catch (Exception ex) {
            return new ResponseEntity<>("{\"mensaje\": \"Error al obtener usuarios\"}", HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @PutMapping("/{id}")
    public ResponseEntity<?> actualizarUsuario(@PathVariable Long id, @RequestBody Usuario usuario) {
        try {
            Usuario actualizado = usuarioService.actualizarUsuario(id, usuario);
            if (actualizado != null) {
                return new ResponseEntity<>(actualizado, HttpStatus.OK);
            } else {
                return new ResponseEntity<>("{\"mensaje\": \"Usuario no encontrado\"}", HttpStatus.NOT_FOUND);
            }
        } catch (Exception ex) {
            return new ResponseEntity<>("{\"mensaje\": \"Error al actualizar usuario\"}", HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<?> eliminarUsuario(@PathVariable Long id) {
        try {
            boolean eliminado = usuarioService.eliminarUsuario(id);
            if (eliminado) {
                return new ResponseEntity<>("{\"mensaje\": \"Usuario eliminado con éxito\"}", HttpStatus.OK);
            } else {
                return new ResponseEntity<>("{\"mensaje\": \"Usuario no encontrado\"}", HttpStatus.NOT_FOUND);
            }
        } catch (Exception ex) {
            return new ResponseEntity<>("{\"mensaje\": \"Error al eliminar usuario\"}", HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }
}
