package desmoj.extensions.space2D.gui;

import java.awt.geom.AffineTransform;
import java.awt.geom.Point2D;

/**
 * a model-view transformer assists in translating from model space (coordinate
 * system used by any spatial model) to view space (screen coordinate system).
 * It uses a set of affine transformations and a scale factor.
 * 
 * @author Ruth Meyer
 * @dependency desmoj.gui.AffineTransformable
 */
public class ModelViewTransformer {

	// ///////////// ATTRIBUTE ///////////////////////////////////////

	/** the scale factor. */
	private double scaleFactor;

	/**
	 * the list of affine transformations to be applied in turn to transform
	 * from model space to view space (screen coordinates).
	 */
	private AffineTransform[] transforms;

	// ///////////// KONSTRUKTOREN ///////////////////////////////////

	/**
	 * constructs a model-view transformer with the given scale factor and
	 * affine transformations.
	 * 
	 * @param scaleFactor
	 *            the scale factor to be used by this transformer
	 * @param transforms
	 *            the affine transformations to be used by this transformer
	 */
	public ModelViewTransformer(double scaleFactor, AffineTransform[] transforms) {
		this.scaleFactor = scaleFactor;
		this.transforms = transforms;
	}

	/**
	 * constructs a model-view transformer with the given scale factor and
	 * parameters determining the affine transformations.
	 * 
	 * @param scaleFactor
	 *            the scale factor to be used by this transformer for scaling
	 *            operations
	 * @param bounds
	 *            the model bounds as an array of the form {min_x, min_y, max_x,
	 *            max_y}
	 * @param origin
	 *            the model origin as an array of the form {orig_x, orig_y} with
	 *            orig_i = one of the constants defined by AffineTransformable
	 * @param scale
	 *            the scale factors as an array of the form {scale_x, scale_y}
	 *            to be used in computing the affine transformations. Usually at
	 *            least one of these scale factors is identical with the first
	 *            parameter.
	 * @param margin
	 *            the view margins as an array of the form {margin_x, margin_y}
	 */
	public ModelViewTransformer(double scaleFactor, double[] bounds,
			int[] origin, double[] scale, int[] margin) {
		this.scaleFactor = scaleFactor;
		this.transforms = new AffineTransform[3];
		transforms[0] = AffineTransform.getTranslateInstance(-bounds[0],
				-bounds[1]);
		int[] translateFactor = new int[2];
		for (int i = 0; i < 2; i++) {
			translateFactor[i] = origin[i] == 1 ? 0 : origin[i];
		}
		transforms[0].translate(translateFactor[0] * (bounds[2] - bounds[0]),
				translateFactor[1] * (bounds[3] - bounds[1]));
		transforms[1] = AffineTransform.getScaleInstance(origin[0] * scale[0],
				origin[1] * scale[1]);
		transforms[2] = AffineTransform.getTranslateInstance(margin[0],
				margin[1]);
	}

	// ///////////// METHODEN ////////////////////////////////////////

	/**
	 * transforms a model point (java.awt.geom.Point2D) to a screen point
	 * (java.awt.geom.Point2D) using the affine transformations.
	 * 
	 * @param point
	 *            the point in model space to be transformed to view space
	 * @return a point in view space
	 */
	public Point2D transform(Point2D point) {
		// point klonen, damit ver�ndern m�glich
		Point2D transP = (Point2D) point.clone();
		for (int i = 0; i < this.transforms.length; i++) {
			this.transforms[i].transform(transP, transP);
		}
		return transP;
	}

	/**
	 * scales a value within the bounds of <code>min</code> and
	 * <code>max</code> using the scale factor.
	 * 
	 * @param value
	 *            the value to be scaled
	 * @param min
	 *            the lower bound
	 * @param max
	 *            the upper bound
	 * @return the scaled value
	 */
	public double scale(double value, double min, double max) {
		double scaledValue = value * this.scaleFactor;
		scaledValue = Math.min(scaledValue, max);
		scaledValue = Math.max(scaledValue, min);
		return scaledValue;
	}

	/**
	 * scales a value using the scale factor.
	 * 
	 * @param value
	 *            the value to be scaled
	 * @return the scaled value
	 */
	public double scale(double value) {
		return value * this.scaleFactor;
	}

	/**
	 * scales a value within the bounds of <code>min</code> and
	 * <code>max</code> using the scale factor.
	 * 
	 * @param value
	 *            the value to be scaled
	 * @param min
	 *            the lower bound
	 * @param max
	 *            the upper bound
	 * @return the scaled value
	 */
	public int scale(int value, int min, int max) {
		double scaledValue = value * this.scaleFactor;
		scaledValue = Math.min(scaledValue, max);
		scaledValue = Math.max(scaledValue, min);
		return (int) scaledValue;
	}

	/**
	 * scales a value using the scale factor.
	 * 
	 * @param value
	 *            the value to be scaled
	 * @return the scaled value
	 */
	public int scale(int value) {
		return (int) (value * this.scaleFactor);
	}

	/**
	 * returns the scale factor.
	 */
	public double getScaleFactor() {
		return this.scaleFactor;
	}

	/**
	 * returns the affine transformations.
	 */
	public AffineTransform[] getTransforms() {
		return this.transforms;
	}
}
