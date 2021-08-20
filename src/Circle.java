public class Circle extends Shape{
    Circle(float a){
        this.a = a;
    }

    @Override
    public float getArea() {
        return (float) Math.PI*a*a;
    }

    @Override
    public float getPerimetr() {
        return (float) (2*Math.PI*a);
    }
}
