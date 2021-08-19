# RoutingStar 프로젝트 포팅 매뉴얼

## 1. gitlab 소스 크론 이후 빌드 및 배포할수 있는 작업 문서 

### FrontEnd

- 모든 빌드 버전은 package.json에 있습니다.
  `bash "aos": "^2.3.4", "aws-sdk": "^2.960.0", "axios": "^0.21.1", "babel-plugin-transform-runtime": "^6.23.0", "circular-json": "^0.5.9", "core-js": "^3.6.5", "firebase": "^8.9.1", "jsonwebtoken": "^8.5.1", "vue": "^2.6.11", "vue-dragscroll": "^3.0.0", "vue-google-login": "^2.0.5", "vue-infinite-loading": "^2.4.5", "vue-router": "^3.2.0", "vuedraggable": "^2.24.3", "vuetify": "^2.4.0", "vuex": "^3.4.0", "vuex-persistedstate": "^4.0.0" `

### BackEnd

- 사용한 JVM : openjdk version 1.8.0_292 (Zulu 8.54.0.21-CA-win64)
- SpringBoot version '2.5.2' (IDE => intellij ultimate version 212.4746.92)- gradle 방식(build.gradle파일에 선언되어있음)

2. 빌드시 사용되는 환경변수
   프론트 엔드에서 빌드시 사용되는 환경변수

- googleMap의 apikey 값이 .env.local파일에 저장되어 있습니다.
- FCM을 구현하기 위한 설정값은 파일 내부에 src/main.js에 config변수로 저장했습니다.
- 아마존 web service에 필요한 환경변수albumBucketName, bucketRegion, IdentityPoolId을 정의한 부분이 아래의 주소들에 있습니다.

```bash
    src/components/revise/ImageInput.vue
    src/components/revise/UpdateMapPointForm.vue
    src/components/revise/OthersImageInput.vue
    src/components/reviseothers/UpdateOthersMapPointForm.vue
    src/components/reviseothers/UpdateMapPointForm.vue
    src/components/routes/CreateRouteCanvas.vue
    src/components/routes/MapPointFormS3.vue
    src/components/routes/PostImageInput.vue
    src/components/store/modules/images.vue
```

백엔드인 springboot파일은 모두 build.gradle파일에 있습니다.
아래는 디펜던시 목록입니다.

```java
dependencies {
    implementation 'org.springframework.boot:spring-boot-starter-data-jdbc'
    implementation 'org.springframework.boot:spring-boot-starter-data-jpa'
    implementation 'org.springframework.boot:spring-boot-starter-oauth2-client'
    implementation 'org.springframework.boot:spring-boot-starter-security'
    implementation 'org.springframework.boot:spring-boot-starter-web'
    compileOnly 'org.projectlombok:lombok'
    developmentOnly 'org.springframework.boot:spring-boot-devtools'
    implementation 'com.h2database:h2'
    runtimeOnly 'org.mariadb.jdbc:mariadb-java-client'
    annotationProcessor 'org.projectlombok:lombok'
    testImplementation 'org.springframework.boot:spring-boot-starter-test'
    testImplementation 'org.springframework.security:spring-security-test'

    // JWT
    implementation group: 'io.jsonwebtoken', name: 'jjwt', version: '0.9.1'
    implementation group: 'javax.xml.bind', name: 'jaxb-api', version: '2.3.0'

    //Swagger 등록
    implementation group: 'io.springfox', name: 'springfox-swagger2', version: '2.9.2'
    implementation group: 'io.springfox', name: 'springfox-swagger-ui', version: '2.9.2'

    //AWS
    implementation group: 'org.springframework.cloud', name: 'spring-cloud-starter-aws', version: '2.2.1.RELEASE'

    implementation group: 'com.fasterxml.jackson.core', name: 'jackson-databind', version: '2.12.3'
}
```

- 백엔드 빌드에 필요한 환경변수는 src/main/resources/\*.properties 파일에 있습니다.
- application파일은 EC2위에 올린 mariaDB 주소와 접근계정 id,password가 있는 환경변수 파일입니다.
- Application-oauth 파일은 소셜로그인에 필요한 google, kakao사이트에서 발급받은 client ID,PW를 가지고 있고 그밖에 소셜로그인에 필요한 주소와 설정값을 가지고 있습니다.

<br>
3) 배포 환경 설명

- ec2위에 nginx를 이용해 forward proxy를 만들어 https통신을 구현했습니다.

- 백엔드 서버와 프론트엔드 서버는 jenkins를 이용해 gitlab의 backend_master, frontend_master 각각의 브랜치에서 webhook을 트리거로 build되어 백엔드는 docker위에 이미지로 배포하고 frontend는 볼륨을 공유하는 nginx를 통해 docker위에 nginx 이미지를 통해 배포되었습니다.

<br>
4) 데이터베이스 접속 정보

```
3306번 포트에 mariaDB이미지로 존재합니다.
접근 계정은
ID : test
PW : routingstar12345
url = jdbc:mariadb://i5a309.p.ssafy.io:3306/RoutingStar?autoReconnect=true&useUnicode=true&characterEncoding=utf8mb4&serverTimezone=KST
```

프로퍼티 정의된 목록은 없습니다.

## 2. 프로젝트에서 사용하는 외부 서비스 정보문서

FCM => (https://firebase.google.com/docs/cloud-messaging?hl=ko) dawit0310@gmail.com으로 등록 : 알림메시지 클라우드<br>
AWS S3 => (https://aws.amazon.com/ko/) edkim3275@gmail.com으로 가입된 S3 버켓을 사용{벗켓 주소 : https://s3.console.aws.amazon.com/s3/buckets/routingstar-photo-album?region=ap-northeast-2&tab=objects}<br>
GoogleMap => .env.local에 api key등록 (등록 이메일 : hyun.ohenn@gmail.com)<br>
Google Cloud => dawit0310@gmail.com으로 등록<br>
Kakao developer => dawit0310@gmail.com으로 등록<br>

## 3. 데이터베이스 덤프 파일 최신본

exec폴더 내에 `backup_20210819.zip`파일로 별도 첨부합니다.

## 4. 시연 시나리오

exec폴더 내에 `시연 시나리오.pptx`파일로 별도 첨부합니다.

