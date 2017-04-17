package ecc.cords;


public class ExerciseFour_Main {

	public static void main(String[] args){
		
		FileHandler.setFileName(args.length > 0? args[0] : "Default.txt");
		
		Table table = new Table();
		boolean isRunning = true;
		
		while(isRunning){
			System.out.println("\n1. PRINT        5. EDIT VALUE");
			System.out.println("2. SEARCH       6. SORT ASCENDING");
			System.out.println("3. ADD CELL     7. SORT DESCENDING");
			System.out.println("4. EDIT KEY     8. EXIT");
			
			String choice = Helper.askString("What do you want to do? (Enter choice number): ");
			
			try{
				switch(choice){
					case "1":
						table.display();
						break;
					case "2":
						table.search(Helper.askString("Enter pattern to be searched: "));
						break;
					case "3":
						table.addCell();
						table.saveToFile();
						break;
					case "4":
						table.editKey(table.inputNewKey());
						break;
					case "5":
						table.editValue(table.inputNewValue());
						break;
					case "6":
						table.sort(true);
						break;
					case "7":
						table.sort(false);
						break;
					case "8":
						System.exit(0);
					default:
						System.out.println("Invalid choice!");
				}
			}catch(IndexOutOfBoundsException | InputException exception){
				System.out.println(exception.getMessage());
			}

		}
	}
	
}
