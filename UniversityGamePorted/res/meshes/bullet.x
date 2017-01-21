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
 16;
 -0.250000;-0.050000;-0.050000;,
 0.000000;-0.050000;-0.100000;,
 -0.125000;0.012500;0.100000;,
 -0.100000;0.100000;0.000000;,
 -0.050000;-0.087500;0.000000;,
 -0.037500;0.062500;0.000000;,
 -0.162500;-0.087500;-0.050000;,
 -0.187500;0.087500;0.000000;,
 0.100000;-0.050000;-0.150000;,
 0.200000;0.050000;0.100000;,
 0.000000;0.050000;0.050000;,
 0.100000;0.150000;0.000000;,
 0.187500;-0.012500;-0.037500;,
 0.175000;0.125000;-0.012500;,
 0.075000;0.087500;0.125000;,
 0.025000;0.000000;-0.037500;;
 24;
 3;1,0,3;,
 3;0,2,3;,
 3;4,1,2;,
 3;5,2,1;,
 3;5,1,3;,
 3;5,3,2;,
 3;6,4,0;,
 3;6,0,1;,
 3;6,1,4;,
 3;7,4,2;,
 3;7,2,0;,
 3;7,0,4;,
 3;12,8,9;,
 3;12,9,10;,
 3;12,10,8;,
 3;13,9,8;,
 3;13,8,11;,
 3;13,11,9;,
 3;14,10,9;,
 3;14,9,11;,
 3;14,11,10;,
 3;15,8,10;,
 3;15,10,11;,
 3;15,11,8;;

 MeshNormals {
  16;
  -0.670532;0.536426;-0.512478;,
  0.572330;0.122642;-0.810801;,
  0.532148;0.115684;0.838711;,
  -0.327514;0.824069;-0.462218;,
  -0.230701;-0.669033;0.706521;,
  0.852266;0.269137;0.448561;,
  -0.076697;-0.920358;-0.383482;,
  -0.285997;-0.776278;0.561781;,
  0.458118;-0.128273;-0.879587;,
  0.872091;-0.206937;0.443436;,
  -0.421205;-0.776889;0.468006;,
  -0.337400;0.899733;-0.276841;,
  -0.101015;-0.909137;0.404061;,
  0.813734;0.348743;-0.464991;,
  -0.196116;0.588348;0.784465;,
  -0.707107;0.424264;-0.565685;;
  24;
  3;1,0,3;,
  3;0,2,3;,
  3;4,1,2;,
  3;5,2,1;,
  3;5,1,3;,
  3;5,3,2;,
  3;6,4,0;,
  3;6,0,1;,
  3;6,1,4;,
  3;7,4,2;,
  3;7,2,0;,
  3;7,0,4;,
  3;12,8,9;,
  3;12,9,10;,
  3;12,10,8;,
  3;13,9,8;,
  3;13,8,11;,
  3;13,11,9;,
  3;14,10,9;,
  3;14,9,11;,
  3;14,11,10;,
  3;15,8,10;,
  3;15,10,11;,
  3;15,11,8;;
 }

 MeshTextureCoords {
  16;
  0.450857;0.048346;,
  0.823010;0.554895;,
  0.325353;0.907112;,
  0.818979;0.154696;,
  0.808237;0.744980;,
  0.497584;0.462047;,
  0.322674;0.046928;,
  0.269381;0.889283;,
  0.726322;0.227939;,
  0.410835;0.860781;,
  0.066907;0.042312;,
  0.536753;0.815046;,
  0.829564;0.807791;,
  0.616306;0.961051;,
  0.251433;0.466430;,
  0.340997;0.297184;;
 }

 MeshMaterialList {
  2;
  24;
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
  1;

  Material {
   1.000000;1.000000;1.000000;1.000000;;
   0.000000;
   0.000000;0.000000;0.000000;;
   0.000000;0.000000;0.000000;;

   TextureFilename {
    "flame_ol.bmp";
   }
  }

  Material {
   1.000000;1.000000;1.000000;1.000000;;
   0.000000;
   0.000000;0.000000;0.000000;;
   0.000000;0.000000;0.000000;;

   TextureFilename {
    "flame_ol.bmp";
   }
  }
 }

 VertexDuplicationIndices {
  16;
  16;
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
  15;
 }
}