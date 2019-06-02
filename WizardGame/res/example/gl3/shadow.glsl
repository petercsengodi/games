/*
 * Vertex shader for shadow bodies.
 */
#version 330


float4x4 World;
float4x4 WIT;
float4x4 ViewProj;
float4 LightPos;


// Incoming vertex position, Model Space.
in vec3 position;
// Incoming vertex position, Model Space.
in vec3 normal;
// Incoming texture coordinates.
// in vec2 texCoord;

// Uniform matrices
uniform mat4 worldMatrix;
uniform mat4 witMatrix;
uniform mat4 viewProjMatrix;

// Light position
uniform vec4 lightPosition;

// Outgoing texture coordinates.
out vec2 interpolatedTexCoord;

void main() {

    vec4 tmpNormal = WIT * vec4(normal, 1);
    
    // this is still old code
    
    float s = LightPos.w;
    float4 light = float4(LightPos.xyz, 1);
    float4 newpos = pos;

    if( dot ( pos - light, tmpNormal ) >= 0){
        float len = length (pos - light);
        s = s / len;
        newpos = (pos - light) * s + pos;
    }

    return mul( newpos, ViewProj );

    // Normally gl_Position is in Clip Space and we calculate it by multiplying 
    // it with the modelToClipMatrix.
    // gl_Position = modelToClipMatrix * vec4(position, 1);

    // We assign the texture coordinate to the outgoing variable.
    // interpolatedTexCoord = texCoord;
}
