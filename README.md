# spring-boot-sample

docker image作成サンプルです。
[jib](https://github.com/GoogleContainerTools/jib/tree/master/jib-maven-plugin)を使い、Google Container Registryに
docker imageをmavenにて登録するまでのサンプルです。

jibを使う事により、`/target/xxxxx.jar` に作成されるパッケージモジュールと、
Dockerfileをシームレスに連携させることができます。


## 環境

・JDK 8以上
・Apache Maven 3.5
・Google Cloud SDK + gcloud components install docker-credential-gcr インストール環境







