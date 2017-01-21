float4x4 World;
float4x4 WIT;
float4x4 ViewProj;
float4 LightPos;

float4 VolumeVS( float4 inPos : POSITION, float4 inNormal : NORMAL) : POSITION
{
	float3 pos = mul(inPos, World);
	float3 normal = mul(inNormal, (float3x3)World);
	float s = LightPos.w;
	float3 light = float4(LightPos.xyz, 1);
	float3 newpos = pos;

	if( dot ( pos - light, normal ) >= 0){
		float len = length (pos - light);
		s = s / len;
		newpos = (pos - light) * s + pos;
	}

	return mul( newpos, ViewProj );
}

technique ShadowVolume
{
	pass P0
	{
		VertexShader = compile vs_1_1 VolumeVS();
	}
}

