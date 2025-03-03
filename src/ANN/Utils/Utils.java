package ANN.Utils;

import Parsing.data.DataSet;
import Parsing.data.Instance;

import java.util.Random;

public class Utils {

    public static double evaluateSigmoid(final double exponent) {
        return 1 / (1 + Math.exp(-exponent) );
    }

    public static double getLoss(final double output, final double expected) {
        return (output - expected);
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
            sum += (a[i] * b[i]);
        }
        return sum;
    }

    public static double normalizeData(double data, double mean, double std) {
        return (data - mean) / std;
    }

    public static DataSet normalizeDataSet(DataSet dataset) {

        double[] means = new double[dataset.numAttributes() -2];
        double[] stds = new double[dataset.numAttributes() - 2];

        DataSet standardizedDataSet = new DataSet(dataset);
        for (int i = 1; i < dataset.numAttributes() -1; i++) {
            means[i - 1] = dataset.mean(i);
            double std = dataset.stdDeviation(i);
            stds[i - 1] = std;
        }

        for (int exampleIndex = 0; exampleIndex < dataset.size(); exampleIndex++) {
            Instance instance = new Instance(dataset.instance(exampleIndex));
            for (int attributeIndex = 1; attributeIndex < instance.numAttributes() - 1; attributeIndex++) {
                instance.setValue(attributeIndex, normalizeData(instance.value(attributeIndex), means[attributeIndex -1], stds[attributeIndex -1]));
            }
            standardizedDataSet.add(instance);
        }

        return standardizedDataSet;
    }

    public static double[] getInstanceValuesWithBias(final Instance instance) {

        double[] values = new double[instance.length() -1];
        for (int i = 1; i < instance.length() -1; i++) {
            values[i-1] = instance.value(i);
        }
        values[values.length -1] = -1.0;

        return values;
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
        Random rnd = new Random(12345);
        for (int i = ar.length - 1; i > 0; i--)
        {
            int index = rnd.nextInt(i + 1);
            Object a = ar[index];
            ar[index] = ar[i];
            ar[i] = a;
        }
    }

    public static double calculateSpecificity(int numTrueNegatives, int numFalsePositives) {
        if ((numTrueNegatives + numFalsePositives) == 0)
            return 1;
        return numTrueNegatives / ((double) numTrueNegatives + numFalsePositives);
    }

    public static double calculateRecall(int numTruePositives, int numFalseNegatives) {
        if ((numTruePositives + numFalseNegatives) == 0)
            return 0;
        return numTruePositives / ( (double) numTruePositives + numFalseNegatives);
    }

    public static double getTrapezoidalArea(double a, double b, double h) {
        return ((a + b) / 2) * h;
    }

    public static void printProgress(int curIndex, int lastIndex) {
        float percentage = curIndex / (float) lastIndex;
        StringBuilder sb = new StringBuilder("\r[");
        for (int i = 0; i < (int) ((percentage * 100) / 5); i++) {
            sb.append('#');
        }
        for (int i = sb.length(); i < 22; i++) {
            sb.append(' ');
        }
        sb.append(String.format("] %,.2f%%", percentage * 100));
        System.out.print(sb.toString());
    }

}
