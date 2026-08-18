# 温泉レビューアプリ

## 概要

温泉施設の検索、レビュー投稿、お気に入り登録ができるWebアプリです。

## 使用技術

* Java 17
* Spring Boot
* Spring Data JPA
* Spring Security
* Thymeleaf
* PostgreSQL
* Bootstrap

## 実装状況

### Entity

* User
* Onsen
* Review
* Favorite

### 機能

#### 新規会員登録

* 入力フォーム
* バリデーション
* メールアドレス重複チェック
* BCryptによるパスワードハッシュ化
* PostgreSQLへのユーザー登録

#### ログイン・ログアウト

* Spring Securityを使用したログイン・ログアウト機能
* メールアドレスとパスワードによる認証
* BCryptによるパスワード照合
* ログイン失敗時のエラー表示
* ログイン成功後のホーム画面遷移

#### 温泉一覧・検索・詳細表示

* 温泉情報の一覧表示
* 温泉名による部分一致検索
* 都道府県による完全一致検索
* 温泉ごとの詳細画面表示
* PathVariable`で温泉IDを受け取り、IDに対応する温泉詳細を取得

## 学習・実装で苦戦した点

### Spring Securityによる認証処理

これまで実装してきた機能では、「ユーザーがURLにアクセス → Controllerがリクエストを受け取る → HTMLを表示 → フォームの入力内容をPOSTで送信 → ControllerのPOST処理で受け取る」という流れが基本だった。

しかし、今回のログイン機能では、POSTされたログイン情報をControllerではなくSpring Securityが受け取り、認証処理を行う。そのため、これまでとは異なる処理の流れを理解することに苦戦した。

`SecurityFilterChain`については、「どのURLへのアクセスを許可するか」「ログイン成功後やログアウト後にどこへ遷移するか」といった設定が中心だったため、比較的スムーズに理解できた。

特に苦戦したのは`DaoAuthenticationProvider`の役割だった。当初は、`provider`変数に取得したUserの情報やパスワードそのものが格納されていると考えていた。

実際には、`DaoAuthenticationProvider`に対して、`CustomUserDetailsService`を使った「ユーザー情報の取得方法」と、`PasswordEncoder`を使った「パスワードの照合方法」を設定している。

この違いを理解することで、Spring Securityでは認証に必要なデータを事前に`provider`へ格納するのではなく、認証時に必要な処理を行うための設定を組み立てている、というイメージを持てるようになった。

### 温泉詳細表示

検索結果に表示された「詳細」リンクを押すことで、選択した温泉のIDを含む `/onsens/{onsenId}` へアクセスする。

Controllerでは `@PathVariable` を使用してURLから`onsenId`を受け取り、Serviceの`getOnsenById()`を呼び出す。

ServiceではRepositoryの`findById()`を使用し、指定されたIDに対応する温泉情報をDBから取得する。

`findById()`の戻り値には`Optional<Onsen>`を使用している。温泉IDは主キーであるため検索結果は最大1件となり、「温泉が存在する状態」と「存在しない状態」の両方を表現できるためである。

Controllerで取得結果を確認し、温泉が存在しない場合はホーム画面へリダイレクトする。

温泉が存在する場合は、`Optional`の`get()`を使用して中に格納されている`Onsen`オブジェクトを取り出し、Modelに格納する。

最後に`onsen/detail.html`へModelを渡し、取得した温泉の詳細情報を画面に表示する。

## 補足

本READMEの「学習・実装で苦戦した点」は、実装時に自身で理解した内容や考えたことを文章化し、生成AIを利用して誤字や表現、文章構成を整えています。
実装内容や学習内容については、自身でコードを確認・動作検証しながら記載しています。
