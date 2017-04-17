package ecc.cords;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;
import static java.util.stream.Collectors.*;

public class Row{

	private List<Cell> cells = new ArrayList<>();
	
	public void addCell(String key, String value){
		cells.add(new Cell(key,value));
	}
		
	public void display(){
		for(int index = 0; index < cells.size(); index++){
			System.out.print("[" + cells.get(index).getKey() + " , " + cells.get(index).getValue() + "]   " );
		}
	}
	
	public void sort(boolean asc){
		Comparator<Cell> cellComparator = (cell1, cell2) -> {
											String cell1Concat = (cell1.getKey() + cell1.getValue()).toLowerCase();
											String cell2Concat = (cell2.getKey() + cell2.getValue()).toLowerCase();
											
											if(cell1Concat.equals(cell2Concat)){
									    		return cell1.getKey().compareTo(cell2.getKey());
											}
											
											return cell1Concat.compareTo(cell2Concat);
										};

		cells = cells.stream()
					 .sorted(asc? cellComparator : cellComparator.reversed())
					 .collect(toList());						  
	}
	
	public void setCellKey(int index, String key){
		cells.get(index).setKey(key);
	}
	
	public String getCellValue(int index){
		return cells.get(index).getValue();
	}
	
	public void setCellValue(int index, String value){
		cells.get(index).setValue(value);
	}
	
	public String getCellKey(int index){
		return cells.get(index).getKey();
	}
	
	public int getRowSize(){
		return cells.size();
	}

}