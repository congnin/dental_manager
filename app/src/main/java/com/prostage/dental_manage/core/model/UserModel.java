package com.prostage.dental_manage.core.model;

import android.os.Parcel;
import android.os.Parcelable;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.List;

/**
 * Created by congnc on 5/16/17.
 */

public class UserModel implements Parcelable {
    @SerializedName("id")
    @Expose
    private Integer id;
    @SerializedName("birthday")
    @Expose
    private String birthday;
    @SerializedName("firstName")
    @Expose
    private String firstName;
    @SerializedName("firstNickName")
    @Expose
    private String firstNickName;
    @SerializedName("lastName")
    @Expose
    private String lastName;
    @SerializedName("lastNickName")
    @Expose
    private String lastNickName;
    @SerializedName("gender")
    @Expose
    private String gender;
    @SerializedName("pushNotification")
    @Expose
    private String pushNotification;
    @SerializedName("note")
    @Expose
    private String note;
    @SerializedName("free")
    @Expose
    private String free;
    @SerializedName("createdDate")
    @Expose
    private String createdDate;
    @SerializedName("updatedDate")
    @Expose
    private String updatedDate;
    @SerializedName("reservations")
    @Expose
    private List<ReservationModel> reservations = null;
    @SerializedName("adminId")
    @Expose
    private Integer adminId;
    @SerializedName("username")
    @Expose
    private String username;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getBirthday() {
        return birthday;
    }

    public void setBirthday(String birthday) {
        this.birthday = birthday;
    }

    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public String getFirstNickName() {
        return firstNickName;
    }

    public void setFirstNickName(String firstNickName) {
        this.firstNickName = firstNickName;
    }

    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public String getLastNickName() {
        return lastNickName;
    }

    public void setLastNickName(String lastNickName) {
        this.lastNickName = lastNickName;
    }

    public String getGender() {
        return gender;
    }

    public void setGender(String gender) {
        this.gender = gender;
    }

    public String getPushNotification() {
        return pushNotification;
    }

    public void setPushNotification(String pushNotification) {
        this.pushNotification = pushNotification;
    }

    public String getNote() {
        return note;
    }

    public void setNote(String note) {
        this.note = note;
    }

    public String getFree() {
        return free;
    }

    public void setFree(String free) {
        this.free = free;
    }

    public String getCreatedDate() {
        return createdDate;
    }

    public void setCreatedDate(String createdDate) {
        this.createdDate = createdDate;
    }

    public String getUpdatedDate() {
        return updatedDate;
    }

    public void setUpdatedDate(String updatedDate) {
        this.updatedDate = updatedDate;
    }

    public List<ReservationModel> getReservations() {
        return reservations;
    }

    public void setReservations(List<ReservationModel> reservations) {
        this.reservations = reservations;
    }

    public Integer getAdminId() {
        return adminId;
    }

    public void setAdminId(Integer adminId) {
        this.adminId = adminId;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }


    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeValue(this.id);
        dest.writeString(this.birthday);
        dest.writeString(this.firstName);
        dest.writeString(this.firstNickName);
        dest.writeString(this.lastName);
        dest.writeString(this.lastNickName);
        dest.writeString(this.gender);
        dest.writeString(this.pushNotification);
        dest.writeString(this.note);
        dest.writeString(this.free);
        dest.writeString(this.createdDate);
        dest.writeString(this.updatedDate);
        dest.writeTypedList(this.reservations);
        dest.writeValue(this.adminId);
        dest.writeString(this.username);
    }

    public UserModel() {
    }

    protected UserModel(Parcel in) {
        this.id = (Integer) in.readValue(Integer.class.getClassLoader());
        this.birthday = in.readString();
        this.firstName = in.readString();
        this.firstNickName = in.readString();
        this.lastName = in.readString();
        this.lastNickName = in.readString();
        this.gender = in.readString();
        this.pushNotification = in.readString();
        this.note = in.readString();
        this.free = in.readString();
        this.createdDate = in.readString();
        this.updatedDate = in.readString();
        this.reservations = in.createTypedArrayList(ReservationModel.CREATOR);
        this.adminId = (Integer) in.readValue(Integer.class.getClassLoader());
        this.username = in.readString();
    }

    public static final Creator<UserModel> CREATOR = new Creator<UserModel>() {
        @Override
        public UserModel createFromParcel(Parcel source) {
            return new UserModel(source);
        }

        @Override
        public UserModel[] newArray(int size) {
            return new UserModel[size];
        }
    };
}
