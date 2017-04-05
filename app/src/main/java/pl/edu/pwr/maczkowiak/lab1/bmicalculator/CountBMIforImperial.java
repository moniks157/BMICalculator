package pl.edu.pwr.maczkowiak.lab1.bmicalculator;

/**
 * Created by Monika Maczkowiak on 2017-04-03.
 */

public class CountBMIforImperial implements CountBMI {
    static final float MINIMAL_MASS=22.0f;
    static final float MAXIMAL_MASS=552.0f;
    static final float MINIMAL_HEIGHT=19.0f;
    static final float MAXIMAL_HEIGHT=99.0f;
    static final float MULTIPLIER =703.0f;

    public boolean isMassValid(float mass){
        return mass>=MINIMAL_MASS&&mass<=MAXIMAL_MASS;
    }

    public boolean isHeightValid(float height){
        return height>=MINIMAL_HEIGHT&&height<=MAXIMAL_HEIGHT;
    }

    public float countBMI(float mass, float height){
        if(isMassValid(mass)&&isHeightValid(height)){
            return (mass* MULTIPLIER)/(height*height);
        }
        else throw new IllegalArgumentException();
    }
}
