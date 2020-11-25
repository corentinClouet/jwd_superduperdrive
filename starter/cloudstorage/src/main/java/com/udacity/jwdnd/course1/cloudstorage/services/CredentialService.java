package com.udacity.jwdnd.course1.cloudstorage.services;

import com.udacity.jwdnd.course1.cloudstorage.mapper.CredentialMapper;
import com.udacity.jwdnd.course1.cloudstorage.mapper.NoteMapper;
import com.udacity.jwdnd.course1.cloudstorage.model.Credential;
import com.udacity.jwdnd.course1.cloudstorage.model.CredentialForm;
import com.udacity.jwdnd.course1.cloudstorage.model.Note;
import com.udacity.jwdnd.course1.cloudstorage.model.NoteForm;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class CredentialService {

    private final CredentialMapper credentialMapper;
    private final UserService userService;

    public CredentialService(CredentialMapper credentialMapper, UserService userService) {
        this.credentialMapper = credentialMapper;
        this.userService = userService;
    }

    /**
     * Get all credentials
     *
     * @return list of credentials
     */
    public List<Credential> getAll() {
        return credentialMapper.getAllCredentials();
    }

    /**
     * Insert credential
     *
     * @param credentialForm to insert
     * @return true if insert/update ok
     */
    public boolean insertOrUpdate(CredentialForm credentialForm) {
        String key = "";
        if (credentialForm.getCredentialId() == null || credentialForm.getCredentialId() < 0) {
            //insert method
            Credential credential = new Credential(null, credentialForm.getCredentialUrl(), credentialForm.getCredentialUsername(), key, credentialForm.getCredentialPassword(), userService.getConnectedUserId());
            return credentialMapper.insert(credential) > 0;
        }
        //update method
        Credential credential = new Credential(credentialForm.getCredentialId(), credentialForm.getCredentialUrl(), credentialForm.getCredentialUsername(), key, credentialForm.getCredentialPassword(), userService.getConnectedUserId());
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
