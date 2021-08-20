public class Triangle extends Shape {
    float c;
    float angleAB;
    float angleBC;
    float angleCA;
    Triangle(float a, float b, float c, float angleAB, float angleBC){
        this.a = a;
        this.b = b;
        this.c = c;
        this.angleAB = angleAB;
        this.angleBC = angleBC;
        this.angleCA = 180 - angleAB - angleBC;
    }

    @Override
    public float getPerimetr() {
        return a + b + c;
    }

    @Override
    public float getArea() {
        return (float) (1/2 * a * b * Math.sin(angleAB));
    }
}
