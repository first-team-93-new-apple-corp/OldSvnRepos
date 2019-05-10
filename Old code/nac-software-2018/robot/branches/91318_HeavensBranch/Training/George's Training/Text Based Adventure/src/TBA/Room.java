package TBA;

import java.util.*;

public class Room {
	ItemContainer m_Items;
	public String m_Desc;
	Room[] m_Rooms;
	public void printDescription()
	{
		System.out.println(m_Desc);
	}
	public void printRooms()
	{
		//body
	}
	public Room(String desc)
	{
		m_Rooms = new Room[3];
		m_Items = new ItemContainer();
		m_Desc = desc;
	}
	public void addItem(Item item)
	{
		m_Items.add(item);
	}
	/**
	 * adds a room
	 * @param room
	 * the room class
	 * @param dir
	 * the direction of the room
	 */
	public void addRoom(Room room, int dir)
	{
		m_Rooms[dir] = room;
	}
	public Item findItem(String name)
	{
		return m_Items.findItem(name);
	}
}