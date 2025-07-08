package tools.physics;

import static org.lwjgl.opengl.GL11.*;

public class PhysicsObject {
    public float x, y;
    public float width, height;
    public float vx, vy;
    public float ax, ay;
    public float mass;
    public float restitution = 0.0f;
    public float friction = 0.01f;
    public boolean isStatic = false;

    public boolean isOnGround = false;

    public PhysicsObject(float x, float y, float width, float height, float mass) {
        this.x = x;
        this.y = y;
        this.width = width;
        this.height = height;
        this.mass = mass;
        this.vx = 0;
        this.vy = 0;
        this.ax = 0;
        this.ay = 0;
    }

    public void applyForce(float fx, float fy) {
        if (isStatic) return; // No aplicamos fuerza a objetos estáticos
        ax += fx / mass;
        ay += fy / mass;
    }

    public void update(float dt) {
        if (isStatic) return; // No actualizamos objetos estáticos

        vy += ay * dt;
        vx += ax * dt;

        // Mover el objeto en la pantalla
        x += vx * dt;
        y += vy * dt;

        ax = 0;
        ay = 0;
    }

    public void render() {
        glColor3f(1f, 0f, 0f); // Rojo para la plataforma
        glBegin(GL_QUADS);
            glVertex2f(x, y);
            glVertex2f(x + width, y);
            glVertex2f(x + width, y + height);
            glVertex2f(x, y + height);
        glEnd();
    }
}
