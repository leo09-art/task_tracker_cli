package com.example.task_tracker_cli.service;
import com.example.task_tracker_cli.models.Task;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializationFeature;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;
import org.springframework.stereotype.Service;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

@Service
public class TaskService {
    public final ObjectMapper mapper;
    File file =new File("tasks.json");

    public TaskService() {
        mapper = new ObjectMapper();
        mapper.registerModule(new JavaTimeModule());
        mapper.disable(SerializationFeature.WRITE_DATES_AS_TIMESTAMPS);
    }

    private List<Task> tasks;

    public void saveTask(List<Task> tasks) {
        try{
            mapper.writeValue(file, tasks);

        }catch (IOException e){
            e.printStackTrace();
        }
    }

    public List<Task> loadTasks(){
        try{
            if(file.exists()){
                return new ArrayList<>( Arrays.asList(mapper.readValue(file, Task[].class)));
            }
        }catch (IOException e){
            e.printStackTrace();
        }
        return new ArrayList<>();
    }

}
