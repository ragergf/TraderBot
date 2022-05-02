package mx.org.traderbot.trade.view.desktop;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.IOException;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

import javax.swing.JCheckBox;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.Timer;
import javax.swing.plaf.basic.BasicTabbedPaneUI.TabbedPaneLayout;

import org.apache.log4j.Logger;
import org.codehaus.jackson.JsonGenerationException;
import org.codehaus.jackson.map.JsonMappingException;
import org.codehaus.jackson.map.ObjectMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;
import org.springframework.context.support.AbstractApplicationContext;

import mx.org.traderbot.exception.BotException;
import mx.org.traderbot.fibo.dao.FiboDAO;
import mx.org.traderbot.fibo.model.FiboModel;
import mx.org.traderbot.fibo.vo.Fibo;
import mx.org.traderbot.fibo.vo.FiboSoporte;
import mx.org.traderbot.fibo.vo.Punto;
import mx.org.traderbot.fibo.vo.Soporte;
import mx.org.traderbot.model.DataTableModel;
import mx.org.traderbot.model.EstrategiaBajaModel;
import mx.org.traderbot.model.TraderBotModel;
import mx.org.traderbot.order.vo.Order;
import mx.org.traderbot.orderbook.vo.OrderResume;
import mx.org.traderbot.orderbook.vo.OrderBook;
import mx.org.traderbot.service.DataService;
import mx.org.traderbot.spring.config.AppConfig;
import mx.org.traderbot.trade.vo.Trade;
import mx.org.traderbot.util.Constantes;
import mx.org.traderbot.util.Util;
import mx.org.traderbot.vo.DataBot;

public class TradeBaseView extends TradeView {
	final static Logger logger = Logger.getLogger(TradeBaseView.class);
	
//  inicio@rager
	
//	DATABOT
//	DataBot dataBot;	
	
//	Delay
	int delayPrice = 500;
	int delayEstrategiaFibo = 10000;
	int delayOrderBook = 500;
	int delayActiveOrder=10000;
	int delayEstrategiaBaja = 300;
	
//	PRICE
	private javax.swing.Timer timerPrice, timerOrderBook, timerActiveOrder, 
	timerEstrategiaFibo, timerEstrategiaBaja, timerEstrategiaBaja2,
	timerEstrategiaBaja_5, timerEstrategiaBaja2_5;
	DataService<Trade> data; 
	Trade[] trade;
	BigDecimal lastPriceNumber;
	BigDecimal priceNumber;
	int compare;
	DataTableModel<Trade> modelTrade = null;
	String[] ColumnsTrade = {"id", "Precio", "Cantidad", "Postura", "Hora"};
	
  
//	ORDERBOOK
	List<OrderResume> ordenesVenta= null;
	List<OrderResume> ordenesCompra= null;
	DataTableModel<OrderResume> modelVenta = null;
	DataTableModel<OrderResume> modelCompra = null;
	DataService<OrderBook> orderBookService = new DataService<OrderBook>();
	OrderBook orderBook;
	String[] Columns = {"Price","Size"};
	
	
//	SOPORTES
	FiboDAO fiboDAO = new FiboDAO();
	Fibo fibo= null;
	DataTableModel<Punto> modelFibo= null;
	String[] ColumnsFibo = {"Soporte"};
	FiboModel fiboModel= null;
	
//	ACTIVE_ORDERS
	Order[] activeOrder=null;
	DataTableModel<Order> modelActiveOrder=null;
	DataService<Order> serviceActiveOrde=null;
	String[] ColumnsActiveOrder = {"id",
			"clientOrderId",
			"symbol",
			"side",
			"status",
			"type",
			"timeInForce",
			"quantity",
			"price",
			"cumQuantity",
			"createdAt",
			"updatedAt",
			"stopPrice",
			"expireTime" };
	List<Order> listActiveOrder=null;
	String cancel_clientOrderId=null;
	DataService<Order> seriviceCancelar= null;
	
//	TRADE
	DataService<Order> orderBuySellService;
	FiboSoporte fiboSoporte= null;
	
//	TRADER BOT
	TraderBotModel modelProcimaOrdem= null; 
	DataTableModel<Order> modelProximaOrden = null;
	List<Order> listProximaOrden;
	String[] ColumnsOrdenesProximas = {"id",
			"clientOrderId",
			"symbol",
			"side",
			"status",
			"type",
			"timeInForce",
			"quantity",
			"price",
			"cumQuantity",
			"createdAt",
			"updatedAt",
			"stopPrice",
			"expireTime" };
	
//	CONTROL DE PROCESOS
	javax.swing.JCheckBox [] checks;
	javax.swing.Timer [] timers;
	

  
//  fin@rager
  
  public TradeBaseView() {
      super();
      initBaseComponents();
  }
  
  public void intOrdenesProximas()
  {
	  modelProcimaOrdem = new TraderBotModel();
	  listProximaOrden = modelProcimaOrdem.getListProximasOrdenes(precio.getText(), fiboSoporte);	 	 	  
	  modelProximaOrden = new DataTableModel<Order>(listProximaOrden);
	  modelProximaOrden.setColumnNames(ColumnsOrdenesProximas);
	  tabla_ordenes_proximas= new JTable(modelProximaOrden);
	  jScrollPane7.setHorizontalScrollBarPolicy(JScrollPane.HORIZONTAL_SCROLLBAR_AS_NEEDED);
	  tabla_ordenes_proximas.setAutoResizeMode(JTable.AUTO_RESIZE_OFF);
	  jScrollPane7.setViewportView(tabla_ordenes_proximas);
	  
	  timerEstrategiaFibo = new Timer (delayEstrategiaFibo, new ActionListener () 
      { 
          public void actionPerformed(ActionEvent e) 
          { 
        	  logger.info("Deteniendo Precio");
        	  if(timerPrice.isRunning())
        		  timerPrice.stop();
        	  logger.info("Iniciando Precio");
        	  if(!timerPrice.isRunning())
        		  timerPrice.start();
        	  
        	  checkVentasCompras.setSelected(true);


          } 
      });
	  
//	  timerEstrategiaFibo.start();
  }
  
  public void initShowPrice()
  {
//    inicio@rager
    trade = null;
	data = new DataService<Trade>();
	lastPriceNumber = new BigDecimal(0);
	priceNumber=new BigDecimal(0);
//	precio = dataBot.getPrice();
	
//	AbstractApplicationContext  context = new AnnotationConfigApplicationContext(AppConfig.class);
//	dataBot = (DataBot)context.getBean("DataBot");
//	precio = dataBot.getPrice();
	
//	price
	
	logger.info("Inicalizando Timer");
    
	timerPrice = new Timer (delayPrice, new ActionListener () 
    { 
        public void actionPerformed(ActionEvent e) 
        { 
        	
        	Object arreglo=null;
			try {
				arreglo = data.getData(Constantes.TRADE_TYPE, Trade[].class, null);
			} catch (BotException e1) {
				// TODO Auto-generated catch block
				e1.printStackTrace();
			}
			catch (Exception e1) {
				// TODO Auto-generated catch block
				e1.printStackTrace();
			}
        	trade = (Trade[]) arreglo;
        	
        	precio.setText(trade[0].getPrice());
        	logger.info("precio: "+precio.getText());
//        	precio.setText(trade[0].getPrice());
//        	precio.setText(DataBot.);
        	priceNumber = new BigDecimal(trade[0].getPrice());
        	logger.info("lastPriceNumber: "+lastPriceNumber.doubleValue());
        	logger.info("priceNumber: " + priceNumber.doubleValue());
        	logger.info("Compare to:"+ priceNumber.compareTo(lastPriceNumber));
        	
        	compare = priceNumber.compareTo(lastPriceNumber);
        	
        	switch(compare)
        	{
        		case -1:
        			precio.setForeground(Color.red);
        			break;
        		case 0:
            		break;
        		case 1:
        			precio.setForeground(Color.green);
            		break;
        	}
        	            
        	lastPriceNumber = new BigDecimal(trade[0].getPrice()); 
        	
        	
        	logger.info("Precio: " + trade[0].getPrice());
        	
        	modelTrade = new DataTableModel<Trade>(trade);
	       	modelTrade.setColumnNames(ColumnsTrade);
	       	tablaTrades = new JTable(modelTrade);
	       	
//	       	tablaTrade.getColumnModel().getColumn(1).setPreferredWidth(250);
//	       	tablaTrade.getColumnModel().getColumn(2).setPreferredWidth(250);
//	       	tablaTrade.getColumnModel().getColumn(3).setPreferredWidth(250);
	       	tablaTrades.getColumnModel().getColumn(4).setPreferredWidth(170);
	       	
	       	jScrollPane3.setHorizontalScrollBarPolicy(JScrollPane.HORIZONTAL_SCROLLBAR_AS_NEEDED);
	       	tablaTrades.setAutoResizeMode(JTable.AUTO_RESIZE_OFF);
	       	jScrollPane3.setViewportView(tablaTrades);
	       	
        	
        	
        } 
    });
    logger.info("timer start");
//    timerPrice.start();
    logger.info("timer ejectuado");
//    fin@rager
  }
  
  public void initShowOrderBook()
  {
	  try {
		orderBook = orderBookService.getData(Constantes.ORDERBOOK_TYPE, OrderBook.class, null);
	} catch (BotException e1) {
		// TODO Auto-generated catch block
		e1.printStackTrace();
	}
	  catch (Exception e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}
  	
	  ordenesVenta = orderBook.getAsk();
	  ordenesCompra = orderBook.getBid();
	  
  	
	  modelVenta = new DataTableModel<OrderResume>(ordenesVenta);
	  modelVenta.setColumnNames(Columns);
	  
	  tablaVenta = new JTable(modelVenta);
	  jScrollPane2.setViewportView(tablaVenta);
	  
	  
	  modelCompra = new DataTableModel<OrderResume>(ordenesCompra);
	  modelCompra.setColumnNames(Columns);
	  tablaCompra = new JTable(modelCompra);
	  jScrollPane1.setViewportView(tablaCompra);

      timerOrderBook = new Timer (delayOrderBook, new ActionListener () 
      { 
          public void actionPerformed(ActionEvent e) 
          { 
        
          	try {
				orderBook = orderBookService.getData(Constantes.ORDERBOOK_TYPE, OrderBook.class, null);
			} catch (BotException e1) {
				// TODO Auto-generated catch block
				e1.printStackTrace();
			}
          	catch (Exception e1) {
				// TODO Auto-generated catch block
				e1.printStackTrace();
			}
          	ordenesVenta = orderBook.getAsk();
          	modelVenta.updateTable(ordenesVenta);
          	
          	ordenesCompra= orderBook.getBid();
          	modelCompra.updateTable(ordenesCompra);
          } 
      });
//      timerOrderBook.start();
  }
  
  public void initFibo()
  {
	  fibo = fiboDAO.getFibo();
	  fiboModel = new FiboModel();
	  modelFibo = new DataTableModel<Punto>(fibo.getSoportesResistencias());

	  modelFibo.setColumnNames(ColumnsFibo);
	  tablaSoportes = new JTable(modelFibo);
	  jScrollPane5.setViewportView(tablaSoportes);
	  
	  borrarSoporte.addActionListener(new java.awt.event.ActionListener() {
          public void actionPerformed(java.awt.event.ActionEvent evt) {
              borrarSoporteActionPerformed(evt);
          }
      });
	  
	  agregarSoporte.addActionListener(new java.awt.event.ActionListener() {
          public void actionPerformed(java.awt.event.ActionEvent evt) {
              agregarSoporteActionPerformed(evt);
          }
      });
	  
	  calcular_retroceso.addActionListener(new java.awt.event.ActionListener() {
          public void actionPerformed(java.awt.event.ActionEvent evt) {
        	  calcular_retrocesoActionPerformed(evt);
          }
      });

  }
  
  public void initActiveOrder()
  {
	  serviceActiveOrde = new DataService<Order>();
	  Object array=null;
	try {
		array = serviceActiveOrde.getData(Constantes.ACTIVEORDERS_SYMBOL_TYPE, Order[].class, "symbol=btcusd");
	} catch (BotException e1) {
		// TODO Auto-generated catch block
		e1.printStackTrace();
	}
	catch (Exception e1) {
		// TODO Auto-generated catch block
		e1.printStackTrace();
	}
	  activeOrder = (Order[])array;	  
	  modelActiveOrder=new DataTableModel<Order>(activeOrder);
	  modelActiveOrder.setColumnNames(ColumnsActiveOrder);
	  tablaOrdenes.setModel(modelActiveOrder);
	  jScrollPane4.setHorizontalScrollBarPolicy(JScrollPane.HORIZONTAL_SCROLLBAR_AS_NEEDED);
	  tablaOrdenes.setAutoResizeMode(JTable.AUTO_RESIZE_OFF);
	  jScrollPane4.setViewportView(tablaOrdenes);

      timerActiveOrder = new Timer (delayActiveOrder, new ActionListener () 
      { 
          public void actionPerformed(ActionEvent e) 
          {         
        	  Object array=null;
			try {
				array = serviceActiveOrde.getData(Constantes.ACTIVEORDERS_SYMBOL_TYPE, Order[].class, "symbol=btcusd");
			} catch (BotException e1) {
				// TODO Auto-generated catch block
				e1.printStackTrace();
			}
			catch (Exception e1) {
				// TODO Auto-generated catch block
				e1.printStackTrace();
			}
        	  activeOrder = (Order[])array;        	 
        	  modelActiveOrder.updateTable(activeOrder);
          } 
      });
      
//      timerActiveOrder.start();
      
      tablaOrdenes.addMouseListener(new java.awt.event.MouseAdapter() {
    	    @Override
    	    public void mouseClicked(java.awt.event.MouseEvent evt) {
    	        int row = tablaOrdenes.rowAtPoint(evt.getPoint());
    	        cancel_clientOrderId = tablaOrdenes.getValueAt(row, 1).toString();
    	        resumen_orden.setText(
    	        tablaOrdenes.getValueAt(row, 1).toString() + " | " +
    	        tablaOrdenes.getValueAt(row, 2).toString() + " | " +
    	        tablaOrdenes.getValueAt(row, 3).toString() + " | " +
    	        tablaOrdenes.getValueAt(row, 4).toString() + " | " +
    	        tablaOrdenes.getValueAt(row, 5).toString() + " \n"+
    	        tablaOrdenes.getValueAt(row, 6).toString() + " | "+
    	        tablaOrdenes.getValueAt(row, 7).toString() + " | "+
    	        tablaOrdenes.getValueAt(row, 8).toString() + " | "
    	        );
    	        
    	        compra_cantidad.setText(tablaOrdenes.getValueAt(row, 7).toString());
    	        compra_precio.setText(tablaOrdenes.getValueAt(row, 8).toString());
    	        
    	    }
    	});
      
      cancelar_boton.addActionListener(new java.awt.event.ActionListener() {
          public void actionPerformed(java.awt.event.ActionEvent evt) {
              cancelar_botonActionPerformed(evt);
          }
      });
  }
  
  public void initCompraVenta()
  {
	  orderBuySellService = new DataService<Order>();
	  comprar_boton.addActionListener(new java.awt.event.ActionListener() {
          public void actionPerformed(java.awt.event.ActionEvent evt) {
              comprar_botonActionPerformed(evt);
          }
      });
	  
	  vender_boton.addActionListener(new java.awt.event.ActionListener() {
          public void actionPerformed(java.awt.event.ActionEvent evt) {
        	  vender_botonActionPerformed(evt);
          }
      });
  }
  
  public void initFiboSoporte()
  {
	  fiboSoporte = new FiboSoporte() ;
	  fiboSoporte.setSoportes(new ArrayList<Soporte>());
	  
	  for(Punto p :fibo.getSoportesResistencias())
	  {
		  fiboSoporte.getSoportes().add(new Soporte(p));
	  }
	  Util.printFiboSoporte(fiboSoporte);
	  
	  
  }
  
  public void initControlProcesos()
  {
	  
	  checks = new JCheckBox[8];
	  timers = new javax.swing.Timer[8];
	  
	  checks[0] = checkLibroOrdenes;
	  checks[1] = checkVentasCompras;
	  checks[2] = checkOrdenesActivas;
	  checks[3] = checkEstrategiaBaja;
	  checks[4] = checkEstrategiaFibo;
	  checks[5] = checkEstrategiaBaja2;
	  checks[6] = checkEstrategiaBaja_5;
	  checks[7] = checkEstrategiaBaja2_5;
	  
	  timers[0] = timerOrderBook;
	  timers[1] = timerPrice;
	  timers[2] = timerActiveOrder;
	  timers[3] = timerEstrategiaBaja;
	  timers[4] = timerEstrategiaFibo;
	  timers[5] = timerEstrategiaBaja2;
	  timers[6] = timerEstrategiaBaja_5;
	  timers[7] = timerEstrategiaBaja2_5;
	  
	  for(int i = 0 ; i < checks.length ; i++)
	  {
		  if(checks[i] != null)
		  {
//			  checks[i].setSelected(true);
		  
		  
			  checks[i].addActionListener(new java.awt.event.ActionListener() {
		          public void actionPerformed(java.awt.event.ActionEvent evt) {
		        	  controlProcesoActionPerformed(evt);
		          }
		      });
		  }
	  }
	  
	  
	  
	  
	  boton_iniciar_proceso.addActionListener(new java.awt.event.ActionListener() {
          public void actionPerformed(java.awt.event.ActionEvent evt) {
        	  boton_iniciar_procesoActionPerformed(evt);
          }
      });
	  
	  boton_detener_proceso.addActionListener(new java.awt.event.ActionListener() {
          public void actionPerformed(java.awt.event.ActionEvent evt) {
        	  boton_detener_procesoActionPerformed(evt);
          }
      });
  }
  
  public void initEstrategiaBaja()
  {
//	  MACDFrame = new MACDView();
	  MACDFrame.setVisible(false);
//	  modelEstrategiaBaja= new EstrategiaBajaModel();
	  
	  timerEstrategiaBaja = new Timer (delayEstrategiaBaja, new ActionListener () 
      { 
          public void actionPerformed(ActionEvent e) 
          {         
        	  modelEstrategiaBaja[0].run3("M3",2, 1);
          } 
      });
	  
	  timerEstrategiaBaja2 = new Timer (delayEstrategiaBaja, new ActionListener () 
      { 
          public void actionPerformed(ActionEvent e) 
          {         
        	  modelEstrategiaBaja[1].run3("M5",2, 1);
          } 
      });
	  
	  timerEstrategiaBaja_5 = new Timer (delayEstrategiaBaja, new ActionListener () 
      { 
          public void actionPerformed(ActionEvent e) 
          {         
        	  modelEstrategiaBaja[2].run3("M15",2, 1);
          } 
      });
	  
	  timerEstrategiaBaja2_5 = new Timer (delayEstrategiaBaja, new ActionListener () 
      { 
          public void actionPerformed(ActionEvent e) 
          {         
        	  modelEstrategiaBaja[3].run3("M30",2, 1);
          } 
      });
	  
	  botonEstrategiaBaja.addActionListener(new java.awt.event.ActionListener() {
          public void actionPerformed(java.awt.event.ActionEvent evt) {
        	  MACDFrame.setVisible(true);
          }
      });
	  
//	  timerEstrategiaBaja.start();
//	  timerEstrategiaBaja2.start();
//	  timerEstrategiaBaja_5.start();
//	  timerEstrategiaBaja2_5.start();
  }
  
  public void initBaseComponents()
  {
	  logger.info("INICIANDO TRADER BOT");
	  initShowPrice();
	  initShowOrderBook();
	  initFibo();
	  initActiveOrder();
	  initCompraVenta();
	  initFiboSoporte();
	  intOrdenesProximas();
	  initEstrategiaBaja();
	  initControlProcesos();

  }
  
  public static void main(String args[]) {
      /* Set the Nimbus look and feel */
      //<editor-fold defaultstate="collapsed" desc=" Look and feel setting code (optional) ">
      /* If Nimbus (introduced in Java SE 6) is not available, stay with the default look and feel.
       * For details see http://download.oracle.com/javase/tutorial/uiswing/lookandfeel/plaf.html 
       */
      try {
          for (javax.swing.UIManager.LookAndFeelInfo info : javax.swing.UIManager.getInstalledLookAndFeels()) {
              if ("Nimbus".equals(info.getName())) {
                  javax.swing.UIManager.setLookAndFeel(info.getClassName());
                  break;
              }
          }
      } catch (ClassNotFoundException ex) {
          java.util.logging.Logger.getLogger(TradeView.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
      } catch (InstantiationException ex) {
          java.util.logging.Logger.getLogger(TradeView.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
      } catch (IllegalAccessException ex) {
          java.util.logging.Logger.getLogger(TradeView.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
      } catch (javax.swing.UnsupportedLookAndFeelException ex) {
          java.util.logging.Logger.getLogger(TradeView.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
      }
      //</editor-fold>

      /* Create and display the form */
      java.awt.EventQueue.invokeLater(new Runnable() {
          public void run() {
              new TradeBaseView().setVisible(true);
          }
      });
     
  }
  private void borrarSoporteActionPerformed(java.awt.event.ActionEvent evt) {                                              
      // TODO add your handling code here:
	  try
	  {
	  	int row = tablaSoportes.getSelectedRow();
	  	logger.info(row);
	  	fibo = fiboDAO.delete(fibo, row);
	  	modelFibo.updateTable(fibo.getSoportesResistencias());
	  }catch(Exception e)
	  {
		  System.err.println("No fue posible borrar el soporte");
	  }
  }
  
  private void agregarSoporteActionPerformed(java.awt.event.ActionEvent evt) {                                               
	  
	  try
	  {
		  Double soporte = new Double(valor_soporte.getText());
		  Punto punto = new Punto(soporte.doubleValue());
		  fibo = fiboDAO.add(fibo, punto);
		  modelFibo.updateTable(fibo.getSoportesResistencias());
	  }
	  catch(Exception e)
	  {
		  System.err.println("Introduzca un valor correcto");
	  }	  	  	  
	  
  }
  private void cancelar_botonActionPerformed(java.awt.event.ActionEvent evt)
  {
	 seriviceCancelar = new DataService<Order>();
	 try {
		seriviceCancelar.getData(Constantes.CANCEL_ORDER_TYPE, Order.class, cancel_clientOrderId);
	} catch (BotException e) {
		// TODO Auto-generated catch block
		e.printStackTrace();
	}
	 catch (Exception e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}
  }
  
  private void comprar_botonActionPerformed(java.awt.event.ActionEvent evt) {                                              
      // TODO add your handling code here:
	  if (!stopLimitCompraCheck.isSelected())
		  trade(Constantes.BUY, compra_cantidad.getText() , compra_precio.getText() );
  }                                             

  private void vender_botonActionPerformed(java.awt.event.ActionEvent evt) {                                             
      // TODO add your handling code here:
	  if (!stopLimitVentaCheck.isSelected())
		  trade(Constantes.SELL, venta_cantidad.getText() , venta_precio.getText() );
  }
  
  private void calcular_retrocesoActionPerformed(java.awt.event.ActionEvent evt) {                                             
      // TODO add your handling code here:
	  fibo = fiboModel.calcular_retroceso(fibo, Double.parseDouble(maximoFibo.getText()), Double.parseDouble(minimoFibo.getText()));
	  fiboDAO.setFibo(fibo);
	  modelFibo.updateTable(fibo.getSoportesResistencias());
  }  
  
  public void trade(String side, String quantity, String price)
  {	  
	  Order order = new Order();
	  order.setSide(side);
      order.setSymbol("btcusd");
      order.setQuantity(quantity);
      order.setPrice(price);
      order.setType("limit");
      order.setTimeInForce("GTC");
      ObjectMapper mapper = new ObjectMapper();
      String jsonInString;
	try {
		jsonInString = mapper.writeValueAsString(order);
		logger.info(jsonInString);
	    Order response = orderBuySellService.getData(Constantes.ORDER_BUY_SELL_TYPE, Order.class, jsonInString);
	} catch (JsonGenerationException e) {
		// TODO Auto-generated catch block
		e.printStackTrace();
	} catch (JsonMappingException e) {
		// TODO Auto-generated catch block
		e.printStackTrace();
	} catch (IOException e) {
		// TODO Auto-generated catch block
		e.printStackTrace();
	} catch (BotException e) {
		// TODO Auto-generated catch block
		e.printStackTrace();
	}     
	catch (Exception e1) {
		// TODO Auto-generated catch block
		e1.printStackTrace();
	}
      
  }
  
  private void controlProcesoActionPerformed(java.awt.event.ActionEvent evt) {                                              
      // TODO add your handling code here:
	  try {		
		 for(int i = 0 ; i < checks.length ; i++)
		 {
			 if(evt.getSource() == checks[i])
			 {
				 if(checks[i].isSelected())
				 {
					 if(!timers[i].isRunning())
						 timers[i].start();
				 }else
				 {
					 if(timers[i].isRunning())
						 timers[i].stop();
				 }
				 
				 break;
			 }
			 
		 }
	  } catch (Exception e) {
			// TODO: handle exception
		  logger.error("Error", e);
		}
  }
  
  private void boton_iniciar_procesoActionPerformed(java.awt.event.ActionEvent evt) {                                              
      // TODO add your handling code here:
	  controlProceso(true);
  }
  
  private void boton_detener_procesoActionPerformed(java.awt.event.ActionEvent evt) {                                              
      // TODO add your handling code here:
	  controlProceso(false);
  }
  
  public void controlProceso(boolean control)
  {
	  for(int i = 0 ; i < checks.length ; i++)
	  {
		  if(checks[i] != null)
			  if(checks[i].isSelected())
			  {
				  if(control)
				  {
					  if(!timers[i].isRunning())
						  timers[i].start();
				  }				  
			  }
			  else
			  {
				  if(!control)
				  {
					  if(timers[i].isRunning())
						  timers[i].stop();
				  }
			  }
	  }
  }
}
