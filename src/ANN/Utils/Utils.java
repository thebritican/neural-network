package ANN.Utils;

import Parsing.data.DataSet;
import Parsing.data.Instance;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Random;

public class Utils {

    public static double evaluateSigmoid(final double exponent) {
        return 1 / (1 + Math.exp(-exponent) );
    }

    public static double getLoss(final double output, final double expected) {
        return (output - expected);
    }

    public static double evaluateSigmoidDerivative(final double exponent) {
        return evaluateSigmoid(exponent) * (1 - evaluateSigmoid(exponent));
    }

    public static double getWeightChangeValueOutputLayer(final double input, final double output, final double expected) {
        return getLoss(output, expected) * input * output * (1 - output);
    }

    public static double getWeightChangeValueHiddenLayer(final double input, final double output, final double downstreamValue) {
        return output * (1 - output) * input * downstreamValue;
    }

    public static double getDotProduct(final double[] a, final double[] b){
        if(a.length != b.length){
            throw new IllegalArgumentException("The dimensions have to be equal!");
        }
        double sum = 0;
        for(int i = 0; i < a.length; i++){
            sum += a[i] * b[i];
        }
        return sum;
    }

    public static double normalizeData(double data, double mean, double std) {
        return (data - mean) / std;
    }

    public static void normalizeDataSet(DataSet dataset) {
        double[] means = new double[dataset.numAttributes()];
        double[] stds = new double[dataset.numAttributes()];
        for (int attribute = 1; attribute < dataset.numAttributes() -1; attribute++) {
            means[attribute] = dataset.mean(attribute);
            stds[attribute] = dataset.stdDeviation(attribute);
        }
            for (int example = 0; example < dataset.size(); example++) {
                Instance instance = dataset.instance(example);
                for (int attributeValue = 1; attributeValue < instance.numAttributes() - 1; attributeValue++) {
                    instance.setValue(attributeValue, normalizeData(instance.value(attributeValue), means[attributeValue], stds[attributeValue]));
            }
        }
    }

    public static double[] getInstanceValues(final Instance instance) {
        double[] values = new double[instance.length()];
        for (int i = 0; i < instance.length(); i++) {
            values[i] = instance.value(i);
        }
        return values;
    }

    public static double[] getInstanceValuesWithBias(final Instance instance) {

        double[] instanceValues = getInstanceValues(instance);
        double[] instanceValuesWithBias =  Arrays.copyOf(instanceValues, instanceValues.length + 1);

        // Add bias neuron value
        instanceValuesWithBias[instanceValuesWithBias.length -1] = -1.0;

        return instanceValuesWithBias;
    }

    public static DataSet getShuffledDataSet(final DataSet dataSet) {
        DataSet shuffledDataSet = new DataSet(dataSet);

        Instance[] instances = new Instance[dataSet.size()];
        for (int i = 0; i < dataSet.size(); i++) {
            instances[i] = dataSet.instance(i);
        }

        shuffleArray(instances);

        shuffledDataSet.add(instances);

        return shuffledDataSet;
    }

    public static void shuffleArray(final Object[] ar)
    {
        Random rnd = new Random();
        for (int i = ar.length - 1; i > 0; i--)
        {
            int index = rnd.nextInt(i + 1);
            Object a = ar[index];
            ar[index] = ar[i];
            ar[i] = a;
        }
    }

    public static double calculateSpecificity(int numTrueNegatives, int numFalsePositives) {
        return numTrueNegatives / (numTrueNegatives + numFalsePositives);
    }

    public static double calculateRecall(int numTruePositives, int numFalseNegatives) {
        return numTruePositives / (numTruePositives + numFalseNegatives);
    }

    public static double getTrapezoidalArea(double a, double b, double h) {
        return ((a + b) / 2) * h;
    }

    public static double[] flattenDoubleArray(double[][] mdarray, int mdArrayFullSize) {
        double[] flattenedArray = new double[mdArrayFullSize];
        for (int i = 0; i < mdarray.length; i++) {
            for (int j = 0; j < mdarray[i].length; j++) {
                flattenedArray[i] = mdarray[i][j];
            }
        }
        return flattenedArray;
    }

}
