package method2;

public class GameObject {
	
	public Mesh mesh;
	public Texture texture;
	public Transformation transformation;
	
	public GameObject(Mesh mesh, Texture texture, Transformation transformation){
		this.mesh = mesh;
		this.texture = texture;
		this.transformation = transformation;
	}

}
