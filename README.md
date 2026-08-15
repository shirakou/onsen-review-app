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

## 学習・実装で苦戦した点

### Spring Securityによる認証処理

これまで実装してきた機能では、「ユーザーがURLにアクセス → Controllerがリクエストを受け取る → HTMLを表示 → フォームの入力内容をPOSTで送信 → ControllerのPOST処理で受け取る」という流れが基本だった。

しかし、今回のログイン機能では、POSTされたログイン情報をControllerではなくSpring Securityが受け取り、認証処理を行う。そのため、これまでとは異なる処理の流れを理解することに苦戦した。

`SecurityFilterChain`については、「どのURLへのアクセスを許可するか」「ログイン成功後やログアウト後にどこへ遷移するか」といった設定が中心だったため、比較的スムーズに理解できた。

特に苦戦したのは`DaoAuthenticationProvider`の役割だった。当初は、`provider`変数に取得したUserの情報やパスワードそのものが格納されていると考えていた。

実際には、`DaoAuthenticationProvider`に対して、`CustomUserDetailsService`を使った「ユーザー情報の取得方法」と、`PasswordEncoder`を使った「パスワードの照合方法」を設定している。

この違いを理解することで、Spring Securityでは認証に必要なデータを事前に`provider`へ格納するのではなく、認証時に必要な処理を行うための設定を組み立てている、というイメージを持てるようになった。
