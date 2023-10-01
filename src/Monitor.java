public class Monitor extends Product{
    private int resolutionY;
    private int resolutionX;

    public Monitor(String productId, String brand, String model, double price, int resolutionY, int resolutionX) {
        super(productId, brand, model, price);
        this.resolutionY = resolutionY;
        this.resolutionX = resolutionX;
    }

    public int getResolutionY() {
        return resolutionY;
    }

    public void setResolutionY(int resolutionY) {
        this.resolutionY = resolutionY;
    }

    public int getResolutionX() {
        return resolutionX;
    }

    public void setResolutionX(int resolutionX) {
        this.resolutionX = resolutionX;
    }
}
