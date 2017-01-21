float4x4 World;
float4x4 WIT;
float4x4 ViewProj;
float4 LightPos;

float4 VolumeVS( float4 inPos : POSITION, float4 inNormal : NORMAL) : POSITION
{
	float4 pos4 = mul(inPos, World);
	float3 pos = float3(pos4.x / pos4.w, pos4.y / pos4.w, pos4.z / pos4.w);
	float4 normal = normalize(mul(inNormal, WIT));

	float3 newpos = pos, light = LightPos.xyz;

	if( dot ( light - pos, normal.xyz ) <= 0){
		float len = length (pos - light);
		float s = (LightPos.w + len) / len;
		newpos = (pos - light) * s + light;
	}

	float4 ret = float4(newpos.x, newpos.y, newpos.z, 1);
	return mul( ret, ViewProj );
}

technique ShadowVolume
{
	pass P0
	{
		VertexShader = compile vs_1_1 VolumeVS();
	}
}

