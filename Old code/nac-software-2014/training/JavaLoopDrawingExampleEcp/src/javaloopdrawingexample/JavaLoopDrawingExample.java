/*
 * N.E.W. Apple Corps
 * FIRST Team 93
 * 2012-2013 Season - Java Programming Example #2 - Drawing a line
 * 
 * Extensions (C) 2012 Plexus Corp. and Appleton Area School District
 */

// Based on Java example from Oracle:
/*
 * Copyright (c) 2010, Oracle. All rights reserved.
 *
 * Redistribution and use in source and binary forms, with or without
 * modification, are permitted provided that the following conditions are met:
 *
 * * Redistributions of source code must retain the above copyright notice,
 *   this list of conditions and the following disclaimer.
 *
 * * Redistributions in binary form must reproduce the above copyright notice,
 *   this list of conditions and the following disclaimer in the documentation
 *   and/or other materials provided with the distribution.
 *
 * * Neither the name of Oracle nor the names of its contributors
 *   may be used to endorse or promote products derived from this software without
 *   specific prior written permission.
 *
 * THIS SOFTWARE IS PROVIDED BY THE COPYRIGHT HOLDERS AND CONTRIBUTORS "AS IS"
 * AND ANY EXPRESS OR IMPLIED WARRANTIES, INCLUDING, BUT NOT LIMITED TO, THE
 * IMPLIED WARRANTIES OF MERCHANTABILITY AND FITNESS FOR A PARTICULAR PURPOSE
 * ARE DISCLAIMED. IN NO EVENT SHALL THE COPYRIGHT OWNER OR CONTRIBUTORS BE
 * LIABLE FOR ANY DIRECT, INDIRECT, INCIDENTAL, SPECIAL, EXEMPLARY, OR
 * CONSEQUENTIAL DAMAGES (INCLUDING, BUT NOT LIMITED TO, PROCUREMENT OF
 * SUBSTITUTE GOODS OR SERVICES; LOSS OF USE, DATA, OR PROFITS; OR BUSINESS
 * INTERRUPTION) HOWEVER CAUSED AND ON ANY THEORY OF LIABILITY, WHETHER IN
 * CONTRACT, STRICT LIABILITY, OR TORT (INCLUDING NEGLIGENCE OR OTHERWISE)
 * ARISING IN ANY WAY OUT OF THE USE OF THIS SOFTWARE, EVEN IF ADVISED OF
 * THE POSSIBILITY OF SUCH DAMAGE.
 */

package javaloopdrawingexample;

import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics;
import java.util.TimerTask;
import javax.swing.InputVerifier;
import javax.swing.JComponent;
import javax.swing.JPanel;
import javax.swing.JTextField;

public class JavaLoopDrawingExample extends javax.swing.JFrame {
    
    // Private variables for demo
    boolean inputA;
    boolean inputB;
    boolean inputC;
    boolean inputD;
    double  inputE;
    double  inputF;
    boolean outputY;
    double  outputZ;
    

    // --------------------------------------------------------------------- //
    // ----- PUT YOUR CUSTOM CODE BELOW THIS LINE.  DO NOT EDIT ABOVE. ----- //
    // --------------------------------------------------------------------- //
    
    // The name of this function may not be changed, but new functions may be
    // added.  This function will be executed on every click of the "Update"
    // button.
    private void updateOutputValues()
    {
        // TODO: You may place code here to update outputY and outputZ
    	// Drawing code belongs in drawOnTheCanvas
    	
        // example:
        outputY = inputA && inputC;
    }
    

    // This function will be executed every time the graphic area needs to be 
    // re-drawn. Again, the name of this function may not be changed.
    // The drawOnTheCanvas function is special with respect to the 
    // updateOutputValues function, because drawOnTheCanvas allows you to draw.
    private void drawOnTheCanvas(DrawingCanvas.ExposedDrawingCanvas canvas)
    {
        final int nInputE = Math.round((float)inputE);  // Is there a better way?
        final int nInputF = Math.round((float)inputF);
        // TODO: Place your loop code in here.
        
        drawHorizontalLine(canvas, 20, 50, 75);
        
        // Example of how to fill in a pixel:
        canvas.drawPoint(10, 15);
    }
    
    private void drawHorizontalLine(
    		DrawingCanvas.ExposedDrawingCanvas canvas,
    		int x, int y,
    		int len)
    {
    	final int x2 = x + len;
    	for (int xi = x; xi < x2; ++xi)
    	{
    		canvas.drawPoint(xi, y);
    	}
    }

    
    // This function will be executed every time the graphic area needs to be 
    // re-drawn. Again, the name of this function may not be changed.
    private void drawOnTheCanvasAdvanced(Graphics canvas)
    {
        final int nInputE = Math.round((float)inputE);
        final int nInputF = Math.round((float)inputF);
        // This is a more full-featured
        
        // You could try this: 
        // canvas.drawArc(10, 10, 110, 110, 45, 90);
        // Or this:
        // canvas.drawArc(10, 10, 110, 110, 45, nInputE);

    }
    
    // --------------------------------------------------------------------- //
    // ----- PUT YOUR CUSTOM CODE ABOVE THIS LINE.  DO NOT EDIT BELOW. ----- //
    // --------------------------------------------------------------------- //
        
    /** Creates new form Find */
    public JavaLoopDrawingExample() {
        // expression example variables
        inputA = false;
        inputB = false;
        inputC = false;
        inputD = false;
        inputE = 0.0;
        inputF = 0.0;
        outputY = false;
        outputZ = 0.0;
        // GUI components
        initComponents();
    }
    
    /** This method is called from within the constructor to
     * initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is
     * always regenerated by the Form Editor.
     */
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        jLabel1 = new javax.swing.JLabel();
        checkBoxA = new javax.swing.JCheckBox();
        checkBoxC = new javax.swing.JCheckBox();
        checkBoxB = new javax.swing.JCheckBox();
        checkBoxD = new javax.swing.JCheckBox();
        buttonUpdate = new javax.swing.JButton();
        buttonClose = new javax.swing.JButton();
        jLabel2 = new javax.swing.JLabel();
        textFieldE = new javax.swing.JTextField();
        textFieldF = new javax.swing.JTextField();
        jLabel3 = new javax.swing.JLabel();
        jLabel4 = new javax.swing.JLabel();
        checkBoxY = new javax.swing.JCheckBox();
        textFieldZ = new javax.swing.JTextField();
        jLabel5 = new javax.swing.JLabel();
        jSeparator1 = new javax.swing.JSeparator();
        panelDrawingCanvas = new DrawingCanvas(this);
        jLabel6 = new javax.swing.JLabel();
        buttonDraw = new javax.swing.JButton();

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);
        setTitle("Find");

        jLabel1.setText("Inputs");

        checkBoxA.setText("A");
        checkBoxA.setBorder(javax.swing.BorderFactory.createEmptyBorder(0, 0, 0, 0));
        checkBoxA.setMargin(new java.awt.Insets(0, 0, 0, 0));

        checkBoxC.setText("C");
        checkBoxC.setBorder(javax.swing.BorderFactory.createEmptyBorder(0, 0, 0, 0));
        checkBoxC.setMargin(new java.awt.Insets(0, 0, 0, 0));

        checkBoxB.setText("B");
        checkBoxB.setBorder(javax.swing.BorderFactory.createEmptyBorder(0, 0, 0, 0));
        checkBoxB.setMargin(new java.awt.Insets(0, 0, 0, 0));

        checkBoxD.setText("D");
        checkBoxD.setBorder(javax.swing.BorderFactory.createEmptyBorder(0, 0, 0, 0));
        checkBoxD.setMargin(new java.awt.Insets(0, 0, 0, 0));

        buttonUpdate.setText("Update");
        buttonUpdate.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                buttonUpdateActionPerformed(evt);
            }
        });

        buttonClose.setText("Close");
        buttonClose.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                buttonCloseActionPerformed(evt);
            }
        });

        jLabel2.setText("Outputs");

        textFieldE.setText("0.0");
        textFieldE.setInputVerifier(new NumberInputVerifier());
        textFieldE.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                textFieldEActionPerformed(evt);
            }
        });

        textFieldF.setText("0.0");
        textFieldF.setInputVerifier(new NumberInputVerifier());
        textFieldF.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                textFieldFActionPerformed(evt);
            }
        });

        jLabel3.setText("E");

        jLabel4.setText("F");

        checkBoxY.setText("Y");
        checkBoxY.setBorder(javax.swing.BorderFactory.createEmptyBorder(0, 0, 0, 0));
        checkBoxY.setMargin(new java.awt.Insets(0, 0, 0, 0));

        textFieldZ.setEditable(false);
        textFieldZ.setText("0.0");
        textFieldZ.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                textFieldZActionPerformed(evt);
            }
        });

        jLabel5.setText("Z");

        panelDrawingCanvas.setBackground(new java.awt.Color(255, 255, 255));
        panelDrawingCanvas.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(0, 0, 0)));

        org.jdesktop.layout.GroupLayout panelDrawingCanvasLayout = new org.jdesktop.layout.GroupLayout(panelDrawingCanvas);
        panelDrawingCanvas.setLayout(panelDrawingCanvasLayout);
        panelDrawingCanvasLayout.setHorizontalGroup(
            panelDrawingCanvasLayout.createParallelGroup(org.jdesktop.layout.GroupLayout.LEADING)
            .add(0, 341, Short.MAX_VALUE)
        );
        panelDrawingCanvasLayout.setVerticalGroup(
            panelDrawingCanvasLayout.createParallelGroup(org.jdesktop.layout.GroupLayout.LEADING)
            .add(0, 239, Short.MAX_VALUE)
        );

        jLabel6.setText("Drawing Canvas");

        buttonDraw.setText("Draw");
        buttonDraw.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                buttonDrawActionPerformed(evt);
            }
        });

        org.jdesktop.layout.GroupLayout layout = new org.jdesktop.layout.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(org.jdesktop.layout.GroupLayout.LEADING)
            .add(layout.createSequentialGroup()
                .addContainerGap()
                .add(layout.createParallelGroup(org.jdesktop.layout.GroupLayout.LEADING)
                    .add(layout.createSequentialGroup()
                        .add(layout.createParallelGroup(org.jdesktop.layout.GroupLayout.LEADING)
                            .add(layout.createSequentialGroup()
                                .add(textFieldE, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE, 46, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE)
                                .addPreferredGap(org.jdesktop.layout.LayoutStyle.UNRELATED)
                                .add(jLabel3))
                            .add(jLabel1)
                            .add(checkBoxA)
                            .add(checkBoxB))
                        .add(135, 135, 135)
                        .add(layout.createParallelGroup(org.jdesktop.layout.GroupLayout.LEADING)
                            .add(layout.createSequentialGroup()
                                .add(layout.createParallelGroup(org.jdesktop.layout.GroupLayout.LEADING)
                                    .add(jLabel2)
                                    .add(checkBoxY))
                                .addPreferredGap(org.jdesktop.layout.LayoutStyle.RELATED, 50, Short.MAX_VALUE)
                                .add(layout.createParallelGroup(org.jdesktop.layout.GroupLayout.TRAILING, false)
                                    .add(buttonClose, org.jdesktop.layout.GroupLayout.DEFAULT_SIZE, org.jdesktop.layout.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                    .add(buttonUpdate)))
                            .add(layout.createSequentialGroup()
                                .add(textFieldZ, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE, 46, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE)
                                .addPreferredGap(org.jdesktop.layout.LayoutStyle.UNRELATED)
                                .add(jLabel5))))
                    .add(checkBoxC)
                    .add(checkBoxD)
                    .add(layout.createSequentialGroup()
                        .add(textFieldF, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE, 46, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(org.jdesktop.layout.LayoutStyle.UNRELATED)
                        .add(jLabel4, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE, 14, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE))))
            .add(jSeparator1, org.jdesktop.layout.GroupLayout.DEFAULT_SIZE, 363, Short.MAX_VALUE)
            .add(layout.createSequentialGroup()
                .addContainerGap()
                .add(jLabel6)
                .addPreferredGap(org.jdesktop.layout.LayoutStyle.RELATED, 208, Short.MAX_VALUE)
                .add(buttonDraw, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE, 67, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE))
            .add(layout.createSequentialGroup()
                .addContainerGap()
                .add(panelDrawingCanvas, org.jdesktop.layout.GroupLayout.DEFAULT_SIZE, org.jdesktop.layout.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addContainerGap())
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(org.jdesktop.layout.GroupLayout.LEADING)
            .add(layout.createSequentialGroup()
                .addContainerGap()
                .add(layout.createParallelGroup(org.jdesktop.layout.GroupLayout.LEADING)
                    .add(layout.createSequentialGroup()
                        .add(jLabel2)
                        .addPreferredGap(org.jdesktop.layout.LayoutStyle.RELATED)
                        .add(checkBoxY))
                    .add(layout.createSequentialGroup()
                        .add(jLabel1)
                        .addPreferredGap(org.jdesktop.layout.LayoutStyle.RELATED)
                        .add(checkBoxA)
                        .addPreferredGap(org.jdesktop.layout.LayoutStyle.RELATED)
                        .add(checkBoxB)
                        .addPreferredGap(org.jdesktop.layout.LayoutStyle.RELATED)
                        .add(checkBoxC)
                        .addPreferredGap(org.jdesktop.layout.LayoutStyle.RELATED)
                        .add(checkBoxD)
                        .add(40, 40, 40)
                        .add(layout.createParallelGroup(org.jdesktop.layout.GroupLayout.BASELINE)
                            .add(textFieldE, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE, org.jdesktop.layout.GroupLayout.DEFAULT_SIZE, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE)
                            .add(jLabel3)
                            .add(textFieldZ, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE, org.jdesktop.layout.GroupLayout.DEFAULT_SIZE, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE)
                            .add(jLabel5))
                        .addPreferredGap(org.jdesktop.layout.LayoutStyle.UNRELATED)
                        .add(layout.createParallelGroup(org.jdesktop.layout.GroupLayout.BASELINE)
                            .add(textFieldF, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE, org.jdesktop.layout.GroupLayout.DEFAULT_SIZE, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE)
                            .add(jLabel4)))
                    .add(layout.createSequentialGroup()
                        .add(buttonUpdate)
                        .addPreferredGap(org.jdesktop.layout.LayoutStyle.RELATED)
                        .add(buttonClose)))
                .add(18, 18, 18)
                .add(jSeparator1, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE, 10, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(org.jdesktop.layout.LayoutStyle.RELATED)
                .add(layout.createParallelGroup(org.jdesktop.layout.GroupLayout.BASELINE)
                    .add(jLabel6)
                    .add(buttonDraw))
                .add(18, 18, 18)
                .add(panelDrawingCanvas, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE, org.jdesktop.layout.GroupLayout.DEFAULT_SIZE, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE)
                .addContainerGap())
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void buttonCloseActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_buttonCloseActionPerformed
        // TODO add your handling code here:
        System.exit(0);
    }//GEN-LAST:event_buttonCloseActionPerformed

    private void buttonUpdateActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_buttonUpdateActionPerformed
        // read inputs
        inputA = checkBoxA.isSelected();
        inputB = checkBoxB.isSelected();
        inputC = checkBoxC.isSelected();
        inputD = checkBoxD.isSelected();
        inputE = Double.valueOf(textFieldE.getText());
        inputF = Double.valueOf(textFieldF.getText());
        // run custom function
        updateOutputValues();
        // report outputs
        checkBoxY.setSelected(outputY);
        textFieldZ.setText(String.valueOf(outputZ));
    }//GEN-LAST:event_buttonUpdateActionPerformed

    private void textFieldEActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_textFieldEActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_textFieldEActionPerformed

    private void textFieldFActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_textFieldFActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_textFieldFActionPerformed

    private void textFieldZActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_textFieldZActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_textFieldZActionPerformed

    private void buttonDrawActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_buttonDrawActionPerformed
        buttonUpdateActionPerformed(evt);
        panelDrawingCanvas.repaint();
    }//GEN-LAST:event_buttonDrawActionPerformed
    
    /**
     * @param args the command line arguments
     */
    public static void main(String args[]) {
        /* Set the Nimbus look and feel */
        //<editor-fold defaultstate="collapsed" desc=" Look and feel setting code (optional) ">
        /* If Nimbus (introduced in Java SE 6) is not available, stay with the default look and feel.
         * For details see http://download.oracle.com/javase/tutorial/uiswing/lookandfeel/plaf.html 
         */
        try {
            javax.swing.UIManager.LookAndFeelInfo[] installedLookAndFeels=javax.swing.UIManager.getInstalledLookAndFeels();
            for (int idx=0; idx<installedLookAndFeels.length; idx++)
                if ("Nimbus".equals(installedLookAndFeels[idx].getName())) {
                    javax.swing.UIManager.setLookAndFeel(installedLookAndFeels[idx].getClassName());
                    break;
                }
        } catch (ClassNotFoundException ex) {
            java.util.logging.Logger.getLogger(JavaLoopDrawingExample.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (InstantiationException ex) {
            java.util.logging.Logger.getLogger(JavaLoopDrawingExample.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (IllegalAccessException ex) {
            java.util.logging.Logger.getLogger(JavaLoopDrawingExample.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (javax.swing.UnsupportedLookAndFeelException ex) {
            java.util.logging.Logger.getLogger(JavaLoopDrawingExample.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        }
        //</editor-fold>

        final JavaLoopDrawingExample myApp = new JavaLoopDrawingExample();
        myApp.setVisible(true);
        /*
        java.util.TimerTask timer = new TimerTask();
        new Runnable() {
            public void run() {
                repaint();
            }
        });
        */
        //java.awt.EventQueue.
                
        // Schedule the next repaint
        /*
        java.awt.EventQueue.invokeLater(new Runnable() {
            public void run() {
                myApp.repaint();
                try
                {
                    Thread.sleep(100);
                }
                catch (Exception e)
                {
                    
                }
            }
        });
        */
    }
    
    private class NumberInputVerifier extends InputVerifier {
        public NumberInputVerifier() {
        }

        public boolean verify(JComponent input) {
            JTextField textField = (JTextField) input;
            String inputStr = textField.getText();
            double inputNum = Double.valueOf(inputStr);
            if (Double.isNaN(inputNum) == false) {
                textField.setText(String.valueOf(inputNum));
                return true;
            } else {
                return false;
            }
        }
    }
    
    private class DrawingCanvas extends JPanel
    {
        Graphics m_g;
        JavaLoopDrawingExample m_app;
        
        public DrawingCanvas(JavaLoopDrawingExample app)
        {
            super();
            m_app = app;
        }
        
        @Override
        public void paintComponent(Graphics g)
        {
            // Give ourselves a reference for the simplified interface
            m_g = g;
            // Not sure what our parent is doing, but this seems safe
            super.paintComponent(g);
            // Setup, then blank out with white, so we always start fresh
            g.setColor(Color.white);
            g.setFont(null);
            g.clearRect(1, 1, this.getBounds().width, this.getBounds().height);
            g.fillRect(1, 1, this.getBounds().width, this.getBounds().height);
            g.setColor(Color.red);
            //g.drawRect(10,10,0,0);
            //drawPoint(20,20);
            
            // Call spiffy functions for the training presentation
            m_app.drawOnTheCanvas(new ExposedDrawingCanvas(this));
            m_app.drawOnTheCanvasAdvanced(g);
            
            // ADVANCED EXERCISE: YOU CAN DRAW IN HERE
            
            // END ADVANCED EXERCISE SPACE
            
            // Clear the special reference we made - don't want to abuse this
            m_g = null;
            
            
            // Schedule the next repaint
            java.awt.EventQueue.invokeLater(new Runnable() {
                public void run() {
                    m_app.repaint();
                    try
                    {
                        Thread.sleep(100);
                    }
                    catch (Exception e)
                    {   
                    
                    }
                }
            }); 
        }
        
        public void drawPoint(int x, int y)
        {
            m_g.drawRect(x,y,0,0);
        }
        
        public class ExposedDrawingCanvas
        {
            private DrawingCanvas m_parent;
            public ExposedDrawingCanvas(DrawingCanvas parent)
            {
                m_parent = parent;
            }
            public void drawPoint(int x, int y)
            {
                m_parent.drawPoint(x, y);
            }
        }
    }

    
    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton buttonClose;
    private javax.swing.JButton buttonDraw;
    private javax.swing.JButton buttonUpdate;
    private javax.swing.JCheckBox checkBoxA;
    private javax.swing.JCheckBox checkBoxB;
    private javax.swing.JCheckBox checkBoxC;
    private javax.swing.JCheckBox checkBoxD;
    private javax.swing.JCheckBox checkBoxY;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JLabel jLabel3;
    private javax.swing.JLabel jLabel4;
    private javax.swing.JLabel jLabel5;
    private javax.swing.JLabel jLabel6;
    private javax.swing.JSeparator jSeparator1;
    private javax.swing.JPanel panelDrawingCanvas;
    private javax.swing.JTextField textFieldE;
    private javax.swing.JTextField textFieldF;
    private javax.swing.JTextField textFieldZ;
    // End of variables declaration//GEN-END:variables
    
}
