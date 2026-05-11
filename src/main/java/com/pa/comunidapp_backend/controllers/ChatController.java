package com.pa.comunidapp_backend.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.pa.comunidapp_backend.dto.ChatMensajeDTO;
import com.pa.comunidapp_backend.dto.ChatRespuestaDTO;
import com.pa.comunidapp_backend.services.ChatContextService;
import com.pa.comunidapp_backend.services.GroqService;

import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;

@RestController
@RequestMapping("/api/chat")
@Tag(name = "ComuniBot")
public class ChatController {

    @Autowired
    private GroqService groqService;

    @Autowired
    private ChatContextService chatContextService;

    @PostMapping("/mensaje")
    public ResponseEntity<ChatRespuestaDTO> enviarMensaje(@Valid @RequestBody ChatMensajeDTO chatMensaje) {
        try {
            String contextoAdicional = null;

            // Detectar si el mensaje requiere contexto de la BD
            if (chatMensaje.getUsuarioId() != null &&
                    chatContextService.requiereContextoBD(chatMensaje.getMensaje())) {

                // Obtener contexto del usuario desde la BD
                contextoAdicional = chatContextService.obtenerContextoUsuario(chatMensaje.getUsuarioId());
            }

            // Enviar mensaje a Groq con el contexto
            ChatRespuestaDTO respuesta = groqService.enviarMensaje(
                    chatMensaje.getMensaje(),
                    contextoAdicional);

            return ResponseEntity.ok(respuesta);
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body(new ChatRespuestaDTO(
                            "Lo siento, hubo un error al procesar tu mensaje. Por favor, intenta de nuevo.",
                            "error",
                            0));
        }
    }
}
