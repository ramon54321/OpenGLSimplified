#version 330

layout (location=0) in vec2 positionInwards;
layout (location=1) in vec2 texCoord;

out vec2 outTexCoord;

uniform mat4 uniformProjection;
uniform mat4 uniformModelView;


void main()
{
	vec3 newPosition = vec3(positionInwards.x, positionInwards.y, -4);
    gl_Position = uniformProjection * uniformModelView * vec4(newPosition, 1.0);
    outTexCoord = texCoord;
}
