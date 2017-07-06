package zhenyuyang.ucsb.edu.cashier;



/**
 * Created by Zhenyu on 2017-07-02.
 */

class Item {

    private String ID;
    private String name;
    private float priceIn;
    private float priceStandard;


    public Item(String ID, String name, float priceIn, float priceStandard){
        this.ID = ID;
        this.name = name;
        this.priceIn = priceIn;
        this.priceStandard = priceStandard;
    }

    public String getID(){
        return this.ID;
    }

    public void setID(String ID){
        this.ID = ID;
    }

    public String getName(){
        return this.name;
    }

    public void setName(String name){
        this.name = name;
    }

    public float getPriceIn(){
        return this.priceIn;
    }

    public void setPriceIn(float priceIn){
        this.priceIn = priceIn;
    }

    public float getPriceStandard(){
        return this.priceStandard;
    }

    public void setPriceStandard(float priceStandard){
        this.priceStandard = priceStandard;
    }

    public String toString(){
        return "ID = "+ID+", name = "+name+", priceIn = "+priceIn+", priceStandard = "+priceStandard;
    }
    public String toSellString(){
        return ID+","+name+","+priceIn+","+priceStandard;
    }

    public String toSellStringWithNewLine(){
        return ID+","+name+","+priceIn+","+priceStandard+"\n";
    }

}
