package com.example.zxingscan.utils;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.Color;
import android.util.Log;
import android.widget.Toast;

import com.google.zxing.BarcodeFormat;
import com.google.zxing.EncodeHintType;
import com.google.zxing.MultiFormatWriter;
import com.google.zxing.common.BitMatrix;
import java.util.Hashtable;


public class CreateQRCode {
    public static Bitmap createQRCode(String codeString, BarcodeFormat code, Context context){
        //首先判断参数的合法性，要求字符串内容不能为空或图片长宽必须大于0
        int width = 255;
        int height = 255;
        if(code == BarcodeFormat.CODABAR || code == BarcodeFormat.CODE_39 || code == BarcodeFormat.CODE_93 || code == BarcodeFormat.CODE_128
        || code == BarcodeFormat.EAN_8 || code == BarcodeFormat.EAN_13 || code == BarcodeFormat.ITF || code == BarcodeFormat.PDF_417
        || code == BarcodeFormat.UPC_A || code == BarcodeFormat.UPC_E) height = 100;
        //设置二维码的相关参数，生成BitMatrix（位矩阵）对象
        Hashtable<EncodeHintType,String> hashtable=new Hashtable<>();
        hashtable.put(EncodeHintType.CHARACTER_SET,"utf-8");  //设置字符转码格式
        if(code == BarcodeFormat.QR_CODE) hashtable.put(EncodeHintType.ERROR_CORRECTION,"H");   //设置容错级别
        else hashtable.put(EncodeHintType.ERROR_CORRECTION, "8");
        hashtable.put(EncodeHintType.MARGIN,"2"); //设置空白边距
        BitMatrix bitMatrix;
        //encode需要抛出和处理异常
        try {
            bitMatrix = new MultiFormatWriter().encode(codeString, code, width, height, hashtable);
        }catch(Exception e){
            Log.d("TAG", "createQRCode:  " + e);
            String[] log = e.toString().split(": ");
            Toast.makeText(context, log[1], Toast.LENGTH_SHORT).show();
            return null;
        }
        //再创建像素数组，并根据位矩阵为数组元素赋颜色值
        int[] pixel=new int[width*width];
        for (int h=0;h<height;h++){
            for (int w=0;w<width;w++){
                if (bitMatrix.get(w,h)){
                    pixel[h*width+w]= Color.BLACK;  //设置黑色色块
                }else{
                    pixel[h*width+w]=Color.WHITE;  //设置白色色块
                }
            }
        }
        //创建bitmap对象
        //根据像素数组设置Bitmap每个像素点的颜色值，之后返回Bitmap对象
        Bitmap qrCodeMap=Bitmap.createBitmap(width,height,Bitmap.Config.ARGB_8888);
        qrCodeMap.setPixels(pixel,0,width,0,0,width,height);
        return qrCodeMap;
    }
}
