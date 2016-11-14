package method2;

public class Mesh {

	public float[] vertexPositions;
	public float[] vertexTextureCoordinates;
	public int[] vertexIndexArray;
	
	public Mesh(){
		vertexPositions = new float[]{
                -0.5f,  0.5f,
                -0.5f, -0.5f,
                 0.5f, -0.5f,
                 0.5f,  0.5f,
        };
        vertexTextureCoordinates = new float[]{
         		0f, 0f,
                0f, 1f,
                1f, 1f,
                1f, 0f,
        };
        vertexIndexArray = new int[]{
         		0, 1, 3, 3, 1, 2,
        };
	}
	
}
