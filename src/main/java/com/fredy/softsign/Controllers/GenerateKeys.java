package com.fredy.softsign.Controllers;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.security.NoSuchAlgorithmException;
import java.security.NoSuchProviderException;

import java.security.KeyPair;
import java.security.KeyPairGenerator;
import java.security.PrivateKey;
import java.security.PublicKey;

@RestController
@RequestMapping("/api/")
public class GenerateKeys {
    private KeyPairGenerator keygen;
    private KeyPair pair;
    private PrivateKey privateKey;
    private PublicKey publicKey;


    public GenerateKeys() throws NoSuchAlgorithmException, NoSuchProviderException{
        this.keygen = KeyPairGenerator.getInstance("RSA");
        this.keygen.initialize(2048);
    }

    public void createKeys() {
        this.pair = this.keygen.generateKeyPair();
        this.privateKey = pair.getPrivate();
        this.publicKey = pair.getPublic();
    }
    public PrivateKey getPrivateKey() {
        return this.privateKey;
    }

    public PublicKey getPublicKey() {
        return this.publicKey;
    }

    public void writeToFile(String path, byte[] key) throws IOException {
        File f = new File(path);
        f.getParentFile().mkdirs();

        FileOutputStream fos = new FileOutputStream(f);
        fos.write(key);
        fos.flush();
        fos.close();
    }


    @GetMapping("/getKeys")
    public void StartGenerateKeys(){

        GenerateKeys gk;
        try {
            gk = new GenerateKeys();
            gk.createKeys();
            gk.writeToFile("KeyPair/publicKey", gk.getPublicKey().getEncoded());
            gk.writeToFile("KeyPair/privateKey", gk.getPrivateKey().getEncoded());

        } catch (Exception e) {
            System.err.println(e.getMessage());
            
        }



    }
}
