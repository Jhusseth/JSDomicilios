package interfaz;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;

import javax.swing.ImageIcon;
import javax.swing.JOptionPane;
import javax.swing.JPanel;

import model.Algoritmo_Dijkstra;
import model.Arboles;
import model.Deliver;
import model.Graph;
import model.Pintar;

public class City extends JPanel implements MouseListener{
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	public Main main;
	
	private Arboles arboles;
	private int tope=0;  
	 
	private int nodoFin;   
	 
	private int permanente;   
	 
	int n=0,nn=0,id,id2;  
	 
	private int aristaMayor;
	
	private Graph<Deliver, String> graph ;
	
	public City(Main m){
		main =m;
		arboles=new Arboles();
		addMouseListener(this);
		
		graph = new Graph<Deliver,String>(false);
	}
	
	@Override
	public void paint(Graphics g){
		super.paint(g);
		ImageIcon fondo = new ImageIcon("data/fondo.gif");
		g.drawImage(fondo.getImage(), 0, 0, main.getWidth(), main.getHeight(), null);
	}
	 public void R_repaint(int tope, Arboles arboles){
		 for (int j = 0; j < tope; j++) {  
			 for (int k = 0; k < tope; k++) {     
				 if(arboles.getmAdyacencia(j, k) == 1)
					 Pintar.pintarLinea(this.getGraphics(),arboles.getCordeX(j),arboles.getCordeY(j), arboles.getCordeX(k), arboles.getCordeY(k),arboles.getmCoeficiente(j, k));
			 }
		 } 
		 for (int j = 0; j < tope; j++)    
			 Pintar.pintarCasita(this.getGraphics(), arboles.getCordeX(j),arboles.getCordeY(j),String.valueOf(arboles.getNombre(j))); 
	 }

	@Override
	public void mouseClicked(MouseEvent e) {
		
	}

	@Override
	public void mouseEntered(MouseEvent e) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void mouseExited(MouseEvent e) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void mousePressed(MouseEvent evt) {
		int xxx, yyy;   	       
		xxx=evt.getX();	      
		yyy=evt.getY();	      
		if(evt.isMetaDown()){	          
			clicIzqSobreNodo(xxx, yyy );            	          
			if(nn==2){	              
				nn=0;	               
				Algoritmo_Dijkstra Dijkstra = new Algoritmo_Dijkstra(arboles,tope,permanente, nodoFin,main);	              
				Dijkstra.dijkstra();	              
				main.acumulado(""+Dijkstra.getAcumulado());	               	           
			}	       
		}	       
		else{       
			if(!cicDerechoSobreNodo(xxx,yyy)){          
				if(tope<50){	               
					arboles.setCordeX(tope,xxx);	              
					arboles.setCordeY(tope,yyy);	              
					arboles.setNombre(tope, main.iniciarPedido().getName());	            
					Pintar.pintarCasita(this.getGraphics(),arboles.getCordeX(tope), arboles.getCordeY(tope),String.valueOf(arboles.getNombre(tope)));	          
					tope++;          	          
				} 	         
				else JOptionPane.showMessageDialog(null,"Se ha llegado al Maximo de nodos..");	       
			}          
			if(n==2 ){	             
				n=0; 	             
				int  ta = ingresarTamano("Ingrese Distancia (Km)");	             
				if(aristaMayor < ta) aristaMayor=ta;	            				
				arboles.setmAdyacencia(id2, id, 1);	            
				arboles.setmAdyacencia(id, id2, 1);	             
				arboles.setmCoeficiente(id2, id,ta );	             
				arboles.setmCoeficiente(id, id2, ta);	             
				Pintar.pintarLinea(this.getGraphics(),arboles.getCordeX(id), arboles.getCordeY(id), arboles.getCordeX(id2), arboles.getCordeY(id2), ta); 	             
				Pintar.pintarCasita(this.getGraphics(),arboles.getCordeX(id), arboles.getCordeY(id),String.valueOf(arboles.getNombre(id)));	             
				Pintar.pintarCasita(this.getGraphics(),arboles.getCordeX(id2), arboles.getCordeY(id2),String.valueOf(arboles.getNombre(id2)));	              
				id=-1;	              
				id2=-1;
			}	        
		}	
	}

	public Arboles getArboles() {
		return arboles;
	}

	public void setArboles(Arboles arboles) {
		this.arboles = arboles;
	}

	public int getTope() {
		return tope;
	}

	public void setTope(int tope) {
		this.tope = tope;
	}

	@Override
	public void mouseReleased(MouseEvent e) {
		// TODO Auto-generated method stub
		
	}
	
	
	public static int ingresarNodoOrigen(String nodoOrige, String noExiste,int tope){	    
		int nodoOrigen = 0;	        
		try{	            
			nodoOrigen = Integer.parseInt(JOptionPane.showInputDialog(""+nodoOrige));   	            
			if(nodoOrigen>=tope){  	                  
				JOptionPane.showMessageDialog(null,""+noExiste+"\nDebe de ingresar  un Nodo existente");	                  
				nodoOrigen = ingresarNodoOrigen(nodoOrige,noExiste, tope);	            
			}	        
		}catch(Exception ex){	            
			nodoOrigen = ingresarNodoOrigen(nodoOrige,noExiste,tope);	        
		}	        
		return nodoOrigen;
	}          
	 
	public  int ingresarTamano(String tama){        	       
		int tamano = 0;	       
		try{	           
			tamano = Integer.parseInt(JOptionPane.showInputDialog(""+tama));	           
			if(tamano<1){ JOptionPane.showMessageDialog(null,"Debe Ingresar un Tama�o Aceptado..");	               
			tamano = ingresarTamano(tama);	           
			}	        
		}
		catch(Exception ex){	            
			tamano = ingresarTamano(tama);	        
		}	       
		return tamano;	   
	}
	
	public boolean cicDerechoSobreNodo(int xxx,int yyy){ 		
		for (int j = 0; j < tope; j++) { 
	            
			if((xxx+2) > arboles.getCordeX(j) && xxx < (arboles.getCordeX(j)+13) && (yyy+2) > arboles.getCordeY(j) && yyy<(arboles.getCordeY(j)+13) ) {	                                       	               
				if(n==0){	                 					
					id = j;	                   
					R_repaint(tope,arboles);	                  
					Pintar.clickSobreNodo(this.getGraphics(), arboles.getCordeX(j), arboles.getCordeY(j), null,Color.orange);       	                  
					n++;                   	              
				}	              
				else{ 	                  
					id2=j;                   	                  
					n++;	                   
					Pintar.clickSobreNodo(this.getGraphics(), arboles.getCordeX(j), arboles.getCordeY(j), null,Color.orange);       	                   
					if(id==id2){	                   
						n=0;	                  
						Pintar.pintarCasita(this.getGraphics(),arboles.getCordeX(id), arboles.getCordeY(id),String.valueOf(arboles.getNombre(id)));	                   
						id=-1;	                    
						id2=-1;	                  
					}	               
				} 	              
				nn=0;	               
				return true;              	            
			}	        
		}	     
		return false;	
	}  
	
	
	public void clicIzqSobreNodo(int xxx, int yyy){          
		for (int j = 0; j <tope; j++) {	             
			if((xxx+2) > arboles.getCordeX(j) && xxx < (arboles.getCordeX(j)+13) && (yyy+2) > arboles.getCordeY(j) && yyy<(arboles.getCordeY(j)+13) ) {	             
				if(nn==0){	            
					permanente =j; 	            
					R_repaint(tope, arboles);                   	            
				}	             
				else{ nodoFin = j;}	             
				nn++;	             
				n=0;	             
				id =-1;	             
				Pintar.clickSobreNodo(this.getGraphics(), arboles.getCordeX(j), arboles.getCordeY(j), null,Color.GREEN);  	             
				break;	              
			}	           
		}
	}
	
	public void pintarDijkstra(){ 
		if(tope>=2){	         
			permanente = ingresarNodoOrigen("Ingrese Nodo Origen..","nodo Origen No existe",tope);// hacemos el llamano de la funcion         	         
			nodoFin =  ingresarNodoOrigen("Ingrese Nodo Fin..","nodo fin No existe",tope);	           
			Algoritmo_Dijkstra Dijkstra = new Algoritmo_Dijkstra(arboles,tope,permanente,nodoFin,main);	            
			Dijkstra.dijkstra();	           
			main.acumulado(""+Dijkstra.getAcumulado());
		}
		else JOptionPane.showMessageDialog(null,"Se deben de crear mas nodos ... ");
	}
}
