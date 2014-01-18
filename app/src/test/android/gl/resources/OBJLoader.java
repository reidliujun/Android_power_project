package test.android.gl.resources;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.InputStreamReader;
import java.nio.FloatBuffer;
import java.util.ArrayList;

import android.content.Context;

//-----------------------------------------------------------------------------
class DATABLOCK3F {
   float vtn1, vtn2, vtn3;
   public DATABLOCK3F(){
	   vtn1 = vtn2 = vtn3 = 0.f;
   }
}

class RESMesh{
    public int count3v;
    public VERTEX[] data3v;
    public int count4v;
    public VERTEX[] data4v;
    
    public RESMesh(){
    	count3v = 0;
        data3v = null;
        count4v = 0;
        data4v = null;
    }
}
//class RESMesh{
//    public FloatBuffer vertices;
//    public FloatBuffer textures;
//    public FloatBuffer normals;
//    public IntBuffer vIndexes;
//    public IntBuffer tIndexes;
//    public IntBuffer nIndexes;
//    
//    public RESMesh(){
//    	vertices = textures = normals = null;
//    }
//}

class VERTEX {
    float posX;
    float posY;
    float posZ;
    float normX;
    float normY;
    float normZ;
    char colR;
    char colG;
    char colB;
    char colA;
    float texS;
    float texT;
}


//-----------------------------------------------------------------------------
class DATABLOCK4I {
   int v1, v2, v3, v4;
   int t1, t2, t3, t4;
   int n1, n2, n3, n4;
   public DATABLOCK4I(){
	   v1 =  v2 = v3 = v4 = t1 = t2 = t3 = t4 = n1 = n2 = n3 = n4 = 0;
   }
}

class OBJLoader implements IMeshLoader {
	private ArrayList<DATABLOCK3F> vBuffer = null;
	private ArrayList<DATABLOCK3F> tBuffer = null;
	private ArrayList<DATABLOCK3F> nBuffer = null;
	
	private ArrayList<DATABLOCK4I> f3vtnBuffer = null;
	private ArrayList<DATABLOCK4I> f4vtnBuffer = null;
	private Context context;
	//-----------------------------------------------------------------------------
	OBJLoader(Context c) {
		context = c;
	    //this->factory = resFactory;
	    vBuffer = new ArrayList<DATABLOCK3F>();
	    tBuffer = new ArrayList<DATABLOCK3F>();
	    nBuffer = new ArrayList<DATABLOCK3F>();

	    f3vtnBuffer = new ArrayList<DATABLOCK4I>();
	    f4vtnBuffer = new ArrayList<DATABLOCK4I>();

	}
	
	OBJLoader() {
		this(null);
	}

	public Mesh load(String fileName) throws Exception {
	    int elmCount = 0;
	    boolean isTexturesData = false;
	    boolean isNormalsData = false;
	    DATABLOCK3F vtnData = null;
	    DATABLOCK4I fData = null;
//	    RESMesh resMesh = new RESMesh();
	    Mesh mesh = new Mesh();
	    
	    
	    BufferedReader reader = new BufferedReader(new InputStreamReader(context.getAssets().open(fileName)));
//	    BufferedReader reader = new BufferedReader(new FileReader(fileName));
	    String line;
	    String []tokens = null;
	    
	    initBuffers();
	    while((line = reader.readLine()) != null)
	    {
	    	tokens = line.split("[\r\t\f\n/ ]");	
		    elmCount = tokens.length - 1;
	    	if (elmCount == 0)
	    		continue;
	    	vtnData =  new DATABLOCK3F();
	    	fData = new DATABLOCK4I();
	    	if(tokens[0].equals("v")){
	    		if(elmCount == 3){
	    			vtnData.vtn1 = Float.parseFloat(tokens[1]);
	    			vtnData.vtn2 = Float.parseFloat(tokens[2]);
	    			vtnData.vtn3 = Float.parseFloat(tokens[3]);
	    			vBuffer.add(vtnData);
	    			continue;
	    		}
	    	}else if(tokens[0].equals("vt")){
	    		if(elmCount == 2){
	    			vtnData.vtn1 = Float.parseFloat(tokens[1]);
	    			vtnData.vtn2 = Float.parseFloat(tokens[2]);
	    	        tBuffer.add(vtnData);
		            isTexturesData = true;
	    			continue;
	    		}else if(elmCount == 3){
	    			vtnData.vtn1 = Float.parseFloat(tokens[1]);
	    			vtnData.vtn2 = Float.parseFloat(tokens[2]);
	    			vtnData.vtn3 = Float.parseFloat(tokens[3]);
	    			tBuffer.add(vtnData);
		            isTexturesData = true;
	    			continue;
	    		}
	    	}else if(tokens[0].equals("vn")){
	    		if(elmCount == 3){
	    			vtnData.vtn1 = Float.parseFloat(tokens[1]);
	    			vtnData.vtn2 = Float.parseFloat(tokens[2]);
	    			vtnData.vtn3 = Float.parseFloat(tokens[3]);
	    			nBuffer.add(vtnData);
		            isNormalsData = true;
	    			continue;
	    		}
	    	}

	        if (isTexturesData && isNormalsData && tokens[0].equals("f")) {
//	        	if(elmCount == 9 || elmCount == 12){
	        	if(elmCount == 9){
		        	fData.v1 = Integer.parseInt(tokens[1]); 
		        	fData.t1 = Integer.parseInt(tokens[2]);
		        	fData.n1 = Integer.parseInt(tokens[3]);
		        	
		        	fData.v2 = Integer.parseInt(tokens[4]); 
		        	fData.t2 = Integer.parseInt(tokens[5]);
		        	fData.n2 = Integer.parseInt(tokens[6]);
		        	
		        	fData.v3 = Integer.parseInt(tokens[7]); 
		        	fData.t3 = Integer.parseInt(tokens[8]);
		        	fData.n3 = Integer.parseInt(tokens[9]);
	        	}
//	        	if(elmCount == 12){	        	
//		        	fData.v4 = Integer.parseInt(tokens[10]); 
//		        	fData.t4 = Integer.parseInt(tokens[11]);
//		        	fData.n4 = Integer.parseInt(tokens[12]);
//	        	}
	        } else if (isTexturesData && tokens[0].equals("f")) {
//	        	if(elmCount == 6 || elmCount == 8){
	        	if(elmCount == 6){
		        	fData.v1 = Integer.parseInt(tokens[1]); 
		        	fData.t1 = Integer.parseInt(tokens[2]);
		        	
		        	fData.v2 = Integer.parseInt(tokens[3]); 
		        	fData.t2 = Integer.parseInt(tokens[4]);
		        	
		        	fData.v3 = Integer.parseInt(tokens[5]); 
		        	fData.t3 = Integer.parseInt(tokens[6]);
	        	}
//	        	if(elmCount == 8){		        	
//		        	fData.v4 = Integer.parseInt(tokens[7]); 
//		        	fData.t4 = Integer.parseInt(tokens[8]);
//	        	}
	        } else if (isNormalsData && tokens[0].equals("f")) {
//	        	if(elmCount == 6 || elmCount == 8){
	        	if(elmCount == 9){      // tokens : f, decimal, empty, decimal, decimal, empty, decimal, decimal, empty, decimal
		        	fData.v1 = Integer.parseInt(tokens[1]); 
		        	fData.n1 = Integer.parseInt(tokens[3]);
		        	
		        	fData.v2 = Integer.parseInt(tokens[4]); 
		        	fData.n2 = Integer.parseInt(tokens[6]);
		        	
		        	fData.v3 = Integer.parseInt(tokens[7]); 
		        	fData.n3 = Integer.parseInt(tokens[9]);
	        	} 
//	        	if(elmCount == 8){		        	
//		        	fData.v4 = Integer.parseInt(tokens[7]); 
//		        	fData.n4 = Integer.parseInt(tokens[8]);
//	        	}
	        }else if (tokens[0].equals("f")){
//	        	if(elmCount == 3 || elmCount == 4){
	        	if(elmCount == 3){
		        	fData.v1 = Integer.parseInt(tokens[1]); 
		        	fData.v2 = Integer.parseInt(tokens[2]); 
		        	fData.v3 = Integer.parseInt(tokens[3]); 
	        	}
//	        	if(elmCount == 4){ 
//		        	fData.v4 = Integer.parseInt(tokens[4]);
//	        	}
	        }

	        if (elmCount == 12 || elmCount == 8 || elmCount == 4) {
	            f4vtnBuffer.add(fData);
	        } else if (elmCount == 9 || elmCount == 6 || elmCount == 3) {
	            f3vtnBuffer.add(fData);
	        }
	    }
	    reader.close();
	    initMeshResource(mesh);
	    return mesh;
	}

	// Inits Mesh resource with data.
	void initMeshResource(Mesh mesh) {
	    DATABLOCK3F []verts = null;
	    DATABLOCK3F []textures = null;
	    DATABLOCK3F []normals = null;
	    DATABLOCK4I fData = null;//new DATABLOCK4I();
	    int vIdx =0, tIdx = 0, nIdx = 0;

	 // moving vertices, normals and texture coords into arrays
	    if (vBuffer.size() > 0) {
	    	Object[] vertices = vBuffer.toArray();
	    	verts = new DATABLOCK3F[vBuffer.size()];
	    	for(int i = 0; i < vBuffer.size(); i++){
	    		verts[i] = (DATABLOCK3F)vertices[i];
	    	}
	    }

	    // textures
	    if (tBuffer.size() > 0) {
	    	Object[] text = tBuffer.toArray();
	    	textures = new DATABLOCK3F[tBuffer.size()];
	    	for(int i = 0; i < tBuffer.size(); i++){
	    		textures[i] = (DATABLOCK3F)text[i];
	    	}
	    }

	    // normals
	    if (nBuffer.size() > 0) {
	    	Object []norm = nBuffer.toArray();
	    	normals = new DATABLOCK3F[nBuffer.size()];
	    	for(int i = 0; i < nBuffer.size(); i++){
	    		normals[i] = (DATABLOCK3F)norm[i];
	    	}
	    }
	    
	    
	    // generating 3-vert faces to mesh resource
	    if (f3vtnBuffer.size() > 0) {
//	        meshRes.count3v = f3vtnBuffer.size() * 3;
//	        meshRes.data3v = new VERTEX[(int)meshRes.count3v]; // !!!
	        FloatBuffer vertBuffer = mesh.getInitVertexBuffer(f3vtnBuffer.size() * 9);
	        FloatBuffer texBuffer = mesh.getInitTextureBuffer(f3vtnBuffer.size() * 9);
	        FloatBuffer normBuffer = mesh.getInitNormalBuffer(f3vtnBuffer.size() * 9);
	        int i = 0;
	        try{
	        for (int k = 0; k < f3vtnBuffer.size(); k++) {
	            fData = f3vtnBuffer.get(k);

	            for (int j=0; j < 3; j++) {	// v1, v2, v3
	                switch (j) {
	                    case 0:
	                        vIdx = fData.v1 - 1;
	                        tIdx = fData.t1 - 1;
	                        nIdx = fData.n1 - 1;
	                        break;
	                    case 1:
	                        vIdx = fData.v2 - 1;
	                        tIdx = fData.t2 - 1;
	                        nIdx = fData.n2 - 1;
	                        break;
	                    case 2:
	                        vIdx = fData.v3 - 1;
	                        tIdx = fData.t3 - 1;
	                        nIdx = fData.n3 - 1;
	                        break;
	                }
	                vertBuffer.put(verts[vIdx].vtn1);
	                vertBuffer.put(verts[vIdx].vtn2);
	                vertBuffer.put(verts[vIdx].vtn3);
	                if (normals != null) {
	                    normBuffer.put(normals[nIdx].vtn1);
	                    normBuffer.put(normals[nIdx].vtn2);
	                    normBuffer.put(normals[nIdx].vtn3);
	                }

	                if (textures != null) {
	                    texBuffer.put(textures[tIdx].vtn1);
	                    texBuffer.put(textures[tIdx].vtn2);
	                } 
	                i++;
	            }
	        }
	        mesh.init(vertBuffer, normBuffer, texBuffer, null, null);
	        }catch(Exception e){
		    	e.printStackTrace();
		    }
	    }
	    
	    
//	    // generating 4-vert faces to mesh resource
//	    if (f4vtnBuffer.size() > 0) {
//	        meshRes.count4v = f4vtnBuffer.size() * 4;
//	        meshRes.data4v = new VERTEX[meshRes.count4v];
//	        int i = 0;
//	        for (int k = 0; k < f4vtnBuffer.size(); k++) {
//	            fData = f4vtnBuffer.get(k);
//
//	            for (int j=0; j < 4; j++) {	// v1, v2, v3, v4
//	                switch (j) {
//	                    case 0:
//	                        vIdx = fData.v1 - 1;
//	                        tIdx = fData.t1 - 1;
//	                        nIdx = fData.n1 - 1;
//	                        break;
//	                    case 1:
//	                        vIdx = fData.v2 - 1;
//	                        tIdx = fData.t2 - 1;
//	                        nIdx = fData.n2 - 1;
//	                        break;
//	                    case 2:
//	                        vIdx = fData.v3 - 1;
//	                        tIdx = fData.t3 - 1;
//	                        nIdx = fData.n3 - 1;
//	                        break;
//	                    case 3:
//	                        vIdx = fData.v4 - 1;
//	                        tIdx = fData.t4 - 1;
//	                        nIdx = fData.n4 - 1;
//	                        break;
//	                }
//	                meshRes.data4v[i] = new VERTEX();
//	                meshRes.data4v[i].posX = verts[vIdx].vtn1;
//	                meshRes.data4v[i].posY = verts[vIdx].vtn2;
//	                meshRes.data4v[i].posZ = verts[vIdx].vtn3;
//	                meshRes.data4v[i].normX = 0;
//	                meshRes.data4v[i].normY = 0;
//	                meshRes.data4v[i].normZ = 0;
//
//	                if (normals != null) {
//	                    meshRes.data4v[i].normX = normals[nIdx].vtn1;
//	                    meshRes.data4v[i].normY = normals[nIdx].vtn2;
//	                    meshRes.data4v[i].normZ = normals[nIdx].vtn3;
//	                } else {
//	                    meshRes.data4v[i].normX = 0;
//	                    meshRes.data4v[i].normY = 0;
//	                    meshRes.data4v[i].normZ = 0;
//	                }
//
//	                meshRes.data4v[i].colR = 255;
//	                meshRes.data4v[i].colG = 255;
//	                meshRes.data4v[i].colB = 255;
//	                meshRes.data4v[i].colA = 255;
//
//	                if (textures != null) {
//	                    meshRes.data4v[i].texS = textures[tIdx].vtn1;
//	                    meshRes.data4v[i].texT = textures[tIdx].vtn2;
//	                } else {
//	                    meshRes.data4v[i].texS = 0;
//	                    meshRes.data4v[i].texT = 0;
//	                }
//	                i++;
//	            }
//	            
//	        }
//	    }
	    
	}
	
//	// Inits Mesh resource with data.
//	void initMeshResource(RESMesh meshRes) {
//	    DATABLOCK3F []verts = null;
//	    DATABLOCK3F []textures = null;
//	    DATABLOCK3F []normals = null;
//	    DATABLOCK4I fData = null;//new DATABLOCK4I();
//	    int vIdx =0, tIdx = 0, nIdx = 0;
//	    // moving vertices, normals and texture coords into arrays
//	    if (vBuffer.size() > 0) {
//	    	meshRes.vertices = FloatBuffer.allocate(vBuffer.size()*3); 
//	    	Object[] vertices = vBuffer.toArray();
//	    	for(int i = 0; i < vBuffer.size(); i++){
//	    		meshRes.vertices.put(vBuffer.get(i).vtn1);
//	    		meshRes.vertices.put(vBuffer.get(i).vtn2);
//	    		meshRes.vertices.put(vBuffer.get(i).vtn3);	    		
//	    	}
//	    	
//	    }
//
//	    // textures
//	    if (tBuffer.size() > 0) {
//	    	meshRes.textures = FloatBuffer.allocate(tBuffer.size()*3);
//	    	Object[] text = tBuffer.toArray();
//	    	for(int i = 0; i < tBuffer.size(); i++){
////	    		textures[i] = (DATABLOCK3F)text[i];
//	    		meshRes.textures.put(tBuffer.get(i).vtn1);
//	    		meshRes.textures.put(tBuffer.get(i).vtn2);
//	    		meshRes.textures.put(tBuffer.get(i).vtn3);
//	    	}
//	    }
//
//	    // normals
//	    if (nBuffer.size() > 0) {
//	    	meshRes.normals = FloatBuffer.allocate(nBuffer.size()*3);
//	    	Object []norm = nBuffer.toArray();
//	    	for(int i = 0; i < nBuffer.size(); i++){
////	    		normals[i] = (DATABLOCK3F)norm[i];
//	    		meshRes.normals.put(nBuffer.get(i).vtn1);
//	    		meshRes.normals.put(nBuffer.get(i).vtn2);
//	    		meshRes.normals.put(nBuffer.get(i).vtn3);
//	    	}
//	    }
//
//	    // generating 3-vert faces to mesh resource
//	    if (f3vtnBuffer.size() > 0) {
//	        //meshRes.count3v = f3vtnBuffer.size() * 3;
//	        //meshRes.data3v = new VERTEX[(int)meshRes.count3v]; // !!!
//	        int i = 0;
//	        meshRes.vIndexes = IntBuffer.allocate(f3vtnBuffer.size() * 3);
//	        meshRes.tIndexes = IntBuffer.allocate(f3vtnBuffer.size() * 3);
//	        meshRes.nIndexes = IntBuffer.allocate(f3vtnBuffer.size() * 3);
//	        try{
//	        for (int k = 0; k < f3vtnBuffer.size(); k++) {
//	            fData = f3vtnBuffer.get(k);
//
//	            for (int j=0; j < 3; j++) {	// v1, v2, v3
//	                switch (j) {
//	                    case 0:
//	                        vIdx = fData.v1 - 1;
//	                        tIdx = fData.t1 - 1;
//	                        nIdx = fData.n1 - 1;
//	                        break;
//	                    case 1:
//	                        vIdx = fData.v2 - 1;
//	                        tIdx = fData.t2 - 1;
//	                        nIdx = fData.n2 - 1;
//	                        break;
//	                    case 2:
//	                        vIdx = fData.v3 - 1;
//	                        tIdx = fData.t3 - 1;
//	                        nIdx = fData.n3 - 1;
//	                        break;
//	                }
//	                meshRes.vIndexes.put(vIdx);
//	                meshRes.tIndexes.put(tIdx);
//	                meshRes.nIndexes.put(nIdx);
////	                meshRes.data3v[i] = new VERTEX();
////	                meshRes.data3v[i].posX = verts[vIdx].vtn1;
////	                meshRes.data3v[i].posY = verts[vIdx].vtn2;
////	                meshRes.data3v[i].posZ = verts[vIdx].vtn3;
//
//	                if (normals != null) {
////	                    meshRes.data3v[i].normX = normals[nIdx].vtn1;
////	                    meshRes.data3v[i].normY = normals[nIdx].vtn2;
////	                    meshRes.data3v[i].normZ = normals[nIdx].vtn3;
//	                } else {
////	                    meshRes.data3v[i].normX = 0;
////	                    meshRes.data3v[i].normY = 0;
////	                    meshRes.data3v[i].normZ = 0;
//	                }
//
////	                meshRes.data3v[i].colR = 255;
////	                meshRes.data3v[i].colG = 255;
////	                meshRes.data3v[i].colB = 255;
////	                meshRes.data3v[i].colA = 255;
//
////	                if (textures != null) {
////	                    meshRes.data3v[i].texS = textures[tIdx].vtn1;
////	                    meshRes.data3v[i].texT = textures[tIdx].vtn2;
////	                } else {
////	                    meshRes.data3v[i].texS = 0;
////	                    meshRes.data3v[i].texT = 0;
////	                }
//	                i++;
//	            }
//	        }
//	        }catch(Exception e){
//		    	System.out.println(e.getMessage());
//		    }
//	    }
//	    
//	}


	//-----------------------------------------------------------------------------
	void initBuffers() {
	    // initialising buffers.
	    vBuffer.clear();
	    tBuffer.clear();
	    nBuffer.clear();

	    f3vtnBuffer.clear();
	    f4vtnBuffer.clear();
	}

}