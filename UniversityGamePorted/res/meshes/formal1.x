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
 12;
 -2.000000;0.000000;0.000000;,
 -1.000000;0.000000;0.000000;,
 -1.000000;0.000000;1.000000;,
 -2.000000;1.000000;0.000000;,
 0.000000;0.000000;0.000000;,
 1.000000;0.000000;0.000000;,
 0.000000;1.000000;0.000000;,
 1.000000;1.000000;0.000000;,
 0.000000;0.000000;1.000000;,
 1.000000;0.000000;1.000000;,
 0.000000;1.000000;1.000000;,
 1.000000;1.000000;1.000000;;
 16;
 3;0,1,2;,
 3;1,0,3;,
 3;2,1,3;,
 3;0,2,3;,
 3;6,5,4;,
 3;6,7,5;,
 3;8,9,10;,
 3;11,10,9;,
 3;5,7,9;,
 3;7,11,9;,
 3;6,4,10;,
 3;4,8,10;,
 3;8,4,5;,
 3;8,5,9;,
 3;6,10,7;,
 3;10,11,7;;

 MeshNormals {
  12;
  -0.707107;-0.707107;0.000000;,
  0.707107;0.000000;-0.707107;,
  0.000000;0.000000;1.000000;,
  0.000000;1.000000;0.000000;,
  -0.816497;-0.408248;-0.408248;,
  0.333333;-0.666667;-0.666667;,
  -0.408248;0.408248;-0.816497;,
  0.666667;0.666667;-0.333333;,
  -0.408248;-0.816497;0.408248;,
  0.666667;-0.333333;0.666667;,
  -0.577350;0.577350;0.577350;,
  0.577350;0.577350;0.577350;;
  16;
  3;0,1,2;,
  3;1,0,3;,
  3;2,1,3;,
  3;0,2,3;,
  3;6,5,4;,
  3;6,7,5;,
  3;8,9,10;,
  3;11,10,9;,
  3;5,7,9;,
  3;7,11,9;,
  3;6,4,10;,
  3;4,8,10;,
  3;8,4,5;,
  3;8,5,9;,
  3;6,10,7;,
  3;10,11,7;;
 }

 MeshTextureCoords {
  12;
  0.000000;0.000000;,
  0.000000;1.000000;,
  1.000000;0.000000;,
  1.000000;1.000000;,
  0.333000;0.666000;,
  0.666000;0.666000;,
  0.333000;0.333000;,
  0.666000;0.333000;,
  0.000000;1.000000;,
  1.000000;1.000000;,
  0.000000;0.000000;,
  1.000000;0.000000;;
 }

 MeshMaterialList {
  2;
  16;
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
  1;

  Material {
   1.000000;1.000000;1.000000;1.000000;;
   0.000000;
   0.000000;0.000000;0.000000;;
   0.000000;0.000000;0.000000;;

   TextureFilename {
    "tiger.bmp";
   }
  }

  Material {
   1.000000;0.000000;0.000000;1.000000;;
   0.000000;
   0.000000;0.000000;0.000000;;
   1.000000;1.000000;0.000000;;

   TextureFilename {
    "metal.bmp";
   }
  }
 }

 VertexDuplicationIndices {
  12;
  12;
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
  11;
 }
}