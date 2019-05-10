/*----------------------------------------------------------------------------*/
/* Copyright (c) 2018 FIRST. All Rights Reserved.                             */
/* Open Source Software - may be modified and shared by FRC teams. The code   */
/* must be accompanied by the FIRST BSD license file in the root directory of */
/* the project.                                                               */
/*----------------------------------------------------------------------------*/

package frc.robot.triggers;

import edu.wpi.first.wpilibj.buttons.Trigger;
import edu.wpi.first.wpilibj.command.Command;

/**
 * Add your docs here.
 */
public class ArtificialButton extends Trigger {
  public boolean isActive = false;
  @Override
  public boolean get() {
    return isActive;
  }

  public void whenActive(final Command command) {
    new ButtonScheduler() {
      // private boolean m_pressedLast = get();
        private boolean pressed;
      @Override
      public void execute() {
        pressed = isActive;
        // boolean pressed = get();

        // if (!m_pressedLast && pressed) {
          // command.start();
        //}

      //   m_pressedLast = pressed;
          if(pressed){
            command.start();
          }

       }
    }.start();
  }
}
