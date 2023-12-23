package com.example.shoping.dto;

import com.example.shoping.entity.Product;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;

import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;

@Builder
@Data
@AllArgsConstructor
public class ProductImageDTO {
    private String uuid;
    private String imgName;
    private String path;

    public String getImageURL(){
        try{
            return URLEncoder.encode(path + "/" + uuid + "_"+ imgName, "UTF-8");
        }catch (UnsupportedEncodingException e){
            e.printStackTrace();
        }
        return "";
    }

    public String getThumbnailURL(){
        try {
            return URLEncoder.encode(path+"/s_"+uuid+"_"+imgName,"UTF-8");
        }catch (UnsupportedEncodingException e){
            e.printStackTrace();
        }
        return "";
    }
}
