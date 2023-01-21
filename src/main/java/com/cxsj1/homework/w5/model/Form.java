package com.cxsj1.homework.w5.model;

public class Form {
    public static class LoginForm {
        public String username;
        public String password;
    }

    public static class PasswordForm {
        public String current_password;
        public String new_password;
    }

    public static class RegisterForm {
        public String username;
        public String password;
        public String nickname;
        public String sex;
    }

    public static class UserInfoForm {
        public String nickname;
        public String sex;
    }

    public static class BookForm {
        public String isbn;
        public String title;
        public String author;
        public String publisher;
        public String publish_at;
        public String page;
        public String binding;
        public String series;
        public String translator;
        public String original_title;
        public String producer;
        public String id;
        public String url;
        public String rating;
        public String rating_people;
        public String intro;
        public String cover;
        public String price;
    }
}
