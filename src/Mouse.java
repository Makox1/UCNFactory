public class Mouse extends Product{
    private boolean isWired;

    public Mouse(String brand, String model, double price, boolean isWired) {
        super(brand, model, price);
        //super(productId, brand, model, price);
        this.isWired = isWired;
    }


    public boolean isWired() {
        return isWired;
    }

    public void setWired(boolean wired) {
        isWired = wired;
    }
}
