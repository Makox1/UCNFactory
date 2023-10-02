public class Monitor extends Product{
    private int resolutionX;
    private int resolutionY;

    public Monitor(String brand, String model, double price, int resolutionX, int resolutionY) {
        super(brand, model, price);
        //super(productId, brand, model, price);
        this.resolutionX = resolutionX;
        this.resolutionY = resolutionY;
    }

    public int getResolutionX() {
        return resolutionX;
    }

    public void setResolutionX(int resolutionX) {
        this.resolutionX = resolutionX;
    }

    public int getResolutionY() {
        return resolutionY;
    }

    public void setResolutionY(int resolutionY) {
        this.resolutionY = resolutionY;
    }
}
