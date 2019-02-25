[![license](https://img.shields.io/github/license/mashape/apistatus.svg) ](LICENSE)

# imgur-uploader
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
                jsonObject.get("link"); // getting the link of the uploaded image
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
            jsonObject.get("link"); // getting the link of the uploaded image
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
```xml
<repository>
   <id>maven-public</id>
   <url>http://nexus.ihaq.me/repository/maven-public/</url>
</repository>
```
```xml
<dependency>
    <groupId>me.ihaq</groupId>
    <artifactId>imgur-uploader</artifactId>
    <version>1.0</version>
</dependency>
```

