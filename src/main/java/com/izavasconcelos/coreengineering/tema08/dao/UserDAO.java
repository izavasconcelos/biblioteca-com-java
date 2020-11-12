package com.izavasconcelos.coreengineering.tema08.dao;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.reflect.TypeToken;
import com.izavasconcelos.coreengineering.tema08.model.User;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.lang.reflect.Type;
import java.util.Collections;
import java.util.List;

public class UserDAO {
    private static final String FILE_DATABASE = "src/main/java/com/izavasconcelos/coreengineering/tema08/database/users.json";
    GsonBuilder builder = new GsonBuilder().setPrettyPrinting();
    Gson gson = builder.create();

    public boolean saveUser(List<User> user) {
        String userToJson = gson.toJson(user);
        try (FileWriter writeFile = new FileWriter(FILE_DATABASE)){
            writeFile.write(userToJson);
            return true;
        } catch (IOException e) {
            e.printStackTrace();
        }
        return false;
    }
    public List<User> listAllUsers() {
        Type userType = new TypeToken<List<User>>(){}.getType();

        try(FileReader readerFile = new FileReader(FILE_DATABASE)) {
            BufferedReader br = new BufferedReader(readerFile);
            return gson.fromJson(br, userType);

        } catch (IOException e) {
            e.printStackTrace();
        }
        return Collections.emptyList();
    }
}
