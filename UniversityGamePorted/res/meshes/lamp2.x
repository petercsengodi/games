xof 0303txt 0032
template Header {
 <3d82ab43-62da-11cf-ab39-0020af71e433>
 WORD major;
 WORD minor;
 DWORD flags;
}

template Vector {
 <3d82ab5e-62da-11cf-ab39-0020af71e433>
 FLOAT x;
 FLOAT y;
 FLOAT z;
}

template Coords2d {
 <f6f23f44-7686-11cf-8f52-0040333594a3>
 FLOAT u;
 FLOAT v;
}

template Matrix4x4 {
 <f6f23f45-7686-11cf-8f52-0040333594a3>
 array FLOAT matrix[16];
}

template ColorRGBA {
 <35ff44e0-6c7c-11cf-8f52-0040333594a3>
 FLOAT red;
 FLOAT green;
 FLOAT blue;
 FLOAT alpha;
}

template ColorRGB {
 <d3e16e81-7835-11cf-8f52-0040333594a3>
 FLOAT red;
 FLOAT green;
 FLOAT blue;
}

template IndexedColor {
 <1630b820-7842-11cf-8f52-0040333594a3>
 DWORD index;
 ColorRGBA indexColor;
}

template Boolean {
 <537da6a0-ca37-11d0-941c-0080c80cfa7b>
 DWORD truefalse;
}

template Boolean2d {
 <4885ae63-78e8-11cf-8f52-0040333594a3>
 Boolean u;
 Boolean v;
}

template MaterialWrap {
 <4885ae60-78e8-11cf-8f52-0040333594a3>
 Boolean u;
 Boolean v;
}

template TextureFilename {
 <a42790e1-7810-11cf-8f52-0040333594a3>
 STRING filename;
}

template Material {
 <3d82ab4d-62da-11cf-ab39-0020af71e433>
 ColorRGBA faceColor;
 FLOAT power;
 ColorRGB specularColor;
 ColorRGB emissiveColor;
 [...]
}

template MeshFace {
 <3d82ab5f-62da-11cf-ab39-0020af71e433>
 DWORD nFaceVertexIndices;
 array DWORD faceVertexIndices[nFaceVertexIndices];
}

template MeshFaceWraps {
 <ed1ec5c0-c0a8-11d0-941c-0080c80cfa7b>
 DWORD nFaceWrapValues;
 array Boolean2d faceWrapValues[nFaceWrapValues];
}

template MeshTextureCoords {
 <f6f23f40-7686-11cf-8f52-0040333594a3>
 DWORD nTextureCoords;
 array Coords2d textureCoords[nTextureCoords];
}

template MeshMaterialList {
 <f6f23f42-7686-11cf-8f52-0040333594a3>
 DWORD nMaterials;
 DWORD nFaceIndexes;
 array DWORD faceIndexes[nFaceIndexes];
 [Material <3d82ab4d-62da-11cf-ab39-0020af71e433>]
}

template MeshNormals {
 <f6f23f43-7686-11cf-8f52-0040333594a3>
 DWORD nNormals;
 array Vector normals[nNormals];
 DWORD nFaceNormals;
 array MeshFace faceNormals[nFaceNormals];
}

template MeshVertexColors {
 <1630b821-7842-11cf-8f52-0040333594a3>
 DWORD nVertexColors;
 array IndexedColor vertexColors[nVertexColors];
}

template Mesh {
 <3d82ab44-62da-11cf-ab39-0020af71e433>
 DWORD nVertices;
 array Vector vertices[nVertices];
 DWORD nFaces;
 array MeshFace faces[nFaces];
 [...]
}


Header {
 1;
 0;
 1;
}

Mesh {
 26;
 -0.080000;0.040000;-0.080000;,
 0.080000;0.040000;-0.080000;,
 -0.080000;0.040000;0.080000;,
 0.080000;0.040000;0.080000;,
 -0.080000;0.360000;-0.080000;,
 0.080000;0.360000;-0.080000;,
 -0.080000;0.360000;0.080000;,
 0.080000;0.360000;0.080000;,
 -0.080000;0.040000;-0.080000;,
 0.080000;0.040000;-0.080000;,
 0.080000;0.360000;-0.080000;,
 0.080000;0.360000;-0.080000;,
 -0.080000;0.360000;-0.080000;,
 -0.080000;0.040000;-0.080000;,
 0.080000;0.040000;0.080000;,
 0.080000;0.360000;-0.080000;,
 0.080000;0.040000;0.080000;,
 -0.080000;0.040000;0.080000;,
 -0.080000;0.360000;0.080000;,
 -0.080000;0.360000;0.080000;,
 0.080000;0.360000;0.080000;,
 0.080000;0.040000;0.080000;,
 -0.080000;0.040000;0.080000;,
 -0.080000;0.360000;-0.080000;,
 -0.080000;0.360000;-0.080000;,
 -0.080000;0.040000;0.080000;;
 12;
 3;0,3,2;,
 3;3,0,1;,
 3;4,7,5;,
 3;7,4,6;,
 3;8,10,9;,
 3;11,13,12;,
 3;1,7,14;,
 3;7,1,15;,
 3;16,18,17;,
 3;19,21,20;,
 3;22,23,0;,
 3;24,25,6;;

 MeshMaterialList {
  1;
  1;
  0;

  Material {
   0.498039;0.498039;0.498039;1.000000;;
   20.000000;
   0.898039;0.898039;0.898039;;
   0.000000;0.000000;0.000000;;

   TextureFilename {
    "flame_ol.bmp";
   }
  }
 }

 MeshNormals {
  30;
  0.000000;-1.000000;0.000000;,
  -1.000000;0.000000;0.000000;,
  0.000000;-1.000000;0.000000;,
  1.000000;0.000000;0.000000;,
  0.000000;-1.000000;0.000000;,
  0.000000;-1.000000;0.000000;,
  0.000000;1.000000;0.000000;,
  0.000000;1.000000;0.000000;,
  0.000000;1.000000;0.000000;,
  -1.000000;0.000000;0.000000;,
  0.000000;1.000000;0.000000;,
  1.000000;0.000000;0.000000;,
  0.000000;0.000000;-1.000000;,
  0.000000;0.000000;-1.000000;,
  0.000000;0.000000;-1.000000;,
  0.000000;0.000000;-1.000000;,
  0.000000;0.000000;-1.000000;,
  0.000000;0.000000;-1.000000;,
  1.000000;0.000000;0.000000;,
  1.000000;0.000000;0.000000;,
  0.000000;0.000000;1.000000;,
  0.000000;0.000000;1.000000;,
  0.000000;0.000000;1.000000;,
  0.000000;0.000000;1.000000;,
  0.000000;0.000000;1.000000;,
  0.000000;0.000000;1.000000;,
  -1.000000;0.000000;0.000000;,
  -1.000000;0.000000;0.000000;,
  -1.000000;0.000000;0.000000;,
  -1.000000;0.000000;0.000000;;
  12;
  3;0,5,4;,
  3;5,0,2;,
  3;6,10,7;,
  3;10,6,8;,
  3;12,14,13;,
  3;15,17,16;,
  3;3,11,18;,
  3;11,3,19;,
  3;20,22,21;,
  3;23,25,24;,
  3;26,27,1;,
  3;28,29,9;;
 }

 MeshTextureCoords {
  26;
  1.000000;1.000000;,
  0.000000;1.000000;,
  1.000000;0.000000;,
  0.000000;0.000000;,
  0.000000;1.000000;,
  1.000000;1.000000;,
  0.000000;0.000000;,
  1.000000;0.000000;,
  0.000000;1.000000;,
  1.000000;1.000000;,
  1.000000;0.000000;,
  1.000000;0.000000;,
  0.000000;0.000000;,
  0.000000;1.000000;,
  1.000000;1.000000;,
  0.000000;0.000000;,
  0.000000;1.000000;,
  1.000000;1.000000;,
  1.000000;0.000000;,
  1.000000;0.000000;,
  0.000000;0.000000;,
  0.000000;1.000000;,
  0.000000;1.000000;,
  1.000000;0.000000;,
  1.000000;0.000000;,
  0.000000;1.000000;;
 }
}