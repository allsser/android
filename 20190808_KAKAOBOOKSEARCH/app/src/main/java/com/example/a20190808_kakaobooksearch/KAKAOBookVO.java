package com.example.a20190808_kakaobooksearch;

import android.graphics.drawable.Drawable;
import android.os.Parcel;
import android.os.Parcelable;
import android.util.Log;

import java.io.InputStream;
import java.net.URL;
import java.util.ArrayList;

// 해당 클래스의 객체가 마샬링(객체를 직렬화?)이 가능하도록 parcelable interface를 구현

public class KAKAOBookVO implements Parcelable {

    private ArrayList<String> authors;
    private ArrayList<String> translators;
    private String contents;
    private String title;
    private String url;
    private String isbn;
    private String datetime;
    private String publisher;
    private String price;
    private String sale_price;
    private String thumbnail;
    private String status;


    private Drawable drawable;

    // CREATOR라고 불리는 static 상수를 반드시 정의!
    public static  final Creator<KAKAOBookVO> CREATOR = new Creator<KAKAOBookVO>() {

        @Override
        public KAKAOBookVO createFromParcel(Parcel parcel) {
            // 마샬링된 데이터를 언마샬링(복원)할 때 사용되는 method
            return new KAKAOBookVO(parcel);
        }

        @Override
        public KAKAOBookVO[] newArray(int size) {
            return new KAKAOBookVO[size];
        }
    };

    // default constructor
    public KAKAOBookVO() {}

    // 모든 filed를 인자로 받는 constructor
    public KAKAOBookVO(ArrayList<String> authors, ArrayList<String> translators, String contents, String title, String url, String isbn, String datetime, String publisher, String price, String sale_price, String thumbnail, String status) {
        this.authors = authors;
        this.translators = translators;
        this.contents = contents;
        this.title = title;
        this.url = url;
        this.isbn = isbn;
        this.datetime = datetime;
        this.publisher = publisher;
        this.price = price;
        this.sale_price = sale_price;
        this.thumbnail = thumbnail;
        this.status = status;
    }

    // 복원작업할때 사용되는 생성자.
    // 복원시 제일 신경써야 하는 부분은 순서이다.(마샬링 순서와 언마샬링 순서가 동일해야 한다.)
    protected KAKAOBookVO(Parcel parcel) {

        authors = parcel.readArrayList(null);
        translators = parcel.readArrayList(null);
        contents = parcel.readString();
        title = parcel.readString();
        url = parcel.readString();
        isbn = parcel.readString();
        datetime = parcel.readString();
        publisher = parcel.readString();
        price = parcel.readString();
        sale_price = parcel.readString();
        thumbnail = parcel.readString();
        status = parcel.readString();
    }

    // override method

    // 수정할 필요 없다.
    @Override
    public int describeContents() {
        return 0;
    }

    // 마샬링(데이터를 변환)하는 역할을 하는 method
    // 데이터를 변화하는 순서와 복원하는 순서가 반드시 같아야 한다.
    @Override
    public void writeToParcel(Parcel parcel, int i) {
        try {
            parcel.writeList(authors);
            parcel.writeList(translators);
            parcel.writeString(contents);
            parcel.writeString(title);
            parcel.writeString(url);
            parcel.writeString(isbn);
            parcel.writeString(datetime);
            parcel.writeString(publisher);
            parcel.writeString(price);
            parcel.writeString(sale_price);
            parcel.writeString(thumbnail);
            parcel.writeString(status);

        } catch (Exception e) {
            Log.i("KAKAOLog", e.toString());
        }
    }

    // getter & setter

    public ArrayList<String> getAuthors() {
        return authors;
    }

    public void setAuthors(ArrayList<String> authors) {
        this.authors = authors;
    }

    public ArrayList<String> getTranslators() {
        return translators;
    }

    public void setTranslators(ArrayList<String> translators) {
        this.translators = translators;
    }

    public String getContents() {
        return contents;
    }

    public void setContents(String contents) {
        this.contents = contents;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public String getIsbn() {
        return isbn;
    }

    public void setIsbn(String isbn) {
        this.isbn = isbn;
    }

    public String getDatetime() {
        return datetime;
    }

    public void setDatetime(String datetime) {
        this.datetime = datetime;
    }

    public String getPublisher() {
        return publisher;
    }

    public void setPublisher(String publisher) {
        this.publisher = publisher;
    }

    public String getPrice() {
        return price;
    }

    public void setPrice(String price) {
        this.price = price;
    }

    public String getSale_price() {
        return sale_price;
    }

    public void setSale_price(String sale_price) {
        this.sale_price = sale_price;
    }

    public String getThumbnail() {
        return thumbnail;
    }

    public void setThumbnail(String thumbnail) {
        this.thumbnail = thumbnail;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public Drawable getDrawable() {
        return drawable;
    }

    // 이미지데이터 추출
    public void drawableFromURL() {
        Drawable d = null;
        try {
            InputStream is = (InputStream)new URL(thumbnail).getContent();
            d = Drawable.createFromStream(is, "NAME");
            this.drawable = d;
        } catch (Exception e) {
            Log.i("Error", e.toString());
        }
    }

}
