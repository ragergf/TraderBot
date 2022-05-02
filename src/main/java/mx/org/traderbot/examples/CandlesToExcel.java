package mx.org.traderbot.examples;

import org.apache.poi.hssf.usermodel.HSSFCell;
import org.apache.poi.hssf.usermodel.HSSFRow;
import org.apache.poi.hssf.usermodel.HSSFSheet;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.CellStyle;
import org.apache.poi.ss.usermodel.Font;
import org.apache.poi.ss.usermodel.IndexedColors;

import mx.org.traderbot.chart.candles.model.CandleModel;
import mx.org.traderbot.chart.candles.vo.Candle;
import mx.org.traderbot.chart.candles.vo.PSAR;
import mx.org.traderbot.chart.candles.vo.PSAR_V2;
import mx.org.traderbot.util.Constantes;

import java.io.FileOutputStream;
import java.math.BigDecimal;
import java.util.List;

public class CandlesToExcel {
	public static void main(String[] args) throws Exception {
		HSSFWorkbook workbook = new HSSFWorkbook();
		HSSFSheet sheet = workbook.createSheet();
		workbook.setSheetName(0, "Hoja excel");
		String[] headers = new String[] { "Open", "Close", "High", "Low", "SAR", "SAR+1 ", "EP", "AF", "EP - SAR", "Tendencia" };
		
		Object data[][] = null;
		CandleModel candleModel = new CandleModel();
		Candle [] candles = candleModel.getCandles("H1", "150", Constantes.SYMBOl_BTCUSD);
		List<PSAR_V2> listPSAR = candleModel.getParabolicSAR_V2(candles);
		
		CellStyle headerStyle = workbook.createCellStyle();
		Font font = workbook.createFont();
		font.setBoldweight(Font.BOLDWEIGHT_BOLD);
		headerStyle.setFont(font);
		CellStyle style = workbook.createCellStyle();
		style.setFillForegroundColor(IndexedColors.LIGHT_YELLOW.getIndex());
		style.setFillPattern(CellStyle.SOLID_FOREGROUND);
		HSSFRow headerRow = sheet.createRow(0);
		for (int i = 0; i < headers.length; ++i) {
			String header = headers[i];
			HSSFCell cell = headerRow.createCell(i);
			cell.setCellStyle(headerStyle);
			cell.setCellValue(header);
		}
		for (int i = 0; i < candles.length; ++i) {
			HSSFRow dataRow = sheet.createRow(i + 1);
			double open = candles[i].getOpen();
			double close = candles[i].getClose();
			double max = candles[i].getMax();
			double min = candles[i].getMin();
			
			double sar = listPSAR.get(i).getSar();
			double sar_1 = listPSAR.get(i).getSar_man();
			double ep = listPSAR.get(i).getEp();
			double af = listPSAR.get(i).getAf();
			String tendencia = (listPSAR.get(i).isFalling())?"Falling":"Rising";
						
			dataRow.createCell(0).setCellValue(open);
			dataRow.createCell(1).setCellValue(close);
			dataRow.createCell(2).setCellValue(max);
			dataRow.createCell(3).setCellValue(min);
			
			dataRow.createCell(4).setCellValue(sar);
			dataRow.createCell(5).setCellValue(sar_1);
			dataRow.createCell(6).setCellValue(ep);
			dataRow.createCell(7).setCellValue(af);
			dataRow.createCell(8).setCellValue(ep-sar);
			dataRow.createCell(9).setCellValue(tendencia);
			
		}
		HSSFRow dataRow = sheet.createRow(1 + candles.length);
		
		FileOutputStream file = new FileOutputStream("D:\\rager\\candles.xls");
		workbook.write(file);
		file.close();
	}
}