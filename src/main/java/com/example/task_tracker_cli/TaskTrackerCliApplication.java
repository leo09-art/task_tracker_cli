package com.example.task_tracker_cli;

import com.example.task_tracker_cli.enums.Status;
import com.example.task_tracker_cli.models.Task;
import com.example.task_tracker_cli.repository.TaskRepository;
import com.example.task_tracker_cli.service.TaskService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

@SpringBootApplication
public class TaskTrackerCliApplication implements CommandLineRunner {

    public static void main(String[] args) {
        SpringApplication.run(TaskTrackerCliApplication.class, args);
    }
    @Autowired TaskRepository taskRepository;
    @Autowired TaskService taskService;

    @Override
    public void run(String... args){
        List<Task> tasks = new ArrayList<>();
        System.out.println("Welcome to the Task Tracker CLI!");
        Scanner input = new Scanner(System.in);

        while (true){
            System.out.print("> ");
            String command = input.nextLine().trim();

            if (command.isEmpty()) {
                continue;
            }

            if ("exit".equalsIgnoreCase(command)) {
                break;
            }

            String[] parts = command.split(" ", 2);
            if (parts.length < 2) {
                System.out.println("Commande invalide. Format attendu: task-cli <action> ...");
                continue;
            }

            String action = parts[1].split(" ")[0].toLowerCase();
            String actionComplete = parts[1].toLowerCase();
            try {
                switch (action) {
                    case "add":
                        String desc = parts[1].split("\"")[1];
                        Task task = new Task();
                        task.setDescription(desc);
                        task.setStatus(Status.todo.toString());
                        task.setCreated_at(LocalDateTime.now());
                        task.setUpdated_at(LocalDateTime.now());
                        task = taskRepository.save(task);
                        tasks.add(task);
                        taskService.saveTask(tasks);
                        System.out.println("Task ajoutée: " + task);
                        break;

                    case "update":
                        Long id = Long.parseLong(parts[1].split(" ")[1]);
                        String newDesc = parts[1].split("\"")[1];
                        Task taskToUpdate = tasks.stream()
                                .filter(t -> t.getId().equals(id))
                                .findFirst()
                                .orElse(null);
                        if (taskToUpdate != null) {
                            taskToUpdate.setUpdated_at(LocalDateTime.now());
                            taskToUpdate.setDescription(newDesc);
                            taskService.saveTask(tasks);
                            System.out.println("Task mise à jour: " + taskToUpdate);
                        } else {
                            System.out.println("Task avec id " + id + " introuvable");
                        }
                        break;

                    case "delete":
                        id = Long.parseLong(parts[1].split(" ")[1]);
                        Task taskToDelete = tasks.stream()
                                .filter(t -> t.getId().equals(id))
                                .findFirst()
                                .orElse(null);
                        if (taskToDelete != null) {
                            tasks.remove(taskToDelete);
                            taskService.saveTask(tasks);
                            System.out.println("Task supprimée: " + id);
                        } else {
                            System.out.println("Task avec id " + id + " introuvable");
                        }
                        break;

                    case "mark-in-progress":
                        id = Long.parseLong(parts[1].split(" ")[1]);
                        Task inProgress = tasks.stream()
                                .filter(t -> t.getId().equals(id))
                                .findFirst()
                                .orElse(null);
                        if (inProgress != null) {
                            inProgress.setUpdated_at(LocalDateTime.now());
                            inProgress.setStatus(Status.in_progress.toString());
                            taskService.saveTask(tasks);
                            System.out.println("Task en cours: " + id);
                        } else {
                            System.out.println("Task avec id " + id + " introuvable");
                        }
                        break;

                    case "mark-done":
                        id = Long.parseLong(parts[1].split(" ")[1]);
                        Task done = tasks.stream()
                                .filter(t -> t.getId().equals(id))
                                .findFirst()
                                .orElse(null);
                        if (done != null) {
                            done.setUpdated_at(LocalDateTime.now());
                            done.setStatus(Status.done.toString());
                            taskService.saveTask(tasks);
                            System.out.println("Task terminée: " + id);
                        } else {
                            System.out.println("Task avec id " + id + " introuvable");
                        }
                        break;
                    case "list":
                        if(tasks.isEmpty()){
                            System.out.println("No tasks found!");
                        }else if(actionComplete.equalsIgnoreCase("list")){
                            System.out.println("Tasks found");
                            tasks.forEach(t -> System.out.println(t.toString()));

                        }
                        else if(actionComplete.equalsIgnoreCase("list todo")){
                                    System.out.println("Tasks found todo");
                                    tasks.stream()
                                            .filter(t-> t.getStatus().equals("todo"))
                                            .forEach(t -> System.out.println(t.toString()));

                        }else if(actionComplete.equals("list in-progress")){
                                    System.out.println("Tasks found progress");
                                    tasks.stream()
                                            .filter(t-> t.getStatus().contains("in_progress"))
                                            .forEach(t -> System.out.println(t.toString()));

                        }else if (actionComplete.equals("list done")){
                                    System.out.println("Tasks found done" );
                                    tasks.stream()
                                            .filter(t-> t.getStatus().equals("done"))
                                            .forEach(t -> System.out.println(t.toString()));
                                }
                        break;
                    default:
                        System.out.println("Commande inconnue. Actions possibles: add, update, delete, mark-in-progress, mark-done, exit");
                }
            } catch (Exception e) {
                System.out.println("Erreur: format invalide. Vérifiez votre commande.");
            }
        }
        System.out.println("Program ended!");
    }


}
