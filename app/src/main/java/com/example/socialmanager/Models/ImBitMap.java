package com.example.socialmanager.Models;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.util.Base64;

import androidx.room.TypeConverter;

import java.io.ByteArrayOutputStream;

public class ImBitMap {
    @TypeConverter
    public static byte[] convertIm2ByteArray(Bitmap bitmap){
        ByteArrayOutputStream stream = new ByteArrayOutputStream();
        bitmap.compress(Bitmap.CompressFormat.JPEG,0, stream);
        return stream.toByteArray();

    }

    @TypeConverter
    public static Bitmap convertByteArray2Im(byte[] array){
        return BitmapFactory.decodeByteArray(array,0,array.length);
    }

}
