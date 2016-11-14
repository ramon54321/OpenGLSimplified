package method2;

import org.joml.Matrix4f;

public class Transformation {
	
	public Matrix4f modelViewMatrix;
	
	public Transformation(){
		modelViewMatrix = new Matrix4f();
        modelViewMatrix.identity();
        modelViewMatrix.translate(1, 0, 0);
	}

}
