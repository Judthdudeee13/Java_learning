package Network;

import random.RandomUtil;

class Neuron {
    public double bias = RandomUtil.uniform();
    public double value;

    public void set_bias(double bias) {
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
