package com.example.barberpalace2;

public class TimeSlot {
    public boolean isAvailable;
    public String time;
    public int slotNum;

    public TimeSlot(boolean isAvailable, String time, int slotNum) {
        this.isAvailable = isAvailable;
        this.time = time;
        this.slotNum = slotNum;
    }

    public boolean isAvailable() {
        return isAvailable;
    }

    public void setAvailable(boolean available) {
        isAvailable = available;
    }

    public String getTime() {
        return time;
    }

    public void setTime(String time) {
        this.time = time;
    }

    public int getSlotNum() {
        return slotNum;
    }

    public void setSlotNum(int slotNum) {
        this.slotNum = slotNum;
    }
}
