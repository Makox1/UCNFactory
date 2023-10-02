public class Computer extends Product{
    private String cpu;
    private String ram;
    private String hdd;

    public Computer(String brand, String model, double price, String cpu, String ram, String hdd){
        super(brand, model, price);
        //super(productId, brand, model, price);
        this.cpu=cpu;
        this.ram=ram;
        this.hdd=hdd;
    }


    public String getCpu() {
        return cpu;
    }

    public String getRam() {
        return ram;
    }

    public String getHdd() {
        return hdd;
    }

    public void setHdd(String hdd) {
        this.hdd = hdd;
    }

    public void setRam(String ram) {
        this.ram = ram;
    }

    public void setCpu(String cpu) {
        this.cpu = cpu;
    }
}
