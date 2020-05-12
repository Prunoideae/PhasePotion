#version 120

//A thing good for Entity blur, but failed when applied to global screen...

uniform sampler2D bgl_RenderedTexture;
uniform float time;

uniform int height;
uniform int width;

#define iResolution vec2(float(width), float(height))

float normpdf(in float x, in float sigma)
{
    return 0.39894*exp(-0.5*x*x/(sigma*sigma))/sigma;
}


void main()
{
    vec2 texcoord = vec2(gl_TexCoord[0]);
    vec2 fragCoord = texcoord*iResolution;
    vec4 t = texture2D(bgl_RenderedTexture, texcoord);
    vec3 c = t.rgb;

    //declare stuff
    const int mSize = 11;
    const int kSize = (mSize-1)/2;
    float kernel[mSize];
    vec3 final_colour = vec3(0.0);

    //create the 1-D kernel
    float sigma = 7.0;
    float Z = 0.0;
    for (int j = 0; j <= kSize; ++j)
    {
        kernel[kSize+j] = kernel[kSize-j] = normpdf(float(j), sigma);
    }

    //get the normalization factor (as the gaussian has been clamped)
    for (int j = 0; j < mSize; ++j)
    {
        Z += kernel[j];
    }

    //read out the texels
    for (int i=-kSize; i <= kSize; ++i)
    {
        for (int j=-kSize; j <= kSize; ++j)
        {
            final_colour += kernel[kSize+j]*kernel[kSize+i]*texture2D(bgl_RenderedTexture, (fragCoord.xy+vec2(float(i), float(j))) / iResolution.xy).rgb;

        }
    }

    gl_FragColor = vec4(final_colour/(Z*Z), t.a);
}