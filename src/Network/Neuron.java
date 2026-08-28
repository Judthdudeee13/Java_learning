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
    InputNeuron[] inputs;
    
    public void setVars(boolean setBiases, double[] biases, double[] weights, int input, int output, int... numPerHiddenLayer){
        data = new int[2 + numPerHiddenLayer.length];
        data[0] = input;
        data[1] = output;

        for (int i = 0; i < numPerHiddenLayer.length; i++){
            data[2+i] = numPerHiddenLayer[i];
        }
        
        if (setBiases){
            
        }

    }

    private void setBiasesAndWeights(){

    }
}