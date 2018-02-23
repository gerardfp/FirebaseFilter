package com.example.gerard.firebasefilter;

import com.example.gerard.firebasefilter.model.Thing;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.Random;

/**
 * Created by gerard on 22/02/2018.
 */

// Esta clase solo sirve para rellenar Firebase con datos falsos
    // Lo importante de esta clase es cuando se añaden las claves de los objetos,
    // en lugar de poner el valor a true, ponemos el valor del campo por el cual
    // vamos a hacer las busquedas (en este caso  el nombre).

public class ThingsManager {

    static ThingsManager instance;

    Random random;

    DatabaseReference mReference;
    String uid;

    public static ThingsManager getInstance(){
        if (instance == null) {
            instance = new ThingsManager();
        }
        return instance;
    }

    ThingsManager(){
        random = new Random();
        mReference = FirebaseDatabase.getInstance().getReference();
        uid = FirebaseAuth.getInstance().getUid();
    }

    public void ponerCosas() {
        for(int i=0; i<100; i++) {
            String thingKey = mReference.child("cosas/data").push().getKey();

            String name = randomWord();

            mReference.child("things/data").child(thingKey).setValue(new Thing(name));

            // -------------------------------------------------------------------------------------------
            // estas dos lineas son fundamentales: NO poner el valor a "true", sino al termino de busqueda
            // -------------------------------------------------------------------------------------------

            mReference.child("things/all-things").child(thingKey).setValue(name);
            mReference.child("things/user-things").child(uid).child(thingKey).setValue(name);
        }
    }

    public void eliminarCosas() {
        mReference.child("things").setValue(null);
    }


    String randomWord(){
        char[] word = new char[random.nextInt(8)+4];
        for (int j = 0; j < word.length; j++) {
            word[j] = (char)('a' + random.nextInt(26));
        }

        return new String(word);
    }
}
