package com.utn.tareas.model;

import lombok.*;

@Getter
@Setter
@ToString
@AllArgsConstructor
@NoArgsConstructor

public class Tarea {
    public Long id;
    public String descripcion;
    public boolean completada;
    public Prioridad prioridad;

    public Tarea(String descripcion, boolean completada, Prioridad prioridad) {
        this.descripcion = descripcion;
        this.completada = completada;
        this.prioridad = prioridad;
    }
}
