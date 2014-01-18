package test.android.gl.utils;


public class Quaternion {
	public float x;
	public float y;
	public float z;
	public float w;
	
	public Quaternion(){
		x = y = z = w = 0.f;
	}

	public Quaternion(float x, float y, float z, float w){
		this.x = x;
		this.y = y;
		this.z = z;
		this.w = w;
	    Normalize();
	}

	public Quaternion(Vector3D vec, float angle) {
	    AxisToQuaternion(vec, angle);
	}

	public Quaternion(float x, float y, float z)
	{
	    EulerToQuaternion(x, y, z);
	}

	public void Normalize()	{
	    double norm = Norm();
	    x = (float) (x / norm);
	    y = (float) (y / norm);
	    z = (float) (z / norm);
	    w = (float) (w / norm);
	}

	public double Norm()
	{
	    return Math.sqrt( x * x + y * y + z * z + w * w);
	}

	void AxisToQuaternion(Vector3D vec, float angle)
	{
	    float x,y,z;		// temporary variables of vector
	    double rad, scale;

	    if (vec.length() == 0.0f)	// if axis is zero, then return quaternion (1,0,0,0)
	    {
	        w = 1.0f;
	        x = 0.0f;
	        y = 0.0f;
	        z = 0.0f;
	        return;
	    }

	    if (vec.length() != 1.0f)
	        vec.normalize();        // make sure the axis is a unit vector

	    rad	= angle / 2.0;
	    w = (float)Math.cos(rad);
	    scale = Math.sin(rad);

	    this.x = (float)(vec.x * scale);
	    this.y = (float)(vec.y * scale);
	    this.z = (float)(vec.z * scale);

	    Normalize();		// make sure a unit quaternion turns up

	}
	
	float degToRad(float alpha){
        return alpha * 0.01745329f;
        //return alpha / 180.0f * 3.141592653589793846f;
	}

	float radToDeg(float radians){
        return radians / 0.01745329f;
        //return alpha * 180.0f / 3.141592653589793846f;
	}

	public void EulerToQuaternion(float x, float y, float z)
	{
	    float	ex, ey, ez;                         // temporary half euler angles
	    float	croll, cpitch, cyaw, sroll, spitch, syaw, // temp vars in roll, pitch, yaw (rotx, roty, rotz)
	                cpcy, spsy;
	    
	    ex = (float) (degToRad(x) / 2.f);	// convert to rads and half them
	    ey = (float) (degToRad(y) / 2.f);
	    ez = (float) (degToRad(z) / 2.f);

	    croll  = (float) Math.cos(ex);
	    cpitch = (float) Math.cos(ey);
	    cyaw   = (float) Math.cos(ez);

	    sroll  = (float) Math.sin(ex);
	    spitch = (float) Math.sin(ey);
	    syaw   = (float) Math.sin(ez);

	    cpcy = cpitch * cyaw;
	    spsy = spitch * syaw;

	    this.w = (float)(croll * cpcy + sroll * spsy);

	    this.x = (float)(sroll * cpcy - croll * spsy);
	    this.y = (float)(croll * spitch * cyaw + sroll * cpitch * syaw);
	    this.z = (float)(croll * cpitch * syaw - sroll * spitch * cyaw);

	    Normalize();
	}

	public float GetAxisAngle(Vector3D vec, float angle) {
	    float	tempAngle;		// temp angle
	    float	scale;			// temp vars

	    tempAngle = (float) Math.acos(w);

	    scale = (float) Math.sqrt(x * x + y * y + z * z);
//		scale = sin(tempAngle);

	    if (tempAngle <= 0.0f || tempAngle >= Math.PI) // make sure angle is 0 - PI
	        return angle;

	    if (Math.abs(scale) <= 0.00001f)		// angle is 0 or 360 so just simply set axis to 0,0,1 with angle 0
	    {
	        angle = 0.0f;
	        vec.x = 0.0f;
	        vec.y = 0.0f;
	        vec.z = 1.0f;
	    }
	    else
	    {
	        angle = radToDeg(tempAngle * 2.0f); // angle in degrees

	        vec.x = x / scale;
	        vec.y = y / scale;
	        vec.z = z / scale;
	        vec.normalize();

	        while (angle > 360.f) angle -= 360.0f;
	        while (angle < 0.f)   angle += 360.0f;
	        //if (vec.length() != 1.0) return false; // make sure a unit axis comes up
	    }
	    return angle; 
	}
	

	public Quaternion product(Quaternion q) {
	    double rx, ry, rz, rw;		// temp result

	    rw	= q.w*w - q.x*x - q.y*y - q.z*z;

	    rx	= q.w*x + q.x*w + q.y*z - q.z*y;
	    ry	= q.w*y + q.y*w + q.z*x - q.x*z;
	    rz	= q.w*z + q.z*w + q.x*y - q.y*x;

	    return(new Quaternion((float)rx, (float)ry, (float)rz, (float)rw));
	}
	
	public void mult(Quaternion q) {
	    double rx, ry, rz, rw;		// temp result

	    rw	= q.w*w - q.x*x - q.y*y - q.z*z;

	    rx	= q.w*x + q.x*w + q.y*z - q.z*y;
	    ry	= q.w*y + q.y*w + q.z*x - q.x*z;
	    rz	= q.w*z + q.z*w + q.x*y - q.y*x;

	    x = (float) rx;
	    y = (float) ry;
	    z = (float) rz;
	    w = (float) rw;
	}
}
