package snake;

public class Room {

    public static final int ROOM_SIZE = 20;
    public static final int FIELD_SIZE = 20;
    private int roomWidth;
    private int roomHeight;

    public Room(int width, int height) {
        this.roomWidth = width;
        this.roomHeight = height;
    }

    public int getRoomWidth() {
        return roomWidth;
    }

    public void setRoomWidth(int roomWidth) {
        this.roomWidth = roomWidth;
    }

    public int getRoomHeight() {
        return roomHeight;
    }

    public void setRoomHeight(int roomHeight) {
        this.roomHeight = roomHeight;
    }
}
