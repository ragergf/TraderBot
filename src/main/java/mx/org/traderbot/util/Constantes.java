package mx.org.traderbot.util;

public class Constantes {
	public static final int ORDERBOOK_TYPE = 0;
	public static final int TRADE_TYPE = 1;
	public static final int ACTIVEORDERS_SYMBOL_TYPE = 2;
	public static final int CANCEL_ORDER_TYPE = 3;
	public static final int ORDER_BUY_SELL_TYPE = 4;
	public static final int CANDLE_TYPE = 5;
	public static final int BALANCE_TYPE = 6;
	public static final int ACTIVE_ONE_ORDER_SYMBOL_TYPE = 7;
		
	public static final int GET = 101;
	public static final int POST = 102;
	public static final int PUT = 103;
	public static final int DELETE = 104;
	
	public static final int ZONA_EMA13 = 13;
	public static final int ZONA_EMA34 = 34;
	public static final int TIME_OUT_ = 34;
	
	public static final double DIFERENCIA_VENTA_FIT = 10;
	public static final double DIFERENCIA_COMPRA = 10;
	public static final double DIFERENCIA_VENTA = 10;
	public static final double DIFERENCIA_COMPRA_FIT = 10;
	
	public static final String ORDERBOOK_URL = "https://api.hitbtc.com/api/2/public/orderbook/BTCUSD"; 	
	public static final String TRADE_URL = "https://api.hitbtc.com/api/2/public/trades/BTCUSD?sort=DESC&limit=10";
	public static final String ORDER_URL = "https://api.hitbtc.com/api/2/order";
	public static final String CANDLE_URL = "https://api.hitbtc.com/api/2/public/candles";
	public static final String BALANCE_URL = "https://api.hitbtc.com/api/2/trading/balance";
	
	public static final String SIZE_CANDLE_UPDATE= "5";
	
	
	public static final String ARCHIVO_FIBO = "D:\\fibo.json";
	
	public static final double [] RETROCESO_FIBONACCI ={0, 0.236, 0.382, 0.5, 0.618, 0.764, 1};
	
	public static final String BUY = "buy";
	public static final String SELL = "sell";
	
	public static final String SYMBOl_BTCLSK = "LSKBTC";
	
	public static final String SYMBOl_BTCUSD = "BTCUSD";
	public static final String SYMBOl_XRPUSD = "XRPUSDT";
	public static final String LIMIT_TYPE = "limit"; 
	public static final String TIME_IN_FORCE = "GTC";
	
	public static final String QUANTITY = "0.01";
	public static final double RANGO_DE_ACTIVACION = 30;
	public static final double RANGO_DE_VACIO = 0;
	
	public static final double UMBRAL_LOSS = -10;
	public static final double UMBRAL_FIT = 20;
	public static final int EVENTOS_X_SUBIDA = 1;
	public static final int EVENTOS_X_BAJADA = 2;
}
