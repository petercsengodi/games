xof 0303txt 0032
template Vector {
 <3d82ab5e-62da-11cf-ab39-0020af71e433>
 FLOAT x;
 FLOAT y;
 FLOAT z;
}

template MeshFace {
 <3d82ab5f-62da-11cf-ab39-0020af71e433>
 DWORD nFaceVertexIndices;
 array DWORD faceVertexIndices[nFaceVertexIndices];
}

template Mesh {
 <3d82ab44-62da-11cf-ab39-0020af71e433>
 DWORD nVertices;
 array Vector vertices[nVertices];
 DWORD nFaces;
 array MeshFace faces[nFaces];
 [...]
}

template MeshNormals {
 <f6f23f43-7686-11cf-8f52-0040333594a3>
 DWORD nNormals;
 array Vector normals[nNormals];
 DWORD nFaceNormals;
 array MeshFace faceNormals[nFaceNormals];
}

template Coords2d {
 <f6f23f44-7686-11cf-8f52-0040333594a3>
 FLOAT u;
 FLOAT v;
}

template MeshTextureCoords {
 <f6f23f40-7686-11cf-8f52-0040333594a3>
 DWORD nTextureCoords;
 array Coords2d textureCoords[nTextureCoords];
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

template Material {
 <3d82ab4d-62da-11cf-ab39-0020af71e433>
 ColorRGBA faceColor;
 FLOAT power;
 ColorRGB specularColor;
 ColorRGB emissiveColor;
 [...]
}

template MeshMaterialList {
 <f6f23f42-7686-11cf-8f52-0040333594a3>
 DWORD nMaterials;
 DWORD nFaceIndexes;
 array DWORD faceIndexes[nFaceIndexes];
 [Material <3d82ab4d-62da-11cf-ab39-0020af71e433>]
}

template TextureFilename {
 <a42790e1-7810-11cf-8f52-0040333594a3>
 STRING filename;
}

template VertexDuplicationIndices {
 <b8d65549-d7c9-4995-89cf-53a9a8b031e3>
 DWORD nIndices;
 DWORD nOriginalVertices;
 array DWORD indices[nIndices];
}


Mesh {
 17;
 0.000000;0.160000;0.020000;,
 0.080000;0.160000;0.020000;,
 0.020000;0.200000;0.040000;,
 0.060000;0.200000;0.040000;,
 0.000000;0.160000;0.140000;,
 0.080000;0.160000;0.140000;,
 0.020000;0.200000;0.120000;,
 0.060000;0.200000;0.120000;,
 0.020000;-0.080000;0.040000;,
 0.060000;-0.080000;0.040000;,
 0.000000;0.160000;0.020000;,
 0.080000;0.160000;0.020000;,
 0.020000;-0.080000;0.120000;,
 0.060000;-0.080000;0.120000;,
 0.000000;0.160000;0.140000;,
 0.080000;0.160000;0.140000;,
 0.040000;-0.100000;0.080000;;
 26;
 3;2,1,0;,
 3;2,3,1;,
 3;4,5,6;,
 3;7,6,5;,
 3;1,3,5;,
 3;3,7,5;,
 3;2,0,6;,
 3;0,4,6;,
 3;4,0,1;,
 3;4,1,5;,
 3;2,6,3;,
 3;6,7,3;,
 3;10,9,8;,
 3;10,11,9;,
 3;12,13,14;,
 3;15,14,13;,
 3;9,11,13;,
 3;11,15,13;,
 3;10,8,14;,
 3;8,12,14;,
 3;10,14,11;,
 3;14,15,11;,
 3;12,16,13;,
 3;16,9,13;,
 3;9,16,8;,
 3;16,12,8;;

 MeshNormals {
  17;
  -0.842152;-0.421076;-0.336861;,
  0.301511;-0.904534;-0.301511;,
  -0.346844;0.780399;-0.520266;,
  0.577350;0.808290;-0.115470;,
  -0.295241;-0.934929;0.196827;,
  0.811107;-0.324443;0.486664;,
  -0.505076;0.808122;0.303046;,
  0.481543;0.842701;0.240772;,
  -0.966746;-0.155927;-0.202705;,
  0.563887;-0.195191;-0.802454;,
  -0.702247;0.117041;-0.702247;,
  0.897926;0.254412;-0.359170;,
  -0.869570;-0.234115;0.434785;,
  0.847093;-0.163953;0.505523;,
  -0.835917;0.222911;0.501550;,
  0.821370;0.159711;0.547580;,
  0.000000;-1.000000;0.000000;;
  26;
  3;2,1,0;,
  3;2,3,1;,
  3;4,5,6;,
  3;7,6,5;,
  3;1,3,5;,
  3;3,7,5;,
  3;2,0,6;,
  3;0,4,6;,
  3;4,0,1;,
  3;4,1,5;,
  3;2,6,3;,
  3;6,7,3;,
  3;10,9,8;,
  3;10,11,9;,
  3;12,13,14;,
  3;15,14,13;,
  3;9,11,13;,
  3;11,15,13;,
  3;10,8,14;,
  3;8,12,14;,
  3;10,14,11;,
  3;14,15,11;,
  3;12,16,13;,
  3;16,9,13;,
  3;9,16,8;,
  3;16,12,8;;
 }

 MeshTextureCoords {
  17;
  0.333000;0.666000;,
  0.666000;0.666000;,
  0.333000;0.333000;,
  0.666000;0.333000;,
  0.000000;1.000000;,
  1.000000;1.000000;,
  0.000000;0.000000;,
  1.000000;0.000000;,
  0.333000;0.666000;,
  0.666000;0.666000;,
  0.333000;0.333000;,
  0.666000;0.333000;,
  0.000000;1.000000;,
  1.000000;1.000000;,
  0.000000;0.000000;,
  1.000000;0.000000;,
  0.333000;0.833000;;
 }

 MeshMaterialList {
  2;
  26;
  0,
  0,
  0,
  0,
  0,
  0,
  0,
  0,
  0,
  0,
  0,
  0,
  1,
  1,
  1,
  1,
  1,
  1,
  1,
  1,
  1,
  1,
  1,
  1,
  1,
  1;

  Material {
   1.000000;1.000000;1.000000;1.000000;;
   0.000000;
   0.000000;0.000000;0.000000;;
   0.000000;0.000000;0.000000;;

   TextureFilename {
    "leather.bmp";
   }
  }

  Material {
   1.000000;1.000000;1.000000;1.000000;;
   0.000000;
   0.000000;0.000000;0.000000;;
   0.000000;0.000000;0.000000;;

   TextureFilename {
    "leather.bmp";
   }
  }
 }

 VertexDuplicationIndices {
  17;
  17;
  0,
  1,
  2,
  3,
  4,
  5,
  6,
  7,
  8,
  9,
  10,
  11,
  12,
  13,
  14,
  15,
  16;
 }
}