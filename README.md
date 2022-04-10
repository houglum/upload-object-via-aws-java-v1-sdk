## Building and running the code:

You'll need to have Java and Maven set up on your system.

Export your environment variables:

```shell
# For AWS:
export AWS_BUCKET_NAME=...
export AWS_ACCESS_KEY_ID=...
export AWS_SECRET_ACCESS_KEY=...

# For GCS:
export GS_BUCKET_NAME=...
export GS_ACCESS_KEY_ID=...
export GS_SECRET_ACCESS_KEY=...
```

`cd` into the project folder and run:

```shell
mvn install && mvn exec:java \
    -Dexec.mainClass="dev.houglum.s3app.AmazonS3PutObjectTest"
```

To re-build, then run the program with HTTP log ouput enabled:

```shell
mvn install && mvn exec:java \
    -Dexec.mainClass="dev.houglum.s3app.AmazonS3PutObjectTest" \
    -Dorg.apache.commons.logging.Log=org.apache.commons.logging.impl.SimpleLog \
    -Dorg.apache.commons.logging.simplelog.log.org.apache.http=DEBUG \
    -Dorg.apache.commons.logging.simplelog.log.org.apache.http.wire=ERROR
```

## Changing values around

In `main()`, you can either call `initConfigAws()` or `initConfigGoog()`. These
load your credentials & bucket name from the appropriate environment variables.

You can also change the file being uploaded, the key used to name the resulting
object, etc. by tweaking the private variables at the top of the class.
