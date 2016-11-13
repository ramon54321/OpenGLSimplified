#version 330

in  vec2 outTexCoord;
out vec4 fragColor;

uniform sampler2D tex;

void main()
{
    fragColor = texture(tex, outTexCoord);
}