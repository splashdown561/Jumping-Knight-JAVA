package graphics;

import org.lwjgl.*;
import org.lwjgl.opengl.*;

import entities.Player;
import tools.Time;
import tools.physics.PhysicsEngine;
import tools.physics.PhysicsObject;

import static org.lwjgl.opengl.GL11.*;

public class Window {
    public void create_window(int width, int height) {
        try {
            Display.setDisplayMode(new DisplayMode(width, height));
            Display.create();
        } catch (LWJGLException e) {
            e.printStackTrace();
            System.exit(1);
        }

        // Configuración de la proyección 2D
        glMatrixMode(GL_PROJECTION);
        glLoadIdentity();
        glOrtho(0, width, height, 0, -1, 1); // Coordenadas 2D
        glMatrixMode(GL_MODELVIEW);
        glLoadIdentity();

        glClearColor(0.0f, 0.5f, 1.0f, 1.0f); // Fondo azul
        glEnable(GL_TEXTURE_2D);
        glDisable(GL_DEPTH_TEST); // Desactivar test de profundidad para 2D

        // Crear jugador
        Player player = new Player(); // Crear el jugador
        player.initAnimations();


        // Bucle principal
        while (!Display.isCloseRequested()) {
            glClear(GL_COLOR_BUFFER_BIT); // Limpiar la pantalla
            glLoadIdentity(); // Reiniciar la matriz

            Time.update(); // deltaTime

            // Actualizar el jugador
            player.update();
            // Renderizar el jugador después de las físicas
            player.render(player.pos, 26, 38);

            Display.update(); // Actualiza la pantalla
            Display.sync(60); // Limitar a 60 FPS
        }

        Display.destroy();
    }
}

