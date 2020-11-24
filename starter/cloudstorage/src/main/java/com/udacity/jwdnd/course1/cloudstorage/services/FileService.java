package com.udacity.jwdnd.course1.cloudstorage.services;

import com.udacity.jwdnd.course1.cloudstorage.mapper.FileMapper;
import com.udacity.jwdnd.course1.cloudstorage.mapper.UserMapper;
import com.udacity.jwdnd.course1.cloudstorage.model.File;
import com.udacity.jwdnd.course1.cloudstorage.model.User;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.security.SecureRandom;
import java.util.Base64;
import java.util.List;

@Service
public class FileService {

    private final FileMapper fileMapper;
    private final UserService userService;

    public FileService(FileMapper fileMapper, UserService userService) {
        this.fileMapper = fileMapper;
        this.userService = userService;
    }

    /**
     * Verify if the file already exists
     *
     * @param fileName file name
     * @return true if not exists
     */
    public boolean isFileAvailable(String fileName) {
        return fileMapper.getFile(fileName) == null;
    }

    /**
     * Upload file to bdd
     *
     * @param fileToUpload file to upload
     * @return id of the created file
     */
    public int uploadFile(MultipartFile fileToUpload) {
        //get file contents
        String fileName = fileToUpload.getOriginalFilename();
        String contentType = fileToUpload.getContentType();
        String fileSize = String.valueOf(fileToUpload.getSize());
        int userId = userService.getConnectedUserId();
        byte[] fileData;

        //verify if the user id is correct
        if (userId < 0) {
            return -1;
        }

        //try to parse file to array of bytes
        try {
            fileData = fileToUpload.getBytes();
        } catch (IOException e) {
            e.printStackTrace();
            return -1;
        }

        //create object File
        File file = new File(null, fileName, contentType, fileSize, userId, fileData);
        return fileMapper.insert(file);
    }

    /**
     * Get all upload files
     *
     * @return list of upload files
     */
    public List<File> getAllFiles() {
        return fileMapper.getAllFiles();
    }

    /**
     * Get file by id
     *
     * @param fileId file id
     * @return file
     */
    public File getFile(int fileId) {
        return fileMapper.getFileById(fileId);
    }

    /**
     * Delete file corresponding to id parameter
     *
     * @param id id of the file to delete
     * @return true if deletion ok
     */
    public boolean deleteFile(int id) {
        return fileMapper.deleteFile(id);
    }
}
