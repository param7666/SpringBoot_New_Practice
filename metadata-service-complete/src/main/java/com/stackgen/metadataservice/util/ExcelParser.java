package com.stackgen.metadataservice.util;

import com.stackgen.metadataservice.dto.*;
import org.apache.poi.ss.usermodel.*;
import org.springframework.web.multipart.MultipartFile;
import java.io.*; import java.util.*;

public final class ExcelParser {
 private static final List<String> REQUIRED=List.of("service","table","column","data type","key","constraints","references","description");
 private ExcelParser(){}
 public static ParsedWorkbook parse(MultipartFile file) throws IOException {
  if(file==null||file.isEmpty()) throw new IllegalArgumentException("Data Dictionary file is empty");
  try(InputStream in=file.getInputStream(); Workbook wb=WorkbookFactory.create(in)){
   Sheet sheet=wb.getSheetAt(0); DataFormatter fmt=new DataFormatter();
   int header=-1; Map<String,Integer> idx=new HashMap<>();
   for(int r=0;r<=Math.min(sheet.getLastRowNum(),30);r++){ Row row=sheet.getRow(r); if(row==null) continue; Map<String,Integer> tmp=new HashMap<>(); for(Cell c:row){ String v=fmt.formatCellValue(c).trim().toLowerCase(); if(!v.isBlank()) tmp.put(v,c.getColumnIndex()); } if(tmp.keySet().containsAll(REQUIRED)){header=r;idx=tmp;break;} }
   if(header<0) throw new IllegalArgumentException("Could not find required headers: "+REQUIRED);
   List<Map<String,String>> rows=new ArrayList<>();
   for(int r=header+1;r<=sheet.getLastRowNum();r++){ Row row=sheet.getRow(r); if(row==null) continue; String table=value(row,idx.get("table"),fmt); String col=value(row,idx.get("column"),fmt); if(table.isBlank()&&col.isBlank()) continue; Map<String,String> m=new LinkedHashMap<>(); for(String h:REQUIRED)m.put(h,value(row,idx.get(h),fmt)); rows.add(m); }
   return new ParsedWorkbook(sheet.getSheetName(),REQUIRED,rows);
  }
 }
 private static String value(Row r,Integer i,DataFormatter f){return i==null?"":f.formatCellValue(r.getCell(i,Row.MissingCellPolicy.RETURN_BLANK_AS_NULL)).trim();}
 public record ParsedWorkbook(String sheetName,List<String> headers,List<Map<String,String>> rows){}
}
