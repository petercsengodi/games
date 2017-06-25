/*
 * Fragment shader.
 */

#version 120

// Incoming interpolated (between vertices) texture coordinates.
attribute vec2 interpolatedTexCoord;

// Uniform 2D sampler for our texture object.
uniform sampler2D texture0;

void main() {
    // We sample texture0 at the interpolatedTexCoord
    gl_FragColor = texture(texture0, interpolatedTexCoord);
}
