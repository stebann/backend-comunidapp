package com.pa.comunidapp_backend.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class ChatContextoDTO {
    private Long usuarioId;
    private String mensaje;
    private Boolean incluirContextoBD; // Si debe consultar la BD
}
