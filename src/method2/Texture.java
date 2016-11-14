package method2;

import static org.lwjgl.opengl.GL11.GL_FLOAT;
import static org.lwjgl.opengl.GL11.GL_LINEAR_MIPMAP_LINEAR;
import static org.lwjgl.opengl.GL11.GL_NEAREST_MIPMAP_NEAREST;
import static org.lwjgl.opengl.GL11.GL_RGB;
import static org.lwjgl.opengl.GL11.GL_TEXTURE_2D;
import static org.lwjgl.opengl.GL11.GL_TEXTURE_MAG_FILTER;
import static org.lwjgl.opengl.GL11.GL_TEXTURE_MIN_FILTER;
import static org.lwjgl.opengl.GL11.glBindTexture;
import static org.lwjgl.opengl.GL11.glGenTextures;
import static org.lwjgl.opengl.GL11.glTexImage2D;
import static org.lwjgl.opengl.GL11.glTexParameteri;
import static org.lwjgl.opengl.GL30.glGenerateMipmap;

import java.nio.FloatBuffer;

import org.lwjgl.BufferUtils;

public class Texture {
	
	public int textureId;
	public float[] pixels;
	
	public Texture(){
		textureId = glGenTextures();
        
        glBindTexture(GL_TEXTURE_2D, textureId);
        
        pixels = new float[]{
        		1.0f, 0.0f, 0.0f,
        	    1.0f, 1.0f, 1.0f,
        	    0.0f, 0.0f, 1.0f,
        	    0.0f, 1.0f, 0.0f,
        };
        
        // GL_NEAREST
        glTexParameteri(GL_TEXTURE_2D, GL_TEXTURE_MIN_FILTER, GL_NEAREST_MIPMAP_NEAREST);
        glTexParameteri(GL_TEXTURE_2D, GL_TEXTURE_MAG_FILTER, GL_LINEAR_MIPMAP_LINEAR);
        
        FloatBuffer pixelData = BufferUtils.createFloatBuffer(pixels.length);
    	pixelData.put(pixels);
    	pixelData.flip();
    	glTexImage2D(GL_TEXTURE_2D, 0, GL_RGB, 2, 2, 0, GL_RGB, GL_FLOAT, pixelData);
    	
    	glGenerateMipmap(GL_TEXTURE_2D);
	}

}
