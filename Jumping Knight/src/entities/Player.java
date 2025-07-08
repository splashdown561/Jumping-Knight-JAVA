package entities;

import org.lwjgl.input.Keyboard;
import org.lwjgl.util.vector.Vector2f;
import tools.animator.*;
import tools.Time;

public class Player {
    private boolean flipX = false;

    private final Animator animator = new Animator();
    public Vector2f pos = new Vector2f(10, 10);

    public Player() {
        initAnimations();
    }

    public void initAnimations() {
        animator.addAnimation("IDLE", new Animation("idle", 13, 19, 4, 150));
        animator.addAnimation("WALK", new Animation("walk", 14, 18, 4, 100));
    }

    public void move() {
        float walkSpeed = 150.0f; // unidades por segundo

        boolean walking = false;

        if (Keyboard.isKeyDown(Keyboard.KEY_LEFT)) {
            flipX = true;
            pos.x -= walkSpeed * Time.deltaTime;
            walking = true;
        } else if (Keyboard.isKeyDown(Keyboard.KEY_RIGHT)) {
            flipX = false;
            pos.x += walkSpeed * Time.deltaTime;
            walking = true;
        }

        if (walking) {
            animator.play("WALK");
        } else {
            animator.play("IDLE");
        }
    }

    public void update() {
        move(); // mueve el cuerpo físico
        animator.update();
    }

    public void render(Vector2f pos, float width, float height) {
        animator.render(pos.x, pos.y, width, height, flipX);
    }
}

