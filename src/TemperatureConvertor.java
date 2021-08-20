public class TemperatureConvertor {
    public double toFahrenheit(double value){
        return  value * 9/5 + 32;
    }

    public double toKelvin(double value){
        return value + 273.15;
    }
}
