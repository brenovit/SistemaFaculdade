package com.qst1.ui;

import javax.swing.*;
import java.awt.*;
import java.awt.event.*;

public class TesteAtalho extends JFrame{
  JButton fechar, min, max;
  public TesteAtalho(){
	  super("Uso de teclas de atalho para botões");
	  Container tela = getContentPane();
	  FlowLayout layout = new FlowLayout(FlowLayout.LEFT);
	  tela.setLayout(layout);
	  fechar = new JButton("Fechar");
	  fechar.setMnemonic(KeyEvent.VK_F);
	  min = new JButton("Minimizar");
	  min.setMnemonic(KeyEvent.VK_M);
	  max = new JButton("Maximizar");
	  max.setMnemonic(KeyEvent.VK_X);
	  tela.add(fechar);
	  tela.add(min);
	  tela.add(max);
	  TratEventos trat = new TratEventos();
	  for(int i = 0; i < tela.getComponentCount(); i++){
		((JButton) tela.getComponent(i)).addActionListener(trat);
	  }
	  setSize(300, 100);
	  setVisible(true);
	  
	  InputMap inputMap = this.getRootPane().getInputMap(JComponent.WHEN_IN_FOCUSED_WINDOW);
	  inputMap.put(KeyStroke.getKeyStroke(KeyEvent.VK_F2, 0),"forward");
	  this.getRootPane().setInputMap(JComponent.WHEN_IN_FOCUSED_WINDOW, inputMap);
	 
	  this.getRootPane().getActionMap().put("forward", new AbstractAction(){
	      private static final long serialVersionUID = 1L;
	      @Override
	      public void actionPerformed(ActionEvent arg0) {
	          System.out.println("F2 foi pressionado");
	          max.doClick();
	      }
	  });
  }
  public static void main(String args[]){
	  TesteAtalho app = new TesteAtalho();
	  app.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
  }
  private class TratEventos implements ActionListener{
	public void actionPerformed(ActionEvent evento){
	  if(evento.getSource() == fechar)
		System.exit(0);
	  if(evento.getSource() == min)
		setExtendedState(ICONIFIED);
	  if(evento.getSource() == max)
		setExtendedState(MAXIMIZED_BOTH);     	 
	}  
  } 
}