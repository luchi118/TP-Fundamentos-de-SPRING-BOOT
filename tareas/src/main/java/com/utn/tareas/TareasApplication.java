package com.utn.tareas;

import com.utn.tareas.model.Prioridad;
import com.utn.tareas.service.MensajeService;
import com.utn.tareas.service.TareaService;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class TareasApplication implements CommandLineRunner {

	private final TareaService tareaService;
	private final MensajeService mensajeService;

	public TareasApplication(MensajeService mensajeService, TareaService tareaService) {
		this.mensajeService = mensajeService;
		this.tareaService = tareaService;
	}

	public static void main(String[] args) {
		SpringApplication.run(TareasApplication.class, args);
	}

	@Override
	public void run(String... args) throws Exception {
		// 1. Mostrar mensaje de bienvenida
		mensajeService.mostrarBienvenida();

		// 2. Mostrar la configuración actual
		System.out.println("\n--- 2. Configuración ---");
		System.out.println(tareaService.imprimirConfiguracion());

		// 3. Listar todas las tareas iniciales
		System.out.println("\n--- 3. Tareas Iniciales ---");
		tareaService.listarTodas().forEach(System.out::println);

		// 4. Agregar una nueva tarea
		System.out.println("\n--- 4. Agregando Tarea ---");
		try {
			tareaService.agregarTarea("Escribir la documentación", Prioridad.ALTA);
			System.out.println("✅ Tarea agregada con éxito.");
		} catch (IllegalStateException e) {
			System.err.println("❌ " + e.getMessage());
		}

		// 5. Listar tareas pendientes
		System.out.println("\n--- 5. Tareas Pendientes (Después de agregar) ---");
		tareaService.listarPendientes().forEach(System.out::println);

		// 6. Marcar una tarea como completada (Usaremos la Tarea con ID=2 de la inicialización)
		Long idAMarcar = 2L;
		System.out.println("\n--- 6. Marcando Tarea ID " + idAMarcar + " como Completada ---");
		if (tareaService.marcarComoCompletada(idAMarcar)) {
			System.out.println("✅ Tarea ID " + idAMarcar + " marcada como completada.");
		} else {
			System.out.println("❌ No se encontró la Tarea con ID " + idAMarcar + ".");
		}

		// 7. Mostrar estadísticas
		System.out.println("\n--- 7. Estadísticas ---");
		System.out.println(tareaService.obtenerEstadisticas());

		// 8. Listar tareas completadas
		System.out.println("\n--- 8. Tareas Completadas ---");
		tareaService.listarCompletadas().forEach(System.out::println);

		// 9. Mostrar mensaje de despedida
		mensajeService.mostrarDespedida();
	}
}