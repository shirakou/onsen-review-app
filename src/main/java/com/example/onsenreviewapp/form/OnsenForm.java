package com.example.onsenreviewapp.form;

import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class OnsenForm {

    @NotBlank(message = "温泉名を入力してください")
    @Size(max = 100, message = "温泉名は100文字以内で入力してください")
    private String name;

    @NotBlank(message = "画像URLを入力してください")
    @Size(max = 255, message = "画像URLは255文字以内で入力してください")
    private String imageUrl;

    @NotBlank(message = "住所を入力してください")
    @Size(max = 255, message = "住所は255文字以内で入力してください")
    private String address;

    @Size(max = 20, message = "電話番号は20文字以内で入力してください")
    private String phone;

    @Size(max = 100, message = "営業時間は100文字以内で入力してください")
    private String businessHours;

    @Size(max = 100, message = "定休日は100文字以内で入力してください")
    private String holiday;

    @NotNull(message = "大人料金を入力してください")
    @Min(value = 0, message = "大人料金は0円以上で入力してください")
    private Integer adultPrice;

    @Min(value = 0, message = "子供料金は0円以上で入力してください")
    private Integer childPrice;

    @Size(max = 100, message = "最寄り駅は100文字以内で入力してください")
    private String nearestStation;

    @Min(value = 0, message = "駅からの時間は0分以上で入力してください")
    private Integer stationTime;

    @NotBlank(message = "都道府県を入力してください")
    @Size(max = 20, message = "都道府県は20文字以内で入力してください")
    private String prefecture;

    private boolean familyBath;

    private boolean sauna;

    private boolean openAirBath;

    private boolean restaurant;

    private boolean alcohol;

    private boolean towelRental;

    private boolean parking;

    @Size(max = 2000, message = "説明は2000文字以内で入力してください")
    private String description;
}