package method2;

import static org.lwjgl.opengl.GL11.GL_FLOAT;
import static org.lwjgl.opengl.GL15.GL_ARRAY_BUFFER;
import static org.lwjgl.opengl.GL15.GL_ELEMENT_ARRAY_BUFFER;
import static org.lwjgl.opengl.GL15.GL_STATIC_DRAW;
import static org.lwjgl.opengl.GL15.glBindBuffer;
import static org.lwjgl.opengl.GL15.glBufferData;
import static org.lwjgl.opengl.GL15.glGenBuffers;
import static org.lwjgl.opengl.GL20.glVertexAttribPointer;
import static org.lwjgl.opengl.GL30.glBindVertexArray;
import static org.lwjgl.opengl.GL30.glGenVertexArrays;

import java.nio.FloatBuffer;
import java.nio.IntBuffer;

import org.lwjgl.BufferUtils;

public class VAO {

	public float[] vertexPositions;
	public float[] vertexTextureCoordinates;
	public int[] vertexIndexArray;

	public int indexCount;
	
	public int vaoId;
	public int vboIdVertexPositions;
	public int vboIdVertexTextureCoords;
	public int vboIdIndexArray;
	
	public VAO(Mesh mesh){
		buildVAO(mesh.vertexPositions, mesh.vertexTextureCoordinates, mesh.vertexIndexArray);
	}
	
	public VAO(float[] vertexPositions, float[] vertexTextureCoordinates, int[] vertexIndexArray) {
		buildVAO(vertexPositions, vertexTextureCoordinates, vertexIndexArray);
	}
	
	private void buildVAO(float[] vertexPositions, float[] vertexTextureCoordinates, int[] vertexIndexArray){
		
		this.vertexPositions = vertexPositions;
		this.vertexTextureCoordinates = vertexTextureCoordinates;
		this.vertexIndexArray = vertexIndexArray;
		
		indexCount = vertexIndexArray.length;
        
        // Vertex Array Object
        vaoId = glGenVertexArrays();
        glBindVertexArray(vaoId);
        
        // Position VBO
        int vboIdVertexPositions = glGenBuffers();
        FloatBuffer vertexPositionsBuffer = BufferUtils.createFloatBuffer(vertexPositions.length);
        vertexPositionsBuffer.put(vertexPositions).flip();
        glBindBuffer(GL_ARRAY_BUFFER, vboIdVertexPositions);
        glBufferData(GL_ARRAY_BUFFER, vertexPositionsBuffer, GL_STATIC_DRAW);
        glVertexAttribPointer(0, 2, GL_FLOAT, false, 0, 0);
        
        // Texture VBO
        vboIdVertexTextureCoords = glGenBuffers();
        FloatBuffer textureCoordinatesBuffer = BufferUtils.createFloatBuffer(vertexTextureCoordinates.length);
        textureCoordinatesBuffer.put(vertexTextureCoordinates).flip();
        glBindBuffer(GL_ARRAY_BUFFER, vboIdVertexTextureCoords);
        glBufferData(GL_ARRAY_BUFFER, textureCoordinatesBuffer, GL_STATIC_DRAW);
        glVertexAttribPointer(1, 2, GL_FLOAT, false, 0, 0);
        
        // Index VBO
        vboIdIndexArray = glGenBuffers();
        IntBuffer indicesBuffer = BufferUtils.createIntBuffer(vertexIndexArray.length);
        indicesBuffer.put(vertexIndexArray).flip();
        glBindBuffer(GL_ELEMENT_ARRAY_BUFFER, vboIdIndexArray);
        glBufferData(GL_ELEMENT_ARRAY_BUFFER, indicesBuffer, GL_STATIC_DRAW);
	}
}
