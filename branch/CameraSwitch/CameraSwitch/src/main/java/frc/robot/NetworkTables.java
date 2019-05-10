/*----------------------------------------------------------------------------*/
/* Copyright (c) 2018 FIRST. All Rights Reserved.                             */
/* Open Source Software - may be modified and shared by FRC teams. The code   */
/* must be accompanied by the FIRST BSD license file in the root directory of */
/* the project.                                                               */
/*----------------------------------------------------------------------------*/

package frc.robot;



import edu.wpi.first.networktables.EntryListenerFlags;
import edu.wpi.first.networktables.NetworkTable;
import edu.wpi.first.networktables.NetworkTableEntry;
import edu.wpi.first.networktables.NetworkTableInstance;

/**
 * Add your docs here.
 */
public class NetworkTables {
    public void run (){
        NetworkTableInstance inst = NetworkTableInstance.getDefault();
        NetworkTable table = inst.getTable("datatable");
        NetworkTableEntry yEntry = table.getEntry("Y");
        inst.startClientTeam(93); //find what's needed to put in the y-axis

        table.addEntryListener("X", (Table, key, entry, value, flags) -> {
            System.out.println("X changed value: " +value.getValue());
        }, EntryListenerFlags.kNew | EntryListenerFlags.kUpdate);

        yEntry.addListener(event -> {
            System.out.println("Y changed value: " + event.value.getValue());  
        }, EntryListenerFlags.kNew | EntryListenerFlags.kUpdate);

        try {
            Thread.sleep(100000);
        } catch (InterruptedException ex) {
            System.out.println("interrupted!");
            return;
        }
        



    }
}
