package Network;

import random.*;

class Parent {
    Network network;
    public void setUp(boolean setUpVals, double[][][] weights, double[][] biases, int[] values){
        int[] numPerHiddenLayer = new int[values.length-2];
        for (int x = 0; x < values.length-2; x++){
            numPerHiddenLayer[x] = values[x+2];
        }
        network = new Network();
        network.setVars(setUpVals, biases, weights, values[0], values[1], numPerHiddenLayer);
    }
}


class Specimin extends Parent{
    Parent parent1;
    Parent parent2;
    int[] data;
    double[][][] childWeights;
    double[][] childBiases;

    private void generateSelf(){
        double[][][] tempChildWeights;
        double[][] tempChildBiases;
        Object[] parent1Data = this.parent1.network.getWeightsAndBiases();
        Object[] parent2Data = this.parent2.network.getWeightsAndBiases();
        double[][][] parent1Weights = (double[][][]) parent1Data[0];
        double[][][] parent2Weights = (double[][][]) parent2Data[0];
        double[][] parent1Biases = (double[][]) parent1Data[1];
        double[][] parent2Biases = (double[][]) parent2Data[1];
        tempChildWeights = new double[parent1Weights.length][][];
        for (int layer = 0; layer < parent1Weights.length; layer++){
            tempChildWeights[layer] = new double[parent1Weights[layer].length][];
            for (int neuron = 0; neuron < parent1Weights[layer].length; neuron++){
                tempChildWeights[layer][neuron] = new double[parent1Weights[layer][neuron].length];
                for (int weight = 0; weight < parent1Weights[layer][neuron].length; weight++){
                    if (RandomUtil.randint(0, 1) == 0){
                        tempChildWeights[layer][neuron][weight] = parent1Weights[layer][neuron][weight];
                    }
                    else{
                        tempChildWeights[layer][neuron][weight] = parent2Weights[layer][neuron][weight];
                    }
                    
                }
            }

        }
        tempChildBiases = new double[parent1Biases.length][];
        for (int layer = 0; layer < parent1Biases.length; layer++){
            tempChildBiases[layer] = new double[parent1Biases[layer].length];
            for (int bias = 0; bias < parent1Biases[layer].length; bias++){
                if (RandomUtil.randint(0, 1) == 0){
                        tempChildBiases[layer][bias] = parent1Biases[layer][bias];
                    }
                    else{
                        tempChildBiases[layer][bias] = parent2Biases[layer][bias];
                    }
            }
        }
        this.childBiases = tempChildBiases;
        this.childWeights = tempChildWeights;
    }

    private void mutate(){
        for (int layer = 0; layer < this.childWeights.length; layer++){
            for (int neuron = 0; neuron < this.childWeights[layer].length; neuron++){
                for (int weight = 0; weight < this.childWeights[layer][neuron].length; weight++){
                    if (RandomUtil.randint(0, 50) == 0){
                        if (RandomUtil.randint(0, 9) == 0){
                            this.childWeights[layer][neuron][weight] += RandomUtil.uniform();
                        }
                        else{
                            this.childWeights[layer][neuron][weight] += RandomUtil.uniform1(-0.2, 0.2);
                        }
                    }
                }
            }
        }
        for (int layer = 0; layer < this.childBiases.length; layer++){
            for (int bias = 0; bias < this.childBiases[layer].length; bias++){
                if (RandomUtil.randint(0, 50) == 0){
                    if (RandomUtil.randint(0, 9) == 0){
                        this.childBiases[layer][bias] += RandomUtil.uniform();
                    }
                    else{
                        this.childBiases[layer][bias] += RandomUtil.uniform1(-0.2, 0.2);
                    }
                }
            }
        }
    }

    private void createNetwork(){
        int[] numPerHiddenLayer = new int[this.data.length-2];
        for (int x = 0; x < this.data.length-2; x++){
            numPerHiddenLayer[x] = this.data[x+2];
        }
        network = new Network();
        network.setVars(true, childBiases, childWeights, this.data[0], this.data[1], numPerHiddenLayer);
    }

    public void run(Parent parent1, Parent parent2){
        this.parent1 = parent1;
        this.parent2 = parent2;
        this.data = parent1.network.data;
        generateSelf();
        mutate();
        createNetwork();
        this.parent1 = null;
        this.parent2 = null;
        this.data = null;
    }
}