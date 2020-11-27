package com.udacity.jwdnd.course1.cloudstorage.services;

import com.udacity.jwdnd.course1.cloudstorage.mapper.CredentialMapper;
import com.udacity.jwdnd.course1.cloudstorage.mapper.NoteMapper;
import com.udacity.jwdnd.course1.cloudstorage.model.Credential;
import com.udacity.jwdnd.course1.cloudstorage.model.CredentialForm;
import com.udacity.jwdnd.course1.cloudstorage.model.Note;
import com.udacity.jwdnd.course1.cloudstorage.model.NoteForm;
import org.springframework.stereotype.Service;

import java.security.SecureRandom;
import java.util.Base64;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

@Service
public class CredentialService {

    private final CredentialMapper credentialMapper;
    private final UserService userService;
    private final EncryptionService encryptionService;

    public CredentialService(CredentialMapper credentialMapper, UserService userService, EncryptionService encryptionService) {
        this.credentialMapper = credentialMapper;
        this.userService = userService;
        this.encryptionService = encryptionService;
    }

    /**
     * Get all credentials
     *
     * @return list of credentials
     */
    public List<Credential> getAll() {
        return credentialMapper.getAllCredentials(userService.getConnectedUserId());
    }

    /**
     * Verify url format
     *
     * @param url to test
     * @return true if the format is ok
     */
    public boolean verifyUrl(String url) {
        String pattern = "^(https?|ftp|file)://[-a-zA-Z0-9+&@#/%?=~_|!:,.;]*[-a-zA-Z0-9+&@#/%=~_|]";
        Pattern patt = Pattern.compile(pattern);
        Matcher matcher = patt.matcher(url);
        return matcher.matches();
    }

    /**
     * Insert/update credential
     *
     * @param credentialForm to insert/update
     * @return true if insert/update ok
     */
    public boolean insertOrUpdate(CredentialForm credentialForm) {
        //generate random key
        SecureRandom random = new SecureRandom();
        byte[] salt = new byte[16];
        random.nextBytes(salt);
        String key = Base64.getEncoder().encodeToString(salt);

        //encrypt password
        String encryptedPassword = encryptionService.encryptValue(credentialForm.getCredentialPassword(), key);

        //insert or update
        if (credentialForm.getCredentialId() == null || credentialForm.getCredentialId() < 0) {
            //insert method
            Credential credential = new Credential(null, credentialForm.getCredentialUrl(), credentialForm.getCredentialUsername(), key, encryptedPassword, userService.getConnectedUserId());
            return credentialMapper.insert(credential) > 0;
        }
        //update method
        Credential credential = new Credential(credentialForm.getCredentialId(), credentialForm.getCredentialUrl(), credentialForm.getCredentialUsername(), key, encryptedPassword, userService.getConnectedUserId());
        return credentialMapper.update(credential);
    }

    /**
     * Delete credential corresponding to id parameter
     *
     * @param credentialId id of the credential to delete
     * @return true if deletion ok
     */
    public boolean delete(int credentialId) {
        return credentialMapper.delete(credentialId);
    }
}
