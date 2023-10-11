package com.example.user.controller;

import com.example.user.service.UsuarioService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import com.example.user.model.Usuario;
import com.example.user.exception.CorreoExistenteException;
import com.example.user.exception.FormatoInvalidoException;
import com.example.user.model.ErrorResponseBody;
import java.util.List;

@RestController
@RequestMapping("/api/usuarios")
public class UsuarioController {
    @Autowired
    private UsuarioService usuarioService;

    @PostMapping("/registro")
    @ResponseBody
    public ResponseEntity<?> registrarUsuario(@RequestBody Usuario usuario) {
        try {
            Usuario registrado = usuarioService.registrarUsuario(usuario);
            return ResponseEntity.status(HttpStatus.CREATED).body(registrado);
        } catch (CorreoExistenteException ex) {
            return ResponseEntity.badRequest().body("{\"mensaje\": \"El correo ya está registrado\"}");
        } catch (FormatoInvalidoException ex) {
            return ResponseEntity.badRequest().body("{\"mensaje\": \"Formato de correo o contraseña inválido\"}");
        }
    }

    @GetMapping("/{id}")
    @ResponseBody
    public ResponseEntity<?> obtenerUsuarioPorId(@PathVariable Long id) {
        try {
            Usuario usuario = usuarioService.obtenerUsuarioPorId(id);
            if (usuario != null) {
                return ResponseEntity.ok(usuario);
            } else {
                ErrorResponseBody error = new ErrorResponseBody("Usuario no encontrado");
                return ResponseEntity.status(HttpStatus.NOT_FOUND).body(error);
            }
        } catch (Exception ex) {
            ErrorResponseBody error = new ErrorResponseBody("Error al obtener usuario");
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(error);
        }
    }

    @GetMapping("/todos")
    @ResponseBody
    public ResponseEntity<?> obtenerTodosLosUsuarios() {
        try {
            List<Usuario> usuarios = usuarioService.obtenerTodosLosUsuarios();
            return ResponseEntity.ok(usuarios);
        } catch (Exception ex) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("{\"mensaje\": \"Error al obtener usuarios\"}");
        }
    }

    @PutMapping("/{id}")
    @ResponseBody
    public ResponseEntity<?> actualizarUsuario(@PathVariable Long id, @RequestBody Usuario usuario) {
        try {
            Usuario actualizado = usuarioService.actualizarUsuario(id, usuario);
            if (actualizado != null) {
                return ResponseEntity.ok(actualizado);
            } else {
                ErrorResponseBody error = new ErrorResponseBody("Usuario no encontrado");
                return new ResponseEntity<>(error, HttpStatus.NOT_FOUND);
            }
        } catch (Exception ex) {
            ErrorResponseBody error = new ErrorResponseBody("Error al actualizar usuario");
            return new ResponseEntity<>(error, HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @DeleteMapping("/{id}")
    @ResponseBody
    public ResponseEntity<?> eliminarUsuario(@PathVariable Long id) {
        try {
            boolean eliminado = usuarioService.eliminarUsuario(id);
            if (eliminado) {
                return ResponseEntity.ok(new ErrorResponseBody("Usuario eliminado con éxito"));
            } else {
                return new ResponseEntity<>(new ErrorResponseBody("Usuario no encontrado"), HttpStatus.NOT_FOUND);
            }
        } catch (Exception ex) {
            ErrorResponseBody error = new ErrorResponseBody("Error al eliminar usuario");
            return new ResponseEntity<>(error, HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }
}
