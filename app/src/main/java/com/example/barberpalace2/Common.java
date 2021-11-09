package com.example.barberpalace2;

public class Common {

    public static String convertTimeSlotToString(int slot) {
        switch (slot)
        {
            case 0:
                return "8:00 A.M.";
            case 1:
                return "8:30 A.M.";
            case 2:
                return "9:00 A.M.";
            case 3:
                return "9:30 A.M.";
            case 4:
                return "10:00 A.M.";
            case 5:
                return "10:30 A.M.";
            case 6:
                return "11:00 A.M.";
            case 7:
                return "11:30 A.M.";
            case 8:
                return "1:00 P.M.";
            case 9:
                return "1:30 P.M.";
            case 10:
                return "2:00 P.M.";
            case 11:
                return "2:30 P.M.";
            case 12:
                return "3:00 P.M.";
            case 13:
                return "3:30 P.M.";
            case 14:
                return "4:00 P.M.";
            case 15:
                return "4:30 P.M.";
            case 16:
                return "5:00 P.M.";
            case 17:
                return "5:30 P.M.";
            case 18:
                return "6:00 P.M.";
            case 19:
                return "6:30 P.M.";
            case 20:
                return "7:00 P.M.";
            default:
                return "Closed";
        }
    }
}
