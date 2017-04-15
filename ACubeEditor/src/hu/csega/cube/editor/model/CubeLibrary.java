package hu.csega.cube.editor.model;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.util.ArrayList;

public class CubeLibrary extends ArrayList<CubeSheet> {

	public CubeLibrary(int size) {
		super(size);
		for(int i = 0; i < size; i++) {
			super.add(null);
		}

		used = new boolean[size];
	}

	public static CubeLibrary load(File f) {
		try (ObjectInputStream stream = new ObjectInputStream(new FileInputStream(f))) {
            return (CubeLibrary) stream.readObject();
	    } catch(Exception ex) {
	        throw new RuntimeException("Loading failed!", ex);
	    }
	}

	public static CubeLibrary load(Class<?> neighborClass, String fileName) {
		try (ObjectInputStream stream = new ObjectInputStream(neighborClass.getResourceAsStream(fileName))) {
            return (CubeLibrary) stream.readObject();
	    } catch(Exception ex) {
	        throw new RuntimeException("Loading failed!", ex);
	    }
	}

    public void save(File f) {
        try (ObjectOutputStream stream = new ObjectOutputStream(new FileOutputStream(f))) {
            stream.writeObject(this);
        } catch(Exception ex) {
            throw new RuntimeException("Saving failed!", ex);
        }
    }

    public void setUsed(int i) {
    	used[i] = true;
    }

    @Override
    public CubeSheet get(int index) {
    	CubeSheet ret = super.get(index);
    	if(ret == null) {
    		ret = new CubeSheet();
    		super.set(index, ret);
    	}

    	return ret;
    }

	@Override
	public void add(int index, CubeSheet element) {
		throw new UnsupportedOperationException("add");
	}

	@Override
	public boolean add(CubeSheet element) {
		throw new UnsupportedOperationException("add");
	}

	@Override
	public CubeSheet set(int index, CubeSheet element) {
		throw new UnsupportedOperationException("set");
	}

	private boolean[] used;

	private static final long serialVersionUID = 1L;
}
