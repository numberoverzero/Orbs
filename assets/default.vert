attribute vec4 a_Position;
attribute vec4 a_Normal;
attribute vec2 a_TexCoord;
attribute vec4 a_Color;

uniform mat4 u_proj;

varying vec2 v_texCoords;
varying vec4 v_color;

void main() {
	v_color = a_Color;
	v_texCoords = a_TexCoord;
	gl_Position = u_proj * a_Position;
}
