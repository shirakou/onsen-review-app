# 温泉レビューアプリ

## 概要

温泉施設の検索、レビュー投稿、お気に入り登録ができるWebアプリです。

## 使用技術

- Java 17
- Spring Boot
- Spring Data JPA
- Spring Security
- Thymeleaf
- PostgreSQL
- Bootstrap

## 実装状況

### Entity

- User
- Onsen
- Review
- Favorite

### 機能

#### 新規会員登録

- 入力フォーム
- バリデーション
- メールアドレス重複チェック
- BCryptによるパスワードハッシュ化
- PostgreSQLへのユーザー登録