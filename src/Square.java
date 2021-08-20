public class Square extends Shape{
    Square(float a){
        this.a = a;
    }

    @Override
    public float getArea() {
        return a * a;
    }

    @Override
    public float getPerimetr() {
        return a + a;
    }
}
