package com.example.gallerify.utils

sealed class UserResource {
    class LoggedOut : UserResource()
    class LoggedIn : UserResource()
}