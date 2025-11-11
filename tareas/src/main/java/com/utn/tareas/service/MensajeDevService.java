package com.utn.tareas.service;

import org.springframework.context.annotation.Profile;
import org.springframework.stereotype.Service;

@Service
@Profile("dev")
public class MensajeDevService implements MensajeService {
    @Override
    public void mostrarBienvenida() {
        System.out.println("\nHola dev!");
    }

    @Override
    public void mostrarDespedida() {
        System.out.println("\nAdios Dev!");
    }
}
