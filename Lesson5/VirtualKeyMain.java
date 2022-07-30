package Lesson5;

import java.util.*;
import java.io.*;
import java.nio.file.FileSystems;
import java.nio.file.Path;

public class VirtualKeyMain {
	public static void main(String[] args) {

		WelcomeScreen welcome = new WelcomeScreen();
		welcome.introWS();
		welcome.GetUserInput();

	}
}

class Directory {

	public static final String name = "src/main/directory/";
	private ArrayList<File> files = new ArrayList<File>();
	Path path = FileSystems.getDefault().getPath(name).toAbsolutePath();
	File Dfiles = path.toFile();

	public String getName() {
		return name;
	}

	public void fillFiles() {
		File[] directoryFiles = Dfiles.listFiles();

		files.clear();
		for (int i = 0; i < directoryFiles.length; i++) {
			if (directoryFiles[i].isFile()) {
				files.add(directoryFiles[i]);
			}
		}
		Collections.sort(files);// sorting
	}

	public ArrayList<File> getFiles() {
		fillFiles();
		return files;
	}

}

interface Screen {
	public void Show();
	public void NavigateOption(int option);
	public void GetUserInput();
}


class DirectoryService {
	private static Directory fileDirectory = new Directory();
	public static void PrintFiles() {
		fileDirectory.fillFiles();
		for (File file : DirectoryService.getFileDirectory().getFiles()) {
			System.out.println(file.getName());
		}
	}

	public static Directory getFileDirectory() {
		return fileDirectory;
	}

	public static void setFileDirectory(Directory fileDirectory) {
		DirectoryService.fileDirectory = fileDirectory;
	}
}



class FileOptionsMenu implements Screen {
	private Directory dir = new Directory();
	private ArrayList<String> options = new ArrayList<>();

	public FileOptionsMenu() {
		options.add("  1> Add File");
		options.add("  2> Delete File");
		options.add("  3> Search File");
		options.add("  4> Return Main Menu");
	}

	@Override
	public void Show() {
		System.out.println(" <File Options Menu>:");
		for (String s : options) {
			System.out.println(s);
		}
	}

	public void GetUserInput() {
		int selectedOption;
		while ((selectedOption = this.getOption()) != 4) {
			this.NavigateOption(selectedOption);
		}
		//if 4 is pressed then exit out of function to previous switch case in main menu
	}

	@Override
	public void NavigateOption(int option) {
		switch (option) {

		case 1: // Add File
			this.AddFile();
			this.Show();
			break;
		case 2: // Delete File
			this.DeleteFile();
			this.Show();
			break;
		case 3: // Search File
			this.SearchFile();
			this.Show();
			break;
		default:
			System.out.println(">Invalid Option");
			break;

		}

	}
	
	public void AddFile() {
		System.out.println(">Enter Filename:");
		String fileName = this.getInputString();
		System.out.println("creating file-> filename: " + fileName);
		try {
			Path path = FileSystems.getDefault().getPath(Directory.name + fileName).toAbsolutePath();
			File file = new File(dir.getName() + fileName);

			if (file.createNewFile()) {
				System.out.println("File created: " + file.getName());
				dir.getFiles().add(file);
			} 
			else {
				System.out.println("File Exits Already");
			}
		} 
		catch (IOException e) {
			System.out.println(e);
		}
	}

	public void DeleteFile() {
		System.out.println("Please Enter the Filename:");
		String fileName = this.getInputString();
		System.out.println("deleting filename: " + fileName);
		Path path = FileSystems.getDefault().getPath(Directory.name + fileName).toAbsolutePath();
		File file = path.toFile();
		if (file.delete()) {
			System.out.println("Deleted filename: " + file.getName());
			dir.getFiles().remove(file);//removing from list
		} 
		else {
			System.out.println("error deleting:" + fileName );
		}
	}

	public void SearchFile() {
		Boolean found = false;
		System.out.println("Enter Filename:");
		String fileName = this.getInputString();
		System.out.println("searching filename: " + fileName);
		ArrayList<File> files = dir.getFiles();
		for (int i = 0; i < files.size(); i++) {
			if (files.get(i).getName().equals(fileName)) {
				System.out.println(" file Found " + fileName);
				found = true;
			}
		}
		if (found == false) {
			System.out.println("File not found");
		}
	}

	private String getInputString() {
		Scanner in = new Scanner(System.in);
		return (in.nextLine());
	}

	private int getOption() {
		Scanner in = new Scanner(System.in);
		int returnOption = 0;
		try {
			returnOption = in.nextInt();
		} 
		catch (InputMismatchException ex) {
			System.out.println("Invalid input");
		}
		return returnOption;
	}
}

class ScreenService {
	public static WelcomeScreen WelcomeScreen = new WelcomeScreen();
	public static FileOptionsMenu FileOptionsMenu = new FileOptionsMenu();
	public static Screen CurrentScreen = WelcomeScreen;
	public static Screen getCurrentScreen() {
		return CurrentScreen;
	}

	public static void setCurrentScreen(Screen currentScreen) {
		CurrentScreen = currentScreen;
	}

}

class WelcomeScreen implements Screen {

	private String welcomeText = " Phase 1 - Virtual Key Repoistory ";
	private String developerText = " Developer: Aishwarya Rajagopal ";
	private ArrayList<String> options = new ArrayList<>();
	public WelcomeScreen() {
		options.add("1. Show Files");
		options.add("2. Show File Options Menu");
		options.add("3. Quit");
	}

	public void introWS() {
		System.out.println(welcomeText);
		System.out.println(developerText);
		System.out.println("\n");
		Show();
	}

	@Override
	public void Show() {
		System.out.println("Main Menu");
		for (String s : options) {
			System.out.println(s);
		}
	}

	public void GetUserInput() {
		int selectedOption = 0;
		while ((selectedOption = this.getOption()) != 3) {
			this.NavigateOption(selectedOption);
		}
	}

	@Override
	public void NavigateOption(int option) {
		switch (option) {
		case 1: // Show Files in Directory
			this.ShowFiles();
			this.Show();
			break;
		case 2: // Show File Options menu
			ScreenService.setCurrentScreen(ScreenService.FileOptionsMenu);
			ScreenService.getCurrentScreen().Show();
			ScreenService.getCurrentScreen().GetUserInput();
			this.Show();
			break;

		default:
			System.out.println("Invalid Option");
			break;
		}
	}

	public void ShowFiles() {
		System.out.println("List of Files: ");
		DirectoryService.PrintFiles();
	}

	private int getOption() {
		Scanner in = new Scanner(System.in);
		int returnOption = 0;
		try {
			returnOption = in.nextInt();
		} 
		catch (InputMismatchException ex) {
			ex.printStackTrace();
		}
		return returnOption;
	}
}