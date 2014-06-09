package gaal;

public class Point3d{

    /*
    * The x coordinate.
    */
    public double x;

    /*
    * The y coordinate.
    */
    public double y;

    /*
    * The z coordinate.
    */
    public double z;

    /*
    * Constructs and initializes a Point3d from the specified xyz coordinates.
    * @param x the x coordinate
    * @param y the y coordinate
    * @param z the z coordinate
    */
    public Point3d(double x, double y, double z){
        this.x = x;
        this.y = y;
        this.z = z;
    }

	/*
	* Returns the distance between this point and point p1.
	* @param p1 the other point
	* @return the distance
	* from: https://github.com/hharrison/vecmath/blob/master/src/javax/vecmath/Point3d.java
	*/
	public double distance(Point3d p1){
		double dx, dy, dz;

		dx = this.x-p1.x;
		dy = this.y-p1.y;
		dz = this.z-p1.z;
		return Math.sqrt(dx*dx+dy*dy+dz*dz);
	}

    //just for test
	public static void main(String[] args) {
		System.out.println("olá");
	}
}