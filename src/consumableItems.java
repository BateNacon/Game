public class consumableItems {
    private String location;
    private String itemName;
    private String itemDesc;
    private boolean hasItem;

    public consumableItems(String location, String itemName, String itemDesc)
    {
        this.location = location;
        this.itemName = itemName;
        this.itemDesc = itemDesc;
        hasItem = false;
    }

    //"gives" the player the item by setting it as true
    public void setHasItem()
    {
        hasItem = true;
    }

    public void setDoesNotHaveItem()
    {
        hasItem = false;
    }

    public String getItemName()
    {
        return itemName;
    }

    public String getItemDesc()
    {
        return itemDesc;
    }

    public String getLocation()
    {
        return location;
    }

    public boolean getDoesHaveItem()
    {
        return hasItem;
    }
}
