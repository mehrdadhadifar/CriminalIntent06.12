package com.example.criminalintent.repository;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;

import androidx.room.Room;

import com.example.criminalintent.database.CrimeDBSchema;
import com.example.criminalintent.database.CrimeDBSchema.CrimeTable.COLS;
import com.example.criminalintent.database.CrimeDataBase;
import com.example.criminalintent.database.cursorwrapper.CrimeCursorWrapper;
import com.example.criminalintent.model.Crime;

import java.io.File;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

public class CrimeDBRepository implements IRepository<Crime> {

    private static CrimeDBRepository sCrimeRepository;

    //future referenced: memory leaks
    private static Context mContext;

    private CrimeDataBase mDatabase;

    public static CrimeDBRepository getInstance(Context context) {
        mContext = context.getApplicationContext();
        if (sCrimeRepository == null)
            sCrimeRepository = new CrimeDBRepository();

        return sCrimeRepository;
    }

    private CrimeDBRepository() {
        mDatabase = Room.databaseBuilder(mContext,
                CrimeDataBase.class,
                "CrimeDB")
                .allowMainThreadQueries()
                .build();
    }

    //Read all
    @Override
    public List<Crime> getList() {
        return mDatabase.CrimeDAO().getCrimes();
    }

    //Read one
    @Override
    public Crime get(UUID uuid) {
        return mDatabase.CrimeDAO().getCrime(uuid.toString());
    }

    //Update one
    @Override
    public void update(Crime crime) {
        mDatabase.CrimeDAO().updateCrime(crime);
    }

    //Delete
    @Override
    public void delete(Crime crime) {
        mDatabase.CrimeDAO().deleteCrime(crime);
    }


    //Create: Insert
    @Override
    public void insert(Crime crime) {
        mDatabase.CrimeDAO().insertCrime(crime);
    }

    //Create: Insert
    @Override
    public void insertList(List<Crime> crimes) {
        Crime[] itemsArray = new Crime[crimes.size()];
        mDatabase.CrimeDAO().insertCrimes(crimes.toArray(itemsArray));
    }


    @Override
    public int getPosition(UUID uuid) {
        List<Crime> crimes = getList();
        for (int i = 0; i < crimes.size(); i++) {
            if (crimes.get(i).getUUID().equals(uuid))
                return i;
        }

        return -1;
    }

    @Override
    public File getPhotoFile(Context context, Crime crime) {
        File photoFile = new File(context.getFilesDir(), crime.getPhotoFileName());
        return photoFile;
    }

    /**
     * Convert crime pojo to ContentValue
     *
     * @param crime
     * @return
     */
}
