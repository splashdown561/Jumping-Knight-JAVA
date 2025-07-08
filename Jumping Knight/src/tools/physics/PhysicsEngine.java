package tools.physics;

import java.util.ArrayList;
import java.util.List;

public class PhysicsEngine {
	private final List<PhysicsObject> objects = new ArrayList<>();

	public void addObject(PhysicsObject obj) {
		objects.add(obj);
	}

	public List<PhysicsObject> getObjects() {
		return objects;
	}

	public void update(float dt) {
		// Aplicar gravedad
		for (PhysicsObject obj : objects) {
			obj.applyForce(0, -9.8f * obj.mass);
		}

		// Actualizar movimiento
		for (PhysicsObject obj : objects) {
			obj.update(dt);
		}

		// Colisiones
		for (int i = 0; i < objects.size(); i++) {
			for (int j = i + 1; j < objects.size(); j++) {
				PhysicsObject a = objects.get(i);
				PhysicsObject b = objects.get(j);

				if (checkCollisionAABB(a, b)) {
					resolveCollisionAABB(a, b);
				}
			}
		}
	}

	private boolean checkCollisionAABB(PhysicsObject a, PhysicsObject b) {
		return (
			a.x < b.x + b.width &&
			a.x + a.width > b.x &&
			a.y < b.y + b.height &&
			a.y + a.height > b.y
		);
	}

	private void resolveCollisionAABB(PhysicsObject a, PhysicsObject b) {
		if (a.isStatic && b.isStatic) return;

		float overlapX = Math.min(a.x + a.width - b.x, b.x + b.width - a.x);
		float overlapY = Math.min(a.y + a.height - b.y, b.y + b.height - a.y);

		if (overlapX < overlapY) {
			if (!a.isStatic && !b.isStatic) {
				if (a.x < b.x) {
					a.x -= overlapX / 2;
					b.x += overlapX / 2;
				} else {
					a.x += overlapX / 2;
					b.x -= overlapX / 2;
				}
			} else if (!a.isStatic) {
				a.x += (a.x < b.x) ? -overlapX : overlapX;
			} else {
				b.x += (b.x < a.x) ? -overlapX : overlapX;
			}

			float rvx = b.vx - a.vx;
			float restitution = Math.min(a.restitution, b.restitution);
			float impulse = -(1 + restitution) * rvx;
			impulse /= (a.isStatic ? 0 : 1 / a.mass) + (b.isStatic ? 0 : 1 / b.mass);

			if (!a.isStatic) a.vx -= impulse / a.mass;
			if (!b.isStatic) b.vx += impulse / b.mass;

		} else {
			if (!a.isStatic && !b.isStatic) {
				if (a.y < b.y) {
					a.y -= overlapY / 2;
					b.y += overlapY / 2;
				} else {
					a.y += overlapY / 2;
					b.y -= overlapY / 2;
				}
			} else if (!a.isStatic) {
				a.y += (a.y < b.y) ? -overlapY : overlapY;
			} else {
				b.y += (b.y < a.y) ? -overlapY : overlapY;
			}

			float rvy = b.vy - a.vy;
			float restitution = Math.min(a.restitution, b.restitution);
			float impulse = -(1 + restitution) * rvy;
			impulse /= (a.isStatic ? 0 : 1 / a.mass) + (b.isStatic ? 0 : 1 / b.mass);

			if (!a.isStatic) a.vy -= impulse / a.mass;
			if (!b.isStatic) b.vy += impulse / b.mass;
		}
	}
}
