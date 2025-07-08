package tools.collision;

public class AABB {

    private final double epsilon = 0.0F;

    public double minX;
    public double minY;
    public double maxX;
    public double maxY;

    /**
     * Bounding box
     *
     * @param minX Minimum x side
     * @param minY Minimum y side
     * @param minZ Minimum z side
     * @param maxX Maximum x side
     * @param maxY Maximum y side
     * @param maxZ Maximum z side
     */
    public AABB(double minX, double minY, double maxX, double maxY) {
        this.minX = minX;
        this.minY = minY;
        this.maxX = maxX;
        this.maxY = maxY;
    }

    /**
     * Copy the current bounding box object
     *
     * @return Clone of the bounding box
     */
    public AABB clone() {
        return new AABB(this.minX, this.minY, this.maxX, this.maxY);
    }

    /**
     * Expand the bounding box. Positive and negative numbers controls which side of the box should grow.
     *
     * @param x Amount to expand the minX or maxX
     * @param y Amount to expand the minY or maxY
     * @param z Amount to expand the minZ or maxZ
     * @return The expanded bounding box
     */
    public AABB expand(double x, double y) {
        double minX = this.minX;
        double minY = this.minY;
        double maxX = this.maxX;
        double maxY = this.maxY;

        // Handle expanding of min/max x
        if (x < 0.0F) {
            minX += x;
        } else {
            maxX += x;
        }

        // Handle expanding of min/max y
        if (y < 0.0F) {
            minY += y;
        } else {
            maxY += y;
        }

        // Create new bounding box
        return new AABB(minX, minY, maxX, maxY);
    }

    /**
     * Expand the bounding box on both sides.
     * The center is always fixed when using grow.
     *
     * @param x
     * @param y
     * @param z
     * @return
     */
    public AABB grow(double x, double y, double z) {
        return new AABB(this.minX - x, this.minY - y, this.maxX + x,this.maxY + y);
    }

    /**
     * Check for collision on the X axis
     *
     * @param otherBoundingBox The other bounding box that is colliding with the this one.
     * @param x                Position on the X axis that is colliding
     * @return Returns the corrected x position that collided.
     */
    public double clipXCollide(AABB otherBoundingBox, double x) {
        // Check if the boxes are colliding on the Y axis
        if (otherBoundingBox.maxY <= this.minY || otherBoundingBox.minY >= this.maxY) {
            return x;
        }

        // Check for collision if the X axis of the current box is bigger
        if (x > 0.0F && otherBoundingBox.maxX <= this.minX) {
            double max = this.minX - otherBoundingBox.maxX - this.epsilon;
            if (max < x) {
                x = max;
            }
        }

        // Check for collision if the X axis of the current box is smaller
        if (x < 0.0F && otherBoundingBox.minX >= this.maxX) {
            double max = this.maxX - otherBoundingBox.minX + this.epsilon;
            if (max > x) {
                x = max;
            }
        }

        return x;
    }

    /**
     * Check for collision on the Y axis
     *
     * @param otherBoundingBox The other bounding box that is colliding with the this one.
     * @param y                Position on the X axis that is colliding
     * @return Returns the corrected x position that collided.
     */
    public double clipYCollide(AABB otherBoundingBox, double y) {
        // Check if the boxes are colliding on the X axis
        if (otherBoundingBox.maxX <= this.minX || otherBoundingBox.minX >= this.maxX) {
            return y;
        }

        // Check for collision if the Y axis of the current box is bigger
        if (y > 0.0F && otherBoundingBox.maxY <= this.minY) {
            double max = this.minY - otherBoundingBox.maxY - this.epsilon;
            if (max < y) {
                y = max;
            }
        }

        // Check for collision if the Y axis of the current box is bigger
        if (y < 0.0F && otherBoundingBox.minY >= this.maxY) {
            double max = this.maxY - otherBoundingBox.minY + this.epsilon;
            if (max > y) {
                y = max;
            }
        }

        return y;
    }

    /**
     * Check for collision on the Y axis
     *
     * @param otherBoundingBox The other bounding box that is colliding with the this one.
     * @param z                Position on the X axis that is colliding
     * @return Returns the corrected x position that collided.
     */
    public double clipZCollide(AABB otherBoundingBox, double z) {
        // Check if the boxes are colliding on the X axis
        if (otherBoundingBox.maxX <= this.minX || otherBoundingBox.minX >= this.maxX) {
            return z;
        }

        // Check if the boxes are colliding on the Y axis
        if (otherBoundingBox.maxY <= this.minY || otherBoundingBox.minY >= this.maxY) {
            return z;
        }

        return z;
    }

    /**
     * Check if the two boxes are intersecting/overlapping
     *
     * @param otherBoundingBox The other bounding box that could intersect
     * @return The two boxes are overlapping
     */
    public boolean intersects(AABB otherBoundingBox) {
        // Check on X axis
        if (otherBoundingBox.maxX <= this.minX || otherBoundingBox.minX >= this.maxX) {
            return false;
        }

        // Check on Y axis
        if (otherBoundingBox.maxY <= this.minY || otherBoundingBox.minY >= this.maxY) {
            return false;
        }
		return false;

    }

    /**
     * Move the bounding box relative.
     *
     * @param x Relative offset x
     * @param y Relative offset y
     * @param z Relative offset z
     */
    public void move(double x, double y) {
        this.minX += x;
        this.minY += y;
        this.maxX += x;
        this.maxY += y;
    }

    /**
     * Create a new bounding box with the given offset
     *
     * @param x Relative offset x
     * @param y Relative offset x
     * @param z Relative offset x
     * @return New bounding box with the given offset relative to this bounding box
     */
    public AABB offset(double x, double y, double z) {
        return new AABB(this.minX + x, this.minY + y, this.maxX + x, this.maxY + y);
    }
}