package nl.han.oose.sapporo.dto;


public class InventoryDTO {
    private int userID;
    private int money;
    private int water;

    public InventoryDTO(int userID, int money, int water) {
        this.userID = userID;
        this.money = money;
        this.water = water;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof InventoryDTO)) return false;
        InventoryDTO that = (InventoryDTO) o;
        return userID == that.userID &&
                money == that.money &&
                water == that.water;
    }

    public int getMoney() {
        return money;
    }

    public int getWater() {
        return water;
    }
}