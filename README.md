Ardroid studio 23.2.1 patch 2
gradle 8.3.2

min SDK 24 (tested on android 9 and 13)

create a new branch before you push your code

use usb debugging or wireless debugging if possible instead of using emulator. but you need an android device.

let me know if you got any gradle conflict errors. usually android studio automatically download and installs any new gradle versions.


# in order to run the confirmation service, you have to create a gmail (for sender)
*  step 1: create a new google email address
*  step 2: go to manage account → security
*  step 3: turn on 2 step verification (using a mobile number)
*  step 4: in "2 step verification" there is a option called app passwords. add a new app password(give any name eg: "MAD CW")
*  step 5: copy that password
*  step 6: then input newly created gmail address and created app password (not the password of email) at following marked locations

# update these two lines at "UserConfirmation.java"
path: mad-cw\app\src\main\java\com\example\mad_cw\user\UserConfirmation.java

![image](https://github.com/Anuradha2k21/mad-cw/assets/61109105/15e25842-0dd1-4c26-a09b-62569a985b1b)

# update the MAPS_API_KEY at "strings.xml"
path: mad-cw\app\src\main\res\values\strings.xml

![image](https://github.com/Anuradha2k21/mad-cw/assets/146810679/f6627572-a78f-4164-9cca-d77aba6dcced)

## admin pannel
email: admin@gmail.com

password: 12345678

## adding environmental variables
add `buildFeatures`, `buildConfigField` to module(app) level *build.gradle* file
and
replace `email_address`, `email_password`

```bash
android {
    buildFeatures {
        buildConfig true
    }

    defaultConfig {
	...
        buildConfigField "String", "SENDER_EMAIL_ADDRESS", "\"email_address\""
        buildConfigField "String", "SENDER_EMAIL_PASSWORD", "\"email_password\""
    }
}
```
remeber to sync gradle

### how to access from *UserConfirmation.java*

stringSenderEmail = BuildConfig.SENDER_EMAIL_ADDRESS

stringPasswordSenderEmail = BuildConfig.SENDER_EMAIL_PASSWORD

---
also don't forget to add that *build.gradle* file to gitignore
