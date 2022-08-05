package com.youtube_demo.util.constText;

import lombok.AllArgsConstructor;
import lombok.Getter;

/**
 * @author wyk
 * @date 2022/8/5 18:34
 * @description youtube相关常量
 */
@Getter
@AllArgsConstructor
public enum YouTubeConst {
    /**操作成功**/
    BASE_URL("https://www.googleapis.com/youtube/v3"),

//    /**workspace_key**/
//    KEY("AIzaSyDkAu2Vi-My8rxmvfVvXkt9Zuj7zuqAq2E"),

    /** Key **/
    KEY("AIzaSyADflK5MEC9uB8bvgLIQU01yuv6ur4Bn2c");




    private final String Text;
}
