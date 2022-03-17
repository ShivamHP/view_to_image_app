# view_to_image_app
This app converts a (a card view, to be specific)view into an image.

## Official resources that I used:
https://www.youtube.com/watch?v=h9TunRURWNU \
https://medium.com/androiddevelopers/sharing-content-between-android-apps-2e6db9d1368b \
https://developer.android.google.cn/reference/androidx/core/content/FileProvider 

## Description:
No READ_EXTERNAL_STORAGE WRITE_EXTERNAL_STORAGE needed: Neither for this app nor for the app that will recieve the image. \
No memory leaks \
The images get removed from cache once it reaches a threshold and more memory is needed.

## Important things to keep in mind:
Make a new file: app/res/xml/provider_path.xml and copy this to it: 
```sh
<?xml version="1.0" encoding="utf-8"?>
<paths>
    <external-cache-path name="external_files" path="my_images/"/>
</paths>
```

Also mention this provider in AndroidManifest.xml 
```sh
<application ...
    <provider
            android:name="androidx.core.content.FileProvider"
            android:authorities="${applicationId}.provider"
            android:exported="false"
            android:grantUriPermissions="true">
            <meta-data
                android:name="android.support.FILE_PROVIDER_PATHS"
                android:resource="@xml/provider_path" />
        </provider>
</application>
```
