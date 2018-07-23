[![license](https://img.shields.io/github/license/mashape/apistatus.svg) ](LICENSE)

# Imgur Helper
A simple library that allows you to upload your images to imgur.

## Usage
```java
public class Main {

    public static void main(String[] args) {
        
        ImgurUploader helper = new ImgurUploader("YOUR_API_KEY");

        // async method
        helper.uploadAsync(new Callback() {
            @Override
            public void onSuccess(JsonObject jsonObject) {
                System.out.println(jsonObject);
                jsonObject.get("link"); // getting the link of the upload image
            }

            @Override
            public void onFail(Exception e) {
                e.printStackTrace();
            }
        }, new File("PATH"));

        // sync method
        try {
            JsonObject jsonObject = helper.uploadSync(new File("PATH"));
            System.out.println(jsonObject);
            jsonObject.get("link"); // getting the link of the upload image
        } catch (IOException | ImgurUploaderException e) {
            e.printStackTrace();
        }

        // call this method when you are done
        helper.shutdown();
    }
}
```
Both will produce a similar result.
```json
{"id":"dWut95o","title":null,"description":null,"datetime":1532366502,"type":"image/png","animated":false,"width":410,"height":389,"size":20391,"views":0,"bandwidth":0,"vote":null,"favorite":false,"nsfw":null,"section":null,"account_url":null,"account_id":0,"is_ad":false,"in_most_viral":false,"has_sound":false,"tags":[],"ad_type":0,"ad_url":"","in_gallery":false,"deletehash":"mihFxhYshaM8kdx","name":"","link":"https://i.imgur.com/dWut95o.png"}
```

## Download

#### Maven
```xml
<repository>
    <id>ihaq-maven</id>
    <url>http://maven.ihaq.me/artifactory/libs-maven/</url>
</repository>

<dependency>
    <groupId>me.ihaq</groupId>
    <artifactId>imgur-uploader</artifactId>
    <version>1.0</version>
</dependency>
```

#### Gradle
```gradle
repositories {
    maven {
        url "http://maven.ihaq.me/artifactory/libs-maven/"
    }
}

dependencies {
    compile 'me.ihaq:imgur-uploader:1.0'
}
```
