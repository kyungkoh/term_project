package org.androidtown.lbs.map;

/**
 * Created by dksek_000 on 2016-11-16.
 */

import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

public class DBHelper extends SQLiteOpenHelper {


    // DBHelper 생성자로 관리할 DB 이름과 버전 정보를 받음
    public DBHelper(Context context, String name, SQLiteDatabase.CursorFactory factory, int version) {
        super(context, name, factory, version);
    }

    // DB를 새로 생성할 때 호출되는 함수
    @Override
    public void onCreate(SQLiteDatabase db) {
        // 새로운 테이블 생성
        /* 이름은 MONEYBOOK이고, 자동으로 값이 증가하는 _id 정수형 기본키 컬럼과
        item 문자열 컬럼, price 정수형 컬럼, create_at 문자열 컬럼으로 구성된 테이블을 생성. */
        db.execSQL("CREATE TABLE MYLOGGER (_id INTEGER PRIMARY KEY AUTOINCREMENT, title TEXT, content TEXT, categori TEXT, lati REAL, longi REAL);");
    }

    // DB 업그레이드를 위해 버전이 변경될 때 호출되는 함수
    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {

    }

    public void insert(String title, String content,String categori, Double lati, Double longi) {
        // 읽고 쓰기가 가능하게 DB 열기
        SQLiteDatabase db = getWritableDatabase();
        // DB에 입력한 값으로 행 추가
        db.execSQL("INSERT INTO MYLOGGER(title, content, categori, lati, longi) VALUES(?,?,?,?,?);",new Object[]{title, content, categori, lati, longi});
        db.close();
    }

    public void delete(String title) {
        SQLiteDatabase db = getWritableDatabase();
        // 입력한 항목과 일치하는 행 삭제
        db.execSQL("DELETE FROM WHERE title='" + title + "';");
        db.close();
    }


    public String getResult() {
        // 읽기가 가능하게 DB 열기
        SQLiteDatabase db = getReadableDatabase();
        String result = "";
        String result1 = "학교 \n";
        String result2 = "집 \n";
        String result3 = "카페 \n";
        String result4 = "여행지 \n";
        String result5 = "식당 \n";
        String result6 = "영화관 \n";
        String result7 = "기타 \n";

        // DB에 있는 데이터를 쉽게 처리하기 위해 Cursor를 사용하여 테이블에 있는 모든 데이터 출력
        Cursor cursor = db.rawQuery("SELECT * FROM MYLOGGER", null);
        while (cursor.moveToNext()) {
            //분류 : 학교, 집, 카페, 여행지, 식당, 영화관

            if(cursor.getString(3).equals("학교") )
            {
                result1 += "제목: " + cursor.getString(1) + "   내용 : " + cursor.getString(2) +  "   위도 : " + cursor.getString(4)+ "   경도 : " + cursor.getString(5) + "\n";
            }

            else if(cursor.getString(3).equals("집"))
            {
                result2 += "제목: " + cursor.getString(1) + "   내용 : " + cursor.getString(2) + "   위도 : " + cursor.getString(4)+ "   경도 : " + cursor.getString(5) +"\n";
            }

            else if(cursor.getString(3).equals("카페") )
            {
                result3 += "제목: " + cursor.getString(1) + "   내용 : " + cursor.getString(2) +"   위도 : " + cursor.getString(4)+ "   경도 : " + cursor.getString(5) +"\n";
            }

            else if(cursor.getString(3).equals( "여행지"))
            {
                result4 += "제목: " + cursor.getString(1) + "   내용 : " + cursor.getString(2) +  "   위도 : " + cursor.getString(4)+ "   경도 : " + cursor.getString(5) +"\n";
            }

            else if(cursor.getString(3).equals("식당") )
            {
                result5 += "제목: " + cursor.getString(1) + "   내용 : " + cursor.getString(2) +  "   위도 : " + cursor.getString(4)+ "   경도 : " + cursor.getString(5) +"\n";
            }

            else if(cursor.getString(3).equals("영화관"))
            {
                result6 += "제목: " + cursor.getString(1) + "   내용 : " + cursor.getString(2) +  "   위도 : " + cursor.getString(4)+ "   경도 : " + cursor.getString(5) +"\n";
            }

            else
            {
                result7 += "제목: " + cursor.getString(1) + "   내용 : " + cursor.getString(2) +  "   위도 : " + cursor.getString(4)+ "   경도 : " + cursor.getString(5) +"\n";
            }
        }

        result = result1+"\n"+result2+"\n"+result3+"\n"+result4+"\n"+result5+"\n"+result6+"\n"+result7;
        return result;
    }
}


