package ecc.cords;
 
import java.util.ArrayList;
import java.util.List;
import java.util.HashSet;
import java.util.Set;

public class Table {
	
	private int col = 0, row = 0;
	private List<Row> table = new ArrayList<>();
	private Set<String> keys = new HashSet<>();
	
	public Table(){
		create();
	}

	public void addCell() throws IndexOutOfBoundsException, InputException{
		row = Helper.askNumericInput("y");
		Helper.isValidIndex(row, table.size() + 1, "y");

		if(row == table.size())
			addRow();

		addCell(inputKey(), Helper.askString("Enter New Value: "), true);

		System.out.println("Sucessfully Added Cell!");
	}
	
	public void addCell(String key, String value, boolean save){
		keys.add(key);
		table.get(row).addCell(key, value);
		if(save){
			saveToFile();
		}
	}
	
	private void addRow(){
		table.add(new Row());
	}
	
	public void create(){
		String txt = "";
		int duplicates = 0;

		txt = FileHandler.readFile();

		String[] lines = txt.split("\n");
		String delimiter = lines[0];
		
		for(int lineIndex = 1; lineIndex < lines.length; lineIndex++){
			String[] cellArray = lines[lineIndex].split(delimiter.trim());
			if(cellArray.length % 2 != 0){
				System.out.println(txt + cellArray[0]);
				for(String s:cellArray)
					System.out.println(s);
				System.out.println("Please check the file used and try again. \nExiting Program...");
				System.exit(0);
			}
			addRow();
			row = lineIndex - 1;
			col = 0;
			for(int index = 0; index < cellArray.length; index +=2, col++){
				if(keys.contains(cellArray[index].trim())){
					duplicates++;
					continue;
				}
				addCell(cellArray[index].trim(), cellArray[index+1].trim(), false);
			}
		}
		if(duplicates > 0){
			System.out.println("Duplicate keys removed from file: " + duplicates);
		}
		display();
	}
	
	public void display(){
		System.out.println("");
		table.forEach(row -> {
						row.display();
						System.out.println("");
					 });
	}
	
	public void editKey(String key){
		keys.remove(table.get(row).getCellKey(col));
		table.get(row).setCellKey(col, key);
		System.out.println(row + "," + col + " key changed into " + table.get(row).getCellKey(col));
		saveToFile();
	}
	
	public void editValue(String value){	
		table.get(row).setCellValue(col, value);
		System.out.println(row + "," + col + " value changed into " + table.get(row).getCellValue(col));
		saveToFile();
	}


	public String inputNewKey() throws IndexOutOfBoundsException, InputException{
		getTargetCell();

		return inputKey();
	}

	public String inputNewValue() throws IndexOutOfBoundsException, InputException{
		getTargetCell();
		
		return Helper.askString("Enter New Value: ");
	}
	
	public void search(String pattern){
		Row row;
		String key, value, keyMsg, valueMsg;
		int keyCount, valueCount;
		
		System.out.println();
		for(int rowIndex = 0; rowIndex < table.size(); rowIndex++){
			row = table.get(rowIndex);
			for(int colIndex = 0; colIndex < table.get(rowIndex).getRowSize(); colIndex++){
				key = row.getCellKey(colIndex);
				value = row.getCellValue(colIndex);
				keyCount = Helper.countPatternOccurence(key, pattern);
				valueCount = Helper.countPatternOccurence(value, pattern);
				keyMsg =  keyCount + (keyCount > 1? " instances" : " instance") + " in Key and ";
				valueMsg =  valueCount + (valueCount > 1? " instances" : " instance") + " in Value";
				
				if(keyCount + valueCount > 0)
					System.out.println(rowIndex + ", " + colIndex + " with " + keyMsg + valueMsg);
			}
		}
	}
	
	public void sort(boolean asc){
		table.forEach(row -> row.sort(asc));
		display();
		saveToFile();
	}

	protected void saveToFile(){
		StringBuilder data = new StringBuilder();
		String delimiter = Helper.generateRandomString(5).trim();
		
		data.append(delimiter + System.lineSeparator());
		
		for(int y = 0; y < table.size(); y++){
			Row tableRow = table.get(y);
			for(int x = 0; x < tableRow.getRowSize(); x++){
				data.append((x!=0? delimiter : "") + tableRow.getCellKey(x) + delimiter + tableRow.getCellValue(x));
			}
			data.append(System.lineSeparator());
		}
		
		FileHandler.writeToFile(data.toString());
	}
	
	private void getTargetCell() throws IndexOutOfBoundsException, InputException{
		row = Helper.askNumericInput("y");
		Helper.isValidIndex(row, table.size(), "y");
			
		col = Helper.askNumericInput("x");
		Helper.isValidIndex(col, table.get(row).getRowSize(), "x");
		
		System.out.println("(" + row + "," + col + ")");
	}

	private String inputKey() throws InputException{
		String key = Helper.askString("Enter New Key: ");
		if(!keys.add(key)){
			throw new InputException("Error: Key exist in Table!");
		}
		return key;
	}
}
