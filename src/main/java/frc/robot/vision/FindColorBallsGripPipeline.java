package frc.robot.vision;

import frc.robot.Constants;
import org.opencv.core.*;
import org.opencv.features2d.FastFeatureDetector;
import org.opencv.imgproc.Imgproc;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;

/**
 * GripPipeline class.
 *
 * <p>An OpenCV pipeline generated by GRIP.
 *
 * @author GRIP
 */
public class FindColorBallsGripPipeline implements FindBallsGripPipeline {

    //Outputs
    private final Mat resizeImageOutput = new Mat();
    private final Mat hsvThresholdOutput = new Mat();
    private final Mat cvErodeOutput = new Mat();
    private final Mat maskOutput = new Mat();
    private final MatOfKeyPoint findBlobsOutput = new MatOfKeyPoint();
    double[] hsvThresholdHue;
    double[] hsvThresholdSaturation;
    double[] hsvThresholdValue;

    static {
        System.loadLibrary(Core.NATIVE_LIBRARY_NAME);
    }

    public FindColorBallsGripPipeline(double[] hsvThresholdHue, double[] hsvThresholdSaturation, double[] hsvThresholdValue) {
        this.hsvThresholdHue = hsvThresholdHue;
        this.hsvThresholdSaturation = hsvThresholdSaturation;
        this.hsvThresholdValue = hsvThresholdValue;
    }

    /**
     * This is the primary method that runs the entire pipeline and updates the outputs.
     */
    @Override
    public void process(Mat source0) {
        // Step Resize_Image0:
        double resizeImageWidth = Constants.Vision.resizeImageWidth;
        double resizeImageHeight = Constants.Vision.resizeImageHeight;
        int resizeImageInterpolation = Imgproc.INTER_CUBIC;
        resizeImage(source0, resizeImageWidth, resizeImageHeight, resizeImageInterpolation, resizeImageOutput);

        // Step HSV_Threshold0:
        Mat hsvThresholdInput = resizeImageOutput;
        hsvThreshold(hsvThresholdInput, hsvThresholdHue, hsvThresholdSaturation, hsvThresholdValue, hsvThresholdOutput);

        // Step CV_erode0:
        Mat cvErodeSrc = hsvThresholdOutput;
        Mat cvErodeKernel = new Mat();
        Point cvErodeAnchor = new Point(-1, -1);
        double cvErodeIterations = 1.0;
        int cvErodeBordertype = Core.BORDER_CONSTANT;
        Scalar cvErodeBordervalue = new Scalar(-1);
        cvErode(cvErodeSrc, cvErodeKernel, cvErodeAnchor, cvErodeIterations, cvErodeBordertype, cvErodeBordervalue, cvErodeOutput);

        // Step Mask0:
        Mat maskInput = resizeImageOutput;
        Mat maskMask = cvErodeOutput;
        mask(maskInput, maskMask, maskOutput);

        // Step Find_Blobs0:
        Mat findBlobsInput = maskOutput;
        double findBlobsMinArea = 11.0;
        double[] findBlobsCircularity = {0.0, 1.0};
        boolean findBlobsDarkBlobs = false;
        findBlobs(findBlobsInput, findBlobsMinArea, findBlobsCircularity, findBlobsDarkBlobs, findBlobsOutput);

    }

    /**
     * This method is a generated getter for the output of a Resize_Image.
     *
     * @return Mat output from Resize_Image.
     */
    public Mat resizeImageOutput() {
        return resizeImageOutput;
    }

    /**
     * This method is a generated getter for the output of a HSV_Threshold.
     *
     * @return Mat output from HSV_Threshold.
     */
    public Mat hsvThresholdOutput() {
        return hsvThresholdOutput;
    }

    /**
     * This method is a generated getter for the output of a CV_erode.
     *
     * @return Mat output from CV_erode.
     */
    public Mat cvErodeOutput() {
        return cvErodeOutput;
    }

    /**
     * This method is a generated getter for the output of a Mask.
     *
     * @return Mat output from Mask.
     */
    public Mat maskOutput() {
        return maskOutput;
    }

    /**
     * This method is a generated getter for the output of a Find_Blobs.
     *
     * @return MatOfKeyPoint output from Find_Blobs.
     */
    public MatOfKeyPoint findBlobsOutput() {
        return findBlobsOutput;
    }


    /**
     * Scales and image to an exact size.
     *
     * @param input         The image on which to perform the Resize.
     * @param width         The width of the output in pixels.
     * @param height        The height of the output in pixels.
     * @param interpolation The type of interpolation.
     * @param output        The image in which to store the output.
     */
    private void resizeImage(Mat input, double width, double height,
                             int interpolation, Mat output) {
        Imgproc.resize(input, output, new Size(width, height), 0.0, 0.0, interpolation);
    }

    /**
     * Segment an image based on hue, saturation, and value ranges.
     *
     * @param input The image on which to perform the HSL threshold.
     * @param hue   The min and max hue
     * @param sat   The min and max saturation
     * @param val   The min and max value
     * @param out   The image in which to store the output.
     */
    private void hsvThreshold(Mat input, double[] hue, double[] sat, double[] val,
                              Mat out) {
        Imgproc.cvtColor(input, out, Imgproc.COLOR_BGR2HSV);
        Core.inRange(out, new Scalar(hue[0], sat[0], val[0]),
                new Scalar(hue[1], sat[1], val[1]), out);
    }

    /**
     * Expands area of lower value in an image.
     *
     * @param src         the Image to erode.
     * @param kernel      the kernel for erosion.
     * @param anchor      the center of the kernel.
     * @param iterations  the number of times to perform the erosion.
     * @param borderType  pixel extrapolation method.
     * @param borderValue value to be used for a constant border.
     * @param dst         Output Image.
     */
    private void cvErode(Mat src, Mat kernel, Point anchor, double iterations,
                         int borderType, Scalar borderValue, Mat dst) {
        if (kernel == null) {
            kernel = new Mat();
        }
        if (anchor == null) {
            anchor = new Point(-1, -1);
        }
        if (borderValue == null) {
            borderValue = new Scalar(-1);
        }
        Imgproc.erode(src, dst, kernel, anchor, (int) iterations, borderType, borderValue);
    }

    /**
     * Filter out an area of an image using a binary mask.
     *
     * @param input  The image on which the mask filters.
     * @param mask   The binary image that is used to filter.
     * @param output The image in which to store the output.
     */
    private void mask(Mat input, Mat mask, Mat output) {
        mask.convertTo(mask, CvType.CV_8UC1);
        Core.bitwise_xor(output, output, output);
        input.copyTo(output, mask);
    }

    /**
     * Detects groups of pixels in an image.
     *
     * @param input       The image on which to perform the find blobs.
     * @param minArea     The minimum size of a blob that will be found
     * @param circularity The minimum and maximum circularity of blobs that will be found
     * @param darkBlobs   The boolean that determines if light or dark blobs are found.
     * @param blobList    The output where the MatOfKeyPoint is stored.
     */
    private void findBlobs(Mat input, double minArea, double[] circularity,
                           Boolean darkBlobs, MatOfKeyPoint blobList) {
        FastFeatureDetector blobDet = FastFeatureDetector.create(FastFeatureDetector.FAST_N);
        try {
            File tempFile = File.createTempFile("config", ".xml");

            StringBuilder config = new StringBuilder();

            config.append("<?xml version=\"1.0\"?>\n");
            config.append("<opencv_storage>\n");
            config.append("<thresholdStep>10.</thresholdStep>\n");
            config.append("<minThreshold>50.</minThreshold>\n");
            config.append("<maxThreshold>220.</maxThreshold>\n");
            config.append("<minRepeatability>2</minRepeatability>\n");
            config.append("<minDistBetweenBlobs>10.</minDistBetweenBlobs>\n");
            config.append("<filterByColor>1</filterByColor>\n");
            config.append("<blobColor>");
            config.append((darkBlobs ? 0 : 255));
            config.append("</blobColor>\n");
            config.append("<filterByArea>1</filterByArea>\n");
            config.append("<minArea>");
            config.append(minArea);
            config.append("</minArea>\n");
            config.append("<maxArea>");
            config.append(Integer.MAX_VALUE);
            config.append("</maxArea>\n");
            config.append("<filterByCircularity>1</filterByCircularity>\n");
            config.append("<minCircularity>");
            config.append(circularity[0]);
            config.append("</minCircularity>\n");
            config.append("<maxCircularity>");
            config.append(circularity[1]);
            config.append("</maxCircularity>\n");
            config.append("<filterByInertia>1</filterByInertia>\n");
            config.append("<minInertiaRatio>0.1</minInertiaRatio>\n");
            config.append("<maxInertiaRatio>" + Integer.MAX_VALUE + "</maxInertiaRatio>\n");
            config.append("<filterByConvexity>1</filterByConvexity>\n");
            config.append("<minConvexity>0.95</minConvexity>\n");
            config.append("<maxConvexity>" + Integer.MAX_VALUE + "</maxConvexity>\n");
            config.append("</opencv_storage>\n");
            FileWriter writer = new FileWriter(tempFile, false);
            writer.write(config.toString());
            writer.close();
            blobDet.read(tempFile.getPath());
        } catch (IOException e) {
            //e.printStackTrace();
        }

        blobDet.detect(input, blobList);
    }


}


