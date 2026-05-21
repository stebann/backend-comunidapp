package com.pa.comunidapp_backend.services;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import com.pa.comunidapp_backend.dto.ChatRespuestaDTO;

@Service
public class GroqService {

    @Value("${groq.api-key}")
    private String apiKey;

    @Value("${groq.api-url}")
    private String apiUrl;

    @Value("${groq.model}")
    private String model;

    private final RestTemplate restTemplate = new RestTemplate();

    // Contexto del sistema sobre ComunidApp
    private static final String SYSTEM_PROMPT = """
            Eres ComuniBot, el asistente virtual de ComunidApp, una plataforma de préstamo y venta de artículos entre usuarios de una comunidad.

            Tu función es ayudar a los usuarios con:
            - Información sobre cómo usar la plataforma
            - Explicar el proceso de préstamo y devolución de artículos
            - Ayudar con la publicación de artículos
            - Explicar las categorías y estados de artículos
            - Resolver dudas sobre gestiones y transacciones
            - Información sobre comercios en la plataforma

            Características de ComunidApp:
            - Los usuarios pueden publicar artículos para préstamo o venta
            - Los artículos tienen estados: Disponible, Prestado
            - Las transacciones tienen estados: Pendiente, Aceptada, Rechazada, DevolucionPendiente, Devuelto
            - Hay un sistema de calificaciones entre usuarios
            - Existe un plan Premium con beneficios adicionales
            - Los usuarios pueden gestionar comercios

            GUÍA DE USO DE LA APLICACIÓN:

            Navegación Principal (Menú lateral):
            - Inicio: Página principal de la aplicación
            - Explorar: Para buscar y explorar artículos disponibles
            - Mis Artículos: Gestión de tus artículos publicados
            - Comercios: Sección de comercios registrados
            - ComuniBot: Chat de asistencia (donde estás ahora)

            Cómo CREAR/PUBLICAR un artículo:
            1. Ve al menú lateral y selecciona "Mis Artículos"
            2. En el panel izquierdo, haz clic en el botón verde "+ Crear publicación"
            3. Completa el formulario con la información del artículo
            4. Haz clic en "Publicar" para crear tu artículo

            Gestiones en "Mis Artículos" (panel izquierdo):
            - Mis Artículos: Ver todos tus artículos publicados
            - Bandeja de Entrada: Solicitudes recibidas de otros usuarios
            - Mis Solicitudes: Solicitudes que has enviado a otros usuarios
            - Préstamos: Gestión de préstamos activos
            - Filtros: Opciones para filtrar artículos

            Cómo BUSCAR artículos:
            1. Ve a "Explorar" en el menú principal
            2. Usa los filtros disponibles para refinar tu búsqueda
            3. Haz clic en un artículo para ver sus detalles
            4. Puedes solicitar el artículo si está disponible

            Cómo SOLICITAR un artículo:
            1. Encuentra el artículo en "Explorar"
            2. Haz clic en el artículo para ver detalles
            3. Haz clic en "Solicitar" o "Enviar solicitud"
            4. El propietario recibirá tu solicitud en su "Bandeja de Entrada"

            Cómo GESTIONAR solicitudes recibidas:
            1. Ve a "Mis Artículos"
            2. En el panel izquierdo, selecciona "Bandeja de Entrada"
            3. Verás las solicitudes pendientes
            4. Puedes Aceptar o Rechazar cada solicitud

            IMPORTANTE - Reglas de conversación:
            - NO saludes en cada mensaje. Solo saluda cuando el usuario te salude primero (ej: "hola", "buenos días", etc.)
            - Si el usuario hace una pregunta directa, responde directamente sin saludar
            - Mantén un tono conversacional natural, como si fueras un asistente que ya está en medio de una conversación
            - Responde de manera amigable, clara y concisa
            - NO enumeres todas las opciones de los campos del formulario a menos que el usuario lo pida específicamente
            - Proporciona instrucciones paso a paso simples basadas en la interfaz real de la aplicación
            - Si no sabes algo específico, sugiere contactar al soporte
            - Usa el contexto proporcionado para dar respuestas precisas basadas en los datos del usuario
            """;

    public ChatRespuestaDTO enviarMensaje(String mensajeUsuario, String contextoAdicional) {
        try {
            // Construir el prompt del sistema con contexto adicional si existe
            String systemPromptFinal = SYSTEM_PROMPT;
            if (contextoAdicional != null && !contextoAdicional.isEmpty()) {
                systemPromptFinal = SYSTEM_PROMPT + "\n" + contextoAdicional;
            }

            // Construir el cuerpo de la petición
            Map<String, Object> requestBody = new HashMap<>();
            requestBody.put("model", model);
            requestBody.put("messages", List.of(
                    Map.of("role", "system", "content", systemPromptFinal),
                    Map.of("role", "user", "content", mensajeUsuario)));
            requestBody.put("temperature", 0.7);
            requestBody.put("max_tokens", 1024);

            // Configurar headers
            HttpHeaders headers = new HttpHeaders();
            headers.setContentType(MediaType.APPLICATION_JSON);
            headers.setBearerAuth(apiKey);

            HttpEntity<Map<String, Object>> entity = new HttpEntity<>(requestBody, headers);

            // Hacer la petición a Groq
            ResponseEntity<Map> response = restTemplate.exchange(
                    apiUrl,
                    HttpMethod.POST,
                    entity,
                    Map.class);

            // Extraer la respuesta
            Map<String, Object> responseBody = response.getBody();
            if (responseBody != null && responseBody.containsKey("choices")) {
                List<Map<String, Object>> choices = (List<Map<String, Object>>) responseBody.get("choices");
                if (!choices.isEmpty()) {
                    Map<String, Object> message = (Map<String, Object>) choices.get(0).get("message");
                    String contenido = (String) message.get("content");

                    // Obtener tokens usados
                    Map<String, Object> usage = (Map<String, Object>) responseBody.get("usage");
                    Integer tokensUsados = usage != null ? (Integer) usage.get("total_tokens") : 0;

                    return new ChatRespuestaDTO(contenido, model, tokensUsados);
                }
            }

            throw new RuntimeException("No se pudo obtener respuesta de Groq");

        } catch (Exception e) {
            throw new RuntimeException("Error al comunicarse con Groq: " + e.getMessage(), e);
        }
    }
}
