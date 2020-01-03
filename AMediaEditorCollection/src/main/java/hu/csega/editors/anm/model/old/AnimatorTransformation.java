package hu.csega.editors.anm.model.old;

public class AnimatorTransformation {

	public double[][] matrix = new double[4][4];

	public AnimatorTransformation() {
		for(int i = 0; i < 4; i++) {
			for(int j = 0; j < 4; j++) {
				if(i == j) {
					this.matrix[i][j] = 1.0;
				} else {
					this.matrix[i][j] = 0.0;
				}
			}
		}
	}

	public AnimatorTransformation(AnimatorTransformation other) {
		copyFrom(other);
	}

	public void copyFrom(AnimatorTransformation other) {
		for(int i = 0; i < 4; i++) {
			for(int j = 0; j < 4; j++) {
				this.matrix[i][j] = other.matrix[i][j];
			}
		}
	}


}
