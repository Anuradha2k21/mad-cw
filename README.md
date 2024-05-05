used → Android Studio Iguana(2023.2.1 Patch 2), gradle 8.4  
required → min SDK 24 (tested on android 9 and 13)

# Before run the application follow 1 and 2 steps
## Step 1. Input gmail credentials (update environmental variables)

this email is used to send you the confirmation email

*  step 1: create a new google email address
*  step 2: go to manage account → security
*  step 3: turn on 2 step verification (using a mobile number)
*  step 4: in "2 step verification" there is a option called "app passwords". add a new app password
*  step 5: copy that password
*  step 6: then input newly created gmail address and created app password (not the password of the email) at module level `build.gradle` file which can be found at `/app/build.gradle` (at line 23, 24)
![image-2](https://github.com/Anuradha2k21/mad-cw/assets/61109105/2bc3cce4-0907-4b84-9a69-580b0f7474ee)


## Step 2. Update the `MAPS_API_KEY` at "strings.xml"
create a google maps API key and enter it to `strings.xml` which can be found at below path  
path: `\app\src\main\res\values\strings.xml`  
(at line 36)  
![image-4](https://github.com/Anuradha2k21/mad-cw/assets/61109105/3cc1776b-cae6-46d7-9bfc-004fa15aefb1)


## How to login as a admin (use admin login)
email: admin@gmail.com  
password: 12345678

**each time you log-in, app automatically adds 2 dummy courses**

## Collaborators

- [Adithya](https://github.com/adithya-yashodhi?query=anuradhasanjaya2024%40gmail.com)
- [Umandi](https://github.com/ThisaruUmandi)
- [Hansani](https://github.com/Samudi1234)
