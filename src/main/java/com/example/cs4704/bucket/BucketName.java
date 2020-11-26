package com.example.cs4704.bucket;

public enum BucketName {
    PROFILE_IMAGE("aws-springboot-image");

    private final String bucketName;

    BucketName(String bucketName) {
        this.bucketName = bucketName;
    }


    public String getBucketName() {
        return bucketName;
    }
}
