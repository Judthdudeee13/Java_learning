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
        if (value1 > 0){
            this.value = value1;
        }
        else{
            this.value = 0;
        }
    }

    public double send(){
        return this.value;
    }
}

class InputNeuron extends Neuron {
    @Override
    public void input(double[] inputs){
        this.value = inputs[0];
    }
}


class Network{
    int score = 0;
    int[] data;
    double[][] biases;
    double[][][] weights;
    InputNeuron[] inputs;
    Neuron[][] hiddenLayers;
    Neuron[] outputs;
    Neuron[][] neurons;
    
    public void setVars(boolean loadVals, double[][] biases, double[][][] weights, int input, int output, int... numPerHiddenLayer){
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
        
        if (loadVals){
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
            //closing of 2 loops
            this.biases[numPerHiddenLayer.length] = new double[output];
            for (int z = 0; z < output; z++){
                this.biases[numPerHiddenLayer.length][z] = RandomUtil.uniform();
            }
            //closing of loop
        }
        //closing of else
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

        this.neurons = new Neuron[2+numPerHiddenLayer.length][];
        this.neurons[0] = this.inputs;
        for (int x = 0; x < numPerHiddenLayer.length; x++){
            this.neurons[x+1] = this.hiddenLayers[x];
        }
        this.neurons[2+numPerHiddenLayer.length-1] = this.outputs;
        if (!loadVals){
            this.weights = new double[this.neurons.length-1][][];
            for (int layer = 0; layer < this.neurons.length-1; layer++){
                this.weights[layer] = new double[this.neurons[layer+1].length][];
                for (int x = 0; x < this.neurons[layer+1].length; x++){
                    this.weights[layer][x] = new double[this.neurons[layer].length];
                    for (int y = 0; y < this.neurons[layer].length; y++){
                        this.weights[layer][x][y] = RandomUtil.uniform();
                    }
                    //closing of 3rd loop
                }
                //closing of second loop
            }
            //closing of first loop
        }
        //closing of if statment
    }
    // closing of __init__ method
    private void input(double[] inputs){
        for (int x = 0; x < this.neurons[0].length; x++){
            double[] input = new double[1];
            input[0] = inputs[x];
            this.neurons[0][x].input(input);
        }
    }
    private void feedForward(){
        for (int layer = 0; layer < this.neurons.length-1; layer++){
            for (int neuron = 0; neuron < this.neurons[layer+1].length; neuron++){
                double[] values = new double[this.weights[layer][neuron].length];
                for (int weight = 0; weight < this.weights[layer][neuron].length; weight++){
                    values[weight] = this.weights[layer][neuron][weight] * this.neurons[layer][weight].send();
                }
                this.neurons[layer+1][neuron].input(values);
            }
        }
    }

    private double[] getOutput(){
        double[] outputs = new double[this.outputs.length];
        for (int x = 0; x < this.outputs.length; x++){
            outputs[x] = this.outputs[x].send();
        }
        return outputs;
    }


    //closing of input method
    public double[] run(double[] inputs){
        input(inputs);
        feedForward();
        return getOutput();
    }

    public Object[] getWeightsAndBiases(){
        Object[] returns = new Object[2];
        double[][] biases = new double[this.neurons.length-1][];
        for (int layer = 1; layer <= this.neurons.length-1; layer++){
            biases[layer] = new double[this.neurons[layer].length];
            for (int neuron = 0; neuron < this.neurons[layer].length-1; neuron++){
                biases[layer][neuron] = this.neurons[layer][neuron].bias;
            }
        }
        returns[0] = this.weights;
        returns[1] = biases;
        return returns;
    }
}