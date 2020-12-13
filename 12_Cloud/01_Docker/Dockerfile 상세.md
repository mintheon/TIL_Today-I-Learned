### 기초

#### FROM

- 도커 이미지를 생성할때 사용할 BASE 이미지를 지정
- 해당 이미지가 없으면 서버 REPOSITORY에서 다운

#### MAINTINER

- Dockerfile을 생성한 사람을 적음

#### RUN

- FROM에서 지정한 기본 이미지 위에 명령 수행하여 새로운 이미지 생성
  - 우분투 최신 패치 수행  
    `RUN apt-get update`  
    `RUN apt-get install wget`

#### CMD

- 컨테이너가 수행될 때 지정된 명령어/명렁/스크립트 파일 실행
- Dockerfile에서 한번만 사용가능
  - `CMD ["echo $PATH"]`

#### ENTRYPOINT

- CMD와 거의 같으나 컨테이너 생성(run)이나 시작(start)될 떄 실행
  - `ENTRYPOINT ["/sample.sh"]`

### 환경변수 설정

- **일반적인 우분투의 경우**

  - 홈 디렉토리의 `~/.bashrc`나 `~/.profile`에 `export sample=/sample`과 같이 지정한 후, `source ~/bashrc` 나 `soource ~/.profile`로 환경변수에 반영한다.

- **도커에서는**
  - 환경변수 지정
    - `docker run --env sample=/sample --name -ubuntu ubuntu`
  - Dockerfile
    - `ENV sample=/sample`
