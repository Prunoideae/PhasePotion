#version 120

uniform sampler2D bgl_RenderedTexture;
uniform float time;

uniform int height;
uniform int width;

uniform float intensity;

float roundToNearest(float f1, float f2){
    if (mod(f1, f2) > 1.-mod(f1, f2)){
        return f2*floor(f1/f2);
    } else {
        return f2*(floor(f1/f2)+1.);
    }
}

void main(){
    vec2 uv = vec2(gl_TexCoord[0]);
    float density = 1.+intensity*12.;
    uv.x = roundToNearest(uv.x, density/(float(width)));
    uv.y = roundToNearest(uv.y, density/(float(height)));
    uv.y += .004*sin(float(time/20.));
    gl_FragColor = texture2D(bgl_RenderedTexture, uv);
}