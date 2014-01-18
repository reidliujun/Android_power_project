package test.android.gl.utils;

public class Vector3D {
	public float x, y, z;
	
	public Vector3D(){
		x = y = z = 0.f;
	}
	
	public Vector3D(float x, float y, float z) {
		this.x = x;
		this.y = y;
		this.z = z;
	}
	
	public void add(Vector3D vec){
		x += vec.x;
		y += vec.y;
		z += vec.z;
	}
	
	public Vector3D sum(Vector3D vec) {
		return new Vector3D(this.x + vec.x, this.y + vec.y, this.z + vec.z);
	}
	
	public void mult(Vector3D vec) {
		x *= vec.x;
		y *= vec.y;
		z *= vec.z;
	}
	
	//returns new vector (scalar product)
	public Vector3D sProd(Vector3D vec) {
		return new Vector3D(this.x * vec.x, this.y * vec.y, this.z * vec.z);
	}
	
	public float length() {
		return (float)Math.sqrt((x * x)+(y * y)+(z * z));
	}
	
	public void normalize() {
        float len = length();
        x = x / len;
        y = y / len;
        z = z / len;
    }

	public void assign(Vector3D vec) {
		this.x = vec.x;
		this.y = vec.y;
		this.z = vec.z;
	}
	
	public String toString() {
		return String.format("x = %.2f y = %.2f z = %.2f", x, y, z);
	}
}
