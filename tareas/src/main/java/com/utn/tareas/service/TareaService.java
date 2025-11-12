package com.utn.tareas.service;

import com.utn.tareas.model.Prioridad;
import com.utn.tareas.model.Tarea;
import com.utn.tareas.repository.TareaRepository;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class TareaService {
    private final TareaRepository tareaRepository;

    @Value("${app.nombre}")
    private String appNombre;

    @Value("${app.max-tareas}")
    private int maxTareas;

    @Value("${app.mostrar-estadisticas}")
    private boolean mostrarEstadisticas;

    public TareaService(TareaRepository tareaRepository) {
        this.tareaRepository = tareaRepository;
    }


    public Tarea agregarTarea(String descripcion, Prioridad prioridad) {
        if (tareaRepository.obtenerTodas().size() >= maxTareas) {
            throw new IllegalStateException(
                    "¡Error! Se alcanzó el límite máximo de tareas (" + maxTareas + ") para la aplicación " + appNombre + "."
            );
        }
        Tarea nuevaTarea = new Tarea(descripcion, false, prioridad);
        return tareaRepository.guardar(nuevaTarea);
    }

    public List<Tarea> listarTodas() {
        return tareaRepository.obtenerTodas();
    }

    public List<Tarea> listarPendientes() {
        return tareaRepository.obtenerTodas().stream()
                .filter(tarea -> !tarea.isCompletada()) // Uso de getter de Lombok
                .collect(Collectors.toList());

    }

    public List<Tarea> listarCompletadas() {
        return tareaRepository.obtenerTodas().stream()
                .filter(Tarea::isCompletada) // Referencia a método usando el getter de Lombok
                .collect(Collectors.toList());
    }

    public boolean marcarComoCompletada(Long id) {
        Optional<Tarea> tareaOptional = tareaRepository.buscarPorId(id);

        if (tareaOptional.isPresent()) {
            Tarea tarea = tareaOptional.get();
            tarea.setCompletada(true); // Uso del setter de Lombok para actualizar el estado
            // En un repositorio real, se llamaría a guardar(tarea) para persistir.
            // Aquí, como el repositorio trabaja con objetos en memoria, la lista ya se actualiza.
            return true;
        }
        return false;
    }

    public String obtenerEstadisticas() {
        if (!mostrarEstadisticas) {
            return "La visualización de estadísticas está deshabilitada por configuración (app.mostrar-estadisticas=false).";
        }
        List<Tarea> todas = tareaRepository.obtenerTodas();
        long total = todas.size();
        long completadas = todas.stream().filter(Tarea::isCompletada).count();
        long pendientes = total - completadas;

        return String.format(
                "--- Estadísticas de Tareas ---\n" +
                        "Total de tareas: %d\n" +
                        "Tareas completadas: %d\n" +
                        "Tareas pendientes: %d\n" +
                        "------------------------------",
                total, completadas, pendientes
        );
    }

    public String imprimirConfiguracion() {
        return String.format(
                "\n*** CONFIGURACIÓN DE LA APLICACIÓN ***\n" +
                        "Nombre de la aplicación (app.nombre): %s\n" +
                        "Límite máximo de tareas (app.max-tareas): %d\n" +
                        "Mostrar estadísticas (app.mostrar-estadisticas): %b\n" +
                        "**************************************",
                appNombre, maxTareas, mostrarEstadisticas
        );
    }
}

