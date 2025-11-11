package com.utn.tareas.repository;

import com.utn.tareas.model.Prioridad;
import com.utn.tareas.model.Tarea;
import org.springframework.stereotype.Repository;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.concurrent.atomic.AtomicLong;

@Repository
public class TareaRepository {
    // Lista para almacenar tareas en memoria
    private final List<Tarea> tareas;

    // Generador de ID automático y concurrente
    private final AtomicLong nextId = new AtomicLong(0);

    public TareaRepository() {
        this.tareas = new ArrayList<>();
            // Usamos el método guardar, que se encarga de asignar el ID.
            guardar(new Tarea("Comprar víveres", false, Prioridad.ALTA));
            guardar(new Tarea("Responder correos", false, Prioridad.MEDIA));
            guardar(new Tarea("Llamar al electricista", true, Prioridad.ALTA));
            guardar(new Tarea("Hacer ejercicio", false, Prioridad.BAJA));
            guardar(new Tarea("Planificar vacaciones", false, Prioridad.MEDIA));
        }

        //Guardar Tareas
        public Tarea guardar (Tarea tarea){
            // Se usa el setter de Tarea generado por Lombok
            if (tarea.getId() == null) {
                tarea.setId(nextId.incrementAndGet());
                tareas.add(tarea);
            }
            return tarea;
        }

        //Obtener todas las tareas
        public List<Tarea> obtenerTodas () {
            return new ArrayList<>(tareas);
        }

        //Obtener tareas por id
        public Optional<Tarea> buscarPorId (Long id){
            // Se usa el getter de Tarea generado por Lombok
            return tareas.stream()
                    .filter(t -> t.getId().equals(id))
                    .findFirst();
      }
}