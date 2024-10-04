package pl.workshop1;

import org.apache.commons.lang3.ArrayUtils;
import org.apache.commons.lang3.StringUtils;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileWriter;
import java.io.IOException;
import java.sql.SQLOutput;
import java.time.LocalDateTime;
import java.util.Arrays;
import java.util.Scanner;

public class Main {
    static String[][] tasks = new String[100][3];
    static int nrTasks = 0;

    public static void main(String[] args) {
        String fileName = "./tasks.csv";
        try{
            retrieveData(fileName);
        }catch (FileNotFoundException e){
            System.out.println("Nu s-a gasit fisierul " +  fileName);
        }

        String[] commands = {"add", "remove", "list", "exit"};
        showOptions(commands);

        Scanner input = new Scanner(System.in);
        String command;

        do{
            System.out.print("Enter command: ");
            command = input.nextLine().trim();
            System.out.println("Command " + command + ": ");
            switch (command){
                case "exit":
                    System.out.println(ConsoleColors.GREEN_BOLD_BRIGHT + "Bye!");
                    break;
                case "add":
                    String[] taskToBeAdded = new String[3];
                    System.out.println(ConsoleColors.PURPLE_BACKGROUND + "Task name: ");
                    taskToBeAdded[0] = input.nextLine();
                    taskToBeAdded[1] = LocalDateTime.now().toString();
                    System.out.println("Resolved?(True/False): ");
                    taskToBeAdded[2] = input.nextLine();
                    add(taskToBeAdded);
                    writeData(fileName);
                    System.out.print(ConsoleColors.RESET);
                    listTasks();
                    break;
                case "list":
                    listTasks();
                    break;
                case "remove":
                    System.out.println("Give the number of the task you want to delete: ");
                    if(input.hasNextInt() == false){
                        System.out.println("Trebuie sa dati o valoare numerica int intre 0 si " + nrTasks);
                        break;
                    }else{
                        int taskNumber = input.nextInt();
                        if(taskNumber >= 0 && taskNumber < nrTasks){
                            remove(taskNumber);
                            writeData(fileName);
                            break;
                        }else {
                            System.out.println("Trebuie sa dati o valoare numerica int intre 0 si " + nrTasks);
                            break;
                        }
                    }

//                    remove(input.nextInt());
//                    writeData(fileName);
//                    break;
                default:
                    System.out.println(ConsoleColors.RED_BOLD + "The entered command does not exist." + ConsoleColors.RESET);
            }


        }while(!command.equals("exit"));
        System.out.println(nrTasks + " , " + tasks.length);


    }

    public static void showOptions(String[] options) {
        System.out.println(ConsoleColors.BLUE + "Comenzi:");
        for (String option : options) {
            System.out.println(option);
        }
        System.out.print(ConsoleColors.RESET);
    }

    public static void retrieveData(String fileName) throws FileNotFoundException {
        File file = new File(fileName);
        Scanner scan  =new Scanner(file);
        int i = 0;
        while(scan.hasNextLine()){
            tasks[i] = scan.nextLine().split(", ");
            i++;
        }
        nrTasks = i;
    }

    public static void listTasks(){
        for(int i = 0; i < nrTasks; i++){
            System.out.print(String.join(", ", tasks[i]));
            System.out.println();
        }
    }

    public static void add(String[] task){
        if(nrTasks <= tasks.length){
            tasks[nrTasks] = task;
            nrTasks ++;
        }else {
            tasks = Arrays.copyOf(tasks, tasks.length * 2);
            add(task);
        }
        System.out.println("Task number " + nrTasks + " has been added!");
    }

    public static void writeData(String fileName){
        try(FileWriter fileWriter = new FileWriter(fileName)){
            for(int i = 0; i < nrTasks; i++){
                fileWriter.write(String.join(", ", tasks[i]) + "\n");
            }
        }catch (IOException e){
            System.out.println("exceptie file writer");
        }
    }

    public static void remove(int task){
//        // Creăm un nou array cu o dimensiune mai mică
//        String[][] newTasks = new String[tasks.length - 1][];

//        // Copiem toate elementele, exceptând rândul pe care vrem să-l eliminăm
//        for (int i = 0, j = 0; i < tasks.length; i++) {
//            if (i != task) {
//                tasks[j++] = tasks[i];
//            }
//        }
        tasks[task] = null;
        nrTasks--;
    }
}
