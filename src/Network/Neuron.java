package Network;

import random.RandomUtil;

class Neuron {
    public double bias = RandomUtil.uniform();
    public double value;

    public void setBias(double bias) {
        this.bias = bias;
    }

    public void input(double[] inputs){
        double value1 = 0;
        for (double input:inputs){
            value1 += input;
        }
        value1 += this.bias;
        this.value = value1;
    }

    public double send(){
        return this.value;
    }
}

class InputNeuron{
    double value;

    public void input(double[] inputs){
        this.value = inputs[0];
        
    }

    public double send(){
        return this.value;
    }
}


class Network{
    int score = 0;
    int[] data;
    double[][] biases;
    double[] weights;
    InputNeuron[] inputs;
    Neuron[][] hiddenLayers;
    Neuron[] outputs;
    
    public void setVars(boolean setBiases, double[][] biases, double[] weights, int input, int output, int... numPerHiddenLayer){
        this.data = new int[2 + numPerHiddenLayer.length];
        this.data[0] = input;
        this.data[1] = output;

        for (int i = 0; i < numPerHiddenLayer.length; i++){
            data[2+i] = numPerHiddenLayer[i];
        }
        
        this.inputs = new InputNeuron[input];
        for (int i = 0; i < input; i++){
            this.inputs[i] = new InputNeuron();
        }
        
        if (setBiases){
            this.biases = biases;
            this.weights = weights;
        }
        else{
            this.biases = new double[numPerHiddenLayer.length+1][];
            for (int x = 0; x < numPerHiddenLayer.length; x++){
                this.biases[x] = new double[numPerHiddenLayer[x]];
                for (int y = 0; y < numPerHiddenLayer[x]; y++){
                    this.biases[x][y] = RandomUtil.uniform();
                }
            }
            this.biases[numPerHiddenLayer.length] = new double[output];
            for (int z = 0; z < output; z++){
                this.biases[numPerHiddenLayer.length][z] = RandomUtil.uniform();
            }
        }
        this.hiddenLayers = new Neuron[numPerHiddenLayer.length][];
        for (int x = 0; x < numPerHiddenLayer.length; x++){
            this.hiddenLayers[x] = new Neuron[numPerHiddenLayer[x]];
            for (int y = 0; y < numPerHiddenLayer[x]; y++){
                this.hiddenLayers[x][y] = new Neuron();
                this.hiddenLayers[x][y].setBias(this.biases[x][y]);
            }
        }
        this.outputs = new Neuron[output];
        for (int x = 0; x < output; x++){
            this.outputs[x] = new Neuron();
            this.outputs[x].setBias(this.biases[this.biases.length - 1][x]);
        }

    }
}