package com.example.criminalintent.model;

import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.PrimaryKey;
import androidx.room.TypeConverter;

import java.io.Serializable;
import java.util.Date;
import java.util.UUID;

@Entity(tableName = "crimetable")
public class Crime implements Serializable {

    @PrimaryKey(autoGenerate = true)
    private long id;
    @ColumnInfo(name = "uuid")
    private UUID mUUID;
    @ColumnInfo(name = "title")
    private String mTitle;
    @ColumnInfo(name = "date")
    private Date mDate;
    @ColumnInfo(name = "solved")
    private boolean mSolved;
    @ColumnInfo(name = "suspect")
    private String mSuspect;

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public void setUUID(UUID UUID) {
        mUUID = UUID;
    }

    public UUID getUUID() {
        return mUUID;
    }

    public String getTitle() {
        return mTitle;
    }

    public void setTitle(String title) {
        mTitle = title;
    }

    public Date getDate() {
        return mDate;
    }

    public void setDate(Date date) {
        mDate = date;
    }

    public boolean isSolved() {
        return mSolved;
    }

    public void setSolved(boolean solved) {
        mSolved = solved;
    }

    public String getSuspect() {
        return mSuspect;
    }

    public void setSuspect(String suspect) {
        mSuspect = suspect;
    }

    /**
     * if you don't have any uuid (that means you want to create new crime) call this
     * constructor.
     */
    public Crime() {
        this(UUID.randomUUID());
//        mDate = DateUtils.getRandomDate(2000, 2020); //random date between 2000 to 2020
    }

    /**
     * if you have a uuid for crime use this constructor. (that means you don't want to create
     * a new crime).
     *
     * @param uuid
     */
    public Crime(UUID uuid) {
        mUUID = uuid;
        mDate = new Date(); //current date
    }

    public Crime(UUID UUID, String title, Date date, boolean solved, String suspect) {
        mUUID = UUID;
        mTitle = title;
        mDate = date;
        mSolved = solved;
        mSuspect = suspect;
    }

    public Crime(String title, boolean solved) {
        this();
        this.mTitle = title;
        this.mSolved = solved;
    }

    /**
     * This is calculator field.
     *
     * @return
     */
    public String getPhotoFileName() {
        return "IMG_" + getUUID() + ".jpg";
    }

    @Override
    public String toString() {
        return "Crime{" +
                "mId=" + mUUID +
                ", mTitle='" + mTitle + '\'' +
                ", mDate=" + mDate +
                ", mSolved=" + mSolved +
                ", mSuspect='" + mSuspect + '\'' +
                '}';
    }


    public static class UUIDConverter {
        @TypeConverter
        public String UUIDToString(UUID value) {
            return value.toString();
        }

        @TypeConverter
        public UUID fromString(String value) {
            return UUID.fromString(value);
        }
    }

    // example converter for java.util.Date
    public static class DateConverters {
        @TypeConverter
        public Date fromTimestamp(Long value) {
            return value == null ? null : new Date(value);
        }

        @TypeConverter
        public Long dateToTimestamp(Date date) {
            if (date == null) {
                return null;
            } else {
                return date.getTime();
            }
        }
    }
}
