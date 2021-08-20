public class Rect extends Shape {
    Rect(float a, float b){
        this.a = a;
        this.b = b;
    }

    @Override
    public float getArea() {
        return a * b;
    }

    @Override
    public float getPerimetr() {
        return a + b;
    }
}
