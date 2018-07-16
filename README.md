# spring-boot-sample

docker image作成サンプルです。
[jib](https://github.com/GoogleContainerTools/jib/tree/master/jib-maven-plugin)を使い、Google Container Registryに
docker imageをmavenにて登録するまでのサンプルです。

jibを使う事により、`/target/xxxxx.jar` に作成されるパッケージモジュールと、
Dockerfileをシームレスに連携させることができます。

## 環境

- JDK 8以上
- Apache Maven 3.5
- Google Cloud SDK + gcloud components install docker-credential-gcr インストール環境

## 概要

[pom.xml](pom.xml#L56-L78) にて、[jib](https://github.com/GoogleContainerTools/jib)を利用し、docker imageをビルドします。

### pom.xmlの簡単な説明

```
<from>
  <image>openjdk:8u171-jre-alpine</image>
</from>
```
ベースイメージは、一番軽量となるalpine + jre のイメージを利用しています。
<br>
<br>
```
<to>
  <image>gcr.io/${gcp.project.name}/${project.name}:${project.version}</image>
</to>
```
作成されるイメージのタグは、Google Container Registryの形式に従います。  
イメージ名、及びバージョンは、pom.xmlの定義に従います。  
GCPプロジェクト名は、実行時の引数より受け取ります。これは後述する [#ビルド]を参照してください。
<br>
<br>
```
<args>
  <arg>--spring.profiles.active=${SPRING_PROFILES_ACTIVE}</arg>
  <arg>--server.port=${SPRING_SERVER_PORT}</arg>
  <arg>--spring.config.location=/spring-boot/config/application.yaml</arg>
</args>
```
Springのプロファイル、サーバーポートは、[環境変数](kubernetes/020_deployments.yml#L49-L54)から設定しています。<br>
また、`=/spring-boot/config/application.yaml` は [configmap](kubernetes/010_configmap.yaml)でマウントされた情報となります。

## 実行

### ビルド
下記コマンドを実行するだけで、Google Container Registryにイメージが登録されます。
`sample-project` は、GCPプロジェクト名を指定してください。

```
mvn -Dgcp.project.name=sample-project compile jib:build
```

### 配備
下記コマンドを実行し、kubernetesに配備を行います。
事前に、 `gcloud container clusters get-credentials ～ ` コマンドにてクラスタに接続を行ってください。

```
spring-boot-sample>kubectl apply -f kubernetes
configmap "spring-boot-config" created
deployment "spring-boot-sample" created
service "spring-boot-sample" created
spring-boot-sample>
```

### 実行

 - ノードポートに対してアクセスをしてください。
 - 必要であれば、ingressも構築し、LBを介してアクセスできるようにしてください。

