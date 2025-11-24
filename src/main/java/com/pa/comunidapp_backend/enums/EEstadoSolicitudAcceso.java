package com.pa.comunidapp_backend.enums;

public enum EEstadoSolicitudAcceso {
    Pendiente(1),
    Aceptada(2),
    Rechazada(3);

    private final int codigo;

    EEstadoSolicitudAcceso(int codigo) {
        this.codigo = codigo;
    }

    public int getCodigo() {
        return codigo;
    }
}
